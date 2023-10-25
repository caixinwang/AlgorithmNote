package BasicLearning.Class07_RecursionToDP;

import TestUtils.ArrayUtil;

public class Code06_Bag {

    static int MIN=1<<31,MAX=MIN-1;
    private static int maxValue(int[] weight, int[] value, int bag) {
        if (weight == null || value == null || value.length == 0 || weight.length == 0 || bag <= 0) {
            return 0;
        }
        return process1(weight, value, 0, bag);
    }

    /**
     * @param weight:固定参数，代表货物的重量，数组的长度代表货物的种类，数组的长度一定和value数组的长度一样
     * @param value:固定参数，代表货物的价值
     * @param index:代表index之前的货物已经做过选择了。
     * @param rest:还剩下多少空间可以装载货物
     * @return :返回的是rest背包种类，对从index下标开始货物做选择能得到的最大价值
     */
    private static int process1(int[] weight, int[] value, int index, int rest) {
        if (index == weight.length) {//base case 已经没有货物可以选了
            return 0;
        }
        int p1 = process1(weight, value, index + 1, rest);//不选index位置的货物所能得到的最大值
        int p2 = rest - weight[index] >= 0 ?
                process1(weight, value, index + 1, rest - weight[index]) + value[index] : 0;
        return Math.max(p1, p2);
    }

    private static int dp1(int[] weight, int[] value,int bag){
        if (weight == null || value == null || value.length == 0 || weight.length == 0 || bag <= 0) {
            return 0;
        }
        int[][] dp=new int[weight.length+1][bag+1];
        //weight.len行默认就是0，已经初始化完毕
        for (int index=weight.length-1;index>=0;index--){//index
            for (int rest=0;rest<=bag;rest++){//bag
                dp[index][rest]=Math.max(dp[index+1][rest],
                        rest - weight[index] >= 0 ? dp[index + 1][ rest - weight[index]] + value[index] : 0);
            }
        }
        return dp[0][bag];
    }

    /** 直接憋动态规划
     * dp[i][j]代表前i个物品自由选择，背包大小为j所能达到的最大价值。---背包不一定装满j
     * @param weight:固定参数，代表货物的重量，数组的长度代表货物的种类，数组的长度一定和value数组的长度一样
     * @param value:固定参数，代表货物的价值
     * @return :返回bag大小的背包所能装到的最大价值
     */
    public static int dp2(int[] weight, int[] value,int bag){
        if (weight == null || value == null || value.length == 0 || weight.length == 0 || bag <= 0) {
            return 0;
        }
        int n=weight.length;
        int[][] dp=new int[n+1][bag+1];
        for (int i=1;i<=n;i++){
            for (int j=0;j<=bag;j++){
                dp[i][j]=dp[i-1][j];
                if (j-weight[i-1]>=0) dp[i][j] = Math.max(dp[i][j], value[i-1]+dp[i-1][j-weight[i-1]]);
            }
        }
        return dp[n][bag];
    }

    /** 直接憋动态规划
     * 改一改可以解决种数的问题
     * dp[i][j]代表前i个物品自由选择，刚好装满了j的背包大小，所能达到的最大价值。---背包一定装满j
     * @param weight:固定参数，代表货物的重量，数组的长度代表货物的种类，数组的长度一定和value数组的长度一样
     * @param value:固定参数，代表货物的价值
     * @return :返回bag大小的背包所能装到的最大价值
     */
    public static int dp3(int[] weight, int[] value,int bag){
        if (weight == null || value == null || value.length == 0 || weight.length == 0 || bag <= 0) {
            return 0;
        }
        int n=weight.length,res=MIN;
        int[][] dp=new int[n+1][bag+1];
        for (int j=1;j<=bag;j++) dp[0][j]=-1;//除了dp[0][0]是0，其它都是-1，代表不能恰好装满j的背包大小
        for (int i=1;i<=n;i++){
            for (int j=0;j<=bag;j++){
                dp[i][j]=dp[i-1][j];//i不选就取决于上面的
                if (j-weight[i-1]>=0&&dp[i-1][j-weight[i-1]]!=-1)//赋值为-1就需要严格判断，如果赋值为MIN/2就不用了
                    dp[i][j] = Math.max(dp[i][j], value[i-1]+dp[i-1][j-weight[i-1]]);
            }
        }
        for (int i = 0; i <=bag; i++) res = Math.max(res, dp[n][i]);//最后一行的最大值就是答案
        return res;
    }

    /** 直接憋动态规划
     * dp[i][j] 代表0~i自由选择，背包里面物品价值恰好为j，最少使用多少背包空间。---背包物品价值一定刚好为j
     * @param weight:固定参数，代表货物的重量，数组的长度代表货物的种类，数组的长度一定和value数组的长度一样
     * @param value:固定参数，代表货物的价值
     * @return :返回bag大小的背包所能装到的最大价值
     */
    public static int dp4(int[] weight, int[] value,int bag){
        if (weight == null || value == null || value.length == 0 || weight.length == 0 || bag <= 0) {
            return 0;
        }
        int n=weight.length,res=MIN,valSum=0;
        for (int i = 0; i < value.length; i++) valSum+=value[i];
        int[][] dp=new int[n+1][valSum+1];
        for (int j=1;j<=valSum;j++) dp[0][j]=MAX>>1;//dp[0][0]才是合法的，其它都是不合法的值,dp放的是最小，所以不合法存最大/2
        for (int i=1;i<=n;i++){
            for (int j=0;j<=valSum;j++){
                dp[i][j] = dp[i - 1][j];
                if (j-value[i-1]>=0){//这种写法由于赋值了MAX/2，不会溢出，也不会影响答案，可以直接去决
                    dp[i][j] = Math.min(dp[i][j],weight[i-1]+dp[i-1][j-value[i-1]]);
                }
            }
        }
        for (int v = 0; v <=valSum; v++) if (bag>=dp[n][v]) res=v;
        return res;
    }

    static ArrayUtil au=new ArrayUtil();
    public static void main(String[] args) {
        for (int i=0;i<100;i++){
            int n=au.ran(1,20);
            int bag = au.ran(1,1000);
            int[] weights = au.generateRandomArr(n,1,100);
            int[] values = au.generateRandomArr(n,1,300);
            int[] v=new int[]{
                    maxValue(weights, values, bag),
                    dp1(weights, values, bag),
                    dp2(weights, values, bag),
                    dp3(weights, values, bag),
                    dp4(weights, values, bag),
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
