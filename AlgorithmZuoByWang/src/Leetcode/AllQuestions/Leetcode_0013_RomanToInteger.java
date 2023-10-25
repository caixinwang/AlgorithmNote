package Leetcode.AllQuestions;

/**
 * 转的范围不会太大，也就到四千。所以搞一个二维的映射表即可。我们需要几千、几百到罗马数字的映射。
 * 所以可以String类型得到map[num] [base]数组来映射。map[2] [2] 代表200映射的罗马数字,2 * 10^2。
 */
public class Leetcode_0013_RomanToInteger {

	//先把str字符数组根据罗马数字到10进制的映射，先放到一个int[]中，然后计算。如果num[i]<num[i+1]则res加一个负的
	public int romanToInt(String s) {
		int n=s.length(),i=0,ans=0;
		int[] nums=new int[n];
		for(var c:s.toCharArray()){
			switch (c) {
				case 'I' -> nums[i++]=1;
				case 'V' -> nums[i++]=5;
				case 'X' -> nums[i++]=10;
				case 'L' -> nums[i++]=50;
				case 'C' -> nums[i++]=100;
				case 'D' -> nums[i++]=500;
				case 'M' -> nums[i++]=1000;
			}
		}
		for(i=0;i<n;i++) ans+=i<n-1&&nums[i]<nums[i+1]?-nums[i]:nums[i];//最左边的应该是最大的，如果比右边的小，那么就要减去自己
		return ans;
	}

}
