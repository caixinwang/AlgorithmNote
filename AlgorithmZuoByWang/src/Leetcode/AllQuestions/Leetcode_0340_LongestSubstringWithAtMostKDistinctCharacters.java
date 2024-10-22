package Leetcode.AllQuestions;


/**
 * 给你一个字符串 s 和一个整数 k ，请你找出至多包含k个不同字符的最长子串的长度
 */
public class Leetcode_0340_LongestSubstringWithAtMostKDistinctCharacters {


	public static int lengthOfLongestSubstringKDistinct1(String str, int k) {
		char[] s = str.toCharArray();
		int N = s.length;
		int[] cnt = new int[256];
		int diff = 0;
		int ans = 0;
		for (int l = 0,r=0; l < N; l++) {//固定左端点
			while (r < N && !(diff == k && cnt[s[r]] == 0)) {
				if (cnt[s[r]]==0) diff++;
				cnt[s[r++]]++;
				ans = Math.max(ans, r-l);
			}
			if (--cnt[s[l]]==0) diff--;
		}
		return ans;
	}

	public static int lengthOfLongestSubstringKDistinct2(String str, int k) {
		char[] s = str.toCharArray();
		int N = s.length;
		int[] cnt = new int[256];
		int diff = 0;
		int ans = 0;
		for (int l = 0,r=0; r < N; r++) {//固定右端点
			if (cnt[s[r]]==0) diff++;
			cnt[s[r]]++;
			while(diff>k){
				if (--cnt[s[l++]]==0) diff--;
			}
			ans = Math.max(ans, r-l+1);
		}
		return ans;
	}

	public static void main(String[] args) {
		System.out.println("test begin");
		out:
		for (int i = 0; i < 10000; i++) {
			StringBuilder sb=new StringBuilder();
			int len=g(1,20);
			for (int j = 0; j < len; j++) {
				sb.append((char)g('a','b'));
			}
			String str = sb.toString();
			int k = g(1,5);
			int[] res = new int[]{
					lengthOfLongestSubstringKDistinct1(str,k),
					lengthOfLongestSubstringKDistinct2(str, k),
			};
			for (int j = 0; j < res.length; j++) {
				if (res[j] != res[0]) {
					System.out.println(str+" "+k);
					for (int re : res) {
						System.out.println(re);
					}
					break out;
				}
			}
		}
		System.out.println("test finish");
	}

	public static int g(int l,int r){
		return l+(int)(Math.random()*(r-l+1));
	}

}
