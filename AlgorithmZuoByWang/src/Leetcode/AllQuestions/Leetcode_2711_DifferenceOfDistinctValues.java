package Leetcode.AllQuestions;

import java.util.HashSet;
import java.util.Set;

public class Leetcode_2711_DifferenceOfDistinctValues {
    public int[][] differenceOfDistinctValues(int[][] grid) {
        int n=grid.length,m=grid[0].length;
        int[][] ans=new int[n][m];
        int[][] pre=new int[n][m];
        int[][] suf=new int[n][m];
        Set<Integer> set=new HashSet<>();
        for(int i=n-1,j=0;j<m;j+=i==0?1:0,i+=i!=0?-1:0){//j的移动写在前面，否则最后一次的i的移动会导致两个指针同时移动
            for(int r=i,c=j;r<n&&c<m;r++,c++){
                set.add(grid[r][c]);
                pre[r][c]=set.size();
            }
            set.clear();
        }
        for(int i=n-1,j=0;i>=0;i+=j!=m-1?0:-1,j+=j!=m-1?1:0){
            for(int r=i,c=j;r>=0&&c>=0;r--,c--){
                set.add(grid[r][c]);
                suf[r][c]=set.size();
                ans[r][c]=Math.abs(
                        (r-1>=0&&c-1>=0?pre[r-1][c-1]:0)
                                -(r+1<n&&c+1<m?suf[r+1][c+1]:0));
            }
            set.clear();
        }
        return ans;
    }
}
