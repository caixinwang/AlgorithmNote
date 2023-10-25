package Leetcode.AllQuestions;

import java.util.ArrayList;

public class Leetcode_0992_SubarraysWithKDifferentIntegers {

	public static int subarraysWithKDistinct(int[] arr, int k) {//恰好的问题可以用至多的问题来解
		return f(arr, k) - f(arr, k - 1);
	}

	public static int f(int[] nums,int k){//种数<=k的子串的个数
		int ans=0,n=nums.length,diff=0;
		int[] cnt=new int[2*(int)(1e4)+1];
		for(int l=0,r=0;r<n;){//固定r
			while(diff==k&&cnt[nums[r]]==0){
				if(--cnt[nums[l++]]==0) diff--;
			}
			if(cnt[nums[r++]]++==0) diff++;
			ans+=r-l;
		}
		return ans;
	}

	public int f2(int[] nums,int k){//种数<=k的子串的个数
		int ans=0,n=nums.length,diff=0;
		int[] cnt=new int[2*(int)(1e4)+1];
		for(int l=0,r=0;l<n;){//固定l
			while(r<n&&!(diff==k&&cnt[nums[r]]==0)){//能阔就一直扩，扩到马上要不达标了就停
				if(cnt[nums[r++]]++==0) diff++;
			}
			ans+=r-l;//上面下来肯定是达标的情况
			if (cnt[nums[l++]]--==1) diff--;
		}
		return ans;
	}

	public static int subarraysWithKDistinct2(int[] arr, int k) {//短窗口维持一个恰好k种的最短子串的答案
		int n=arr.length,types=0,types1=0,ans=0;
		int[][] cnt=new int[2][n+1];//分别维持r作为右端点，最短的窗口和最长的窗口
		for (int r=0,l0=0,l1=0;r<n;r++){//l是短窗口的左端点，l1是长窗口的左端点
			while((types==k&&cnt[0][arr[r]]==0)){//当且仅当type==k且r位置是一个新数的时候需要缩小左端点。
				if (--cnt[0][arr[l0++]]==0) types--;
			}
			while((types1==k&&cnt[1][arr[r]]==0)){
				if (--cnt[1][arr[l1++]]==0) types1--;
			}
			if (cnt[0][arr[r]]++==0) types++;
			if (cnt[1][arr[r]]++==0) types1++;
			while(types==k&&cnt[0][arr[l0]]>1) --cnt[0][arr[l0++]];//短窗口在长窗口的基础上，缩到不能再缩
			if (types1==k) ans+=l0-l1+1;//之间夹的就是r作为右端点的答案
		}
		return ans;
	}

	public static int subarraysWithKDistinct3(int[] arr, int k) {//短窗口维持一个恰好k-1种数的最长的答案，和上面的做法差别就是-1
		int n=arr.length,types=0,types1=0,ans=0;
		int[][] cnt=new int[2][n+1];//分别维持r作为右端点，最短的窗口和最长的窗口
		for (int r=0,l0=0,l1=0;r<n;r++){//l是短窗口的左端点，l1是长窗口的左端点
			while((types==k-1&&cnt[0][arr[r]]==0)){//当且仅当type==k且r位置是一个新数的时候需要缩小左端点。
				if (--cnt[0][arr[l0++]]==0) types--;
			}
			while((types1==k&&cnt[1][arr[r]]==0)){
				if (--cnt[1][arr[l1++]]==0) types1--;
			}
			if (cnt[0][arr[r]]++==0) types++;
			if (cnt[1][arr[r]]++==0) types1++;
			if (types1==k) ans+=l0-l1;//之间夹的就是r作为右端点的答案
		}
		return ans;
	}


}
