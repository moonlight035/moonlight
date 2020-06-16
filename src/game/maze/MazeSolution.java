package game.maze;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * @author jing.liu14@ucarinc.com
 * @date 2020/6/16
 * @description:
 */
public class MazeSolution {
    private Node start;
    private Node end;
    private int[][] maze; //0 通过 1 不通
    private List<Node> result;
    private ThreadPoolExecutor executor = new ThreadPoolExecutor(5,5,0, TimeUnit.MINUTES,new LinkedBlockingQueue<>());
    private Map<Node,Node> dealedNodeMap = new ConcurrentHashMap<>();
    private AtomicInteger atomicInteger = new AtomicInteger(0);
    private final int WALL = 0;
    private final int ROUTE = 1;

    public MazeSolution(int[][] maze){
        this.maze = maze;
        start = new Node(1,0,0,null);
        end = new Node(maze.length-2,maze[0].length-1,0,null);
        result = Collections.synchronizedList(new ArrayList<>());
    }

    public void solve() throws InterruptedException {
        atomicInteger.incrementAndGet();
        executor.execute(()->{
            try {
                dealNode(start);
            } finally {
                atomicInteger.decrementAndGet();
            }
        });
        while(atomicInteger.get()!=0)
            Thread.sleep(1000);
        executor.shutdown();
    }

    private void dealNode(Node node){
        if(node.x==end.x&&node.y==end.y){
            result.add(node);
            return;
        }
        Node pre = dealedNodeMap.putIfAbsent(node,node);
        if(pre == null){
            for (Node n : getLegalNode(node)) {
                atomicInteger.incrementAndGet();
                executor.execute(()->{
                    try {
                        dealNode(n);
                    } finally {
                        atomicInteger.decrementAndGet();
                    }
                });
            }
        }
        else {
            synchronized (pre){
                if(pre.stepCount>node.stepCount){
                    pre.stepCount = node.stepCount;
                    pre.preNode = node.preNode;
                }
            }
        }
    }

    private List<Node> getLegalNode(Node node){
        int[][] predict = new int[][]{{node.x-1,node.y},{node.x+1,node.y},
                {node.x,node.y+1},{node.x,node.y-1}};
        List<Node> result = new ArrayList();
        for (int i = 0; i < predict.length; i++) {
            int x=predict[i][0];
            int y=predict[i][1];
            Node pre = node.preNode;
            if(y>=0 && (pre==null||x!=pre.x||y!=pre.y) && maze[x][y]==ROUTE
                    || (x==end.x&& y==end.y)){
                result.add(new Node(x,y,node.stepCount+1,node));
            }
        }
        return result;
    }

    private boolean isBorder(int x, int y){
        if(x==0 || x<=maze.length-1 || y==0 || y==maze[0].length-1)
            return true;
        return false;
    }

    public void print(){
        result.sort((n1,n2)->{
            if(n1.stepCount==n2.stepCount)
                return 0;
            return n1.stepCount<n2.stepCount?1:-1;
        });
        List<Node> solution = new ArrayList<>();
        if(result.size()==0){
            System.out.println("no solution");
            return;
        }
        Node best = result.get(0);
        System.out.println(result.size());
        System.out.println(best.stepCount);
        for(Node temp = best; temp!=null ; temp = temp.preNode)
            maze[temp.x][temp.y] = -1;
        for(int i=0 ; i<maze.length ; i++){
            for(int j = 0 ; j < maze[i].length ; j++)
                if(maze[i][j]==WALL)
                    System.out.print("* ");
                else if(maze[i][j]==ROUTE)
                    System.out.print("  ");
                else
                    System.out.print("0 ");
            System.out.println();
        }
    }

    private class Node {
        int x;
        int y;
        int stepCount;
        Node preNode;

        public Node(int x, int y, int stepCount, Node preNode) {
            this.x = x;
            this.y = y;
            this.stepCount = stepCount;
            this.preNode = preNode;
        }

        @Override
        public boolean equals(Object obj) {
            if(obj instanceof Node){
                if(obj==this) return true;
                Node node = (Node)obj;
                if(this.x == node.x && this.y == node.y)
                    return true;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return x+y;
        }
    }
}
