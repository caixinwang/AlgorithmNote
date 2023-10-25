package ClassicInterviewCoding.InterviewCoding03;

public class Code02_SnacksWays {

	public static int ways1(int[] arr, int w) {
		// arr[0...]
		return process2(arr, 0, w);
//		return process(arr,0.w);
	}

	/** 递归前判断合法性
	 * 如果(rest-arr[index])<0不放在p2的话，那么就需要多写一个base case返回-1，代表背包容量溢出
	 * @param arr 零食
	 * @param index 0~index-1 已经选择过了，现在是index~len-1来选择
	 * @param rest 还剩下rest的空间
	 * @return 方法数
	 */
	public static int process(int[] arr, int index, int rest) {
		if (index==arr.length) return 1;
		int p1=process(arr,index+1,rest);
		int p2=(rest-arr[index])<0?0:process(arr,index+1,rest-arr[index]);
		return p1+p2;
	}

	//process的另一种写法，递归后才判断合不合法
	public static int process2(int[] arr, int index, int rest) {
		if (rest<0) return -1;//不合法
		if (index==arr.length) return 1;
		int p1=process(arr,index+1,rest);
		int p2=process(arr,index+1,rest-arr[index]);
		return p1+(p2==-1?0:p2);//要记得加括号
	}


	public static int ways2(int[] arr, int w) {//将上面递归改成动态规划，这是通过递归改的，dp没有定义含义
		int N = arr.length;
		int[][] dp = new int[N + 1][w + 1];
		for (int j = 0; j <= w; j++) {
			dp[N][j] = 1;
		}
		for (int i = N - 1; i >= 0; i--) {
			for (int j = 0; j <= w; j++) {
				dp[i][j] = dp[i + 1][j] + (j - arr[i] >= 0? dp[i + 1][j - arr[i]] : 0);//要把三元运算符括起来
			}
		}
		return dp[0][w];
	}

	/**
	 * dp [ i ] [ j ] 代表0~i自由选择，刚好凑成 j 的的方法数。最终结果就是sum{dp [N-1] [0...j]}
	 * @param arr 零食
	 * @param bag 背包大小
	 * @return bag大小的背包，背包里面不一定装满，有多少种选零食的方案
	 */
	public static int ways3(int[] arr, int bag) {
		int N = arr.length;
		int[][] dp = new int[N][bag + 1];
		for (int i = 0; i < N; i++) {//刚好凑成0，都全不选 为一种凑法
			dp[i][0] = 1;
		}
		if (arr[0] <= bag) {//只有一个物品要凑成，背包要刚好等于物品的大小。只有在arr[0]<=w才有一个等于1，否则都是0
			dp[0][arr[0]] = 1;
		}
		for (int i = 1; i < N; i++) {
			for (int j = 1; j <= bag; j++) {
				dp[i][j] = dp[i - 1][j] + ((j - arr[i]) >= 0 ? dp[i - 1][j - arr[i]] : 0);
			}
		}
		int ans = 0;
		for (int j = 0; j <= bag; j++) {
			ans += dp[N - 1][j];
		}
		return ans;
	}


	public static void main(String[] args) {
		int[] arr = { 4, 3, 2, 9 ,7,12,4,6,7 };
		int w = 20;
		System.out.println(ways1(arr, w));
		System.out.println(ways2(arr, w));
		System.out.println(ways3(arr, w));

	}

}
