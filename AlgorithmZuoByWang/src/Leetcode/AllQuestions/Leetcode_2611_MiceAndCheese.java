package Leetcode.AllQuestions;

import TestUtils.ArrayUtil;

import java.util.Arrays;

public class Leetcode_2611_MiceAndCheese {
    //方法1 贪心：假设都被第2只老鼠吃了，然后再去挪k个给第1只老鼠吃。
    public static int miceAndCheese(int[] reward1, int[] reward2, int k) {
        int ans=0,n=reward1.length;
        for(int i=0;i<n;i++){//计算n个奶酪全部给老鼠2吃的起始分数。以及处理r1数组
            ans+=reward2[i];
            reward1[i]-=reward2[i];
        }
        Arrays.sort(reward1);
        for(int i=n-1,j=0;j<k;i--,j++){//处理之后的数组从大到小遍历，挪k个奶酪给老鼠1
            ans+=reward1[i];
        }
        return ans;
    }

    //方法2 超时: 动态规划01背包问题，选reward1[i] or reward2[i] ,加k的限制
    //dp[i][j]含义为：在0~i范围上，选j个奶酪给老鼠1吃的最大得分
    //转移方程dp[i][j]：dp[i-1][j] 与 dp[i-1][j-1],如果[i][j]从[i-1][j]来，那么i奶酪只能给老鼠2
    //如果从[i-1][j-1]来，那么i奶酪就给老鼠1吃。
    //dp[i][j]=max{dp[i-1][j-1]+r1[i],dp[i-1][j]+r2[i]}
    static int MIN=1<<31,MAX=MIN-1;
    public static int miceAndCheese2(int[] reward1, int[] reward2, int k) {
        int n=reward1.length;
        int[][] dp=new int[n][k+1];
        for(int i=0;i<n;i++){//第一列，全部给老鼠2吃
            dp[i][0]=reward2[i]+(i-1>=0?dp[i-1][0]:0);
        }
        if(k==0) return dp[n-1][0];
        for(int j=1;j<=k;j++){
            dp[0][j]=j==1?reward1[0]:MIN;//第一行初始化完毕
        }
        for(int i=1;i<n;i++){
            for(int j=1;j<=k;j++){
                if(i+1<j) dp[i][j]=MIN;//奶酪比老鼠1吃的数量还少
                else dp[i][j]=Math.max(dp[i-1][j-1]+reward1[i],dp[i-1][j]+reward2[i]);
            }
        }
        return  dp[n-1][k];
    }

    static ArrayUtil au=new ArrayUtil();

    public static void main(String[] args) {
        for (int i = 0; i < 100000; i++) {
            int[] arr = au.generateRandomArr(au.ran(1, 300), 1, 100);
            int[] arr2 = au.generateRandomArr(arr.length, 1, 100);
            int k=au.ran(0,arr.length);
            int ans2=miceAndCheese2(au.copyArray(arr),au.copyArray(arr2),k);
            int ans1=miceAndCheese(arr,arr2,k);
            if (ans1!=ans2) {
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("======================");
                break;
            }
        }
    }
}
