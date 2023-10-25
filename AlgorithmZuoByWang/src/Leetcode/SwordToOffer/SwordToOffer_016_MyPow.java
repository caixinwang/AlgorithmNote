package Leetcode.SwordToOffer;

public class SwordToOffer_016_MyPow {
    int MIN=1<<31,MAX=MIN-1;
    public double myPow(double x, int n) {
        double ans=1,base=x;
        int pow=n==MIN?MAX:abs(n);
        for(;pow!=0;pow>>>=1,base*=base){
            if((pow&1)!=0) ans*=base;
        }
        if(n==MIN)ans*=x;
        return n<0?1d/ans:ans;
    }
    public int abs(int a){return a>0?a:-a;}
}
