package Leetcode.SwordToOffer;

public class SwordToOffer_056_SingleNumberII {
    public int singleNumber(int[] nums) {//kn问题
        int[] b=new int[32];
        for(int num:nums){
            for(int i=0;i<32;i++){
                if((num>>>i&1)!=0) b[i]++;
            }
        }
        int ans=0;
        for(int i=0;i<32;i++) {
            b[i]%=3;
            if(b[i]!=0) ans|=1<<i;
        }
        return ans;
    }
}
