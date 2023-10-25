package Leetcode.AllQuestions;

import java.util.Arrays;

public class Leetcode_2305_DistributeCookies {
    //答案和输入的顺序无关
    //涉及到集合(子序列)的划分、这里面有消耗的概念，你需要去消耗k个集合，这k个集合组成了输入
    //当看到如上特点的时候，就要往状态压缩dp上面想。
    //f[i][j]:消耗了i个子序列，这些子序列组成了集合j，这k个子序列最大值的最小值为f[i][j]
    //推荐使用消耗和集合的思想来解决这类问题
    //f[i][j]怎么转移？我们肯定需要枚举j集合的子集s，算出子集s的元素和sum[s],我们需要从f[i-1][j^s]
    //转义过来，f[i-1][j^s]其实就是剩下集合划分中最大值最小的，需要和sum[s]再pk出i个集合中最大的
    //所以f[i][j]=min{max(f[i-1][j^s],sum[s]) for s in j}
    //1.为什么这里不枚举集合即可，还需要一个消耗序列的概念呢？因为如果不枚举消耗的概念，你单单拿到一个集合，不知道这个集合的值
    //已经划分了几个子序列，无法帮助你转移。
    //2.这里为什么不枚举cookies[i]，而是直接枚举子集s呢？因为你只枚举出一个数字，而这个数字加到哪里你是不知道的，没有办法转移
    public int distributeCookies1(int[] cookies, int k) {
        int n=cookies.length;
        int[][] f=new int[k+1][1<<n];//n个元素的集合一共有2^n个子集
        for(var a:f) Arrays.fill(a,1<<30);//求最小，初始化为大的值
        int[] sum=new int[1<<n];//sum[i]代表集合i的元素和
        for(int i=0;i<1<<n;i++){//i是一个集合
            for(int j=0;j<n;j++){//枚举子集的j个位置看看是否有元素
                if((i>>j&1)==1) sum[i]+=cookies[j];
            }
        }
        //f的第一行，也就是消耗了0个序列，组成了j集合是没有意义的，初始化第二行，从第三行开始填
        System.arraycopy(sum,0,f[1],0,1<<n);//消耗了1个集合组成j集合，那么说明f[1][j]=sum[j]
        for(int i=2;i<=k;i++){
            for(int j=0;j<1<<n;j++){
                for(int s=j;s>0;s=(s-1)&j){//s为j的非空子集
                    f[i][j]=Math.min(f[i][j],Math.max(f[i-1][j^s],sum[s]));
                }
            }
        }
        return f[k][(1<<n)-1];
    }

    //上面的f[i][j]的转移只和f[i-1][x]有关，并且x<j，如果采用滚动数组，左边的需要晚点更新，所以j逆序填
    public int distributeCookies2(int[] cookies, int k) {
        int n=cookies.length;
        int[] f=new int[1<<n];//n个元素的集合一共有2^n个子集
        int[] sum=new int[1<<n];//sum[i]代表集合i的元素和
        for(int i=0;i<1<<n;i++){//i是一个集合
            for(int j=0;j<n;j++){//枚举子集的j个位置看看是否有元素
                if((i>>j&1)==1) sum[i]+=cookies[j];
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
    public int distributeCookies4(int[] cookies, int k) {
        Arrays.sort(cookies);
        int n=cookies.length,l=0,r=Arrays.stream(cookies).sum(),mid;
        while(l<=r){
            mid=l+(r-l>>1);
            if(can(cookies,k,mid)) r=mid-1;
            else l=mid+1;
        }
        return l;
    }

    public boolean can(int[] cookies, int k,int target){//任意桶的累加和<=target就成功
        int n=cookies.length;
        return f(cookies,k,target,n-1,new int[k]);
    }

    //index代表当前要分配的饼干，index方向从右往左，因为大的先分配可以剪枝。
    //sum[i]代表i号孩子累计接到的饼干总量
    //回溯的枚举方式：枚举每一个饼干发给每一个孩子
    public boolean f(int[] cookies, int k,int target,int index,int[] sum){
        if(index==-1) return true;
        for(int i=0;i<k;i++){//枚举把cookies[index]发给一个孩子
            if(cookies[index]+sum[i]<=target){
                sum[i]+=cookies[index];
                if(f(cookies,k,target,index-1,sum)) return true;
                sum[i]-=cookies[index];
            }
            //如果当前孩子什么都没有，进入递归都没有返回，那么意味着无论怎么分配都满足不了target
            if(sum[i]==0) return false;//极其重要的剪枝！！！
        }
        return false;
    }

}
