package ClassicInterviewCoding.InterviewCoding08;

import java.util.Arrays;

public class Code04_MoneyProblem {

	// int[] d d[i]：i号怪兽的武力
	// int[] p p[i]：i号怪兽要求的钱
	// ability 当前你所具有的能力
	// index 来到了第index个怪兽的面前
	// 目前，你的能力是ability，你来到了index号怪兽的面前，如果要通过后续所有的怪兽，
	// 请返回需要花的最少钱数
	public static long process(int[] d, int[] p, int ability, int index) {
		if (index == d.length) {
			return 0;
		}
		if (ability < d[index]) {
			return p[index] + process(d, p, ability + d[index], index + 1);
		} else { // 可以贿赂，也可以不贿赂
			return
					Math.min(
							p[index] + process(d, p, ability + d[index], index + 1),
						    process(d, p, ability, index + 1)
							);
		}
	}

	public static long func1(int[] d, int[] p) {
		return process(d, p, 0, 0);
	}

	public static long func2(int[] d, int[] p) {//暴力递归直接改动态规划
		int sum = 0;
		for (int num : d) {
			sum += num;
		}
		long[][] dp = new long[d.length + 1][sum + 1];
		for (int cur = d.length - 1; cur >= 0; cur--) {
			for (int hp = 0; hp <= sum; hp++) {
				// 如果这种情况发生，那么这个hp必然是递归过程中不会出现的状态
				// 既然动态规划是尝试过程的优化，尝试过程碰不到的状态，不必计算
				if (hp + d[cur] > sum) {
					continue;
				}
				if (hp < d[cur]) {
					dp[cur][hp] = p[cur] + dp[cur + 1][hp + d[cur]];
				} else {
					dp[cur][hp] = Math.min(p[cur] + dp[cur + 1][hp + d[cur]], dp[cur + 1][hp]);
				}
			}
		}
		return dp[0][0];
	}

	/**
	 * dp[i][j]代表通关前i关，刚好花够j元，所能达到的最大武力值.
	 * 这样定义的需要创建的长度是N+1,初始化的时候只需要初始化[0][0]位置的元素,使用d和p数组需要i-1
	 * @param d 怪兽武力值，>0
	 * @param p 怪兽的贿赂价格,>0
	 * @return 返回最少花多少钱能通关
	 */
	public static int dp1(int[] d,int[] p){
		int N=d.length,M=0;
		for (int i=0;i<N;i++) M+=p[i];//最大贿赂金额
		int[][] dp=new int[N+1][M+1];//通关前i关，刚好花够j元，所能达到的最大武力值
		for (int[] a:dp) Arrays.fill(a,-1);
		dp[0][0]=0;
		for (int i=1;i<=N;i++){
			for (int j=0;j<=M;j++){
				if (dp[i-1][j]>=d[i-1]&&dp[i-1][j]!=-1){
					//这一关不贿赂，前提是能力值够通过当前怪兽，从上面转移下来
					dp[i][j]=dp[i-1][j];
				}
				if (j-p[i-1]>=0&&dp[i-1][j-p[i-1]]!=-1) {
					//决定贿赂
					dp[i][j] = Math.max(dp[i][j],d[i-1]+dp[i-1][j-p[i-1]]);
				}
			}
		}
		int ans=-1;
		for (int j=0;j<=M&&ans==-1;j++) if (dp[N][j]!=-1) ans =j;
		return ans;
	}

	/**
	 * f[i][j]通关0~i 恰好花j元 所能达到的最大武力值
	 * 如果是这样定义f[i][j] 那么f长度为N 和上面写法的区别就是初始化的区别
	 */
	public static int dp2(int[] d,int[] p){
		int N=d.length,M=Arrays.stream(p).sum();
		int[][] f = new int[N][M+1];
		for (int[] arr : f) Arrays.fill(arr,-1);
		f[0][p[0]]=d[0];
		for (int i=1;i<N;i++){
			for (int j=1;j<=M;j++){
				if (j-p[i]>=0&&f[i-1][j-p[i]]!=-1){
					f[i][j]=d[i]+f[i-1][j-p[i]];
				}
				if (f[i-1][j]>=d[i]){
					f[i][j] = Math.max(f[i][j], f[i-1][j]);
				}
			}
		}
		int res=-1;
		for (int j = 0; j < f[N - 1].length; j++) {
			if (f[N-1][j]!=-1) {
				res=j;
				break;
			}
		}
		return res;
	}

