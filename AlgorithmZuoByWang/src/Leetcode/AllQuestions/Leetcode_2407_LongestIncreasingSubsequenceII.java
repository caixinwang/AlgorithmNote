package Leetcode.AllQuestions;

import java.util.TreeMap;
import java.util.TreeSet;

public class Leetcode_2407_LongestIncreasingSubsequenceII {

    class SegmentTree{//lazy版本的数据不能是复合的，例如不能是Node。如果是区间修改单点查询就不需要lazy
        int N;
        int[] max;
        boolean[] lzmod;
        int[] mod;

        public  SegmentTree(int N){
            this.N=N;
            lzmod=new boolean[N<<2];
            mod=new int[N<<2];
            max=new int[N<<2];
        }

        private void pushUp(int rt){
            max[rt]=Math.max(max[rt<<1],max[rt<<1|1]);
        }

        private void pushDown(int rt,int l,int r){
            if (lzmod[rt]){
                lzmod[rt<<1]=true;
                lzmod[rt<<1|1]=true;
                mod[rt<<1]=mod[rt];
                mod[rt<<1|1]=mod[rt];
                max[rt<<1]=mod[rt];
                max[rt<<1|1]=mod[rt];
                lzmod[rt]=false;
                mod[rt]=0;
            }
        }

        private void modify(int L,int R,int M,int rt,int l,int r){
            int mid=l+(r-l>>1);
            if (L<=l&&r<=R) {
                lzmod[rt]=true;
                mod[rt]=M;
                max[rt]=M;
            }else {
                pushDown(rt,l,r);
                if (L<=mid) modify(L,R,M,rt<<1,l,mid);
                if (mid+1<=R) modify(L,R,M,rt<<1|1,mid+1,r);
                pushUp(rt);
            }
        }

        public void modify(int L,int R,int M){
            modify(L,R,M,1,1,N);
        }

        private int query(int L,int R,int rt,int l,int r){
            int mid=l+(r-l>>1);
            if (L<=l&&r<=R) {
                return max[rt];
            }else {
                pushDown(rt,l,r);
                int p1=Integer.MIN_VALUE,p2=Integer.MIN_VALUE;
                if (L<=mid) p1=query(L,R,rt<<1,l,mid);
                if (mid+1<=R) p2=query(L,R,rt<<1|1,mid+1,r);
                return Math.max(p1,p2);
            }
        }

        public int query(int L,int R){
            return query(L,R,1,1,N);
        }
    }

    //dp[i]为必须以i结尾的最长递增子序列。i'满足arr[i]-arr[i']>=k。
    //如果i'存在的话，dp[i]=1+max{dp[i']....}，不存在的话dp[i]=1
    public int lengthOfLIS(int[] nums, int k) {
        int max=nums[0];
        for (int n:nums) max = Math.max(max, n);
        SegmentTree segmentTree=new SegmentTree(max);//区间是值！不是下标
        int[] dp=new int[nums.length];
        dp[0]=1;
        segmentTree.modify(nums[0],nums[0],dp[0]);//arr[i]映射为线段树上的点，上面存的值为dp[i]。这样就有arr[i]=>dp的映射，不需要遍历
        for (int i=1;i<dp.length;i++)  {
            dp[i]=1;//只有自己
            if (nums[i]!=1&&segmentTree.query(Math.max(1,nums[i]-k),nums[i]-1)!=0){//i‘存在，线段树区间从1开始
                dp[i]=1+segmentTree.query(Math.max(1,nums[i]-k),nums[i]-1);
            }
            segmentTree.modify(nums[i],nums[i],dp[i]);//保持映射含义
        }
        max=0;
        for (int n:dp) max = Math.max(max, n);
        return max;
    }

    public int lengthOfLIS2(int[] nums, int k) {//上面甚至全程没有用到dp[i-1]，直接空间压缩
        int max=nums[0];
        for (int n:nums) max = Math.max(max, n);
        SegmentTree segmentTree=new SegmentTree(max+1);//区间是值！不是下标
        int ans=0;
        for (int j:nums){
            j++;//所有值都+1，这样-1之后都还在线段树的表示范围内
            ans=1+ segmentTree.query(Math.max(1,j-k),j-1);
            segmentTree.modify(j,j,ans);
        }
        return segmentTree.query(1,segmentTree.N);
    }

    public int lengthOfLIS3(int[] nums, int k) {//做离散化,更慢了,因为本题的值的范围和数组的长度差不多。数组短的时候去离散化
        TreeSet<Integer> set=new TreeSet<>();
        for (int num:nums) {
            set.add(num);
            set.add(num-1);
            set.add(num-k);
        }
        TreeMap<Integer,Integer> idxMap=new TreeMap<>();
        int no=1;
        for (int num:set)idxMap.put(num,no++);
        SegmentTree segmentTree=new SegmentTree(idxMap.size());
        int ans=0;
        for (int num:nums){
            int index=idxMap.get(num);
            Integer preidx=idxMap.get(num-k);
            Integer tailidx=idxMap.get(num-1);
            ans=1+ segmentTree.query(preidx,tailidx);
            segmentTree.modify(index,index,ans);
        }
        return segmentTree.query(1,segmentTree.N);
    }


}
