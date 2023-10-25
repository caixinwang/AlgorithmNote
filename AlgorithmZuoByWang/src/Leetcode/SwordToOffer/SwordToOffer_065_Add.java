package Leetcode.SwordToOffer;

public class SwordToOffer_065_Add {
    public int add(int a, int b) {
        while(b!=0){//b代表进位
            int t=a^b;//不进位相加
            b=(a&b)<<1;//代表进位，(a&b)需要加括号
            a=t;
        }
        return a;
    }
}
