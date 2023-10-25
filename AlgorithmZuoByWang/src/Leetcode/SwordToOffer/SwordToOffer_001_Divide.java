package Leetcode.SwordToOffer;

public class SwordToOffer_001_Divide {
    final int MIN=1<<31,MAX=MIN-1;
    public int divide(int a, int b) {
        if(b==MIN) return a==MIN?1:0;
        if(a==MIN){
            if(b==-1) return MAX;
            int res=div(a+1,b);
            return res+div(a-res*b,b);
        }
        return div(a,b);
    }

    public int div(int a,int b){//不要给我传MIN
        boolean is_neg=(a>0)^(b>0);
        int ans=0;
        a=abs(a);b=abs(b);
        for(int i=31;i>=0;i--){
            if(b<=a>>>i){
                ans|=1<<i;
                a-=b<<i;
            }
        }
        return is_neg?-ans:ans;
    }

    public int multi(int a,int b){
        int ans=0;
        for(a=abs(a),b=abs(b);b!=0;b>>>=1,a<<=1){
            if((b&1)==1)ans+=a;
        }
        return ans;
    }

    public int abs(int a){return a>0?a:-a;}
}
