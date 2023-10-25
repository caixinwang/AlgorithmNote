package Leetcode.AllQuestions;

import java.util.Arrays;

public class Leetcode_0698_CanPartitionKSubsets {
    //状压dp
    //f[i]代表i集合能否划分出相等的状态
    public boolean canPartitionKSubsets1(int[] nums, int k) {//复杂度 3^n，超时,这题n偏大
        int n=nums.length,all=Arrays.stream(nums).sum(),avg=all/k;
        if(all%k!=0) return false;
        int[] sum=new int[1<<n];//sum[i]代表集合i的元素和
        for(int i=0;i<1<<n;i++){//i是一个集合
            for(int j=0;j<n;j++){//枚举子集的j个位置看看是否有元素
                if((i>>j&1)==1) sum[i]+=nums[j];
            }
        }
        boolean[] f=new boolean[1<<n];//n个元素的集合一共有2^n个子集
        f[0]=true;
        for(int i=1;i<1<<n;i++){
            for(int s=i;s>0;s=(s-1)&i){//s为i的非空子集
                f[i]|=f[i^s]&&sum[s]==avg;
                if(f[i]) break;//重要剪枝，后面的子集对应的累加和越来越大，可以提前退出了
            }
        }
        return f[(1<<n)-1];
    }

    //状压dp
    //f[i]表示使用i所代表的那些数字 是否能够使得每个集合累加和都<=avg
    //我们分配集合的顺序是从左往右的，例如i-->(i|(1<<j))就是在原本i的集合的右边加上了nums[j]
    //由于我们强制sum=avg*k,所以如果最后可以满足每个子集合累加和都<=avg，那么必然是都等于avg
    //为什么sum[i]代表的元素和要%agv？因为这样才可以保证是一次凑一组转移来的
    public boolean canPartitionKSubsets2(int[] nums, int k) {//复杂度 n*2^n 通过
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

    //爆搜剪枝
    public boolean canPartitionKSubsets3(int[] nums, int k) {
        int n=nums.length,all= Arrays.stream(nums).sum(),avg=all/k;
        if(all%k!=0) return false;
        Arrays.sort(nums);
        return f(nums,k,avg,n-1,new int[k]);
    }

    //sum[i]代表第i个集合的累加和，index代表当前要选择一个集合让nums[index]进去
    //本质就是index位置选还是不选，选进哪一个桶里？
    public boolean f(int[] nums, int k,int avg,int index,int[] sum){
        if(index==-1){//反着来，因为要从大到小
            //for循环可以不用，因为上游保证all可以整除k，既然这里每个集合都<=avg，递归到底必然是avg
            //反之，如果上游调用没有检查all%k!=0，那么这里就需要检查
            for(var c:sum) if(c!=avg) return false;
            return true;
        }
        for(int i=0;i<k;i++){//枚举集合让nums[index]进去
            if(sum[i]+nums[index]<=avg){
                sum[i]+=nums[index];
                if(f(nums,k,avg,index-1,sum)) return true;
                sum[i]-=nums[index];
            }
            if(sum[i]==0) break;//最最重要的剪枝！sum[i]进了一次循环出来还是空的，说明根本没法继续填充了
        }
        return false;
    }
}
