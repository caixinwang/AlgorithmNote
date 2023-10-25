package Leetcode.AllQuestions;

public class Leetcode_2379_MinimumRecolors {
    public int minimumRecolors(String blocks, int k) {
        int left=0,n=blocks.length(),sum=0,ans=1<<30;
        char[] s=blocks.toCharArray();
        for(int right=left;left+k<=n;){
            while(right<left+k){
                if(s[right++]=='B') sum++;
                if(k-sum<ans) ans=k-sum;
            }
            if(s[left++]=='B') sum--;
        }
        return ans;
    }
}
