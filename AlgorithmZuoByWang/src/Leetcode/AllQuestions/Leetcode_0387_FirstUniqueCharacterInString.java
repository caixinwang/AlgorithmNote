package Leetcode.AllQuestions;

public class Leetcode_0387_FirstUniqueCharacterInString {

	public int firstUniqChar(String s) {
		char[] str = s.toCharArray();
		int[] map=new int[128];
		for (char c:str) map[c]++;
		for (int i = 0; i < str.length; i++) {
			if (map[str[i]]==1) return i;
		}
		return -1;
	}

}
