package Leetcode.AllQuestions;

public class Leetcode_0043_MultiplyStrings {
    public static String multiply(String num1, String num2) {
        int n=num1.length(),m=num2.length();
        char[] str1=num1.toCharArray(),str2=num2.toCharArray();
        String ans="0";
        for(int i=n-1;i>=0;i--){
            StringBuilder sb=new StringBuilder();
            sb.append("0".repeat(n-1-i));//补0
            int mul=0,carry=0;
            for(int j=m-1;j>=0||carry>0;j--){
                mul=(str1[i]-'0')*(j>=0?str2[j]-'0':0)+carry;
                carry=mul/10;
                sb.append(mul%10);
            }
            ans=add(ans,sb.reverse().toString());
        }
        return ans;
    }

    public static String add(String num1, String num2) {
        int n=num1.length(),m=num2.length(),sum=0,carry=0;
        char[] str1=num1.toCharArray(),str2=num2.toCharArray();
        StringBuilder sb=new StringBuilder();
        for(int i=n-1,j=m-1;i>=0||j>=0||carry>0;i--,j--){
            sum=(i>=0?str1[i]-'0':0)+(j>=0?str2[j]-'0':0)+carry;
            carry=sum/10;
            sb.append(sum%10);
        }
        sb.reverse();
        while(sb.length()>1&&sb.charAt(0)=='0') sb.deleteCharAt(0);//去前导0
        return sb.toString();
    }

    public static void main(String[] args) {
        String ans="1";
        for (int i=1;i<=100;i++) ans=multiply(ans,String.valueOf(i));
        System.out.println(ans);
    }
}
