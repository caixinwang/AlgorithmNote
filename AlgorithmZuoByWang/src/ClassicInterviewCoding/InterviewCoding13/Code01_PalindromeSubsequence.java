package ClassicInterviewCoding.InterviewCoding13;

public class Code01_PalindromeSubsequence {

	public static int maxLen1(String str) {
		if (str == null || str.length() == 0) {
			return 0;
		}
		char[] str1 = str.toCharArray();
		char[] str2 = reverse(str1);
		return lcse(str1, str2);
	}

	public static char[] reverse(char[] str) {
		char[] reverse = new char[str.length];
		for (int i = 0; i < reverse.length; i++) {
			reverse[i] = str[str.length - 1 - i];
		}
		return reverse;
	}

	public static int lcse(char[] str1, char[] str2) {
		int[][] dp = new int[str1.length][str2.length];
		dp[0][0] = str1[0] == str2[0] ? 1 : 0;
		for (int i = 1; i < str1.length; i++) {
			dp[i][0] = Math.max(dp[i - 1][0], str1[i] == str2[0] ? 1 : 0);
		}
		for (int j = 1; j < str2.length; j++) {
			dp[0][j] = Math.max(dp[0][j - 1], str1[0] == str2[j] ? 1 : 0);
		}
		for (int i = 1; i < str1.length; i++) {
			for (int j = 1; j < str2.length; j++) {
				dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
				if (str1[i] == str2[j]) {
					dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - 1] + 1);
				}
			}
		}
		return dp[str1.length - 1][str2.length - 1];
	}

	//范围上的尝试
	public static int maxLen2(String s) {
		char[] str = s.toCharArray();
		int N=str.length;
		int[][] dp=new int[N][N];//[L,R]
		for (int i = 0; i < N; i++) {
			dp[i][i]=1;
		}
		for (int i = 0; 1+i < N; i++) {
			dp[i][1+i]=str[i]==str[1+i]?2:1;
		}
		for (int j = 2; j < N; j++) {//填对角线
			for (int i = 0; i+j <N ; i++) {//dp[i][i+j]
				int l=i,r=i+j;
				dp[l][r]=str[l]==str[r]?dp[l+1][r-1]+2:dp[l+1][r-1];
				dp[l][r] = Math.max(dp[l][r],Math.max(dp[l+1][r],dp[l][r-1]));
			}
		}
//		for (int l=N-3;l>=0;l--){//从下往上，从左往右
//			for (int r =l+2; r < N; r++) {
//				dp[l][r]=str[l]==str[r]?dp[l+1][r-1]+2:dp[l+1][r-1];
//				dp[l][r] = Math.max(dp[l][r],Math.max(dp[l+1][r],dp[l][r-1]));
//			}
//		}
		return dp[0][N-1];
	}

	public static void main(String[] args) {
		String test = "A1BC2D33FG2H1I";
		System.out.println(maxLen1(test));
		System.out.println(maxLen2(test));
	}

}
