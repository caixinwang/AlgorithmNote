package ClassicInterviewCoding.InterviewCoding01;

/**
 * maxLength:返回一个括号字符串中，最长的括号有效子串的长度
 * deep:返回一个达标括号字符串中，括号嵌套的最大深度
 *
 */
public class Code03_ParenthesesDeep {

	public static boolean isValid(char[] str) {
		if (str == null || str.length == 0) {
			return false;
		}
		int status = 0;
		for (int i = 0; i < str.length; i++) {
			if (str[i] != ')' && str[i] != '(') {
				return false;
			}
			if (str[i] == ')' && --status < 0) {
				return false;
			}
			if (str[i] == '(') {
				status++;
			}
		}
		return status == 0;
	}

	public static int deep(String s) {
		char[] str = s.toCharArray();
		if (!isValid(str)) return 0;
		int count = 0;
		int max = 0;
		for (int i = 0; i < str.length; i++) {
			if (str[i] == '(') {
				max = Math.max(max, ++count);
			} else {
				count--;
			}
		}
		return max;
	}

	public static int maxLength(String s) {
		if (s==null||s.length()<2) return 0;
		char[] str = s.toCharArray();
		int[]dp=new int[s.length()];//初始化全为0
		int res=0;
		for (int i=1;i<s.length();i++){//写出最优子结构，对照着写。
			if (str[i]==')'&&i-1-dp[i-1]>=0&&str[i-1-dp[i-1]]=='('){//只有这种情况dp[i]才不是0
				dp[i]=dp[i-1]+2+dp[i-1-dp[i-1]];
				res = Math.max(res, dp[i]);
			}
		}
		return res;
	}

	public static void main(String[] args) {
		String test = "((()))";
		System.out.println(maxLength(test));
	}

}
