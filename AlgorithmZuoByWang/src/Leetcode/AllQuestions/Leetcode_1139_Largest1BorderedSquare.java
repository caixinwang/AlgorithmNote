package Leetcode.AllQuestions;

import java.util.Arrays;

public class Leetcode_1139_Largest1BorderedSquare {
    //使用前缀和来达到快速确认是否四条边都达标的目的
    public int largest1BorderedSquare(int[][] grid) {
        int n=grid.length,m=grid[0].length,i,j,cnt,ans=0;
        int[][][] pre=new int[n][m][2];//行列方向上连续1的个数
        for(i=0;i<n;i++){
            for(j=cnt=0;j<m;j++){//从左往右
                if(grid[i][j]==1) cnt++;
                else cnt=0;
                pre[i][j][1]=cnt;
            }
        }
        for(j=0;j<m;j++){
            for(i=cnt=0;i<n;i++){//从上往下
                if(grid[i][j]==1) cnt++;
                else cnt=0;
                pre[i][j][0]=cnt;
            }
        }
        for(i=0;i<n;i++){
            for(j=0;j<m;j++){
                if(grid[i][j]==0) continue;
                int len=min(pre[i][j][0],pre[i][j][1]);
                for(;len>=1;len--){
                    if(pre[i-(len-1)][j][1]>=len&&pre[i][j-(len-1)][0]>=len){
                        ans=max(ans,len);
                        break;
                    }
                }
            }
        }
        return ans*ans;
    }
    public int min(int a,int b){return a<b?a:b;}
    public int max(int a,int b){return a>b?a:b;}
}
