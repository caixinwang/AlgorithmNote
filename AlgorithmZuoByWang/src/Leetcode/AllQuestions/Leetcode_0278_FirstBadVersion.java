package Leetcode.AllQuestions;

public class Leetcode_0278_FirstBadVersion {

    public boolean isBadVersion(int a){return false;}
    public int firstBadVersion(int n) {
        int l=1,r=n;
        while(l<=r){
            int mid=l+(r-l>>1);
            if(isBadVersion(mid)) r=mid-1;
            else l=mid+1;
        }
        return l;
    }
}
