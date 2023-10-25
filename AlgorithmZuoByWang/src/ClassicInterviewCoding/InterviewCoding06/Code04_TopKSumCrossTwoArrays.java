package ClassicInterviewCoding.InterviewCoding06;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Code04_TopKSumCrossTwoArrays {

	public static class Node{
		public int index1;//arr1的下标
		public int index2;//arr2的下标
		public int sum;//arr1[index1]+arr2[index2]

		public Node(int index1, int index2, int sum) {
			this.index1 = index1;
			this.index2 = index2;
			this.sum = sum;
		}
	}

	public static class NodeComparator implements Comparator<Node>{//从大到小---由于构造大根堆
		@Override
		public int compare(Node o1, Node o2) {
			return o2.sum-o1.sum;
		}
	}

	public static int[] topKSum(int[] arr1, int[] arr2, int topK){
		if (arr1==null||arr2==null|| arr1.length==0||arr2.length==0||topK<1||topK> arr1.length*arr2.length) return null;
		PriorityQueue<Node> heap=new PriorityQueue<>(new NodeComparator());//大根堆
		int[] res=new int[topK];
		int index=0;//和数组搭配使用
		int N=arr1.length,M=arr2.length;
		boolean[][] isEnter=new boolean[N][M];
		heap.add(new Node(N-1,M-1,arr1[N-1]+arr2[M-1]));
		while(index<res.length){//一直填写，直到res满
			Node cur = heap.poll();//弹出最大，加入答案
			res[index++]=cur.sum;
			if (cur.index1-1>=0&&!isEnter[cur.index1 - 1][cur.index2]) {
				isEnter[cur.index1 - 1][cur.index2]=true;
				heap.add(new Node(cur.index1 - 1, cur.index2, arr1[cur.index1 - 1] + arr2[cur.index2]));
			}
			if (cur.index2-1>=0&&!isEnter[cur.index1][cur.index2-1]) {
				isEnter[cur.index1][cur.index2-1]=true;
				heap.add(new Node(cur.index1, cur.index2 - 1, arr1[cur.index1] + arr2[cur.index2 - 1]));
			}
		}
		return res;
	}

	// For test, this method is inefficient but absolutely right
	public static int[] topKSumTest(int[] arr1, int[] arr2, int topK) {
		int[] all = new int[arr1.length * arr2.length];
		int index = 0;
		for (int i = 0; i != arr1.length; i++) {
			for (int j = 0; j != arr2.length; j++) {
				all[index++] = arr1[i] + arr2[j];
			}
		}
		Arrays.sort(all);
		int[] res = new int[Math.min(topK, all.length)];
		index = all.length - 1;
		for (int i = 0; i != res.length; i++) {
			res[i] = all[index--];
		}
		return res;
	}

	public static int[] generateRandomSortArray(int len) {
		int[] res = new int[len];
		for (int i = 0; i != res.length; i++) {
			res[i] = (int) (Math.random() * 50000) + 1;
		}
		Arrays.sort(res);
		return res;
	}

	public static void printArray(int[] arr) {
		for (int i = 0; i != arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	public static boolean isEqual(int[] arr1, int[] arr2) {
		if (arr1 == null || arr2 == null || arr1.length != arr2.length) {
			return false;
		}
		for (int i = 0; i != arr1.length; i++) {
			if (arr1[i] != arr2[i]) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		int a1Len = 5000;
		int a2Len = 4000;
		int k = 20000000;
		int[] arr1 = generateRandomSortArray(a1Len);
		int[] arr2 = generateRandomSortArray(a2Len);
		long start = System.currentTimeMillis();
		int[] res = topKSum(arr1, arr2, k);
		long end = System.currentTimeMillis();
		System.out.println(end - start + " ms");

		start = System.currentTimeMillis();
		int[] absolutelyRight = topKSumTest(arr1, arr2, k);
		end = System.currentTimeMillis();
		System.out.println(end - start + " ms");

		System.out.println(isEqual(res, absolutelyRight));

	}

}
