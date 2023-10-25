package Leetcode.AllQuestions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Leetcode_2713_MaximumStrictlyIncreasingCellsInAMatrix {
    //定义dp的含义为从最多走dp[i][j]步从某一个格子走到(i,j)，那么就是从同一行同一列小的格子转移过来。
    //所以我们需要先把同一行同一列中值比较小的格子的dp值先算出来。故我们填格子的顺序是乱的，值小的先填
    //细节：需要把值相同的一次性处理完了之后才能去更新row_max、col_max
    //还可以用一个TreeMap，<Integer,List<int[]>>，key是矩阵值，value是一个List，装着所有值等于key的矩阵下标
    //这一题为什么不需要线段树？因为我们每次都是求一整行、一整列，并不是一个范围查询，所以不需要线段树。
    public int maxIncreasingCells(int[][] mat) {
        int n=mat.length,m=mat[0].length;
        int[][] dp=new int[n][m];
        for (int[] a:dp) Arrays.fill(a,-1);
        int[] row_max=new int[n],col_max=new int[m];
        int[][] infos=new int[n*m][3];
        for (int i=0,index=0;i<n;i++)
            for (int j=0;j<m;j++)
                infos[index++]=new int[]{mat[i][j],i,j};
        Arrays.sort(infos,(a,b)->a[0]-b[0]);//按照矩阵的值排序
        List<int[]> updates=new ArrayList<>();
        for(int i=0;i< infos.length;i++){
            if (i!=0&&infos[i][0]!=infos[i-1][0]) update(updates,row_max,col_max);
            int row=infos[i][1],col=infos[i][2],dp_val=1+Math.max(row_max[row],col_max[col]);
            updates.add(new int[]{row,col,dp_val});
            dp[row][col]=dp_val;
        }
        int ans=0;
        for (int[] a:dp) for (int b:a) ans = Math.max(ans, b);
        return ans;
    }
    private void update(List<int[]> updates, int[] rowMax, int[] colMax) {
        if (updates.size()==0) return;
        for (int[] u:updates){
            int row=u[0],col=u[1],v=u[2];
            rowMax[row] = Math.max(rowMax[row],v);
            colMax[col] = Math.max(colMax[col], v);
        }
        updates.clear();
    }

}
