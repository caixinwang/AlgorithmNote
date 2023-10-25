package Leetcode.AllQuestions;

// 来自哈喽单车
// 本题是leetcode原题 : https://leetcode.cn/problems/stone-game-iv/
public class Leetcode_1510_StoneGameIV {

	public static boolean winnerSquareGame1(int n) {//函数返回先手是否会赢
		if (n==0) return false;
		boolean ans=false;
		for (int i=1;i*i<=n;i++){
			ans|=!winnerSquareGame1(n-i*i);//后手输了，先手就赢了
		}
		return ans;
	}

	public static boolean winnerSquareGame2(int N) {//函数返回先手是否会赢
		boolean[] dp=new boolean[N+1];
		for (int n = 1; n < dp.length; n++) {//dp[i]的这个i相当于递归的n
			for (int k=1;k*k<=n;k++){
				dp[n]|=!dp[n-k*k];
			}
		}
		return dp[N];
	}
	
	public static void main(String[] args) {
		System.out.println(winnerSquareGame1(1));
		System.out.println(winnerSquareGame1(2));
		System.out.println(winnerSquareGame1(3));
		System.out.println(winnerSquareGame1(4));
		System.out.println(winnerSquareGame2(1));
		System.out.println(winnerSquareGame2(2));
		System.out.println(winnerSquareGame2(3));
		System.out.println(winnerSquareGame2(4));
	}

}
