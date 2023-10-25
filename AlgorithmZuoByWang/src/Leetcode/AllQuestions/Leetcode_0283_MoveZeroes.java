package Leetcode.AllQuestions;

public class Leetcode_0283_MoveZeroes {

	/**
	 * 一般这种题两种思路，第一种是遇到一个垃圾往后面放，这种就是双指针，一个r代表垃圾区，一个l代表有用区。
	 * 但是这题要求原本的顺序不乱，所以垃圾区做法不行。那么我们就只维持一个有用区，遇到有用的往有用区放就行了。
	 * 推到头了，有用区以外的全部刷成0即可。
	 */
	public static void moveZeroes(int[] nums) {
		int r=0;
		int l=-1;//[...,l]都是非零元素
		while (r<nums.length) {
			if (nums[r]!=0) swap(nums,r++,++l);//极端情况就是没有0，那么就是一直自己和自己交换
			else r++;
		}
		while(++l< nums.length) nums[l]=0;
	}

	public static void swap(int[] arr, int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

}
