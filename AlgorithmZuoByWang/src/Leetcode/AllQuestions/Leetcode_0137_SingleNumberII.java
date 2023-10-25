package Leetcode.AllQuestions;

public class Leetcode_0137_SingleNumberII {

	//kn问题，所有的数都出现了n次，只有一个出现了k次。那么就用32个桶来接住每一个数的二进制位
	//最后统一模上n，然后/k,最后转成int就是答案
	public int singleNumber(int[] nums) {
		int[] bits=new int[32];
		for(var c:nums){
			for(int index=0;c!=0;c>>>=1,index++){
				bits[index]+=c&1;
			}
		}
		int ans=0;
		for(int i=0;i<32;i++) {
			bits[i]%=3;
			ans+=bits[i]*(1<<i);
		}
		return ans;
	}

}
