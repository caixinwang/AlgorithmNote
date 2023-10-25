package Leetcode.AllQuestions;

import TestUtils.ArrayUtil;

import java.util.*;

public class Leetcode_0548_SplitArrayEithEqualSum {//都是正数的时候IC06
	//这题有正有负有0，单调性丧失，N^3的解也直接过了

	public static boolean splitArray(int[] nums) {
		if (nums.length < 7) {
			return false;
		}
		int[] sumLeftToRight = new int[nums.length];
		int[] sumRightToLeft = new int[nums.length];
		int s = 0;
		for (int i = 0; i < nums.length; i++) {
			sumLeftToRight[i] = s;
			s += nums[i];
		}
		s = 0;
		for (int i = nums.length - 1; i >= 0; i--) {
			sumRightToLeft[i] = s;
			s += nums[i];
		}
		for (int i = 1; i < nums.length - 5; i++) {
			for (int j = nums.length - 2; j > i + 3; j--) {
				if (sumLeftToRight[i] == sumRightToLeft[j] && find(sumLeftToRight, sumRightToLeft, i, j)) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean find(int[] sumLeftToRight, int[] sumRightToLeft, int l, int r) {
		int s = sumLeftToRight[l];
		int prefix = sumLeftToRight[l + 1];
		int suffix = sumRightToLeft[r - 1];
		for (int i = l + 2; i < r - 1; i++) {
			int s1 = sumLeftToRight[i] - prefix;
			int s2 = sumRightToLeft[i] - suffix;
			if (s1 == s2 && s1 == s) {
				return true;
			}
		}
		return false;
	}

	public static boolean splitArray2(int[] nums) {
		int n=nums.length;
		if (n<7) return false;
		int[] presum=new int[n+1];//sum[l...r]=presum[r+1]-presum[l]
		for (int i = 0; i < n; i++) {
			presum[i+1]=presum[i]+nums[i];
		}
		for (int i1=1;i1<n-5;i1++){
			for (int i2=i1+2;i2<n-3;i2++){
				if (getsum(presum,0,i1-1)!=getsum(presum,i1+1,i2-1)) continue;
				for (int i3=i2+2;i3<n-1;i3++){
					if (getsum(presum,i1+1,i2-1)!=getsum(presum,i2+1,i3-1)) continue;
					if (getsum(presum,i2+1,i3-1)==getsum(presum,i3+1,n-1)) return true;
				}
			}
		}
		return false;
	}

	public static int getsum(int[] presum,int l,int r){
		return presum[r+1]-presum[l];
	}

	public static boolean splitArray3(int[] nums) {//先确定两边再确定中间速度会变快，因为跳过了极端数据
		int n=nums.length;
		if (n<7) return false;
		int[] presum=new int[n+1];//sum[l...r]=presum[r+1]-presum[l]
		for (int i = 0; i < n; i++) {
			presum[i+1]=presum[i]+nums[i];
		}
		for (int i1=1;i1<n-5;i1++){
			for (int i3=n-2;i3>=i1+4;i3--){
				if (getsum(presum,0,i1-1)!=getsum(presum,i3+1,n-1)) continue;
				for (int i2=i1+2;i2<i3-1;i2++){
					if (getsum(presum,i1+1,i2-1)==getsum(presum,i2+1,i3-1)
							&&getsum(presum,i2+1,i3-1)==getsum(presum,i3+1,n-1)) return true;
				}
			}
		}
		return false;
	}

	public static boolean splitArray4(int[] nums) {//N^2 复杂度最优 先确定中间，再找两边
		int n=nums.length;
		if (n<7) return false;
		int[] presum=new int[n+1];//sum[l...r]=presum[r+1]-presum[l]
		for (int i = 0; i < n; i++) {
			presum[i+1]=presum[i]+nums[i];
		}
		for (int i2=3;i2<n-3;i2++){
			HashSet<Integer> set=new HashSet<>();
			for (int i1 = 1; i1 < i2 - 1; i1++) {
				if (getsum(presum,0,i1-1)==getsum(presum,i1+1,i2-1)) set.add(getsum(presum,0,i1-1));
			}
			if (set.isEmpty()) continue;
			for (int i3=i2+2;i3<n-1;i3++){
				if (getsum(presum,i2+1,i3-1)==getsum(presum,i3+1,n-1)
						&&set.contains(getsum(presum,i2+1,i3-1))) return true;
			}
		}
		return false;
	}

	public static void testForArr() {//参数为arr
		ArrayUtil arrayUtil = new ArrayUtil();
		int times = 10;//测试次数
		long time1 = 0, time2 = 0;
		boolean isok = true;
		int maxSize = 100;//数组大小在[0~maxSize]随机
		int maxValue = 100;//数组的值在[0,maxValue]随机
//        int parameter1=0;//测试函数的参数
//        int maxParameter1=100;//参数1在[0,maxParameter1]随机
		int[] t1 = null, t2 = null;

		boolean res1 = false, res2 = false;
		for (int i = 0; i < times; i++) {

//            parameter1=arrayUtil.ran(maxParameter1);

			t1 = arrayUtil.generateRandomArr(3000, -maxValue,maxValue);
//            t2=arrayUtil.generateRandomArr(arrayUtil.ran(maxSize),maxValue);

//            t1=arrayUtil.generateRandomArr(arrayUtil.ran(maxSize),1,maxValue);//正数数组[1,maxValue]
//            t2 = arrayUtil.generateRandomArr(arrayUtil.ran(maxSize), 1, maxValue);//正数数组[1,maxValue]

			long l = System.currentTimeMillis();
			res1 = splitArray2(t1);
			time1 += System.currentTimeMillis() - l;
			l = System.currentTimeMillis();
			res2 = splitArray4(t1);
			time2 += System.currentTimeMillis() - l;
			if (res1 != res2) {
				isok = false;
				break;
			}
		}
		arrayUtil.printArr(t1);//打印参数
//        arrayUtil.printArr(t2);//打印参数
//        System.out.println("parameter1:"+parameter1);//打印参数
		if (isok) System.out.println("m1 cost " + time1 + "ms");
		System.out.println(res1);//针对返回值的操作
		if (isok) System.out.println("m2 cost " + time2 + "ms");
		System.out.println(res2);//针对返回值的操作
		System.out.println(isok ? "success" : "fail");
	}

	public static void main(String[] args) {
		testForArr();
	}

}
