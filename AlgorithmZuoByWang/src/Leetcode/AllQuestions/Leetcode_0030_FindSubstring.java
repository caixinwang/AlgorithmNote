package Leetcode.AllQuestions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Leetcode_0030_FindSubstring {
    /**
     * s中找出一个子串，恰好包含words中的所有单词，不能多也不能少，并且单词的长度都是一样的。
     * 通过题意可知，窗口的大小wid是固定的，大小为单词长度wlen乘以单词数量。
     * 关键点1：一旦选定了窗口的起始点，后面l或者r的移动都是一次移动wlen。
     * 关键点2：介于关键点1，我们分别选定0~wlen-1 这个范围作为我们的起始点。
     * 关键点3：使用两个哈希表来简化判断逻辑
     */
    public List<Integer> findSubstring(String s, String[] words) {
        char[] str=s.toCharArray();
        int n=str.length,wlen=words[0].length(),wid=wlen*words.length;
        HashMap<String,Integer> origin=new HashMap<>();
        for(String w:words){
            if(!origin.containsKey(w)) origin.put(w,1);
            else origin.put(w,1+origin.get(w));
        }
        HashMap<String,Integer> map=new HashMap<>();
        List<Integer> ans=new ArrayList<>();
        for(int start=0;start<wlen;start++){//根据单词长度定起始位置
            for(int l=start,r=start;l+wid-1<n;){//窗口
                while(r<=l+wid-1){//将窗口扩到wid长度
                    String t=s.substring(r,r+wlen);
                    r+=wlen;
                    if(!map.containsKey(t)) map.put(t,1);
                    else map.put(t,1+map.get(t));
                }
                boolean check=true;//检查这个窗口是否符合题目要求
                if(map.size()!=origin.size()) check=false;
                for(String o:origin.keySet()){
                    if(!map.containsKey(o)||!map.get(o).equals(origin.get(o))){
                        check=false;
                        break;
                    }
                }
                if(check)ans.add(l);
                String t=s.substring(l,l+wlen);//l位置出窗口
                if(map.get(t)==1) map.remove(t);
                else map.put(t,-1+map.get(t));
                l+=wlen;
            }
            map.clear();
        }
        return ans;
    }

    //需要注意：这题的窗口还套了一层循环，all以及map需要重新给
    public List<Integer> findSubstring2(String s, String[] words) {
        char[] str=s.toCharArray();
        int n=str.length,wlen=words[0].length(),wid=wlen*words.length;
        HashMap<String,Integer> origin=new HashMap<>();
        for(String w:words){
            if(!origin.containsKey(w)) origin.put(w,1);
            else origin.put(w,1+origin.get(w));
        }
        List<Integer> ans=new ArrayList<>();
        for(int start=0;start<wlen;start++){//根据单词长度定起始位置，主要不要写成start<n
            int all=words.length;//注意！all和map需要在循环里面定义
            HashMap<String,Integer> map=new HashMap<>(origin);
            for(int l=start,r=start;l+wid-1<n;){//窗口
                while(r<=l+wid-1){//将窗口扩到wid长度
                    String t=s.substring(r,r+wlen);
                    r+=wlen;
                    if (map.containsKey(t)){//如果map里面没有这个t，那么就直接无视，all不可能减到0
                        if (map.get(t)>0)all--;
                        map.put(t,-1+map.get(t));
                    }
                }
                if(all==0)ans.add(l);
                String t=s.substring(l,l+wlen);//l位置出窗口
                if(map.containsKey(t)) {//map里面有这个t我才管
                    if (map.get(t) >= 0) all++;
                    map.put(t, 1 + map.get(t));
                }
                l+=wlen;
            }
        }
        return ans;
    }

    public List<Integer> findSubstring3(String s, String[] words) {
        HashSet<Integer> ans=new HashSet<>();
        char[] ss=s.toCharArray();
        int n=ss.length,wlen=words[0].length(),win=wlen*words.length,all=words.length;
        HashMap<String,Integer> cnt=new HashMap<>();
        for(String w:words) {
            if(!cnt.containsKey(w)) cnt.put(w,1);
            else cnt.put(w,1+cnt.get(w));
        }
        for(int start=0;start<n;start++){
            for(int l=start,r=start;l+win<=n;){
                while(r<l+win){
                    String t=s.substring(r,r+wlen);
                    if(cnt.containsKey(t)){
                        if(cnt.get(t)>0) all--;
                        cnt.put(t,-1+cnt.get(t));
                    }
                    r+=wlen;
                }
                if(all==0) ans.add(l);
                String t=s.substring(l,l+wlen);
                if(cnt.containsKey(t)){
                    if(cnt.get(t)>=0) all++;
                    cnt.put(t,1+cnt.get(t));
                }
                l+=wlen;
            }
        }
        return new ArrayList<>(ans);
    }
}
