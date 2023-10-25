package Leetcode.AllQuestions;

public class Leetcode_0010_RegularExpressionMatching {//IC16

	/**
	 *
	 * @param target 只能有a~z
	 * @param expression 除了有a~z还可以有 . * ，并且*不能位于开头以及不能有连续的*
	 * @return 返回参数是否合法
	 */
	public static boolean isValid(String target,String expression){
		if (target==null||expression==null) return false;
		char[] s = target.toCharArray();
		char[] p = expression.toCharArray();
		for (int i = 0; i < s.length; i++) {
			if (s[i]<'a'||s[i]>'z') return false;
		}
		for (int i = 0; i < p.length; i++) {
			if (p[i]=='*'&&(i==0||p[i-1]=='*')) return false;//检查*出现的位置的合法性
		}
		return true;
	}

	public static boolean isMatch(String s, String p) {
		return isValid(s,p)&&process(s.toCharArray(),0,p.toCharArray(),0);
	}

	private static boolean process(char[] s, int si, char[] p, int pi) {
		int N=s.length,M=p.length;
		if (pi==M){
			return si==N;
		}
		if (si==N){//从上面下来，潜台词就是pi不是结尾
			return pi+1<M&&p[pi+1]=='*'&&process(s,si,p,pi+2);
		}
		//pi和si都不是结尾
		if (pi==M-1||p[pi+1]!='*'){//pi后面不是星，pi到头了也算是后面不是星
			return (s[si]==p[pi]||p[pi]=='.')&&process(s,si+1,p,pi+1);
		}else {
			boolean res=process(s,si,p,pi+2);// c* 组合拳匹配了0个
			while (si<N&&(p[pi]==s[si]||p[pi]=='.')){//si有动你就要判断有没有越界
				res|=process(s,++si,p,pi+2);
			}
			return res;
		}
	}


	public static boolean isMatch2(String target, String expression) {//改动态规划
		if (!isValid(target,expression)) return false;
		int N = target.length();
		int M = expression.length();
		char[] s = target.toCharArray();
		char[] p = expression.toCharArray();
		boolean[][] dp=new boolean[N+1][M+1];
		dp[N][M]=true;//最后一列
		for (int pi=M-1;pi>=0;pi--){//最后一行
			dp[N][pi]=pi+1<M&&p[pi+1]=='*'&&dp[N][pi+2];
		}
		for (int si=N-1;si>=0;si--){
			for (int pi=M-1;pi>=0;pi--){
				if (pi==M-1||p[pi+1]!='*'){//pi后面不是星，pi到头了也算是后面不是星
					dp[si][pi]= (s[si]==p[pi]||p[pi]=='.')&&dp[si+1][pi+1];
//					continue;//没有else这里就需要continue
				}else {
					boolean res=dp[si][pi+2] ;// c* 组合拳匹配了0个
					int t=si;//si要动，用t去动，不然for循环就乱套了
					while (t<N&&(p[pi]==s[t]||p[pi]=='.')){//si有动你就要判断有没有越界
						res|=dp[++t][pi+2] ;
					}
					dp[si][pi]= res;
				}
			}
		}
		return dp[0][0];
	}

	public static boolean isMatch3(String target, String expression) {//省去枚举行为
		if (!isValid(target,expression)) return false;
		int N = target.length();
		int M = expression.length();
		char[] s = target.toCharArray();
		char[] p = expression.toCharArray();
		boolean[][] dp=new boolean[N+1][M+1];
		dp[N][M]=true;//最后一列
		for (int pi=M-1;pi>=0;pi--){//最后一行
			dp[N][pi]=pi+1<M&&p[pi+1]=='*'&&dp[N][pi+2];
		}
		for (int si=N-1;si>=0;si--){
			for (int pi=M-1;pi>=0;pi--){
				if (pi==M-1||p[pi+1]!='*'){//pi后面不是星，pi到头了也算是后面不是星
					dp[si][pi]= (s[si]==p[pi]||p[pi]=='.')&&dp[si+1][pi+1];
				}else {//(p[pi]==s[si]||p[pi]=='.')条件带上，因为只有这个条件满足才会进入到枚举行为中
					dp[si][pi]=dp[si][pi+2]||((p[pi]==s[si]||p[pi]=='.')&&dp[si+1][pi]) ;// c* 组合拳匹配了0个
				}
			}
		}
		return dp[0][0];
	}


	public boolean f(char[] s,int si,char[] p,int pi){
		int n=s.length,m=p.length;
		if(pi==m) return si==n;
		if(si==n) return pi+1<m&&p[pi+1]=='*'&&f(s,si,p,pi+2);
		if(pi+1<m&&p[pi+1]=='*') {
			for(int nsi=si;nsi<=n&&(nsi==si||p[pi]=='.'||s[nsi-1]==p[pi]);nsi++){
				if(f(s,nsi,p,pi+2)) return true;
			}
		}
		return (s[si]==p[pi]||p[pi]=='.')&&f(s,si+1,p,pi+1);
	}

	public boolean isMatch4(String ss, String pp) {//根据f
		char[] s=ss.toCharArray(),p=pp.toCharArray();
		int n=s.length,m=p.length;
		boolean[][] dp=new boolean[n+1][m+1];
		for(int si=n;si>=0;si--){
			for(int pi=m;pi>=0;pi--){
				if(pi==m) dp[si][pi]= si==n;
				else if(si==n) dp[si][pi] =pi+1<m&&p[pi+1]=='*'&&dp[si][pi+2];
				else if(pi+1<m&&p[pi+1]=='*') {
					dp[si][pi]=dp[si][pi+2]||((p[pi]=='.'||s[si]==p[pi])&&dp[si+1][pi]);
				}
				else dp[si][pi]= (s[si]==p[pi]||p[pi]=='.')&&dp[si+1][pi+1];
			}
		}
		return dp[0][0];
	}

	public boolean isMatch5(String s, String p) {//直接写dp
		int n=s.length(),m=p.length();
		char[] ss=s.toCharArray(),pp=p.toCharArray();
		boolean[][] dp=new boolean[n+1][m+1];
		dp[0][0]=true;//第一列，p串没有了，s还有，那么就是false。只有两个都没有了才是true
		for(int j=1;j<=m;j++) dp[0][j]=pp[j-1]=='*'&&dp[0][j-2];//第一行。ss没有了，pp还有，只有在pp[j-1]是*才有救
		for(int i=1;i<=n;i++){
			for(int j=1;j<=m;j++){
				if(pp[j-1]=='*'){
					dp[i][j]=dp[i][j-2];//一个都不匹配
					if(ss[i-1]==pp[j-2]||pp[j-2]=='.') dp[i][j]|=dp[i-1][j];//至少匹配一个，可以斜率优化
				}else{
					dp[i][j]=(ss[i-1]==pp[j-1]||pp[j-1]=='.')&&dp[i-1][j-1];
				}
			}
		}
		return dp[n][m];
	}

}
