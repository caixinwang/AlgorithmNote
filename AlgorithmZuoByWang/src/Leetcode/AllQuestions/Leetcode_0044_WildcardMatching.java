package Leetcode.AllQuestions;

public class Leetcode_0044_WildcardMatching {

	public static boolean isMatch(String str, String match) {
		if (str==null||match==null) return true;
		return process(str.toCharArray(),match.toCharArray(),0,0);
	}

	/**
	 * 一个样本做行一个样本做列的对应模型，si....与pi....可以配出来
	 * @param s 待匹配串
	 * @param p 匹配串
	 * @param si s[si...]去和p[pi...]配
	 * @param pi -
	 * @return 配出来返回true
	 */
	public static boolean process(char[] s,char[] p,int si,int pi){
		int N=s.length,M=p.length;
		if (pi==M){
			return si==N;
		}
		if (si==N){//pi!=M
			return p[pi]=='*'&&process(s,p,si,pi+1);
		}
		if (p[pi]!='*'){
			return (s[si]==p[pi]||p[pi]=='?')&&process(s,p,si+1,pi+1);
		}
		for (int unsolve = si; unsolve <= s.length; unsolve++) {//unsolve:第一个没有解决的位置，前面的[si...unsolve-1]都解决了
			if (process(s,p,unsolve,pi+1)) return true;
		}
		return false;
	}

	//一个格子依赖右下角，所以从下往上，从右往左填即可。
	public static boolean isMatch2(String str, String match) {
		if (str==null||match==null) return true;
		char[] s = str.toCharArray();
		char[] p = match.toCharArray();
		int N=s.length,M=p.length;
		boolean[][]dp=new boolean[N+1][M+1];
		dp[N][M]=true;
		for (int pi=M-1;pi>=0;pi--){
			dp[N][pi]=p[pi]=='*'&&dp[N][pi+1];
		}

		for (int si=N-1;si>=0;si--){
			out: //如果递归里面有break，这里就需要continue，退出继续填表
			for (int pi=M-1;pi>=0;pi--){
				if (p[pi]!='*'){
					dp[si][pi]= (s[si]==p[pi]||p[pi]=='?')&&dp[si+1][pi+1];
					continue out;//return下面要加上continue
				}
				//dp[si][pi]=(dp[unsolve][pi+1]||....||...)
				for (int unsolve = si; unsolve <= s.length; unsolve++) {//unsolve:第一个没有解决的位置，前面的[si...unsolve-1]都解决了
					if (dp[unsolve][pi+1]) {
						dp[si][pi] = true;
						continue out;
					}
				}
				dp[si][pi] = false;
			}
		}
		return dp[0][0];
	}

	//斜率优化，枚举的时候一个格子依赖自己右边往下一列，所以找自己下面的格子帮自己省事，补上自己右边的格子即可
	public static boolean isMatch3(String str, String match) {
		if (str==null||match==null) return true;
		char[] s = str.toCharArray(),p = match.toCharArray();
		int N=s.length,M=p.length;
		boolean[][]dp=new boolean[N+1][M+1];
		dp[N][M]=true;
		for (int pi=M-1;pi>=0;pi--) dp[N][pi]=p[pi]=='*'&&dp[N][pi+1];
		for (int si=N-1;si>=0;si--){
			for (int pi=M-1;pi>=0;pi--){
				if (p[pi]!='*') dp[si][pi]= (s[si]==p[pi]||p[pi]=='?')&&dp[si+1][pi+1];//return下面要加上continue或者else语句
				else dp[si][pi]=dp[si][pi+1]||dp[si+1][pi];//dp[si][pi]=(dp[unsolve][pi+1]||....||...)
			}
		}
		return dp[0][0];
	}

}
