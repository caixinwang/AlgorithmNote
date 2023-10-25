package Leetcode.AllQuestions;

import java.util.Arrays;

public class Leetcode_0473_Makesquare {
    //使用698题的代码
    public boolean makesquare(int[] matchsticks) {
        return canPartitionKSubsets(matchsticks,4);
    }

    public boolean canPartitionKSubsets(int[] nums, int k) {//复杂度 n*2^n 通过
        int n=nums.length,all=Arrays.stream(nums).sum(),avg=all/k;
        Arrays.sort(nums);
        if(all%k!=0||nums[n-1]>avg) return false;
        int[] sum=new int[1<<n];//sum[i]代表集合i的元素和%avg
        boolean[] f=new boolean[1<<n];//n个元素的集合一共有2^n个子集
        f[0]=true;
        for(int i=1;i<1<<n;i++){
            for(int j=0;j<n;j++){//尝试使用nums[j]
                if(sum[i^(1<<j)]+nums[j]<=avg&&(1<<j|i)==i&&f[i^(1<<j)]){
                    sum[i]=(sum[i^(1<<j)]+nums[j])%avg;
                    f[i]=true;
                    break;
                }
            }
        }
        return f[(1<<n)-1];
    }
}
