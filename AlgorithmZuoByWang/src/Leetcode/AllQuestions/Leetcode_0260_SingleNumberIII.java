package Leetcode.AllQuestions;

public class Leetcode_0260_SingleNumberIII {
	//使用最右边的1作为区分的锚点
	public int[] singleNumber(int[] nums) {
		int both=0,one=0;
		for(var c:nums) both^=c;
		for(var c:nums)if((c&(both&-both))!=0) one^=c;
		return new int[]{one,both^one};
	}

}
