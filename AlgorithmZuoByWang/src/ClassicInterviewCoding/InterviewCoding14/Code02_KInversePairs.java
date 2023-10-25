package ClassicInterviewCoding.InterviewCoding14;

public class Code02_KInversePairs {

    /**
     * dp[i][j]代表你有1~i的数字，自由排列，逆序对为j的排列种数有多少
     * @param n 你有1~n的数
     * @param k 逆序对为k的排列有几种
     * @return ~
     */
    public static int kInversePairs1(int n, int k) {//动态规划
        if (n < 1 || k < 0) {
            return 0;
        }
        int[][] dp = new int[n + 1][k + 1];
        for (int i = 0; i <= n; i++) {//第一列
            dp[i][0] = 1;
        }
        //第一行无意义
        dp[1][0] = 1;//第二行初始化
        for (int i = 2; i <= n; i++) {
            for (int j = 1; j <= k; j++) {
                for (int p = 0; p < i; p++) {//i个数字，就有i个坑位，枚举这所有的坑位
                    dp[i][j] += j - p >= 0 ? dp[i - 1][j - p] : 0;
                }
            }
        }
        return dp[n][k];
    }

    public static int kInversePairs2(int n, int k) {//省掉枚举行为
        if (n < 1 || k < 0) {
            return 0;
        }
        int[][] dp = new int[n + 1][k + 1];
        for (int i = 0; i <= n; i++) {//第一列
            dp[i][0] = 1;
        }
        //第一行无意义
        dp[1][0] = 1;//第二行初始化
        for (int i = 2; i <= n; i++) {
            for (int j = 1; j <= k; j++) {
                //观察发现每次会多出后面一个，如果前面有的话，会多算最前面一个，要剪掉，最前面的下标j-1-(i-1),因为p->[0,i)
                dp[i][j]=dp[i][j-1]-(j-1-i+1>=0?dp[i-1][j-1-i+1]:0)+dp[i-1][j];
            }
        }
        return dp[n][k];
    }

        public static void main(String[] args) {
            System.out.println(kInversePairs1(7,4));
            System.out.println(kInversePairs2(7,4));
        }
}