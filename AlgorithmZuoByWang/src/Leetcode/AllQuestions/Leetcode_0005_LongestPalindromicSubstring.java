package Leetcode.AllQuestions;

public class Leetcode_0005_LongestPalindromicSubstring {//最长回文子串问题
    //这题其实就是ManACher算法。
    public static String longestPalindrome(String s) {
        if (s==null||s.length()==0) return s;
        char[] str = manacherStr(s).toCharArray();
        int[] parr=new int[str.length];
        int c=-1;
        int r=-1;
        int maxIndex=0;//从哪个位置扩出来的最长
        int max=0;//在这个位置的回文半径是多少
        for (int i = 0; i < str.length; i++) {
            int p=i<r?Math.min(parr[2*c-i],r-i):0;//当前位置阔出去的回文半径
            while(i+p+1<str.length&&i-p-1>=0&&str[i+p+1]==str[i-p-1]) p++;//最后一个合格的位置
            parr[i]=p;
            if (i+p>r){
                r=i+p;
                c=i;
            }
            if (p>max){
                max=p;
                maxIndex=i;
            }
        }
        String res="";
        for (int i=maxIndex-max;i<=maxIndex+max;i++){
            if ((i&1)==1) res+=str[i];//奇数位置的才是源字符串中的字符
        }
        return res;
    }

    public static String longestPalindrome2(String s) {
        String ms=manacherStr(s);
        char[] str=ms.toCharArray();
        int n=str.length,r=0,c=0,p=0,len=0,cc=0;
        int[] pArr=new int[n];
        for(int i=0;i<n;i++){
            p=i<r?Math.min(r-i,pArr[2*c-i]):0;//确定以i为中心的起始回文半径,r大于i才让你帮助
            while(i+p+1<n&&i-p-1>=0&&str[i+p+1]==str[i-p-1]) p++;//找最后一个失败的地方
            pArr[i]=p;
            if(i+p>r){
                r=p;
                c=i;
            }
            if(p>len){
                len=p;//manacher串的最长回文子串的回文半径
                cc=i;//manacher串的最长回文子串的拓展中心点
            }
        }
        return ms.substring(cc-len,cc+len+1).replaceAll("#","");
    }


    public static String manacherStr(String s){
        char[] str=s.toCharArray(),ans=new char[str.length<<1|1];
        for(int i=0,j=0;i<ans.length;i++) ans[i]=(i&1)==0?'#':str[j++];
        return String.valueOf(ans);
    }

    public static void main(String[] args) {
        System.out.println(longestPalindrome("aabaacctgh"));
    }
}
