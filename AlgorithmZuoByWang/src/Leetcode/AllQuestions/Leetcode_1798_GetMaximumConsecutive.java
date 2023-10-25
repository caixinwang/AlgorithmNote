package Leetcode.AllQuestions;

import java.util.Arrays;

public class Leetcode_1798_GetMaximumConsecutive {
    //先排序，然后用一个dp（空间压缩），代表前i个硬币可以凑出多少个连续整数
    public int getMaximumConsecutive(int[] coins) {
        int n=coins.length,f=1;
        Arrays.sort(coins);
        for(int i=1;i<=n;i++)if(coins[i-1]<=f) f+=coins[i-1];
        return f;
    }
}
