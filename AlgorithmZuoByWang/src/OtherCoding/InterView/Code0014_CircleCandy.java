package OtherCoding.InterView;

import TestUtils.ArrayUtil;

// 来自网易
// 给定一个正数数组arr，表示每个小朋友的得分
// 任何两个相邻的小朋友，如果得分一样，怎么分糖果无所谓，但如果得分不一样，分数大的一定要比分数少的多拿一些糖果
// 假设所有的小朋友坐成一个环形，返回在不破坏上一条规则的情况下，需要的最少糖果数
public class Code0014_CircleCandy {

	public static int minCandy(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		if (arr.length == 1) {
			return 1;
		}
		int n = arr.length;
		int minIndex = 0;
		for (int i = 0; i < n; i++) {
			if (arr[i] <= arr[lastIndex(i, n)] && arr[i] <= arr[nextIndex(i, n)]) {
				minIndex = i;
				break;
			}
		}
		int[] nums = new int[n + 1];
		for (int i = 0; i <= n; i++, minIndex = nextIndex(minIndex, n)) {
			nums[i] = arr[minIndex];
		}
		int[] left = new int[n + 1];
		left[0] = 1;
		for (int i = 1; i <= n; i++) {
			left[i] = nums[i] > nums[i - 1] ? (left[i - 1] + 1) : 1;
		}
		int[] right = new int[n + 1];
		right[n] = 1;
		for (int i = n - 1; i >= 0; i--) {
			right[i] = nums[i] > nums[i + 1] ? (right[i + 1] + 1) : 1;
		}
		int ans = 0;
		for (int i = 0; i < n; i++) {
			ans += Math.max(left[i], right[i]);
		}
		return ans;
	}

	public static int nextIndex(int i, int n) {
		return i == n - 1 ? 0 : (i + 1);
	}

	public static int lastIndex(int i, int n) {
		return i == 0 ? (n - 1) : (i - 1);
	}

	public static int minCandy2(int[] arr) {//我的做法：从最小的下标开始，顺着绕一圈，逆着绕一圈
		if (arr == null || arr.length == 0) return 0;
		if (arr.length == 1) return 1;
		int N=arr.length;
		int start=0;
		int min=arr[0];
		for (int i = 0; i < arr.length; i++) {
			if (arr[i]<min){
				start=i;
				min=arr[i];
			}
		}
		int[] left=new int[N];
		int[] right=new int[N];
		left[start]=right[start]=1;
		for (int i=nextIndex(start,N);i!=start;i=nextIndex(i,N)){
			int preIndex=lastIndex(i,N);
			left[i]=arr[i]>arr[preIndex]?left[preIndex]+1:1;
		}
		for (int i=lastIndex(start,N);i!=start;i=lastIndex(i,N)){
			int preIndex=nextIndex(i,N);
			right[i]=arr[i]>arr[preIndex]?right[preIndex]+1:1;
		}
		int ans=0;
		for (int i = 0; i < left.length; i++) {
			ans+=Math.max(left[i],right[i]);
		}
		return ans;
	}

	static ArrayUtil au=new ArrayUtil();
	public static void main(String[] args) {
		int times=1000;
		for (int i = 0; i < times; i++) {
			int[] arr=au.generateRandomArr(au.ran(1,100),1,100);
			int ans1=minCandy(au.copyArray(arr));
			int ans2=minCandy2(au.copyArray(arr));
			if (ans1!=ans2){
				System.out.println("opps");
			}
		}
		System.out.println("ok");
	}

}
