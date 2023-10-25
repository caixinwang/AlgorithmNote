package Leetcode.AllQuestions;

import java.util.ArrayList;
import java.util.List;

public class Leetcode_0054_SpiralMatrix {
	public List<Integer> spiralOrder(int[][] matrix) {
		List<Integer> ans=new ArrayList<>();
		int r1=0,r2=matrix.length-1;
		int c1=0,c2=matrix[0].length-1;
		while (r1<=r2&&c1<=c2){
			printRectangle(matrix,ans,r1++,r2--,c1++,c2--);
		}
		return ans;
	}

	//打印外圈
	public void printRectangle(int[][] matrix,List<Integer> ans,int r1,int r2,int c1,int c2){
		if (r1==r2){
			for (int i = c1; i <=c2 ; i++) {
				ans.add(matrix[r1][i]);
			}
			return;
		}
		if (c1==c2){
			for (int i = r1; i <= r2; i++) {
				ans.add(matrix[i][c1]);
			}
			return;
		}
		int N=r2-r1+1;
		int M=c2-c1+1;
		for (int i = 0; i < M-1; i++) {
			ans.add(matrix[r1][c1+i]);
		}
		for (int i = 0; i < N-1; i++) {
			ans.add(matrix[r1+i][c2]);
		}
		for (int i = 0; i < M-1; i++) {
			ans.add(matrix[r2][c2-i]);
		}
		for (int i = 0; i < N-1; i++) {
			ans.add(matrix[r2-i][c1]);
		}
	}
}
