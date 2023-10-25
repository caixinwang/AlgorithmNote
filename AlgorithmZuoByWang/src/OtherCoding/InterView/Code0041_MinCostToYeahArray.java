package OtherCoding.InterView;

import java.util.Arrays;

// 来自360笔试
// 给定一个正数数组arr，长度为n，下标0~n-1
// arr中的0、n-1位置不需要达标，它们分别是最左、最右的位置
// 中间位置i需要达标，达标的条件是 : arr[i-1] > arr[i] 或者 arr[i+1] > arr[i]哪个都可以
// 你每一步可以进行如下操作：对任何位置的数让其-1
// 你的目的是让arr[1~n-2]都达标，这时arr称之为yeah！数组
// 返回至少要多少步可以让arr变成yeah！数组
// 数据规模 : 数组长度 <= 10000，数组中的值<=500
public class Code0041_MinCostToYeahArray {

	public static final int INVALID = Integer.MAX_VALUE;

	// 纯暴力方法，只是为了结果对
	// 时间复杂度极差
	public static int minCost0(int[] arr) {
		if (arr == null || arr.length < 3) {
			return 0;
		}
		int n = arr.length;
		int min = INVALID;
		for (int num : arr) {
			min = Math.min(min, num);
		}
		int base = min - n;
		return process0(arr, base, 0);
	}

	public static int process0(int[] arr, int base, int index) {
		if (index == arr.length) {
			for (int i = 1; i < arr.length - 1; i++) {
				if (arr[i - 1] <= arr[i] && arr[i] >= arr[i + 1]) {
					return INVALID;
				}
			}
			return 0;
		} else {
			int ans = INVALID;
			int tmp = arr[index];
			for (int cost = 0; arr[index] >= base; cost++, arr[index]--) {
				int next = process0(arr, base, index + 1);
				if (next != INVALID) {
					ans = Math.min(ans, cost + next);
				}
			}
			arr[index] = tmp;
			return ans;
		}
	}

	static final int MAX=Integer.MAX_VALUE;
	//最小值min，n个数，降n-1次，降完最小值为min-(n-1)。源数组全部减去这个数，那么最低不需要降到0以下。
	public static int minCost1(int[] arr) {
		int min=MAX,n=arr.length;
		if (n<3) return 0;
		for (int num:arr) min = Math.min(min, num);
		for (int i = 0; i < arr.length; i++) {
			arr[i]-=min-(n-1);
		}
		return f(arr,1,arr[0], true);
	}

	private static int f(int[] arr, int index, int pre, boolean valid) {
		if (index==arr.length-1){
			return (valid||arr[index]>pre)?0:MAX;
		}
		int p1=MAX,p2=MAX;
		if (valid){
			for (int i=arr[index],j=0;i>=0;i--,j++){
				int next=f(arr,index+1,i,i<pre);
				if (next!=MAX) p1 = Math.min(p1,j+next);
			}
		}else {
			for (int i=arr[index],j=0;i>=pre+1;i--,j++){
				int next=f(arr,index+1,i,i<pre);
				if (next!=MAX) p2 = Math.min(p2,j+next);
			}
		}
		return Math.min(p1,p2);
	}

	public static int minCost2(int[] arr) {
		int min=MAX,n=arr.length;
		if (n<3) return 0;
		for (int num:arr) min = Math.min(min, num);
		for (int i = 0; i < arr.length; i++) {
			arr[i]-=min-(n-1);
		}
		int[][][] dp=new int[n][2][];
		for (int i = 1; i < n; i++) {
			for (int j = 0; j <= 1; j++) {
				dp[i][j]=new int[arr[i-1]+1];
				Arrays.fill(dp[i][j],MAX);
			}
		}
		for (int pre=arr[n-2];pre>=0;pre--){
			dp[n-1][1][pre]=0;
			dp[n-1][0][pre]=arr[n-1]>pre?0:MAX;
		}
		for (int index=arr.length-2;index>=1;index--){
			for (int pre=arr[index-1];pre>=0;pre--){
				for (int i=arr[index],j=0;i>=0;i--,j++){
					int next=dp[index+1][i<pre?1:0][i];
					if (next!=MAX) dp[index][1][pre] = Math.min(dp[index][1][pre],j+next);
				}
				for (int i=arr[index],j=0;i>=pre+1;i--,j++){
					int next=dp[index+1][0][i];
					if (next!=MAX) dp[index][0][pre] = Math.min(dp[index][0][pre],j+next);
				}
			}
		}
		return dp[1][1][arr[0]];
	}


