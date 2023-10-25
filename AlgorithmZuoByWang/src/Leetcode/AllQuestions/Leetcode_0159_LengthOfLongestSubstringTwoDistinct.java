package Leetcode.AllQuestions;

public class Leetcode_0159_LengthOfLongestSubstringTwoDistinct {
    public int lengthOfLongestSubstringTwoDistinct(String s) {
        char[] str=s.toCharArray();
        int ans=0,n=str.length,diff=0;
        int[] cnt=new int[128];
        for(int l=0,r=0;r<n;){//固定r，不满足之前就缩，和不满足了之后才缩都可以
            while(diff==2&&cnt[str[r]]==0){//这里是不满足之前就缩
                if(--cnt[str[l++]]==0) diff--;
            }
            if(cnt[str[r++]]++==0) diff++;
            if(r-l>ans) ans=r-l;
        }
        return ans;
    }

    public int lengthOfLongestSubstringTwoDistinct2(String s) {
        char[] str=s.toCharArray();
        int ans=0,n=str.length,diff=0;
        int[] cnt=new int[128];
        for(int l=0,r=0;r<n;){//固定r，不满足之前就缩，和不满足了之后才缩都可以
            if(cnt[str[r++]]++==0) diff++;
            while(diff>2){//这里是不满足了才缩
                if(--cnt[str[l++]]==0) diff--;
            }
            if(r-l>ans) ans=r-l;
        }
        return ans;
    }

    public int lengthOfLongestSubstringTwoDistinct3(String s) {
        char[] str=s.toCharArray();
        int ans=0,n=str.length,diff=0;
        int[] cnt=new int[128];
        for(int l=0,r=0;l<n;){//固定l,能阔就阔
            while(r<n&&!(diff==2&&cnt[str[r]]==0)) if (cnt[str[r++]]++==0) diff++;
            if (r-l>ans) ans=r-l;
            if (cnt[str[l++]]--==1) diff--;
        }
        return ans;
    }


}
