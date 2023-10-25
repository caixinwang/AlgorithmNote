package Leetcode.AllQuestions;

public class Leetcode_0067_AddBinary {
    public String addBinary(String a, String b) {
        char[] aa=a.toCharArray(),bb=b.toCharArray();
        int sum=0,carry=0,n=aa.length,m=bb.length;
        StringBuilder sb=new StringBuilder();
        for(int i=n-1,j=m-1;i>=0||j>=0||carry!=0;i--,j--){
            sum=(i>=0?aa[i]-'0':0)+(j>=0?bb[j]-'0':0)+carry;
            carry=sum/2;
            sb.append(sum%2);
        }
        return sb.reverse().toString();//记得翻转
    }
}
