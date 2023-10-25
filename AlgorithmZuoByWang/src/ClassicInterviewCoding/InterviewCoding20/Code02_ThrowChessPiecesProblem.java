package ClassicInterviewCoding.InterviewCoding20;

import TestUtils.ArrayUtil;

public class Code02_ThrowChessPiecesProblem {

	public static int solution1(int nLevel, int kChess) {
		if (nLevel<1||kChess<1) return 0;
		return process1(nLevel,kChess);
	}

	/**
	 * 因为运气很差，所以本题就是在所有的最坏情况中取最小的。你可以到1~rest中的任何一层i扔。
	 * i如果碎了就还剩i-1层要验证。i没碎就去上面去验证
	 * @param rest 还剩多少层楼需要去验证
	 * @param k 还有多少颗棋子能够使用
	 * @return 一定要验证出最高的不会碎的楼层,但是每次都是坏运气,返回至少需要扔几次
	 */
	public static int process1(int rest, int k) {
		if (rest==0) return 0;
		if (k==1) return rest;
		int min=Integer.MAX_VALUE;
		for (int i = 1; i <= rest  ; i++) {
			min = Math.min(min, Math.max(1+process1(rest-i,k),1+process1(i-1,k-1)));
		}
		return min;
	}

	public static int solution2(int nLevel, int kChess) {//直接暴力递归改动态规划
		if (nLevel<1||kChess<1) return 0;
		int[][] dp=new int[nLevel+1][kChess+1];
		//dp[0][j]=0
		//第一列无意义
		for (int i = 0; i <= nLevel; i++) {//第二列
			dp[i][1]=i;
		}
		for (int rest = 1; rest <= nLevel; rest++) {
			for (int k = 2; k <= kChess; k++) {
				int min=Integer.MAX_VALUE;
				for (int i = 1; i <= rest  ; i++) {
					min = Math.min(min, Math.max(1+dp[rest-i][k],1+dp[i-1][k-1]));
				}
				dp[rest][k]= min;
			}
		}
		return dp[nLevel][kChess];
	}

	public static int solution3(int nLevel, int kChess) {//空间压缩
		if (nLevel < 1 || kChess < 1) {
			return 0;
		}
		if (kChess == 1) {
			return nLevel;
		}
		int[] preArr = new int[nLevel + 1];
		int[] curArr = new int[nLevel + 1];
		for (int i = 1; i != curArr.length; i++) {
			curArr[i] = i;
		}
		for (int i = 1; i != kChess; i++) {
			int[] tmp = preArr;
			preArr = curArr;
			curArr = tmp;
			for (int j = 1; j != curArr.length; j++) {
				int min = Integer.MAX_VALUE;
				for (int k = 1; k != j + 1; k++) {
					min = Math.min(min, Math.max(preArr[k - 1], curArr[j - k]));
				}
				curArr[j] = min + 1;
			}
		}
		return curArr[curArr.length - 1];
	}

	static ArrayUtil arrayUtil=new ArrayUtil();

	public static int solution4(int nLevel, int kChess) {
		if (nLevel<1||kChess<1) return 0;
		int[][] dp=new int[nLevel+1][kChess+1];
		int[][] best=new int[nLevel+1][kChess+1];
		for (int i = 0; i < best.length; i++) {
			for (int j = 0; j < best[0].length; j++) {
				best[i][j]=-1;
			}
		}
		//dp[0][j]=0
		//第一列无意义
		for (int i = 0; i <= nLevel; i++) {//第二列
			dp[i][1]=i;
//			best[i][1]=i;//加不加都可以跑对
		}
		for (int rest = 1; rest <= nLevel; rest++) {
			for (int k = kChess; k >=2; k--) {//从右边开始填，因为上限是从右边拿的。120ms,如果从左开始填，拿不到上限，800ms
				int min=Integer.MAX_VALUE;
				int bottom=best[rest-1][k]!=-1?best[rest-1][k]:1;//上小拿bottom
				int limit=(k+1<=kChess&&best[rest][k+1]!=-1)?best[rest][k+1]:rest;//右大拿limit
				for (int i = bottom; i <= limit  ; i++) {
					if (Math.max(1+dp[rest-i][k],1+dp[i-1][k-1])<=min){
						min=Math.max(1+dp[rest-i][k],1+dp[i-1][k-1]);
						best[rest][k]=i;
					}
				}
				dp[rest][k]= min;
			}
		}
//		arrayUtil.printMatrix(best);
		return dp[nLevel][kChess];
	}

	public static int solution5(int nLevel, int kChess) {
		if (nLevel < 1 || kChess < 1) {
			return 0;
		}
		int bsTimes = log2N(nLevel) + 1;
		if (kChess >= bsTimes) {
			return bsTimes;
		}
		int[] dp = new int[kChess];
		int res = 0;
		while (true) {
			res++;
			int previous = 0;
			for (int i = 0; i < dp.length; i++) {
				int tmp = dp[i];
				dp[i] = dp[i] + previous + 1;
				previous = tmp;
				if (dp[i] >= nLevel) {
					return res;
				}
			}
		}
	}

	public static int log2N(int n) {
		int res = -1;
		while (n != 0) {
			res++;
			n >>>= 1;
		}
		return res;
	}

	public static void main(String[] args) {
		System.out.println(solution1(21, 2));
		System.out.println(solution2(21, 2));
		System.out.println(solution3(21, 2));
		System.out.println(solution4(21, 2));
		System.out.println(solution5(21, 2));

		System.out.println("==============");

		System.out.println(solution2(105, 2));
		System.out.println(solution3(105, 2));
		System.out.println(solution4(105, 2));
		System.out.println(solution5(105, 2));

		System.out.println("==============");

		System.out.println(solution2(3000, 10));
		System.out.println(solution3(3000, 10));
		System.out.println(solution4(3000, 10));
		System.out.println(solution5(3000, 10));

		System.out.println("==============");

		System.out.println(solution2(6884, 5));
		System.out.println(solution3(6884, 5));
		System.out.println(solution4(6884, 5));
		System.out.println(solution5(6884, 5));

		System.out.println("==============");

		System.out.println(solution2(6885, 5));
		System.out.println(solution3(6885, 5));
		System.out.println(solution4(6885, 5));
		System.out.println(solution5(6885, 5));

		System.out.println("==============");

		int nLevel = 10000;
		int kChess = 10;
		long start = System.currentTimeMillis();
		System.out.println(solution3(nLevel, kChess));
		long end = System.currentTimeMillis();
		System.out.println("cost time: " + (end - start) + " ms");

	}

}
