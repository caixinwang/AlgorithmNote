package Leetcode.SwordToOffer;

public class SwordToOffer_017_PrintNumbers {
    public int[] printNumbers(int n) {
        int max=Integer.parseInt("9".repeat(n));
        int[] ans=new int[max];
        for(int index=0,i=1;i<=max;i++) ans[index++]=i;
        return ans;
    }
}
