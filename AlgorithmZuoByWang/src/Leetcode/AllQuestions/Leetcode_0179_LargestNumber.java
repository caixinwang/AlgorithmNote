package Leetcode.AllQuestions;

import java.util.Arrays;
import java.util.Comparator;

public class Leetcode_0179_LargestNumber {

	public static class MyComparator implements Comparator<String> {

		@Override
		public int compare(String o1, String o2) {
			return (o2 + o1).compareTo(o1 + o2);//定义一个排序策略
		}

	}

	public String largestNumber(int[] nums) {
		String[] strs = new String[nums.length];
		for (int i = 0; i < nums.length; i++) {
			strs[i] = String.valueOf(nums[i]);
		}
		Arrays.sort(strs, new MyComparator());
		StringBuilder builder = new StringBuilder();
		for (String str : strs) {
			builder.append(str);
		}
		String ans = builder.toString();
		char[] str = ans.toCharArray();
		int i=-1;
		while(++i<str.length&&str[i]=='0');
		return i==str.length?"0":ans.substring(i);
	}
}
