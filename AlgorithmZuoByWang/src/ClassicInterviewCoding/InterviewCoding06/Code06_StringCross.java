package ClassicInterviewCoding.InterviewCoding06;

public class Code06_StringCross {

	/**
	 * dp[i][j]代表长度为i的s1前缀和长度为j为s2前缀，长度为i+j前缀的s3是不是他们的交错组成
	 * dp[0][0]=true
	 * 第一行的第一列，必须s1或者s2要和s3一路相等才行
	 * dp[i][j]只和上面的和左边的有关，和左上角的无关，因为左上角被上面或者左边的包含了
	 * @param s1 s1
	 * @param s2 s2
	 * @param aim 问aim是不是s1和s2的交错组成
	 * @return
	 */
	public static boolean isCross1(String s1, String s2, String aim) {
		if (s1==null||s2==null||aim==null) return false;
		if (s1.length()+s2.length()!=aim.length()) return false;
		char[] str1 = s1.toCharArray();
		char[] str2 = s2.toCharArray();
		char[] str3 = aim.toCharArray();
		int N=str1.length,M=str2.length;
		boolean[][] dp=new boolean[N+1][M+1];//考虑空串的情况
		dp[0][0]=true;
		for (int i = 1; i <= N ; i++) {//长度
			if (str1[i-1]==str3[i-1]){
				dp[i][0]=true;
			}else {//一旦有一个位置不相等，后面都不是交错组成
				break;
			}
		}
		for (int i = 1; i <= M ; i++) {//长度
			dp[0][i]=dp[0][i-1]&&str2[i-1]==str3[i-1];//另外一种初始化方式
		}
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= M; j++) {
				dp[i][j]= dp[i-1][j]&&str1[i-1]==str3[i+j-1]  ||  dp[i][j-1]&&str2[j-1]==str3[i+j-1];
			}
		}
		return dp[N][M];
	}

	public static boolean isCross2(String s1, String s2, String aim) {//空间压缩
		if (s1==null||s2==null||aim==null) return false;
		if (s1.length()+s2.length()!=aim.length()) return false;
		boolean s1Longer= s1.length()>=s2.length();
		char[] str1 = s1.toCharArray();
		char[] str2 = s2.toCharArray();
		char[] str3 = aim.toCharArray();
		if (!s1Longer){//让长的竖着摆（做row），短的横着摆（做col）--长的做str1，短的做str2。我们我们竖着滚下去
			char[] tmp=str1;
			str1=str2;
			str2=tmp;
		}
		int N=str1.length,M= str2.length;
		boolean[] dp=new boolean[M+1];//str2---M---比较短
		dp[0]=true;
		for (int i = 1; i <= M ; i++) {//这种初始化方式也行
			dp[i]=dp[i-1]&&str2[i-1]==str3[i-1];
		}
		for (int i = 1; i <= N ; i++) {
			dp[0]=dp[0]&&str1[i-1]==str3[i-1];
			for (int j = 1; j <= M ; j++) {
				dp[j]=dp[j-1]&&str2[j-1]==str3[i+j-1] || dp[j]&&str1[i-1]==str3[i+j-1];
			}
		}
		return dp[M];
	}

	public static void main(String[] args) {
		String str1 = "12345678";
		String str2 = "abcdefg";
		String aim = "1a23bcd45e67f8g";
		System.out.println(isCross1(str1, str2, aim));
		System.out.println(isCross2(str1, str2, aim));

	}

}