	public static int minCost3(int[] arr) {
		int min=MAX,n=arr.length;
		if (n<3) return 0;
		for (int num:arr) min = Math.min(min, num);
		for (int i = 0; i < arr.length; i++) {
			arr[i]-=min-(n-1);
		}
		int[][][] dp=new int[n][2][];
		for (int i = 1; i < n; i++) {
			for (int j = 0; j <= 1; j++) {
				dp[i][j]=new int[arr[i-1]+1];
				Arrays.fill(dp[i][j],MAX);
			}
		}
		for (int pre=arr[n-2];pre>=0;pre--){
			dp[n-1][1][pre]=0;
			dp[n-1][0][pre]=arr[n-1]>pre?0:MAX;
		}
		for (int index=arr.length-2;index>=1;index--){
			int[][] best=new int[2][arr[index]+1];
			for (int[] a:best) Arrays.fill(a,MAX);
			for (int f=arr[index],t=0;f>=0;f--,t++){
				if (dp[index+1][0][f]!=MAX){
					best[0][f] = Math.min(f+1<=arr[index]?best[0][f+1]:MAX,dp[index+1][0][f]+t);
				}
				if (dp[index+1][1][t]!=MAX){
					best[1][t] = Math.min(t-1>=0?best[1][t-1]:MAX,dp[index+1][1][t]+f);
				}
			}
			for (int pre=arr[index-1];pre>=0;pre--){
				if (arr[index] < pre) {
					dp[index][1][pre] = best[1][arr[index]];
				} else {
					dp[index][1][pre] = Math.min(best[0][pre], pre > 0 ? best[1][pre - 1] : MAX);
				}
				dp[index][0][pre] = arr[index] <= pre ? MAX : best[0][pre + 1];
			}
		}
		return dp[1][1][arr[0]];
	}


	// 最终的最优解，贪心
	// 时间复杂度O(N)
	// 请注意，重点看上面的方法
	// 这个最优解容易理解，但让你学到的东西不是很多
	public static int yeah(int[] arr) {
		if (arr == null || arr.length < 3) {
			return 0;
		}
		int n = arr.length;
		int[] nums = new int[n + 2];
		nums[0] = Integer.MAX_VALUE;
		nums[n + 1] = Integer.MAX_VALUE;
		for (int i = 0; i < arr.length; i++) {
			nums[i + 1] = arr[i];
		}
		int[] leftCost = new int[n + 2];
		int pre = nums[0];
		int change = 0;
		for (int i = 1; i <= n; i++) {
			change = Math.min(pre - 1, nums[i]);
			leftCost[i] = nums[i] - change + leftCost[i - 1];
			pre = change;
		}
		int[] rightCost = new int[n + 2];
		pre = nums[n + 1];
		for (int i = n; i >= 1; i--) {
			change = Math.min(pre - 1, nums[i]);
			rightCost[i] = nums[i] - change + rightCost[i + 1];
			pre = change;
		}
		int ans = Integer.MAX_VALUE;
		for (int i = 1; i <= n; i++) {
			ans = Math.min(ans, leftCost[i] + rightCost[i + 1]);
		}
		return ans;
	}

	// 为了测试
	public static int[] randomArray(int len, int v) {
		int[] arr = new int[len];
		for (int i = 0; i < len; i++) {
			arr[i] = (int) (Math.random() * v) + 1;
		}
		return arr;
	}

	// 为了测试
	public static int[] copyArray(int[] arr) {
		int[] ans = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			ans[i] = arr[i];
		}
		return ans;
	}

	// 为了测试
	public static void main(String[] args) {
		int len = 7;
		int v = 10;
		int testTime = 100;
		System.out.println("==========");
		System.out.println("功能测试开始");
		int cnt=0;
		for (int i = 0; i < testTime; i++) {
			int n = (int) (Math.random() * len) + 1;
			int[] arr = randomArray(n, v);
			int[] arr0 = copyArray(arr);
			int[] arr1 = copyArray(arr);
			int[] arr2 = copyArray(arr);
			int[] arr3 = copyArray(arr);
			int[] arr4 = copyArray(arr);
			int ans0 = minCost0(arr0);
			int ans1 = minCost1(arr1);
			int ans2 = minCost2(arr2);
			int ans3 = minCost3(arr3);
			int ans4 = yeah(arr4);
			if (ans0 != ans1 || ans0 != ans2 || ans0 != ans3 || ans0 != ans4) {
				System.out.println("出错了！"+(cnt++));
			}
		}
		System.out.println("功能测试结束");
		System.out.println("==========");

		System.out.println("性能测试开始");

		len = 10000;
		v = 500;
		System.out.println("生成随机数组长度：" + len);
		System.out.println("生成随机数组值的范围：[1, " + v + "]");
		int[] arr = randomArray(len, v);
		int[] arr3 = copyArray(arr);
		int[] arrYeah = copyArray(arr);
		long start;
		long end;
		start = System.currentTimeMillis();
		int ans3 = minCost3(arr3);
		end = System.currentTimeMillis();
		System.out.println("minCost3方法:");
		System.out.println("运行结果: " + ans3 + ", 时间(毫秒) : " + (end - start));

		start = System.currentTimeMillis();
		int ansYeah = yeah(arrYeah);
		end = System.currentTimeMillis();
		System.out.println("yeah方法:");
		System.out.println("运行结果: " + ansYeah + ", 时间(毫秒) : " + (end - start));

		System.out.println("性能测试结束");
		System.out.println("==========");

	}

}
