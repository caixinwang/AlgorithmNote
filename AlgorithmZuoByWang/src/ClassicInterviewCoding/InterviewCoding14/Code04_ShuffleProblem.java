package ClassicInterviewCoding.InterviewCoding14;

import TestUtils.ArrayUtil;

import java.util.Arrays;

public class Code04_ShuffleProblem {
	/**
	 * L1L2L3L4R1R2R3R4
	 * 变为
	 * R1L1R2L2R3L3R4L4
	 *
	 * 找最接近的base-1,第一种方法是设置prek和prenum；第二种是自己当做pre,往后多看一位
	 * base-1<=len  等价于base<=len+1  因为要接近但是不超过，所以3*base<=len+1
	 * 3*base可能越界  证明  3*base<=len+1 等价于 base<=(len+1)/3
	 * (len+1)可以被3整除时,等价；(len+1)不能被3整除时, 前面3*base<len+1后面base<=(len+1)/3
	 * 所以3*base<=len+1 等价于 base<=(len+1)/3
	 *
	 * 最终求出来的k是多少，代表要怼几轮。每轮的起点分别为1,3,9,27....。转为下标就是减1
	 * 对于普通的偶数，需要旋转一下再开始循环怼
	 * @param arr 在arr[l...r]上做完美洗牌
	 */
	public static void shuffle(String[] arr){
		int l=0,r=arr.length-1;
		while(l<r){
			int len=r-l+1,base=1,k=0;//找到base-1最接近len的
			for (;base<=(len+1)/3;base*=3,k++);//找到最接近len但是不超过len的3^k-1
			int mid=l+(r-l>>1),N=(base-1)/2;
			rotate(arr,l+N,mid,mid+N);
			cycle(arr,l,l+2*N-1);
			l+=2*N;
		}
	}

	/**
	 * cur代表起始位置，1,3,9....，你只需要减去1然后加上起始的偏移量即可得到循环怼的起点。
	 * @param arr -
	 * @param s 循环怼大区间的开始位置
	 * @param e 结束位置
	 */
	private static void cycle(String[] arr, int s, int e) {
		//写法1：从尾部出发的到头结束do while写法（相当于到经过头两次）
//		for (int size=1;s+size-1<=e;size*=3){
//			int head=s+(size-1);
//			String pre=arr[head];
//			do {
//				int next=next(s,e,head);
//				String t=arr[next];
//				arr[next]=pre;
//				pre=t;
//				head=next;
//			}while (head!=s+(size-1));
//		}
		//写法2：既然要经过start两次,那么就直接使用计数作为循环条件
		for (int base=1;base <= e-s+1;base*=3){
			int start=s+(base-1),head=start;
			int cnt=0;
			String pre=arr[head];
			while(cnt!=2){
				if(head==start) cnt++;
				int next = next(s, e, head);
//				String t=arr[head];
//				arr[head]=pre;
				String t=arr[next];
				arr[next]=pre;
				pre=t;
				head=next;
			}
		}
	}

	/**
	 * 我们知道从0开始或者从1开始的next,算出来之后需要找一个参照点,可以是cur,也可以是起点（0或1）,
	 * 	用算出来的数值减去这个参照点得到偏移量。在任意一个s~e的范围内,如果参照点是起点,那么就是s+偏移量
	 * 	如果参照点是cur,那么就是cur+偏移量
	 * @param s 在[s,e]这个区间循环怼，cur是这个区间里面的下标，请问应该到哪里去？
	 * @param e -
	 * @param cur -
	 * @return -
	 *
	 */
	private static int next(int s,int e,int cur){
		int N=(e-s+1)/2;
		return s+(nextFrom0(N,cur-(s-0))-0);
//		return cur+(nextFrom0(N,cur-(s-0))-(cur-(s-0)));
//		return s+(nextFrom1(N,cur-(s-1))-1);
//		return cur+(nextFrom1(N,cur-(s-1))-(cur-(s-1)));
	}

	//    N
	//0 1 2 3   ->  2 0 3 1
	private static int nextFrom0(int N,int cur){
		if (cur>=N){
			return cur-N<<1;
		}else {
			return cur<<1|1;
		}
	}

	//   N
	// 1 2 3 4  =>  3 1 4 2
	private static int nextFrom1(int N,int cur){
		if (cur<=N){
			return cur<<1;
		}else {
			return (cur-N)*2 - 1;
		}
	}

	private static void rotate(String[] arr, int l, int mid, int r) {
		reverse(arr,l,mid);
		reverse(arr,mid+1,r);
		reverse(arr,l,r);
	}

	public static void reverse(String[] arr,int l,int r){
		for (;l<r;l++,r--) swap(arr,l,r);
	}

	public static void swap(String[] nums, int i, int j) {
		String tmp = nums[i];
		nums[i] = nums[j];
		nums[j] = tmp;
	}
	
	public static void main(String[] args) {
		testShuffle();

	}


	private static void testShuffle(){
		String[] arr1=new String[]{},arr2=new String[]{};
		for (int i = 0; i < 5000; i++) {
			arr1=new String[g()];
			arr2=new String[arr1.length];
			for (int i1 = 0,z=1; i1 < arr1.length/2; i1++,z++) {
				arr1[i1]="L"+z;
			}
			for (int i1 = arr1.length/2,z=1; i1 < arr1.length; i1++,z++) {
				arr1[i1]="R"+z;
			}
			System.arraycopy(arr1,0,arr2,0,arr1.length);
			shuffle(arr1);
			shuffle(arr2);
			if (!Arrays.equals(arr2,arr1)) {
				System.out.println("error!");
				break;
			}
		}
		System.out.println(Arrays.toString(arr1));

	}

	private static int g(){
		int l=2,r=100,res;
		do {res=l+(int)((r-l+1)*Math.random());}
		while((res&1)!=0);
		return res;
	}
}
