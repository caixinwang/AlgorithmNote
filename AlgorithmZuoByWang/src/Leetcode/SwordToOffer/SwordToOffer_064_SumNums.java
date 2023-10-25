package Leetcode.SwordToOffer;

public class SwordToOffer_064_SumNums {
    public int sumNums(int n) {
        boolean f=n>0&&(n+=sumNums(n-1))>0;
        return n;
    }
}
