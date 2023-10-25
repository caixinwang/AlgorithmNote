package Leetcode.AllQuestions;

public class Leetcode_0918_MaxSubarraySumCircular {
    int MIN=1<<31,MAX=MIN-1;
    //思路：
    // 1.将nums数组头尾拼接成一个2n大小的数组arr，这样环形数组的所有子数组都是arr的子数组，
    //   arr所有的长度小于等于n的子数组就是环形数组的所有子数组。
    // 2.问题就转化为在长度为2n的arr中，求长度小于等于n的子数组的累加和的最大值。
    // 3.利用单调队列dq维持一个递增的序列，对应的下标存在idxs里面，方便移除长度超过n的左端点，也就是队头。
    // 4.为什么可以使用单调队列：因为[l,r]子数组的累加和其实等于sum[r]-sum[l-1].我们要累加和的最大值,固定左端点去求
    //      那么肯定希望sum[r]大sum[l]小。所以维持一个递增的序列
    public int maxSubarraySumCircular(int[] nums) {
        int n=nums.length,h=0,t=-1,ans=MIN,sum=0;
        int[] dq=new int[n<<1|1];//单调队列，存值
        int[] idxs=new int[n<<1|1];//和单点队列配合使用存下标
        int[] arr=new int[n<<1];//拼接源数组模拟环
        System.arraycopy(nums,0,arr,0,n);
        System.arraycopy(nums,0,arr,n,n);
        dq[++t]=0;//什么都没开始之前已经有一个累加和叫做0了.s[0]=0
        for(int i=0;i<n<<1;i++){
            sum+=arr[i];//s[i+1]
//            sum+=nums[i%n];//用这句就可以不用创建arr数组了
            while(h<=t&&i-idxs[h]>=n) h++;//拉的太长了，超出环形数组了
            while(h<=t&&sum<dq[t]) t--;//队头到到队尾是单调递增的
            ans=max(ans,sum-dq[h]);
            dq[++t]=sum;
            idxs[t]=i;
        }
        return ans;
    }
    public int maxSubarraySumCircular2(int[] nums) {
        int sum=0,minc=0,maxc=0,maxAll=MIN,minAll=MAX;
        for(int c:nums){
            sum+=c;
            maxc=maxc>0?c+maxc:c;
            minc=minc<0?c+minc:c;
            maxAll=max(maxAll,maxc);
            minAll=min(minAll,minc);
        }
        return max(maxAll,sum==minAll?MIN:sum-minAll);
    }

    public int max(int a,int b){return a>b?a:b;}
    public int min(int a,int b){return a<b?a:b;}
}
