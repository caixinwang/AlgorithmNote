package ClassicInterviewCoding.InterviewCoding04;

public class Code06_SubArrayMaxSum {

	/**
	 * 用一个sum一路累加，一路更新max。sum<0了就重置为0
	 * @param arr 返回arr的子数组最大累加和
	 */
	public static int maxSum(int[] arr) {
		if (arr==null||arr.length==0) return Integer.MIN_VALUE;
		int max=Integer.MIN_VALUE,sum=0;
		for (int i = 0; i < arr.length; i++) {
			sum+=arr[i];
			max=Math.max(max,sum);
			if (sum<0) sum=0;
		}
		return max;
	}

	public static void printArray(int[] arr) {
		for (int i = 0; i != arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	public static void main(String[] args) {
		int[] arr1 = { -2, -3, -5, 40, -10, -10, 100, 1 };
		System.out.println(maxSum(arr1));

		int[] arr2 = { -2, -3, -5, 0, 1, 2, -1 };
		System.out.println(maxSum(arr2));

		int[] arr3 = { -2, -3, -5, -1 };
		System.out.println(maxSum(arr3));

	}

}
