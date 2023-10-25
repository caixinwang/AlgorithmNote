package ClassicInterviewCoding.InterviewCoding06;

public class Code07_PMinParts {

	public static int minParts(String s) {
		if (s==null||s.length()==0) return 0;
		if (s.length()==1) return 1;//单个字符
		int N=s.length();
		char[] str = s.toCharArray();
		int[] dp=new int[N];//dp[i]代表0~i,最少划分部分
		dp[0]=1;//arr[0..0]本身就是回文
		boolean[][] isP=new boolean[N][N];//isP[i][j]代表arr[i...j]是不是回文串
		for (int i=0;i<N;i++) isP[i][i]=true;//第一条对角线
		for (int i=0;i+1<N;i++) isP[i][i+1]=str[i]==str[i+1];//第二条对角线
		for (int j=2;j<N;j++){//填写后面的对角线，列举起始位置(0,j)
			for (int i=0;i+j<N;i++){//对角线位置(0+i,j+i)
				isP[i][i+j]=isP[i+1][i+j-1]&&str[i]==str[i+j];
			}
		}
		for (int j=1;j<N;j++){//填写dp[j]
			for (int i=j;i>=0;i--){//列举[i...j]的所有情况，看看其中哪些是回文串，利用前面的答案
				if (isP[i][j]){
					if (i==0) dp[j]=1;//如果i==0，说明arr[0...j]一整个都是回文串
					else dp[j] = Math.min(dp[j], dp[i-1]+1);//更新迭代最小的
				}
			}
		}
		return dp[N-1];
	}

	//和上面做法的区别：填写isP时是按照从下往上从左往右填的。并且dp[i]的含义是arr[i...N-1]的最少回文部分
	//填写dp[i]的时候，没有对边界情况进行判断，也就是end==N-1时，通过拓展了dp的长度为N+1，统一了所有情况
	public static int minParts2(String s) {
		if (s == null || s.length() == 0) {
			return 0;
		}
		if (s.length() == 1) {
			return 1;
		}
		char[] str = s.toCharArray();
		int N = str.length;
		boolean[][] isP = new boolean[N][N];
		for (int i = 0; i < N; i++) {
			isP[i][i] = true;
		}
		for (int i = 0; i < N - 1; i++) {
			isP[i][i + 1] = str[i] == str[i + 1];
		}
		for (int row = N - 3; row >= 0; row--) {
			for (int col = row + 2; col < N; col++) {
				isP[row][col] = str[row] == str[col] && isP[row + 1][col - 1];
			}
		}
		int[] dp = new int[N + 1];
		for (int i = 0; i <= N; i++) {
			dp[i] = Integer.MAX_VALUE;
		}
		dp[N] = 0;
		for (int i = N - 1; i >= 0; i--) {
			for (int end = i; end < N; end++) {
				// i..end
				if (isP[i][end]) {
					dp[i] = Math.min(dp[i], 1 + dp[end + 1]);
				}
			}
		}
		return dp[0];
	}

	public static void main(String[] args) {
		String test = "aba12321412321TabaKFK";
		System.out.println(minParts(test));
	}

}
