package Leetcode.AllQuestions;

import java.util.Arrays;
import java.util.HashSet;

public class Leetcode_0268_MissingNumber {
	//排序，然后遍历，第一个不是前面+1的数就是缺失的数
	public int missingNumber(int[] arr) {
		Arrays.sort(arr);
		int i=0;
		for (i = 0; i < arr.length; i++) {//正常来说i数就应该在i位置，如果i位置不是i，那么缺的就是i
			if (arr[i]!=i) break;
		}
		return i;
	}

	//HashSet
	public int missingNumber2(int[] arr) {
		HashSet<Integer> set=new HashSet<>();
		for (int num:arr) set.add(num);
		int n=0;
		for (; n <=arr.length; n++) {
			if (!set.contains(n)) break;
		}
		return n;
	}

	//arr里面存在的数，异或上0~arr.len的数，结果就是缺的数
	public int missingNumber3(int[] arr) {
		int eor=0;
		for (int i = 0; i < arr.length; i++) {
			eor^=arr[i];
			eor^=i;
		}
		eor^=arr.length;
		return eor;
	}
	//如果0~arr.len的数都存在，那么累加和就是(len*(len+1))/2,减去arr的累加和就是缺的数
	public int missingNumber4(int[] arr) {
		int sum=(arr.length*(arr.length+1))/2;
		for (int n:arr) sum-=n;
		return sum;
	}

	//借用第41题的思想，找到第一个缺失的正数。改为这题，这题问的是从0开始缺失最小是啥。
	//可以随便改，下次换成从k开始，缺失的最小的数是啥，也能做！！！从k开始连续，那么一个值x就应该是x-k位置
	public int missingNumber5(int[] arr) {
		int N=arr.length;
		int l=0;//[0,l)的数从0开始是连续的,总共l个数连续，0...l-1是连续的，所以下一个点正的数就是l.
		int r=N;//[r,N-1]是垃圾区,扔掉了N-r个数，所以前面还剩下r个数，也就是说，最多从0...r-1==>也就是r代表此时期望的最大的数为r-1
		while(l<r){
			if (arr[l]==l){//点挺正，直接动
				l++;
			}else if (l<arr[l]&&arr[l]<=r-1&&arr[arr[l]]!=arr[l]){//确定这个值在范围内，并且之前没有出现过这个值
				swap(arr,l,arr[l]);
			}else {//垃圾
				swap(arr,l,--r);
			}
		}
		return l;//[0...l)连续,总共l个数,凑齐了0,1...,l-1，所以缺l
	}

	public void swap(int[] arr,int a,int b){
		int t=arr[a];
		arr[a]=arr[b];
		arr[b]=t;
	}




}
