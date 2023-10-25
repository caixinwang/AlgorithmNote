package ClassicInterviewCoding.InterviewCoding13;

import java.util.Arrays;

public class Code02_SnakeGame {

	public static int walk1(int[][] matrix) {
		if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
			return 0;
		}
		int res = Integer.MIN_VALUE;
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				int[] ans = process(matrix, i, j);
				res = Math.max(res, Math.max(ans[0], ans[1]));
			}
		}
		return res;
	}

	/**
	 * 如果到达不了，就返回-1
	 * 递归含义：从第一列的假想位置以0血出发到达(i,j)位置所能达到的最大长度，注意这里的含义是到达（i，j）位置能到达的最大长度，
	 * 而不是到达（i，j）的途中能达到的最大值。所以主函数调用的时候需要把matrix所有的地方都调用一遍。
	 * 最终游戏过程中能达到的最大长度不一定要通关，可能他为了一个很大的数去吃了，让蛇的长度最大，但是后面是陷阱，
	 * 蛇死了，这种情况比他正常通关完达到的长度还大，这才是游戏过程达到的最长，游戏不一定要通过
	 *
	 * 可能性分析：一个普遍的位置分为两种情况，一种是使用超能力，第二种是不使用超能力。如果使用了超能力，
	 * 就需要前面的子问题提供不使用超能力时候达到的最长长度。如果不使用超能力，那么就要子问题返回他们能用超能力
	 * 能到达的最大值。所以返回值需要两个信息，一个是不使用超能力所能达到的最大值，一个是使用超能力达到的最大值
	 * 关键问题：你要子问题返回两个信息，那么你自己也要维持好这个信息，让信息传递下去
	 * @return 返回：[不使用，使用]
	 */
	public static int[] process(int[][] matrix ,int i,int j){
		if (i<0||i>= matrix.length) return new int[]{-1,-1};//含义为非法的地方到达不了
		if (j==0){//base case ，从第一列出发，到第一列，出生就在罗马
			return new int[]{matrix[i][j],-matrix[i][j]};
		}
		int[] info1=process(matrix,i-1,j-1);//拿到三个子问题的信息
		int[] info2=process(matrix, i,j-1);
		int[] info3=process(matrix,i+1,j-1);
		int preUse=Math.max(Math.max(info1[1],info2[1]),info3[1]);//子问题可以使用超能力
		int preUnuse=Math.max(Math.max(info1[0],info2[0]),info3[0]);//子问题不使用超能力
		int no = -1; // 之前没使用过能力，当前位置也不使用能力，的最优解
		int yes = -1; // 不管是之前使用能力，还是当前使用了能力，请保证能力使用且仅使用一次，最优解
		if (preUnuse >= 0) {
			yes = Math.max(yes, preUnuse-matrix[i][j]);
			no = Math.max(no, preUnuse+matrix[i][j]);
		}
		if (preUse >= 0) {
			yes = Math.max(yes, preUse+matrix[i][j]);
		}
		return new int[] { no, yes };
	}


	public static int walk2(int[][] matrix) {
		if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
			return 0;
		}
		int max = Integer.MIN_VALUE;
		int[][][] dp = new int[matrix.length][matrix[0].length][2];
		for (int i = 0; i < dp.length; i++) {
			dp[i][0][0] = matrix[i][0];
			dp[i][0][1] = -matrix[i][0];
			max = Math.max(max, Math.max(dp[i][0][0], dp[i][0][1]));
		}
		for (int j = 1; j < matrix[0].length; j++) {
			for (int i = 0; i < matrix.length; i++) {
				int preUnuse = dp[i][j - 1][0];
				int preUse = dp[i][j - 1][1];
				if (i - 1 >= 0) {
					preUnuse = Math.max(preUnuse, dp[i - 1][j - 1][0]);
					preUse = Math.max(preUse, dp[i - 1][j - 1][1]);
				}
				if (i + 1 < matrix.length) {
					preUnuse = Math.max(preUnuse, dp[i + 1][j - 1][0]);
					preUse = Math.max(preUse, dp[i + 1][j - 1][1]);
				}
				dp[i][j][0] = -1;
				dp[i][j][1] = -1;
				if (preUnuse >= 0) {
					dp[i][j][0] = matrix[i][j] + preUnuse;
					dp[i][j][1] = -matrix[i][j] + preUnuse;
				}
				if (preUse >= 0) {
					dp[i][j][1] = Math.max(dp[i][j][1], matrix[i][j] + preUse);
				}
				max = Math.max(max, Math.max(dp[i][j][0], dp[i][j][1]));
			}
		}
		return max;
	}

	public static int[][] generateRandomArray(int row, int col, int value) {
		int[][] arr = new int[(int) (Math.random() * row) + 1][(int) (Math.random() * col) + 1];
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[0].length; j++) {
				arr[i][j] = (int) (Math.random() * value) * (Math.random() > 0.5 ? -1 : 1);
			}
		}
		return arr;
	}

	public static void main(String[] args) {
		int times = 1000000;
		for (int i = 0; i < times; i++) {
			int[][] matrix = generateRandomArray(5, 5, 10);
			int ans1 = walk1(matrix);
			int ans2 = walk2(matrix);
			if (ans1 != ans2) {
				for (int j = 0; j < matrix.length; j++) {
					System.out.println(Arrays.toString(matrix[j]));
				}
				System.out.println("Oops   ans1: " + ans1 + "   ans2:" + ans2);
				break;
			}
		}
		System.out.println("finish");
	}

}
