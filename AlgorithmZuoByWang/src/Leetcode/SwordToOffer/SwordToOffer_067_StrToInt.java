package Leetcode.SwordToOffer;

public class SwordToOffer_067_StrToInt {
    // 步骤：
    // 1.先找到s串中的第一个数字fi，如果没有数字直接返回0
    // 2.检查0~fi-1的前缀的合法性，在这个前缀上只能出现空格以及加减符号，并且如果有加减符号，必须出现在fi-1这个位置上
    // 3.负数的表示域比正数的多一个，所以使用负数来接住答案。
    int MIN=1<<31,MAX=MIN-1;
    public int strToInt(String s) {
        char[] str=s.toCharArray();
        int n=str.length,fi=0;
        for(;fi<n&&!Character.isDigit(str[fi]);fi++);//找到第一个数字
        if(fi==n) return 0;//没有数字
        for(int i=0;i<fi;i++) if(str[i]!=' '&&!(i==fi-1&&(str[i]=='+'||str[i]=='-')))return 0;
        boolean isNeg=fi-1>=0&&str[fi-1]=='-';
        int ans=0,minq=MIN/10,minr=MIN%10;//minq minr 都是小于0的
        for(;fi<n&&Character.isDigit(str[fi]);fi++){
            if(ans<minq||(ans==minq&&('0'-str[fi])<minr)) return isNeg?MIN:MAX;
            ans=ans*10+'0'-str[fi];
        }
        return isNeg?ans:ans==MIN?MAX:-ans;
    }
}
