package Leetcode.AllQuestions;

public class Leetcode_0581_ShortestUnsortedContinuousSubarray {

	/**
	 * 排序，我们知道最终较小的值要去左边，所以如果较小的值左边有比它大数，都要为较小值让道
	 * 同理，较大的值右边如果有比它小的，也要给较大值让道。
	 */
	public static int findUnsortedSubarray(int[] nums) {
		int N=nums.length;
		int max=Integer.MIN_VALUE,min=Integer.MAX_VALUE;
		int l=N-1,r=0;//l为大于min的最左的位置，r为小于max的最右的位置。l右边的数要为min让道，r左边的数要为max让道
		for (int i=0;i<N;i++){//同时维持往左和往右的逻辑
			if (nums[i]<max){//如果右边有比他小的数，都要给他让道,等于不用让道
				r=i;
			}else {//不断地更新目前较大的值
				max=nums[i];
			}
			if (nums[N-1-i]>min){
				l=N-1-i;
			}else {
				min=nums[N-1-i];
			}
		}
		return l>=r?0:r-l+1;
	}

}
