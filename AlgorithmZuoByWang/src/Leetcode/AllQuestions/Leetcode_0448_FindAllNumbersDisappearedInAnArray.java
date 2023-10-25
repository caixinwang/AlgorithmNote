package Leetcode.AllQuestions;

import java.util.ArrayList;
import java.util.List;
//这题的下标循环怼是由值决定去的位置，所以使用发货的方式。如果是由下标决定去哪里，那么就要使用常规的下标循环怼。
public class Leetcode_0448_FindAllNumbersDisappearedInAnArray {

	public List<Integer> findDisappearedNumbers(int[] arr) {
		List<Integer> ans=new ArrayList<>();
		for (int i = 0; i < arr.length; i++) {
			while (arr[i]!=i+1&&arr[arr[i]-1]!=arr[i]){//如果能发货就一直发货。
				swap(arr,i,arr[i]-1);
			}
		}
		for (int i = 0; i < arr.length; i++) {//最后遍历一遍看看哪些位置缺数字
			if (arr[i]!=i+1) ans.add(i+1);
		}
		return ans;
	}

	public void swap(int[] arr,int a,int b){
		int t=arr[a];
		arr[a]=arr[b];
		arr[b]=t;
	}

}
