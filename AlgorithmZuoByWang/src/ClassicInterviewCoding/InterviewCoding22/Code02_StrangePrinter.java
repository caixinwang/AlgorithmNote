package ClassicInterviewCoding.InterviewCoding22;

public class Code02_StrangePrinter {

	public static int strangePrinter(String s){
		if (s==null||s.length()==0) return 0;
		char[] str = s.toCharArray();
		return process(str,0,str.length-1);
	}

	/**
	 * 贪心：第一转只转str[l]的字符不会影响最后的结果。我们先不想具体的，我们只知道第一转肯定会把str[l]搞定，并且递归的第一转肯定也会把
	 * str[i+1]搞定，所以自然地想到，如果两个位置相等，跨越两个区间的时候，跨越一起搞定。
	 * 返回把str[l...r]全部都转出来最少需要多少转
	 */
	private static int process(char[] str, int l, int r) {
		if (l==r) return 1;
//		int min=1+process(str,l+1,r);
//		for (int i=l+1;i<r;i++){//第一转把[l...i]位置字符全部变成str[l]
//			min = Math.min(min, 1+process(str,l+1,i)-(str[l]==str[l+1]?1:0)+process(str,i+1,r)-(str[l]==str[i+1]?1:0));
//		}
		int min=Integer.MAX_VALUE;
		for (int i=l;i<r;i++){
			min = Math.min(min, process(str,l,i)+process(str,i+1,r)-(str[l]==str[i+1]?1:0));
		}
		return min;
	}

	public static int strangePrinter2(String s){
		if (s==null||s.length()==0) return 0;
		char[] str = s.toCharArray();
		int N=str.length;
		int[][] dp=new int[N][N];
		for (int i = 0; i < N; i++) {
			dp[i][i]=1;
		}
		for (int j = 1; j < N; j++) {
			for (int i = 0; i+j < N; i++) {
				int l=i,r=i+j;
				int min=Integer.MAX_VALUE;
				for (int k=l;k<r;k++){
					min = Math.min(min, dp[l][k]+dp[k+1][r]-(str[l]==str[k+1]?1:0));
				}
				dp[l][r]= min;
			}
		}
		return dp[0][N-1];
	}

	public static void main(String[] args) {
		String s="aadasds";
		System.out.println(strangePrinter(s));
		System.out.println(strangePrinter2(s));
	}

}
