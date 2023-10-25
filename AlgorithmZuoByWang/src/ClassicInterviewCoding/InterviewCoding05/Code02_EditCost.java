package ClassicInterviewCoding.InterviewCoding05;

public class Code02_EditCost {
	/**
	 * dp[i][j]：s1[0..i-1]变成s2[0...j-1]的最小编辑代价。
	 * dp[0][0]=0.都是空串，明显不需要处理
	 * dp[0][k]=dp[0][k-1]+ic
	 * dp[k][0]=dp[k-1][0]+dc
	 * dp[i][j]=min{ str1[i]==str2[j]?dp[i-1][j-1]:dp[i-1][j-1]+min{rc,dc+ic} , dp[i-1][j]+dc , dp[i][j-1]+ic}
	 * 怎么看的：例如dp[i-1][j]，当做已经把str1[0..i-1]变成了str2[0...j]，那么现在你要str1[0...i]的代价，比前面的多了个str1[i]，
	 * 把这个str1[i]删掉即可。
	 * @param s1 s1要变成s2
	 * @param s2 ·
	 * @param ic 插入代价
	 * @param dc 删除代价
	 * @param rc 替换代价
	 * @return 返回s1变成s2的最小编辑代价
	 */
	public static int minCost1(String s1, String s2, int ic, int dc, int rc) {
		if (s1 == null || s2 == null) return 0;
		char[] str1 = s1.toCharArray();
		char[] str2 = s2.toCharArray();
		int N=s1.length(),M=s2.length();
		int[][] dp=new int[N+1][M+1];//由于有空串的存在，所以要多加一行一列
		dp[0][0]=0;
		for (int k = 1; k <= N; k++) dp[k][0]=dp[k-1][0]+dc;
		for (int k = 1; k <= M; k++) dp[0][k]=dp[0][k-1]+ic;
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= M; j++) {
				dp[i][j]=Math.min(str1[i-1]==str2[j-1]?dp[i-1][j-1]:dp[i-1][j-1]+Math.min(rc,ic+dc),Math.min( dp[i-1][j]+dc , dp[i][j-1]+ic));
			}
		}
		return dp[N][M];
	}

	public static int minCost2(String str1, String str2, int ic, int dc, int rc) {
		if (str1 == null || str2 == null) {
			return 0;
		}
		char[] chs1 = str1.toCharArray();
		char[] chs2 = str2.toCharArray();
		char[] longs = chs1.length >= chs2.length ? chs1 : chs2;
		char[] shorts = chs1.length < chs2.length ? chs1 : chs2;
		if (chs1.length < chs2.length) {
			int tmp = ic;
			ic = dc;
			dc = tmp;
		}
		int[] dp = new int[shorts.length + 1];
		for (int i = 1; i <= shorts.length; i++) {
			dp[i] = ic * i;
		}
		for (int i = 1; i <= longs.length; i++) {
			int pre = dp[0];
			dp[0] = dc * i;
			for (int j = 1; j <= shorts.length; j++) {
				int tmp = dp[j];
				if (longs[i - 1] == shorts[j - 1]) {
					dp[j] = pre;
				} else {
					dp[j] = pre + rc;
				}
				dp[j] = Math.min(dp[j], dp[j - 1] + ic);
				dp[j] = Math.min(dp[j], tmp + dc);
				pre = tmp;
			}
		}
		return dp[shorts.length];
	}

	public static void print(int[][] arr){
		for (int i = 0; i < arr.length; i++) {
			for (int i1 = 0; i1 < arr[0].length; i1++) {
				System.out.printf("%d\t",arr[i][i1]);
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		String str1 = "ab12cd3";
		String str2 = "abcdf";

		System.out.println(minCost1(str1, str2, 5, 3, 10));
		System.out.println(minCost2(str1, str2, 5, 3, 10));

		str1 = "abcdf";
		str2 = "ab12cd3";
		System.out.println(minCost1(str1, str2, 3, 2, 6));
		System.out.println(minCost2(str1, str2, 3, 2, 6));

		str1 = "";
		str2 = "ab12cd3";
		System.out.println(minCost1(str1, str2, 1, 7, 5));
		System.out.println(minCost2(str1, str2, 1, 7, 5));

		str1 = "abcdf";
		str2 = "";
		System.out.println(minCost1(str1, str2, 2, 9, 8));
		System.out.println(minCost2(str1, str2, 2, 9, 8));

	}

}
