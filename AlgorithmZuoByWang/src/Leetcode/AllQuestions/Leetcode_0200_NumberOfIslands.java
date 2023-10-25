package Leetcode.AllQuestions;

import java.util.HashMap;

public class Leetcode_0200_NumberOfIslands {//岛问题，可以用递归搞定。但是去试试并查集

	//这题甚至用不上走不走得出去的信息。。。
	public int numIslands(char[][] grid) {
		int ans=0;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (grid[i][j]=='1'){
					f(grid,i,j,'1','0');
					ans++;
				}
			}
		}
		return ans;
	}

	/**
	 * @param grid 二维网格
	 * @param i 出发点
	 * @param j -
	 * @param meet 遇到meet我才往下走，并且meet全部改成change
	 * @param change -
	 * @return 返回能不能走出去。
	 */
	public boolean f(char[][] grid,int i,int j,char meet,char change){
		if (i<0||j<0||i>grid.length-1||j>grid[0].length-1) return true;
		boolean ans=false;
		if (grid[i][j]==meet) {
			grid[i][j] = change;
			ans|=f(grid,i+1,j,meet,change);//不能||不然会断联，改不全
			ans|=f(grid,i-1,j,meet,change);
			ans|=f(grid,i,j+1,meet,change);
			ans|=f(grid,i,j-1,meet,change);
		}
		return ans;
	}

	int[][] trans=new int[][]{{1,0},{-1,0},{0,1},{0,-1}};
	public boolean f2(char[][] grid,int i,int j,char meet,char change){
		if (i<0||j<0||i>grid.length-1||j>grid[0].length-1) return true;
		if (grid[i][j]!=meet) return false;
		grid[i][j]=change;
		boolean ans=false;
		for (var p:trans) if (f2(grid,i+p[0],j+p[1],meet,change)) ans=true;
		return ans;
	}

	class UnionFind{
		class Node{
			Node parent;
			public Node(){parent=this;}
		}

		Node[][] nodes;
		HashMap<Node,Integer> size;

		public UnionFind(char[][] grid){
			nodes=new Node[grid.length][grid[0].length];
			size=new HashMap<>();
			for(int i=0;i<grid.length;i++){
				for(int j=0;j<grid[0].length;j++){
					if(grid[i][j]=='1') {
						nodes[i][j]=new Node();
						size.put(nodes[i][j],1);
					}
				}
			}
		}

		public Node getf(Node node){
			if(node.parent==node)return node;
			return node.parent=getf(node.parent);
		}

		public void union(int i1,int j1,int i2,int j2){
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
		}

	}
	public int numIslands2(char[][] grid) {
		UnionFind uf=new UnionFind(grid);
		for(int i=0;i<grid.length;i++){
			for(int j=0;j<grid[0].length;j++){
				if(grid[i][j]=='1') {
					for(var p:trans){
						if(i+p[0]>=0&&i+p[0]<grid.length&&j+p[1]>=0&&j+p[1]<grid[0].length&&grid[i+p[0]][j+p[1]] == '1') uf.union(i,j,i+p[0],j+p[1]);
					}
				}
			}
		}
		return uf.size.size();
	}



}
