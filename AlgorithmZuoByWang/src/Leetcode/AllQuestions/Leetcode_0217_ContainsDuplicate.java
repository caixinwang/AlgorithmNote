package Leetcode.AllQuestions;

import java.util.Arrays;
import java.util.HashSet;

//Code0287 一起看
public class Leetcode_0217_ContainsDuplicate {//如果不让你用额外空间，这题用堆排序
	public boolean containsDuplicate(int[] arr) {
		HashSet<Integer> set=new HashSet<>();
		for(int i:arr){
			if (set.contains(i)) return true;
			set.add(i);
		}
		return false;
	}

	public boolean containsDuplicate2(int[] arr) {//排序
		Arrays.sort(arr);
		for (int i=1;i<arr.length;i++){
			if (arr[i]==arr[i-1]) return true;
		}
		return false;
	}
}
