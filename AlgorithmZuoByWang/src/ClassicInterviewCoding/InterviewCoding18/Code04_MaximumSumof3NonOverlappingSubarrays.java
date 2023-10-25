package ClassicInterviewCoding.InterviewCoding18;

import TestUtils.ArrayUtil;

public class Code04_MaximumSumof3NonOverlappingSubarrays {

	static ArrayUtil util=new ArrayUtil();
	public static int[] maxSumOfThreeSubarrays(int[] nums, int k) {
		if (nums==null||k<1||nums.length<3*k) return null;
		int N = nums.length;
		int[] range = new int[N];
		int[] left = new int[N];
		int sum = 0;
		for (int i = 0; i < k; i++) {
			sum += nums[i];
		}
		range[0] = sum;
		left[k - 1] = 0;
		int max = sum;
		for (int i = k; i < N; i++) {
			sum = sum - nums[i - k] + nums[i];
			range[i - k + 1] = sum;
			left[i] = left[i - 1];
			if (sum > max) {
				max = sum;
				left[i] = i - k + 1;
			}
		}
		sum = 0;
		for (int i = N - 1; i >= N - k; i--) {
			sum += nums[i];
		}
		max = sum;
		int[] right = new int[N];
		right[N - k] = N - k;
		for (int i = N - k - 1; i >= 0; i--) {
			sum = sum - nums[i + k] + nums[i];
			right[i] = right[i + 1];
			if (sum > max) {
				max = sum;
				right[i] = i;
			}
		}
		int a = 0;
		int b = 0;
		int c = 0;
		max = 0;
		for (int i = k; i < N - 2 * k + 1; i++) { // 中间一块的起始点  (0...k-1)选不了   i == N-1
			int part1 = range[left[i - 1]];
			int part2 = range[i];
			int part3 = range[right[i + k]];
			if (part1 + part2 + part3 > max) {
				max = part1 + part2 + part3;
				a = left[i - 1];
				b = i;
				c = right[i + k];
			}
		}
		return new int[] { a, b, c };
	}

	public static int maxSumOfThreeSubarrays2(int[] arr, int k) {
		if (arr==null||k<1||arr.length<3*k) return 0;
		int N=arr.length;
		int[] left=new int[N];//left[i]代表从arr[0...i]范围上子数组长度为k的最大累加和
		int[] right=new int[N];//left[i]代表从arr[i...N-1]范围上子数组长度为k的最大累加和
		int[] sumk=new int[N];//sumk[i]代表arr[i-(k-1)....i] k个数的累加和,也就是i往前的k个数的累加和
		int sumLeft=0;
		int sumRight=0;
		for (int i = 0; i < k; i++) {//把前k个数先加上
			sumLeft+=arr[i];
			sumRight+=arr[N-1-i];
		}
		int maxLeft=sumLeft;
		int maxRight=sumRight;
		left[k-1]=sumLeft;
		right[N-1-(k-1)]=sumRight;
		sumk[k-1]=sumLeft;
		for (int i = k; i < N; i++) {
			sumLeft+=(arr[i]-arr[i-k]);//加上新的减去老的，维持住k大小的子数组
			sumRight+=(arr[N-1-i]-arr[N-1-(i-k)]);
			sumk[i]=sumLeft;
			maxRight = Math.max(maxRight, sumRight);
			maxLeft = Math.max(maxLeft, sumLeft);
			left[i]=maxLeft;
			right[N-1-i]=maxRight;
		}
		int res=Integer.MIN_VALUE;
		for (int i = k+(k-1); i <= N-1-k ; i++) {
			res = Math.max(res,sumk[i]+left[i-k]+right[i+1]);
		}

		return  res;
	}

