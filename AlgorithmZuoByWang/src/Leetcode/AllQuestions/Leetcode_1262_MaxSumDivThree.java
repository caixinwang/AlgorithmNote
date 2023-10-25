package Leetcode.AllQuestions;

public class Leetcode_1262_MaxSumDivThree {
    //(a+b)%3=(a%3+b%3)%3  =>  (dp[i-1][j]+nums[i])%3=(dp[i-1][j']%3+nums[i]%3)=j
    // => j' + nums[i]%3 = j  => j'=(j-num[i]%3+3)%3
    public int maxSumDivThree(int[] nums) {
        int n=nums.length;
        int[][] dp=new int[n+1][3];//dp[i][j]代表前i个元素随便选，最终值%3=j，的最大值是多少
        for(int j=1;j<3;j++) dp[0][j]=-1;//只有dp[0][0]合法
        for(int i=1;i<=n;i++){
            for(int j=0;j<3;j++){
                dp[i][j]=dp[i-1][j];
                if(dp[i-1][(j-nums[i-1]%3+3)%3]!=-1){
                    dp[i][j]=Math.max(dp[i][j],nums[i-1]+dp[i-1][(j-nums[i-1]%3+3)%3]);
                }
            }
        }
        return dp[n][0];
    }

    public int maxSumDivThree2(int[] nums) {//空间压缩
        int n=nums.length;
        int[] dp=new int[3];//dp[i][j]代表前i个元素随便选，最终值%3=j，的最大值是多少
        for(int j=1;j<3;j++) dp[j]=-1;//只有dp[0][0]合法
        for(int i=1;i<=n;i++){
            int[] ndp=new int[3];
            for(int j=0;j<3;j++){
                ndp[j]=dp[j];
                if(dp[(j-nums[i-1]%3+3)%3]!=-1){
                    ndp[j]=Math.max(dp[j],nums[i-1]+dp[(j-nums[i-1]%3+3)%3]);
                }
            }
            for(int k=0;k<3;k++) dp[k]=ndp[k];
        }
        return dp[0];
    }
}
