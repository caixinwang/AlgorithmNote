package ClassicInterviewCoding.InterviewCoding20;

public class Code04_MergeRecord {
	
	/*
	 * 腾讯原题
	 * 
	 * 给定整数power，给定一个数组arr，给定一个数组reverse。含义如下：
	 * arr的长度一定是2的power次方，reverse中的每个值一定都在0~power范围。
	 * 例如power = 2, arr = {3, 1, 4, 2}，reverse = {0, 1, 0, 2}
	 * 任何一个在前的数字可以和任何一个在后的数组，构成一对数。可能是升序关系、相等关系或者降序关系。
	 * 比如arr开始时有如下的降序对：(3,1)、(3,2)、(4,2)，一共3个。
	 * 接下来根据reverse对arr进行调整：
	 * reverse[0] = 0, 表示在arr中，划分每1(2的0次方)个数一组，然后每个小组内部逆序，那么arr变成
	 * [3,1,4,2]，此时有3个逆序对。
	 * reverse[1] = 1, 表示在arr中，划分每2(2的1次方)个数一组，然后每个小组内部逆序，那么arr变成
	 * [1,3,2,4]，此时有1个逆序对
	 * reverse[2] = 0, 表示在arr中，划分每1(2的0次方)个数一组，然后每个小组内部逆序，那么arr变成
	 * [1,3,2,4]，此时有1个逆序对。
	 * reverse[3] = 2, 表示在arr中，划分每4(2的2次方)个数一组，然后每个小组内部逆序，那么arr变成
	 * [4,2,3,1]，此时有4个逆序对。
	 * 所以返回[3,1,1,4]，表示每次调整之后的逆序对数量。
	 * 
	 * 输入数据状况：
	 * power的范围[0,20]
	 * arr长度范围[1,10的7次方]
	 * reverse长度范围[1,10的6次方]
	 * 
	 * */

	public static int[] reversePair1(int[] originArr, int[] reverseArr, int power) {
		int[] ans = new int[reverseArr.length];
		for (int i = 0; i < reverseArr.length; i++) {
			reverseArray(originArr, 1 << (reverseArr[i]));
			ans[i] = countReversePair(originArr);
		}
		return ans;
	}

	public static void reverseArray(int[] originArr, int teamSize) {
		if (teamSize < 2) {
			return;
		}
		for (int i = 0; i < originArr.length; i += teamSize) {
			reversePart(originArr, i, i + teamSize - 1);
		}
	}

	public static void reversePart(int[] arr, int L, int R) {
		while (L < R) {
			int tmp = arr[L];
			arr[L++] = arr[R];
			arr[R--] = tmp;
		}
	}

	public static int countReversePair(int[] originArr) {
		int ans = 0;
		for (int i = 0; i < originArr.length; i++) {
			for (int j = i + 1; j < originArr.length; j++) {
				if (originArr[i] > originArr[j]) {
					ans++;
				}
			}
		}
		return ans;
	}

	public static int[] reversePair2(int[] originArr, int[] reverseArr, int power) {
		int[] reverseOriginArr=new int[originArr.length];
		System.arraycopy(originArr, 0, reverseOriginArr, 0, reverseOriginArr.length);
		reversePart(reverseOriginArr,0,reverseOriginArr.length-1);
		int[] dp1=new int[power+1];
		int[] dp2=new int[power+1];
		process(originArr,0,originArr.length-1,power,dp1);
		process(reverseOriginArr,0,reverseOriginArr.length-1,power,dp2);
		int[] ans=new int[reverseArr.length];
		for (int i = 0; i < reverseArr.length; i++) {
			int pow=reverseArr[i];
			for (int p = 0; p <= pow; p++) {// <=pow的位置全部都要交换
				int t=dp1[p];
				dp1[p]=dp2[p];
				dp2[p]=t;
			}
			for (int val : dp1) {
				ans[i] += val;
			}
		}
		return ans;
	}

