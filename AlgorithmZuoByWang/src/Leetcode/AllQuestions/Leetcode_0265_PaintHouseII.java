package Leetcode.AllQuestions;

public class Leetcode_0265_PaintHouseII {
    //从左到右的尝试模型，考虑当前房子涂什么颜色，但是我们关心前面的房子涂了什么颜色，所以要加入颜色这个状态
    //我们每次只依赖i-1，所以直接去掉一个维度。但是你会发现我只关心前面一个状态的最优和次优。所以还可以继续砍掉一个维度
    //只维持四个变量即可
    public int minCostII(int[][] costs) {
        int n=costs.length,k=costs[0].length;
        int[] f=new int[k];
        for(int[] cost:costs){
            int[] new_f=new int[k];//需要新开一个，因为新值需要老值，老值不能更新掉
//            int min_idx=0;//最小的cost对应的下标
//            for(int i=0;i<k;i++){
//                if(f[i]<f[min_idx]) min_idx=i;
//            }
//            int min2_idx=min_idx==0?1:0;//次小对应的下标，初始值不能和min_idx一样
//            for(int i=0;i<k;i++){
//                if(i!=min_idx&&f[i]<f[min2_idx]) min2_idx=i;//下标不能和min_idx一样
//            }
            int min_idx=0,min2_idx=1;//最优和次优的揉在一起的写法
            for (int i = 1; i < k; i++) {
                if (f[i]<f[min_idx]){
                    min2_idx=min_idx;
                    min_idx=i;
                }else if (f[i]<f[min2_idx]){
                    min2_idx=i;
                }
            }
            for(int i=0;i<k;i++){
                new_f[i]=cost[i]+(min_idx!=i?f[min_idx]:f[min2_idx]);//当前的房子涂成i号颜色，那么只需要知道前面房子的最小和次小
            }
            f=new_f;
        }
        int ans=Integer.MAX_VALUE;
        for(int c:f) ans=Math.min(ans,c);
        return ans;
    }

    public int minCostII2(int[][] costs) {//不使用额外空间
        int n=costs.length,k=costs[0].length;
        int min1=0,min2=0;//最优、次优
        int min1_color=0,min2_color=1;
        for(int[] cost:costs){
            int new_min1,new_min2,new_min1_color,new_min2_color;
            new_min1=new_min2=Integer.MAX_VALUE;
            new_min1_color=0;new_min2_color=1;
            for (int color = 0; color < k; color++) {
                if (color!=min1_color){//前面的颜色使用min1_color
                    if (cost[color]+min1<new_min1){
                        new_min2=new_min1;
                        new_min2_color=new_min1_color;
                        new_min1=cost[color]+min1;
                        new_min1_color=color;
                    }else if (cost[color]+min1<new_min2){
                        new_min2=cost[color]+min1;
                        new_min2_color=color;
                    }
                }else {//之前的颜色使用min2
                    if (cost[color]+min2<new_min1){
                        new_min2=new_min1;
                        new_min2_color=new_min1_color;
                        new_min1=cost[color]+min2;
                        new_min1_color=color;
                    }else if (cost[color]+min2<new_min2){
                        new_min2=cost[color]+min2;
                        new_min2_color=color;
                    }
                }
            }
            min1=new_min1;
            min2=new_min2;
            min1_color=new_min1_color;
            min2_color=new_min2_color;
        }
        return min1;
    }
}
