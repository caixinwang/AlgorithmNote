package ClassicInterviewCoding.InterviewCoding15;

import java.util.HashSet;
import java.util.TreeSet;
/*
 * 给定一个非负数组arr，和一个正数m。 返回arr的所有子序列中累加和%m之后的最大值。
 */
public class Code04_SubsquenceMaxModM {

	/**
	 * 暴力递归，从左往右的尝试模型，直接试出所有的累加和，然后一个一个去%m，抓住最大值
	 * 这种方法其实就是背包问题，就是一个arr给你，问你能凑出多少
	 * @param arr 非负arr
	 * @param m 模m
	 * @return 返回arr子序列累加和%m之后的最大值
	 */
	public static int max1(int[] arr, int m) {
		HashSet<Integer> set=new HashSet<>();
		process(arr,0,0,set);//填充set
		int max=0;
		for (Integer integer : set) {//抓最大
			max = Math.max(max, integer%m);
		}
		return max;
	}

	public static void process(int[] arr, int index, int sum, HashSet<Integer> set) {
		if (index==arr.length){
			set.add(sum);//说明所有可能选法都尝试了
		}else {
			process(arr,index+1,sum,set);//不选
			process(arr,index+1,sum+arr[index],set);
		}
	}

	/**
	 * 憋背包问题的动态规划。
	 * dp[i][j]代表0~i自由选择否能能刚好凑出j。最后遍历dp表的最后一行得到答案
	 * dp表的列数是所有数的累加和，arr的sum不是很大的时候选它
	 * dp[i][j]=dp[i-1][j]||dp[i-1][j-arr[i]]
	 */
	public static int max2(int[] arr, int m) {
		int sum=0;
		for (int i : arr) sum+=i;
		int N=arr.length;
		boolean[][]dp=new boolean[N][sum+1];
		//dp[0][i]
		dp[0][arr[0]]=true;
		//dp[i][0] - 子序列，什么都不选就是0
		for (int i = 0; i < N; i++) {
			dp[i][0]=true;
		}
		for (int i = 1; i <N ; i++) {
			for (int j = 1; j <= sum; j++) {
				dp[i][j]=dp[i-1][j]||(j - arr[i] >= 0 && dp[i - 1][j - arr[i]]);
			}
		}
		int max=0;
		for (int i = 0; i <= sum; i++) {
			if (dp[N-1][i]) max = Math.max(max,i%m);
		}
		return max;
	}

	/**
	 * dp[i][j]定义为0~i自由选择之后的sum%m,能否严格凑出j
	 * 对max2进行改进，如果sum很大，那么不妨用m。显然m一定要大于sum，不然我们直接写一个过滤器reutrn sum就行了
	 */
	public static int max3(int[] arr, int m) {
		int N=arr.length;
		boolean[][] dp=new boolean[N][m];
		dp[0][0]=true;
		//dp[0][i]
		dp[0][arr[0]%m]=true;
		//dp[i][0]
		for (int i=1;i<N;i++){
			dp[i][0]=true;
		}
		for (int i = 1; i < N; i++) {
			for (int j = 1; j < m; j++) {
				int c=arr[i]%m;
				dp[i][j]=dp[i-1][j]||(j-c>=0?dp[i-1][j-c]:dp[i-1][j-c+m]);
			}
		}
		int max=0;
		for (int i = 0; i < m; i++) {
			if (dp[N-1][i]) max=i;
		}
		return max;
	}

	/**
	 * 这种数据状况下前面两种动态规划就不适用了。我们就直接暴力枚举所有的累计和，然后直接求
	 * 但是暴力枚举和方法1不一样，我们这里分治了
	 * @param arr arr的长度比较短
	 * @param m m很大
	 */
	public static int max4(int[] arr, int m) {
		if (arr.length==1) return arr[0]%m;
		int mid= arr.length-1>>1;
		TreeSet<Integer> set1=new TreeSet<>();//因为我们需要拿到离m-c最近的
		TreeSet<Integer> set2=new TreeSet<>();
		process4(arr,0,0,mid,m,set1);
		process4(arr,mid+1,0,arr.length-1,m,set2);
		int max=0;
		for (Integer num:set1){
			max = Math.max(max, num+set2.floor(m-1-num));//最大的是m-1
		}
		return max;
	}

	// 从index出发，最后有边界是end+1，arr[index...end]
	public static void process4(int[] arr, int index, int sum, int end, int m, TreeSet<Integer> sortSet) {
		if (index==end+1){
			sortSet.add(sum%m);
		}else {
			process4(arr,index+1,(sum+arr[index])%m,end,m,sortSet);
			process4(arr,index+1,sum,end,m,sortSet);
		}
	}

	public static int[] generateRandomArray(int len, int value) {
		int[] ans = new int[(int) (Math.random() * len) + 1];
		for (int i = 0; i < ans.length; i++) {
			ans[i] = (int) (Math.random() * value);
		}
		return ans;
	}

	public static void main(String[] args) {
		int len = 10;
		int value = 100;
		int m = 76;
		int testTime = 500;
		System.out.println("test begin");
		for (int i = 0; i < testTime; i++) {
			int[] arr = generateRandomArray(len, value);
			int ans1 = max1(arr, m);
			int ans2 = max2(arr, m);
			int ans3 = max3(arr, m);
			int ans4 = max4(arr, m);
			if (ans1 != ans2 || ans2 != ans3 || ans3 != ans4) {
				System.out.print("Oops!");
			}
		}
		System.out.println("test finish!");

	}

}
