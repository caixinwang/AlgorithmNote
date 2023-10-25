package Leetcode.SwordToOffer;

public class SwordToOffer_066_ConstructArr {
    //正着一遍，反着一遍
    public int[] constructArr(int[] a) {
        int n=a.length;
        int[] ans=new int[n];
        for(int i=0,m=1;i<n;m*=a[i],i++){
            ans[i]=m;
        }
        for(int i=n-1,m=1;i>=0;m*=a[i],i--){
            ans[i]*=m;
        }
        return ans;
    }
}
