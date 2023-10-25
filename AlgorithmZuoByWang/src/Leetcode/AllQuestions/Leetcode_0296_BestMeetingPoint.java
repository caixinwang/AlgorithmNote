package Leetcode.AllQuestions;

public class Leetcode_0296_BestMeetingPoint {
    public int minTotalDistance(int[][] grid) {//先确定行再确定列
        int n=grid.length,m=grid[0].length;
        int[] row_ones=new int[n],col_ones=new int[m];
        for (int i=0;i<n;i++)
            for (int j=0;j<m;j++)
                if (grid[i][j]==1){
                    row_ones[i]++;
                    col_ones[j]++;
                }
        int ans=0,cost_l=row_ones[0],cost_r=row_ones[n-1];
        int l=0,r=n-1;
        while(l<r){
            if (cost_l<=cost_r){
                ans+=cost_l;
                cost_l+=row_ones[++l];
            }else {
                ans+=cost_r;
                cost_r+=row_ones[--r];
            }
        }
        cost_l=col_ones[0];cost_r=col_ones[m-1];
        l=0;r=m-1;
        while(l<r){
            if (cost_l<=cost_r){
                ans+=cost_l;
                cost_l+=col_ones[++l];
            }else {
                ans+=cost_r;
                cost_r+=col_ones[--r];
            }
        }
        return ans;
    }
}
