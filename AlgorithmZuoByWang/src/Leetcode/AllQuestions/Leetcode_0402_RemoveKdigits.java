package Leetcode.AllQuestions;

public class Leetcode_0402_RemoveKdigits {
    public String removeKdigits(String num, int k) {
        int n=num.length(),len=n-k;//删除k个，等价于保留n-k个
        char[] str=num.toCharArray();
        char[] dq=new char[n];//单调栈
        int h=0,t=-1,i;//单调栈的指针
        for(i=0;i<n&&(t-h+1+n-i)>len;i++){//t-h+1+n-i代表家底，家底够你霍霍才能去dq里面踢人
            while(h<=t&&(t-h+1+n-i)>len&&str[i]<dq[t]) t--;//家底够才能踢
            dq[++t]=str[i];
        }
        StringBuilder sb=new StringBuilder();
        for(int j=h;j<=t;j++) sb.append(dq[j]);
        sb.append(num.substring(i,n));//如果附加的不是空串，说明家底刚好够凑len个
        String ans=sb.toString().substring(0,len).replaceAll("^(0+)","");//只保留len、去掉前导0
        return ans.length()==0?"0":ans;
    }

    public String removeKdigits2(String num, int k) {
        char[] str=num.toCharArray();
        int n=str.length,h=0,t=-1,rm=0;
        char[] dq=new char[n];
        for(char c:str){
            for(;h<=t&&rm<k&&c<dq[t];t--,rm++);
            dq[++t]=c;
        }
        String ans=String.valueOf(dq).substring(h,h+n-k).replaceAll("^0+","");
        return ans.equals("")?"0":ans;
    }
}
