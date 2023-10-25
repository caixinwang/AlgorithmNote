package ClassicInterviewCoding.InterviewCoding06;

import java.util.HashMap;

public class Code01_MostEOR {//加题

	public static int mostEOR(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int N = arr.length;
		int[] dp = new int[N]; // dp[i] = 0
		HashMap<Integer, Integer> map = new HashMap<>();
		map.put(0, -1);
		int sum = 0;
		for (int i = 0; i < arr.length; i++) {
			sum ^= arr[i];
			if (map.containsKey(sum)) {
				int pre = map.get(sum);
				dp[i] = pre == -1 ? 1 : (dp[pre] + 1);
			}
			if (i > 0) {
				dp[i] = Math.max(dp[i - 1], dp[i]);
			}
			map.put(sum, i);
		}
		return dp[dp.length - 1];
	}



	/**
	 * 这种方法和上面的方法，区别在于初始化结点有没有把0这个key放到map里面。
	 * 如果不放，那么将导致第一次出现sum等于0的时候，dp[i]=0，这是不对的，因为至少等于1.
	 * 不提前放0，你的sum就算等于0了，那么也会因为在map中找不到0而跳过第一个for循环，然后最后dp[i-1]可能刚好是0，最后被赋值为0.
	 * 要注意特殊情况，sum为0时就是一种特殊情况，如果之前没有记录过0，就可能产生错误。sum为0，dp[i]至少是1.
	 */
	public static int mostEOR2(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int N = arr.length;
		int[] dp = new int[N]; // dp[i] = 0
		dp[0]=arr[0]==0?1:0;
		HashMap<Integer, Integer> map = new HashMap<>();
		int sum = arr[0];
		map.put(sum,0);
		for (int i = 1; i < arr.length; i++) {
			sum ^= arr[i];
//			if (sum==0&&!map.containsKey(sum)){
//				map.put(0,i);
//			}
			if (sum==0) dp[i]=1;//这一句可以替代上面一句
			if (map.containsKey(sum)) {
				int pre = map.get(sum);
				dp[i] = dp[pre] + 1;
			}
			dp[i] = Math.max(dp[i - 1], dp[i]);
			map.put(sum, i);
		}
		return dp[dp.length - 1];
	}

	// for test
	public static int comparator(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int[] eors = new int[arr.length];
		int eor = 0;
		for (int i = 0; i < arr.length; i++) {
			eor ^= arr[i];
			eors[i] = eor;
		}
		int[] mosts = new int[arr.length];
		mosts[0] = arr[0] == 0 ? 1 : 0;
		for (int i = 1; i < arr.length; i++) {
			mosts[i] = eors[i] == 0 ? 1 : 0;
				for (int j = 0; j < i; j++) {
					if ((eors[i] ^ eors[j]) == 0) {
						mosts[i] = Math.max(mosts[i], mosts[j] + 1);
					}
				}
				mosts[i] = Math.max(mosts[i], mosts[i - 1]);
		}
		return mosts[mosts.length - 1];
	}

	// for test
	public static int[] generateRandomArray(int maxSize, int maxValue) {
		int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) ((maxValue + 1) * Math.random());
		}
		return arr;
	}

	// for test
	public static void printArray(int[] arr) {
		if (arr == null) {
			return;
		}
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	public static void test1(){
		int testTime = 5000;
		int maxSize = 100;
		int maxValue = 100;
		boolean succeed = true;
		for (int i = 0; i < testTime; i++) {
			int[] arr = generateRandomArray(maxSize, maxValue);
			int res = mostEOR2(arr);
			int comp = comparator(arr);
			if (res != comp) {
				succeed = false;
				printArray(arr);
				System.out.println(res);
				System.out.println(comp);
				break;
			}
		}
		System.out.println(succeed ? "Nice!" : "Fucking fucked!");
	}

	public static void test2(){
		int[] arr={1,1};
		int res = mostEOR2(arr);
		int comp = comparator(arr);
	}

	// for test
	public static void main(String[] args) {
//		test2();
		test1();
	}


}
