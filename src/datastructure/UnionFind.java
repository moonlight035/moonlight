package datastructure;

/**
 * 并查集
 * @author jing.liu14@ucarinc.com
 * @date 2020/9/18
 * @description:
 */
public class UnionFind {
    private int[] ancestor;
    public UnionFind(int size){
        ancestor = new int[size+1];
        for(int i = 0 ; i < ancestor.length ; i++)
            ancestor[i] = i;
    }

    public int find(int node){
        if (ancestor[node] != node)
            ancestor[node] = find(ancestor[node]);
        return ancestor[node];
    }

    public void union(int u, int v){
        ancestor[find(v)] = find(u);
    }

    public boolean isWith(int u, int v){
        return find(u) == find(v);
    }
}
