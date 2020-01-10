package interfaces;

/**
 * @author jing.liu14@ucarinc.com
 * @date 2020/1/8
 * @description:
 */
public interface AbstractTree<K,V> {
    V get(K k);
    void put(K k, V v);
    void delete(K k);
    int getHeight();
}
