package Leetcode.AllQuestions;

public class Leetcode_0070_ClimbingStairs {

	//f(n)=f(n-1)+f(n-2)  f(1)=1,f(2)=2
	public int climbStairs2(int n) {
		if(n<=2) return n;
		int f1=2,f2=1;//f1->f[n-1]  f2 -> f[n-2]
		for(int i=3;i<=n;i++){
			int nf1=f1+f2;
			f2=f1;
			f1=nf1;
		}
		return f1;
	}
}

