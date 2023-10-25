package Leetcode.AllQuestions;

public class Leetcode_0034_FindFirstAndLastPositionOfElementInSortedArray {

	public static int[] searchRange(int[] nums, int target) {
		if (nums==null||nums.length==0) return new int[]{-1,-1};
		int[] res=new int[2];
		res[0]=findLeft(nums,target);
		res[1]=findRight(nums,target);
		if (res[0]==-1||nums[res[0]]!=target)return new int[]{-1,-1};
		return res;
	}

	private static int findRight(int[] nums, int target) {//<=target 最右
		int l=0,r=nums.length-1,mid;
		while(l<=r){
			mid=l+(r-l>>1);
			if (nums[mid]<=target) l=mid+1;
			else r=mid-1;
		}
		return r;
	}

	private static int findLeft(int[] nums, int target) {//>=target 最左
		int l=0,r=nums.length-1,mid;
		while(l<=r){
			mid=l+(r-l>>1);
			if (nums[mid]>=target) r=mid-1;
			else l=mid+1;
		}
		return l==nums.length?-1:l;
	}

}
