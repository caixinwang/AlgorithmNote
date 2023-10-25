package Leetcode.AllQuestions;

import java.util.Arrays;

public class Leetcode_1723_MinimumTimeRequired {//解析到2305看
    public int minimumTimeRequired(int[] jobs, int k) {
        int n=jobs.length;
        int[] f=new int[1<<n];//n个元素的集合一共有2^n个子集
        int[] sum=new int[1<<n];//sum[i]代表集合i的元素和
        for(int i=0;i<1<<n;i++){//i是一个集合
            for(int j=0;j<n;j++){//枚举子集的j个位置看看是否有元素
                if((i>>j&1)==1) sum[i]+=jobs[j];
            }
        }
        //f的第一行，也就是消耗了0个序列，组成了j集合是没有意义的，初始化第二行，从第三行开始填
        System.arraycopy(sum,0,f,0,1<<n);//消耗了1个集合组成j集合，那么说明f[1][j]=sum[j]
        for(int i=2;i<=k;i++){
            for(int j=(1<<n)-1;j>=0;j--){
                f[i]=1<<30;
                for(int s=j;s>0;s=(s-1)&j){//s为j的非空子集
                    f[j]=Math.min(f[j],Math.max(f[j^s],sum[s]));
                }
            }
        }
        return f[(1<<n)-1];
    }

    //二分找答案+剪枝回溯实现can方法
    public int minimumTimeRequired2(int[] job, int k) {
        Arrays.sort(job);
        int n=job.length,l=0,r=Arrays.stream(job).sum(),mid;
        while(l<=r){
            mid=l+(r-l>>1);
            if(can(job,k,mid)) r=mid-1;
            else l=mid+1;
        }
        return l;
    }

    public boolean can(int[] job, int k,int target){//任意桶的累加和<=target
        int n=job.length;
        return f(job,k,target,n-1,new int[k]);
    }

    //index代表当前要分配的任务，index方向从右往左，因为大的先分配可以剪枝。
    //sum[i]代表i号工人累计接到的任务总量
    //回溯的枚举方式：枚举每一个任务发给每一个工人
    public boolean f(int[] job, int k,int target,int index,int[] sum){
        if(index==-1) return true;
        for(int i=0;i<k;i++){//枚举把cookies[index]发给一个工人
            if(job[index]+sum[i]<=target){
                sum[i]+=job[index];
                if(f(job,k,target,index-1,sum)) return true;
                sum[i]-=job[index];
            }
            //如果当前工人什么都没有，进入递归都没有返回，那么意味着无论怎么分配都满足不了target
            if(sum[i]==0) return false;//极其重要的剪枝！！！
        }
        return false;
    }
}
