package Leetcode.AllQuestions;

public class Leetcode_0059_SpiralMatrixII {
    public int[][] generateMatrix(int n) {
        int[][] ans=new int[n][n];
        int num=1;
        for(int i=0,j=n-1;i<=j;i++,j--){
            if(i==j) ans[i][j]=num++;//i和j撞上的时候下面的for循环一个都不会进
            for(int c=i;c<j;c++) ans[i][c]=num++;
            for(int r=i;r<j;r++) ans[r][j]=num++;
            for(int c=j;c>i;c--) ans[j][c]=num++;
            for(int r=j;r>i;r--) ans[r][i]=num++;
        }
        return ans;
    }

    int cnt=1;
    public int[][] generateMatrix2(int n) {//是长方形螺旋的时候用这个方法
        int[][] m=new int[n][n];
        for(int r1=0,c1=0,r2=n-1,c2=n-1;r1<=r2&&c1<=c2;r1++,c1++,r2--,c2--){
            print(m,r1,c1,r2,c2);
        }
        return m;
    }

    public void print(int[][] m,int r1,int c1,int r2,int c2){
        if(r1==r2){
            for(int j=c1;j<=c2;j++) m[r1][j]=cnt++;
        }else if(c1==c2){
            for(int i=r1;i<=r2;i++) m[i][c1]=cnt++;
        }else {
            for(int i=r1,j=c1;j<c2;j++) m[i][j]=cnt++;
            for(int i=r1,j=c2;i<r2;i++) m[i][j]=cnt++;
            for(int i=r2,j=c2;j>c1;j--) m[i][j]=cnt++;
            for(int i=r2,j=c1;i>r1;i--) m[i][j]=cnt++;
        }
    }
}
