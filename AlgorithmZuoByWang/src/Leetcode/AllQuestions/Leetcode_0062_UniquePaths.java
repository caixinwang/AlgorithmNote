package Leetcode.AllQuestions;

public class Leetcode_0062_UniquePaths {

	public static int uniquePaths(int m, int n) {
		int all=m+n-2;
		int part=m<n?m-1:n-1;//C(all,part)
		long o1=1;
		long o2=1;
		for (int i = 1; i <= part ; i++) {
			o1*=all-i+1;
			o2*=i;
			long q=gcd(o1,o2);
			o1/=q;
			o2/=q;
		}
		return (int)(o1/o2);
	}

	// 调用的时候，请保证初次调用时，m和n都不为0
	public static long gcd(long m, long n) {
		return n == 0 ? m : gcd(n, m % n);
	}

}
