package ClassicInterviewCoding.InterviewCoding08;

import java.util.Arrays;
import java.util.HashSet;

public class Code05_LongestIntegratedLength {

	public static int getLIL1(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int len = 0;
		// O(N^3 * log N)
		for (int start = 0; start < arr.length; start++) { // 子数组所有可能的开头
			for (int end = start; end < arr.length; end++) { // 开头为start的情况下，所有可能的结尾
				// arr[s ... e]
				// O(N * logN)
				if (isIntegrated(arr, start, end)) {
					len = Math.max(len, end - start + 1);
				}
			}
		}
		return len;
	}

	public static boolean isIntegrated(int[] arr, int left, int right) {
		int[] newArr = Arrays.copyOfRange(arr, left, right + 1); // O(N)
		Arrays.sort(newArr); // O(N*logN)
		for (int i = 1; i < newArr.length; i++) {
			if (newArr[i - 1] != newArr[i] - 1) {
				return false;
			}
		}
		return true;
	}

	public static int getLIL2(int[] arr) {
		if (arr==null||arr.length==0) return 0;
		int max=Integer.MIN_VALUE,min=Integer.MAX_VALUE,res=0;
		HashSet<Integer> set=new HashSet<>();
		for (int l = 0; l < arr.length; l++) {
			for (int r = l; r < arr.length; r++) {//验证每一个开头的子数组
				if (set.contains(arr[r])){
					set.clear();
					break;
				}
				set.add(arr[r]);
				max = Math.max(max, arr[r]);
				min = Math.min(min, arr[r]);
				if (max-min==r-l){
					res=r-l+1;
				}
			}
		}
		return res;
	}

	public static void main(String[] args) {
		int[] arr = { 5, 5, 3, 2, 6, 4, 3 };
		System.out.println(getLIL1(arr));
		System.out.println(getLIL2(arr));

	}

}
