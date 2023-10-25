package ClassicInterviewCoding.InterviewCoding20;

import java.util.Arrays;

public class Code03_PostOfficeProblem {

	/**
	 * dp[i][j]代表0~i的居民点刚好建够j个邮局，最短路径和
	 * j>=i+1的格子全部都是0。只要初始化第二列
	 * @param arr 居民点有arr.len个，具体在哪arr[i]
	 * @param num 邮局的数量
	 * @return 返回将num个邮局建立在最合适的位置，最短路径累加和返回
	 */
	public static int minDis1(int[] arr, int num) {
		if (arr==null||num<1||arr.length<=num) return 0;
		int N=arr.length;
		int[][] dp=new int[N][num+1];
		int[][] record = getRecord(arr);
		for (int i = 0; i < N; i++) {//第二列
			dp[i][1]=record[0][i];
		}
		for (int i = 2; i < N; i++) {
			for (int j = 2;j<=num ; j++) {
				if (j>=i+1) continue;
				dp[i][j]=record[0][i];//最后一个邮局全责0~i的全部
				for (int k = 1; k <= i; k++) {//最后一个邮局全责k~i的全部
					dp[i][j] = Math.min(dp[i][j],dp[k-1][j-1]+record[k][i]);
				}
			}
		}
		return dp[N-1][num];
	}

	/**
	 * 做四边形不等式优化。每个点都从上面和右边获得一个边界。
	 * 画个图，把choose和dp的初始化一起做了。根据我们的初始化，dp最右边的格子有一部分是没有choose的，自己判断，设置为limit为i。
	 */
	public static int minDis2(int[] arr, int num) {
		if (arr==null||num<1||arr.length<=num) return 0;
		int N=arr.length;
		int[][] dp=new int[N][num+1];
		int[][] record = getRecord(arr);
		int[][] choose = new int[N][num+1];//最优的时候是从k~i,返回这个k的值
		for (int i = 0; i < N; i++) {//第二列
			dp[i][1]=record[0][i];
			choose[i][1]=i>>1;//只有一个邮局的时候肯定在终点位置最好
		}
		for (int i = 2; i < N; i++) {
			for (int j = num;j>=2 ; j--) {
				if (j>=i+1) {
					choose[i][j]=i;
					continue;
				}
				int limit=j+1<=num?choose[i][j+1]:i;
				int bottom=choose[i-1][j];
				dp[i][j]=record[0][i];//最后一个邮局全责0~i的全部
				for (int k = Math.max(bottom, 1); k <= limit; k++) {//最后一个邮局全责k~i的全部
					if (dp[k-1][j-1]+record[k][i]<dp[i][j]){
						dp[i][j]=dp[k-1][j-1]+record[k][i];
						choose[i][j]=k;
					}
				}
			}
		}
		return dp[N-1][num];
	}

	public static int[][] getRecord(int[] arr) {
		int N = arr.length;
		int[][] record = new int[N][N];
		for (int L = 0; L < N; L++) {
			for (int R = L + 1; R < N; R++) {
				record[L][R] = record[L][R - 1] + arr[R] - arr[(L + R) >> 1];
			}
		}
		return record;
	}


	// for test
	public static int[] getSortedArray(int len, int range) {
		int[] arr = new int[len];
		for (int i = 0; i != len; i++) {
			arr[i] = (int) (Math.random() * range);
		}
		Arrays.sort(arr);
		return arr;
	}

	// for test
	public static void printArray(int[] arr) {
		for (int i = 0; i != arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	// for test
	public static void main(String[] args) {
		int[] arr = { 1,3,8,10,12 };
		int num = 3;
		System.out.println(minDis1(arr, num));
		System.out.println(minDis2(arr, num));

		int times = 100; // test time
		int len = 1000; // test array length
		int range = 2000; // every number in [0,range)
		int p = 50; // post office number max
		long time1 = 0; // method1 all run time
		long time2 = 0;// method2 all run time
		long time3 = 0;
		long start = 0;
		long end = 0;
		int res1 = 0;
		int res2 = 0;
		int res3 = 0;
		for (int i = 0; i != times; i++) {
			int office = (int) (Math.random() * p) + 1;
			arr = getSortedArray(len, range);
			start = System.currentTimeMillis();
			res1 = minDis1(arr, office);
			end = System.currentTimeMillis();
			time1 += end - start;
			start = System.currentTimeMillis();
			res2 = minDis2(arr, office);
			end = System.currentTimeMillis();
			time2 += end - start;

			start = System.currentTimeMillis();
			res3 = minDis1(arr, office);
			end = System.currentTimeMillis();
			time3 += end - start;
			if (res1 != res2 || res1 != res3) {
				printArray(arr);
				break;
			}
			if (i % 10 == 0) {
				System.out.print(". ");
			}
		}
		System.out.println();
		System.out.println("method1 all run time(ms): " + time1);
		System.out.println("method2 all run time(ms): " + time2);
		System.out.println("method3 all run time(ms): " + time3);

	}

}
