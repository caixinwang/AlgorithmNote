package Leetcode.AllQuestions;

public class Leetcode_0307_RangeSumQueryMutable {


    /**
     * 内部接口永远都是add和query，如果要update，其实都是转为add来处理。并且如果要有update操作，需要有另外一个arr来存放元素
     * add和query接口永远只接收1开始的下标。用户接口看情况，需要转化成1开始的下标
     */
    class NumArray {
        int[] sum;
        int[] arr;
        int N;

        public NumArray(int[] nums) {
            N=nums.length;
            sum=new int[N+1];
            arr=new int[N];
            for (int i = 0; i < nums.length; i++) {
                update(i,nums[i]);
            }
        }

        public void update(int index, int val) {
            int d=val-arr[index];
            arr[index]=val;
            add(index+1,d);
        }

        private void add(int index,int d){
            for (;index<=N;sum[index]+=d,index+=index&-index);
        }

        private int query(int index){
            int ans=0;
            for (;index>0;ans+=sum[index],index-=index&-index);
            return ans;
        }

        public int sumRange(int left, int right) {
            return query(right+1)-query(left);
        }
    }
}
