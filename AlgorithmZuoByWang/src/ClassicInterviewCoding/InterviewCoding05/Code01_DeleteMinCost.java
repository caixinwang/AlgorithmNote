package ClassicInterviewCoding.InterviewCoding05;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class Code01_DeleteMinCost {

	// 题目：
	// 给定两个字符串s1和s2，问s2最少删除多少字符可以成为s1的子串？
	// 比如 s1 = "abcde"，s2 = "axbc"
	// 返回 1

	// 解法一，来自群里的解法：
	// 求出str2所有的子序列，然后按照长度排序，长度大的排在前面。
	// 然后考察哪个子序列字符串和s1的某个子串相等(KMP)，答案就出来了。
	// 分析：
	// 因为题目原本的样本数据中，有特别说明s2的长度很小。所以这么做也没有太大问题，也几乎不会超时。
	// 但是如果某一次考试给定的s2长度远大于s1，这么做就不合适了。
	public static int minCost1(String s1, String s2) {
		List<String> s2Subs = new ArrayList<>();
		process(s2.toCharArray(), 0, "", s2Subs);
		s2Subs.sort(new LenComp());
		for (String str : s2Subs) {
			if (s1.indexOf(str) != -1) { // indexOf底层和KMP算法代价几乎一样，也可以用KMP代替
				return s2.length() - str.length();
			}
		}
		return s2.length();
	}

	public static void process(char[] str2, int index, String path, List<String> list) {
		if (index == str2.length) {
			list.add(path);
			return;
		}
		process(str2, index + 1, path, list);
		process(str2, index + 1, path + str2[index], list);
	}

	public static class LenComp implements Comparator<String> {

		@Override
		public int compare(String o1, String o2) {
			return o2.length() - o1.length();
		}

	}

	// 解法二
	// 我的方法，看的时间比较短，希望同学们积极反馈
	// 生成所有s1的子串
	// 然后考察每个子串和s2的编辑距离(假设编辑距离只有删除动作且删除一个字符的代价为1)
	// 如果s1的长度较小，s2长度较大，这个方法比较合适
	public static int minCost2(String s1, String s2) {
		if (s1.length() == 0 || s2.length() == 0) {
			return s2.length();
		}
		int ans = Integer.MAX_VALUE;
		char[] str2 = s2.toCharArray();
		for (int start = 0; start < s1.length(); start++) {
			for (int end = start + 1; end <= s1.length(); end++) {
				// str1[start....end]
				// substring -> [ 0,1 )
				ans = Math.min(ans, distance(s1.substring(start, end).toCharArray(),str2));
			}
		}
		return ans == Integer.MAX_VALUE ? s2.length() : ans;
	}

	/**
	 * dp[i][j]：str1[0...i]与str2[0...j],后者要删除多少字符才能变成前者。原问题答案就是dp[N-1][M-1]
	 * dp[0][k],如果str2[0...k]中有str1[0]，那么dp[0][k]=k。否则dp[0][k]=MAX
	 * dp[0][0],str1[0]==str2[0]那么就等于0，否则就是MAX。在上面的过程中初始化了
	 * dp[k][0]，k==0时，上面填了。k>0时，都为-1，因为1个字符不可能删成2个字符
	 * dp[i][j]: 1.从dp[i-1][j-1]来，这种情况下如果str1[i]==str2[j]，那么dp[i][j]=dp[i-1][j-1]。
	 * 如果str1[i]!=str2[j]，那么str2就变不成str1了，dp[i][j]=-1
	 * 2.从dp[i-1][j]来，来不了一点，dp[i][j]=-1
	 * 3.从dp[i][j-1]来，把str2[j]删了就行，dp[i][j]=dp[i][j-1]+1
	 * 综上：dp[i][j]的来源只有两个，其它的来源都是MAX。并且要看看另外几个来源是不是MAX
	 * @return str2删除多少个字符才能变成str1.也就是编辑距离问题，并且只有删除。如果通过删除str2变不成str1那么就返回MAX
	 */
	public static int distance(char[] str1, char[] str2) {
		int N=str1.length,M=str2.length;
		int[][] dp=new int[N][M];
		boolean t=false;//str2[0...k]中是否有str1[0]
		for (int i = 0; i < dp[0].length; i++) {
			if (str2[i]==str1[0]) t=true;
			dp[0][i]=t?i:Integer.MAX_VALUE;
		}
		for (int i = 1; i < dp.length; i++) {
			dp[i][0]=Integer.MAX_VALUE;
		}
		for (int i = 1; i < N; i++) {
			for (int j = 1; j < M; j++) {
				dp[i][j]=Integer.MAX_VALUE;
				if (dp[i-1][j-1]!=Integer.MAX_VALUE&&str1[i]==str2[j]) dp[i][j]=dp[i-1][j-1];
				if (dp[i][j-1]!=Integer.MAX_VALUE) dp[i][j] = Math.min(dp[i][j], dp[i][j-1]+1);
			}
		}
		return dp[N-1][M-1];
	}

	// 解法二的优化。
	public static int minCost3(String s1, String s2) {//优化！减一阶的复杂度
		if (s1.length() == 0 || s2.length() == 0) {
			return s2.length();
		}
		int ans = Integer.MAX_VALUE;
		char[] str2 = s2.toCharArray();
		for (int start = 0; start < s1.length(); start++) {//列举str1所有以start开头的子串
			int[][] dp = getdp(s1.substring(start).toCharArray(), str2);//start~start,start+1,...,start+n都情况都在dp里
			int N=dp.length;
			int M=dp[0].length;
			for (int i = 0; i < N; i++) {//str1以start位置开头的所有子串的答案都在dp的最后一列中
				ans = Math.min(ans, dp[i][M-1]);
			}
		}
		return ans == Integer.MAX_VALUE ? s2.length() : ans;
	}

	public static int[][] getdp(char[] str1, char[] str2) {//和distance一样，只不过返回的是dp
		int N=str1.length,M=str2.length;
		int[][] dp=new int[N][M];
		boolean t=false;//str2[0...k]中是否有str1[0]
		for (int i = 0; i < dp[0].length; i++) {
			if (str2[i]==str1[0]) t=true;
			dp[0][i]=t?i:Integer.MAX_VALUE;
		}
		for (int i = 1; i < dp.length; i++) {
			dp[i][0]=Integer.MAX_VALUE;
		}
		for (int i = 1; i < N; i++) {
			for (int j = 1; j < M; j++) {
				dp[i][j]=Integer.MAX_VALUE;
				if (dp[i-1][j-1]!=Integer.MAX_VALUE&&str1[i]==str2[j]) dp[i][j]=dp[i-1][j-1];
				if (dp[i][j-1]!=Integer.MAX_VALUE) dp[i][j] = Math.min(dp[i][j], dp[i][j-1]+1);
			}
		}
		return dp;
	}

	// 来自学生的做法，时间复杂度O(N * M平方)
	// 复杂度和方法三一样，但是思路截然不同
	public static int minCost4(String s1, String s2) {
		char[] str1 = s1.toCharArray();
		char[] str2 = s2.toCharArray();
		HashMap<Character, ArrayList<Integer>> map1 = new HashMap<>();
		for (int i = 0; i < str1.length; i++) {
			ArrayList<Integer> list = map1.getOrDefault(str1[i], new ArrayList<Integer>());
			list.add(i);
			map1.put(str1[i], list);
		}
		int ans = 0;
		// 假设删除后的str2必以i位置开头
		// 那么查找i位置在str1上一共有几个，并对str1上的每个位置开始遍历
		// 再次遍历str2一次，看存在对应str1中i后续连续子串可容纳的最长长度
		for (int i = 0; i < str2.length; i++) {
			if (map1.containsKey(str2[i])) {
				ArrayList<Integer> keyList = map1.get(str2[i]);
				for (int j = 0; j < keyList.size(); j++) {
					int cur1 = keyList.get(j) + 1;
					int cur2 = i + 1;
					int count = 1;
					for (int k = cur2; k < str2.length && cur1 < str1.length; k++) {
						if (str2[k] == str1[cur1]) {
							cur1++;
							count++;
						}
					}
					ans = Math.max(ans, count);
				}
			}
		}
		return s2.length() - ans;
	}

	public static String generateRandomString(int l, int v) {
		int len = (int) (Math.random() * l);
		char[] str = new char[len];
		for (int i = 0; i < len; i++) {
			str[i] = (char) ('a' + (int) (Math.random() * v));
		}
		return String.valueOf(str);
	}

	public static void main(String[] args) {
		int str1Len = 20;
		int str2Len = 10;
		int v = 5;
		int testTime = 10000;
		boolean pass = true;
		System.out.println("test begin");
		for (int i = 0; i < testTime; i++) {
			String str1 = generateRandomString(str1Len, v);
			String str2 = generateRandomString(str2Len, v);
			int ans1 = minCost1(str1, str2);
			int ans2 = minCost2(str1, str2);
			int ans3 = minCost3(str1, str2);
			int ans4 = minCost4(str1, str2);
			if (ans1 != ans2 || ans3 != ans4 || ans1 != ans3) {
				pass = false;
				System.out.println(str1);
				System.out.println(str2);
				System.out.println(ans1);
				System.out.println(ans2);
				System.out.println(ans3);
				System.out.println(ans4);
				break;
			}
		}
		System.out.println("test pass : " + pass);
	}

}
