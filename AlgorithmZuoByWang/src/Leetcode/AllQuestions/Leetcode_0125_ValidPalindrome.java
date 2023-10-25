package Leetcode.AllQuestions;

public class Leetcode_0125_ValidPalindrome {
	public boolean isPalindrome(String s) {
		char[] str = s.toLowerCase().toCharArray();//忽略大小写
		int l=-1,r=str.length;
		while(l<=r){
			while(++l<str.length&&!(('a'<=str[l]&&str[l]<='z')||('0'<=str[l]&&str[l]<='9')));//找到第一个字母或者数字
			while(--r>=0&&!(('a'<=str[r]&&str[r]<='z')||('0'<=str[r]&&str[r]<='9')));
			if (l<=r&&str[l]!=str[r]) return false;
		}
		return true;
	}
}
