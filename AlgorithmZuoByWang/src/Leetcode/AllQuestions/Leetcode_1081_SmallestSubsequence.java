package Leetcode.AllQuestions;

public class Leetcode_1081_SmallestSubsequence {
    public String smallestSubsequence(String s) {
        int n=s.length();
        char[] str=s.toCharArray();
        int[] cnt=new int[128];
        for(char c:str) cnt[c]++;
        boolean[] exist=new boolean[128];
        char[] dq=new char[n];
        int h=0,t=-1;
        for(char c:str){
            cnt[c]--;
            if(exist[c]) continue;
            while(t>=h&&cnt[dq[t]]>0&&c<dq[t]) {
                exist[dq[t--]]=false;
            }
            dq[++t]=c;
            exist[c]=true;
        }
        StringBuilder sb=new StringBuilder();
        for(int i=h;i<=t;i++){
            sb.append(dq[i]);
        }
        return sb.toString();
    }
}
