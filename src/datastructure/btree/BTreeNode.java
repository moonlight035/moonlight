package datastructure.btree;

import interfaces.MyNode;

import java.util.List;

/**
 * @author jing.liu14@ucarinc.com
 * @date 2020/1/9
 * @description:
 */

public class BTreeNode<K,V> extends MyNode {
    private BTreeNode parent;

    private List<BTreeNode<K,V>> child;

    private List<MyEntry<K,V>> entryList;

    private boolean isLeaf;

    public void setChild(List<BTreeNode<K, V>> child) {
        this.child = child;
    }

    public void setEntryList(List<MyEntry<K, V>> entryList) {
        this.entryList = entryList;
    }

    public BTreeNode<K,V> getParent() {
        return parent;
    }

    public List<BTreeNode<K,V>> getChild() {
        return child;
    }

    public List<MyEntry<K, V>> getEntryList() {
        return entryList;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setParent(BTreeNode parent) {
        this.parent = parent;
    }

    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }

}
