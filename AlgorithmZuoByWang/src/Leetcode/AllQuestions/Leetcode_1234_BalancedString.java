package Leetcode.AllQuestions;

public class Leetcode_1234_BalancedString {
    //思路：
    //1. 由于子串可以变为任何字符串，所以我们需要将s中多的字符变为少的字符，“多的”就是个数大于n/4的。
    //2. 通过第一步的问题转化之后，我们需要得到一张冗余表，告诉我们哪些字符比n/4多，多了多少？
    //3. 使用滑动窗口再维持一个词频表，需要凑齐所有的冗余字符。
    public int balancedString(String s) {
        int i,l,r,n=s.length(),ans=n;
        char[] str=s.toCharArray();
        int[] cnt=new int[128];//此时为词频表
        for(i=0;i<n;i++)cnt[str[i]]++;//统计词频
        for(i=0;i<f.length;i++) cnt[f[i]]-=n>>2;//减去n/4之后变成了冗余表
        int[] win=new int[128];//窗口维持的词频表，需要涵盖cnt中所有的冗余字符
        for(l=r=0;l<n&&l<=r;){
            while(r<n&&!check(cnt,win)) win[str[r++]]++;
            if(check(cnt,win)) ans=Math.min(ans,r-l);
            win[str[l++]]--;
        }
        return ans;
    }

    int[] f=new int[]{'Q','W','E','R'};//只检查这4个下标位置，而不是全部的128个位置
    public boolean check(int[] cnt,int[] win){
        for(int i=0;i<f.length;i++){
            if(cnt[f[i]]>0&&win[f[i]]<cnt[f[i]]) return false;
        }
        return true;
    }
}
