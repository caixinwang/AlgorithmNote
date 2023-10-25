package ClassicInterviewCoding.InterviewCoding16;

public class Code05_MinimumCostToMergeStones {

	/**
	 * 把arr数组中所有数只能相邻k个合并，要求最终合成1份，问最小的代价是多少
	 */
	public static int mergeStones1(int[] arr, int k) {
		if (arr==null||arr.length==0||k<2) return -1;
		int N=arr.length;
		if ((N-1)%(k-1)!=0) return -1;
		int[] sum=new int[N];
		sum[0]=arr[0];
		for (int i = 1; i < N; i++) {
			sum[i]=sum[i-1]+arr[i];
		}
		return process1(0,N-1,1,arr,k,sum);
	}

	/**
	 * arr[l...r]范围上只能相邻k个合，问你最终合并成part份的最小代价是多少
	 * 可能性划分：part为1与part不为1。
	 * 注意，下面for循环中，如果你是i+=k-1,那么在上面part==1的分支中就不需要加if ((r-l)%(k-1)!=0) return -1;
	 * 但是如果你是i++，那么就需要在分支中加上这句判断。原因就是如果你不控制分支的进入，你就要自己加上判断语句，将
	 * 错误的分支筛除。
	 */
	private static int process1(int l, int r, int part, int[] arr, int k,int[] sum) {
		if (l==r) return part==1?0:-1;//base case，l==r的时候只有合成一份的时候可以，并且是不需要代价的。
		if (part==1){
			if ((r-l)%(k-1)!=0) return -1;//下面是i++就需要这句，如果是i+=k-1就不需要这句
			int p1 = process1(l, r, k, arr, k, sum);
			if (p1==-1) return -1;
			return p1+sum[r]-(l-1>=0?sum[l-1]:0);
		}
		//part>1
		int min=Integer.MAX_VALUE;
		for (int i = l; i <r; i++) {//[l,i]单独作为一份，[i+1,r]去搞出part-1份
			int p1=process1(l,i,1,arr,k,sum);
			int p2=process1(i+1,r,part-1,arr,k,sum);
			if (p1!=-1&&p2!=-1) min = Math.min(min, p1+p2);
		}
		return min;
	}

	public static int mergeStones2(int[] stones, int K) {
		int n = stones.length;
		if ((n - 1) % (K - 1) > 0) {
			return -1;
		}
		int[] presum = new int[n + 1];
		for (int i = 0; i < n; i++) {
			presum[i + 1] = presum[i] + stones[i];
		}
		int[][][] dp = new int[n][n][K + 1];
		return process2(0, n - 1, 1, stones, K, presum, dp);
	}

	public static int process2(int i, int j, int part, int[] arr, int K, int[] presum, int[][][] dp) {
		if (dp[i][j][part] != 0) {
			return dp[i][j][part];
		}
		if (i == j) {
			return part == 1 ? 0 : -1;
		}
		if (part == 1) {
			int next = process2(i, j, K, arr, K, presum, dp);
			if (next == -1) {
				dp[i][j][part] = -1;
				return -1;
			} else {
				dp[i][j][part] = next + presum[j + 1] - presum[i];
				return next + presum[j + 1] - presum[i];
			}
		} else {
			int ans = Integer.MAX_VALUE;
			// i...mid是第1块，剩下的是part-1块
			for (int mid = i; mid < j; mid += K - 1) {
				int next1 = process2(i, mid, 1, arr, K, presum, dp);
				int next2 = process2(mid + 1, j, part - 1, arr, K, presum, dp);
				if (next1 == -1 || next2 == -1) {
					dp[i][j][part] = -1;
					return -1;
				} else {
					ans = Math.min(ans, next1 + next2);
				}
			}
			dp[i][j][part] = ans;
			return ans;
		}
	}

	// for test
	public static int[] generateRandomArray(int maxSize, int maxValue) {
		int[] arr = new int[(int) (maxSize * Math.random()) + 1];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) ((maxValue + 1) * Math.random());
		}
		return arr;
	}

	// for test
	public static void printArray(int[] arr) {
		if (arr == null) {
			return;
		}
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	public static void main(String[] args) {
		int maxSize = 12;
		int maxValue = 100;
		System.out.println("Test begin");
		for (int testTime = 0; testTime < 10000; testTime++) {
			int[] arr = generateRandomArray(maxSize, maxValue);
			int K = (int) (Math.random() * 7) + 2;
			int ans1 = mergeStones1(arr, K);
			int ans2 = mergeStones2(arr, K);
			if (ans1 != ans2) {
				System.out.println(ans1);
				System.out.println(ans2);
			}
		}

	}

}
