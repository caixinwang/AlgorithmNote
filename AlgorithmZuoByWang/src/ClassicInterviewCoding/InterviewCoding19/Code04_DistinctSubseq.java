package ClassicInterviewCoding.InterviewCoding19;

public class Code04_DistinctSubseq {

	//dp[i]代表从0~i范围上随便选，有几种不同的字面值
	//map[i]代表必须以str[i]结尾的有几种不同的字面值
	public static int distinctSubseq1(String s) {//别扭
		if (s == null || s.length() == 0) {
			return 0;
		}
		char[] str = s.toCharArray();
		int N=s.length();
		int[] dp=new int[N];
		int[] map=new int[128];
		dp[0]=2;
		map[str[0]]=1;
		for (int i = 1; i < N; i++) {
			dp[i]=dp[i-1]+dp[i-1]-map[str[i]];
			map[str[i]]+=dp[i-1]-map[str[i]];
		}
		return dp[N-1]-1;//空的不算
	}


	public static int distinctSubseq2(String s) {
		if (s == null || s.length() == 0) {
			return 0;
		}
		char[] str = s.toCharArray();
		int[] count=new int[128];
		int res=1;//空序列也算一种
		for (int i = 0; i < str.length; i++) {
			int add = res - count[str[i] - 'a'];
			res += add;
			count[str[i]- 'a'] += add;
		}
		return res-1;//答案不算空集就把空集减掉
	}
	

	public static String random(int len, int varible) {
		int size = (int) (Math.random() * len) + 1;
		char[] str = new char[size];
		for (int i = 0; i < size; i++) {
			str[i] = (char) ((int) (Math.random() * varible) + 'a');
		}
		return String.valueOf(str);
	}

	public static void main(String[] args) {
		int len = 10;
		int varible = 5;
		for (int i = 0; i < 100; i++) {
			String test = random(len, varible);
			if (distinctSubseq1(test) != distinctSubseq2(test)) {
				System.out.println("fuck");
			}
		}
		
		System.out.println("hello");

	}

}
