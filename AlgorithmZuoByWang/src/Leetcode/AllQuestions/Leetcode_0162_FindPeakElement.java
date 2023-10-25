package Leetcode.AllQuestions;

public class Leetcode_0162_FindPeakElement {

	public int findPeakElement(int[] nums) {//二分法
		if (nums==null||nums.length==0) return -1;
		if(nums.length==1) return 0;
		if (nums[0]>nums[1]) return 0;
		if (nums[nums.length-1]>nums[nums.length-2]) return nums.length-1;
		int l=1,r=nums.length-2;
		while(l<=r){
			int mid=l+(r-l>>1);
			if (nums[mid]>nums[mid+1]&&nums[mid]>nums[mid-1]) {
				return mid;
			} else if (nums[mid+1]>nums[mid]){
				l=mid+1;
			}else {
				r=mid-1;
			}
		}
		return -1;
	}

	public int findPeakElement2(int[] nums) {//染色法
		int l=0,r=nums.length-1;//l左边是垃圾区，因为l往爬坡的方向走，l左边必然不是答案。r作为可能区。所以l最后压中答案
		while (l<=r){
			int mid=l+(r-l>>1);
			if (mid+1<nums.length&&nums[mid]<nums[mid+1]) l=mid+1;//如果右边还有坡可以爬，那么就一直往右边走，一直走这个分支不会越界
			else r=mid-1;
		}
		return l;//最极限的情况就是l一直往右边走，但是条件中有mid+1<nums.length天然使得l位置不会越界
	}

	public int findPeakElement3(int[] nums) {//染色法
		int l=0,r=nums.length-1;//l是有用区
		while(l<=r){
			int mid=l+(r-l>>1);
			if (mid-1>=0&&nums[mid-1]<nums[mid]) l=mid+1;
			else r=mid-1;
		}
		return r==-1?0:r;
	}

}
