package ClassicInterviewCoding.InterviewCoding09;

import TestUtils.ArrayUtil;

public class Code01_LongestIncreasingPath {

	/**
	 * 先写出一个递归，求出从某一个位置(i,j)出发的最长增长链。
	 * 然后在主函数里面枚举矩阵中的每一个位置，都去过一遍递归求出最长增长链，答案必在其中
	 * @param matrix 求出matrix最长增长链的长度
	 * @return 返回最长增长链的长度
	 */
	public static int maxPath(int[][] matrix) {
		if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
			return 0;
		}
		int res=0,N=matrix.length,M=matrix[0].length;
		int[][] dp=new int[N][M];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				dp[i][j]=-1;
			}
		}
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				res = Math.max(res, process(matrix,i,j,dp));
			}
		}
		return res;
	}

	// 假设在matrix中，从i行，j列出发，能走出的最长递增路径，返回最长递增路径的长度

	/**
	 * 递归程序，每个位置是否进上下左右的递归取决于，上下左右的这几个位置是不是大于当前的位置。
	 * 虽然加上记忆化搜索在我们的第一个递归里面没有什么用，但是由于主程序里面要调用N * M次
	 * 所以加上记忆化搜索可以使得后面的递归函数的时间效率变为O(1)
	 * 记忆化搜索的初始化值为process中得不到的值即可
	 * @param matrix 求出从matrix的(i,j)位置出发最长增长链的长度
	 * @param i 下标位置 主函数调用保证不越界
	 * @param j 下标位置
 	 * @return 返回从matrix的(i,j)位置出发最长增长链的长度
	 */
	public static int process(int[][] matrix, int i, int j,int[][] dp) {
		if (dp[i][j]!=-1) return dp[i][j];
		int up=i-1>=0&&matrix[i-1][j]>matrix[i][j]?process(matrix,i-1,j,dp):0;
		int down=i+1<matrix.length&&matrix[i+1][j]>matrix[i][j]?process(matrix,i+1,j,dp):0;
		int left=j-1>=0&&matrix[i][j-1]>matrix[i][j]?process(matrix,i,j-1,dp):0;
		int right=j+1<matrix[0].length&&matrix[i][j+1]>matrix[i][j]?process(matrix,i,j+1,dp):0;
		dp[i][j]=1+Math.max(Math.max(up,down),Math.max(left,right));//要加上1-自己，最大的元素的长度就是1+0
		return dp[i][j];
	}


	public static int longestIncreasingPath(int[][] m) {
		if (m == null || m.length == 0 || m[0].length == 0) {
			return 0;
		}
		int[][] dp = new int[m.length][m[0].length];
		// dp[i][j] (i,j)出发，走出的最长链长度
		int max = 0;
		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < m[0].length; j++) {
				// 每一个(i,j)位置出发，都尝试
				max = Math.max(max, maxIncrease(m, dp, i + 1, j, m[i][j]) + 1);
				max = Math.max(max, maxIncrease(m, dp, i, j + 1, m[i][j]) + 1);
				max = Math.max(max, maxIncrease(m, dp, i - 1, j, m[i][j]) + 1);
				max = Math.max(max, maxIncrease(m, dp, i, j - 1, m[i][j]) + 1);
			}

		}
		return max;
	}

	// 来到的当前位置是i,j位置
	// p 上一步值是什么
	// 从(i,j)位置出发，走出的最长链，要求：上一步是可以迈到当前步上的
	public static int maxIncrease(int[][] m, int[][] dp, int i, int j, int p) {
		if (i < 0 || i >= m.length || j < 0 || j >= m[0].length || m[i][j] <= p) {
			return 0;
		}
		if (dp[i][j] == 0) { // i,j 出发，当前没算过
			dp[i][j] = maxIncrease(m, dp, i + 1, j, m[i][j]) + 1;
			dp[i][j] = Math.max(dp[i][j], maxIncrease(m, dp, i, j + 1, m[i][j]) + 1);
			dp[i][j] = Math.max(dp[i][j], maxIncrease(m, dp, i - 1, j, m[i][j]) + 1);
			dp[i][j] = Math.max(dp[i][j], maxIncrease(m, dp, i, j - 1, m[i][j]) + 1);
		}
		return dp[i][j];
	}

	public static void testForMatrix(){//参数为arr
		ArrayUtil arrayUtil=new ArrayUtil();
		int times=1000;//测试次数
		boolean isok=true;
		int maxN=10;//matrix大小在[0~maxN][0~maxM]随机
		int maxM=10;//matrix大小在[0~maxN][0~maxM]随机
		int maxValue=10;//matrix的值在[0,maxValue]随机
//        int parameter1=0;//测试函数的参数
//        int maxParameter1=100;//参数1在[0,maxParameter1]随机
		int r=0;//测试函数的返回值
		int[][] f= arrayUtil.generateRandomMatrix(arrayUtil.ran(maxN), arrayUtil.ran(maxM), maxValue);
//        int[] f= arrayUtil.generateRandomArr(arrayUtil.ran(maxSize),1, maxValue);//正数数组[1,maxValue]
		int res1=0,res2=0;
		for (int i = 0; i < times; i++) {
//            parameter1=arrayUtil.ran(maxParameter1);
			int[][] t= arrayUtil.generateRandomMatrix(arrayUtil.ran(maxN), arrayUtil.ran(maxM), maxValue);
			f=t;
//            int[] t=arrayUtil.generateRandomArr(arrayUtil.ran(maxSize),1,maxValue);//正数数组[1,maxValue]
			res1=maxPath(t);
			res2=longestIncreasingPath(t);
			if (res1!=res2){
				isok=false;
				f=t;
				break;
			}
		}
		arrayUtil.printMatrix(f);//打印参数
//        System.out.println(parameter1);//打印参数
		System.out.println(res1);//针对返回值的操作
		System.out.println(res2);//针对返回值的操作
		System.out.println(isok?"success":"fail");
	}

	public static void main(String[] args) {
		testForMatrix();
	}

}
