package Leetcode.AllQuestions;

public class Leetcode_0345_ReverseVowels {
    public String reverseVowels(String s) {
        int n=s.length(),l=0,r=n-1;
        char[] str=s.toCharArray();
        for(;l<=r;){
            while(l<n&&!isaeiou(str[l])) l++;
            while(r>=0&&!isaeiou(str[r])) r--;
            if(l<=r) swap(str,l++,r--);
        }
        return String.valueOf(str);
    }

    public boolean isaeiou(char c){
        c=Character.toLowerCase(c);
        return c=='a'||c=='e'||c=='i'||c=='o'||c=='u';
    }

    public void swap(char[] arr,int a,int b){
        char t=arr[a];
        arr[a]=arr[b];
        arr[b]=t;
    }
}
