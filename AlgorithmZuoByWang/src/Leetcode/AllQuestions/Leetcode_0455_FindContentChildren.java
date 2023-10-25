package Leetcode.AllQuestions;

import java.util.Arrays;

public class Leetcode_0455_FindContentChildren {
    //法1 二分答案：让最大的饼干k个饼干去满足胃口最小的k个孩子
    public int findContentChildren2(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);
        int n=g.length,m=s.length,l=0,r=n,mid;
        while(l<=r){
            mid=l+(r-l>>1);
            if(can(g,s,mid)) l=mid+1;
            else r=mid-1;
        }
        return r;
    }
    public boolean can(int[] g,int[] s,int target){
        int n=g.length,m=s.length;
        if(m<target) return false;//饼干不够
        for(int i=0;i<target;i++){
            if(g[i]>s[m-target+i]) return false;//从最大的k个饼干中的最小的饼干开始
        }
        return true;
    }

    //法2：双指针
    public int findContentChildren(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);
        int n=g.length,m=s.length,ans=0;
        for(int i=0,j=0;i<n&&j<m;i++){
            while(j<m&&s[j]<g[i]) j++;//找到第一个可以满足s[i]的饼干
            if(j++<m) ans++;//没有越界说明找到了
        }
        return ans;
    }
}
