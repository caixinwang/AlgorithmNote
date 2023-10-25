package ClassicInterviewCoding.InterviewCoding10;

public class Code01_BestTimetoBuyandSellStock1 {

	//在每一个卖出点i，我们肯定希望我们在0~i的最低点买入，所以我们从左往右遍历，抓住0~i的最小值
	public static int maxProfit(int[] prices) {//枚举卖出点
		if (prices == null || prices.length == 0) {
			return 0;
		}
		int min = prices[0];
		int ans = 0;
		for (int i = 0; i < prices.length; i++) {
			min = Math.min(min, prices[i]);
			ans = Math.max(ans, prices[i] - min);
		}
		return ans;
	}
	//在每一个买入点i，我们肯定希望我们在i~N-1的最高点卖出，所以我们从右往左遍历，抓住0~i的最大值
	public static int maxProfit2(int[] prices) {//枚举买入点
		if (prices == null || prices.length == 0) {
			return 0;
		}
		int N=prices.length;
		int max = prices[N-1];
		int ans = 0;
		for (int i = N-1; i >= 0;i--) {
			max = Math.max(max, prices[i]);
			ans = Math.max(ans, max-prices[i]);
		}
		return ans;
	}

	public static void main(String[] args) {
		int[] arr=new int[]{2,3,4,5,1,4,6,7,4,2,8,9,6,3,4,5,8,5};
		System.out.println(maxProfit(arr));
		System.out.println(maxProfit2(arr));

	}

}
