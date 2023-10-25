package Leetcode.AllQuestions;

public class Leetcode_0008_StringToInteger {
	/**
	 * 这题的边界条件挺多。
	 * 1. 去掉开头的0。要考虑开头位置有没有+或者-的情况。
	 * 2. 先过滤，直到最后才碰算法
	 * 3. 算法依然是使用负数来接着，防止溢出。在中途转的过程中卡主它溢出的时刻。
	 */

	public static int myAtoi(String s) {
		if (s==null||s.length()==0) return 0;
		char[] str = s.toCharArray();

		int N= str.length;
//		int fi=-1;
//		while (++fi<N&&(str[fi]<'0'||str[fi]>'9'));//fi跳到第一个为数字的地方
		int fi=0;
		for (;fi<N&&(str[fi]<'0'||str[fi]>'9');fi++) ;
		if (fi==N) return 0;//压根没有数，不合法
		for (int i = 0; i < fi; i++) {//检查从第一个字符到fi-1处的字符只能是空格，除了fi-1能是+-，其它位置只要出现了非空格就是非法的
			//如果i位置不为空格大概率是不合法，但是有一个例外在i-1位置是+-，让这个例外通过&&!(condition)跳出这个if，继续下面的逻辑
			if (str[i]!=' '&&!(i==fi-1&&(str[i]=='-'||str[i]=='+'))) return 0;
		}
		boolean isNeg=fi-1>=0&&str[fi-1]=='-';//只有fi-1位置是'-'结果才是负数，其它' '或者'+'都为正数
		int res=0;
		int minq=Integer.MIN_VALUE/10;//-214748364 商
		int minr=Integer.MIN_VALUE%10;//-8 余数
		for (int i = fi; i < N&&Character.isDigit(str[i]); i++) {//后面遇到非数字就直接退出了
			if ((res<minq)||(res==minq&&'0'-str[i]<minr)) {//判断越界
				return isNeg?Integer.MIN_VALUE:Integer.MAX_VALUE;
			}
			res=res*10+('0'-str[i]);
		}
		if (res==Integer.MIN_VALUE&&!isNeg) return Integer.MAX_VALUE;//防止“+2147483648”这样的测试用例，直接取相反数会溢出
		return isNeg?res:-res;
	}

	final int MIN=1<<31,MAX=MIN-1;
	public int myAtoi2(String s) {
		s=s.trim();
		if(s.length()==0) return 0;
		char[] str=s.toCharArray();
		int n=s.length(),start=0;
		boolean isNeg=str[0]=='-';
		if(str[0]=='-'||str[0]=='+') start++;
		int minq=MIN/10,minr=MIN%10,ans=0;//用负数接
		for(int i=start;i<n&&Character.isDigit(str[i]);i++){
			if(ans<minq||(ans==minq&&'0'-str[i]<minr)) return isNeg?MIN:MAX;
			ans=ans*10+'0'-str[i];
		}
		if(ans==MIN&&!isNeg) return MAX;
		return isNeg?ans:-ans;
	}

	public static void main(String[] args) {
		System.out.println(myAtoi("words and 987") );
		System.out.println(myAtoi("    +9999999999999") );
		System.out.println(myAtoi("    -9999999999999     wdas ") );
		System.out.println(Integer.MIN_VALUE/10);
		System.out.println(Integer.MIN_VALUE%10);
		System.out.println(Integer.MAX_VALUE);
	}
}
