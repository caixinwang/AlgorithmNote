package ClassicInterviewCoding.InterviewCoding02;

public class Code04_TrappingRainWater {

	/*
	 * 给定一个正整数数组arr，把arr想象成一个直方图。返回这个直方图如果装水，能装下几格水？
	 * 比如，r={3,1,2,5,2,4}根据值画出的直方图就是容器形状，该容器可以装下5格水
	 */

	/**
	 * 求出中间格子(1...N-2)中任意一个格子上可以存多少水。
	 * 1. i格子存水。求出arr[0...i-1]中最高的柱子和arr[i+1...N-1]中最高的柱子，其中最小的柱子高度min作为瓶颈
	 * 2. i格子可以存 max(min-arr[i],0)，套一个max函数是为了存的水一定大于等于0，不能为负数
	 * @param arr arr中每一个数组代表每个格子上的柱子有多高
	 * @return 返回arr这个地势可以存多少水
	 */
	public static int water1(int[] arr) {//暴力，不使用预处理数组
		if (arr==null||arr.length<=2)return 0;
		int res=0;
		for (int i = 1; i < arr.length-1; i++) {//第一格和最后一格不存水
			int lm=0,rm=0,min=0;//分别代表左边的最高和右边的最高
			for (int j = 0; j < i; j++) {//求左边的最高
				lm = Math.max(lm, arr[j]);
			}
			for (int j=i+1;j<arr.length;j++){//求右边的最高
				rm = Math.max(rm, arr[j]);
			}
			min=Math.min(lm,rm);//瓶颈
			res+=Math.max(0,min-arr[i]);
		}
		return res;
	}

	public static int water2(int[] arr) {//使用两个预处理数组
		if (arr==null||arr.length<=2)return 0;
		int res=0,N=arr.length;
		int[] lm=new int[N],rm=new int[N];//lm[i]代表arr[0...i]的最大值。rm[i]代表arr[i...N-1]的最大值
		lm[0]=arr[0];
		rm[N-1]=arr[N-1];
		for (int i = 1; i < N; i++) {
			lm[i]=Math.max(lm[i-1],arr[i]);//lm[0+i]
			rm[N-1-i]=Math.max(rm[N-i],arr[N-1-i]);//rm[N-i]=rm[N-1-(i-1)]
		}
		for (int i=1;i<N-1;i++){
			res+=Math.max(0,Math.min(lm[i-1],rm[i+1])-arr[i]);
		}
		return res;
	}

	public static int water3(int[] arr) {
		if (arr==null||arr.length<=2)return 0;
		int res=0,N=arr.length,lm=0;
		int[] rm=new int[N];//lm[i]代表arr[0...i]的最大值。rm[i]代表arr[i...N-1]的最大值
		rm[N-1]=arr[N-1];//留rm（数组）是因为我们习惯从左到右累加res，在这个过程中我们可以顺路求lm（变量）
		for (int i = 1; i < N; i++) {
			rm[N-1-i]=Math.max(rm[N-i],arr[N-1-i]);//rm[N-i]=rm[N-1-(i-1)]
		}
		for (int i=1;i<N-1;i++){
			lm = Math.max(lm, arr[i-1]);
			res+=Math.max(0,Math.min(lm,rm[i+1])-arr[i]);
		}
		return res;
	}

	public static int water4(int[] arr) {
		if (arr==null||arr.length<=2)return 0;
		int res=0,l=1,r=arr.length-2,lm=arr[0],rm=arr[arr.length-1];
		while(l<=r){
			if (lm<=rm){//更新L位置
				lm = Math.max(lm, arr[l]);
				res+= Math.max(lm-arr[l++], 0);
			}else {//更新R位置
				rm = Math.max(rm, arr[r]);
				res+= Math.max(rm-arr[r--], 0);
			}
		}
		return res;
	}

	// for test
	public static int[] generateRandomArray(int len, int value) {
		int[] ans = new int[(int) (Math.random() * len) + 1];
		for (int i = 0; i < ans.length; i++) {
			ans[i] = (int) (Math.random() * value) + 1;
		}
		return ans;
	}

	public static void test(){
		int[] arr = generateRandomArray(20, 30);
		int ans1 = water1(arr);
		int ans2 = water2(arr);
		int ans3 = water3(arr);
		int ans4 = water4(arr);
		System.out.println(ans1);
		System.out.println(ans2);
		System.out.println(ans3);
		System.out.println(ans4);
	}

	public static void main(String[] args) {
		int len = 100;
		int value = 200;
		int testTimes = 100;
		System.out.println("test begin");
		for (int i = 0; i < testTimes; i++) {
			int[] arr = generateRandomArray(len, value);
			int ans1 = water1(arr);
			int ans2 = water2(arr);
			int ans3 = water3(arr);
			int ans4 = water4(arr);
			if (ans1 != ans2 || ans3 != ans4 || ans1 != ans3) {
				System.out.println("Oops!");
			}
		}
		System.out.println("test finish");
//		test();
	}

}
