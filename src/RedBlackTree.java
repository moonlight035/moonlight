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
    private TreeNode getSite(Integer key){
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
    * description: 插入操作自平衡红黑树
    * @author: 刘竞(jing.liu14@ucarinc.com)
    * @param1 insertNode：
    * @Return: void
    * @Date: 2019-10-23 16-04-52
    */
    private void insertBalance(TreeNode insertNode){
        TreeNode parent = insertNode.parent;
        //父节点为黑色则不用处理
        if(parent.color==1) {return;}
        TreeNode grandParent = parent.parent;
        TreeNode uncle = grandParent.lChild==parent?grandParent.rChild:grandParent.lChild;
        //叔叔节点为叶子节点或者黑色
        if(uncle==null||uncle.color==1){
            grandParent.color=0;
            //插入节点和父节点位置相同
            if(parent.site.equals(insertNode.site)){
                parent.color=1;
                changeWithParent(parent);
            }
            //插入节点和父节点位置不同
            else {
                insertNode.color=1;
                changeWithParent(insertNode);
                changeWithParent(insertNode);
            }
        }
        else{
            grandParent = parent.parent;
            parent.color=uncle.color=1;
            if(grandParent.parent==null) {return ;}
            grandParent.color=0;
            if(grandParent.parent.color==0){
                insertBalance(grandParent);
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
        TreeNode father = getSite(key);
        TreeNode insertNode = new TreeNode(key,value);
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
        insertBalance(insertNode);
    }

    /**
    * description: 获取后继节点
    * @author: 刘竞(jing.liu14@ucarinc.com)
    * @param1 treeNode：
    * @Return: RedBlackTree.TreeNode
    * @Date: 2019-10-24 11-19-57
    */
    private TreeNode getBehindNode(TreeNode treeNode){
        TreeNode temp = treeNode.rChild;
        while(temp.lChild!=null){
            temp=temp.lChild;
        }
        return temp;
    }

    /**
    * description: 节点子节点的情况
    * @author: 刘竞(jing.liu14@ucarinc.com)
    * @param1 temp：
    * @Return: int : 1:红色子节点与该节点同向 0:红色子节点与该节点异向 -1:无红色子节点
    * @Date: 2019-10-24 15-54-57
    */
    private int childrenStatus(TreeNode temp){
        if(temp.lChild!=null&&temp.lChild.color==0&&temp.site==0
                ||temp.rChild!=null&&temp.rChild.color==0&&temp.site==1){
            return 1;
        }
        if(temp.lChild!=null&&temp.lChild.color==0
                ||temp.rChild!=null&&temp.rChild.color==0){
            return 0;
        }
        return -1;
    }

    /**
    * description: 删除操作自平衡
    * @author: 刘竞(jing.liu14@ucarinc.com)
    * @param1 targetNode：
    * @Return: void
    * @Date: 2019-10-24 11-20-53
    */
    private void deleteBalance(TreeNode targetNode){
        if(targetNode.parent==null) {
            if(targetNode.lChild==null&&targetNode.rChild==null) {
                root = null;
            }
            return;
        }
        if(targetNode.color==0) {
            if(targetNode.site==0) {targetNode.parent.lChild=null;}
            else {targetNode.parent.rChild=null;}
            return;
        }
        TreeNode parent = targetNode.parent;
        TreeNode brother = parent.lChild!=targetNode?parent.lChild:parent.rChild;
        if(brother.color==1){
            switch (childrenStatus(brother)){
                case 1:
                    brother.color=parent.color;
                    parent.color=1;
                    if(brother.site==0) {brother.lChild.color=1;}
                    else {brother.rChild.color=1;}
                    changeWithParent(brother);
                    break;
                case 0:
                    TreeNode nephewNode=brother.site==0?brother.rChild:brother.lChild;
                    nephewNode.color=parent.color;
                    parent.color=1;
                    brother.color=1;
                    changeWithParent(nephewNode);
                    changeWithParent(nephewNode);
                    break;
                case -1:
                    if(parent.color==0){
                        brother.color=0;
                        parent.color=1;
                    }
                    else{
                        brother.color=0;
                        deleteBalance(parent);
                    }
                    break;
                default:
                    System.out.println("fuck you");
                    return ;
            }
        }
        else{
            brother.color=1;
            parent.color=0;
            changeWithParent(brother);
            deleteBalance(targetNode);
        }
    }

    /**
    * description: 红黑树删除
    * @author: 刘竞(jing.liu14@ucarinc.com)
    * @param1 key：
    * @Return: boolean
    * @Date: 2019-10-24 10-29-56
    */
    public boolean delete(Integer key){
        TreeNode deleteNode = getSite(key);
        if(deleteNode==null||(!deleteNode.key.equals(key))){
            return false;
        }
        if(deleteNode.lChild==null&&deleteNode.rChild==null){
            deleteBalance(deleteNode);
            if(deleteNode.site==0){deleteNode.parent.lChild=null;}
            else {deleteNode.parent.rChild=null;}
        }
        else if(deleteNode.lChild!=null&&deleteNode.rChild!=null){
            TreeNode targetNode=getBehindNode(deleteNode);
            deleteNode.value=targetNode.value;
            deleteNode.key=targetNode.key;
            deleteBalance(targetNode);
            if(targetNode.site==0){targetNode.parent.lChild=null;}
            else {targetNode.parent.rChild=null;}
        }
        else {
            TreeNode unNullNode = deleteNode.lChild!=null?deleteNode.lChild:deleteNode.rChild;
            unNullNode.parent=deleteNode.parent;
            if(deleteNode.site==0){deleteNode.parent.lChild=unNullNode;}
            else {deleteNode.parent.rChild=unNullNode;}
            unNullNode.site=deleteNode.site;
            unNullNode.color=1;
        }
        return true;
    }

    /**
    * description: 打印树节点
    * @author: 刘竞(jing.liu14@ucarinc.com)
    * @param1 temp：
    * @Return: void
    * @Date: 2019-10-24 08-32-37
    */
    public void print(TreeNode temp){
        if(temp==null) {return;}
        System.out.println(temp.key+"=="+temp.value
                +"  color=="+(temp.color==0?"red":"black")
                +"  parentKey=="+(temp.parent==null?null:temp.parent.key)
                +"  lchild=="+(temp.lChild==null?null:temp.lChild.key)
                +"  rchild=="+(temp.rChild==null?null:temp.rChild.key));
        print(temp.lChild);
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
        TreeNode(Integer key, Integer value){
            this.key=key;
            this.value=value;
        }
    }
}
