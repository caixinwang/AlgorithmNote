package Leetcode.AllQuestions;

public class Leetcode_0032_LongestValidParentheses {

	public static int longestValidParentheses(String s) {
		if (s == null || s.equals("")) {
			return 0;
		}
		int ans=0;
		char[] str=s.toCharArray();
		int[] dp=new int[str.length];//必须以i位置结尾，那么必然是右括号,0位置必然不达标
		for (int i=1;i<dp.length;i++){
			if (str[i]==')'){
				int len=dp[i-1];//前面搞定的长度
				int index=i-1-len;//前面断掉的坑，看看能不能和i位置续上，然后和前面的index-1结尾的再连起来
				if (index>=0&&str[index]=='('){
					dp[i]=2+dp[i-1]+(index-1>=0?dp[index-1]:0);
				}
			}
		}
		for (int num:dp){
			ans = Math.max(ans, num);
		}
		return ans;
	}

}
