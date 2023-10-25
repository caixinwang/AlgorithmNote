package Leetcode.AllQuestions;

import java.util.ArrayList;
import java.util.List;

public class Leetcode_0438_FindAllAnagramsInAString {
    //为什么记账可以呢？因为窗口大小是固定的，如果能成功还款，那么一定是刚刚好的
    public List<Integer> findAnagrams(String s, String p) {//记账
        List<Integer> ans=new ArrayList<>();
        char[] str=s.toCharArray();
        int n=str.length,m=p.length(),all=m;
        int[] cnt=new int[128];
        for(char c:p.toCharArray()) cnt[c]++;
        for(int l=0,r=0;l+m-1<n;){
            while(r<l+m) {
                if (cnt[str[r++]]-- > 0) all--;
            }
            if(all==0) ans.add(l);
            if(++cnt[str[l++]]>0) all++;
        }
        return ans;
    }

    public List<Integer> findAnagrams2(String s, String p) {//不使用记账
        List<Integer> ans=new ArrayList<>();
        char[] str=s.toCharArray();
        int n=str.length,m=p.length();
        int[] cnt=new int[128];
        for(char c:p.toCharArray()) cnt[c]++;
        for(int l=0,r=0;l+m-1<n;){
            while(r<l+m){
                cnt[str[r++]]--;
            }
            boolean check=true;
            for(int c:cnt) {
                if(c!=0) {
                    check=false;
                    break;
                }
            }
            if(check) ans.add(l);
            cnt[str[l++]]++;
        }
        return ans;
    }



}
