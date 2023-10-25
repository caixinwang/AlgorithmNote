package Leetcode.AllQuestions;

public class Leetcode_0172_FactorialTrailingZeroes {

	public int trailingZeroes(int n) {//0来源于2和5的乘积，5比2多，所以等价于算有多少5的因子。
		int ans=0;
		while(n!=0){
			n/=5;
			ans+=n;
		}
		return ans;
	}

}
