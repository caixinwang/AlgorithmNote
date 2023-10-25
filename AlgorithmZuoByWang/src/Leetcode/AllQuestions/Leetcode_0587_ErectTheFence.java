package Leetcode.AllQuestions;

import java.util.Arrays;

public class Leetcode_0587_ErectTheFence {
	public int[][] outerTrees(int[][] trees) {
		Arrays.sort(trees,(a,b)->a[0]!=b[0]?a[0]-b[0]:a[1]-b[1]);//两个维度都从小到大排序
		int n=trees.length,top=-1;
		int[][] stack=new int[n<<1][2];
		for(int i=0;i<n;i++){//正着一遍
			while(top>=1&&cross(stack[top-1],stack[top],trees[i])>0) top--;
			stack[++top]=trees[i];
		}
		for(int i=n-1;i>=0;i--){//逆着一遍
			while(top>=1&&cross(stack[top-1],stack[top],trees[i])>0) top--;
			stack[++top]=trees[i];
		}
		Arrays.sort(stack,0,top+1,(a,b)->a[0]!=b[0]?a[0]-b[0]:a[1]-b[1]);//两个维度都从小到大排序
		n = 1;//最终答案的大小为n，初始默认第一个是补充的，从第二个开始验证
		for (int i = 1; i <=top; i++) {
			// 如果i点，x和y，与i-1点，x和y都一样
			// i点与i-1点，在同一个位置，此时，i点不保留
			if (stack[i][0] != stack[i - 1][0] || stack[i][1] != stack[i - 1][1]) {
				stack[n++] = stack[i];
			}
		}
		return Arrays.copyOf(stack, n);
	}

	//AB向量叉乘BC向量：AB向量={B[0]-A[0],B[1]-A[1]}  BC向量={C[0]-B[0],C[1]-B[1]}
	//AB X BC = x1y2-x2y1
	public int cross(int[] A,int[] B,int[] C){
		return (B[0]-A[0])*(C[1]-B[1])-(C[0]-B[0])*(B[1]-A[1]);
	}


}
