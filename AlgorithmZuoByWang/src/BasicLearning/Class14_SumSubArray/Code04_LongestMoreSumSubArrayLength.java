package BasicLearning.Class14_SumSubArray;

import TestUtils.ArrayUtil;

public class Code04_LongestMoreSumSubArrayLength {//>=K的最长

	public static int moreSumSubArrayLength(int[] arr, int k) {//使用二分，更加通用
		int n=arr.length,ans=0,sum=0;
		int[] down=new int[n+1];
		for(int i=0;i<n;i++) {
			sum+=arr[i];
			down[i+1]=sum<down[i]?sum:down[i];//维持一个非递增序列down
		}
		sum=0;
		for (int i=1;i<=n;i++){
			sum+=arr[i-1];//代表s[i]
			int l=0,r=i-1,mid;
			while(l<=r){
				mid=l+(r-l>>1);
				if (down[mid]<=sum-k) r=mid-1;
				else l=mid+1;
			}
			ans = Math.max(ans, i-l);
		}
		return ans;
	}

	//>=0，固定右边的情况下希望左边越小越好。所以单调栈越靠近右边就越小,所以是一个递减的序列
	public static int moreSumSubArrayLength2(int[] arr, int k) {//使用单调栈-这里把单调栈叫做候选可能更加合适
		int n=arr.length,ans=0,top=-1;
		int[] stack=new int[n+1],s=new int[n+1];//单调栈和前缀和数组
		for (int i=0;i<n;i++) s[i+1]=s[i]+arr[i];//处理成前缀和数组之后就忘掉源数组！
		for (int i=0;i<n;i++) if (top==-1||s[i]<s[stack[top]]) stack[++top]=i;//不踢人，比栈顶小就进来,放下标
		for (int i=n;i>=0;i--) while(top>=0&&s[i]-s[stack[top]]>=k) ans = Math.max(ans,i-stack[top--]);
		return ans;
	}

	public static int correct(int[] nums,int k){//暴力
		int n=nums.length,ans=0;
		for(int l=0;l<n;l++){
			for (int r=l;r<n;r++){
				int sum=0;
				for (int i=l;i<=r;i++) sum+=nums[i];
				if (sum>=k) ans = Math.max(ans, r-l+1);
			}
		}
		return ans;
	}

	static ArrayUtil au=new ArrayUtil();

	public static void main(String[] args) {
		System.out.println("test begin");
		for (int i = 0; i < 1000000; i++) {
			int[] arr = au.generateRandomArr(10, -20,20);
			int k = au.ran(-200,200);
			int ans1= moreSumSubArrayLength(arr, k);
			int ans2=moreSumSubArrayLength2(arr, k);
			if (ans1 != ans2) {
				System.out.println(ans1);
				System.out.println(ans2);
				System.out.println("==================");
			}
		}
		System.out.println("test finish");
	}

}
