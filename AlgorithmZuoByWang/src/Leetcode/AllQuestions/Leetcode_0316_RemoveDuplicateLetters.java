package Leetcode.AllQuestions;

public class Leetcode_0316_RemoveDuplicateLetters {
    //需要维持一个头部比较小的单调队列。需要注意几点：
    //1.当队尾元素没有多余的时候，不能弹出队尾，当前元素直接入队即可
    //2.由于不能重复，如果当前元素之前出现过，那么就直接跳过。
    public String removeDuplicateLetters(String s) {
        int n=s.length();
        char[] str=s.toCharArray();
        int[] cnt=new int[128];//统计词频
        for(char c:str) cnt[c]++;
        boolean[] exist=new boolean[128];//标记字符有没有在dq中
        char[] dq=new char[n];
        int h=0,t=-1;//dq的头尾指针
        for(char c:str){
            cnt[c]--;
            if(exist[c]) continue;//之前出现过直接跳过
            while(t>=h&&cnt[dq[t]]>0&&c<dq[t]) {//队尾元素还有剩余才能弹出
                exist[dq[t--]]=false;
            }
            dq[++t]=c;
            exist[c]=true;
        }
        StringBuilder sb=new StringBuilder();
        for(int i=h;i<=t;i++) sb.append(dq[i]);
        return sb.toString();
    }
}
