package Leetcode.SwordToOffer;

public class SwordToOffer_029_SpiralOrder {
    int index=0;
    public int[] spiralOrder(int[][] matrix) {
        if(matrix.length==0) return new int[]{};
        int n=matrix.length,m=matrix[0].length;
        int[] ans=new int[n*m];
        int x1=0,y1=0,x2=n-1,y2=m-1;
        while(x1<=x2&&y1<=y2){
            add(matrix,ans,x1,y1,x2,y2);
            x1++;y1++;x2--;y2--;
        }
        return ans;
    }
    public void add(int[][] matrix,int[] ans,int x1,int y1,int x2,int y2){
        if(x1==x2){
            for(int i=x1,j=y1;j<=y2;j++) ans[index++]=matrix[i][j];
            return;
        }
        if(y1==y2){
            for(int i=x1,j=y1;i<=x2;i++) ans[index++]=matrix[i][j];
            return;
        }
        for(int i=x1,j=y1;j<y2;j++) ans[index++]=matrix[i][j];
        for(int i=x1,j=y2;i<x2;i++) ans[index++]=matrix[i][j];
        for(int i=x2,j=y2;j>y1;j--) ans[index++]=matrix[i][j];
        for(int i=x2,j=y1;i>x1;i--) ans[index++]=matrix[i][j];
    }
}
