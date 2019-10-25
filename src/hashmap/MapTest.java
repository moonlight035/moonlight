package hashmap;

import java.util.HashMap;

/**
 * @author jing.liu14@ucarinc.com
 * @date 2019/10/25
 * @description:
 */
public class MapTest {
    public static void main(String[] args) {
        MyHashMap myHashMap = new MyHashMap(16);
        myHashMap.put(1,10);
        myHashMap.put(12,10);
        myHashMap.put(32,10);
        myHashMap.put(15,10);
        myHashMap.put(213,10);
        myHashMap.put(98,10);
        myHashMap.put(15,10);
        myHashMap.put(9,10);
        myHashMap.put(17,10);
        for(int i = 0 ; i<9 ; i++){
            myHashMap.put(17+i*16,11);
        }
        myHashMap.delete(15);
        myHashMap.delete(65);
    }
}
