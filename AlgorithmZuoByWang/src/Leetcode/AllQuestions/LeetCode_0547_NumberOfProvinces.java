package Leetcode.AllQuestions;

import java.util.Arrays;

public class LeetCode_0547_NumberOfProvinces {

    class UnionFind{
        int[] parents;//结点直接映射成数组了，元素为负是代表结点，绝对值是集合中元素的个数。正数为parent的对应的下标
        public UnionFind(int[][] isConnected){
            int N=isConnected.length;
            parents=new int[N];
            Arrays.fill(parents, -1);//所有结点都是自己的代表结点
            for (int a = 0; a < N; a++) {
                for (int b = 0; b < N; b++) {
                    if (isConnected[a][b]==1) union(a,b);
                }
            }
        }

        private int findFather(int node){
            if (parents[node]<0) return node;
            else return parents[node]=findFather(parents[node]);
        }

        private void union(int node1,int node2){
            int a=findFather(node1);
            int b=findFather(node2);
            if (a==b) return;
            if (-parents[node1]<-parents[node2]){//永远让a是大集合
                int t=a;
                a=b;
                b=t;
            }
            parents[a]+=parents[b];//更新集合里面元素的个数
            parents[b]=a;//小集合b挂到大集合a下面
        }

        public int getNums(){
            int ans=0;
            for (int n:parents) if (n<0) ans++;
            return ans;
        }

    }
    public int findCircleNum(int[][] isConnected) {
        UnionFind unionFind=new UnionFind(isConnected);
        return unionFind.getNums();
    }
}
