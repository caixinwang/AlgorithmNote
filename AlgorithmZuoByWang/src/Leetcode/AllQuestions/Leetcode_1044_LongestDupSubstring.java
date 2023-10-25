package Leetcode.AllQuestions;

import java.util.HashSet;

public class Leetcode_1044_LongestDupSubstring {
    public String longestDupSubstring2(String s) {//使用Hashset<String> 复杂度为O(logn n²)
        var str=s.toCharArray();
        int n=str.length,l=0,r=n,mid;
        String t=null,ans=null;
        while(l<=r){
            mid=l+(r-l>>1);
            if((t=can(s,mid))!=null){
                l=mid+1;
                ans=t;
            }
            else r=mid-1;
        }
        return ans==null?"":ans;
    }

    //是否有最长重复子串长度可以达到target,那么就用一个滑动窗口固定为target长度
    //使用HashSet<String> -> 计算hashcode和String的长度有关，是一个O(N)的复杂度
    //O(N²)
    public String can(String s,int target){
        HashSet<String> set=new HashSet<>();
        String ans=null;
        int n=s.length();
        // for(int start=0;start<target;start++){
        //     for(int l=start,r=start;l+target<=n;){
        //         while(r<l+target){
        //             String t=s.substring(l,l+target);
        //             if(set.contains(t)) return t;
        //             set.add(t);
        //             r+=target;
        //         }
        //         l+=target;
        //     }
        // }
        for(int l=0;l+target<=n;){
            String t=s.substring(l,l+target);
            if(set.contains(t)) return t;
            set.add(t);
            l++;
        }
        return ans;
    }

    //h[i]为长度为i的子串的hashcode，p[i]为P^i
    //下标i~j子串的hashcode为h[j+1]-h[i]*P
    long[] h, p;
    public String longestDupSubstring(String s) {
        int n=s.length(),P=131313;
        h=new long[n+1];
        p=new long[n+1];
        p[0]=1;
        for(int i=0;i<n;i++){
            p[i+1]=p[i]*P;
            h[i+1]=h[i]*P+s.charAt(i);
        }
        int l=0,r=n,mid;
        String ans=null,t=null;
        while(l<=r){
            mid=l+(r-l>>1);
            if((t=check(s,mid))!=null){
                l=mid+1;
                ans=t;
            }
            else r=mid-1;
        }
        return ans==null?"":ans;
    }

    //h[i] 越靠近i，乘上的P阶数就越小。要乘以 p[j - i + 1]的原因在于要补上str[0]乘的阶数
    public String check(String s, int target) {
        HashSet<Long> set=new HashSet<>();
        String ans=null;
        for(int l=0;l+target<=s.length();l++){
            long cur=h[l+target]-h[l]*p[target];//子串下标l~l+target-1
            if(set.contains(cur)) ans=s.substring(l,l+target);
            else set.add(cur);
        }
        return ans;
    }
}
