package Leetcode.AllQuestions;

/**
 * 0~N-1随机选一个位置和N-1作交换。0~N-2随机选一个位置和N-2作交换，直到0停。
 */
public class Leetcode_0384_ShuffleAnArray {

	class Solution {
		int[] origin;
		int[] shuffle;
		int N;
		public Solution(int[] nums) {
			N=nums.length;
			origin=nums;
			shuffle=new int[N];
			for (int i = 0; i < shuffle.length; i++) {
				shuffle[i]=origin[i];
			}
		}

		public int[] reset() {
			return origin;
		}

		public int[] shuffle() {//i位置(0~i)随机选一个和自己交换
			for (int i=N-1;i>=1;i--){
				swap(shuffle,i,(int)(Math.random()*(i+1)));
			}
			return shuffle;
		}

		private void swap(int[]arr,int a,int b){
			int t=arr[a];
			arr[a]=arr[b];
			arr[b]=t;
		}
	}

}
