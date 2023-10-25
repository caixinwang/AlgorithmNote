package Leetcode.AllQuestions;

public class Leetcode_0461_HammingDistance {
    public int hammingDistance(int x, int y) {
        x^=y;//异或完就代表不一样
        y=0;
        for (;x!=0;y+=x&1,x>>>=1);
        return y;
    }

}
