package Leetcode.AllQuestions;

public class Leetcode_0026_RemoveDuplicatesFromSortedArray {

	public static int removeDuplicates(int[] nums) {
		if (nums==null||nums.length==0) return 0;
		int index=0;
		int count=0;
		for (int cur=0;cur<nums.length;cur++){
			if (cur==0||nums[cur]!=nums[cur-1]){//要和前面的不一样，并且0位置也算
				count++;
				nums[index++]=nums[cur];
			}
		}
		return count;
	}

}
