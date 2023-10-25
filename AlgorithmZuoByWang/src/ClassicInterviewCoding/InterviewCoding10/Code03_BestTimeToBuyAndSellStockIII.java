package ClassicInterviewCoding.InterviewCoding10;

public class Code03_BestTimeToBuyAndSellStockIII {

    //最大的两个上坡不是答案！
    public static int maxProfit(int[] prices) {
        return maxProfitKDays2(prices, 2);
    }

    public static int maxProfitKDays(int[] arr, int k) {
        if (k > arr.length / 2) return f(arr);
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
	 * 原题更简单，我们实现的更加全能
	 * 优化思路，一个格子依赖左边的往上一整列，所以肯定依赖上面的格子，也就是说
	 * 一个格子可以利用它上面格子传下来的信息，所以我们把填表的顺序改为从左往右再从上往下。
	 * 由于第一行格子已经被我们初始化了，所以max的初始值不能赋值为系统最小，应该初始化为第一行的值。
	 * 能够这么快发现优化的思路是因为我们在没有优化的版本中，把max函数中的公共部分提出来了。
	 */
    public static int maxProfitKDays2(int[] arr, int k) {
        if (k > arr.length / 2) return f(arr);
        int[][] dp = new int[arr.length][k + 1];
        for (int j = 1; j <= k; j++) {
			int max = dp[0][j-1]-arr[0];//不能初始化为系统最小
            for (int i = 1; i < arr.length; i++) {
				dp[i][j] = dp[i - 1][j];
				max = Math.max(max,dp[i][j-1]-arr[i]);
				dp[i][j] = Math.max(max + arr[i], dp[i][j]);
            }
        }
        return dp[arr.length - 1][k];
    }

    public static int f(int[] prices) {//无限买卖股票问题
        int ans = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]) {
                ans += prices[i] - prices[i - 1];
            }
        }
        return ans;
    }

    //根据dp的玩法，我们直接去完，反正就2次交易
    public static int maxProfit2(int[] prices) {
        int ans=0;
        int info=-prices[0];//0~sell-1上做一次交易的最大值再减去prices[?]的最大
        int min=prices[0];//抓出0~sell-1上的最小值
        int oneTransaction=0;//第一次交易的最大收益
        for (int sell = 1; sell <prices.length; sell++) {//枚举第二次卖出的时间点
            ans = Math.max(ans, info+prices[sell]);
            min = Math.min(min, prices[sell]);
            oneTransaction = Math.max(oneTransaction, prices[sell]-min);
            info = Math.max(info, oneTransaction-prices[sell]);
        }
        return ans;
    }

}
