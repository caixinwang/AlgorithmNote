package Leetcode.AllQuestions;

public class Leetcode_0033_SearchInRotatedSortedArray {

	//这题的做法是分区域二分
	public static int search(int[] arr, int num) {//arr不重
		int l=0,r=arr.length-1;
		while(l<=r){
			int mid=l+(r-l>>1);
			if (arr[mid]==num) return mid;
			if (arr[mid]>=arr[l]){//说明是上半区
				if (arr[l]<=num&&num<arr[mid]){
					r=mid-1;
				}else {
					l=mid+1;
				}
			}else {//下半区
				if (arr[mid]<num&&num<=arr[r]){
					l=mid+1;
				}else {
					r=mid-1;
				}
			}
		}
		return -1;
	}

	//利用染色的方法，本质就让l~r的区间都去探索位置的区域。l左边和r的右边都是已知区域，[l,r]是待探索区域，...l)以及(r... 是已知的
	//我们让[...,l)代表答案可能出现的位置,让(r,...]代表答案不可能出现的位置。最终结束的位置r就应该是答案了，因为r右边都是不可能出现答案的。
	//这题不保证target一定存在，r最终的位置是答案可能出现的位置，我们需要验证一下。
	public static int search2(int[] arr, int target) {//arr不重
		int l=0,r=arr.length-1;
		while(l<=r){
			int mid=l+(r-l>>1);
			if ((target<arr[0]&&(arr[mid]>=arr[0]||arr[mid]<=target))||(target>=arr[0]&&arr[mid]>=arr[0]&&arr[mid]<=target)){
				l=mid+1;
			}else {
				r=mid-1;
			}
		}
		return arr[r]==target?r:-1;
	}


	public static void main(String[] args) {
		int[] arr=new int[]{4,5,6,7,0,1,2};
		System.out.println(search2(arr,3));
	}

}
