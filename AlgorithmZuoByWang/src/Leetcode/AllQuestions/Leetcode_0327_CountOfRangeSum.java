package Leetcode.AllQuestions;

import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

public class Leetcode_0327_CountOfRangeSum {
    /**
     * 给你一个整数数组 nums 以及两个整数 lower 和 upper 。
     * 求数组中，值位于范围 [lower, upper] （包含 lower 和 upper）之内的 区间和的个数 。
     * 区间和 S(i, j) 表示在 nums 中，位置从 i 到 j 的元素之和，包含 i 和 j (i ≤ j)。
     */

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

    static class SegmentTreeNode2{//动态开点线段树
        class Node{
            Node left,right;
            long sum;
            Node(long sum){
                this.sum=sum;
                left=right=null;
            }
        }
        long size;
        Node root;

        public SegmentTreeNode2(long size) {
            this.size=size;
            root=new Node(0);
        }

        public void add(long i,long A,long l,long r,Node cur){
            if(l>r) return;
            if(l==r) {
                cur.sum+=A;
                return;
            }
            long mid=(r-l>>1)+l;
            if(i<=mid){
                if(cur.left==null)cur.left=new Node(0);
                add(i,A,l,mid,cur.left);
            }else{
                if(cur.right==null)cur.right=new Node(0);
                add(i,A,mid+1,r,cur.right);
            }
            cur.sum=(cur.left==null?0:cur.left.sum)+(cur.right==null?0:cur.right.sum);
        }

        public void add(long i,long A){
            add(i,A,1,size,root);
        }

        public long query(long L,long R,long l,long r,Node cur){
            if(L<=l&&r<=R) return cur.sum;
            long mid=(r-l>>1)+l,left=0,right=0;
            if(L<=mid){
                if(cur.left==null)cur.left=new Node(0);
                left=query(L,R,l,mid,cur.left);
            }
            if(R>=mid+1){
                if(cur.right==null)cur.right=new Node(0);
                right=query(L,R,mid+1,r,cur.right);
            }
            return left+right;                        
        }

        public long query(long L,long R){
            if(L>R) return 0;
            return query(L, R, 1, size, root);
        }
    }
    
    //low<=sum-?<=high  sum-low>=?>=sum-high
    //不需要离散化，但是题目数据太恶心，所以很慢。要快的还是需要离散化。
    public int countRangeSum3(int[] arr, int low, int high) {
        final long t=1L<<58;
        SegmentTreeNode2 sg=new SegmentTreeNode2(1L<<60);
        long res=0;
        long sum=0;
        sg.add(sum+t,1);
        for (int i = 0; i < arr.length; i++) {
            sum+=arr[i];
            res+=sg.query(sum-high+t,sum-low+t);
            sg.add(sum+t,1);
        }
        return (int)res;
    }


}
