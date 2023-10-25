package ClassicInterviewCoding.InterviewCoding02;

/**
 * 给定一个数组arr长度为N,你可以把任意长度大于0且小于N的前缀作为左部分，剩下的作为右部分。
 * 但是每种划分下都有左部分的最大值和右部分的最大值，请返回最大的，左部分最大值减去右部分最大值的绝对值。
 */
public class Code03_MaxABSBetweenLeftAndRight {

	public static int maxABS1(int[] arr) {//暴力O(N²)
		int res = Integer.MIN_VALUE;
		int maxLeft = 0;
		int maxRight = 0;
		for (int i = 0; i<arr.length-1; i++) {//0~i划分出来做左部分,不能一整个数组作为左部分
			maxLeft = Integer.MIN_VALUE;
			for (int j = 0; j <= i; j++) {//找出左部分的最大值
				maxLeft = Math.max(arr[j], maxLeft);
			}
			maxRight = Integer.MIN_VALUE;
			for (int j = i + 1; j<arr.length; j++) {//找出右部分的最大值
				maxRight = Math.max(arr[j], maxRight);
			}
			res = Math.max(Math.abs(maxLeft - maxRight), res);
		}
		return res;
	}

	/**
	 * 对暴力做法做了加速，使用预处理数组快速找出左部分和右部分的最大值。
	 * larr[i]代表arr[0...i]中的最大值
	 * rarr[i]代表arr[i...N-1]的最大值
	 */
	public static int maxABS2(int[] arr) {//O(N)
		int[] lArr = new int[arr.length];
		int[] rArr = new int[arr.length];
		lArr[0] = arr[0];
		rArr[arr.length - 1] = arr[arr.length - 1];
		for (int i = 1; i < arr.length; i++) {
			lArr[i] = Math.max(lArr[i - 1], arr[i]);
		}
		for (int i = arr.length - 2; i > -1; i--) {
			rArr[i] = Math.max(rArr[i + 1], arr[i]);
		}
		int max = 0;
		for (int i = 0; i < arr.length - 1; i++) {
			max = Math.max(max, Math.abs(lArr[i] - rArr[i + 1]));
		}
		return max;
	}

	public static int maxABS3(int[] arr) {//最快速的方法 -- O(N)
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < arr.length; i++) {
			max = Math.max(arr[i], max);//找到全局最大值
		}
		return max - Math.min(arr[0], arr[arr.length - 1]);
	}

	public static int[] generateRandomArray(int length) {
		int[] arr = new int[length];
		for (int i = 0; i != arr.length; i++) {
			arr[i] = (int) (Math.random() * 1000) - 499;
		}
		return arr;
	}

	public static void main(String[] args) {
		int[] arr = generateRandomArray(200);
		System.out.println(maxABS1(arr));
		System.out.println(maxABS2(arr));
		System.out.println(maxABS3(arr));
	}

}