	/**
	 * dp[i][j]代表通关前i关，刚好凑够j的能力值，所花的最少钱数
	 * 初始化为MAX可以在取最小的时候少一个判断
	 * @param d 怪兽武力值，>0
	 * @param p 怪兽的贿赂价格,>0
	 * @return 返回最少花多少钱能通关。dp数组最后一行中不等于-1最小的值就是答案
	 */
	public static int dp3(int[] d,int[] p){
		int N=d.length,MAX=Integer.MAX_VALUE,ans=MAX,sum=0;
		for (var c:d) sum+=c;
		int[][] dp=new int[N+1][sum+1];
		for (var a:dp) Arrays.fill(a,MAX);//最小就赋值为最大
		dp[0][0]=0;
		for (int i = 1; i <= N; i++) {
			for (int j = 0; j <= sum; j++) {
				if (j>=d[i-1]&&dp[i-1][j]!=MAX) dp[i][j]=dp[i-1][j];//不贿赂，要求当前武力值大于怪兽，并且前面的状态有效
				if (j-d[i-1]>=0&&dp[i-1][j-d[i-1]]!=MAX) dp[i][j] = Math.min(dp[i][j],p[i-1]+dp[i-1][j-d[i-1]]);//贿赂
			}
		}
		for (var c:dp[N]) if (c!=MAX) ans = Math.min(ans, c);
		return ans;
	}

	//f[i][j] 代表通关0~i 恰好在i处凑够j能力值 所花的最少钱数
	public static int dp4(int[] d,int[] p){
		int N=d.length,M=Arrays.stream(d).sum();
		int[][] f=new int[N][M+1];
		for (var a:f) Arrays.fill(a,-1);//最小就赋值为最大
		f[0][0]=0;
		for (int i = 1; i <= N; i++) {
			for (int j = 0; j <= M; j++) {
				if (j>=d[i-1]&&f[i-1][j]!=-1) {
					f[i][j]=f[i-1][j];//不贿赂，要求当前武力值大于怪兽，并且前面的状态有效
				}
				if (j-d[i-1]>=0&&f[i-1][j-d[i-1]]!=-1) {//不初始化为MAX需要多一次判断
					if (f[i][j]!=-1)f[i][j] = Math.min(f[i][j],p[i-1]+f[i-1][j-d[i-1]]);//贿赂
					else f[i][j]=p[i-1]+f[i-1][j-d[i-1]];
				}
			}
		}
		return Arrays.stream(f[N-1]).filter(a->a!=-1).min().orElse(-1);
	}


	//f[i][j] 从i处开始 初始有j的能力值 通关i~N 至少需要多少钱
	public static int dp5(int[] d,int[] p){
		int N=d.length,M=Arrays.stream(d).sum();
		int[][] f = new int[N+1][M+1];
		for (int i=N-1;i>=0;i--){
			for (int j=M-1;j>=0;j--){
				//这种填法可以保证从f[0][0]依赖的格子都可以填对 f[0]其它的格子可能会依赖错误的值,因为第一个if越界直接跳过了
				//这个if语句f[0][0]所依赖的格子都能进去
//				if (j+d[i]<=M) f[i][j]=p[i]+f[i+1][j+d[i]];
//				if (j>=d[i]) f[i][j]=Math.min(f[i+1][j],f[i][j]);

				//如果写的严谨一点,可以进行-1的判断,但是对于f[0][0]依赖的格子没有影响
				f[i][j]=-1;
				if (j+d[i]<=M&&f[i+1][j+d[i]]!=-1) f[i][j]=p[i]+f[i+1][j+d[i]];
				if (j>=d[i]&&f[i+1][j]!=-1) {
					if (f[i][j]!=-1)f[i][j]=Math.min(f[i+1][j],f[i][j]);
					else f[i][j]=f[i+1][j];
				}
			}
		}
		return f[0][0];
	}

	public static int[][] generateTwoRandomArray(int len, int value) {
		int size = (int) (Math.random() * len) + 1;
		int[][] arrs = new int[2][size];
		for (int i = 0; i < size; i++) {
			arrs[0][i] = (int) (Math.random() * value) + 1;
			arrs[1][i] = (int) (Math.random() * value) + 1;
		}
		return arrs;
	}

	public static void main(String[] args) {
		int len = 10;
		int value = 20;
		int testTimes = 10000;
		for (int i = 0; i < testTimes; i++) {
			int[][] arrs = generateTwoRandomArray(len, value);
			int[] d = arrs[0];
			int[] p = arrs[1];
			long ans1 = func1(d, p);
//			long ans2 = func2(d, p);
			long ans2 = dp5(d, p);
//			long ans3 = func3(d, p);
			long ans3 = dp1(d, p);
			if (ans1 != ans2 || ans2 != ans3) {
				System.out.println("oops!");
				System.out.println(ans1);
				System.out.println(ans2);
				System.out.println(ans3);
				break;
			}
		}



	}

}
