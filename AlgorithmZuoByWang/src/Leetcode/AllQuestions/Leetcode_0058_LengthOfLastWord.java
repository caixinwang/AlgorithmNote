package Leetcode.AllQuestions;

public class Leetcode_0058_LengthOfLastWord {
    public int lengthOfLastWord(String s) {
        s=s.trim();
        int ans=0;
        for(int n=s.length()-1;n>=0&&s.charAt(n)!=' ';n--,ans++);
        return ans;
    }
}
