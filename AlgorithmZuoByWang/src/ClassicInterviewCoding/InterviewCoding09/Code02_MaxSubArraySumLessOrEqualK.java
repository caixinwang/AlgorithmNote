package ClassicInterviewCoding.InterviewCoding09;

import TestUtils.ArrayUtil;

import java.util.TreeSet;

public class Code02_MaxSubArraySumLessOrEqualK {


	public static int getMaxLessOrEqualK(int[] arr, int K) {//暴力尝试
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
	 * s[j]-s[i]<=K 等价于 s[i]>=s[j]-K
	 * 为保证完全等价转换，set里面得有0，使得0~j范围的累加和的情况不会漏掉.
	 * s为前缀和数组 s[i]为arr前i个元素的累加和
	 * 转化为找>=s[j]-K且最接近的某一个前缀的值sum[x]
	 * @param arr 找到<=K的最大子数组累加和.数组不能为空且长度要大于0
	 * @param K K
	 * @return 返回小于等于K且最接近K的子数组累加和
	 */
	public static int getMaxLessOrEqualK2(int[] arr, int K) {
		TreeSet<Integer> set=new TreeSet<>();//只关心值
		set.add(0);
		int N=arr.length,res=Integer.MIN_VALUE,sum=0;//sum的含义是前缀和数组的每一个元素
		for (int j = 0; j < N; j++) {//以j位置结尾
			sum+=arr[j];
			Integer i=set.ceiling(sum-K);//取到>=s[j]-K的最小值
			if (i!=null) res = Math.max(res,sum-i);//如果m为空，说明以j结尾的子串没有答案
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
