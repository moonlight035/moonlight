package game.maze;

/**
 * @author jing.liu14@ucarinc.com
 * @date 2020/6/16
 * @description:
 */
public class Test {
    public static void main(String[] args) throws InterruptedException {
        Maze maze = new Maze(50,50);
        maze.print();
        MazeSolution solution = new MazeSolution(maze.getMaze());
        solution.solve();
        solution.print();
    }
}
