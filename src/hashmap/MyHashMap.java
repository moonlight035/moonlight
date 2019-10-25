package hashmap;

import interfaces.MyNode;
import redblacktree.RedBlackTree;
import sun.reflect.generics.tree.Tree;

import javax.naming.directory.NoSuchAttributeException;

/**
 * @author jing.liu14@ucarinc.com
 * @date 2019/10/25
 * @description:
 */
public class MyHashMap {

    MyNode[] store;
    private int size;
    private int length;
    private final float GROW_FACTORY=0.75F;

    MyHashMap(int size){
        store=new MyNode[size];
        this.size=size;
    }


    /**
    * description: 扩充
    * @author: 刘竞(jing.liu14@ucarinc.com)
    * @param1 ：
    * @Return: void
    * @Date: 2019-10-25 11-36-19
    */
    private void resize(){
        MyHashMap myHashMap = new MyHashMap(size*2);
        for (MyNode myNode: store) {
            if(myNode==null) {continue;}
            if(myNode instanceof LinkNode){
                LinkNode temp = (LinkNode)myNode;
                while(temp!=null){
                    myHashMap.put(temp.key,temp.value);
                    temp=temp.next;
                }
            }
            if(myNode instanceof RedBlackTree.TreeNode){
                RedBlackTree.TreeNode root = (RedBlackTree.TreeNode)myNode;
                reStore(root,myHashMap);
            }
        }
        store=myHashMap.store;
        size=myHashMap.size;
    }

    /**
    * description: 遍历红黑树所有节点
    * @author: 刘竞(jing.liu14@ucarinc.com)
    * @param1 treeNode：
* @param2 hashMap：
    * @Return: void
    * @Date: 2019-10-25 12-24-03
    */
    private void reStore(RedBlackTree.TreeNode treeNode,MyHashMap hashMap){
        if(treeNode!=null){
            hashMap.put(treeNode.getKey(),treeNode.getValue());
            reStore(treeNode.getlChild(),hashMap);
            reStore(treeNode.getrChild(),hashMap);
        }
    }
    /**
    * description: 获取数据
    * @author: 刘竞(jing.liu14@ucarinc.com)
    * @param1 key：
    * @Return: int
    * @Date: 2019-10-25 11-28-41
    */
    public int get(int key) {
        int site=key%size;
        if(store[site]==null) {throw new RuntimeException("stupid");}
        if(store[site] instanceof LinkNode){
            LinkNode temp = (LinkNode)store[site];
            while(temp!=null){
                if(temp.key==key){
                    return temp.value;
                }
                temp=temp.next;
            }
        }
        if(store[site] instanceof RedBlackTree.TreeNode){
            RedBlackTree.TreeNode root=(RedBlackTree.TreeNode)store[site];
            RedBlackTree redBlackTree=root.getRedBlackTree();
            return redBlackTree.get(key);
        }
        throw new RuntimeException("stupid");
    }
    /**
    * description: 将链表修改为红黑树
    * @author: 刘竞(jing.liu14@ucarinc.com)
    * @param1 site：
    * @Return: void
    * @Date: 2019-10-25 11-22-17
    */
    private void changeToTree(int site){
        if(!(store[site] instanceof LinkNode)) {return;}
        RedBlackTree redBlackTree = new RedBlackTree();
        LinkNode temp = (LinkNode) store[site];
        while(temp!=null){
            redBlackTree.put(temp.key,temp.value);
            temp=temp.next;
        }
        store[site]=redBlackTree.getRoot();
    }

    /**
    * description: 插入数据
    * @author: 刘竞(jing.liu14@ucarinc.com)
    * @param1 key：
* @param2 value：
    * @Return: void
    * @Date: 2019-10-25 11-16-41
    */
    public boolean put(int key, int value){
        if(length>size*GROW_FACTORY) {
            resize();
        }
        int site=key%size;
        if(store[site]==null){
            LinkNode linkNode = new LinkNode(key,value);
            store[site]=linkNode;
            length++;
        }
        else{
            if(store[site] instanceof LinkNode){
                int size=1;
                LinkNode temp=(LinkNode) store[site];
                if(temp.key==key) {temp.value=value;return true;}
                while(temp.next!=null){
                    size++;
                    temp=temp.next;
                    if(temp.key==key) {temp.value=value;return true;}
                }
                LinkNode linkNode = new LinkNode(key,value);
                temp.next=linkNode;
                linkNode.pre=temp;
                size++;
                if(size>8){
                    changeToTree(site);
                }
            }
            if(store[site] instanceof RedBlackTree.TreeNode){
                RedBlackTree.TreeNode root = (RedBlackTree.TreeNode)store[site];
                RedBlackTree redBlackTree=root.getRedBlackTree();
                redBlackTree.put(key,value);
                store[site]=redBlackTree.getRoot();
            }
        }
        return true;
    }


    public boolean delete(int key){
        int site = key%size;
        if(store[site]==null) {return false;}
        if(store[site] instanceof LinkNode){
            LinkNode node = (LinkNode)store[site];
            do {
                if (node.key == key) {
                    break;
                }
                node = node.next;
            }while(node!=null);
            if(node==null) {return false;}
            if(node.pre==null) {store[site]=node.next;}
            else {
                node.pre.next=node.next;
                node.next.pre=node.pre;
            }
            return true;
        }
        else {
            RedBlackTree.TreeNode root = (RedBlackTree.TreeNode)store[site];
            RedBlackTree redBlackTree=root.getRedBlackTree();
            return redBlackTree.deletebyKey(key);
        }
    }
    /**
    * description: 链表节点
    * @author: 刘竞(jing.liu14@ucarinc.com)
    * @param1 null：
    * @Return:
    * @Date: 2019-10-25 11-20-21
    */
    private class LinkNode extends MyNode {
        private Integer key;
        private Integer value;
        private LinkNode next;
        private LinkNode pre;
        LinkNode(int key,int value){
            this.key=key;
            this.value=value;
        }
    }
}
