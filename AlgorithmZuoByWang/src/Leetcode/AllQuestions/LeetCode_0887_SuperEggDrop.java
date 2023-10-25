package Leetcode.AllQuestions;

import java.util.Arrays;

public class LeetCode_0887_SuperEggDrop {

    /**
     * dp[i][j]的含义是我有i个蛋，可以扔j次，最多搞定多少楼？
     * 我在任意一个位置扔，如果碎了，我知道下面还可以搞定dp[i-1][j-1]层，没碎上面可以搞定dp[i][j-1]层，
     * 所以dp[i][j]=1+dp[i-1][j-1]+dp[i][j-1]。空间压缩，横着滚动更新，值刚超n我就停。
     * dp[i]=1+dp[i]+t,这个t存着左上角的值
     * @param k 蛋的数量
     * @param n 要搞定的楼层的数量
     * @return 返回最少扔几次
     */
    public static int superEggDrop(int k, int n) {//k个蛋，n层楼
        int[] dp=new int[k+1];//横着更新,第一列默认都是0，因为扔0次，肯定只能搞定0层
        for (int throwTimes=1;;throwTimes++){//第一列默认正确，从第二列开始
            int t=0;//存左上角dp[i-1][j-1]的值
            for (int eggs=1;eggs<dp.length;eggs++){//dp[0]也默认正确，因为0个蛋什么也解决不了
                int val=1+dp[eggs]+t;
                if (val>=n) return throwTimes;
                t=dp[eggs];//覆盖之前先存起来
                dp[eggs]=val;
            }
        }
    }

    /**
     * dp[i][j]的含义是有i个蛋，解决j层楼，至少要扔几次？
     * 枚举扔在m层(1~j)，如果碎了，那么需要到下面去试，1+dp[i-1][m-1]。如果没碎，需要去上面试，1+dp[i][j-m]。
     * 假定你总是最倒霉的，所以扔在m层，你需要扔1+max{dp[i-1][m-1],dp[i][j-m]}
     * dp[i][j]的值为枚举所有的m，选出min，1+min{max{dp[i-1][m-1],dp[i][j-m]......}}
     *
     */
    public static int superEggDrop2(int k, int n) {//是对的，但是超时，需要四边形不等式优化。
       int[][]dp=new int[k+1][n+1];
        //第一行，只有0个蛋，什么也解决不了，无意义。
        //第二行，有1个蛋，解决j楼需要扔j次。
        for (int j=1;j<=n;j++) dp[1][j]=j;
        //第一个列，解决0层楼，你一次也不用扔，默认填好了。这样从第三行，第二列开始填
        for (int j=1;j<=n;j++){//由于依赖自己左边的值，所以我们先把一列填好，这样就可以从任意行开始填了,为平行四边形优化做铺垫
            for (int i=k;i>=2;i--){
                int min=Integer.MAX_VALUE;
                for (int m=1;m<=j;m++){
                    min = Math.min(min, Math.max(dp[i-1][m-1],dp[i][j-m]));
                }
                dp[i][j]=1+min;
            }
        }
        return dp[k][n];
    }

    public static int superEggDrop3(int k, int n) {//四边形不等式优化
        int[][]dp=new int[k+1][n+1];
        int[][]best=new int[k+1][n+1];
        //第一行，只有0个蛋，什么也解决不了，无意义。
        //第二行，有1个蛋，解决j楼需要扔j次。
        for (int j=1;j<=n;j++) dp[1][j]=j;
        for (int[] ints:best) Arrays.fill(ints,-1);
        //第一个列，解决0层楼，你一次也不用扔，默认填好了。这样从第三行，第二列开始填
        for (int j=1;j<=n;j++){
            for (int i=k;i>=2;i--){
                int min=Integer.MAX_VALUE;
                int bottom=j-1>=0&&best[i][j-1]!=-1?best[i][j-1]:1;//左边要bottom
                int limit=i+1<=k&&best[i+1][j]!=-1?best[i+1][j]:j;//下面要limit
                for (int m=bottom;m<=limit;m++){
                    if (Math.max(dp[i-1][m-1],dp[i][j-m])<=min){//一定是<=不然会出错
                        min=Math.max(dp[i-1][m-1],dp[i][j-m]);
                        best[i][j]=m;
                    }
                }
                dp[i][j]=1+min;
            }
        }
        return dp[k][n];
    }

    public static void main(String[] args) {
        System.out.println(superEggDrop(5,200));
        System.out.println(superEggDrop2(5,200));
        System.out.println(superEggDrop3(5,200));
    }

}
