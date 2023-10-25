package Leetcode.AllQuestions;

public class Leetcode_0647_PalindromicSubstrings {

    /**
     * 在求Parr的过程中，其实就在求所有回文子串的数量了。Parr[i]的值就代表了实际中s[l...r]的长度
     * aba长度为3，可以搞出两个子回文串，2=(3+1)/2
     * baab长度为4，搞出来的也是两个子回文串，2=(4+1)/2
     */
    public static int countSubstrings(String s) {
        char[] str=manacherStr(s);
        int[] pArr=new int[str.length];
        int r=-1;
        int c=-1;
        int ans=0;
        for (int i = 0; i < str.length; i++) {
            int p=r<i?0:Math.min(r-i,pArr[2*c-i]);//r的范围没有把你包括，或者你没有对称点，都是0.最多帮你到r-i这个长度
            while(i+p+1<str.length&&i-p-1>=0&&str[i+p+1]==str[i-p-1]) p++;//出来是最后一个达标的位置
            if (i+p>r){
                r=i+p;
                c=i;
            }
            ans+=(p+1)>>1;
            pArr[i]=p;
        }
        return ans;
    }

    public static char[] manacherStr(String s){
        char[] str = s.toCharArray();
        char[] ans=new char[str.length<<1|1];
        for (int i = 0; i < ans.length; i++) {
            ans[i]=(i&1)==0?'#':str[i>>1];
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println(countSubstrings("abc"));
        System.out.println(countSubstrings("hello"));
    }
}
