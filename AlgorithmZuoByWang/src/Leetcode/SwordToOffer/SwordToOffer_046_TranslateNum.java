package Leetcode.SwordToOffer;

public class SwordToOffer_046_TranslateNum {
    public int translateNum(int num) {
        String s=String.valueOf(num);
        char[] str=s.toCharArray();
        int n=str.length,f2=1,f1=num%100<=25&&num%100>=10?2:1;//在i~n-1的后缀上的答案,i+1,i+2
        if(num<100) return f1;
        for(int i=n-3;i>=0;i--){
            int v=(str[i]-'0')*10+str[i+1]-'0';
            int new_f1=v>=10&&v<=25?f1+f2:f1;
            f2=f1;
            f1=new_f1;
        }
        return f1;
    }

}
