package Leetcode.AllQuestions;

public class Leetcode_0215_KthLargestElementInAnArray {

	public int findKthLargest(int[] arr, int k) {
		return findKthMin(arr,0,arr.length-1,arr.length-k);
	}

	public int findKthMin(int[] arr,int L,int R, int k) {
		if (L>=R) return arr[L];
		swap(arr,L,L+(int)(Math.random()*(R-L+1)));//随机选一个做L，pivot
		int index=partition(arr,L,R);
		if (index==k){
			return arr[index];
		}else if (index<k){
			return findKthMin(arr,index+1,R,k);
		}else {
			return findKthMin(arr,L,index-1,k);
		}
	}

	public int partition(int[] arr,int L,int R){
		int l=L,r=R+1;
		while(l<r){
			while(++l<=R&&arr[l]<arr[L]);
			while(--r>=L&&arr[r]>arr[L]);
			swap(arr,r,l<r?l:L);
		}
		return r;
	}

	public void swap(int[] arr,int a,int b){
		int r=arr[a];
		arr[a]=arr[b];
		arr[b]=r;
	}

}
