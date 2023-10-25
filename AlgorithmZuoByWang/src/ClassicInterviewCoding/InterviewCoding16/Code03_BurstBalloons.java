package ClassicInterviewCoding.InterviewCoding16;

public class Code03_BurstBalloons {

	public static int maxCoins1(int[] arr) {
		if (arr==null||arr.length==0) return 0;
		if (arr.length==1) return arr[0];
		int N=arr.length;
		int[] help =new int[N+2];
		help[0]=1;
		help[help.length-1]=1;
		for (int i = 0; i < arr.length; i++) {
			help[i+1]=arr[i];
		}
		return process(help,1,N);
	}

	/**
	 * 潜台词：l-1与r+1的气球不爆。主函数调用的时候一定保证两个卡边缘的位置不要给我传进来
	 * 可能性列举：枚举所有球最后爆的情况。开头结尾可能性最特殊先列出来
	 * @param arr 气球数组
	 * @param l 从l~r范围上把气球打爆的最高得分。l>1,r<arr.len-1，最后两个位置永远保证不给我传进来
	 * @param r -
	 * @return 返回从l~r范围上把所有气球打爆的最高得分
	 */
	private static int process(int[] arr, int l, int r) {//
		if (l==r) return arr[l-1]*arr[l]*arr[r+1];
		int max=Integer.MIN_VALUE;
		max = Math.max(max,arr[l-1]*arr[l]*arr[r+1]+process(arr,l+1,r));
		max = Math.max(max,arr[l-1]*arr[r]*arr[r+1]+process(arr,l,r-1));
		for (int i = l+1; i <r ; i++) {
			max = Math.max(max,arr[l-1]*arr[i]*arr[r+1]+process(arr,l,i-1)+process(arr,i+1,r));
		}
		return max;
	}

	/**
	 * 暴力递归改动态规划。注意process中的arr和我们下面的help才是一样的
	 * 依赖关系就是依赖自己左下角的值，我们沿着对角线填写肯定没有问题，注意避开第一个和最后一个
	 */
	public static int maxCoins2(int[] arr) {
		if (arr==null||arr.length==0) return 0;
		if (arr.length==1) return arr[0];
		int N=arr.length;
		int[] help =new int[N+2];
		help[0]=1;
		help[help.length-1]=1;
		for (int i = 0; i < arr.length; i++) {
			help[i+1]=arr[i];
		}
		//process里面的那个参数arr，其实就是这里的help
		int[][] dp=new int[help.length][help.length];//首尾不用
		for (int i = 1; i < help.length-1 ; i++) {//第一条对角线，控制行号
			dp[i][i]=help[i-1]*help[i]*help[i+1];
		}
		for (int j = 1; j < help.length-1; j++) {//第二条对角线到倒数第二条对角线
			for (int i = 1; i+j<help.length-1; i++) {//对角线括跨越的行号从[1~len-2]
				int l=i,r=i+j;
				int max=Integer.MIN_VALUE;
				max = Math.max(max,help[l-1]*help[l]*help[r+1]+process(help,l+1,r));
				max = Math.max(max,help[l-1]*help[r]*help[r+1]+process(help,l,r-1));
				for (int k = l+1; k <r ; k++) {
					max = Math.max(max,help[l-1]*help[k]*help[r+1]+process(help,l,k-1)+process(help,k+1,r));
				}
				dp[l][r]=max;
			}
		}
		return dp[1][N];
	}

	public static void main(String[] args) {
		int[] arr = { 4, 2,56,34,6,23,12,3,1,4,34,5,5,36};
		System.out.println(maxCoins1(arr));
		System.out.println(maxCoins2(arr));
	}

}
