package ClassicInterviewCoding.InterviewCoding10;

public class Code02_BestTimetoBuyandSellStock2 {

	//压榨prices中所有的上升点
	public static int maxProfit(int[] prices) {
		if (prices == null || prices.length == 0) {
			return 0;
		}
		int ans = 0;
		for (int i = 1; i < prices.length; i++) {
            ans += Math.max(prices[i] - prices[i-1], 0);
		}
		return ans;
	}

	//压榨prices中所有的上升“线段” --  玩一下
	public static int maxProfit2(int[] prices) {
		if (prices == null || prices.length == 0) {
			return 0;
		}
		int ans = 0;
		boolean isRise=false;
		int bottom=0;
		int top=0;
		for (int i = 1; i < prices.length; i++) {
			if (prices[i]>prices[i-1]){//处于上升期
				if (!isRise) {//刚进入上升期，更新底部
					isRise = true;
					bottom=prices[i-1];
				}
				top=prices[i];
			}else {//处于下降期
				if (isRise){//刚刚进入下降期
					isRise=false;
					ans+=top-bottom;
				}
			}
		}
		return ans;
	}

	public static void main(String[] args) {
		int[] arr=new int[]{2,3,4,5,1,4,6,7,4,2,8,9,6,3,4,5,8,5};
		System.out.println(maxProfit(arr));
		System.out.println(maxProfit2(arr));
	}

}
