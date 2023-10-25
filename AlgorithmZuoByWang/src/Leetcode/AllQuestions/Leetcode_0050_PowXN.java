package Leetcode.AllQuestions;

public class Leetcode_0050_PowXN {
	// x^10 = 1*x^8 + 0*x^4 + 1*x^2 + 0*x^1
	//      = 1*x^(2^3) + 0*x^(2^2) + 1*x^(2^1) + 0*x^(2^0)
	public static double myPow(double x, int n) {
		if (n==0) return 1;
		int pow=n==Integer.MIN_VALUE?Integer.MAX_VALUE:Math.abs(n);
		double t=x,ans=1;
		while(pow!=0){
			if((pow&1)==1) {
				ans*=t;
			}
			t*=t;
			pow>>>=1;
		}
		if (n==Integer.MIN_VALUE) ans*=x;
		return n<0?1/ans:ans;
	}


	public static void main(String[] args) {
		System.out.println(myPow(2,-2));
		System.out.println(myPow(2,2));
		System.out.println(Integer.MIN_VALUE);
		System.out.println(Math.pow(1.0000001D,Integer.MIN_VALUE));
		System.out.println(myPow(1.0000001D,Integer.MIN_VALUE));
	}
}
