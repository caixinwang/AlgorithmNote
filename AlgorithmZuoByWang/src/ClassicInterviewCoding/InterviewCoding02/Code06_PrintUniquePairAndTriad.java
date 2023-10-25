package ClassicInterviewCoding.InterviewCoding02;

/**
 * 给定一个有序数组arr,给定一个正数aim
 * 1)返回累加和为aim的，所有不同二元组
 * 2)返回累加和为aim的，所有不同三元组
 * 例如1122233356666777788899，aim=9，---》  (1,8)/(2,7)/(3,6)
 */
public class Code06_PrintUniquePairAndTriad {

	public static void printUniquePair(int[] arr, int k) {//左右指针
		if (arr == null || arr.length == 0) return;
		int N = arr.length, l = 0, r = N - 1;
		while (l < r) {
			if (arr[l] + arr[r] < k) {
				l++;//l左边的全部放弃，因为越左边越小，越不可能达到k
			} else if (arr[l] + arr[r] > k) {
				r--;
			} else {
				System.out.printf("%d,%d\n", arr[l], arr[r]);
				l++;
				r--;
			}
		}
	}

	public static void printUniqueTriad(int[] arr, int k) {
		if (arr == null || arr.length == 0) return;
		for (int i = 0; i < arr.length - 2; i++) {//右边至少留两个
			if (i == 0 || arr[i] != arr[i - 1]) {//固定的不能和左边的一样
				printRest(arr, i, i + 1, arr.length-1, k-arr[i]);//注意这里k的传参
			}
		}
	}

	/**
	 * 原理和printUniquePair一样，只不过是在一个数组的范围上进行操作
	 * @param arr 操作的数组
	 * @param f   固定的第一个位置
	 * @param l   操作数组arr的[l...r]范围
	 * @param r   如上
	 * @param k   在arr[l...r]的范围上找到相加为k的二元组，然后前面加上固定的第一个元素arr[f]打印出来
	 */
	public static void printRest(int[] arr, int f, int l, int r, int k) {
		if (arr == null || arr.length == 0 || r - l + 1 < 2) return;
		int  p1 = l, p2 = r;
		while (p1 < p2) {
			if (arr[p1] + arr[p2] < k) {
				p1++;//l左边的全部放弃，因为越左边越小，越不可能达到k
			} else if (arr[p1] + arr[p2] > k) {
				p2--;
			} else {
				System.out.printf("%d,%d,%d\n", arr[f], arr[p1], arr[p2]);
				p1++;
				p2--;
			}
		}
	}

	public static void printArray(int[] arr) {
		for (int i = 0; i != arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	public static void main(String[] args) {
		int sum = 10;
		int[] arr1 = {-8, -4, -3, 0, 1, 2, 4, 5, 8, 9};
		printArray(arr1);
		System.out.println("====");
		printUniquePair(arr1, sum);
		System.out.println("====");
		printUniqueTriad(arr1, sum);

	}

}
