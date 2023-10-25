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
	 * 注意，需要保持dp的含义，含义是需要通过前i关！！！
	 * @param d 怪兽武力值，>0
	 * @param p 怪兽的贿赂价格,>0
	 * @return 返回最少花多少钱能通关
	 */
	public static int dp1(int[] d,int[] p){
		if (d.length==0||p.length==0) return -1;
		int n=d.length,sum=0,ans=Integer.MAX_VALUE;
		for (int i=0;i<n;i++) sum+=p[i];//最大贿赂金额
		int[][] dp=new int[n+1][sum+1];//通关前i关，刚好花够j元，所能达到的最大武力值
		for (int[] a:dp) Arrays.fill(a,-1);
		dp[0][0]=0;
		for (int i=1;i<=n;i++){
			for (int j=0;j<=sum;j++){
				if (dp[i-1][j]>=d[i-1]&&dp[i-1][j]!=-1) dp[i][j]=dp[i-1][j];//这一关不贿赂，前提是能力值够通过当前怪兽，从上面转移下来
				if (j-p[i-1]>=0&&dp[i-1][j-p[i-1]]!=-1) dp[i][j] = Math.max(dp[i][j],d[i-1]+dp[i-1][j-p[i-1]]);//决定贿赂
			}
		}
		for (int j=0;j<=sum&&ans==Integer.MAX_VALUE;j++) if (dp[n][j]!=-1) ans =j;
		return ans;
	}

	/**
	 * dp[i][j]代表通关前i关，刚好凑够j的能力值，所花的最少钱数
	 * @param d 怪兽武力值，>0
	 * @param p 怪兽的贿赂价格,>0
	 * @return 返回最少花多少钱能通关。dp数组最后一行中不等于-1最小的值就是答案
	 */
	public static int dp2(int[] d,int[] p){
		if (d.length==0||p.length==0) return -1;
		int n=d.length,MAX=Integer.MAX_VALUE,ans=MAX,sum=0;
		for (var c:d) sum+=c;
		int[][] dp=new int[n+1][sum+1];
		for (var a:dp) Arrays.fill(a,MAX);//最小就赋值为最大
		dp[0][0]=0;
		for (int i = 1; i <= n; i++) {
			for (int j = 0; j <= sum; j++) {
				if (j>=d[i-1]&&dp[i-1][j]!=MAX) dp[i][j]=dp[i-1][j];//不贿赂，要求当前武力值大于怪兽，并且前面的状态有效
				if (j-d[i-1]>=0&&dp[i-1][j-d[i-1]]!=MAX) dp[i][j] = Math.min(dp[i][j],p[i-1]+dp[i-1][j-d[i-1]]);//贿赂
			}
		}
		for (var c:dp[n]) if (c!=MAX) ans = Math.min(ans, c);
		return ans;
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
		int testTimes = 1000000;
		for (int i = 0; i < testTimes; i++) {
			int[][] arrs = generateTwoRandomArray(len, value);
			int[] d = arrs[0];
			int[] p = arrs[1];
			long ans1 = func1(d, p);
//			long ans2 = func2(d, p);
			long ans2 = dp2(d, p);
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
