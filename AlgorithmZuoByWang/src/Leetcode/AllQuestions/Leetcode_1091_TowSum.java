package Leetcode.AllQuestions;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Leetcode_1091_TowSum {
    int[][] trans=new int[][]{{-1,-1},{-1,0},{-1,1},{0,-1},{0,1},{1,-1},{1,0},{1,1}};

    //q里面放[row,col,cnt]
    //1.这题其实不需要使用优先级队列,因为有单调性：队头的肯定距离比较短。如果题目改为有开销不是1的，那么就需要优先级队列来保证
    //队头元素是开销最小的。
    //2.这题不走回头路的机制可以直接在边遍历一层边设置，因为一层里面设置了不会影响到同层的，如果会影响到，那么就需要使用updates了
    public int shortestPathBinaryMatrix(int[][] grid) {
        int n=grid.length,m=grid[0].length;
        Queue<int[]> q=new PriorityQueue<>((a,b)->{return a[2]-b[2];});//普通队列即可，不需要优先级队列
        boolean[][] visited=new boolean[n][m];
        LinkedList<int[]> updates=new LinkedList<>();//一层一层的更新，否则会被同层的影响
        if(grid[0][0]==1) return -1;//确保入队的都是0
        q.add(new int[]{0,0,1});
        visited[0][0]=true;
        while(!q.isEmpty()){
            int[] cur=q.poll();//进去q里面的都是0
            if(cur[0]==n-1&&cur[1]==m-1) return cur[2];
            for(var pos:trans) {//八个方向
                int row=cur[0]+pos[0],col=cur[1]+pos[1];//准备去到row,col位置
                if(row>=0&&row<n&&col>=0&&col<m&&!visited[row][col]&&grid[row][col]==0){
                    q.add(new int[]{row,col,cur[2]+1});
                    updates.add(new int[]{row,col});//不着急更新，一层8个方向结束再更新
                }
            }
            for(int[] u=null;!updates.isEmpty()&&(u=updates.poll())!=null;visited[u[0]][u[1]]=true);
        }
        return -1;
    }

    public int shortestPathBinaryMatrix2(int[][] grid) {
        int n=grid.length,m=grid[0].length;
        Queue<int[]> q=new LinkedList<>();//普通队列即可，花费不为1的时候使用优先级队列
        if(grid[0][0]==1) return -1;//确保入队的都是0
        q.add(new int[]{0,0,1});
        while(!q.isEmpty()){
            int[] cur=q.poll();//进去q里面的都是0
            if(cur[0]==n-1&&cur[1]==m-1) return cur[2];
            for(var pos:trans) {//八个方向
                int row=cur[0]+pos[0],col=cur[1]+pos[1];//准备去到row,col位置
                if(row>=0&&row<n&&col>=0&&col<m&&grid[row][col]==0){
                    q.add(new int[]{row,col,cur[2]+1});
                    grid[row][col]=1;//不走回头路
                }
            }
        }
        return -1;
    }
}
