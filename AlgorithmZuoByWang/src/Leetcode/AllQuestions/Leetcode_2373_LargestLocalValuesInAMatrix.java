package Leetcode.AllQuestions;

public class Leetcode_2373_LargestLocalValuesInAMatrix {
    public int[][] largestLocal(int[][] grid) {
        int n=grid.length;
        int[][] ans=new int[n-2][n-2];
        for (int i=1;i<n-1;i++){
            for (int j=1;j<n-1;j++){
                int max=0;
                for (int r=i-1;r<=i+1;r++){
                    for (int c=j-1;c<=j+1;c++){
                        max = Math.max(max, grid[r][c]);
                    }
                }
                ans[i-1][j-1]=max;
            }
        }
        return ans;
    }
}