	public static int[] maxSumOfThreeSubarrays3(int[] arr, int k) {
		if (arr==null||k<1||arr.length<3*k) return null;
		int N=arr.length;
		int[] left=new int[N];//left[i]代表从arr[0...i]范围上子数组长度为k的最大累加和子数组的左边的开头
		int[] right=new int[N];//left[i]代表从arr[i...N-1]范围上子数组长度为k的最大累加和子数组的左边的开头
		int[] sumk=new int[N];//sumk[i]代表arr[i....i+(k-1)] k个数的累加和,开头为i往右推k个
		int sumLeft=0;
		int sumRight=0;
		for (int i = 0; i < k; i++) {//把前k个数先加上
			sumLeft+=arr[i];
			sumRight+=arr[N-1-i];
		}
		int maxLeft=sumLeft;
		int maxLeftIndex=0;
		int maxRight=sumRight;
		int maxRightIndex=N-1-(k-1);
		left[0]=maxLeftIndex;
		right[N-1-(k-1)]=maxRightIndex;
		sumk[0]=sumLeft;
		for (int i = k; i < N; i++) {
			sumLeft+=(arr[i]-arr[i-k]);//加上新的减去老的，维持住k大小的子数组
			sumRight+=(arr[N-1-i]-arr[N-1-(i-k)]);
			sumk[i-(k-1)]=sumLeft;
			if (sumLeft>maxLeft){
				maxLeft=sumLeft;
				maxLeftIndex=i-(k-1);
			}
			if (sumRight>maxRight){
				maxRight=sumRight;
				maxRightIndex=N-1-i;
			}
			left[i]=maxLeftIndex;
			right[N-1-i]=maxRightIndex;
		}
		int a=0,b=0,c=0;
		int res=Integer.MIN_VALUE;
		for (int i = k; i <= N-1-k-(k-1) ; i++) {
			if (sumk[left[i-1]]+sumk[i]+sumk[right[i+k]]>res){
				a=left[i-1];
				b=i;
				c=right[i+k];
				res=sumk[left[i-1]]+sumk[i]+sumk[right[i+k]];
			}
		}
		return new int[]{a,b,c};

	}

	public static boolean isEqual(int[] arr1, int[] arr2) {
		if (arr1 == null || arr2 == null) {//判断arr1和arr2是否有其中一个为空
			if (arr1 == null & arr2 == null)
				return true;
			else
				return false;
		}
		if (arr1.length != arr2.length)//长度不相等就不需要比较
			return false;
		int i;
		for (i = 0; i < arr1.length && arr1[i] == arr2[i]; i++) ;
		if (i == arr1.length)//退出循环的条件有两个，一个是i=len，一个是数组1不等于数组2.
			return true;
		else
			return false;
	}

	public static void testForArr() {//参数为arr
		ArrayUtil arrayUtil = new ArrayUtil();
		int times = 10000;//测试次数
		long time1=0,time2=0;
		boolean isok = true;
		int maxSize = 100;//数组大小在[0~maxSize]随机
		int maxValue = 100;//数组的值在[0,maxValue]随机
        int parameter1=0;//测试函数的参数
        int maxParameter1=20;//参数1在[0,maxParameter1]随机
		int[] t1 = null, t2 = null;

		int[] res1=null , res2=null ;
		for (int i = 0; i < times; i++) {

            parameter1=arrayUtil.ran(maxParameter1);

			t1 = arrayUtil.generateRandomArr(arrayUtil.ran(maxSize), maxValue);
//            t2=arrayUtil.generateRandomArr(arrayUtil.ran(maxSize),maxValue);

//            t1=arrayUtil.generateRandomArr(arrayUtil.ran(maxSize),1,maxValue);//正数数组[1,maxValue]
//            t2 = arrayUtil.generateRandomArr(arrayUtil.ran(maxSize), 1, maxValue);//正数数组[1,maxValue]

			long l = System.currentTimeMillis();
			res1 = maxSumOfThreeSubarrays(t1,parameter1);
			time1+=System.currentTimeMillis()-l;
			l=System.currentTimeMillis();
			res2 = maxSumOfThreeSubarrays3(t1,parameter1);
			time2+=System.currentTimeMillis()-l;
			if (!isEqual(res1,res2)) {
				isok = false;
				break;
			}
		}
		arrayUtil.printArr(t1);//打印参数
//        arrayUtil.printArr(t2);//打印参数
        System.out.println("parameter1:"+parameter1);//打印参数
		if (isok) System.out.println("m1 cost "+time1+"ms");
//		System.out.println(res1);//针对返回值的操作
		arrayUtil.printArr(res1);
		if (isok)System.out.println("m2 cost "+time2+"ms");
		arrayUtil.printArr(res2);
		System.out.println(isok ? "success" : "fail");
	}

	public static void main(String[] args) {
//		int[] nums = { 9, 8, 7, 6, 2, 2, 2, 2,4,7,5,4,3,13,1,5,5,8,7,9,7,7,6,5,5,4 };
//		int k = 4;
//		int[] ans = maxSumOfThreeSubarrays(nums, k);
//		System.out.println(ans[0] + "," + ans[1] + "," + ans[2]);
//		System.out.println(maxSumOfThreeSubarrays(nums, k));
//		System.out.println(maxSumOfThreeSubarrays2(nums, k));
		testForArr();

	}

}
