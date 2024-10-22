package Leetcode.AllQuestions;

import TestUtils.StringUtil;

import java.util.HashMap;

/**
 * 给你一个字符串 s 、一个字符串 t 。返回 s 中涵盖 t 所有字符的最小子串。
 * 如果 s 中不存在涵盖 t 所有字符的子串，则返回空字符串 "" 。
 */
public class Leetcode_0076_MinimumWindowSubstring {

    public String minWindow(String big, String small) {//滑动窗口，推荐第二种方法，因为用一个变量代替了isok函数
        char[] s = big.toCharArray();
        char[] t = small.toCharArray();
        HashMap<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < t.length; i++) {
            if (!map.containsKey(t[i])) map.put(t[i], 0);
            map.put(t[i], map.get(t[i]) + 1);
        }
        int l = 0, r = 0;//[l,r)
        int start = 0, end = 0;
        int ans = Integer.MAX_VALUE;
        while (r < s.length) {
            while (r < s.length && !isok(map)) {//r移动到第一个成功的位置，[l,r)全部划分进来
                if (map.containsKey(s[r])) map.put(s[r], map.get(s[r]) - 1);
                r++;
            }
            while (l < s.length && isok(map)) {//走到第一个失败的位置退出，能进去都是成功的位置
                if (r - l < ans) {
                    ans = r - l;
                    start = l;
                    end = r;
                }
                if (map.containsKey(s[l])) map.put(s[l], map.get(s[l]) + 1);
                l++;
            }
        }
        return big.substring(start, end);
    }


    public boolean isok(HashMap<Character, Integer> map) {
        for (Character character : map.keySet()) {
            if (map.get(character) > 0) return false;
        }
        return true;
    }

    public String minWindow2(String big, String small) {//使用数组作欠账本，用一个变量all代替isok
        char[] s = big.toCharArray();
        char[] t = small.toCharArray();
        int[] map = new int[128];
        for (char c : t) map[c]++;
        int all = t.length;
        int l = 0, r = 0;
        int min = Integer.MAX_VALUE;
        int start = 0, end = 0;
        while (r < s.length) {
            if (!(all==0)) {//扩，扩到符合条件停。all==0是符合的条件
                map[s[r]]--;
                if (map[s[r]] >= 0) all--;//减完之后大于0才是有效还款
                r++;
            }
            while (all == 0) {//缩,缩到不符合条件
                if (r - l < min) {
                    min = r - l;
                    start = l;
                    end = r;
                }
                map[s[l]]++;
                if (map[s[l]] > 0) all++;
                l++;
            }
        }
        return big.substring(start, end);
    }

    static int MIN=1<<31,MAX=MIN-1;
    public static String minWindow3(String s, String t) {
        char[] str1=s.toCharArray(),str2=t.toCharArray();
        int n=str1.length,m=str2.length,all=m,len=MAX,start=0;
        int[] cnt=new int[128];
        for(char c:str2)cnt[c]++;
        for(int l=0,r=0;l<n;){//固定l，扩到达标就停
            while(r<n&&all>0){//不达标就一直扩，扩到达标就停，此时就是l开头的达标窗口中最短的
                if(cnt[str1[r++]]-->0) all--;
            }
            if(all==0&&r-l<len){//达标，并且比之前的答案好，就更新
                len=r-l;
                start=l;
            }
            if(++cnt[str1[l++]]>0) all++;//出一个
        }
        return len==MAX?"":s.substring(start,start+len);
    }

    public static String minWindow4(String s, String t) {
        char[] str1=s.toCharArray(),str2=t.toCharArray();
        int n=str1.length,m=str2.length,all=m,len=MAX,start=0;
        int[] cnt=new int[128];
        for (char c:str2) cnt[c]++;
        for (int l=0,r=0;r<n;){//固定r，能缩就缩
            if (cnt[str1[r++]]-->0) all--;//本轮固定的r先进来
            while(l<n&&all==0&&cnt[str1[l]]<0) cnt[str1[l++]]++;//如果满足条件all==0，那么就能缩就缩
            if (all==0&&r-l<len){
                len=r-l;
                start=l;
            }
        }
        return len==MAX?"":s.substring(start,start+len);
    }

    static StringUtil su=new StringUtil();

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            String t=su.generateRandom_a_z_String(su.ran(1,50));
            String s=su.generateRandom_a_z_String(su.ran(t.length(),80));
            String ans1=minWindow4(s,t);
            String ans2=minWindow3(s,t);
            if (!ans1.equals(ans2)){
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
    }


}
