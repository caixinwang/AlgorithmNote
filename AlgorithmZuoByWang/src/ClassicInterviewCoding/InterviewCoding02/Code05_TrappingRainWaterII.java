package ClassicInterviewCoding.InterviewCoding02;

import java.util.Comparator;
import java.util.PriorityQueue;

public class Code05_TrappingRainWaterII {

	public static class Node {
		public int value;
		public int row;
		public int col;

		public Node(int v, int r, int c) {
			value = v;
			row = r;
			col = c;
		}

	}

	public static class NodeComparator implements Comparator<Node> {

		@Override
		public int compare(Node o1, Node o2) {
			return o1.value - o2.value;
		}

	}

	public static int trapRainWater(int[][] heightMap) {
		if (heightMap==null||heightMap.length==0||heightMap[0]==null||heightMap[0].length==0) return 0;
		int res=0,N=heightMap.length,M=heightMap[0].length,r=0,c=0,v=0,limit=0;
		boolean[][] isin=new boolean[N][M];
		PriorityQueue<Node> heap=new PriorityQueue<>(new NodeComparator());
		while(c<M){//先往下再往右。连着矩阵中心对称的那个点也加进来
			heap.add(new Node(heightMap[r][c],r,c));
			heap.add(new Node(heightMap[N-1-r][M-1-c],N-1-r,M-1-c));
			isin[r][c]=true;
			isin[N-1-r][M-1-c]=true;
			if (r<N-1){//注意这里是N-1
				r++;
			}else {
				c++;
			}
		}
		while(!heap.isEmpty()){
			Node pop = heap.poll();
			r= pop.row;
			c=pop.col;
			v=pop.value;
			limit = Math.max(limit, v);//更新瓶颈
			if (r+1<N&&!isin[r+1][c]){//进栈，改isin，结算
				heap.add(new Node(heightMap[r+1][c],r+1,c));
				isin[r+1][c]=true;
				res+=Math.max(0,limit-heightMap[r+1][c]);
			}
			if (r-1>=0&&!isin[r-1][c]){
				heap.add(new Node(heightMap[r-1][c],r-1,c));
				isin[r-1][c]=true;
				res+=Math.max(0,limit-heightMap[r-1][c]);
			}
			if (c+1<M&&!isin[r][c+1]){
				heap.add(new Node(heightMap[r][c+1],r,c+1));
				isin[r][c+1]=true;
				res+=Math.max(0,limit-heightMap[r][c+1]);
			}
			if (c-1>=0&&!isin[r][c-1]){
				heap.add(new Node(heightMap[r][c-1],r,c-1));
				isin[r][c-1]=true;
				res+=Math.max(0,limit-heightMap[r][c-1]);
			}

		}
		return res;
	}

	public static void main(String[] args) {
		int[][] matrix=new int[][]{//12
				{7,5,9,9,9,6,8,8},
				{9,1,2,1,9,1,2,1},
				{9,9,9,9,7,7,7,7}
		};
		System.out.println(trapRainWater(matrix));
	}

}
