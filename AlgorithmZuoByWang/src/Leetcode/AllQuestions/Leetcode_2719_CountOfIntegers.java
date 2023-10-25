package Leetcode.AllQuestions;

import java.util.Arrays;

public class Leetcode_2719_CountOfIntegers {
    final int MOD = (int) 1e9 + 7;
    int min_sum, max_sum;

    //这题要计算范围在[num1,num2]的好整数。我们可以先搞定范围在0~num的好整数。答案就是f(num2)-f(num1)+isok(num1)?1:0
    public int count(String num1, String num2, int min_sum, int max_sum) {
        this.max_sum = max_sum;
        this.min_sum = min_sum;
        char[] str1 = num1.toCharArray();
        char[] str2 = num2.toCharArray();
        int sum = 0;
        for (int i = 0; i < str1.length; sum += str1[i] - '0', i++) ;
        boolean isok = sum >= min_sum && sum <= max_sum;
        long[][][] dp1 = new long[str1.length + 1][max_sum + 1][2];
        long[][][] dp2 = new long[str2.length + 1][max_sum + 1][2];
        for (long[][] a : dp2) for (long[] b : a) Arrays.fill(b, -1);
        for (long[][] a : dp1) for (long[] b : a) Arrays.fill(b, -1);
        return (int) ((f(str2, 0, 0, 1, dp2)
                - f(str1, 0, 0, 1, dp1) + (isok ? 1 : 0) + MOD) % MOD);
    }

    /**
     * 这题不需要处理前导零，如果需要处理前导零，那么只需要多加一个boolean的变量
     * 事实上，这一题记忆化搜索不需要第三个维度，因为is_limit为1的情况只会被算一次
     *
     * @param num      -
     * @param index    当前填到第i个位置，从i~往后填
     * @param preSum   之前累计的数位合
     * @param is_limit 是否被前面约束。如果被约束，那么只能填写从0到自己，否则可以从0~9
     * @return 构造小于等于num的好整数
     */
    public long f(char[] num, int index, int preSum, int is_limit, long[][][] dp) {
        if (dp[index][preSum][is_limit] != -1) return dp[index][preSum][is_limit];
        if (index == num.length) {
            dp[index][preSum][is_limit] = preSum >= min_sum ? 1 : 0;
        } else {
            long ans = 0;
            for (int digit = 0, up = is_limit == 1 ? num[index] - '0' : 9; digit <= up; digit++) {
                if (preSum + digit <= max_sum) {//提前剪枝，不剪枝的话和min_sum一起放在base case
                    ans += f(num, index + 1, preSum + digit, is_limit == 1 && digit == up ? 1 : 0, dp);
                    ans %= MOD;
                }
            }
            dp[index][preSum][is_limit] = ans;
        }
        return dp[index][preSum][is_limit];
    }

    public int count2(String num1, String num2, int min_sum, int max_sum) {
        this.max_sum = max_sum;
        this.min_sum = min_sum;
        char[] str1 = num1.toCharArray();
        char[] str2 = num2.toCharArray();
        int sum = 0;
        for (int i = 0; i < str1.length; sum += str1[i] - '0', i++) ;
        boolean isok = sum >= min_sum && sum <= max_sum;
        long[][] dp1 = new long[str1.length + 1][max_sum + 1];
        long[][] dp2 = new long[str2.length + 1][max_sum + 1];
        for (long[] a : dp2) Arrays.fill(a, -1);
        for (long[] a : dp1) Arrays.fill(a, -1);
        return (int) ((f2(str2, 0, 0, true, dp2)
                - f2(str1, 0, 0, true, dp1) + (isok ? 1 : 0) + MOD) % MOD);
    }

    public long f2(char[] num, int index, int preSum, boolean is_limit, long[][] dp) {//记忆化搜索去掉第三个维度
        if (dp[index][preSum] != -1 && !is_limit) return dp[index][preSum];
        if (index == num.length) {
            if (!is_limit) dp[index][preSum] = preSum >= min_sum ? 1 : 0;
            return preSum >= min_sum ? 1 : 0;
        }
        long ans = 0;
        for (int digit = 0, up = is_limit ? num[index] - '0' : 9; digit <= up; digit++) {
            if (preSum + digit <= max_sum) {//提前剪枝，不剪枝的话和min_sum一起放在base case
                ans += f2(num, index + 1, preSum + digit, is_limit&&digit == up, dp);
                ans %= MOD;
            }
        }
        if (!is_limit)dp[index][preSum] = ans;
        return ans;
    }

    public static void main(String[] args) {
        String a = "4179205230";
        String b = "7748704426";
        System.out.println(new Leetcode_2719_CountOfIntegers().count(a, b, 8, 46));
    }
}
