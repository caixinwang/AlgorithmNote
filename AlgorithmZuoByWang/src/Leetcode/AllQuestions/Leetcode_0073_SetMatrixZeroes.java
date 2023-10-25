package Leetcode.AllQuestions;

public class Leetcode_0073_SetMatrixZeroes {

    public void setZeroes(int[][] matrix) {
        int N= matrix.length,M=matrix[0].length;
        boolean fr=false;//第一行的信息，为true代表第一行需要都变0
        for (int col = 0; col < M; col++) {
            if (matrix[0][col]==0) fr=true;
        }
        boolean fc=false;
        for (int row = 0; row < N; row++) {
            if (matrix[row][0]==0) fc=true;
        }
        for (int i = 1; i < N; i++) {
            for (int j = 1; j < M; j++) {
                if (matrix[i][j]==0){
                    matrix[0][j]=0;
                    matrix[i][0]=0;
                }
            }
        }
        for (int col = 1; col < M; col++) {//(0,0)位置空出来的
            if (matrix[0][col]==0) {//第col列需要都变为0
                for (int row=1;row<N;row++) matrix[row][col]=0;//
            }
        }
        for (int row = 1; row < N; row++) {
            if (matrix[row][0]==0) {
                for (int col=1;col<M;col++) matrix[row][col]=0;
            }
        }
        if (fr) for (int col=0;col<M;col++) matrix[0][col]=0;
        if (fc) for (int row=0;row<N;row++) matrix[row][0]=0;
    }


}
