package ClassicInterviewCoding.InterviewCoding10;

import TestUtils.ArrayUtil;

public class Code06_CherryPickup {

	/**
	 * 等价于两个人同时从(0,0)出发走到(N-1,M-1)所累积的和，两人同时经过的点也不重复计算
	 * @param matrix 返回matrix从(0,0)出发走到(N-1,M-1)再走回(0,0)所能达到的最大累加和，重复经过的点不重复计算
	 * @return 如上
	 */
	public static int comeGoMaxPathSum1(int[][] matrix){
		if (matrix==null||matrix.length==0||matrix[0]==null||matrix[0].length==0) return Integer.MIN_VALUE;
		return process1(matrix,0,0,0);
	}

	/**
	 * 限制：A和B同时走过的位置不能重复计算
	 * @param matrix 二维数组
	 * @param r1 A所在的行下标
	 * @param c1 A所在的列下标
	 * @param r2 B所在的行下标。c2=r1+c1-r2
	 * @return 返回A从(r1,c1)出发，B从(r2,c2)出发，所能达到的最大累加和
	 */
	private static int process1(int[][] matrix, int r1, int c1, int r2) {
		int N=matrix.length,M=matrix[0].length;//保证调用不会出异常，主函数调用已经写了过滤器
		if (r1==N-1&&c1==M-1) return matrix[r1][c1];//只返回一份-base case
		int c2=r1+c1-r2;
		int p1=r1+1<N&&r2+1<N?process1(matrix,r1+1,c1,r2+1):Integer.MIN_VALUE;
		int p2=c1+1<M&&c2+1<M?process1(matrix,r1,c1+1,r2):Integer.MIN_VALUE;
		int p3=r1+1<N&&c2+1<M?process1(matrix,r1+1,c1,r2):Integer.MIN_VALUE;
		int p4=c1+1<M&&r2+1<N?process1(matrix,r1,c1+1,r2+1):Integer.MIN_VALUE;
		int max=Math.max(Math.max(p1,p2),Math.max(p3,p4));
		return max+(r1==r2?matrix[r1][c1]:matrix[r1][c1]+matrix[r2][c2]);
	}

	public static int comeGoMaxPathSum2(int[][] matrix){
		if (matrix==null||matrix.length==0||matrix[0]==null||matrix[0].length==0) return Integer.MIN_VALUE;
		int[][][] dp=new int[matrix.length][matrix[0].length][matrix.length];
		for (int i = 0; i < dp.length; i++) {
			for (int i1 = 0; i1 < dp[0].length; i1++) {
				for (int i2 = 0; i2 < dp[0][0].length; i2++) {
					dp[i][i1][i2]=Integer.MIN_VALUE;
				}
			}
		}
		return process2(matrix,0,0,0,dp);
	}

	/**
	 * 限制：A和B同时走过的位置不能重复计算
	 * @param matrix 二维数组
	 * @param r1 A所在的行下标
	 * @param c1 A所在的列下标
	 * @param r2 B所在的行下标。c2=r1+c1-r2
	 * @return 返回A从(r1,c1)出发，B从(r2,c2)出发，所能达到的最大累加和
	 */
	private static int process2(int[][] matrix, int r1, int c1, int r2,int[][][] dp) {
		if (dp[r1][c1][r2]!=Integer.MIN_VALUE) return dp[r1][c1][r2];
		int N=matrix.length,M=matrix[0].length;//保证调用不会出异常，主函数调用已经写了过滤器
		if (r1==N-1&&c1==M-1) {
			dp[r1][c1][r2]=matrix[r1][c1];
			return matrix[r1][c1];//只返回一份-base case
		}
		int c2=r1+c1-r2;
		int p1=r1+1<N&&r2+1<N?process2(matrix,r1+1,c1,r2+1,dp):Integer.MIN_VALUE;
		int p2=c1+1<M&&c2+1<M?process2(matrix,r1,c1+1,r2,dp):Integer.MIN_VALUE;
		int p3=r1+1<N&&c2+1<M?process2(matrix,r1+1,c1,r2,dp):Integer.MIN_VALUE;
		int p4=c1+1<M&&r2+1<N?process2(matrix,r1,c1+1,r2+1,dp):Integer.MIN_VALUE;
		int max=Math.max(Math.max(p1,p2),Math.max(p3,p4));
		dp[r1][c1][r2]=max+(r1==r2?matrix[r1][c1]:matrix[r1][c1]+matrix[r2][c2]);
		return dp[r1][c1][r2];
	}

