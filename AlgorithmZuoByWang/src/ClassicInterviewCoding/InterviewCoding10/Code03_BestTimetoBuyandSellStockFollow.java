package ClassicInterviewCoding.InterviewCoding10;

public class Code03_BestTimetoBuyandSellStockFollow {

	public static int maxProfitKDays(int[] arr, int k) {
		if (k > arr.length / 2) return allTrans(arr);
		int[][] dp = new int[arr.length][k + 1];
		for (int i = 1; i < arr.length; i++) {
			for (int j = 1; j <= k; j++) {
				dp[i][j] = dp[i - 1][j];//初值为i位置不参与交易
				int max = Integer.MIN_VALUE;//i位置参与交易的最大获益
				for (int buy = i; buy >= 0; buy--) {//枚举买入点
					max = Math.max(max, dp[buy][j - 1] - arr[buy]);
				}
				dp[i][j] = Math.max(max + arr[i], dp[i][j]);
			}
		}
		return dp[arr.length - 1][k];
	}

	/**
	 * dp[i][j]代表0~i的时间点上交易不超过j次的最大收益,对一个普遍的点(i,j)进行讨论，分为i参与交易和不参与交易两种
	 * dp[i] [j] = max{max{dp[i-k][j-1]-arr[i-k]}+arr[i] , dp[i-1][j]}
	 * max{dp[i-k][j-1]-arr[i-k]}+arr[i]为参与交易的最大值，dp[i-1][j]为不参与交易的最大值，两个中再取大的
	 * 参与交易的最大值有枚举行为max{dp[i-k][j-1]-arr[i-k]}，
	 * 我们用一个变量抓住最大值max{dp[i-k][j-1]-arr[i-k]}，需要从上往下更新
	 *
	 * --为什么枚举了在i位置买入你就只能从0~i范围内继续求最大了呢？因为股票买卖是不交叉的。
	 *
	 * 优化思路，一个格子依赖左边的往上一整列，所以肯定依赖上面的格子，也就是说
	 * 一个格子可以利用它上面格子传下来的信息，所以我们把填表的顺序改为从左往右再从上往下。
	 * 由于第一行格子已经被我们初始化了，所以max的初始值不能赋值为系统最小，应该初始化为第一行的值。
	 * 能够这么快发现优化的思路是因为我们在没有优化的版本中，把max函数中的公共部分提出来了。
	 *
	 * @param K 最多交易k次
	 * @param prices 代表股票价格变化的数组
	 * @return 如果最多交易k次，你最多能赚多少
	 */
	public static int dp(int K, int[] prices) {
		if (prices == null || prices.length == 0) {
			return 0;
		}
		int N = prices.length;
		if (K >= N / 2) {
			return allTrans(prices);
		}
		int[][] dp = new int[N][K + 1];
		for (int j = 1; j <= K; j++) {//从左往右，从上往下
			int t=dp[0][j-1]-prices[0];//(i,j)=>max{dp[i-k][j-1]-arr[i-k]},初始化位置是(0,j),令k=0代进去
			for (int i = 1; i < N; i++) {
				dp[i][j] = dp[i-1][j];
				t=Math.max(t,dp[i][j-1]-prices[i]);
				dp[i][j] = Math.max(dp[i][j], t+prices[i]);
			}
		}
		return dp[N-1][K];
	}

	public static int maxProfit(int K, int[] prices) {
		if (prices == null || prices.length == 0) {
			return 0;
		}
		int N = prices.length;
		if (K >= N / 2) {
			return allTrans(prices);
		}
		// dp一维表，做了空间压缩
		int[] dp = new int[N];
		int ans = 0;
		for (int tran = 1; tran <= K; tran++) {
			int pre = dp[0];
			int best = pre - prices[0];
			for (int index = 1; index < N; index++) {
				pre = dp[index];
				dp[index] = Math.max(dp[index - 1], prices[index] + best);
				best = Math.max(best, pre - prices[index]);
				ans = Math.max(dp[index], ans);
			}
		}
		return ans;
	}

	public static int allTrans(int[] prices) {//Code02的情况，无限次数交易，作为过滤器的使用
		int ans = 0;
		for (int i = 1; i < prices.length; i++) {
			ans += Math.max(prices[i] - prices[i - 1], 0);
		}
		return ans;
	}

	public static void main(String[] args) {
		int[] test = { 4, 1, 231, 21, 12, 312, 312, 3, 5, 2, 423, 43, 146 };
		int K = 3;
		System.out.println(dp(K, test));
		System.out.println(maxProfit(K, test));

	}

}
