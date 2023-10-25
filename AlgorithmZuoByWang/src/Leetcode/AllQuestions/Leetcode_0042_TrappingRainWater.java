package Leetcode.AllQuestions;

public class Leetcode_0042_TrappingRainWater {

	public static int trap(int[] arr) {
		if (arr==null||arr.length<3) return 0;
		int res=0;
		int l=1,r= arr.length-2;
		int lmax=arr[0],rmax=arr[arr.length-1];
		while(l<=r){
			if (lmax<=rmax){
				res+=Math.max(0,lmax-arr[l]);//最少也要加个0，因为当前位置可能比较高
				lmax = Math.max(lmax, arr[l++]);
			}else {
				res+=Math.max(0,rmax-arr[r]);
				rmax = Math.max(rmax, arr[r--]);
			}
		}
		return res;
	}

	public int trap2(int[] height) {
		int n=height.length;
		if(n<=2) return 0;
		int[] pre=new int[n],suf=new int[n];
		pre[0]=height[0];suf[n-1]=height[n-1];
		for(int i=1;i<n;i++){
			pre[i]=Math.max(pre[i-1],height[i]);
			suf[n-1-i]=Math.max(suf[n-1-i+1],height[n-1-i]);
		}
		int ans=0;
		for(int i=1;i<n-1;i++){
			ans+=Math.max(0,Math.min(pre[i-1],suf[i+1])-height[i]);
		}
		return ans;
	}

}
