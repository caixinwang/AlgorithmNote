package Leetcode.AllQuestions;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Leetcode_0077_Combine {
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> ans=new ArrayList<List<Integer>>();
        f(1,n,k,new LinkedList<>(),ans);
        return ans;
    }
    public void f(int index,int n,int k,LinkedList<Integer> path,List<List<Integer>> ans){
        if(index==n+1){
            if(path.size()==k) ans.add(new LinkedList<>(path));
        }else{
            f(index+1,n,k,path,ans);
            path.addLast(index);
            f(index+1,n,k,path,ans);
            path.pollLast();
        }
    }
}
