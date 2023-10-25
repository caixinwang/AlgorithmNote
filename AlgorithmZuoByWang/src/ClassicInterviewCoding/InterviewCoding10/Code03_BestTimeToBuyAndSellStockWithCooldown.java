package ClassicInterviewCoding.InterviewCoding10;

public class Code03_BestTimeToBuyAndSellStockWithCooldown {
    /**
     * sell[i]表示不一定在i位置卖出,0~i上做交易所能得到的最大收益，最后一次操作是卖出
     * sell[i]=max{sell[i-1],arr[i]+buy[i-1]},i位置不参与就是sell[i-1]，i位置参与的话需要一个buy指标，告诉我们
     * 在之前的哪一个位置买入最好。
     * buy[i]不一定在i位置有买入行为，也就是0~i上做交易的收益再减去一个买入的消耗 的最大值,最后一次操作是买入的操作
     */
    public int maxProfit(int[] arr) {
        if (arr==null||arr.length<2) return 0;
        if (arr.length==2) return Math.max(0,arr[1]-arr[0]);
        int[] sell=new int[arr.length];
        int[] buy=new int[arr.length];
        sell[1]=Math.max(0,arr[1]-arr[0]);
        buy[1]=Math.max(-arr[1],-arr[0]);
        for (int i=2;i< arr.length;i++){
            sell[i] = Math.max(sell[i-1],arr[i]+buy[i-1]);
            buy[i] = Math.max(buy[i-1],sell[i-2]-arr[i]);
        }
        return sell[arr.length-1];
    }
}
