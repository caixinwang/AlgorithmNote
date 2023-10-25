package ClassicInterviewCoding.InterviewCoding08;

import java.util.Arrays;

public class Code02_MoneyWays {

	public static int moneyWays(int[] arbitrary, int[] onlyone, int money) {
		if (money < 0) {
			return 0;
		}
		if ((arbitrary == null || arbitrary.length == 0) && (onlyone == null || onlyone.length == 0)) {
			return money == 0 ? 1 : 0;
		}
		// 任意张 的数组， 一张的数组，不可能都没有
		int[][] dparb = getDpArb(arbitrary, money);
		int[][] dpone = getDpOne(onlyone, money);
		if (dparb == null) { // 任意张的数组没有，一张的数组有
			return dpone[dpone.length - 1][money];
		}
		if (dpone == null) { // 任意张的数组有，一张的数组没有
			return dparb[dparb.length - 1][money];
		}
		int res = 0;
		for (int i = 0; i <= money; i++) {
			res += dparb[dparb.length - 1][i] * dpone[dpone.length - 1][money - i];
		}
		return res;
	}

	public static int[][] getDpArb(int[] arr, int money) {
		if (arr == null || arr.length == 0) {
			return null;
		}
		int[][] dp = new int[arr.length][money + 1];
		// dp[i][j] 0..i券 自由选择张数， 搞定j元， 有多少方法？

		for (int i = 0; i < arr.length; i++) {
			dp[i][0] = 1;
		}
		// [0] 5元 0元 5元 10元 15元 20元
		for (int j = 1; arr[0] * j <= money; j++) {
			dp[0][arr[0] * j] = 1;
		}
		// 0行 0列 填完了
		for (int i = 1; i < arr.length; i++) {
			for (int j = 1; j <= money; j++) {
				dp[i][j] = dp[i - 1][j];
				dp[i][j] += j - arr[i] >= 0 ? dp[i][j - arr[i]] : 0;
			}
		}
		return dp;
	}

	public static int[][] getDpOne(int[] arr, int money) {
		if (arr == null || arr.length == 0) {
			return null;
		}
		int[][] dp = new int[arr.length][money + 1];
		for (int i = 0; i < arr.length; i++) {
			dp[i][0] = 1;
		}
		if (arr[0] <= money) {
			dp[0][arr[0]] = 1;
		}
		for (int i = 1; i < arr.length; i++) {
			for (int j = 1; j <= money; j++) {
				dp[i][j] = dp[i - 1][j];
				dp[i][j] += j - arr[i] >= 0 ? dp[i - 1][j - arr[i]] : 0;
			}
		}
		return dp;
	}

	public static int moneyWays2(int[] arbitrary, int[] onlyone, int money) {
		if (money<0) return 0;
		if ((arbitrary==null||arbitrary.length==0) && (onlyone==null||onlyone.length==0)) return money==0?1:0;
		int res=0;
		int[][] dp1 = getDp1(arbitrary,money);
		int[][] dp2 = getDp2(onlyone,money);
		if (dp1==null) return dp2[dp2.length-1][money];
		if (dp2==null) return dp1[dp1.length-1][money];
		for (int i = 0; i <= money; i++) {
			res+=dp1[dp1.length-1][i] * dp2[dp2.length-1][money-i];
		}
		return res;
	}

	/**
	 * dp[i][j]代表使用arr[0...i]凑出money有几种方法
	 * dp[i][j]=sum{dp[i-1][j-k*arr[i] },k∈[0,m],k>m => j<k*arr[i]
	 * @param arr 货币，可以使用无限张，arr[i]代表货币的面值,调用者保证，你给我传的arr一定不为空，且有元素
	 * @param money 用这些货币凑出money
	 * @return dp数组
	 */
	private static int[][] getDp1(int[] arr,int money){//无限张
		if (arr == null || arr.length == 0) {
			return null;
		}
		int N=arr.length;
		int[][]dp=new int[N][money+1];
		dp[0][0]=1;
		for (int i = arr[0]; i <=money; i+=arr[0]) {
			dp[0][i]=1;
		}
		for (int i = 1; i < N; i++) {
			dp[i][0]=1;
		}
		for (int i = 1; i < N; i++) {
			for (int j=1;j<=money;j++){
//				for (int k=0;j-k*arr[i]>=0;k++){
//					dp[i][j]+=dp[i-1][j-k*arr[i]];
//				}
				dp[i][j]+=dp[i-1][j]+(j-arr[i]>=0?dp[i][j-arr[i]]:0);
			}
		}
		return dp;
	}

