package btree;

import interfaces.AbstractTree;

import java.util.*;

/**
 * @author jing.liu14@ucarinc.com
 * @date 2020/1/8
 * @description:
 */
public class BTree<K,V> implements AbstractTree<K,V> {

    private BTreeNode<K,V> root;

    private int degree;

    private int height;

    private Comparator<? super K> comparator;

    public BTree(int degree, Comparator<? super K> comparator){
        if(degree<2)
            throw new RuntimeException();
        this.degree=degree;
        this.comparator=comparator;
    }

    public BTree(int degree){
        this(degree,null);
    }

    @Override
    public V get(K k) {
        if(root==null) return null;
        return getVal(root,k).getV();
    }

    private MyEntry<K,V> getVal(BTreeNode<K,V> node, K k){
        List<BTreeNode<K,V>> childs = node.getChild();
        List<MyEntry<K, V>> entryList = node.getEntryList();
        int site;
        for (site = 0; site < entryList.size(); site++) {
            if(compare(k,entryList.get(site).getK())<=0) break;
        }
        if(site<entryList.size()&&compare(k,entryList.get(site).getK())==0)
            return entryList.get(site);
        if(node.isLeaf()) return null;
        return getVal(childs.get(site),k);
    }

    @Override
    public void put(K k, V v) {
        if(root==null){
            root = new BTreeNode<>();
            MyEntry<K,V> entry = new MyEntry(k,v);
            root.setEntryList(new ArrayList(Arrays.asList(entry)));
            root.setLeaf(true);
            height++;
        }
        else putVal(root,k,v);
    }

    private void putVal(BTreeNode<K,V> node, K k, V v){
        List<BTreeNode<K,V>> childList = node.getChild();
        List<MyEntry<K, V>> entryList = node.getEntryList();
        int site;
        for (site = 0; site < entryList.size(); site++) {
            if(compare(k,entryList.get(site).getK())<=0) break;
        }
        if(site<entryList.size()&&compare(k,entryList.get(site).getK())==0)
            entryList.get(site).setV(v);
        BTreeNode<K,V>[] nodes=null;
        if(entryList.size()==degree*2-1) {
            nodes = splitNode(node);
        }
        if(node.isLeaf()){
            MyEntry<K,V> entry =new MyEntry<>(k,v);
            if(nodes==null)
                entryList.add(site,new MyEntry<>(k,v));
            else if(site<=degree-1)
                nodes[0].getEntryList().add(site,entry);
            else
                nodes[1].getEntryList().add(site-degree,entry);
        }else{
            if(nodes==null)
                putVal(childList.get(site),k,v);
            else if(site<=degree-1)
                putVal(nodes[0].getChild().get(site),k,v);
            else
                putVal(nodes[1].getChild().get(site-degree),k,v);
        }

    }

    private BTreeNode<K,V>[] splitNode(BTreeNode<K,V> node){
        List<BTreeNode<K,V>> childList = node.getChild();
        List<MyEntry<K, V>> entryList = node.getEntryList();
        BTreeNode<K,V> parent = node.getParent();
        BTreeNode<K,V> newNode = new BTreeNode<>();
        List<MyEntry<K, V>> newEntryList = new ArrayList(entryList.subList(degree,2*degree-1));
        entryList.removeAll(newEntryList);
        MyEntry<K,V> upEntry = entryList.get(degree-1);
        entryList.remove(upEntry);
        newNode.setEntryList(newEntryList);
        newNode.setLeaf(node.isLeaf());
        if(!node.isLeaf()){
            List<BTreeNode<K,V>> newChildList = new ArrayList(entryList.subList(degree,2*degree));
            childList.removeAll(newChildList);
            newNode.setChild(newChildList);
        }
        if(parent==null){
            parent = new BTreeNode();
            parent.setLeaf(false);
            parent.setChild(new ArrayList<>());
            parent.setEntryList(new ArrayList<>());
            node.setParent(parent);
            root = parent;
            height++;
        }
        newNode.setParent(parent);
        upNodeToParent(node,upEntry,newNode);
        return new BTreeNode[]{node,newNode};
    }

    private void upNodeToParent(BTreeNode<K,V> lchild, MyEntry<K,V> entry, BTreeNode<K,V> rchild){
        BTreeNode<K,V> parent = lchild.getParent();
        List<BTreeNode<K,V>> childList = parent.getChild();
        List<MyEntry<K, V>> entryList = parent.getEntryList();
        int site;
        for (site = 0; site < entryList.size(); site++) {
            if(compare(entry.getK(),entryList.get(site).getK())<=0) break;
        }
        entryList.add(site,entry);
        if(childList.size()>0)
            childList.remove(site);
        childList.add(site,rchild);
        childList.add(site,lchild);
    }

    @Override
    public void delete(K k) {
        if(root==null) return;
        deleteVal(root,k);
    }

