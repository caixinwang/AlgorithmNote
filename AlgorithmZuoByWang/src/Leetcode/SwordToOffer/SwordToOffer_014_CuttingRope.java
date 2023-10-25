package Leetcode.SwordToOffer;

public class SwordToOffer_014_CuttingRope {
    //做法1:动态规划
    //做法2：3是最优选择，数学求导
    //数据量太大，使用数学的方法。

    public int cuttingRope(int n) {
        if(n<=3) return n-1;
        long res = 1;
        while(n > 4){
            res *= 3;
            res = res % 1000000007;
            n -= 3;
        }
        return (int)(res * n % 1000000007);
    }

}
