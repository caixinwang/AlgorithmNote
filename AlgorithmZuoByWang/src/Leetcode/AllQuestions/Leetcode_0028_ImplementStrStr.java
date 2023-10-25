package Leetcode.AllQuestions;

public class Leetcode_0028_ImplementStrStr {
	public int strStr(String str, String match) {
		if (str==null||match==null||match.length()==0||str.length()<match.length()) return -1;
		int p1=0,p2=0;
		char[] s = str.toCharArray();
		char[] m = match.toCharArray();
		int[] next = getNext(m);
		while(p1<s.length&&p2<m.length){
			if (s[p1]==m[p2]){
				p1++;
				p2++;
			}else if (next[p2]!=-1){
				p2=next[p2];
			}else {
				p1++;
			}
		}
		return p2==m.length?p1-p2:-1;
	}

	/**
	 *
	 * @param match match[i]代表0~i-1的子串，最长的前缀和后缀相等的长度。0位置没有前面没有子串，所以0位置没有含义。
	 *              前缀和后缀不能是整个字符串。所以1位置肯定是0。
	 * @return
	 */
	public int[] getNext(char[] match){
		if (match==null||match.length==0) return null;
		int[] next=new int[match.length];
		next[0]=-1;
		for (int i=1;i<next.length;i++){
			int p=next[i-1];
			while(p!=-1&&match[i-1]!=match[p]) p=next[p];//包含了1位置的初始化
			if (p==-1) next[i]=0;
			else next[i]=p+1;
		}
		return next;
	}
}
