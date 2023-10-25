package Leetcode.AllQuestions;

public class Leetcode_0136_SingleNumber {

	public static int singleNumber(int[] nums) {
		int res=0;
		for (int num : nums) {
			res^=num;
		}
		return res;
	}

}
