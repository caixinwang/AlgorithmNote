package Leetcode.AllQuestions;

public class Leetcode_1768_MergeAlternately {
    public String mergeAlternately(String word1, String word2) {
        StringBuilder sb=new StringBuilder();
        char[] str1=word1.toCharArray(),str2=word2.toCharArray();
        int n=str1.length,m=str2.length;
        for(int i=0,j=0;i<n||j<m;i++,j++){
            if(i<n)sb.append(str1[i]);
            if(j<m)sb.append(str2[j]);
        }
        return sb.toString();
    }
}
