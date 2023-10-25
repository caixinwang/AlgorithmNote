package Leetcode.AllQuestions;

public class Leetcode_0121_BestTimeToBuyAndSellStock {

	public static int maxProfit(int[] prices) {//同时抓最大最小，其实没必要，抓最小就行了
		int min=prices[0],max=prices[0];
		int ans=0;
		for (int price : prices) {
			if (price<min){//最大值只能在最小值后面，最小值更新最大值也要更新
				min=price;
				max=price;
			}else {
				max = Math.max(max, price);
			}
			ans = Math.max(ans, max-min);
		}
		return ans;
	}

	public static int maxProfit2(int[] prices) {//同时抓最大最小，其实没必要，抓最小就行了
		int min=prices[0],ans=0;
		for (int price : prices) {
			if (price<min) min=price;
			ans = Math.max(ans, price-min);
		}
		return ans;
	}

}
