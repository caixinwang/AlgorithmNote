package Leetcode.AllQuestions;

public class Leetcode_0334_IncreasingTripletSubsequence {

	//直接拿300题改
	public static boolean increasingTriplet(int[] arr) {
		if (arr==null|| arr.length<3) return false;
		int[] ends=new int[3];
		int len=0;//有效区长度
		for (int num:arr){
			int l=0,r=len-1;
			while (l<=r){
				int mid=l+(r-l>>1);
				if (ends[mid]>=num) r=mid-1;
				else l=mid+1;
			}
			if (l==len) len++;
			if (len==3) return true;
			ends[l]=num;
		}
		return false;
	}

}
