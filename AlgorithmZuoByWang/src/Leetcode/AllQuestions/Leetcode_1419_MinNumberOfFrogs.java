package Leetcode.AllQuestions;

public class Leetcode_1419_MinNumberOfFrogs {
    public int minNumberOfFrogs(String croakOfFrogs) {
        int[] cnt=new int[128];//当前可以叫出第i个字符的青蛙有几只？
        int all=0;//当前总共有all只青蛙
        int[] nexts=new int[128];//字符到下一个字符下标的映射
        nexts['c']='r';
        nexts['r']='o';
        nexts['o']='a';
        nexts['a']='k';
        nexts['k']='c';
        for(char c:croakOfFrogs.toCharArray()){
            if(cnt[c]==0){
                if(c!='c') return -1;//如果没有青蛙能叫出这声，那么此时只能是'c'要叫
                all++;//增加一只青蛙
                cnt['c']++;//能叫出'c'的青蛙增加一只
            }
            cnt[c]--;
            cnt[nexts[c]]++;
        }
        return all==cnt['c']?all:-1;//all==cnt['c']说明青蛙都叫完了
    }
}
