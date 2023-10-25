package Leetcode.AllQuestions;

public class Leetcode_0151_ReverseWords {
    public String reverseWords(String s) {
        String[] arr=s.split(" +");//匹配>=1个的空格
        String t;
        for(int l=0,r=arr.length-1;l<r;t=arr[l],arr[l]=arr[r],arr[r]=t,l++,r--);
        StringBuilder sb=new StringBuilder();
        for(String ss:arr){
            if(sb.length()!=0) sb.append(" ");
            sb.append(ss);
        }
        return sb.toString().trim();
    }
}
