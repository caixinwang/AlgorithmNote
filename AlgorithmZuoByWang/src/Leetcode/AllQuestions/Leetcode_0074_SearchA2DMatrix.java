package Leetcode.AllQuestions;

public class Leetcode_0074_SearchA2DMatrix {
    public boolean searchMatrix(int[][] matrix, int target) {
        int l=0,r=matrix.length-1;
        while(l<=r){
            int mid=l+(r-l>>1);
            if(matrix[mid][0]<=target) l=mid+1;
            else r=mid-1;
        }
        int row=r;
        if(row==-1) return false;
        l=0;r=matrix[0].length-1;
        while(l<=r){
            int mid=l+(r-l>>1);
            if(matrix[row][mid]<=target) l=mid+1;
            else r=mid-1;
        }
        if(row==-1) return false;
        return matrix[row][r]==target;
    }
}
