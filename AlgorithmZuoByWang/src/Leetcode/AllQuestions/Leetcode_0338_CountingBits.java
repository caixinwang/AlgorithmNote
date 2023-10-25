package Leetcode.AllQuestions;

public class Leetcode_0338_CountingBits {
    public int[] countBits(int n) {
        int[] ans=new int[n+1];
        for (int i = 0; i < ans.length; i++) {
            int cur=i;
            while(cur!=0){
                if ((cur&1)==1) ans[i]++;
                cur>>>=1;
            }
        }
        return ans;
    }

    //1101  -->  dp[0110]+1  因为1100之前算过了
    public int[] countBits2(int n) {//对上面进行优化，把自己的最高位算完之后，右移是之前算过的，不用重复计算
        int[] ans=new int[n+1];
        for (int i = 1; i < ans.length; i++) {
            ans[i]=(i&1)+ans[i>>1];
        }
        return ans;
    }
}
