package Leetcode.AllQuestions;

public class Leetcode_0169_MajorityElement {

	/**
	 * 原理就是一次删掉两个不同的数，下面优雅的实现了这件事
	 * @param nums 找出nums数组中出现了超过2/N的数
	 * @return -
	 */
	public static int majorityElement(int[] nums) {//水王问题
		int candi=nums[0],hp=1;
		for (int i = 1; i < nums.length; i++) {
			if (nums[i]==candi){
				hp++;
			}else if (--hp==0){
				candi=nums[i];
				hp=1;
			}
		}
//		hp=0;//验证，leetcode上题不用验证
//		for(int num:nums){
//			if (num==candi) hp++;
//		}
		return candi;
	}

}
