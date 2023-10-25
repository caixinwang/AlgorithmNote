package Leetcode.AllQuestions;

public class Leetcode_0091_DecodeWays {
	public int numDecodings(String s) {//暴力递归，超时
		char[] str = s.toCharArray();
		return f(str,0);
	}

	private int f(char[] str, int index) {
		if (index==str.length) return 1;
		if (str[index]=='0') return 0;
		int p1=f(str,index+1);
		int p2=0;
		if (index+1<str.length&&(str[index]-'0')*10+str[index+1]-'0'<=26){
			p2=f(str,index+2);
		}
		return p1+p2;
	}

	public int numDecodings2(String s) {//改动态规划
		char[] str = s.toCharArray();
		int[] dp=new int[str.length+1];
		dp[str.length]=1;
		for (int index=str.length-1;index>=0;index--){
			if (str[index]=='0'){
				dp[index]=0;
				continue;
			}
			int p1=dp[index+1];
			int p2=0;
			if (index+1<str.length&&(str[index]-'0')*10+str[index+1]-'0'<=26){
				p2=dp[index+2];
			}
			dp[index]= p1+p2;
		}
		return dp[0];
	}

	public int numDecodings3(String s) {//空间压缩
		char[] str = s.toCharArray();
		int pre1=1;
		int pre2=-1;
		int ans=0;
		for (int index=str.length-1;index>=0;index--){
			if (str[index]=='0'){
				ans=0;
				pre2=pre1;
				pre1=0;
				continue;
			}
			int p1=pre1;
			int p2=0;
			if (index+1<str.length&&(str[index]-'0')*10+str[index+1]-'0'<=26){
				p2=pre2;
			}
			ans=p1+p2;
			pre2=pre1;
			pre1= p1+p2;
		}
		return ans;
	}

}
