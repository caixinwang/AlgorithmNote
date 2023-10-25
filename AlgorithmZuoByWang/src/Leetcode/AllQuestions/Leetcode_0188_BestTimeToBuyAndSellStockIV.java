package Leetcode.AllQuestions;

import java.util.Arrays;
import java.util.Scanner;

public class Leetcode_0188_BestTimeToBuyAndSellStockIV {

    public int maxProfit(int k, int[] arr) {
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
     * 优化思路，一个格子依赖左边的往上一整列，所以肯定依赖上面的格子，也就是说
     * 一个格子可以利用它上面格子传下来的信息，所以我们把填表的顺序改为从左往右再从上往下。
     * 由于第一行格子已经被我们初始化了，所以max的初始值不能赋值为系统最小，应该初始化为第一行的值。
     * 能够这么快发现优化的思路是因为我们在没有优化的版本中，把max函数中的公共部分提出来了。
     */
    public static int maxProfit2(int k, int[] arr) {
        if (k > arr.length / 2) return f(arr);
        int[][] dp = new int[arr.length][k + 1];
        for (int j = 1; j <= k; j++) {
            int max = dp[0][j - 1] - arr[0];//不能初始化为系统最小
            for (int i = 1; i < arr.length; i++) {
                dp[i][j] = dp[i - 1][j];
                max = Math.max(max, dp[i][j - 1] - arr[i]);
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

    static int[][][] dp;

    public static int maxProfit3(int k, int[] prices) {
        dp = new int[prices.length + 1][k + 1][2];
        for (int[][] arr : dp) for (int[] arr2 : arr) Arrays.fill(arr2, -1);
        return f(prices, 0, k, 0);
    }

    private static int f(int[] p, int i, int rest, int hold) {
        if (dp[i][rest][hold] != -1) return dp[i][rest][hold];
        if (i == p.length) {
            dp[i][rest][hold] = 0;
        } else if ((hold & 1) == 1) {
            dp[i][rest][hold] = Math.max(f(p, i + 1, rest, hold), rest > 0 ? p[i] + f(p, i + 1, rest - 1, hold ^ 1) : 0);
        } else {
            dp[i][rest][hold] = Math.max(f(p, i + 1, rest, hold), -p[i] + f(p, i + 1, rest, hold ^ 1));
        }
        return dp[i][rest][hold];
    }

    //f[i][j][k]:i~n天还有j次交易机会并且第i天的时候持有股票的状态为k
    //第i填还是分状态讨论，如果持有股票，那么可以卖出或者继续持有
    // 如果没有股票，那么可以选择买入股票或者保持没有股票进入下一天
    //注意：次数j的减少时机在买入和卖出中二选一即可
    public int maxProfit4(int k, int[] prices) {
        int n=prices.length;
        var f=new int[n+1][k+1][2];
        for(int i=n-1;i>=0;i--){
            for(int j=0;j<=k;j++){
                f[i][j][0]=Math.max(f[i+1][j][0],j>0?-prices[i]+f[i+1][j-1][1]:0);
                f[i][j][1]=Math.max(f[i+1][j][1],prices[i]+f[i+1][j][0]);
            }
        }
        return f[0][k][0];
    }

    //上面的转义只从下一行转义来，所以可以进行空间压缩
    //f[i][j][1]需要f[i+1][j][0]，所以f[i][j][0]更新需要在f[i][j][1]之后
    //f[i][j][0]需要f[i+1][j-1][1]，也就是在左边的j-1，所以我们需要倒序
    public int maxProfit5(int k, int[] prices) {
        int n=prices.length;
        var f=new int[k+1][2];//f[i][j][k]:i~n天还有j次交易机会并且第i天的时候持有股票的状态为k
        for(int i=n-1;i>=0;i--){
            for(int j=k;j>=0;j--){
                f[j][1]=Math.max(f[j][1],prices[i]+f[j][0]);
                f[j][0]=Math.max(f[j][0],j>0?-prices[i]+f[j-1][1]:0);
            }
        }
        return f[k][0];
    }
}
