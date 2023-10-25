package Leetcode.AllQuestions;

public class Leetcode_1605_RestoreMatrix {
    //关键点，按照小的填.如果行小，那么填完格子之后本行其它位置都为0，反之列的其它位置为0
    public int[][] restoreMatrix(int[] rowSum, int[] colSum) {
        int n=rowSum.length,m=colSum.length;
        int[][] matrix = new int[n][m];
        for(int i=0,j=0;i<n&&j<m;){//每次填好一行或者一列
            if(rowSum[i]<colSum[j]){
                matrix[i][j]=rowSum[i];
                colSum[j]-=rowSum[i++];
            }else{
                matrix[i][j]=colSum[j];
                rowSum[i]-=colSum[j++];
            }
        }
        return matrix;
    }
}
