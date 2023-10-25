package Leetcode.AllQuestions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Leetcode_0568_MaximumVacationDays {

	public static int maxVacationDays(int[][] fly, int[][] day) {
		int n = fly.length;
		int k = day[0].length;
		// pas[i] = {a, b, c}
		// 从a、b、c能飞到i
		int[][] pass = new int[n][];
		for (int i = 0; i < n; i++) {
			int s = 0;
			for (int j = 0; j < n; j++) {
				if (fly[j][i] != 0) {
					s++;
				}
			}
			pass[i] = new int[s];
			for (int j = n - 1; j >= 0; j--) {
				if (fly[j][i] != 0) {
					pass[i][--s] = j;
				}
			}
		}
		// dp[i][j] -> 第i周必须在j这座城，0~i-1周（随意），最大休假天数
		int[][] dp = new int[k][n];
		// 飞的时机，是周一早上飞，认为对时间没有影响，直接到某个城，然后过一周
		dp[0][0] = day[0][0];
		for (int j = 1; j < n; j++) {
			dp[0][j] = fly[0][j] != 0 ? day[j][0] : -1;
		}
		for (int i = 1; i < k; i++) { // 第i周
			for (int j = 0; j < n; j++) { // 在j号城过！
				// 第i周，要怎么到j号城
				// 下面max的初始值，我第i-1周，就在j号城，选择不动地方，进入第i周
				int max = dp[i - 1][j];
				for (int p : pass[j]) { // 枚举什么？能到j号城的城市p
					max = Math.max(max, dp[i - 1][p]);
				}
				dp[i][j] = max != -1 ? max + day[j][i] : -1;
			}
		}
		int ans = 0;
		for (int i = 0; i < n; i++) {
			ans = Math.max(ans, dp[k - 1][i]);
		}
		return ans;
	}

	int MIN=1<<31,MAX=MIN-1;

	//dp[i][j]的含义:第i周在j号城过，可以得到的最大休假天数
	//为了方便得到有哪些城市可以到达j号城市，所以我们需要对flights数组进行加工
	public int maxVacationDays2(int[][] flights, int[][] days) {
		int n=days.length,k=days[0].length;
		ArrayList<List<Integer>> citys=new ArrayList<>();
		for(int i=0;i<n;i++) citys.add(new ArrayList<Integer>());
		for(int from=0;from<n;from++){
			for(int to=0;to<n;to++){
				if(flights[from][to]==0) continue;
				citys.get(to).add(from);//这些航班没有自己到自己的线路，但是我们是可以选择留下的
			}
		}
		int[][] dp=new int[k][n];//第一个维度是周数，第二个维度是城市的数量
		for(int[] a:dp) Arrays.fill(a,MIN);
		dp[0][0]=days[0][0];//一开始所在的城市为0号城
		for(int to=1;to<n;to++){//第一周如果0号城市有到别的城市的航班，你也可以去别的城市
			if(flights[0][to]==1)dp[0][to]=days[to][0];
		}
		for(int i=1;i<k;i++){
			for(int j=0;j<n;j++){
				dp[i][j]=dp[i-1][j]+days[j][i];
				for(int city:citys.get(j)) dp[i][j]=max(dp[i][j],dp[i-1][city]+days[j][i]);
			}
		}
		int ans=MIN;
		for(int v:dp[k-1]) ans=max(ans,v);
		return ans;
	}
	public int max(int a,int b){return a>b?a:b;}


}
