package OtherCoding.InterView;

import TestUtils.StringUtil;

// 来自美团
// 给定两个字符串s1和s2
// 返回在s1中有多少个子串等于s2
public class Code0017_MatchCount {

	public static int sa1(String s1, String s2) {
		if (s1 == null || s2 == null || s1.length() < s2.length()) {
			return 0;
		}
		char[] str1 = s1.toCharArray();
		char[] str2 = s2.toCharArray();
		int[] next = getNext(str2);
		int ans=0;
		int i=0,j=0,N=str1.length,M=str2.length;
		while(i!=N){
			for(;i<N&&j<M&&str1[i]==str2[j];i++,j++);//第一个匹配失败的位置
			if (j==M) ans++;
			if (next[j]==-1) i++;
			else j=next[j];
		}
		return ans;
	}

	public static int[] getNext(char[] str){//next[i]含义是0~i-1子串的最长前缀和后缀的匹配长度
		if (str.length==1) return new int[]{-1,0};
		int[] next=new int[str.length+1];//多生成一个长度，因为我们需要让str2在匹配结束的时候也可以跳转继续匹配
		next[0]=-1;//0位置没有意义
		for (int i = 1; i <= str.length; i++) {
			int p=next[i-1];
			while(p!=-1&&str[p]!=str[i-1]) p=next[p];
			next[i]=p+1;
		}
		return next;
	}

	public static int sa2(String s1, String s2) {//滑动窗口加欠账算出哪些开头可能是答案，然后再去验证。
		if (s1 == null || s2 == null || s1.length() < s2.length()) {
			return 0;
		}
		char[] str1 = s1.toCharArray();
		char[] str2 = s2.toCharArray();
		int N=str1.length,M=str2.length;
		int l=0,r=0,all=0;
		int[] count=new int[128];
		boolean [] start=new boolean[N];
		for (char  c: str2) {
			all++;
			count[c]++;
		}
		for (; r < M; r++) {
			if (--count[str1[r]]>=0) all--;
		}
		if (all==0) start[l]=true;
		while(r<N){
			if (--count[str1[r++]]>=0) all--;
			if (++count[str1[l++]]>0) all++;
			if (all==0) start[l]=true;
		}
		int ans=0;
		for (int i = 0; i < start.length; i++) {
			if (start[i]&&s1.substring(i,i+M).equals(s2)) ans++;
		}
		return ans;
	}



	static StringUtil su=new StringUtil();

	public static void main(String[] args) {
		int times=10000;
		for (int i = 0; i < times; i++) {
			String s2=su.generateRandom_a_z_String(su.ran(1,100));
			String s1=su.generateRandom_a_z_String(su.ran(s2.length(),100));
			int ans1=sa2(s1,s2),ans2= sa1(s1,s2);
			if (ans1!=ans2) System.out.println("opps");
		}
		System.out.println(sa1("abcbbcabcbcabc","abc"));
		System.out.println(sa2("abcbbcabcbcabc","abc"));
	}

}
