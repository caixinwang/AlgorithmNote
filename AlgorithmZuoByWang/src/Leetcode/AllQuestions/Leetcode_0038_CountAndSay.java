package Leetcode.AllQuestions;

public class Leetcode_0038_CountAndSay {
	public static String countAndSay(int n) {
		return process(n);
	}

	private static String process(int n) {
		if (n==1) return "1";
		char[] str = process(n - 1).toCharArray();
		StringBuilder builder=new StringBuilder();
		for (int i = 0,p=i; i < str.length; i=p) {
			char num = str[i];
			while(++p< str.length&&str[p]==str[p-1]);//来到第一个不等于i位置的字符的位置,(p-i)就是长度
			builder.append(p-i).append(num);
		}
		return builder.toString();
	}
}
