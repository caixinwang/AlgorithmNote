package Leetcode.AllQuestions;

import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

public class Leetcode_0327_CountOfRangeSum {

    class SegmentTree {
        int[] sum;
        int N;

        public SegmentTree(int N) {
            this.N = N;
            sum = new int[N << 2];
        }

        public int merge(int left, int right) {
            return left + right;
        }

        private void add(int L, int R, int A, int l, int r, int rt) {
            if (l==r){//递归到底
                sum[rt]+=A;
                return;
            }
            int mid=l+(r-l>>1);
            if (mid>=L) add(L,R,A,l,mid,rt<<1);
            if (mid<R) add(L,R,A,mid+1,r,rt<<1|1);
            sum[rt]=merge(sum[rt<<1],sum[rt<<1|1]);
        }

        public void add(int index,int A){
            add(index,index,A,1,N,1);
        }

        private int query(int L, int R, int l, int r, int rt){
            if (L<=l&&r<=R){
                return sum[rt];
            }
            int mid=l+(r-l>>1);
            int p1=0,p2=0;
            if (mid>=L) p1=query(L,R,l,mid,rt<<1);
            if (mid<R) p2=query(L,R,mid+1,r,rt<<1|1);
            return merge(p1,p2);
        }
        public int query(int L,int R){
            return query(L,R,1,N,1);
        }

    }

    /**
     * 要求arr[i...j]位于[low,high]之间，问有多少个这样的(i,j)
     * arr[i...j]=presum[j+1]-presum[i]  =>  presum[i]∈[presum[j+1]-high,presum[j+1]-low]
     * 问题等价于问有多少个presum[i]位于区间[presum[j+1]-high,presum[j+1]-low]
     * 其实就是一个单点修改（每次add 1），以及一个区间查询
     * 如果直接把presum[i]这个值映射到线段树的下标，太大了，行不通，所以我们把presum[i]做离散化
     * 求每个j结尾时候的答案，加起来返回。
     * j结尾时候的答案就是之前有多少累加和位于[presum[j]-high,presum[j]-low]
     */
    public int countRangeSum(int[] arr, int low, int high) {
        long[] presum=new long[arr.length+1];//多开一个空间，不然会漏掉0~i这种情况
        for (int i = 1; i < presum.length; i++) {
            presum[i]=presum[i-1]+(long)arr[i-1];
        }
        Set<Long> set=new TreeSet<>();
        for (long num:presum){
            set.add(num);
            set.add(num-high);
            set.add(num-low);
        }
        HashMap<Long,Integer> map=new HashMap<>();
        int no=1;
        for (long num:set){
            map.put(num,no++);
        }
        SegmentTree segmentTree=new SegmentTree(map.size());
        int ans=0;
        for (long num:presum){
            int index=map.get(num);
            int index1=map.get(num-high);
            int index2=map.get(num-low);
            ans+=segmentTree.query(index1,index2);
            segmentTree.add(index,1);
        }
        return ans;
    }

    class IndexTree{
        long[] sum;
        int N;
        public IndexTree(int N){
            this.N=N;
            sum=new long[N+1];
        }

        public void add(int index,int d){
            for (;index<=N;index+=index&-index){
                sum[index]+=d;
            }
        }

        public long query(int index){
            long ans=0;
            for (;index>0;index-=index&-index){
                ans+=sum[index];
            }
            return ans;
        }

        public long query(int L,int R){//用户传的也是从1开始的下标
            return query(R)-query(L-1);//L-1变为0了也满足，刚好减0
        }
    }

    public int countRangeSum2(int[] arr, int low, int high) {
        long[] presum=new long[arr.length+1];//多开一个空间，不然会漏掉0~i这种情况
        for (int i = 1; i < presum.length; i++) {
            presum[i]=presum[i-1]+(long)arr[i-1];
        }
        Set<Long> set=new TreeSet<>();
        for (long num:presum){
            set.add(num);
            set.add(num-high);
            set.add(num-low);
        }
        HashMap<Long,Integer> map=new HashMap<>();
        int no=1;
        for (long num:set){
            map.put(num,no++);
        }
        IndexTree indexTree=new IndexTree(map.size());
        int ans=0;
        for (long num:presum){
            int index=map.get(num);
            int index1=map.get(num-high);
            int index2=map.get(num-low);
            ans+=indexTree.query(index1,index2);
            indexTree.add(index,1);
        }
        return ans;
    }
}
