package Leetcode.AllQuestions;

public class Leetcode_0242_ValidAnagram {

	public static boolean isAnagram(String s, String t) {
		if (s==null||t==null||s.length()!=t.length()) return false;
		char[] str1 = s.toCharArray();
		char[] str2 = t.toCharArray();
		int[] count=new int[128];
		for (int i = 0; i < str1.length; i++) {
			count[str1[i]]++;
			count[str2[i]]--;
		}
		for (int i = 0; i < count.length; i++) {
			if (count[i]!=0) return false;
		}
		return true;
	}

}
