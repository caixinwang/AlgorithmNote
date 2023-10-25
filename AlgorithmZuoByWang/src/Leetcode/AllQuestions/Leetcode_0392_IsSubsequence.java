package Leetcode.AllQuestions;

import java.util.Arrays;

public class Leetcode_0392_IsSubsequence {
    public boolean isSubsequence2(String ss, String tt) {//双指针
        char[] s=ss.toCharArray(),t=tt.toCharArray();
        int n=s.length,m=t.length,p1,p2;
        for(p1=p2=0;p1<n&&p2<m;){
            if(s[p1]==t[p2])p1++;
            p2++;
        }
        return p1==n;
    }

    //有大量的s输入，那么就需要快速的找到t[i]下一个匹配字符j的位置,从尾巴开始dp
    //dp[i][j]:从i~len-1，最近的出现j字符的位置
    public boolean isSubsequence(String ss, String tt){
        char[] s=ss.toCharArray(),t=tt.toCharArray();
        int n=s.length,m=t.length,p1,p2;
        if(m==0) return n==0;
        int[][] dp=new int[m][26];
        Arrays.fill(dp[m-1],m);//把最后一行初始化
        dp[m-1][t[m-1]-'a']=m-1;
        for(int i=m-2;i>=0;i--){//从倒数第二行开始
            for(char j='a';j<='z';j++){
                dp[i][j-'a']=dp[i+1][j-'a'];
                if(t[i]==j) dp[i][j-'a']=i;
            }
        }
        for(p1=p2=0;p1<n&&p2<m;){
            if(s[p1]==t[p2]){
                p1++;
                p2++;
            }
            else p2=dp[p2][s[p1]-'a'];//快速定位
        }
        return p1==n;
    }
}