	private static int[][] getDp2(int[] arr,int money){//有限张
		if (arr == null || arr.length == 0) {
			return null;
		}
		int N=arr.length;
		int[][]dp=new int[N][money+1];
		dp[0][0]=1;
		if (arr[0]<=money) dp[0][arr[0]]=1;//第一行初始化
		for (int i = 1; i < N; i++) {//第一列
			dp[i][0]=1;
		}
		for (int i = 1; i < N; i++) {
			for (int j=1;j<=money;j++){
				for (int k=0;j-k*arr[i]>=0&&k<=1;k++){
					dp[i][j]+=dp[i-1][j-k*arr[i]];
				}
			}
		}
		return dp;
	}


	//test
	public static int[] generateRandomArr(int size,int max){//生成大小固定为size，数值在[0~max]随机的数组,max>0
		int[] res=new int[size];
		for (int i = 0; i < res.length; i++) {
			do{res[i]=ran(max);}
			while (res[i]==0);
		}
		return res;
	}

	//test
	public static int ran(int val){//[0,val]
		return (int)(Math.random()*(val+1));
	}
	//test
	public static void printArr(int[][] arr){
		if (arr==null||arr.length==0)return;
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[0].length; j++) {
				System.out.print(arr[i][j]+" ");
			}
			System.out.println();
		}
		System.out.println();
	}

	//test
	public static void printArr(int[] arr){
		if (arr==null||arr.length==0)return;
		for (int i = 0; i < arr.length; i++) {
				System.out.print(arr[i]+" ");
		}
		System.out.println();
	}
	//test
	public static boolean equals(int[][] arr1,int[][] arr2){
		if (arr1==null&&arr2==null) return true;
		if (arr1==null||arr2==null) return false;
		if (arr1.length!=arr2.length) return false;
		if (arr1[0].length!=arr2[0].length) return false;
		for (int i = 0; i < arr1.length; i++) {
			for (int j = 0; j < arr1[0].length; j++) {
				if (arr1[i][j]!=arr2[i][j]) return false;
			}
		}
		return true;
	}
	//test
	public static void test1(){//测试dp数组
		int times=1000;
		int maxSize=10;
		int max=10;
		int maxMoney=30;
		boolean isok=true;
		int money=0;
		int[] f=generateRandomArr(ran(maxSize),max);
		for (int i = 0; i < times; i++) {
			money=ran(maxMoney);
			int[] t=generateRandomArr(ran(maxSize),max);
			if (!equals(getDpOne(t, money), getDp2(t, money))){
				isok=false;
				f=t;
				break;
			}
		}
//		System.out.println(f);
		System.out.println(Arrays.toString(f));
		printArr(getDpOne(f, money));
		printArr(getDp2(f, money));
		System.out.println(isok?"success":"fail");
	}

	//test
	public static void test2(){//测试
		int times=10000;
		int maxSize=10;
		int max=10;
		int maxMoney=30;
		boolean isok=true;
		int money=0;
		int[] f1=generateRandomArr(ran(maxSize),max);
		int[] f2=generateRandomArr(ran(maxSize),max);
		for (int i = 0; i < times; i++) {
			money=ran(maxMoney);
			int[] t1=generateRandomArr(ran(maxSize),max);
			int[] t2=generateRandomArr(ran(maxSize),max);
			if (moneyWays(t1,t2,money)!=moneyWays2(t1,t2,money)){
				isok=false;
				f1=t1;
				f2=t2;
				break;
			}
		}
		printArr(f1);
		printArr(f2);
		System.out.println(moneyWays(f1,f2,money));
		System.out.println(moneyWays2(f1,f2,money));
		System.out.println(isok?"success":"fail");
	}

	public static void main(String[] args) {
		test2();
	}



}
