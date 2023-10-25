package Leetcode.SwordToOffer;

public class SwordToOffer_013_MovingCount {
    public int movingCount(int n, int m, int k) {//岛问题
        return f(new boolean[n][m],k,0,0);
    }

    public int f(boolean[][] matrix,int k,int i,int j){
        int n=matrix.length, m=matrix[0].length;
        if(i<0||i>=n||j<0||j>=m||matrix[i][j]||getsum(i)+getsum(j)>k) return 0;
        matrix[i][j]=true;
        return 1+f(matrix,k,i+1,j)+f(matrix,k,i-1,j)+f(matrix,k,i,j+1)+f(matrix,k,i,j-1);
    }

    public int getsum(int num){
        int ans=0;
        while(num!=0){
            ans+=num%10;
            num/=10;
        }
        return ans;
    }
}
