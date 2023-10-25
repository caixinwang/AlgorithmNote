package OtherCoding.InterView;

import TestUtils.ArrayUtil;

import java.util.PriorityQueue;

// 来自网易
// map[i][j] == 0，代表(i,j)是海洋，渡过的话代价是2
// map[i][j] == 1，代表(i,j)是陆地，渡过的话代价是1
// map[i][j] == 2，代表(i,j)是障碍，无法渡过
// 每一步上、下、左、右都能走，返回从左上角走到右下角最小代价是多少，如果无法到达返回-1
public class Code0013_WalkToEnd {

	public static int minCost(int[][] map) {
		if (map[0][0] == 2) {
			return -1;
		}
		int n = map.length;
		int m = map[0].length;
		PriorityQueue<Node> heap = new PriorityQueue<>((a, b) -> a.cost - b.cost);
		boolean[][] visited = new boolean[n][m];
		add(map, 0, 0, 0, heap, visited);
		while (!heap.isEmpty()) {
			Node cur = heap.poll();
			if (cur.row == n - 1 && cur.col == m - 1) {
				return cur.cost;
			}
			add(map, cur.row - 1, cur.col, cur.cost, heap, visited);
			add(map, cur.row + 1, cur.col, cur.cost, heap, visited);
			add(map, cur.row, cur.col - 1, cur.cost, heap, visited);
			add(map, cur.row, cur.col + 1, cur.cost, heap, visited);
		}
		return -1;
	}

	public static void add(int[][] m, int i, int j, int pre, PriorityQueue<Node> heap, boolean[][] visited) {
		if (i >= 0 && i < m.length && j >= 0 && j < m[0].length && m[i][j] != 2 && !visited[i][j]) {
			heap.add(new Node(i, j, pre + (m[i][j] == 0 ? 2 : 1)));
			visited[i][j] = true;
		}
	}

	public static class Node {
		public int row;
		public int col;
		public int cost;

		public Node(int a, int b, int c) {
			row = a;
			col = b;
			cost = c;
		}
	}

	public static int minCost2(int[][] map) {
		if (map[0][0]==2) return -1;
		int N=map.length,M=map[0].length;
		PriorityQueue<int[]> queue=new PriorityQueue<>((a,b)->a[2]-b[2]);//[2]放的是代价
		boolean[][] visit=new boolean[N][M];
		queue.add(new int[]{0,0,cost(map[0][0])});
		visit[0][0]=true;
		while (!queue.isEmpty()){
			int[] ints = queue.poll();
			int row=ints[0];
			int col=ints[1];
			int cost=ints[2];
			if (row==N-1&&col==M-1) return cost;
			if (row+1<N&&!visit[row+1][col]&&map[row+1][col]!=2){
				visit[row+1][col]=true;
				queue.add(new int[]{row+1,col,cost+cost(map[row+1][col])});
			}
			if (row-1>=0&&!visit[row-1][col]&&map[row-1][col]!=2){
				visit[row-1][col]=true;
				queue.add(new int[]{row-1,col,cost+cost(map[row-1][col])});
			}
			if (col+1<M&&!visit[row][col+1]&&map[row][col+1]!=2){
				visit[row][col+1]=true;
				queue.add(new int[]{row,col+1,cost+cost(map[row][col+1])});
			}
			if (col-1>=0&&!visit[row][col-1]&&map[row][col-1]!=2){
				visit[row][col-1]=true;
				queue.add(new int[]{row,col-1,cost+cost(map[row][col-1])});
			}
		}
		return -1;
	}

	public static int cost(int i) {return i==0?2:1;}

	static ArrayUtil au=new ArrayUtil();
	public static void main(String[] args) {
		int times=1000;
		for (int i = 0; i < times; i++) {
			int[][] map= au.generateRandomMatrix(au.ran(1,100),au.ran(1,100),0,2);
			int ans1=minCost(au.copyMatrix(map));
			int ans2=minCost2(au.copyMatrix(map));
			if (ans1!=ans2){
				System.out.println("opps!");
				System.out.println(ans1);
				System.out.println(ans2);
			}
		}
		System.out.println("ok");
	}


}
