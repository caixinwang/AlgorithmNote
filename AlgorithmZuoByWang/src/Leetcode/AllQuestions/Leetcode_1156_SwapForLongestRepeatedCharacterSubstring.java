package Leetcode.AllQuestions;

public class Leetcode_1156_SwapForLongestRepeatedCharacterSubstring {

    //r走到第一个不一样的字符就停，此时是xxxxxc。让find到r右边继续去找与str[l]相同的字符，还是走到第一个不同的字符就停，xxxxd
    //总体为xxxxxcxxxxd,r在c的位置，find为d的位置。
    //此时总的字符数为(r-l)+1+(find-r-1),不能超过count[str[l]]，两者取min
    //和l位置一样的都可以一次性失败了，不需要去验证了。直接来到第一个位置即可
    public static int maxRepOpt1(String text) {
        int N=text.length(),ans=0;
        char[] str=text.toCharArray();
        int[] count=new int[128];
        for (char c:str) count[c]++;
        int l=0,r=0,find=0;
        while(l<N){
            while(r<N&&str[l]==str[r]) r++;
            find=r;
            while(++find<N&&str[find]==str[l]);
            ans = Math.max(ans,Math.min(count[str[l]], find-l) );
            l=r;
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println(maxRepOpt1("aaabaaa"));//6
    }
}
