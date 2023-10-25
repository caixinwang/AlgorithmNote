package Leetcode.AllQuestions;

public class Leetcode_0191_NumberOf1Bits {

	public static int hammingWeight1(int n) {
//		int ans=0;
//		for (;n!=0;n>>>=1){
//			if ((n&1)==1) ans++;
//		}
//		return ans;
		int ans=0;
		int mr=0;
		while(n!=0){
			mr=n&(~n+1);//最右侧的1
			if ((n&mr)==mr) ans++;
//			n&=~mr;//去掉n上面的最右侧的1
			n^=mr;//也是去掉n上面的最右侧的1
		}
		return ans;
	}

	public static int hammingWeight2(int n) {
		n = (n & 0x55555555) + ((n >>> 1) & 0x55555555);
		n = (n & 0x33333333) + ((n >>> 2) & 0x33333333);
		n = (n & 0x0f0f0f0f) + ((n >>> 4) & 0x0f0f0f0f);
		n = (n & 0x00ff00ff) + ((n >>> 8) & 0x00ff00ff);
		n = (n & 0x0000ffff) + ((n >>> 16) & 0x0000ffff);
		return n;
	}
	
}
