package Leetcode.AllQuestions;

public class Leetcode_0075_SortColors {
	public void sortColors(int[] arr) {//三向切分，荷兰国旗问题
		int less=-1,more=arr.length,i=0;
		while(i<more){
			if (arr[i]<1){
				swap(arr,++less,i++);
			} else if (arr[i] == 1) {
				i++;
			}else {
				swap(arr,--more,i);
			}
		}
	}
	public void swap(int[] arr,int a,int b){
		int t=arr[a];
		arr[a]=arr[b];
		arr[b]=t;
	}
}
