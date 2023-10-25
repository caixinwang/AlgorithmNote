package ClassicInterviewCoding.InterviewCoding03;

public class Code03_MinPathSum {

	/**
	 * dp[i][j]代表从m[0][0]到 m[i][j]需要的最小路径和
	 * @param m m[i][j]代表路过矩阵的[i][j]位置需要的值
	 * @return 返回从m[0][0]到m[row][colum]需要的最小路径和，只能往右边和下边走
	 * 这题不能多加一行一列，因为没有办法统一
	 */
	public static int minPathSum1(int[][] m) {
		if (m==null||m.length==0||m[0]==null||m[0].length==0) return 0;
		int row=m.length,colum=m[0].length;
		int[][] dp=new int[row][colum];
		dp[0][0]=m[0][0];//显然从[0][0]从[0][0]是m[0][0]的值
		for (int i = 1; i < m[0].length; i++) {//第一行初始化
			dp[0][i]=dp[0][i-1]+m[0][i];
		}
		for (int i = 1; i < m.length; i++) {//第一列初始化
			dp[i][0]=dp[i-1][0]+m[i][0];
		}
		for (int i = 1; i < m.length; i++) {
			for (int j = 1; j < m[0].length; j++) {
				dp[i][j]=m[i][j]+Math.min(dp[i-1][j],dp[i][j-1]);
			}
		}
		return dp[row-1][colum-1];
	}

	public static int minPathSum2(int[][] m) {
		if (m==null||m.length==0||m[0]==null||m[0].length==0) return 0;
		int row=m.length,colum=m[0].length;
		boolean rowMore=row>=colum;//决定横着滚还是竖着滚
		int[] dp=new int[rowMore?colum:row];
		dp[0]=m[0][0];
		for (int i = 1; i < dp.length; i++) {
			dp[i]=dp[i-1]+(rowMore?m[0][i]:m[i][0]);
		}
		for (int i = 1; i < (rowMore?row:colum); i++) {//行多i代表行，列多i代表列。j相反
			dp[0]=dp[0]+(rowMore?m[i][0]:m[0][i]);//更新第一个位置
			for (int j = 1; j < dp.length; j++) {
				dp[j]=Math.min(dp[j-1],dp[j])+(rowMore?m[i][j]:m[j][i]);
			}
		}
		return dp[dp.length-1];
	}


	public static int minPathSum3(int[][] m) {//管它三七二十一直接竖着滚
		if (m == null || m.length == 0 || m[0] == null || m[0].length == 0) {
			return 0;
		}
		int[] dp = new int[m[0].length];
		int N = m.length;
		int M = m[0].length;
		dp[0] = m[0][0];
		for(int col = 1; col <M; col++) {
			dp[col] = dp[col-1] + m[0][col];
		}
		for(int row = 1; row < N; row++) {
			dp[0] = dp[0] + m[row][0];
			for(int col = 1;col <M; col++ ) {
				dp[col] = Math.min(dp[col-1], dp[col]) + m[row][col];
			}
		}
		return dp[M-1];
	}




	// for test
	public static int[][] generateRandomMatrix(int rowSize, int colSize) {
		if (rowSize < 0 || colSize < 0) {
			return null;
		}
		int[][] result = new int[rowSize][colSize];
		for (int i = 0; i != result.length; i++) {
			for (int j = 0; j != result[0].length; j++) {
				result[i][j] = (int) (Math.random() * 10);
			}
		}
		return result;
	}

	// for test
	public static void printMatrix(int[][] matrix) {
		for (int i = 0; i != matrix.length; i++) {
			for (int j = 0; j != matrix[0].length; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		// int[][] m = generateRandomMatrix(3, 4);
		int[][] m = { { 1, 3, 5, 9 }, { 8, 1, 3, 4 }, { 5, 0, 6, 1 },
				{ 8, 8, 4, 0 } };
		printMatrix(m);
		System.out.println(minPathSum1(m));
		System.out.println(minPathSum2(m));
		System.out.println(minPathSum3(m));

	}
}
