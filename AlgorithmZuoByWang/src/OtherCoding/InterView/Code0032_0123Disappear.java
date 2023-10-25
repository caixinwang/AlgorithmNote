package OtherCoding.InterView;

import TestUtils.StringUtil;

// 真实笔试，忘了哪个公司，但是绝对大厂
// 一个子序列的消除规则如下:
// 1) 在某一个子序列中，如果'1'的左边有'0'，那么这两个字符->"01"可以消除
// 2) 在某一个子序列中，如果'3'的左边有'2'，那么这两个字符->"23"可以消除
// 3) 当这个子序列的某个部分消除之后，认为其他字符会自动贴在一起，可以继续寻找消除的机会
// 比如，某个子序列"0231"，先消除掉"23"，那么剩下的字符贴在一起变成"01"，继续消除就没有字符了
// 如果某个子序列通过最优良的方式，可以都消掉，那么这样的子序列叫做“全消子序列”
// 一个只由'0'、'1'、'2'、'3'四种字符组成的字符串str，可以生成很多子序列，返回“全消子序列”的最大长度
// 字符串str长度 <= 200
// 体系学习班，代码46节，第2题+第3题
public class Code0032_0123Disappear {

	// str[L...R]上，都能消掉的子序列，最长是多少？
	public static int f(char[] str, int L, int R) {
		if (L >= R) {
			return 0;
		}
		if (L == R - 1) {
			return (str[L] == '0' && str[R] == '1') || (str[L] == '2' && str[R] == '3') ? 2 : 0;
		}
		// L...R 有若干个字符 > 2
		// str[L...R]上，都能消掉的子序列，最长是多少？
		// 可能性1，能消掉的子序列完全不考虑str[L]，最长是多少？
		int p1 = f(str, L + 1, R);
		if (str[L] == '1' || str[L] == '3') {
			return p1;
		}
		// str[L] =='0' 或者 '2'
		// '0' 去找 '1'
		// '2' 去找 '3'
		char find = str[L] == '0' ? '1' : '3';
		int p2 = 0;
		// L() ......
		for (int i = L + 1; i <= R; i++) {
			// L(0) ..... i(1) i+1....R
			if (str[i] == find) {
				p2 = Math.max(p2, f(str, L + 1, i - 1) + 2 + f(str, i + 1, R));
			}
		}
		return Math.max(p1, p2);
	}

	public static int maxDisappear(String str) {
		if (str == null || str.length() == 0) {
			return 0;
		}
		return disappear(str.toCharArray(), 0, str.length() - 1);
	}

	// s[l..r]范围上，如题目所说的方式，最长的都能消掉的子序列长度
	public static int disappear(char[] s, int l, int r) {
		if (l >= r) {
			return 0;
		}
		if (l == r - 1) {
			return (s[l] == '0' && s[r] == '1') || (s[l] == '2' && s[r] == '3') ? 2 : 0;
		}
		int p1 = disappear(s, l + 1, r);
		if (s[l] == '1' || s[l] == '3') {
			return p1;
		}
		int p2 = 0;
		char find = s[l] == '0' ? '1' : '3';
		for (int i = l + 1; i <= r; i++) {
			if (s[i] == find) {
				p2 = Math.max(p2, disappear(s, l + 1, i - 1) + 2 + disappear(s, i + 1, r));
			}
		}
		return Math.max(p1, p2);
	}

	//这题的动态规划不能按照4中情况分类，因为L和R就算消不了也还有救，所以分类两种即可
	public static int maxDisappear2(String s) {
		if (s == null || s.length() == 0) {
			return 0;
		}
		char[] str=s.toCharArray();
		int N=str.length;
		int[][] dp=new int[N][N];
		for (int i = 0; i +1 < N; i++) {
			dp[i][i+1]=str[i]=='0'&&str[i+1]=='1'||str[i]=='2'&&str[i+1]=='3'?2:0;
		}
		for (int j = 2; j < N; j++) {
			for (int i = 0; i+j < N; i++) {
				int L=i,R=i+j;
				dp[L][R]=dp[L+1][R];
				if (str[L]=='1'||str[L]=='3') continue;//L为1和3说明L参与是没答案的
				char find=str[L]=='0'?'1':'3';
				for (int k=L+1;k<=R;k++){
					if (str[k]==find){
						dp[L][R] = Math.max(dp[L][R],2+dp[L+1][k-1]+(k+1<=R?dp[k+1][R]:0));
					}
				}
			}
		}
		return dp[0][N-1];
	}

	static StringUtil su=new StringUtil();
	static public String generateStr(int size){
		char[] ans=new char[size];
		char[] set=new char[]{'0','1','2','3'};
		for (int i = 0; i < ans.length; i++) {
			ans[i]=set[su.ran(0,3)];
		}
		return String.valueOf(ans);
	}

	public static void main(String[] args) {
		int times=1000;
		for (int i = 0; i < times; i++) {
			String str=generateStr(su.ran(0,30));
			int ans1=maxDisappear(str),ans2=maxDisappear2(str);
			if (ans1!=ans2){
				System.out.println("ans1 = " + ans1);
				System.out.println("ans2 = " + ans2);
			}
		}
	}

}
