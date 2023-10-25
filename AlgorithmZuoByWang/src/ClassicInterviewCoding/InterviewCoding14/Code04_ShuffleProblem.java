package ClassicInterviewCoding.InterviewCoding14;

import TestUtils.ArrayUtil;

import java.util.Arrays;

public class Code04_ShuffleProblem {

	public static void shuffle(int[] arr) {
		if (arr != null && arr.length != 0 && (arr.length & 1) == 0) {
			shuffle(arr, 0, arr.length - 1);
		}
	}

	/**
	 * L1L2L3L4R1R2R3R4
	 * 变为
	 * R1L1R2L2R3L3R4L4
	 * base-1 -> len  等价于base -> len+1  因为要接近但是不超过，所以3*base<=len+1 防止越界 =》 base<=(len+1)/3
	 * 最终求出来的k是多少，代表要怼几轮。每轮的起点分别为1,3,9,27....。转为下标就是减1
	 * 对于普通的偶数，需要旋转一下再开始循环怼
	 * @param arr 在arr[l...r]上做完美洗牌
	 * @param l -
	 * @param r -
	 */
	public static void shuffle(int[] arr,int l,int r){
		while(l<r){
			int len=r-l+1,base=1,k=0;//找到base-1最接近len的
			for (;base<=(len+1)/3;base*=3,k++);//找到最接近len但是不超过len的3^k-1
			int mid=l+(r-l>>1),N=(base-1)/2;
			rotate(arr,l+N,mid,mid+N);
			cycle(arr,l,l+2*N-1,k);
			l+=2*N;
		}
	}

	/**
	 * cur代表起始位置，1,3,9....，你只需要减去1然后加上起始的偏移量即可得到循环怼的起点。
	 * @param arr -
	 * @param start 开始位置start
	 * @param end 结束位置
	 * @param k 循环怼k次
	 */
	private static void cycle(int[] arr, int start, int end, int k) {
		for (int cur=1;k>0;k--,cur*=3){
			int s=start+(cur-1);
			int next=0,pre=0,t=arr[s];
			do {
				next=next(start,end,s);
				pre=t;
				t=arr[next];
				arr[next]=pre;
				s=next;
			}while (s!=start+(cur-1));
		}
	}

	/**
	 * 我们知道[0,N-1]里面的i去哪里的，只需要算出来，然后加上s这个起始偏移量即可
	 * @param s 在[s,e]这个区间循环怼，cur是这个区间里面的下标，请问应该到哪里去？
	 * @param e -
	 * @param cur -
	 * @return -
	 */
	private static int next(int s,int e,int cur){
		int N=(e-s+1)/2;
		int i=cur-s;
		return s+(i>=N?i-N<<1:i<<1|1);
	}

	private static void rotate(int[] arr, int l, int mid, int r) {
		reverse(arr,l,mid);
		reverse(arr,mid+1,r);
		reverse(arr,l,r);
	}

	public static void reverse(int[] arr,int l,int r){
		for (;l<r;l++,r--) swap(arr,l,r);
	}

	public static void swap(int[] nums, int i, int j) {
		int tmp = nums[i];
		nums[i] = nums[j];
		nums[j] = tmp;
	}


	static ArrayUtil arrayUtil=new ArrayUtil();
	public static void test1(){
		int[] arr=new int[]{1,2,3,4,5,6,7,8};
//		reverse(arr,1,5);
		rotate(arr,1,7,3);
		arrayUtil.printArr(arr);
	}
	public static void test2(){
		int[] arr=new int[]{1,2,3,4,5,6,7,8};
		cycle(arr,0,7,2);
		arrayUtil.printArr(arr);
	}
	public static void test3(){
		for (int i = 0; i < 50; i++) {
			int[] arr = generateArray();
			Arrays.sort(arr);
			arrayUtil.printArr(arr);
			wiggleSort(arr);
			arrayUtil.printArr(arr);
			System.out.println("==============================");
			if (!isValidWiggle(arr)) {
				System.out.println("ooops!");
				printArray(arr);
				break;
			}
		}
	}

	public static void wiggleSort(int[] arr) {
		if (arr == null || arr.length == 0) {
			return;
		}
		// 假设这个排序是额外空间复杂度O(1)的，当然系统提供的排序并不是，你可以自己实现一个堆排序
		Arrays.sort(arr);
		if ((arr.length & 1) == 1) {
			shuffle(arr, 1, arr.length - 1);
		} else {
			shuffle(arr, 0, arr.length - 1);
			for (int i = 0; i < arr.length; i += 2) {
				int tmp = arr[i];
				arr[i] = arr[i + 1];
				arr[i + 1] = tmp;
			}
		}
	}

	// for test
	public static boolean isValidWiggle(int[] arr) {
		for (int i = 1; i < arr.length; i++) {
			if ((i & 1) == 1 && arr[i] < arr[i - 1]) {
				return false;
			}
			if ((i & 1) == 0 && arr[i] > arr[i - 1]) {
				return false;
			}
		}
		return true;
	}

	// for test
	public static void printArray(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	// for test
	public static int[] generateArray() {
		int len = (int) (Math.random() * 10) * 2;
		int[] arr = new int[len];
		for (int i = 0; i < len; i++) {
			arr[i] = (int) (Math.random() * 100);
		}
		return arr;
	}
	public static void main(String[] args) {
//		test1();
//		test2();
		test3();

	}
}
