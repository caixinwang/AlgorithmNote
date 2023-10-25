package Leetcode.AllQuestions;
//import Leetcode.LeetClass.*;
import java.util.*;
public class Leetcode_1254_ClosedIsland {
    public int closedIsland2(int[][] grid) {
        int n=grid.length,m=grid[0].length,ans=0;
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(grid[i][j]==0&&!f(grid,i,j,0,1)) ans++;
            }
        }
        return ans;
    }
    int[][] pos=new int[][]{{1,0},{0,1},{-1,0},{0,-1}};
    //返回从i,j 出现能不能走出去。走出去说明就不是孤岛，遇到meet就改为change
    public boolean f(int[][] grid,int i,int j,int meet,int change){
        int n=grid.length,m=grid[0].length;
        if(i==-1||i==n||j==-1||j==m) return true;
        if(grid[i][j]!=meet) return false;
        grid[i][j]=change;
        boolean ans=false;
        for(var p:pos) ans|=f(grid,i+p[0],j+p[1],meet,change);
        return ans;
    }

    class UnionFind{
        class Node{
            Node parent;
            boolean flag;//只有代表结点才有含义，代表集合是否是封闭岛
            public Node(){
                parent=this;
                flag=true;
            }
        }

        Node[][] nodes;
        HashMap<Node,Integer> size;

        public UnionFind(int[][] grid){
            nodes=new Node[grid.length][grid[0].length];
            size=new HashMap<>();
            int n=grid.length,m=grid[0].length;
            for(int i=0;i<n;i++){
                for(int j=0;j<m;j++){
                    if(grid[i][j]==0) {//0才是土地
                        nodes[i][j]=new Node();
                        if(i==0||i==n-1||j==0||j==m-1) nodes[i][j].flag=false;
                        size.put(nodes[i][j],1);
                    }
                }
            }
        }

        public Node getf(Node node){
            if(node.parent==node)return node;
            return node.parent=getf(node.parent);
        }

        public void union(int i1,int j1,int i2,int j2){//外层调用需要保证nodes[i][j]不为空
            Node node1=nodes[i1][j1],node2=nodes[i2][j2];
            Node f1=getf(node1),f2=getf(node2);
            if(f1==f2)return ;
            if(size.get(f1)>size.get(f2)) {
                union(i2,j2,i1,j1);
                return;
            }
            f1.parent=f2;
            size.put(f2,size.get(f2)+size.get(f1));
            size.remove(f1);
            if(!f1.flag) f2.flag=false;
        }

    }

    public int closedIsland(int[][] grid) {
        int n=grid.length,m=grid[0].length,ans=0;
        UnionFind uf=new UnionFind(grid);
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(grid[i][j]==0) {
                    for(var p:pos){
                        if(i+p[0]>=0&&i+p[0]<n&&j+p[1]>=0&&j+p[1]<m&&grid[i+p[0]][j+p[1]]==0) {
                            uf.union(i,j,i+p[0],j+p[1]);
                        }
                    }
                }
            }
        }
        for(var key:uf.size.keySet()) if(key.flag) ans++;
        return ans;
    }
}
