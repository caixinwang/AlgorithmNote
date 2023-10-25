package Leetcode.AllQuestions;

public class Leetcode_0014_LongestCommonPrefix {
    //看数据量，直接一个N²的方法搞定
    //拿一个字符串出来，去和其他的字符串算公共部分的长度，以最小的公共长度为主即可。
    public static String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) return null;
        char[] str = strs[0].toCharArray();//拿第一个串出来去不断和其他的截断
        int end = str.length;//第一个失败的位置，从这往后截断
        for (int i = 1,fail = 0; i<strs.length&&end>0; i++) {
            char[] s = strs[i].toCharArray();
            for (; fail < Math.min(end, s.length) && s[fail] == str[fail]; fail++) ;//fail出来是第一个匹配失败的位置
            end=fail;//加速过程,不断更新
        }
        return strs[0].substring(0, end);//[0,end)
    }

    public String longestCommonPrefix2(String[] strs) {
        int n=strs.length,min_len=1<<30,res=0;
        for(var s:strs) if(s.length()<min_len) min_len=s.length();
        for(int i=1,match=0;i<=min_len;res=i,i++){
            String m=strs[0].substring(0,i);
            for(var s:strs) if(!m.equals(s.substring(0,i))) return strs[0].substring(0,res);
        }
        return strs[0].substring(0,res);
    }

    public static void main(String[] args) {
        String[] strings=new String[]{"flower","flower","flower","flower"};
        System.out.println(longestCommonPrefix(strings));
    }

}
