package Leetcode.SwordToOffer;

public class SwordToOffer_050_FirstUniqChar {
    public char firstUniqChar(String s) {
        int[] cnt=new int[128];
        char[] str=s.toCharArray();
        for(char c:str)cnt[c]++;
        for(char c:str)if(cnt[c]==1) return c;
        return ' ';
    }
}
