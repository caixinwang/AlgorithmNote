package Leetcode.AllQuestions;

public class Leetcode_1035_UncrossedLines {
	//本质：求最长公共子序列
	//dp[i][j]含义为长度i的nums1前缀与长度j的num2前缀的最大连线数量
	//[i][j]位置转移:1.与i和j位置都有关，nums1[i-1]==nums2[j-1] => dp[i][j]=dp[i-1][j-1]+1
	//2.与i有关与j无关，dp[i][j]=dp[i][j-1]
	//3.与j有关与i无关,dp[i][j]=dp[i-1][j]
	//4.与i和j都无关，dp[i][j]=dp[i-1][j-1]，显然这种不需要考虑，肯定干不过2和3
	//==>转移方程:dp[i][j]=dp[i-1][j-1]+1,nums1[i-1]==nums2[j-1]
	//           dp[i][j]=max{dp[i][j-1],dp[i-1][j]},nums1[i-1]!=nums2[j-1]
	public int maxUncrossedLines(int[] nums1, int[] nums2) {
		int n=nums1.length,m=nums2.length;
		int[][] dp=new int[n+1][m+1];//零行零列天然是0
		for(int i=1;i<=n;i++){
			for(int j=1;j<=m;j++){
				if(nums1[i-1]==nums2[j-1]) dp[i][j]=dp[i-1][j-1]+1;
				else dp[i][j]=Math.max(dp[i-1][j],dp[i][j-1]);
			}
		}
		return dp[n][m];
	}

}
