package Leetcode.AllQuestions;

public class Leetcode_0052_TotalNQueens {
    public int totalNQueens(int n) {
        return f(n,0,new int[n]);
    }
    //n皇后问题，现在在讨论index行的皇后应该放在哪列。row[i]的值代表i行皇后放的列号
    public int f(int n,int index,int[] row){
        if(index==n) return 1;
        int ans=0;
        for(int i=0;i<n;i ++){
            if(check(row,index,i)){
                row[index]=i;
                ans+=f(n,index+1,row);
            }
        }
        return ans;
    }
    //row[index]=k 能否可行   0~index-1 都是填过的，检查一下
    public boolean check(int[] row,int index,int k){
        for(int i=0;i<index;i++){
            if(row[i]==k||index-i==Math.abs(k-row[i])) return false;
        }
        return true;
    }
}
