package Leetcode.AllQuestions;

import java.util.TreeSet;

public class Leetcode_1049_LastStoneWeightII {
    //挑出来的石头和没有挑出来的石头粉碎：|p1+p2-(sum-p1+p2)| = |2p1+2p2-sum|
    //|2p1+2p2-sum| 要小等价于 |p1+p2-sum/2|要小 等价于 p2要接近-(p1-sum/2)=sum/2-p1
    int MIN=1<<31,MAX=MIN-1;
    public int lastStoneWeightII(int[] stones) {//类似划分两个部分的，可以用分治
        int n=stones.length,ans=MAX,sum=0;
        for(int num:stones) sum+=num;
        TreeSet<Integer> set1=new TreeSet<>();
        TreeSet<Integer> set2=new TreeSet<>();
        f(stones,0,n>>1,0,set1);
        f(stones,n>>1,n,0,set2);
        for(int p1:set1){
            Integer p2=set2.ceiling(sum/2-p1);//只要一边，另一边是对称的。
            if(p2!=null) ans=min(ans,abs((p1+p2)*2-sum));
        }
        return ans;
    }

    public int min(int a,int b){return a<b?a:b;}
    public int max(int a,int b){return a>b?a:b;}
    public int abs(int a){return a<0?-a:a;}

    public void f(int[] stones,int index,int end,int sum,TreeSet<Integer> set){
        if(index==end) set.add(sum);
        else{
            f(stones,index+1,end,sum+stones[index],set);
            f(stones,index+1,end,sum,set);
        }
    }

    //等价于背包容量为sum/2 问最多可以得到多少分 特殊的地方在于石头的价值和重量一样
    //dp[i][j]：有j的空间，拿取前i个的石头，可以得到多少分数
    //p1>p2,最高分p2得到之后,p1-p2=(sum-p2)-p2=sum-2p2就是答案
    public int lastStoneWeightII2(int[] stones) {
        int sum=0,n=stones.length;
        for(int s:stones) sum+=s;
        int[][] dp=new int[n+1][sum/2+1];
        for(int i=1;i<=n;i++){
            for(int j=0;j<=sum/2;j++){
                dp[i][j]=dp[i-1][j];
                if(j>=stones[i-1]){
                    dp[i][j]=max(dp[i][j],dp[i-1][j-stones[i-1]]+stones[i-1]);
                }
            }
        }
        return sum-2*dp[n][sum/2];
    }
}
