package Leetcode.AllQuestions;

public class Leetcode_0066_PlusOne {

	public static int[] plusOne(int[] digits) {
		int i= digits.length;
		while(--i>=0&&digits[i]==9) digits[i]=0;//找到第一个不为9的位置，并把前面为9的位置都改为0
		if (i>=0){
			digits[i]++;
			return digits;
		}
		int[]res=new int[digits.length+1];
		res[0]=1;
		return res;
	}

}
