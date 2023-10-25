package ClassicInterviewCoding.InterviewCoding19;

import java.util.Scanner;

public class Code01_NiuNiuSplitField {

	/**
	 * 思路：先固定竖三刀，去解决竖三刀固定，你去决定横三刀，返回牛牛能得到的最大值
	 * 先把matrix转成areaDp，因为areaDp可以代表matrix的全部信息了
	 * @param matrix 田地，ij位置对应这田地的价值
	 * @return 返回横三刀竖三刀之后，牛牛总拿最小的，请问牛牛拿到的最大的价值是多少
	 */
	public static int maxMinSumIn16(int[][] matrix){
		if (matrix==null||matrix[0]==null||matrix.length<4||matrix[0].length<4) return 0;
		int colum=matrix[0].length;
		int res=Integer.MIN_VALUE;
		int[][] areaDp = getAreaDp(matrix);//先转，后序全部带着areaDp跑
		for (int c1 = 0; c1 < colum-2; c1++) {//给后两刀留位置
			for (int c2 = c1+1; c2 <colum-1 ; c2++) {//给后一刀留位置
				for (int c3 = c2+1; c3 < colum; c3++) {
					res = Math.max(res, fixedThreeColumMax(areaDp,c1,c2,c3));
				}
			}
		}
		return res;
	}

	/**
	 *
	 * @return 返回竖三刀固定为c1、c2、c3，你去决定横三刀，把牛牛能拿到的最大价值返回
	 */
	private static int fixedThreeColumMax(int[][] areaDp, int c1, int c2, int c3) {
		int res=0,N=areaDp.length,M=areaDp[0].length;
		int[] up=getUpDp(areaDp,c1,c2,c3);//up[i]表示在竖三刀固定的情况下，从[0...i]行怎么切最好，把最好的情形下的最小块值返回
		int[] down=getDownDp(areaDp,c1,c2,c3);//down[i]表示在竖三刀固定的情况下，从[i...N-1]行怎么切最好，把最好的情形下的最小块值返回
		for (int i = 1; i < N-1; i++) {//枚举中间一刀，把第一刀位置和第三刀位置腾出来
			res = Math.max(res, Math.min(up[i],down[i+1]));
		}
		return res;
	}

	/**
	 *
	 * @return dp[i]代表在c1、c2、c3竖三刀固定的情况下，0~i行你去切一刀，牛牛拿到的最大的价值是多少
	 */
	private static int[] getUpDp(int[][] areaDp, int c1, int c2, int c3) {
		int N=areaDp.length,M=areaDp[0].length;
		int[] dp=new int[N];
		dp[1]=getMinIn8Area(areaDp,c1,c2,c3,0,1,0);//[r1,split] [split+1,r2]
		int split=0;//split作为底，可以取，初始化为0
		int max;
		for (int i = 2; i < dp.length; i++) {
			max=getMinIn8Area(areaDp,c1,c2,c3,0,i,split);//只要从split往下去切，split上面的肯定不行
			while(split+1<i&&getMinIn8Area(areaDp,c1,c2,c3,0,i,split+1)>=max) {
				max=getMinIn8Area(areaDp,c1,c2,c3,0,i,split+1);
				split++;//如果split往下走更好就一直往下走
			}//出while的时候split处在最好的切分位置
			dp[i]=max;
		}
		return dp;
	}

	/**
	 *
	 * @return dp[i]代表在c1、c2、c3竖三刀固定的情况下，i~n-1行你去切一刀，牛牛拿到的最大的价值是多少
	 */
	private static int[] getDownDp(int[][] areaDp, int c1, int c2, int c3) {
		int N=areaDp.length,M=areaDp[0].length;
		int[] dp=new int[N];
		dp[N-1-1]=getMinIn8Area(areaDp,c1,c2,c3,N-1-1,N-1,N-1-1);//[N-1-1,N-1-1] [N-1-1+1,N-1]
		int split=N-1-1;//split作为底，可以取，初始化为N-1-1，而不是N-1
		int max;
		for (int i = N-1-2; i>=0; i--) {
			max=getMinIn8Area(areaDp,c1,c2,c3,i,N-1,split);
			while(split-1>=0&&getMinIn8Area(areaDp,c1,c2,c3,i,N-1,split-1)>=max) {
				max=getMinIn8Area(areaDp,c1,c2,c3,i,N-1,split-1);
				split--;
			}
			dp[i]=max;
		}
		return dp;
	}

	/**
	 * [r1,split]  [split+1,r2]
	 * @return 返回竖三刀固定，在r1和r2中间切了一刀split之后，八块中最小的块。
	 */
	private static int getMinIn8Area(int[][] areaDp, int c1, int c2,int c3,int r1,int r2, int split) {//[r1..split..r2]
		return Math.min(getMinIn4Area(areaDp,c1,c2,c3,r1,split),getMinIn4Area(areaDp,c1,c2,c3,split+1,r2));
	}

	/**
	 *
	 * @return 返回竖三刀固定，在r1和r2中间四块中最小的块
	 */
	private static int getMinIn4Area(int[][] areaDp, int c1, int c2, int c3, int r1,int r2) {
		return min(
				getArea(areaDp,r1,r2,0,c1),
				getArea(areaDp,r1,r2,c1+1,c2),
				getArea(areaDp,r1,r2,c2+1,c3),
				getArea(areaDp,r1,r2,c3+1,areaDp[0].length-1)
				);
	}

	private static int min(int a,int b,int c,int d){//代码块
		return Math.min(Math.min(Math.min(a,b),c),d);
	}


	/**
	 * 技巧，不单独初始化第一行第一列，直接初始化左上角格子之后就开始填表。先列出普遍情况，越界就加0，只有加了两次才要减0，
	 * 所以减之前要先判断一下是不是加了两次
	 * @param matrix 将原始矩阵变成面积矩阵
	 * @return 返回的dp[i][j]代表从matrix[0][0]~matrix[i][j]方块的累加和
	 */
	private static int[][] getAreaDp(int[][] matrix){
		int N=matrix.length,M=matrix[0].length;
		int[][] dp=new int[N][M];
		dp[0][0]=matrix[0][0];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (i==0&&j==0) continue;
				dp[i][j]=matrix[i][j]+(i-1>=0?dp[i-1][j]:0)+(j-1>=0?dp[i][j-1]:0)-(i-1>=0&&j-1>=0?dp[i-1][j-1]:0);
			}
		}
		return dp;
	}

	/**
	 * 技巧：列出普遍情况，如果越界就减0。发现只有减了两次才会重复减。所以减之前做判断
	 * @param areaDp areaDp[i][j] 代表从matrix[0][0]~matrix[i][j]方块的累加和
	 * @return 返回r1/r2/c1/c2组成的块的价值
	 */
	private static int getArea(int[][] areaDp,int r1,int r2,int c1,int c2){
		return areaDp[r2][c2]
				-(r1-1>=0?areaDp[r1-1][c2]:0)
				-(c1-1>=0?areaDp[r2][c1-1]:0)
				+(r1-1>=0&&c1-1>=0?areaDp[r1-1][c1-1]:0);
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		while (in.hasNext()) {
			int n = in.nextInt();
			int m = in.nextInt();
			int[][] matrix = new int[n][m];
			for (int i = 0; i < n; i++) {
				char[] chas = in.next().toCharArray();
				for (int j = 0; j < m; j++) {
					matrix[i][j] = chas[j] - '0';
				}
			}
			System.out.println(maxMinSumIn16(matrix));
		}
		in.close();
	}

}