package Leetcode.AllQuestions;

public class Leetcode_0240_SearchA2DMatrixII {

	public static boolean searchMatrix(int[][] m, int target) {
		int N=m.length,M=m[0].length;
		if (N==0||M==0) return false;
		int i=0,j=M-1;//右上角点，两个方向的增量不一样才行。如果在左上角，两个方向都是增大的，没有单调性
		while (i<N&&j>=0){
			if (m[i][j]==target) return true;
			if (m[i][j]>target){
				j--;
			}else {
				i++;
			}
		}
		return false;
	}


}
