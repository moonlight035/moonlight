/**
 * @author jing.liu14@ucarinc.com
 * @date 2019/10/23
 * @description:
 */
public class TreeTest {
    public static void main(String[] args) {
        RedBlackTree redBlackTree = new RedBlackTree();
        redBlackTree.put(1,987);
        redBlackTree.put(9,64);
        redBlackTree.put(87,21);
        redBlackTree.put(65,69);
        redBlackTree.put(15,321);
        redBlackTree.put(36,151);
        redBlackTree.print(redBlackTree.getRoot());
        System.out.println(redBlackTree.get(1));
        redBlackTree.put(1,999);
        System.out.println(redBlackTree.get(1));
    }
}
