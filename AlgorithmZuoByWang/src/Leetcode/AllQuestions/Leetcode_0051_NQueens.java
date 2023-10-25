package Leetcode.AllQuestions;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Leetcode_0051_NQueens {
    char[] str=null;
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> ans =new ArrayList<>();
        str=new char[n];
        for(int i=0;i<n;i++) str[i]='.';
        f(n,0,new int[n],ans);
        return ans;
    }
    //n皇后问题，现在在讨论index行的皇后应该放在哪列。row[i]的值代表i行皇后放的列号
    public void f(int n,int index,int[] row,List<List<String>> ans){
        if(index==n){
            List<String> list=new ArrayList<>();
            for(int i=0;i<n;i++){
                str[row[i]]='Q';
                list.add(String.valueOf(str));
                str[row[i]]='.';
            }
            ans.add(list);
            return;
        }
        for(int i=0;i<n;i ++){
            if(check(row,index,i)){
                row[index]=i;
                f(n,index+1,row,ans);
            }
        }
    }
    //row[index]=k 能否可行   0~index-1 都是填过的，检查一下
    public boolean check(int[] row,int index,int k){
        for(int i=0;i<index;i++){
            if(row[i]==k||index-i==Math.abs(k-row[i])) return false;
        }
        return true;
    }
}