	/**
	 * 从本质来说，归并排序就是用分治法去求一个arr中的所有逆序对。一个数组拆分为两半，逆序对来自于左边和右边，以及跨越左右边。
	 * merge相当于就是求了跨越左右两边的。但是这里我们不是求总的逆序对个数。我们要的是按照某个大小来分组，求所有组的组内的逆序对
	 * 个数之和。根据组的大小2^power 填到record[power]数组里面。事实上就是把原本的逆序对的总数分散到了Record数组里面了。
	 * 我递归调用的时候，子问题没有把自己内部的总数告诉我，所以我们在父问题存的时候只存了跨越两个区间的逆序对。Record的总和
	 * 才是原本的逆序对的总数。由于我们的Record只存了，跨越区间的逆序对。那么如果我将2^power大小的组每个都去reverse了，
	 * 大于2^power的那些大组不受影响，因为我们只记录了跨越区间的逆序对。你只需要去更小的区间去交换正序对和逆序对即可
	 * @param arr 统计数组[L...R]范围内的数，并且将它每组内的逆序对的总数之和，填到record[power]上
	 * @param l -
	 * @param r -
	 * @param power arr[L..R]上面数的总数为 2^power
	 * @param record -
	 */
	public static void process(int[] arr, int l, int r, int power, int[] record) {
		if (l==r) return;//如果L==R，那么power==1，此时没有逆序对，不需要到填到record里面
		int mid=l+(r-l>>1);
		process(arr,l,mid,power-1,record);
		process(arr,mid+1,r,power-1,record);
		record[power]+=merge(arr,l,mid,r);
	}

	public static int merge(int[] arr, int l, int m, int r) {
		int p1=l,p2=m+1;
		int[] help=new int[r-l+1];
		for (int i = 0; i < help.length; i++) {
			help[i]=arr[l+i];
		}
		int i=0;
		int ans=0;//这一个小组的逆序对个数
		while(p1<=m&&p2<=r){
			if (arr[p1]>arr[p2]){
				ans+=m-p1+1;//差别就在这里，我p1>p2了，arr[p1~m]全部都大于p2
				help[i++]=arr[p2++];
			}else {
				help[i++]=arr[p1++];
			}
		}
		while(p1<=m){
			help[i++]=arr[p1++];
		}
		while(p2<=r){
			help[i++]=arr[p2++];
		}
		for (int k = 0; k < help.length; k++) {
			arr[l+k]=help[k];
		}
		return ans;
	}

	// for test
	public static int[] generateRandomOriginArray(int power, int value) {
		int[] ans = new int[1 << power];
		for (int i = 0; i < ans.length; i++) {
			ans[i] = (int) (Math.random() * value);
		}
		return ans;
	}

	// for test
	public static int[] generateRandomReverseArray(int len, int power) {
		int[] ans = new int[len];
		for (int i = 0; i < ans.length; i++) {
			ans[i] = (int) (Math.random() * (power + 1));
		}
		return ans;
	}

	// for test
	public static void printArray(int[] arr) {
		System.out.println("arr size : " + arr.length);
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	// for test
	public static int[] copyArray(int[] arr) {
		if (arr == null) {
			return null;
		}
		int[] res = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			res[i] = arr[i];
		}
		return res;
	}

	// for test
	public static boolean isEqual(int[] arr1, int[] arr2) {
		if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
			return false;
		}
		if (arr1 == null && arr2 == null) {
			return true;
		}
		if (arr1.length != arr2.length) {
			return false;
		}
		for (int i = 0; i < arr1.length; i++) {
			if (arr1[i] != arr2[i]) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		int powerMax = 8;
		int msizeMax = 10;
		int value = 30;
		int testTime = 50000;
		System.out.println("test begin");
		for (int i = 0; i < testTime; i++) {
			int power = (int) (Math.random() * powerMax) + 1;
			int msize = (int) (Math.random() * msizeMax) + 1;
			int[] originArr = generateRandomOriginArray(power, value);
			int[] originArr1 = copyArray(originArr);
			int[] originArr2 = copyArray(originArr);
			int[] reverseArr = generateRandomReverseArray(msize, power);
			int[] reverseArr1 = copyArray(reverseArr);
			int[] reverseArr2 = copyArray(reverseArr);
			int[] ans1 = reversePair1(originArr1, reverseArr1, power);
			int[] ans2 = reversePair2(originArr2, reverseArr2, power);
			if (!isEqual(ans1, ans2)) {
				System.out.println("Oops!");
			}
		}
		System.out.println("test finish!");

		powerMax = 20;
		msizeMax = 1000000;
		value = 1000;
		int[] originArr = generateRandomOriginArray(powerMax, value);
		int[] reverseArr = generateRandomReverseArray(msizeMax, powerMax);
		// int[] ans1 = reversePair1(originArr1, reverseArr1, powerMax);
		long start = System.currentTimeMillis();
		reversePair2(originArr, reverseArr, powerMax);
		long end = System.currentTimeMillis();
		System.out.println("run time : " + (end - start) + " ms");
	}

}
