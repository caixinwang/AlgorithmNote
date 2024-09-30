package BasicLearning.Class07_RecursionToDP;

import TestUtils.ArrayUtil;

import java.util.Arrays;

public class Code06_Bag {

    static int MIN=1<<31,MAX=MIN-1;

    private static int maxValue(int[] weight, int[] value, int bag) {
        return process1(weight, value, 0, bag);
    }

    /**
     * 递归
     * @param weight:长度N 货物的重量
     * @param value:长度N 货物的价值
     * @param index:从index位置出发选到N位置
     * @param rest:还可以装rest重量的物品
     * @return :从index位置开始到N位置结束,还可以装rest的重量,返回能装的最大价值
     */
    private static int process1(int[] weight, int[] value, int index, int rest) {
        if (index==weight.length) return 0;
        int p1 = process1(weight, value, index + 1, rest);//不选index位置的货物所能得到的最大值
        int p2 = rest - weight[index] >= 0 ?
                process1(weight, value, index + 1, rest - weight[index]) + value[index] : 0;
        return Math.max(p1, p2);
    }

    //dp[i][j]:i位置起始有j的空间,从i位置出发选到N位置,所能达到的最大价值
    //任意一个位置[i][j]依赖[i+1][j]与[i+1][j-w[i]] , 只需要初始化i=N的情况 控制(j-w[i])>=0即可
    //i=N时,已经没有物品可选,所以最后一行初始化为0即可,默认值就是0,所以已经默认初始化好
    //[i][j]依赖正下方和下方偏左的位置 从下往上填dp 左右方向任意
    private static int dp1(int[] weight, int[] value,int bag){
        int N=value.length;
        int[][] dp=new int[N+1][bag+1];
        for (int i=N-1;i>=0;i--){
            for (int j=0;j<=bag;j++){
                //不选i位置的东西
                dp[i][j]=dp[i+1][j];
                //选i位置的东西
                if (j>=weight[i]){
                    dp[i][j] = Math.max(dp[i][j],dp[i+1][j-weight[i]]+value[i]);
                }
            }
        }
        return dp[0][bag];
    }

    /**
     * dp[i][j]代表起始空间为bag（空间不一定用完） 前i个物品自由选择 所能达到的最大价值
     * 操作weight和value数组的时候需要i-1,因为多初始化了一行
     */
    public static int dp2(int[] weight, int[] value,int bag){
        int N=weight.length;
        int[][] dp=new int[N+1][bag+1];
        for (int i=1;i<=N;i++){
            for (int j=0;j<=bag;j++){
                dp[i][j]=dp[i-1][j];
                if (j-weight[i-1]>=0) {
                    dp[i][j] = Math.max(dp[i][j], value[i-1]+dp[i-1][j-weight[i-1]]);
                }
            }
        }
        return dp[N][bag];
    }

    /**
     * 如果要求能选到最大值的组合种数,这里的dp含义改出来
     * dp[i][j]代表前i个物品自由选择，恰好装满了j的背包大小，所能达到的最大价值。
     * @param weight:固定参数，代表货物的重量，数组的长度代表货物的种类，数组的长度一定和value数组的长度一样
     * @param value:固定参数，代表货物的价值
     * @return :返回bag大小的背包所能装到的最大价值
     */
    public static int dp3(int[] weight, int[] value,int bag){
        int N=weight.length;
        int[][] dp=new int[N+1][bag+1];
        for(int[] a:dp) Arrays.fill(a,-1);//-1代表无法完成
        dp[0][0]=0;//前0个物品自由选择 就是没有物品的情况下 只能恰好装满0的空间
        for (int i=1;i<=N;i++){
            for (int j=0;j<=bag;j++){
                dp[i][j]=dp[i-1][j];//dp[i-1][j]是否为 -1 的情况都包含在这个语句里面
                if (j-weight[i-1]>=0&&dp[i-1][j-weight[i-1]]!=-1) {
                    dp[i][j] = Math.max(dp[i][j], value[i - 1] + dp[i - 1][j - weight[i - 1]]);
                }
            }
        }
        return Arrays.stream(dp[N]).filter(a->a!=-1).max().orElse(-1);
    }

    /**
     * dp[i][j] 代表前 i 个物品自由选择，总价值恰好为 j ，最少使用多少空间。
     * 不合法使用MIN>>1 与dp3 的写法作对比
     */
    public static int dp4(int[] weight, int[] value,int bag){
        int N=weight.length,M=Arrays.stream(value).sum(),res=MIN;
        int[][] dp=new int[N+1][M+1];
        for (int[]a:dp) Arrays.fill(a,MAX>>1);
        dp[0][0]=0;
        for (int i=1;i<=N;i++){
            for (int j=0;j<=M;j++){
                dp[i][j] = dp[i - 1][j];
                if (j-value[i-1]>=0){//这种写法由于赋值了MAX/2，+w[i]不会溢出，也不会影响答案，可以直接取最小
                    dp[i][j] = Math.min(dp[i][j],weight[i-1]+dp[i-1][j-value[i-1]]);
                }
            }
        }
        for (int v = 0; v <=M; v++) {
            //从前往后不需要break,自动出来就是最大
            if (bag >= dp[N][v]) res = v;
        }
        return res;
    }

    //dp[i][j] 代表从i开始,i~N自由选择 恰好选够j的总价值 最少需要多少空间
    public static int dp5(int[] weight, int[] value,int bag){
        int N=weight.length,M=Arrays.stream(value).sum();
        int[][] dp=new int[N+1][M+1];
        for (int[]a:dp) Arrays.fill(a,-1);
        dp[N][0]=0;
        for (int i=N-1;i>=0;i--){
            for (int j=0;j<=M;j++){
                dp[i][j]=dp[i+1][j];
                if (j-value[i]>=0&&dp[i+1][j-value[i]]!=-1){
                    if (dp[i][j]==-1) dp[i][j] = dp[i+1][j-value[i]]+weight[i];
                    else dp[i][j]=Math.min(dp[i][j],dp[i+1][j-value[i]]+weight[i]);
                }
            }
        }
        for (int i=0,j=M;j>=0;j--){
            if (dp[i][j]!=-1&&dp[i][j]<=bag) return j;
        }
        return -1;
    }



    static ArrayUtil au=new ArrayUtil();
    public static void main(String[] args) {
        for (int i=0;i<10000;i++){
            int n=au.ran(1,20);
            int bag = au.ran(1,1000);
            int[] weights = au.generateRandomArr(n,1,10);
            int[] values = au.generateRandomArr(n,1,10);
            int[] v=new int[]{
                    maxValue(weights, values, bag),
                    dp1(weights, values, bag),
                    dp2(weights, values, bag),
                    dp3(weights, values, bag),
                    dp4(weights, values, bag),
                    dp5(weights, values, bag),
            };
            for (int j = 0; j < v.length-1; j++) {
                if (v[j]!=v[j+1]) {
                    System.out.println("oops!");
                    au.printArr(v);
                    break;
                }
            }
        }
    }

}
