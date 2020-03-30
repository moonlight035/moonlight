package datastructure.stack;

import java.util.Arrays;

/**
 * @author jing.liu14@ucarinc.com
 * @date 2019/10/25
 * @description:
 */
public class StackTest {
    public static void main(String[] args) {
        TopStack topStack = new TopStack(true,16);
        TopStack topStack1 = new TopStack(false,16);
        topStack.insert(5);
        topStack.insert(9);
        topStack.insert(8);
        topStack.insert(16);
        topStack.insert(7);
        topStack.insert(18);
        topStack.insert(13);
        int a[]={2,6,454,98,65,651,321,54,8,79,8,987,98,5,46,546,54};
        System.out.println(topStack.find());
        topStack.sort(a);
        System.out.println(Arrays.toString(a));
        topStack1.sort(a);
        System.out.println(Arrays.toString(a));
    }
}
