package ClassicInterviewCoding.InterviewCoding09;

import TestUtils.ArrayUtil;

import java.util.TreeSet;

public class Code02_MaxSubArraySumLessOrEqualK {


	public static int getMaxLessOrEqualK(int[] arr, int K) {//暴力
		int res=Integer.MIN_VALUE,N=arr.length;
		for (int j = 0; j < N; j++) {
			for (int i = j; i >= 0 ; i--) {
				int sum=0;
				for (int k = i; k <=j  ; k++) {
					sum+=arr[k];
				}
				if (sum<=K) res = Math.max(res, sum);
			}
		}
		return res;
	}

	/**
	 * 为保证完全等价转换，set里面得有0，使得[0...j]的情况不会漏掉.
	 * 转化为找>=sum[0...j]-K且最接近的某一个前缀的值m,这样一来子数组的对应的累加和就是sum[0...j]-m
	 * @param arr 找到最接近K的子数组累加和.数组不能为空且长度要大于0
	 * @param K K
	 * @return 返回小于等于K且最接近K的子数组累加和
	 */
	public static int getMaxLessOrEqualK2(int[] arr, int K) {
		TreeSet<Integer> set=new TreeSet<>();//只关心值
		set.add(0);//使得i等于0的时候sum[0-1]的值对应为0，不会漏掉情况
		int N=arr.length,res=Integer.MIN_VALUE,sum=0;//sum的含义是0...j的累加和
		for (int j = 0; j < N; j++) {//以j位置结尾
			sum+=arr[j];//sum[0...j]
			Integer m=set.ceiling(sum-K);
			if (m!=null) res = Math.max(res,sum-m);//如果m为空，说明以j结尾的子串没有答案
			set.add(sum);
		}
		return res;
	}

	public static void testForArr() {//参数为arr
		ArrayUtil arrayUtil = new ArrayUtil();
		int times = 1000;//测试次数
		boolean isok = true;
		int maxSize = 100;//数组大小在[0~maxSize]随机
		int maxValue = 100;//数组的值在[0,maxValue]随机
        int parameter1=0;//测试函数的参数
        int maxParameter1=10000;//参数1在[0,maxParameter1]随机
		int[] f1 = arrayUtil.generateRandomArr(arrayUtil.ran(maxSize), maxValue);
//        int[] f= arrayUtil.generateRandomArr(arrayUtil.ran(maxSize),1, maxValue);//正数数组[1,maxValue]
//        int[] f2= arrayUtil.generateRandomArr(arrayUtil.ran(maxSize), maxValue);

		int res1 = 0, res2 = 0;
		for (int i = 0; i < times; i++) {
            parameter1=arrayUtil.ran(maxParameter1);
			int[] t1 = arrayUtil.generateRandomArr(arrayUtil.ran(maxSize), maxValue);
//            int[] t1=arrayUtil.generateRandomArr(arrayUtil.ran(maxSize),1,maxValue);//正数数组[1,maxValue]
//            int[] t2=arrayUtil.generateRandomArr(arrayUtil.ran(maxSize),maxValue);
			f1 = t1;
//            f2=t2;
			res1 = getMaxLessOrEqualK(t1,parameter1);
			res2 = getMaxLessOrEqualK2(t1,parameter1);
			if (res1 != res2) {
				isok = false;
				break;
			}
		}
		arrayUtil.printArr(f1);//打印参数
//        arrayUtil.printArr(f2);//打印参数
        System.out.println("parameter1:"+parameter1);//打印参数
		System.out.println(res1);//针对返回值的操作
		System.out.println(res2);//针对返回值的操作
		System.out.println(isok ? "success" : "fail");
	}

	public static void main(String[] args) {
		testForArr();
	}
}
