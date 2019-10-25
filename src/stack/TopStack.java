package stack;

import java.util.Arrays;

/**
 * @author jing.liu14@ucarinc.com
 * @date 2019/10/25
 * @description:
 */
public class TopStack {
    /**存放元素*/
    private int[] store;
    /**false小顶堆  true大顶堆*/
    private boolean directIsBig;
    /**存放元素数量*/
    private int size;

    private final int GROW_SIZE=16;

    TopStack(boolean directIsBig){
        this.directIsBig=directIsBig;
        store=new int[GROW_SIZE];
    }

    TopStack(boolean directIsBig,int size){
        this.directIsBig=directIsBig;
        store=new int[size];
    }

    /**
    * description: 获得节点的父节点
    * @author: 刘竞(jing.liu14@ucarinc.com)
    * @param1 site：
    * @Return: int
    * @Date: 2019-10-25 09-51-22
    */
    private int getParentSite(int site){
        if(site%2==0) {
            return site/2-1;
        }
        else {
            return site/2;
        }
    }

    /**
    * description: 是否需要交换
    * @author: 刘竞(jing.liu14@ucarinc.com)
    * @param1 src：
    * @param2 pos：
    * @Return: boolean
    * @Date: 2019-10-25 09-55-12
    */
    private boolean isChange(int src, int pos, int[] target){
        if(directIsBig==(target[src]>target[pos])){
            return true;
        }
        return false;
    }

    /**
    * description: 提高数组容量
    * @author: 刘竞(jing.liu14@ucarinc.com)
    * @param1 ：
    * @Return: void
    * @Date: 2019-10-25 10-01-38
    */
    private void grow(){
        store= Arrays.copyOf(store,store.length+GROW_SIZE);
    }

    /**
    * description: 向上平衡节点
    * @author: 刘竞(jing.liu14@ucarinc.com)
    * @param1 site：
    * @Return: void
    * @Date: 2019-10-25 10-06-01
    */
    private void balance(int site,int[] target){
        if(site==0) {return;}
        if(target==store&&site>=size) {return ;}
        if(site>=target.length){return;}
        int parent=getParentSite(site);
        if(isChange(site,parent,target)){
            int temp=target[site];
            target[site]=target[parent];
            target[parent]=temp;
        }
        balance(parent,target);
    }
    /**
    * description: 插入节点
    * @author: 刘竞(jing.liu14@ucarinc.com)
    * @param1 num：
    * @Return: void
    * @Date: 2019-10-25 09-51-40
    */
    public void insert(int num){
        if(size==store.length) {grow();}
        store[size++]=num;
        balance(size-1,store);
    }

    /**
    * description: 移除堆顶并返回
    * @author: 刘竞(jing.liu14@ucarinc.com)
    * @param1 ：
    * @Return: int
    * @Date: 2019-10-25 10-08-13
    */
    public int remove(){
        int result=store[0];
        store=Arrays.copyOfRange(store,1,size);
        for(int i=size-1 ; i>=0 ; i++){
            if(2*i+1<size) {break;}
            balance(i,store);
        }
        return result;
    }

    /**
    * description: 获取堆顶
    * @author: 刘竞(jing.liu14@ucarinc.com)
    * @param1 ：
    * @Return: int
    * @Date: 2019-10-25 10-13-03
    */
    public int find(){
        if(size==0) {throw new ArrayIndexOutOfBoundsException("stupid");}
        return store[0];
    }

    /**
    * description: 大顶堆降、小顶堆升序
    * @author: 刘竞(jing.liu14@ucarinc.com)
    * @param1 target：
    * @Return: void
    * @Date: 2019-10-25 10-16-04
    */
    public void sort(int[] target){
        for(int i=0 ; i<target.length-1 ; i++) {
            for (int j = target.length-1-i; j >= 0; j--) {
                if (2 * j + 1 < target.length-i) {
                    break;
                }
                balance(j,target);
            }
            int temp=target[0];
            target[0]=target[target.length-1-i];
            target[target.length-1-i]=temp;
        }
    }

    /**
    * description: 长度
    * @author: 刘竞(jing.liu14@ucarinc.com)
    * @param1 ：
    * @Return: int
    * @Date: 2019-10-25 10-23-30
    */
    public int size() {
        return size;
    }
}
