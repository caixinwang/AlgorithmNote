package Leetcode.AllQuestions;

public class Leetcode_0312_BurstBalloons {

    public int maxCoins(int[] nums) {
        int[] arr=new int[nums.length+2];
        System.arraycopy(nums,0,arr,1,nums.length);
        arr[0]=1;
        arr[arr.length-1]=1;
        int[][] dp=new int[arr.length][arr.length];
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp.length; j++) {
                dp[i][j]=-1;
            }
        }
        return f(arr,1,nums.length,dp);
    }

    /**
     * 潜台词：L-1位置和R+1位置的气球没有爆。要维持住这个含义，你需要去枚举最后一个爆掉的气球。
     * 枚举最后一个爆掉的气球本质上就是在求一个逆过程
     * @param arr -
     * @param L 打爆[L,R]的气球可以获得的最大分数
     * @param R -
     * @param dp 傻缓存
     * @return -
     */
    private int f(int[] arr, int L, int R,int[][] dp) {
        if (dp[L][R]!=-1) return dp[L][R];
        if (L>R) {
            dp[L][R]= 0;
            return dp[L][R];
        }
        if (L==R) {
            dp[L][R]= arr[L - 1] * arr[R + 1] * arr[L];
            return dp[L][R];
        }
        int ans=0;
        for (int last=L;last<=R;last++){
            ans=Math.max(ans,arr[L-1]*arr[R+1]*arr[last]+f(arr,L,last-1,dp)+f(arr,last+1,R,dp));
        }
        dp[L][R]= ans;
        return dp[L][R];
    }

    public int maxCoins2(int[] nums) {
        int[] arr=new int[nums.length+2];
        System.arraycopy(nums,0,arr,1,nums.length);
        arr[0]=1;
        arr[arr.length-1]=1;
        int[][] dp=new int[arr.length+1][arr.length+1];
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp.length; j++) {
                dp[i][j]=-1;
            }
        }
        return f2(arr,0,nums.length+1,dp);
    }

    /**
     * 潜台词：L和R气球你不能去打，并且他们还没有爆
     * @param arr -
     * @param L 打爆[L,R]的气球可以获得的最大分数
     * @param R -
     * @param dp 傻缓存
     * @return -
     */
    private int f2(int[] arr, int L, int R,int[][] dp) {
        if (dp[L][R]!=-1) return dp[L][R];
        if (L>=R) {
            dp[L][R]= 0;
            return dp[L][R];
        }
        int ans=0;
        for (int last=L+1;last<R;last++){
            ans=Math.max(ans,arr[L]*arr[R]*arr[last]+f2(arr,L,last,dp)+f2(arr,last,R,dp));
        }
        dp[L][R]= ans;
        return dp[L][R];
    }


}
