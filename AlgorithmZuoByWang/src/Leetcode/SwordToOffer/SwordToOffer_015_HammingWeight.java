package Leetcode.SwordToOffer;

public class SwordToOffer_015_HammingWeight {
    // you need to treat n as an unsigned value
    public int hammingWeight(int n) {
        int cnt=0;
        for(;n!=0;n>>>=1){// >>>是无符号位移
            if((n&1)!=0) cnt++;
        }
        return cnt;
    }
}
