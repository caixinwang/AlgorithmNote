package Leetcode.AllQuestions;

public class Leetcode_0048_RotateImage {

	public static void rotate(int[][] matrix) {
		for (int i = 0; matrix.length-1-i > i; i++) {
			int r1=i,r2= matrix.length-1-i;//r1<r2
			int len=r2-r1+1;
			for (int k = 0; k < len-1; k++) {//换len-1次
				r(matrix,r1,r2,k);
			}
		}
	}

	public static void r(int[][] matrix,int r1,int r2,int i){//c1==r1,c2=r2
//		swap(matrix,r1+i,r2,r1,r1+i);
//		swap(matrix,r1,r1+i,r2-i,r1);
//		swap(matrix,r2-i,r1,r2,r2-i);
		int c1=r1;
		int c2=r2;
		int t=matrix[r1+i][c2];//这个位置存起来来，说明别的数可以来了
		matrix[r1+i][c2]=matrix[r1][c1+i];
		matrix[r1][c1+i]=matrix[r2-i][c1];
		matrix[r2-i][c1]=matrix[r2][c2-i];
		matrix[r2][c2-1]=t;
	}

	public static void swap(int[][] matrix,int r1,int c1,int r2,int c2){
		int t=matrix[r1][c1];
		matrix[r1][c1]=matrix[r2][c2];
		matrix[r2][c2]=t;
	}

	public static void printStar(int N) {
		int leftUp = 0;
		int rightDown = N - 1;
		char[][] m = new char[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				m[i][j] = ' ';
			}
		}
		while (leftUp <= rightDown) {
			set(m, leftUp, rightDown);
			leftUp += 2;
			rightDown -= 2;
		}
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				System.out.print(m[i][j] + " ");
			}
			System.out.println();
		}
	}

	public static void set(char[][] m, int leftUp, int rightDown) {
		for (int col = leftUp; col <= rightDown; col++) {
			m[leftUp][col] = '*';
		}
		for (int row = leftUp + 1; row <= rightDown; row++) {
			m[row][rightDown] = '*';
		}
		for (int col = rightDown - 1; col > leftUp; col--) {
			m[rightDown][col] = '*';
		}
		for (int row = rightDown - 1; row > leftUp + 1; row--) {
			m[row][leftUp + 1] = '*';
		}
	}

	public static void main(String[] args) {
		printStar(20);
	}


}