    private void deleteVal(BTreeNode<K,V> node, K k){
        List<BTreeNode<K,V>> childList = node.getChild();
        List<MyEntry<K, V>> entryList = node.getEntryList();
        int site;
        for (site = 0; site < entryList.size(); site++) {
            if(compare(k,entryList.get(site).getK())<=0) break;
        }
        if(site<entryList.size()&&compare(k,entryList.get(site).getK())==0){
            nodeDelete(site,node,k);
        }
        else{
            BTreeNode<K,V> next = childList.get(site);
            if(next.getEntryList().size()>=degree){
                deleteVal(next,k);
            }
            else{
                if(site>0&&childList.get(site-1).getEntryList().size()>=degree){
                    getEntryFromBro(node,site,true);
                    deleteVal(childList.get(site),k);
                }
                else if(site<childList.size()-2&&childList.get(site+1).getEntryList().size()>=degree){
                    getEntryFromBro(node,site,false);
                    deleteVal(childList.get(site),k);
                }
                else if(site>0){
                    mergeNode(childList.get(site-1),childList.get(site),entryList.get(site-1));
                    deleteVal(childList.get(site-1),k);
                }
                else {
                    mergeNode(childList.get(site),childList.get(site+1),entryList.get(site));
                    deleteVal(childList.get(site),k);
                }
            }
        }
    }

    /**flag:true 从左节点移过来  flag:false 从右节点移过来*/
    private void getEntryFromBro(BTreeNode<K,V> parent, int site, boolean flag){
        BTreeNode<K,V> to = parent.getChild().get(site);
        BTreeNode<K,V> from = flag?parent.getChild().get(site-1):parent.getChild().get(site+1);
        List<BTreeNode<K,V>> childList = from.getChild();
        List<MyEntry<K, V>> entryList = from.getEntryList();
        if(flag){
            exchangeEntry(parent.getEntryList().get(site),entryList.get(entryList.size()-1));
            to.getEntryList().add(0,entryList.get(entryList.size()-1));
            to.getChild().add(0,childList.get(childList.size()-1));
            childList.remove(childList.size()-1);
            entryList.remove(entryList.size()-1);
        }
        else{
            exchangeEntry(parent.getEntryList().get(site),entryList.get(0));
            to.getEntryList().add(entryList.get(0));
            to.getChild().add(childList.get(0));
            childList.remove(0);
            entryList.remove(0);
        }
    }

    /**node 的entry个数>=degree*/
    private void nodeDelete(int site, BTreeNode<K,V> node, K k){
        List<BTreeNode<K,V>> childList = node.getChild();
        List<MyEntry<K, V>> entryList = node.getEntryList();
        if(node.isLeaf())
            entryList.remove(site);
        else {
            BTreeNode<K,V> lchild = node.getChild().get(site);
            BTreeNode<K,V> rchild = node.getChild().get(site+1);
            if(lchild.getEntryList().size()>=degree){
                exchangeEntry(entryList.get(site),lchild.getEntryList().get(lchild.getEntryList().size()));
                nodeDelete(lchild.getEntryList().size(),lchild,k);
            }
            else if(rchild.getEntryList().size()>=degree){
                exchangeEntry(entryList.get(site),rchild.getEntryList().get(0));
                nodeDelete(0,rchild,k);
            }
            else {
                MyEntry<K,V> entry = entryList.get(site);
                mergeNode(lchild,rchild,entry);
                nodeDelete(degree-1,lchild,k);
            }
        }
    }

    private void mergeNode(BTreeNode<K,V> lchild, BTreeNode<K,V> rchild, MyEntry<K,V> entry){
        lchild.getEntryList().add(entry);
        lchild.getEntryList().addAll(rchild.getEntryList());
        if(!lchild.isLeaf())
            lchild.getChild().addAll(rchild.getChild());
        BTreeNode<K, V> parent = lchild.getParent();
        parent.getChild().remove(rchild);
        parent.getEntryList().remove(entry);
        if(parent.getEntryList().size()==0){
            root = lchild;
            height--;
        }
    }

    private void exchangeEntry(MyEntry<K,V> lentry, MyEntry<K,V> rentry){
        K k = lentry.getK();
        V v = rentry.getV();
        lentry.setK(rentry.getK());
        lentry.setV(rentry.getV());
        rentry.setK(k);
        rentry.setV(v);
    }

    @Override
    public int getHeight() {
        return height;
    }

    private int compare(K k1, K k2){
        if(comparator!=null){
            return comparator.compare(k1,k2);
        }
        return ((Comparable<? super K>)k1).compareTo(k2);
    }

    public boolean isBTree(){
        return isBTree(root,degree);
    }

    public boolean isBTree(BTreeNode<K,V> node, int degree){
        List<BTreeNode<K,V>> childList = node.getChild();
        List<MyEntry<K, V>> entryList = node.getEntryList();
        if(node.getParent()==null){
            return true;
        }
        if(entryList.size()<degree-1){
            return false;
        }
        for (BTreeNode<K, V> child : childList) {
            if(!isBTree(child,degree))
                return false;
        }
        return true;
    }

    public static void main(String[] args) {
        BTree<Integer,String> bTree = new BTree(3);
        bTree.put(1,null);
        bTree.put(2,null);
        bTree.put(3,null);
        bTree.put(4,null);
        bTree.put(5,null);
        bTree.put(6,null);
//        bTree.put(7,null);
        System.out.println(bTree.height);
        bTree.delete(3);
        bTree.delete(6);
        System.out.println(bTree.height);
    }
}
