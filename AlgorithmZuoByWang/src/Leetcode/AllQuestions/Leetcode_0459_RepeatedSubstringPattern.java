package Leetcode.AllQuestions;

public class Leetcode_0459_RepeatedSubstringPattern {
    public boolean repeatedSubstringPattern(String s) {
        return (s+s).substring(1,s.length()*2-1).indexOf(s)!=-1;
    }
}
