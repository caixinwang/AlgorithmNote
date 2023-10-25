package ClassicInterviewCoding.InterviewCoding12;

import TestUtils.ArrayUtil;

import java.util.Arrays;

public class Code05_MinBoat {

	// 请保证arr有序
	public static int minBoat(int[] arr, int limit) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int N = arr.length;
		 Arrays.sort(arr);
		if(arr[N - 1] > limit) {
			return -1;
		}
		int lessR = -1;
		// 所有的人体重都不超过limit，继续讨论,  <= limit / 2  最右的位置
		for (int i = N  - 1; i >= 0; i--) {
			if (arr[i] <= (limit / 2)) {
				lessR = i;
				break;
			}
		}
		if (lessR == -1) {
			return N;
		}
		//  <= limit / 2
		int L = lessR;
		int R = lessR + 1;
		int noUsed = 0; // 画X的数量统计，画对号的量(加工出来的)
		int solved = 0; // 此时的[L]，让R画过了几个数
		while (R<arr.length&&L>=0) {
			while(L>=0&&arr[L]+arr[R]>limit) {//保证出循环L位置能和R凑
				L--;
				noUsed++;
			}
			if (L>=0&&arr[L]+arr[R]<=limit){
				L--;
				R++;
				solved++;
			}
		}
		noUsed+=L+1;//包括了L越界和没越界的情况
		int fat=arr.length-R;//[r,arr.len-1]都是没配对的胖子，R越界的情况也包括了，刚好是0
		int thin=noUsed+1>>1;//没配对的瘦子两人一条船
		return fat+thin+solved;
	}

	// 请保证arr有序
	public static int minBoat2(int[] arr, int limit) {//zuo
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int N = arr.length;
		 Arrays.sort(arr);
		if(arr[N - 1] > limit) {
			return -1;
		}
		int lessR = -1;
		// 所有的人体重都不超过limit，继续讨论,  <= limit / 2  最右的位置
		for (int i = N  - 1; i >= 0; i--) {
			if (arr[i] <= (limit / 2)) {
				lessR = i;
				break;
			}
		}
		if (lessR == -1) {
			return N;
		}
		//  <= limit / 2
		int L = lessR;
		int R = lessR + 1;
		int noUsed = 0; // 画X的数量统计，画对号的量(加工出来的)
		while (L >= 0) {
			int solved = 0; // 此时的[L]，让R画过了几个数
			while (R < N && arr[L] + arr[R] <= limit) {
				R++;
				solved++;
			}
			// R来到又不达标的位置
			if (solved == 0) {
				noUsed++;
				L--;
			} else { // 此时的[L]，让R画过了solved（>0）个数
				L = Math.max(-1, L - solved);
			}
		}
		int all = lessR + 1;// 左半区总个数  <= limit /2 的区域
		int used = all - noUsed; // 画对号的量
		int moreUnsolved = (N - all) - used; // > limit/2 区中，没搞定的数量
		return used + ((noUsed + 1) >> 1) + moreUnsolved;
	}

	public static int minBoat3(int[] arr, int limit){
		if (arr.length==0||arr==null) return 0;
		int N=arr.length;
		Arrays.sort(arr);
		if (arr[N-1]>limit) return -1;
		int l=0,r=N-1;
		int ans=0;
		while(l<=r){
			if (arr[l]+arr[r]>limit){
				ans++;
				r--;
			}else {
				ans++;
				r--;
				l++;
			}
		}
		return ans;
	}

	public static void testForArr() {//参数为arr
		ArrayUtil arrayUtil = new ArrayUtil();
		int times = 10000;//测试次数
		long time1=0,time2=0;
		boolean isok = true;
		int maxSize = 100;//数组大小在[0~maxSize]随机
		int maxValue = 100;//数组的值在[0,maxValue]随机
        int parameter1=0;//测试函数的参数
        int maxParameter1=150;//参数1在[0,maxParameter1]随机
		int[] t1 = null, t2 = null;

		int res1 = 0, res2 = 0;
		for (int i = 0; i < times; i++) {

            parameter1=arrayUtil.ran(1,maxParameter1);

			t1 = arrayUtil.generateRandomArr(arrayUtil.ran(maxSize), 1,maxValue);//人,重量大于0
			Arrays.sort(t1);
//            t2=arrayUtil.generateRandomArr(arrayUtil.ran(maxSize),maxValue);

//            t1=arrayUtil.generateRandomArr(arrayUtil.ran(maxSize),1,maxValue);//正数数组[1,maxValue]
//            t2 = arrayUtil.generateRandomArr(arrayUtil.ran(maxSize), 1, maxValue);//正数数组[1,maxValue]

			long l = System.currentTimeMillis();
			res1 = minBoat(t1,parameter1);
			time1+=System.currentTimeMillis()-l;
			l=System.currentTimeMillis();
			res2 = minBoat3(t1,parameter1);
			time2+=System.currentTimeMillis()-l;
			if (res1 != res2) {
				isok = false;
				break;
			}
		}
		arrayUtil.printArr(t1);//打印参数
//        arrayUtil.printArr(t2);//打印参数
//        System.out.println("parameter1:"+parameter1);//打印参数
		if (isok) System.out.println("m1 cost "+time1+"ms");
		System.out.println(res1);//针对返回值的操作
		if (isok)System.out.println("m2 cost "+time2+"ms");
		System.out.println(res2);//针对返回值的操作
		System.out.println(isok ? "success" : "fail");
	}
	public static void main(String[] args) {
//		int[] arr = { 1, 2, 2, 2, 2, 3, 3, 3, 3, 3, 4, 4, 4, 4, 5 };
//		int weight = 6;
//		System.out.println(minBoat(arr, weight));
		testForArr();
	}

}
