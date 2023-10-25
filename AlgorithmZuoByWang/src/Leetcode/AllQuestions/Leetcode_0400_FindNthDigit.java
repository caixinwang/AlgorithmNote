package Leetcode.AllQuestions;

public class Leetcode_0400_FindNthDigit {
    public int findNthDigit(int n) {
        long base=1,digit=1;
        for(;n-9*base*digit>0;n-=9*base*digit,base*=10,digit++);
        base+=(n-1)/digit;
        long offset=(n-1)%digit;//从题目的从高位数转为从低位数
        return String.valueOf(base).charAt((int)offset)-'0';
    }
}
