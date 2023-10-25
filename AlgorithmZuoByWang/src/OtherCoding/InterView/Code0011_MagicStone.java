package OtherCoding.InterView;

import TestUtils.ArrayUtil;

import java.util.Arrays;

// 来自小红书
// [0,4,7] ： 0表示这里石头没有颜色，如果变红代价是4，如果变蓝代价是7
// [1,X,X] ： 1表示这里石头已经是红，而且不能改颜色，所以后两个数X无意义
// [2,X,X] ： 2表示这里石头已经是蓝，而且不能改颜色，所以后两个数X无意义
// 颜色只可能是0、1、2，代价一定>=0
// 给你一批这样的小数组，要求最后必须所有石头都有颜色，且红色和蓝色一样多，返回最小代价
// 如果怎么都无法做到所有石头都有颜色、且红色和蓝色一样多，返回-1
public class Code0011_MagicStone {

    static int [][] dp;
    public static int minCost(int[][] stones) {//把所有的0石头抓出来，变成背包问题，加一个条件限制
        int N = stones.length;
        if ((N & 1) == 1) return -1;
        int red = 0, blue = 0, zeros = 0;
        for (int[] stone : stones) {
            if (stone[0] == 0) {
                zeros++;
            } else if (stone[0] == 1) {
                red++;
            } else {
                blue++;
            }
        }
        int half = N >> 1;
        if (red > half || blue > half) return -1;
        int[][] zeroStone = new int[zeros][3];
        int index = 0;
        for (int[] stone : stones) {
            if (stone[0] == 0) zeroStone[index++] = stone;
        }
        int restRed = half - red;
        dp=new int[zeroStone.length+1][restRed+1];
        for (int[] ints : dp) Arrays.fill(ints,Integer.MAX_VALUE);
        return f(zeroStone, 0, restRed);
    }

    public static int f(int[][] zeroStone, int index, int restRed) {//暴力递归改记忆化搜索
        if (dp[index][restRed]!=Integer.MAX_VALUE) return dp[index][restRed];
        if (index == zeroStone.length) {
            dp[index][restRed]=0;
            return dp[index][restRed];
        }
        int p1 = Integer.MAX_VALUE, p2 = Integer.MAX_VALUE;
        if (restRed > 0) p1 = zeroStone[index][1]+f(zeroStone, index+1, restRed - 1);
        if (zeroStone.length-index-restRed>0) p2 = zeroStone[index][2]+f(zeroStone, index+1, restRed);
        dp[index][restRed]=Math.min(p1,p2);
        return dp[index][restRed];
    }

    /**
     * 统一先变为红色，所以最后要选一些变回蓝色，那么产生的差值就是a[2]-a[1]，我们希望这个差值越小越好，这样我们的cost就会尽可能变小
     */
    public static int minCost2(int[][] stones) {
        int N = stones.length;
        if ((N & 1) == 1) return -1;
        int red = 0, blue = 0, cost = 0, zeros = 0;
        Arrays.sort(stones, (a, b) -> a[0] == 0 && b[0] == 0 ? (a[2] - a[1]) - (b[2] - b[1]) : a[0] - b[0]);
        for (int[] stone : stones) {
            if (stone[0] == 0) {
                zeros++;
                cost += stone[1];
            } else if (stone[0] == 1) {
                red++;
            } else {
                blue++;
            }
        }
        int half = N >> 1;
        if (red > half || blue > half) return -1;
        int rest = half - blue;//现在假设0石头已经全部变成红色了，还有这么多需要变为蓝色
        for (int i = 0; i < rest; i++) {//由于我们的排序策略，现在stones数组前面的就是差值小的，我们直接加即可
            cost += stones[i][2] - stones[i][1];
        }
        return cost;
    }

    static ArrayUtil au = new ArrayUtil();

    public static int[][] generateStones() {
        int size = au.ran(1, 100);
        int[][] ans = new int[size][3];
        for (int[] an : ans) {
            an[0] = au.ran(0, 2);
            an[1] = au.ran(1, 100);
            an[2] = au.ran(1, 100);
        }
        return ans;
    }

    public static void main(String[] args) {
        int times = 10000;
        for (int i = 0; i < times; i++) {
            int[][] stones = generateStones();
            int[][] stones2 = au.copyMatrix(stones);
            int ans1=minCost(stones),ans2=minCost2(stones2);
            if ( ans1!= ans2) {
                System.out.println("opps!");
                System.out.println(ans1);
                System.out.println(ans2);
            }
        }
        System.out.println("ok");
    }

}
