package ClassicInterviewCoding.InterviewCoding07;

import java.util.Arrays;
import java.util.HashSet;

public class Code02_SmallestUnFormedSum {

	/**
	 * 暴力递归的实现。思路就是利用暴力递归把所有的能得到的sum都加到一个set里面
	 * 然后从min开始，一个一个加上去看看哪个数不在set里面，哪个就是最小不可组成和
	 * @param arr 正数数组
	 * @return 返回最小不可组成和
	 */
	public static int unformedSum1(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 1;
		}
		HashSet<Integer> set = new HashSet<Integer>();
		process(arr, 0, 0, set);
		int min = Integer.MAX_VALUE;
		for (int i = 0; i != arr.length; i++) {
			min = Math.min(min, arr[i]);
		}
		for (int i = min + 1; i != Integer.MIN_VALUE; i++) {
			if (!set.contains(i)) {
				return i;
			}
		}
		return 0;
	}

	/**
	 *
	 * @param arr 正数数组
	 * @param i 当前来到i位置，0~i-1位置的数已经选择是否累加了
	 * @param sum 0~i-1的位置经过选择之后累加和为sum
	 * @param set 将arr能凑成的sum全部add到set里面
	 */
	public static void process(int[] arr, int i, int sum, HashSet<Integer> set) {
		if (i == arr.length) {//当i来到arr.len的时候说明0~N-1位置已经选择完毕，加成了sum，加到set里面
			set.add(sum);
			return;
		}
		process(arr, i + 1, sum, set);//i位置不加到sum里面
		process(arr, i + 1, sum + arr[i], set);//i位置加到sum里面
	}

	/**
	 * 上面的暴力递归里面的set是用来记录一个sum能不能凑成的。
	 * 所以这个set的功能我们可以用dp数组的返回值boolean来代替。dp[i][j]=true代表set里面有j这个sum
	 * 上面这个暴力递归不好改，我们直接抽象出dp[i][j]的含义:arr[0...i]可以凑到j
	 * 最小不可组成和就是dp最后一行从左到右第一个为false对应的那个j
	 * @param arr 正数数组
	 * @return 返回最小不可组成和
	 */
	public static int unformedSum2(int[] arr) {
		if (arr == null || arr.length == 0) return -1;
		int N=arr.length,sumAll=0,min=Integer.MAX_VALUE;
		for (int i=0;i<N;i++) {
			sumAll += arr[i];
			min = Math.min(min, arr[i]);
		}
		boolean[][] dp=new boolean[N][sumAll+1];//dp[i][j]代表arr[0...i]可以凑到j
		dp[0][0]=true;//不选就能凑到0元
		dp[0][arr[0]]=true;//第一行初始化
		for (int i = 1; i < N; i++) {//第一列初始化
			dp[i][0]=true;//什么都不选
		}
		for (int i=1;i<N;i++){//dp[i][j]
			for (int j = 1; j <= sumAll; j++) {
				dp[i][j]=dp[i-1][j]||j-arr[i]>=0&&dp[i-1][j-arr[i]];
			}
		}
		for (int i=min;i<=sumAll;i++){//找最后一行从第min列开始找第一个为false对应的列
			if (!dp[N-1][i]) return i;
		}
		return sumAll+1;//说明min到SumAll都可以，那么就根据定义返回sumAll+1
	}

	/**
	 * 因为第一个元素是1，所以在初始状态range为1的时候可以维持住为0~1都可以凑成这个含义。
	 * 如果第一个是2，最开始0~2是凑不出的，range维持不住含义。只有在第一个数是1的情况下才维持得住含义
	 * @param arr 正数数组，并且肯定含有1这个数字
	 * @return 返回最小不可组成和
	 */
	public static int unformedSum3(int[] arr) {
		if (arr==null||arr.length==0) return -1;
		Arrays.sort(arr);//先排序
		int N=arr.length,range=1;//先把arr[0]这个固定是1的数算进去
		for (int i=1;i<N;i++){//从第二个元素开始拉拢
			if (range+1>=arr[i]){//arr[0]肯定是1,1>=1成立，不需要额外对开头位置进行处理
				range+=arr[i];
			}else {
				return range+1;
			}
		}
		return range+1;
	}

	public static int[] generateArray(int len, int maxValue) {
		int[] res = new int[len];
		if (res.length>=1) res[0]=1;//第一个元素是1
		for (int i = 1; i != res.length; i++) {
			res[i] = (int) (Math.random() * maxValue) + 1;
		}
		return res;
	}

	public static void printArray(int[] arr) {
		for (int i = 0; i != arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	public static void main(String[] args) {
		int len = 27;
		int max = 30;
		int[] arr = generateArray(len, max);
		printArray(arr);
		long start = System.currentTimeMillis();
		System.out.println(unformedSum1(arr));
		long end = System.currentTimeMillis();
		System.out.println("cost time: " + (end - start) + " ms");
		System.out.println("======================================");

		start = System.currentTimeMillis();
		System.out.println(unformedSum2(arr));
		end = System.currentTimeMillis();
		System.out.println("cost time: " + (end - start) + " ms");
		System.out.println("======================================");

		System.out.println("set arr[0] to 1");
		start = System.currentTimeMillis();
		System.out.println(unformedSum3(arr));
		end = System.currentTimeMillis();
		System.out.println("cost time: " + (end - start) + " ms");

	}
}
