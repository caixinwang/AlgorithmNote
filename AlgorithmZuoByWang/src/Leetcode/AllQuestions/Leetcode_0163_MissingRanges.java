package Leetcode.AllQuestions;

import java.util.ArrayList;
import java.util.List;

public class Leetcode_0163_MissingRanges {

	public static List<String> findMissingRanges(int[] nums, int lower, int upper) {
		List<String> ans = new ArrayList<>();
		for (int num : nums) {
			if (num > lower) {
				ans.add(miss(lower, num - 1));
			}
			if (num == upper) {
				return ans;
			}
			lower = num + 1;
		}
		if (lower <= upper) {
			ans.add(miss(lower, upper));
		}
		return ans;
	}

	// 生成"lower->upper"的字符串，如果lower==upper，只用生成"lower"
	public static String miss(int lower, int upper) {
		String left = String.valueOf(lower);
		String right = "";
		if (upper > lower) {
			right = "->" + String.valueOf(upper);
		}
		return left + right;
	}

	public static void main(String[] args) {
		int[] arr=new int[]{3,6,9,13,17,20};
		List<String> missingRanges = findMissingRanges(arr, 8, 15);
		for (String missingRange : missingRanges) {
			System.out.print(missingRange+" ");
		}

	}

}
