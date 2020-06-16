package game.maze;

import java.util.*;

/**
 * @author jing.liu14@ucarinc.com
 * @date 2020/6/16
 * @description:
 */
public class Maze {
    private int[] start;
    private int[] end;
    private int[][] maze;

    public Maze(int x, int y) {
        generateMaze(x,y);
    }

    private final int WALL = 0;
    private final int ROUTE = 1;

    private void generateMaze(int x, int y){
        maze = new int[x][y];
        start = new int[]{1,0};
        end = new int[]{x-2,y-1};
        maze[1][0] = ROUTE;
        maze[x-2][y-1] = ROUTE;
        Deque<int[]> node = new ArrayDeque<>();
        node.push(new int[]{1,1});
        while(maze[x-2][y-2]!=ROUTE) {
            if(!node.isEmpty()) {
                int[] pop = node.pop();
                dig(pop[0], pop[1],-1, node);
            }else{
                break;
            }
        }
//        if(maze[x-2][y-2]!=ROUTE) {
//            node.push(new int[]{x-2, y-2});
//            while (maze[x - 2][y - 2] != ROUTE) {
//                if (!node.isEmpty()) {
//                    int[] pop = node.pop();
//                    dig(pop[0], pop[1],-1, node);
//                } else {
//                    break;
//                }
//            }
//        }
    }

    private boolean dig(int x , int y, int move,Deque node){
        if(maze[x][y]==WALL&&!isBorder(x,y)&&!maybeCircle(x,y)){
            maze[x][y]=ROUTE;
            node.push(new int[]{x,y});
            boolean success = false;
            for(int i=0 ; i<4 ; i++) {
                int r = new Random().nextInt(4);
//                if(r==move)
//                    r = new Random().nextInt(4);
                while(move!=-1&&Math.abs(r-move)==2){
                    r = new Random().nextInt(4);
                }
                switch (r){
                    case 0:success = dig(x,y+1,0,node);break;
                    case 1:success = dig(x+1,y,1,node);break;
                    case 2:success = dig(x,y-1,2,node);break;
                    case 3:success = dig(x-1,y,3,node);break;
                }
            }
            if(!success){
                    if (dig(x, y + 1, 0, node) || dig(x + 1, y, 1, node)
                            || dig(x, y - 1, 2, node) || dig(x - 1, y, 3, node))
                        ;
            }
            return true;
        }
        return false;
    }

    private boolean isBorder(int x, int y){
        if(x==0 || x==maze.length-1 || y==0 || y==maze[0].length-1)
            return true;
        return false;
    }

    private boolean maybeCircle(int x, int y){
        if(x==maze.length-2&&y==maze[0].length-2)
            return false;
        if(maze[x-1][y]+maze[x+1][y]+maze[x][y-1]+maze[x][y+1]<=ROUTE)
            return false;
        return true;
    }

    public void print(){
        for(int i=0 ; i<maze.length ; i++){
            for(int j = 0 ; j < maze[i].length ; j++)
                if(maze[i][j]==WALL)
                    System.out.print("* ");
                else
                    System.out.print("  ");
            System.out.println();
        }
    }

    public int[][] getMaze(){
        return maze;
    }
}
