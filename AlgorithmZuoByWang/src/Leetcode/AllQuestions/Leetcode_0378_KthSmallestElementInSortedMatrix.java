package Leetcode.AllQuestions;

import java.util.PriorityQueue;

/**
 * 准备一个小根堆，把左上角放进去。每次弹出一个，然后把它的下边和右边放进去。然后实现一个不重复放的机制。弹够k个，就是答案。
 * 为什么放右边的和下边的？因为行和列都有序，第二小的数只可能出现在左边和右边。
 * 不重复放：搞一个boolean类型的矩阵即可。
 */
public class Leetcode_0378_KthSmallestElementInSortedMatrix {

	public int kthSmallest(int[][] matrix, int k) {//合并k个升序链表的类似做法
		PriorityQueue<int[]> queue=new PriorityQueue<>((a,b)->a[0]-b[0]);
		int N=matrix.length,M=matrix[0].length;
		for (int i=0;i<N;i++){
			queue.add(new int[]{matrix[i][0],i,0});
		}
		for (int i=0;i<k-1;i++){
			int[] node=queue.poll();
			if (++node[2]<M) {
				node[0] = matrix[node[1]][node[2]];
				queue.add(node);
			}
		}
		return queue.poll()[0];
	}

	public int kthSmallest2(int[][] matrix, int k) {//行、列、值 封装为一个node
		PriorityQueue<int[]> queue=new PriorityQueue<>((a,b)->a[0]-b[0]);
		int N=matrix.length,M=matrix[0].length;
		boolean[][] isEnter=new boolean[N][M];
		queue.add(new int[]{matrix[0][0],0,0});
		isEnter[0][0]=true;
		for (int i=0;i<k-1;i++){
			int[] node=queue.poll();
			int r=node[1],c=node[2];
			if (r+1<N&&!isEnter[r+1][c]){
				isEnter[r+1][c]=true;
				queue.add(new int[]{matrix[r+1][c],r+1,c});
			}
			if (c+1<M&&!isEnter[r][c+1]){
				isEnter[r][c+1]=true;
				queue.add(new int[]{matrix[r][c+1],r,c+1});
			}
		}
		return queue.poll()[0];
	}




}
