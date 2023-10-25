package Leetcode.AllQuestions;

public class Leetcode_0383_CanConstruct {
    public boolean canConstruct(String ransomNote, String magazine) {
        int[] cnt=new int[128];
        for(char c:magazine.toCharArray()) cnt[c]++;
        for(char c:ransomNote.toCharArray()){
            if(--cnt[c]<0) return false;
        }
        return true;
    }
}