	public static int comeGoMaxPathSum3(int[][] grid) {
		if (grid==null||grid.length==0||grid[0]==null||grid[0].length==0) return Integer.MIN_VALUE;
		int N = grid.length;
		int M = grid[0].length;
		int[][][] dp = new int[N][M][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				for (int k = 0; k < N; k++) {
					dp[i][j][k] = Integer.MIN_VALUE;
				}
			}
		}
		return process3(grid, 0, 0, 0, dp);
	}

	public static int process3(int[][] grid, int x1, int y1, int x2, int[][][] dp) {
		if (x1 == grid.length || y1 == grid[0].length || x2 == grid.length || x1 + y1 - x2 == grid[0].length) {
			return Integer.MIN_VALUE;
		}
		if (dp[x1][y1][x2] != Integer.MIN_VALUE) {
			return dp[x1][y1][x2];
		}
		if (x1 == grid.length - 1 && y1 == grid[0].length - 1) {
			dp[x1][y1][x2] = grid[x1][y1];
			return dp[x1][y1][x2];
		}
		int next = Integer.MIN_VALUE;
		next = Math.max(next, process3(grid, x1 + 1, y1, x2 + 1, dp));
		next = Math.max(next, process3(grid, x1 + 1, y1, x2, dp));
		next = Math.max(next, process3(grid, x1, y1 + 1, x2, dp));
		next = Math.max(next, process3(grid, x1, y1 + 1, x2 + 1, dp));
		if (x1 == x2) {
			dp[x1][y1][x2] = grid[x1][y1] + next;
			return dp[x1][y1][x2];
		}
		dp[x1][y1][x2] = grid[x1][y1] + grid[x2][x1 + y1 - x2] + next;
		return dp[x1][y1][x2];
	}

	public static void testForIntMatrix() {//参数为int[][]
		ArrayUtil arrayUtil = new ArrayUtil();
		int times = 10;//测试次数
		long time1=0,time2=0;
		boolean isok = true;
		int maxN = 30;//matrix大小在[0~maxN][0~maxM]随机
		int maxM = 30;//matrix大小在[0~maxN][0~maxM]随机
		int maxValue = 10;//matrix的值在[0,maxValue]随机
//        int parameter1=0;//测试函数的参数
//        int maxParameter1=1000;//参数1在[0,maxParameter1]随机
		int[][] t1 = null;
//        int[][] t2 = null;
		int res1 = 0, res2 = 0;
		for (int i = 0; i < times; i++) {
//            parameter1=arrayUtil.ran(maxParameter1);

			t1 = arrayUtil.generateRandomMatrix(arrayUtil.ran(maxN), arrayUtil.ran(maxM),-maxValue, maxValue);
//            t2 = arrayUtil.generateRandomMatrix(arrayUtil.ran(maxN), arrayUtil.ran(maxM), maxValue);

//            t1=arrayUtil.generateRandomArr(arrayUtil.ran(maxSize),1,maxValue);//正数数组[1,maxValue]
//            t2=arrayUtil.generateRandomArr(arrayUtil.ran(maxSize),1,maxValue);//正数数组[1,maxValue]

			long l = System.currentTimeMillis();
			res1 = comeGoMaxPathSum3(t1);
			time1+=System.currentTimeMillis()-l;
			l=System.currentTimeMillis();
			res2 = comeGoMaxPathSum2(t1);
			time2+=System.currentTimeMillis()-l;

			if (res1 != res2) {
				isok = false;
				break;
			}
		}
		arrayUtil.printMatrix(t1);//打印参数
//        System.out.println("parameter:"+parameter1);//打印参数
		System.out.println("m1 cost "+time1+"ms");
		System.out.println(res1);//针对返回值的操作
		System.out.println("m2 cost "+time2+"ms");
		System.out.println(res2);//针对返回值的操作
		System.out.println(isok ? "success" : "fail");
	}
	public static void main(String[] args) {
		testForIntMatrix();

	}

}
