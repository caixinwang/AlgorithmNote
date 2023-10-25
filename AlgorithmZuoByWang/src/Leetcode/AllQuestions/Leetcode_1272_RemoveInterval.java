package Leetcode.AllQuestions;

import java.util.ArrayList;
import java.util.List;

public class Leetcode_1272_RemoveInterval {
    public List<List<Integer>> removeInterval(int[][] intervals, int[] toBeRemoved) {
        List<List<Integer>> ans=new ArrayList<>();
        for (int[] cur : intervals) {
            if (cur[1]<=toBeRemoved[0]||cur[0]>=toBeRemoved[1]) {
                ans.add(List.of(cur[0],cur[1]));
            }else {//有重叠
                if (cur[0]<toBeRemoved[0]) ans.add(List.of(cur[0],toBeRemoved[0]));
                if (cur[1]>toBeRemoved[1]) ans.add(List.of(toBeRemoved[1],cur[1]));
            }
        }
        return ans;
    }
}
