package Leetcode.AllQuestions;

public class Leetcode_0088_MergeSortedArray {

	public static void merge(int[] nums1, int m, int[] nums2, int n) {
		int p1=m-1;
		int p2=n-1;
		int i=m+n-1;
		while(p1>=0&&p2>=0){
			if (nums1[p1]>nums2[p2]){
				nums1[i--]=nums1[p1--];
			}else {
				nums1[i--]=nums2[p2--];
			}
		}
		while(p1>=0) nums1[i--]=nums1[p1--];
		while(p2>=0) nums1[i--]=nums2[p2--];
	}

	public void merge2(int[] nums1, int m, int[] nums2, int n) {
		int p1=m-1,p2=n-1,index=n+m-1;
		while(p1!=-1||p2!=-1){
			if(p2==-1||p1!=-1&&nums1[p1]>=nums2[p2]) nums1[index--]=nums1[p1--];
			else nums1[index--]=nums2[p2--];
		}
	}

	public void merge3(int[] nums1, int m, int[] nums2, int n) {
		while(n!=0||m!=0){
			if(n==0||m!=0&&nums1[m-1]>=nums2[n-1]) nums1[n+m-1]=nums1[--m];
			else nums1[n+m-1]=nums2[--n];
		}
	}

}
