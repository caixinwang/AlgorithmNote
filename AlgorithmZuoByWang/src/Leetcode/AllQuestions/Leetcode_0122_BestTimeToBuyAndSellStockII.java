package Leetcode.AllQuestions;

import TestUtils.ArrayUtil;

import java.util.Arrays;

public class Leetcode_0122_BestTimeToBuyAndSellStockII {

    public static int maxProfit(int[] prices) {//贪心：压榨每一段上坡
        int ans = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]) {
                ans += prices[i] - prices[i - 1];
            }
        }
        return ans;
    }

    static int[][] dp;

    /**
     * 在一个位置，可以做决定，买还是卖？
     * 1.持有股票，那么在i位置只能不做或者卖出并且你能否买卖取决你当前有没有持有股票.
     * 2.未持有，在i位置只能不做或者买入
     * Max{case1,case2}
     */
    public static int maxProfit2(int[] prices) {
        dp = new int[prices.length + 1][2];
        for (int[] arr : dp) Arrays.fill(arr, -1);
        return f(prices, 0, 0);
    }

    private static int f(int[] p, int i, int hold) {
        if (dp[i][hold] != -1) return dp[i][hold];
        if (i == p.length) {
            dp[i][hold] = 0;
        } else if ((hold & 1) != 0) {
            dp[i][hold] = Math.max(f(p, i + 1, hold), p[i] + f(p, i + 1, hold ^ 1));
        } else {
            dp[i][hold] = Math.max(f(p, i + 1, hold), -p[i] + f(p, i + 1, hold ^ 1));
        }
        return dp[i][hold];
    }

    public static int maxProfit3(int[] p) {
        int N=p.length;
        int[][] dp = new int[N+1][2];
        for (int i = N-1; i >= 0; i--) {
            dp[i][1] = Math.max(dp[i + 1][1], p[i] + dp[i + 1][0]);
            dp[i][0] = Math.max(dp[i + 1][0], -p[i] + dp[i + 1][1]);
        }
        return dp[0][0];
    }

    public static int maxProfit4(int[] p) {
        int f0 = 0, f1 = 0;
        for (int i = p.length - 1; i >= 0; i--) {
            int nf = Math.max(f1, p[i] + f0);
            f0 = Math.max(f0, -p[i] + f1);
            f1 = nf;
        }
        return f0;
    }

    static ArrayUtil au = new ArrayUtil();

    public static void main(String[] args) {
        int[] arr = au.generateRandomArr(10, 1, 100);
        System.out.println(maxProfit(arr));
        System.out.println(maxProfit2(arr));
        System.out.println(maxProfit3(arr));
    }

}
