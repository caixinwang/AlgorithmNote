package Leetcode.AllQuestions;

import java.util.Arrays;

public class LeetCode_0684_RedundantConnection {

    class UnionFind{
        int[] parents;//结点直接映射成数组了，元素为负是代表结点，绝对值是集合中元素的个数。正数为parent的对应的下标
        public UnionFind(int N){
            parents=new int[N];
            Arrays.fill(parents, -1);//所有结点都是自己的代表结点
        }

        private int findFather(int node){
            if (parents[node]<0) return node;
            else return parents[node]=findFather(parents[node]);
        }

        private boolean union(int node1,int node2){
            int a=findFather(node1);
            int b=findFather(node2);
            if (a==b) return false;
            if (-parents[node1]<-parents[node2]){//永远让a是大集合
                int t=a;
                a=b;
                b=t;
            }
            parents[a]+=parents[b];//更新集合里面元素的个数
            parents[b]=a;//小集合b挂到大集合a下面
            return true;
        }

        public int getNums(){
            int ans=0;
            for (int n:parents) if (n<0) ans++;
            return ans;
        }

    }

    public int[] findRedundantConnection(int[][] edges) {
        int N=edges.length;
        UnionFind unionFind=new UnionFind(N+1);
        for (int[] edge:edges){
            if (!unionFind.union(edge[0],edge[1])) return edge;
        }
        return null;
    }
}
