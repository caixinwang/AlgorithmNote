package ClassicInterviewCoding.InterviewCoding07;

public class Code01_MinLengthForSort {

	public static int getMinLength(int[] arr) {
		if (arr==null||arr.length<2) return 0;
		int max=Integer.MIN_VALUE,min=Integer.MAX_VALUE;//记录遍历位置的左侧或者右侧的最大值和最小值
		int N=arr.length,right=0,left=N-1;//right和left为打叉的最边边
		for (int i = 0; i < N; i++) {//一个for循环同时控制从左往右和从右往左的过程
			if (arr[i]>=max){//从左往右增加的，比左侧的最大值要大就是达标的
				max=arr[i];
			}else {//不达标
				right=i;
			}
			if (arr[N-1-i]<=min){//从右往左减少的，比右侧的最小值要小就是达标的
				min=arr[N-1-i];
			}else {
				left=N-1-i;
			}
		}
		return right-left+1;
	}

	public static void main(String[] args) {
		int[] arr = { 1, 2, 4, 7, 10, 11, 7, 12, 6, 7, 16, 18, 19 };
		System.out.println(getMinLength(arr));

	}

}
