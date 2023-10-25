package Leetcode.AllQuestions;

public class Leetcode_0415_AddStrings {
    public static String addStrings(String num1, String num2) {
        int n=num1.length(),m=num2.length(),sum=0,carry=0;
        char[] str1=num1.toCharArray(),str2=num2.toCharArray();
        StringBuilder sb=new StringBuilder();
        for(int i=n-1,j=m-1;i>=0||j>=0||carry>0;i--,j--){
            sum=(i>=0?str1[i]-'0':0)+(j>=0?str2[j]-'0':0)+carry;
            carry=sum/10;
            sb.append(sum%10);
        }
        return sb.reverse().toString();
    }

    public static void main(String[] args) {
        System.out.println(addStrings("123456","123456"));
    }
}
