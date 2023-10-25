package ClassicInterviewCoding.InterviewCoding16;

public class Code04_HanoiProblem {

	public static int step1(int[] arr) {
		if (arr == null || arr.length == 0) {
			return -1;
		}
		return process(arr, arr.length - 1, 1,3,2);
	}

	// 目标是:  把0~i的圆盘，从from全部挪到to上
	// 返回，根据arr中的状态arr[0..i]，它是最优解的第几步？
	// f(i, 3 , 2, 1)    f(i, 1, 3, 2)    f(i, 3, 1, 2)
	public static int process(int[] arr, int i, int from, int to, int other) {
		if (i==-1) return 0;//base case
		if (arr[i]==other) return -1;//0~i要整体去to，i这个最大的圆盘肯定不会经过other的
		if (arr[i]==from){
			return process(arr,i-1,from,other,to);
		}else {
			//0~i-1已经全部从from 到 other了，完成了i层汉诺塔问题,(2^n)-1
			int rest=process(arr,i-1,other,to,from);
			if (rest==-1) return -1;
			return (1<<i)-1+1+rest;
		}
	}

	public static int step2(int[] arr) {
		if (arr == null || arr.length == 0) {
			return -1;
		}
		int from = 1;
		int mid = 2;
		int to = 3;
		int i = arr.length - 1;
		int res = 0;
		int tmp = 0;
		while (i >= 0) {
			if (arr[i] != from && arr[i] != to) {
				return -1;
			}
			if (arr[i] == to) {
				res += 1 << i;
				tmp = from;
				from = mid;
			} else {
				tmp = to;
				to = mid;
			}
			mid = tmp;
			i--;
		}
		return res;
	}

	public static void main(String[] args) {

		int[] arr = { 3, 3, 2, 1 };
		System.out.println(step1(arr));
		System.out.println(step2(arr));

	}
}
