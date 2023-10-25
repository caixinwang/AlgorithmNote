package Leetcode.AllQuestions;

import java.util.Arrays;

public class Leetcode_0006_ZigzagConversion {
    public String convert(String s, int numRows) {
        if(numRows==1) return s;
        char[][] ans=new char[numRows][s.length()];
        for(char[] cs:ans) Arrays.fill(cs,' ');
        char[] str=s.toCharArray();
        int N=str.length;
        int i=0,start=0;
        while(i<N){
            for(int r=0;i<N&&r<numRows-1;r++) ans[r][start]=str[i++];
            for(int r=numRows-1,c=start;i<N&&r>0;r--,c++) ans[r][c]=str[i++];
            start+=numRows-1;//(0,start)开始
        }
        StringBuilder sb=new StringBuilder();
        for(i=0;i<numRows;i++){
            for(int j=0;j<N;j++){
                if(ans[i][j]!=' ') sb.append(ans[i][j]);
            }
        }
        return sb.toString();
    }

    public String convert2(String s, int numRows) {//优化空间，时间复杂度变为O(N)
        if(numRows==1) return s;
        StringBuilder[] ans=new StringBuilder[numRows];
        for (int i = 0; i < ans.length; i++) {
            ans[i]=new StringBuilder();
        }
        char[] str=s.toCharArray();
        int N=str.length;
        int i=0,start=0;
        while(i<N){
            for(int r=0;i<N&&r<numRows-1;r++) ans[r].append(str[i++]);
            for(int r=numRows-1,c=start;i<N&&r>0;r--,c++) ans[r].append(str[i++]);
            start+=numRows-1;//(0,start)开始
        }
        StringBuilder sb=new StringBuilder();
        for(i=0;i<numRows;i++) sb.append(ans[i].toString());
        return sb.toString();
    }

    //观察规律，如何直接将ans[i]对应到str[i']呢？我们发现周期T=2*(numRows-1)
    //因为s的顺序是按照周期来填写的，所以我们可以指定位置，并且通过周期算出来下标
    //形状是一个V字
    public String convert3(String s, int numRows) {//进一步优化空间
        if(numRows==1) return s;
        int T=numRows-1<<1,n=s.length();
        StringBuilder sb=new StringBuilder();
        char[] str = s.toCharArray();
        for (int i=0;i<n;i+=T) sb.append(str[i]);//除了首尾，中间的位置都是一对一对的打印
        for (int start = 1; start < T>>1; start++) {//一行一行填写
            for (int i = start,j=T-start; i < n; i+=T,j+=T) {//一对一对的打印
                sb.append(str[i]);
                if (j<n)sb.append(str[j]);//j可能没有
            }
        }
        for (int i = T>>1 ; i < n; i+=T) sb.append(str[i]);//首尾分开打印
        return sb.toString();
    }


}
