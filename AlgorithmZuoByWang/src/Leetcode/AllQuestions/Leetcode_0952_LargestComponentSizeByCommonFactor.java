package Leetcode.AllQuestions;

import java.util.Arrays;
import java.util.HashMap;

public class Leetcode_0952_LargestComponentSizeByCommonFactor {//并查集

    public int largestComponentSize(int[] arr) {//N * 根号v
        int N = arr.length;
        UnionFind unionFind = new UnionFind(N);
        HashMap<Integer, Integer> fatorsMap = new HashMap<>();
        for (int i = 0; i < N; i++) {
            int num = arr[i];
            int limit = (int) Math.sqrt(num);
            for (int j = 1; j <= limit; j++) {
                if (num % j == 0) {
                    if (j != 1) {
                        if (!fatorsMap.containsKey(j)) {
                            fatorsMap.put(j, i);
                        } else {
                            unionFind.union(fatorsMap.get(j), i);
                        }
                    }
                    int other = num / j;
                    if (other != 1) {
                        if (!fatorsMap.containsKey(other)) {
                            fatorsMap.put(other, i);
                        } else {
                            unionFind.union(fatorsMap.get(other), i);
                        }
                    }
                }
            }
        }
        return unionFind.maxSize();
    }

    public static class UnionFind {
        private int [] parent;//给元素编号，默认从0开始,下标i就代表是第i号元素

        public UnionFind(int N) {//初始状态有N个独立的集合
            parent=new int[N];
            Arrays.fill(parent, -1);//全部填成-1代表是一开始所有的结点都是代表结点
        }

        public int maxSize(){//返回最大的集合有多少个元素
            int max=Integer.MIN_VALUE;
            for (int i = 0; i < parent.length; i++) {
                if (parent[i]<0){//代表结点我才看
                    max = Math.max(max, -parent[i] );
                }
            }
            return max;
        }

        public int find(int i){//找i所在集合的代表结点
            if (parent[i]<0) return i;
            else return parent[i]=find(parent[i]);//递归的含义就是找代表结点，且一路打扁平，尾递归，效率很高
        }

        public void union(int i,int j){//i和j所在的集合合并去，小的挂在大的下面
            int f1 = find(i);
            int f2 = find(j);
            if (f1==f2) return;//相同的集合，合并个毛
            if (-parent[f2]>-parent[f1]){//保证f1代表最大，f2代表最小
                int tmp=f1;
                f1=f2;
                f2=tmp;
            }
            parent[f1] += parent[f2];// 更新大集合的大小，-2 + (-3) =-5 ,含义没变
            parent[f2]=f1;//小集合f2挂在大集合f1上面
        }
    }

}
