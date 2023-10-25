package ClassicInterviewCoding.InterviewCoding01;

/**
 * 括号有效配对是指：1)任何一个左括号都能找到和其正确配对的右括号 2)任何一个右括号都能找到和其正确配对的左括号
 * 有效的：(())  ()()  (()())  等    无效的：(()  )(  等
 *
 */
public class Code02_NeedParentheses {

	//任意时刻左括号数都不比右括号数少，并且最终左括号和右括号的数目相等。
	public static boolean valid(String s) {//判断一个括号字符串s有效
		char[] str = s.toCharArray();
		int count = 0;
		for (int i = 0; i < str.length; i++) {
			count += str[i] == '(' ? 1 : -1;
			if (count < 0) {
				return false;
			}
		}
		return count == 0;
	}

	//need为需要补的左括号数  count为需要补的右括号数
	public static int needParentheses(String s) {//如果一个括号字符串无效，返回至少填几个字符能让其整体有效
		char[] str = s.toCharArray();
		int count = 0;
		int need = 0;
		for (int i = 0; i < str.length; i++) {
			if (str[i] == '(') {
				count++;
			} else { // 遇到的是')'
				if (count == 0) {
					need++;
				} else {
					count--;
				}
			}
		}
		return count + need;
	}

	public static int needParentheses2(String s) {//如果一个括号字符串无效，返回至少填几个字符能让其整体有效
		char[] str = s.toCharArray();
		int count = 0;
		int need = 0;
		for (int i = 0; i < str.length; i++) {
			count+=str[i]=='('?1:-1;
			if (count<0){
				count=0;
				need++;
			}
		}
		return count + need;
	}

}
