package Leetcode.AllQuestions;

public class Leetcode_2375_ConstructSmallestNumberFromDiString {
    public String smallestNumber(String pattern) {
        char[] ans=new char[9];
        for (int i = 0; i < ans.length; i++) {
            ans[i]=(char)('1'+i);
        }
        char[] p = pattern.toCharArray();
        for (int i = 0; i < p.length; i++) {
            if (p[i]=='I') continue;
            int i0=i;//D的起点
            while(++i<p.length&&p[i]=='D');//i-1为D的终点
            for (int l=i0,r=i,t;l<r;t=ans[l],ans[l]=ans[r],ans[r]=(char)t,l++,r--);
        }
        return String.valueOf(ans).substring(0,pattern.length()+1);
    }
}
