import sun.reflect.generics.tree.Tree;

import java.util.TreeMap;
import java.util.TreeSet;

/**
 * @author jing.liu14@ucarinc.com
 * @date 2019/10/23
 * @description:
 */
public class RedBlackTree {

    /**根节点*/
    private TreeNode root;


    public TreeNode getRoot() {
        return root;
    }

    /**
    * description: 根据key拿到value
    * @author: 刘竞(jing.liu14@ucarinc.com)
    * @param1 key：
    * @Return: java.lang.Integer
    * @Date: 2019-10-23 15-44-21
    */
    public Integer get(Integer key){
        TreeNode temp=root;
        while(temp!=null){
            if(temp.key.equals(key)) {return temp.value;}
            if(temp.key>key) {temp=temp.lChild;continue;}
            if(temp.key<key) {temp=temp.rChild;continue;}
        }
        return null;
    }

    /**
    * description: 获得key插入的父节点
    * @author: 刘竞(jing.liu14@ucarinc.com)
    * @param1 key：
    * @Return: RedBlackTree.TreeNode
    * @Date: 2019-10-23 15-44-37
    */
    private TreeNode getInsertSite(Integer key){
        TreeNode temp=root;
        if(temp==null) {return null;}
        while(temp!=null){
            if(temp.key.equals(key)) {return temp;}
            if(temp.key>key) {
                if(temp.lChild==null) {return temp;}
                temp=temp.lChild;
                continue;
            }
            if(temp.key<key) {
                if(temp.rChild==null) {return temp;}
                temp=temp.rChild;
                continue;
            }
        }
        return null;
    }

    /**
    * description: 和父节点交换位置
    * @author: 刘竞(jing.liu14@ucarinc.com)
    * @param1 node：
    * @Return: void
    * @Date: 2019-10-23 16-32-55
    */
    private void changeWithParent(TreeNode node){
        if(node==null||node.parent==null) {return;}
        TreeNode parent=node.parent;
        if(node.site==0){
            parent.lChild=node.rChild;
            node.rChild=parent;
        }
        else {
            parent.rChild=node.lChild;
            node.lChild=parent;
        }
        node.parent=parent.parent;
        parent.parent=node;
        if(node.parent==null) {
            root=node;
        }
        else {
            int parentSite=parent.site;
            parent.site=(node.site-1)*(node.site-1);
            node.site=parentSite;
            if(parentSite==0){
                node.parent.lChild=node;
            }
            else {
                node.parent.rChild=node;
            }
        }
    }

    /**
    * description: 自平衡红黑树
    * @author: 刘竞(jing.liu14@ucarinc.com)
    * @param1 insertNode：
    * @Return: void
    * @Date: 2019-10-23 16-04-52
    */
    private void balance(TreeNode insertNode){
        TreeNode parent = insertNode.parent;
        //父节点为黑色则不用处理
        if(parent.color==1) {return;}
        TreeNode grandParent = parent.parent;
        TreeNode uncle = grandParent.lChild==parent?grandParent.rChild:grandParent.lChild;
        //叔叔节点为叶子节点
        if(uncle==null||uncle.color==1){
            parent.color=1;
            grandParent.color=0;
            //插入节点和父节点位置相同
            if(parent.site.equals(insertNode.site)){
                changeWithParent(parent);
            }
            //插入节点和父节点位置不同
            else {
                changeWithParent(insertNode);
                changeWithParent(insertNode);
            }
        }
        else{
            grandParent = parent.parent;
            parent.color=uncle.color=1;
            if(grandParent.parent==null) {return ;}
            if(grandParent.parent.color==0){
                grandParent.color=0;
                balance(grandParent);
            }
        }
    }
    /**
    * description: 插入key value
    * @author: 刘竞(jing.liu14@ucarinc.com)
    * @param1 key：
* @param2 value：
    * @Return: void
    * @Date: 2019-10-23 15-44-49
    */
    public void put(Integer key,Integer value){
        TreeNode father = getInsertSite(key);
        TreeNode insertNode = new TreeNode(key,value,father);
        if(father==null) {
            insertNode.color=1;
            root=insertNode;
            return;
        }
        if(father.key.equals(key)) {father.value=value;return;}
        insertNode.parent=father;
        if(father.key>key) {
            insertNode.site=0;
            father.lChild = insertNode;
        }
        if(father.key<key) {
            insertNode.site=1;
            father.rChild = insertNode;
        }
        balance(insertNode);
    }

    public void print(TreeNode temp){
        if(temp==null) {return;}
        print(temp.lChild);
        System.out.println(temp.key+"=="+temp.value
                +"  parentKey=="+(temp.parent==null?null:temp.parent.key)
                +"  lchild=="+(temp.lChild==null?null:temp.lChild.key)
                +"  rchild=="+(temp.rChild==null?null:temp.rChild.key));
        print(temp.rChild);
    }

    /**
    * description: 树节点
    * @author: 刘竞(jing.liu14@ucarinc.com)
    * @param1 null：
    * @Return:
    * @Date: 2019-10-23 16-35-44
    */
    private class TreeNode {
        private Integer key;
        private Integer value;
        //0为红 1为黑
        private int color;
        private TreeNode parent;
        private TreeNode lChild;
        private TreeNode rChild;
        //0为左 1为右
        private Integer site;
        TreeNode(Integer key, Integer value, TreeNode parent){
            this.key=key;
            this.value=value;
        }
    }
}
