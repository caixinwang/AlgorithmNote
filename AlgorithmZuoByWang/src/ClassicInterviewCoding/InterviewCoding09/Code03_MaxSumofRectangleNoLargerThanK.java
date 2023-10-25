package ClassicInterviewCoding.InterviewCoding09;

import java.util.TreeSet;

public class Code03_MaxSumofRectangleNoLargerThanK {

	public static int maxSumSubmatrix(int[][] matrix, int k) {
		if (matrix == null||matrix.length==0 || matrix[0] == null||matrix[0].length==0)
			return Integer.MIN_VALUE;
		int row = matrix.length, col = matrix[0].length, res = Integer.MIN_VALUE;
		TreeSet<Integer> sumSet = new TreeSet<>();
		for (int s = 0; s < row; s++) { // s开始行
			int[] colSum = new int[col];
			for (int e = s; e < row; e++) { // e结束行
				// 子矩阵必须包含s~e行的数，且只包含s~e行的数
				sumSet.add(0);
				int rowSum = 0;
				for (int c = 0; c < col; c++) {
					colSum[c] += matrix[e][c];
					rowSum += colSum[c];
					Integer it = sumSet.ceiling(rowSum - k);
					if (it != null) {
						res = Math.max(res, rowSum - it);
					}
					sumSet.add(rowSum);
				}
				sumSet.clear();
			}
		}
		return res;
	}

	public static int maxSumSubmatrix2(int[][] matrix, int k) {
		if (matrix == null||matrix.length==0 || matrix[0] == null||matrix[0].length==0)
			return Integer.MIN_VALUE;
		int row = matrix.length, col = matrix[0].length, res = Integer.MIN_VALUE;
		for (int s = 0; s < row; s++) { // s开始行
			int[] colSum = new int[col];
			for (int e = s; e < row; e++) { // e结束行
				for (int i = 0; i < col; i++) {
					colSum[i]+=matrix[e][i];
				}
				res = Math.max(res, getMaxLessOrEqualK(colSum,k));
			}
		}
		return res;
	}


	// arr -> m
	// m  logM
	public static int getMaxLessOrEqualK(int[] arr, int K) {
		TreeSet<Integer> sums = new TreeSet<>();
		sums.add(0);
		int sum = 0;
		int ans = Integer.MIN_VALUE;
		for (int i = 0; i < arr.length; i++) {
			sum += arr[i];
			Integer tmp = sums.ceiling(sum - K);	
			if (tmp != null) {
				ans = Math.max(ans, sum - tmp);
			}
			sums.add(sum);
		}
		return ans;
	}


}
