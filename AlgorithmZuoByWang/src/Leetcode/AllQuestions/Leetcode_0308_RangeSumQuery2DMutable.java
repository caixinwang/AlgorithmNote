package Leetcode.AllQuestions;

import TestUtils.ArrayUtil;
import TestUtils.RandomUtil;

// 提交时把类名和构造函数名从Problem_0308_RangeSumQuery2DMutable改成NumMatrix
public class Leetcode_0308_RangeSumQuery2DMutable {
	static class IndexTree2D1{
		private int[][] tree;
		private int[][] nums;
		private int N;
		private int M;

		public IndexTree2D1(int[][] matrix) {
			if (matrix.length == 0 || matrix[0].length == 0) {
				return;
			}
			N = matrix.length;
			M = matrix[0].length;
			tree = new int[N + 1][M + 1];
			nums = new int[N][M];
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < M; j++) {
					update(i, j, matrix[i][j]);
				}
			}
		}

		// 用户给我的row，col不能越界
		private int sum(int row, int col) {
			int sum = 0;
			for (int i = row + 1; i > 0; i -= i & (-i)) {
				for (int j = col + 1; j > 0; j -= j & (-j)) {
					sum += tree[i][j];
				}
			}
			return sum;
		}

		// 用户给我的row，col不能越界
		public void update(int row, int col, int val) {
			if (N == 0 || M == 0) {
				return;
			}
			int add = val - nums[row][col];
			nums[row][col] = val;
			for (int i = row + 1; i <= N; i += i & (-i)) {
				for (int j = col + 1; j <= M; j += j & (-j)) {
					tree[i][j] += add;
				}
			}
		}

		public int sumRegion(int row1, int col1, int row2, int col2) {
			if (N == 0 || M == 0) {
				return 0;
			}
			return sum(row2, col2) + sum(row1 - 1, col1 - 1) - sum(row1 - 1, col2) - sum(row2, col1 - 1);
		}
	}

	static class IndexTree2D2{
		int N;
		int M;
		int[][] m;
		int[][] tree;

		public IndexTree2D2(int[][] origin){
			N=origin.length;
			M=origin[0].length;
			m=new int[N][M];
			tree =new int[N+1][M+1];
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < M; j++) {
					update(i,j,origin[i][j]);
				}
			}
		}

		public void update(int row,int col,int val){//用户传进来的row和col从0开始
			int add=val-m[row][col];
			m[row][col]=val;
			for (int i=row+1;i<=N;i+=i&-i){
				for (int j=col+1;j<=M;j+=j&-j){
					tree[i][j]+=add;
				}
			}
		}

		public int sum(int row,int col){
			int ans=0;
			for (int i=row+1;i>0;i-=i&-i){
				for (int j=col+1;j>0;j-=j&-j){
					ans+= tree[i][j];
				}
			}
			return ans;
		}

		public int sumRegion(int row1, int col1, int row2, int col2){
			return sum(row2,col2)- sum(row2,col1-1)- sum(row1-1,col2)+sum(row1-1,col1-1);
		}
	}


	public static void testForStructure() {//测试证明两个UnionFind都是正确的
		RandomUtil r = new RandomUtil();
		ArrayUtil au=new ArrayUtil();
		int times = 100;
		int size = 100;
		boolean isok = true;
		for (int i = 0; i < times; i++) {//进行times次测试
			int realSize = (int) (Math.random() * (size + 1));//[0,1000]随机数据规模
			int tests = 10000;
			int [][] origin1=au.generateRandomMatrix(size,size,300);
			int [][] origin2=au.copyMatrix(origin1);
			int N=origin1.length;
			int M=origin1[0].length;
            IndexTree2D1 structure1=new IndexTree2D1(origin1);
			IndexTree2D2 structure2=new IndexTree2D2(origin2);
			for (int j = 0; j < tests; j++) {//每次测试随机进行times次的数据操作并且检查数据的正确性
				if (r.oneOrZero()) {
					int row=r.ran(0,N-1);
					int col=r.ran(0,M-1);
					int val=r.ran(-100,100);
					structure1.update(row,col,val);
					structure2.update(row,col,val);
				} else {
					int row1=r.ran(0,N-1);
					int col1=r.ran(0,M-1);
					int row2=r.ran(row1,98);
					int col2=r.ran(col1,98);
					if (structure1.sumRegion(row1,col1,row2,col2)!=structure2.sumRegion(row1,col1,row2,col2)){
						isok=false;
						System.out.println(structure1.sumRegion(row1,col1,row2,col2));
						System.out.println(structure2.sumRegion(row1,col1,row2,col2));
						break;
					}
				}
			}
			if (!isok) break;
		}
		System.out.println(isok ? "success" : "fail");
	}
	public static void main(String[] args) {
		testForStructure();
	}


}
