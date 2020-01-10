package btree;

/**
 * @author jing.liu14@ucarinc.com
 * @date 2020/1/9
 * @description:
 */
public class MyEntry<K,V>{
    private K k;
    private V v;

    public MyEntry(K k, V v) {
        this.k = k;
        this.v = v;
    }

    public K getK() {
        return k;
    }

    public void setK(K k) {
        this.k = k;
    }

    public V getV() {
        return v;
    }

    public void setV(V v) {
        this.v = v;
    }
}
