package Leetcode.AllQuestions;

public class Leetcode_0189_RotateArray {

	/**
	 * 123456 k=2 ===》 561234 。
	 * @param arr -
	 * @param k 整体向右平移k。也等价于右边k个整体变到左边，左边整体变到右边，可以用数组翻转实现
	 */
	public void rotate(int[] arr, int k) {
		if (arr==null||arr.length<=1) return;
		k%=arr.length;
		reverse(arr,arr.length-k,arr.length-1);
		reverse(arr,0,arr.length-k-1);
		reverse(arr,0,arr.length-1);
	}

	public void reverse(int[] arr,int l,int r){
		while(l<r){
			swap(arr,l++,r--);
		}
	}

	public void swap(int[] arr,int a,int b){
		int t=arr[a];
		arr[a]=arr[b];
		arr[b]=t;
	}

	/**
	 * 下标循环怼，最多怼gcd(N,k)轮。或者用一个count计数。我们采用使用count来计数
	 * 下标循环怼的题目特点就是可以直接计算出一个下标应该去哪里。
	 */
	public void rotate2(int[] arr, int k) {//计数
		if (arr==null||arr.length<=1) return;
		int N=arr.length;
		k%=N;
		int count=0;
		for (int start = 0; start < N&&count<N; start++) {
			int cur=start;//当前位置
			int next=-1;
			int pre=-1;//next位置应该赋值的值,迭代依靠t
			int t=arr[start];//next位置值被覆盖前先存到t上
			do {
				next=(cur+k)%N;
				pre=t;//next位置要赋的值
				t=arr[next];//赋值之前先存起来
				arr[next]=pre;//赋值
				cur=next;//迭代
				count++;
			}while (cur!=start);
		}
	}

	public static void rotate3(int[] arr, int k) {//用gcd怼精确的轮数
		if (arr==null||arr.length<=1) return;
		int N=arr.length;
		k%=N;
		int round=gcd(N,k);
		for (int start = 0; start < round; start++) {//精确控制次数
			int cur=start;//当前位置
			int next=-1;
			int pre=-1;//next位置应该赋值的值,迭代依靠t
			int t=arr[start];//next位置值被覆盖前先存到t上
			do {
				next=(cur+k)%N;
				pre=t;//next位置要赋的值
				t=arr[next];//赋值之前先存起来
				arr[next]=pre;//赋值
				cur=next;//迭代
			}while (cur!=start);
		}
	}

	public static int gcd(int a,int b){
		return b==0?a:gcd(b,a%b);
	}

	public static void main(String[] args) {
		int[] arr=new int[]{ 1,2,3,4,5,6,7};
		rotate3(arr,3);
	}

}
