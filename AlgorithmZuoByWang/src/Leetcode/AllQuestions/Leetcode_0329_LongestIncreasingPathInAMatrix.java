package Leetcode.AllQuestions;

/**
 * 直接傻缓存！
 */
public class Leetcode_0329_LongestIncreasingPathInAMatrix {
	public int longestIncreasingPath(int[][] matrix) {
		int[][] dp=new int[matrix.length][matrix[0].length];
		int ans=0;
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				ans = Math.max(ans, f(matrix,i,j,dp));
			}
		}
		return ans;
	}

	/**
	 * 可以使用登上去之前判断合法性，这样由于题目本身有大于才能才的需求，所以必然不可能走回头路
	 * 这题如果登上去才验证合法性就麻烦了，我们还需要保存之前那个格子的值是多少，这样倒不如登上去之前验证
	 * @param matrix 从(i,j)位置出发能走出的最长的递增序列长度
	 * @param i 因为登上之前判断合法性了，所以(i,j)位置已经算我们登上去了
	 * @param j -
	 * @return -
	 */
	public int f(int[][] matrix,int i,int j,int[][] dp){
		if (dp[i][j]!=0) return dp[i][j];
		int p1=0,p2=0,p3=0,p4=0;
		if (i+1< matrix.length&&matrix[i+1][j]>matrix[i][j]) p1=f(matrix,i+1,j,dp);
		if (i-1>=0 && matrix[i-1][j]>matrix[i][j]) p2=f(matrix,i-1,j,dp);
		if (j+1< matrix[0].length&&matrix[i][j+1]>matrix[i][j]) p3=f(matrix,i,j+1,dp);
		if (j-1>=0&&matrix[i][j-1]>matrix[i][j]) p4=f(matrix,i,j-1,dp);
		dp[i][j]=1+Math.max(Math.max(Math.max(p1,p2),p3),p4);
		return dp[i][j];
	}


}
