package Leetcode.AllQuestions;

/**
 * 用快排的partition找到一个数组的中位数。然后根据这个中位数去做一个荷兰国旗问题，小于中位数、等于中位数、大于中位数
 * 这题我们其实想要的就是一个数拿大的，一个数拿小的。如果我们用中位数partition了，我们肯定可以做到。
 * 假设排完之后是：并且数目是偶数个
 * 3  2  1  4  4  7  6  8
 * L1 L2 L3 L4 R1 R2 R3 R4
 * 想到了完美洗牌问题，把上面的顺序变为
 * L1 R1 L2 R2 L3 R3 L4 R4
 * 3  4  2  7  1  6  4  8
 * 这样就符合题目的要求了
 *要注意，完美洗牌问题是这样放的
 * R1 L1 R2 L2 R3 L3 R4 L4
 * 但是只需要两两互换就可以变为题目需要的。
 *
 * 如果是奇数个的时候
 * 3  3  2  1  4  4  7  6  8
 *    L1 L2 L3 L4 R1 R2 R3 R4
 *第一个位置不动，剩下的位置直接完美洗牌
 * 3  4  3  7  2  6  1  8  4
 *    R1 L1 R2 L2 R3 L3 R4 L4
 *
 */
public class Leetcode_0324_WiggleSortII {

	// 时间复杂度O(N)，额外空间复杂度O(1)
	public void wiggleSort(int[] nums) {
		if (nums == null || nums.length < 2) {
			return;
		}
		int N = nums.length;
		findIndexNum(nums, 0, nums.length - 1, N / 2);
		if ((N & 1) == 0) {
			shuffle(nums, 0, nums.length - 1);
			reverse(nums, 0, nums.length - 1);
		} else {
			shuffle(nums, 1, nums.length - 1);
		}
	}

	private int findIndexNum(int[] arr, int l, int r, int k) {
		while(l<r){
			swap(arr,l,l+(int)(Math.random()*(r-l+1)));
			int[] p=partition(arr,l,r);
			if (p[0]<=k&&k<=p[1]) return arr[k];
			else if (k>p[1]) l=p[1]+1;
			else l=p[0]-1;
		}
		return arr[l];
	}

	private int[] partition(int[] arr,int l,int r){
		int pivot=arr[l];
		int less=l,more=r+1,i=l+1;
		while(i<more){
			if (arr[i]==pivot) i++;
			else if (arr[i]<pivot) swap(arr,++less,i++);
			else swap(arr,--more,i);
		}
		swap(arr,l,less);
		return new int[]{less,more-1};
	}


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
	 *
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

	public static void main(String[] args) {
		int s=2;
		int e=9;
		int cur=s;
		for (int i = 0; i <= e-s; i++,cur++) {
			System.out.println(next(s,e,cur));
		}
	}
}
