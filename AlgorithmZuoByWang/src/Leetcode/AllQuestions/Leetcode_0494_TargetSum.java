package Leetcode.AllQuestions;

import java.util.Arrays;
import java.util.HashMap;
public class Leetcode_0494_TargetSum {
	//添加正号数的和p，总和s，那么添加负数的和s-p
	//根据题目要求p-(s-p)=t , 2p=s+t , p=(s+t)/2
	//p一定是整数，所以s+t一定要是偶数才行。并且由于nums都是非负数，所以p>=0
	//这题转化为一个01背包问题，等价于每个位置有 选和不选 最终需要挑选出出(s+t)/2这么多
	public int findTargetSumWays(int[] nums, int target) {
		int n=nums.length;
		int s= Arrays.stream(nums).sum(),p=s+target>>1;
		if((s+target&1)!=0||p<0) return 0;//s+t不是偶数或者p为偶数都不行

		int[] f=new int[p+1];
		f[0]=1;
		for(int i=n-1;i>=0;i--){
			//由于每次都是依赖自己上面的，左边的位置，所以我们从右边开始更新，不会覆盖左边导致铸错
			for(int j=p;j>=0;j--){
				if(j<nums[i]) f[j]=f[j];
				else f[j]=f[j]+f[j-nums[i]];
			}
		}
		return f[p];

		// int[][] f=new int[n+1][p+1];//可以改空间压缩
		// f[n][0]=1;//边界条件
		// for(int i=n-1;i>=0;i--){
		//     for(int j=0;j<=p;j++){
		//         if(j<nums[i]) f[i][j]=f[i+1][j];
		//         else f[i][j]=f[i+1][j]+f[i+1][j-nums[i]];
		//     }
		// }
		// return f[0][p];

		// return f(nums,0,p); //改动态规划
	}

	//暴搜：nums[index]讨论选或者不选，还需要选出rest的值
	public int f(int[] nums,int index, int rest){
		if(index==nums.length) return rest==0?1:0;//边界条件
		if(rest<nums[index]) return f(nums,index+1,rest);//不够选
		return f(nums,index+1,rest)+f(nums,index+1,rest-nums[index]);
	}

}
