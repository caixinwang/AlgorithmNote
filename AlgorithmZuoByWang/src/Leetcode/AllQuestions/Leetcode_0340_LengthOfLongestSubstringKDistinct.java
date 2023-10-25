package Leetcode.AllQuestions;

public class Leetcode_0340_LengthOfLongestSubstringKDistinct {
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        char[] str=s.toCharArray();
        int ans=0,n=str.length,diff=0;
        int[] cnt=new int[128];
        for(int l=0,r=0;r<n;){
            while(diff==k&&cnt[str[r]]==0){
                if(--cnt[str[l++]]==0) diff--;
            }
            if(cnt[str[r++]]++==0) diff++;
            if(diff<=k&&r-l>ans) ans=r-l;
        }
        return ans;
    }
}
