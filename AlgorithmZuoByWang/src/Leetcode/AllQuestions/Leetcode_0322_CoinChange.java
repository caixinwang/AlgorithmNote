package Leetcode.AllQuestions;

import java.util.Arrays;

/**
 * 正确的dp[i][j]的含义就是使用0~i种硬币，严格凑出amount
 *
 * 这种下面做法不行，如果不是需要严格凑出来的话，这个方法就会很好，参考打怪兽问题。
 * dp[i][j]代表，严格使用j个硬币，可以达到的最大金额。
 * dp[i][j]=max{dp[i-1][j],coins[i]+dp[i-1][j-1],2*coins[i]+dp[i-1][j-2]...,m*coins[i]+dp[i-1][0]}
 *         =max{dp[i-1][j],coins[i]+dp[i][j-1]}
 * 发现一个位置只依赖自己上面的以及左边的值，所以可以空间压缩，横着滚动。这题直接搞空间压缩，因为最多使用多少硬币不好确定，
 * 可以抓住最小值，算出最多需要几个硬币，强行建表。
 * 滚动到第一次出现amount位置，如果一直滚动到每个位置的值都大于amount了，就直接返回-1，凑不出来
 */
public class Leetcode_0322_CoinChange {
	public int coinChange(int[] coins, int amount) {
		int[][] dp=new int[coins.length][amount+1];//dp[i][j]代表0~i的硬币随便选，刚好凑出j元的最少数量
		//第一列，凑成0元，都是0
		//第一行，只能使用coins[0]，可以整除的地方放整除以后的结果，其它都是-1，代表没有方案
		for (int i=0;i<dp.length;i++){
			for (int j=0;j<dp[0].length;j++){
				dp[i][j]=Integer.MAX_VALUE;
			}
		}
		for (int i=0;i<dp[0].length;i++){//第一行
			dp[0][i]=i%coins[0]==0?i/coins[0]:Integer.MAX_VALUE;
		}
		for (int i=0;i<dp.length;i++){//第一列
			dp[i][0]=0;
		}
		for (int i=1;i<dp.length;i++){
			for (int j=1;j<dp[0].length;j++){
				dp[i][j]=dp[i-1][j];//i硬币不参与构成
				//i参与其实就是枚举使用1枚、2枚...,直接斜率优化
				if (j-coins[i]>=0&&dp[i][j-coins[i]]!=Integer.MAX_VALUE) {
					dp[i][j] = Math.min(dp[i][j], 1 + dp[i][j - coins[i]]);
				}
			}
		}
		return dp[coins.length-1][amount]==Integer.MAX_VALUE?-1:dp[coins.length-1][amount];
	}

	final int MIN=1<<31,MAX=MIN-1;
	public int coinChange2(int[] coins, int amount) {
		int n=coins.length;

		int[] f=new int[amount+1];
		Arrays.fill(f,MAX>>1);
		f[0]=0;
		for(int i=n-1;i>=0;i--){
			for(int j=0;j<=amount;j++){
				if(j<coins[i]) f[j]=f[j];
				else f[j]=Math.min(f[j],1+f[j-coins[i]]);
			}
		}
		int ans=f[amount];
		return ans>=MAX>>1?-1:ans;

		// int[][] f=new int[n+1][amount+1];//改动态规划
		// for(var a:f)Arrays.fill(a,MAX>>1);
		// f[n][0]=0;
		// for(int i=n-1;i>=0;i--){
		//     for(int j=0;j<=amount;j++){//依赖自己上面和本行左边，所以要从左往右填，因为自己左边要填好
		//         if(j<coins[i]) f[i][j]=f[i+1][j];
		//         else f[i][j]=Math.min(f[i+1][j],1+f[i][j-coins[i]]);
		//     }
		// }
		// int ans=f[0][amount];
		// return ans>=MAX>>1?-1:ans;


		// int ans=f(coins,0,amount);//改动态规划
		// return ans>=MAX>>1?-1:ans;
	}

	public int f(int[] coins,int index,int rest){//因为可以无限选，所以选完之后可以继续呆在原地
		if(index==coins.length) return rest==0?0:MAX>>1;//MAX>>1是为了加完不溢出
		if(rest< coins[index]) return f(coins,index+1,rest);//只能不选
		return Math.min(f(coins,index+1,rest),f(coins,index,rest-coins[index])+1);//转移到原地
	}



}
