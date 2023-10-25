package ClassicInterviewCoding.InterviewCoding08;

public class Code06_PalindromeMinAdd {

	public static String getPalindrome1(String str) {
		if (str == null || str.length() < 2) {
			return str;
		}
		char[] chas = str.toCharArray();
		int[][] dp = getDP(chas);
		char[] res = new char[chas.length + dp[0][chas.length - 1]];
		int i = 0;
		int j = chas.length - 1;
		int resl = 0;
		int resr = res.length - 1;
		while (i <= j) {
			if (chas[i] == chas[j]) {
				res[resl++] = chas[i++];
				res[resr--] = chas[j--];
			} else if (dp[i][j - 1] < dp[i + 1][j]) {
				res[resl++] = chas[j];
				res[resr--] = chas[j--];
			} else {
				res[resl++] = chas[i];
				res[resr--] = chas[i++];
			}
		}
		return String.valueOf(res);
	}

	public static int[][] getDP(char[] str) {
		if (str==null||str.length==0) return null;
		int N=str.length;
		int[][]dp=new int[N][N];
		for (int i = 0; i < str.length; i++) {//第一条对角线
			dp[i][i]=1;
		}
		for (int i = 0; i+1 < str.length; i++) {//第二条对角线
			dp[i][i+1]=str[i]==str[i+1]?1:0;
		}
		for (int j = 2; j < N; j++) {//从第二条对角线开始填写
			for (int i = 0; i+j < N; i++) {//对角线元素(i,i+j)
				dp[i][i+j]=Math.min(dp[i+1][i+j],dp[i][i+j-1])+1;
				if (str[i+1]==str[i+j-1]) dp[i][i + j] = Math.min(dp[i][i + j],dp[i+1][i+j-1]);
			}
		}
		return dp;
	}

	public static void main(String[] args) {
		String str = "AB1CD2EFG3H43IJK2L1MN";
		System.out.println(getPalindrome1(str));
	}

}
