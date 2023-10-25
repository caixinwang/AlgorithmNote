package Leetcode.AllQuestions;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Leetcode_0039_CombinationSum {

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> ans=new LinkedList<>();
        LinkedList<Integer> path=new LinkedList<>();
        f(candidates,0,target,path,ans);
        return ans;
    }

    /**
     *
     * @param candidates -
     * @param index index~N-1自由选择，凑出来
     * @param path 之前决定的路径
     * @param ans -
     */
    private void f(int[] candidates, int index, int rest,LinkedList<Integer> path, List<List<Integer>> ans) {
        if (rest==0){
            ans.add(new LinkedList<>(path));
            return;
        }
        if (index==candidates.length) return;
        for (int times=0;rest-times*candidates[index]>=0;times++){
            for (int i=0;i<times;i++) path.addLast(candidates[index]);
            f(candidates,index+1,rest-times*candidates[index],path,ans);
            for (int i=0;i<times;i++) path.pollLast();
        }
    }

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        return g(candidates,candidates.length-1,target);
    }

    /**
     *
     * @param candidates -
     * @param index 0~index自由选择
     * @param rest -
     * @return -
     */
    private List<List<Integer>> g(int[] candidates, int index, int rest) {
        List<List<Integer>> ans=new ArrayList<>();
        if (rest==0){
            ans.add(new ArrayList<>());
            return ans;//[[]]
        }
        if (index==-1) return ans;//[]
        for (int times=0;rest-times*candidates[index]>=0;times++){
            List<List<Integer>> lists=g(candidates,index-1,rest-times*candidates[index]);
            if (lists.size()==0) continue;
            for (List<Integer> list : lists) {
                for (int i=0;i<times;i++) list.add(candidates[index]);
                ans.add(list);
            }
        }
        return ans;
    }


}
