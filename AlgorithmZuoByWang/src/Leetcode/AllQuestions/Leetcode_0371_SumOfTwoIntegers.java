package Leetcode.AllQuestions;

public class Leetcode_0371_SumOfTwoIntegers {

	public static int getSum(int a, int b) {
		int sum=a;
		while(b!=0){//b作为进位信息
			sum^=b;
			b=(a&b)<<1;
			a=sum;
		}
		return sum;
	}

}
