package Leetcode.AllQuestions;

public class Leetcode_0541_ReverseStr {
    public String reverseStr(String str, int k) {
        var s=str.toCharArray();
        int n=s.length;
        for(int i=0;i<n;i+=k<<1){
            if(n-i>=k){//够k个
                for(int l=i,r=i+k-1;l<r;swap(s,l++,r--));
            }else for(int l=i,r=n-1;l<r;swap(s,l++,r--));
        }
        return String.valueOf(s);
    }
    public void swap(char[] s,int l,int r){
        var c=s[l];
        s[l]=s[r];
        s[r]=c;
    }
}
