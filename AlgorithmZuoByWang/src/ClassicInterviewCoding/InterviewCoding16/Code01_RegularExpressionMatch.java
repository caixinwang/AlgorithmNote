package ClassicInterviewCoding.InterviewCoding16;

public class Code01_RegularExpressionMatch {

	public static boolean isValid(char[] s, char[] e) {
		// s中不能有'.'  or  '*'
		for (int i = 0; i < s.length; i++) {
			if (s[i] == '*' || s[i] == '.') {
				return false;
			}
		}
		// 开头的e[0]不能是'*'，没有相邻的'*'
		for (int i = 0; i < e.length; i++) {
			if (e[i] == '*' && (i == 0 || e[i - 1] == '*')) {
				return false;
			}
		}
		return true;
	}

	public static boolean isMatch(String str, String exp) {
		if (str == null || exp == null) {
			return false;
		}
		char[] s = str.toCharArray();
		char[] e = exp.toCharArray();
		return isValid(s, e) && process(s, e, 0, 0);
	}

	// e[ei....]  能否变成  s[si...]
	// 重要限制：e[ei]不能压中'*'
	public static boolean process(char[] s, char[] e, int si, int ei) {
		if (ei==e.length) return si==s.length;
		if (ei==e.length-1||e[ei+1]!='*'){//ei位置后面不是*，e的最后位置也认为是后面不为*的情况
			//si要有东西，并且要和ei匹配
			return si!=s.length&&(e[ei]==s[si]||e[ei]=='.')&&process(s,e,si+1,ei+1);
		}else {//ei后面是星
			boolean res = process(s, e, si, ei + 2);// 初值为 x* 组合拳不解决任何东西
			while(si!=s.length&&(e[ei]==s[si]||e[ei]=='.')){
				res|=process(s,e,++si,ei+2);
			}
			return res;
		}
	}

	/**
	 * 初始化dp表，根据递归决定初始化到哪一步，看我们的递归其实初始化第一列就行了
	 * 但是如果我们什么都不知道，就去看看倒数几行几列有多少我们可以直观的初始化的.
	 * 你初始化到哪，for循环开始位置做调整就行了
	 */
	public static boolean isMatchDP(String str, String exp) {//直接改动态规划
		if (str == null || exp == null) {
			return false;
		}
		char[] s = str.toCharArray();
		char[] e = exp.toCharArray();
		if (!isValid(s, e)) {
			return false;
		}
		// initDPMap  做出一张表，而且填好倒数两列，和最后一行
		//当然根据我们的递归，你只需要填好最后一列。但是你当然也可以多初始化一些。for循环开始位置做小小的调整即可
		int slen = s.length;
		int elen = e.length;
		boolean[][] dp = new boolean[slen + 1][elen + 1];
		dp[slen][elen]=true;//初始化最后一列dp[i][elen]
//		if (s[slen-1]==e[elen-1]||e[elen-1]=='.') dp[slen-1][elen-1]=true;//初始化倒数第二列，dp[i][elen-1]
//		for (int i = elen-2;i>=0; i-=2) {//初始化最后一行，dp[slen][i]，后两列填好了，从倒三列开始
//			if (e[i]!='*'&&e[i+1]=='*') dp[slen][i]=true;
//			else break;
//		}
		for (int si = s.length; si > -1; si--) {
			for (int ei = e.length - 1; ei > -1; ei--) {//只初始化了第一列
				if (ei==e.length-1||e[ei+1]!='*'){//ei位置后面不是*，e的最后位置也认为是后面不为*的情况
					//si要有东西，并且要和ei匹配
					dp[si][ei]= si!=s.length&&(e[ei]==s[si]||e[ei]=='.')&&dp[si+1][ei+1];
				}else {//ei后面是星
					boolean res=false;
					res|=dp[si][ei+2];// x* 组合拳不解决任何东西
					int p=si;
					while(p!=s.length&&(e[ei]==s[p]||e[ei]=='.')){
						res|=dp[++p][ei+2];
					}
					dp[si][ei]=res;
				}
			}
		}
		return dp[0][0];
	}

	public static void main(String[] args) {
		String str = "abcccdefgee";
		String exp = "ab.*d.*e.*.";
		System.out.println(isMatch(str, exp));
		System.out.println(isMatchDP(str, exp));

	}

}
