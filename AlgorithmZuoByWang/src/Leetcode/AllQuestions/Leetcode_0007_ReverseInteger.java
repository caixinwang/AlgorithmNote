package Leetcode.AllQuestions;

public class Leetcode_0007_ReverseInteger {

    int MIN=1<<31,MAX=MIN-1;
    public int reverse(int x) {//不转long的做法
        int minq=MIN/10,minr=MIN%10,ans=0,d=0;
        boolean isNeg=x<0;//结果是整数还是负数
        x=x>0?-x:x;//转负数处理，负数表示范围大
        for(d=x%10;x!=0;x/=10,d=x%10){//d表示为x的个位数
            if(ans<minq||ans==minq&&(d<minr)) return 0;
            ans=ans*10+d;
        }
        return isNeg?ans:-ans;
    }

    public static void main(String[] args) {
        System.out.println("Integer.MIN_VALUE:" + Integer.MIN_VALUE);
        System.out.println("Integer.MAX_VALUE:" + Integer.MAX_VALUE);
        System.out.println("-99%10=" + (-99 % 10));
        System.out.println("123123123".indexOf("123", 0));
    }

}
