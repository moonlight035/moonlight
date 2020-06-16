package game.maze;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author jing.liu14@ucarinc.com
 * @date 2020/6/16
 * @description:
 */
public class MazeGenerate {
    private int[][] result;
    private int[] start;
    private int[] end;

    private final int WALL = 0;
    private final int ROUTE = 1;

    public void generateMaze(int x, int y){
        result = new int[x][y];
        start = new int[]{1,0};
        end = new int[]{x-2,y-1};
        result[1][0] = ROUTE;
        result[x-2][y-1] = ROUTE;
        Deque<int[]> node = new ArrayDeque<>();
        node.push(new int[]{1,1});
        while(result[x-2][y-2]!=ROUTE) {
            if(!node.isEmpty()) {
                int[] pop = node.pop();
                dig(pop[0], pop[1],-1, node);
            }else{
                break;
            }
        }
        if(result[x-2][y-2]!=ROUTE) {
            node.push(new int[]{x-2, y-2});
            while (result[x - 2][y - 2] != ROUTE) {
                if (!node.isEmpty()) {
                    int[] pop = node.pop();
                    dig(pop[0], pop[1],-1, node);
                } else {
                    break;
                }
            }
        }
    }

    private void dig(int x , int y,int move,Deque node){
        if(result[x][y]==WALL&&!isBorder(x,y)&&!maybeCircle(x,y)){
            result[x][y]=ROUTE;
            node.push(new int[]{x,y});
            for(int i=0 ; i<4 ; i++) {
                int r = new Random().nextInt(4);
                while(move!=-1&&(r==move||Math.abs(r-move)==2)){
                    r = new Random().nextInt(4);
                }
                switch (r){
                    case 0:dig(x,y+1,0,node);break;
                    case 1:dig(x+1,y,1,node);break;
                    case 2:dig(x,y-1,2,node);break;
                    case 3:dig(x-1,y,3,node);break;
                }
            }
        }
    }

    private boolean isBorder(int x, int y){
        if(x==0 || x==result.length-1 || y==0 || y==result[0].length-1)
            return true;
        return false;
    }

    private boolean maybeCircle(int x, int y){
        if(x==result.length-2&&y==result[0].length-2)
            return false;
        if(result[x-1][y]+result[x+1][y]+result[x][y-1]+result[x][y+1]<=ROUTE)
            return false;
        return true;
//        return false;
    }

    public void print(){
        for(int i=0 ; i<result.length ; i++){
            for(int j = 0 ; j < result[i].length ; j++)
                if(result[i][j]==WALL)
                    System.out.print("* ");
                else
                    System.out.print("  ");
            System.out.println();
        }
    }

    public static void main(String[] args) {
        MazeGenerate generate = new MazeGenerate();
        generate.generateMaze(20,20);
        generate.print();
    }
}
