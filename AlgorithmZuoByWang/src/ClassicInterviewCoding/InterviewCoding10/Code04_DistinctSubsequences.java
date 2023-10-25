package ClassicInterviewCoding.InterviewCoding10;

import TestUtils.StringUtil;

public class Code04_DistinctSubsequences {

	public static int numDistinct1(String S, String T) {
		char[] s = S.toCharArray();
		char[] t = T.toCharArray();
		return process(s, t, s.length, t.length);
	}

	public static int process(char[] s, char[] t, int i, int j) {
		if (j == 0) {
			return 1;
		}
		if (i == 0) {
			return 0;
		}
		int res = process(s, t, i - 1, j);
		if (s[i - 1] == t[j - 1]) {
			res += process(s, t, i - 1, j - 1);
		}
		return res;
	}

	// S[...i]的所有子序列中，包含多少个字面值等于T[...j]这个字符串的子序列
	// 记为dp[i][j]
	// 可能性1）S[...i]的所有子序列中，都不以s[i]结尾，则dp[i][j]肯定包含dp[i-1][j]
	// 可能性2）S[...i]的所有子序列中，都必须以s[i]结尾，
	// 这要求S[i] == T[j]，则dp[i][j]包含dp[i-1][j-1]
	public static int numDistinct2(String S, String T) {//dp多加了一行一列，考虑空串
		if (S==null||T==null) return -1;
		char[] s = S.toCharArray();
		char[] t = T.toCharArray();
		int[][] dp = new int[s.length + 1][t.length + 1];
		for (int j = 0; j <= t.length; j++) {
			dp[0][j] = 0;
		}
		for (int i = 0; i <= s.length; i++) {
			dp[i][0] = 1;
		}
		for (int i = 1; i <= s.length; i++) {
			for (int j = 1; j <= t.length; j++) {
				dp[i][j] = dp[i - 1][j] + (s[i - 1] == t[j - 1] ? dp[i - 1][j - 1] : 0);
			}
		}
		return dp[s.length][t.length];
	}

	public static int numDistinct3(String S, String T) {//没有考虑空串
		if (S==null||T==null) return -1;
		if (S.length()==0||T.length()==0){//手动考虑空串
			if (T.length()==0) return 1;//T为空串，把S删完即可
			else return 0;//T不为空串，但是S为空串就没救了
		}
		char[] s = S.toCharArray();
		char[] t = T.toCharArray();
		int N=s.length,M=t.length;
		int[][] dp=new int[N][M];//长度
		dp[0][0]=s[0]==t[0]?1:0;
		for (int i=1;i<N;i++){
			dp[i][0]=dp[i-1][0]+(s[i]==t[0]?1:0);
		}
		for (int i=1;i<N;i++){
			for (int j = 1; j < M ; j++) {
				dp[i][j]=dp[i-1][j]+(s[i]==t[j]?dp[i-1][j-1]:0);
			}
		}
		return dp[N-1][M-1];
	}

	public static int numDistinct4(String S, String T) {//空间压缩
		char[] s = S.toCharArray();
		char[] t = T.toCharArray();
		int[] dp = new int[t.length + 1];
		dp[0] = 1;
		for (int j = 1; j <= t.length; j++) {
			dp[j] = 0;
		}
		for (int i = 1; i <= s.length; i++) {
			for (int j = t.length; j >= 1; j--) {
				dp[j] += s[i - 1] == t[j - 1] ? dp[j - 1] : 0;
			}
		}
		return dp[t.length];
	}

	public static void testForString() {//参数为String
		StringUtil stringUtil = new StringUtil();
		int times = 1000;//测试次数
		boolean isok = true;
		int maxSize = 20;//String长度在[0~maxSize]随机

//        int parameter1=0;//测试函数的参数
//        int maxParameter1=100;//参数1在[0,maxParameter1]随机

		String t1=null;
        String t2=null;

		int res1 = 0, res2 = 0;
		for (int i = 0; i < times; i++) {
//            parameter1=arrayUtil.ran(maxParameter1);
			t1 = stringUtil.generateRandom_a_z_String(stringUtil.ran(maxSize));
        t2= stringUtil.generateRandom_a_z_String(stringUtil.ran(maxSize));

//        t1= stringUtil.generateRandom_all_String(stringUtil.ran(maxSize));
//        t2= stringUtil.generateRandom_all_String(stringUtil.ran(maxSize));

			res1 = numDistinct2(t1,t2);
			res2 = numDistinct3(t1,t2);

//            res1 = m1(t1,t2);
//            res2 = m2(t1,t2);

			if (res1 != res2) {
				isok = false;
				break;
			}
		}
		System.out.println("t1:"+t1);
        System.out.println("t2:"+t2);
//        System.out.println("parameter1:"+parameter1);//打印参数
		System.out.println(res1);//针对返回值的操作
		System.out.println(res2);//针对返回值的操作
		System.out.println(isok ? "success" : "fail");
	}

	public static void main(String[] args) {
		testForString();
	}
}
