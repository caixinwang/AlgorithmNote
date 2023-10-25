package Leetcode.AllQuestions;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Leetcode_0040_CombinationSum2 {
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        var cnt=new int[51];
        for(var c:candidates) cnt[c]++;
        List<List<Integer>> ans=new ArrayList<>();
        f(cnt,target,1,0,new LinkedList<>(),ans);
        return ans;
    }

    public void f(int[] cnt, int target,int num,int sum,LinkedList<Integer> path,List<List<Integer>> ans){
        if(num==51){
            if(sum==target)ans.add(new LinkedList<>(path));
            return;
        }
        for(int i=0;i<=cnt[num]&&i*num+sum<=target;i++){//枚举num用几次
            for(int j=0;j<i;j++) path.addLast(num);
            f(cnt,target,num+1,sum+i*num,path,ans);
            for(int j=0;j<i;j++) path.pollLast();
        }
    }
}
