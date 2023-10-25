package Leetcode.AllQuestions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Leetcode_2572_SquareFreeSubsets {
    //考虑如何把方便表示数。例如6和15这两个数字，6=2x3 15=3x5 ，如果同时选入一个集合，会有平方因子9。
    //找到这个表示数的方法之后，使得我们可以快速判断两个数相乘是否有平方因子。
    //因为数据范围只有1~30，所以我们可以找出里面所有的质数，每个数可以又哪些质数相乘得到也表示出来，
    //由于1~30只有10个质数，所以我们可以用int的10个位置来表示一个数可以拆分哪些质数,
    //所以我们需要先把每一个无平方因子数的质数因子表示为一个二进制，并且每一位对应的质数恰好只有1个
    //1.这样我们就可以转化为一个背包问题，枚举每一个数，放到每个不同的背包里面
    //f[i][j]定义为，前i个数自由选取(01背包)，恰好凑成j的方案个数。这里的j是质数因子表述的二进制数
    //2.转化为一个子集状压dp，枚举每一个子集。

    final static int MOD=(int)1e9+7;
    static int[] primes=new int[]{2,3,5,7,11,13,17,19,23,29};
    static int[] nsq_to_mask=new int[31];//nsq的质因子二进制编码存储在这里,非nsq编码都是-1
    static{
        for(int i=2;i<=30;i++){
            for(int j=0,p=primes[j];j<primes.length&&(p=primes[j])>=1<<31;j++){
                if(i%p==0){//说明这个数有primes[j]这个质数因子
                    if(i%(p*p)==0) {
                        nsq_to_mask[i]=-1;//说明这个数不是无平方因子数
                        break;
                    }
                    else nsq_to_mask[i]|=1<<j;
                }
            }
        }
    }

    //01背包
    public int squareFreeSubsets1(int[] nums) {
        int n=nums.length;
        int[][] f=new int[n+1][1<<10];
        f[0][0]=1;
        for(int i=1;i<=n;i++){
            int mask=nsq_to_mask[nums[i-1]];
            for(int j=0;j<1<<10;j++){
                f[i][j]=f[i-1][j];//不选nums[i]
                //只有在nums是一个无平方质因数的时候才能选且是背包的子集才能选
                if(mask>=0&&(mask|j)==j){
                    f[i][j]=(f[i][j]+f[i-1][j^mask])%MOD;
                }
            }
        }
        int ans=0;
        for(int v:f[n]) ans=(ans+v)%MOD;
        return(int)((ans-1+MOD)%MOD);//减去空集,也就是j==0的情况
    }

    //做了空间压缩的01背包
    public int squareFreeSubsets2(int[] nums) {
        int n=nums.length,m=1<<10;
        int[] f=new int[m];
        f[0]=1;
        for(int i=1;i<=n;i++){
            int mask=nsq_to_mask[nums[i-1]];
            if(mask<0) continue;
            for(int j=m-1;j>=0;j--){//依赖上面的左边，所以右边先填就可以完成空间压缩
                f[j]=f[j];//不选nums[i]
                //只有在nums是一个无平方质因数的时候才能选且是背包的子集才能选
                if(mask>=0&&(mask|j)==j){
                    f[j]=(f[j]+f[j^mask])%MOD;
                }
            }
        }
        int ans=0;
        for(int v:f) ans=(ans+v)%MOD;
        return(int)((ans-1+MOD)%MOD);//减去空集,也就是j==0的情况
    }


    //状压dp。f[i][j]定义为：自由使用2~i这些种类的数值，nums数组有f[i][j]个子集凑成了j这个因数因子二进制数
    //f[i][j]如何转移。如果使用i这个数的话，那么i对应的mask必须是j的子集。
    //如果不使用i那么有f[i-1][j]种方案；如果使用i，那么有f[i-1][j^mask]*cnt[i]种方案
    //f[i][j] 也有选和不选两种，不选的是都可以的，选的只有mask是j的子集才行。
    //由于每次都使用的是i-1行的，并且都是从比自己小的j转移来，所以我们可以进行空间压缩，从右往左填写
    public static int squareFreeSubsets3(int[] nums) {
        int n=nums.length,m=1<<10,pow2=1;
        var f=new long[31][m];//f[i][j]使用1~i这些数值，凑成j这个质因子集合
        var cnt=new int[31];
        for(var c:nums) {
            if(c==1) pow2=pow2*2%MOD;//1的组合总数
            cnt[c]++;
        }
        f[1][0]=(pow2);//使用1只能凑成0这个集合，因为1没有质因子
        for(int i=2;i<=30;i++){
            int mask=nsq_to_mask[i];
            if(cnt[i]<=0||mask<=0) continue;//在外循环剪枝
            for(int j=0;j<m;j++){
                f[i][j]=f[i-1][j];//不使用i这个数
                if((j|mask)==j){//使用i这个数
                    f[i][j]=(f[i][j]+f[i-1][j^mask]*cnt[i])%MOD;
                }
            }
        }
        return (int)((Arrays.stream(f[30]).sum()-1)%MOD);
    }

    //从上面到这一步的空间压缩，我们发现f[j]=f[j]是可以省略的，也就是每一轮i天生就有了不选的方案数了
    //这个时候其实有很多的j如果不是mask的超级有其实就不需要循环了，因为它选不了i，并且自己不进循环也可以
    //保证继承了不选的方案数。所以我们可以进一步优化，只去枚举可能用到i的集合j
    public static int squareFreeSubsets4(int[] nums) {
        int n=nums.length,m=1<<10,pow2=1;
        var f=new long[m];//f[i][j]使用1~i这些数值，凑成j这个质因子集合
        var cnt=new int[31];
        for(var c:nums) {
            if(c==1) pow2=pow2*2%MOD;//1的组合总数
            cnt[c]++;
        }
        f[0]=(pow2);//使用1只能凑成0这个集合，因为1没有质因子
        for(int i=2;i<=30;i++){
            int mask=nsq_to_mask[i];
            if(cnt[i]<=0||mask<=0) continue;//在外循环剪枝
            for(int j=m-1;j>=0;j--){
                // f[j]=f[j];//不使用i这个数
                if((j|mask)==j){//使用i这个数
                    f[j]=(f[j]+f[j^mask]*cnt[i])%MOD;
                }
            }
        }
        return (int)((Arrays.stream(f).sum()-1)%MOD);
    }

    //值枚举可能使用i这个数的j，那么就要求mask是j的子集，那么反过来说，我们直接枚举mask的所有超集就行
    //如何枚举mask的超集？mask或上mask补集的子集即可。
    public static int squareFreeSubsets(int[] nums) {
        int n=nums.length,m=1<<10,pow2=1;
        var f=new long[m];
        var cnt=new int[31];
        for(var c:nums) {
            if(c==1) pow2=pow2*2%MOD;
            cnt[c]++;
        }
        f[0]=(pow2);//空集和{1...}先算进去
        for (int i=2;i<cnt.length;i++){//i的含义，使用2~i这些值，组成j集合
            int mask=nsq_to_mask[i],other=(m-1)^mask;
            if(mask<=0||cnt[i]<=0) continue;
            for (int s=other,j=s|mask;;s=(s-1)&other,j=s|mask){//枚举s的补集,s|mask得到mask的超集j
                f[j]=(f[j]+f[j^mask]*cnt[i])%MOD;//这里就不需要判断j是否是mask的超集了，这里j一定是
                if(s==0) break;//把空集也枚举的写法
            }
        }
        return (int)((Arrays.stream(f).sum()-1)%MOD);
    }


}
