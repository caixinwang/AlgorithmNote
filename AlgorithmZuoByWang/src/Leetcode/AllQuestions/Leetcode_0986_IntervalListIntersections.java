package Leetcode.AllQuestions;

import java.util.ArrayList;
import java.util.List;

public class Leetcode_0986_IntervalListIntersections {
    public int[][] intervalIntersection(int[][] firstList, int[][] secondList) {//题目确保有序
        List<int[]> ans=new ArrayList<>();
        for (int i=0,j=0;i< firstList.length&&j<secondList.length;){
            int start=Math.max(firstList[i][0],secondList[j][0]);
            int end=Math.min(firstList[i][1],secondList[j][1]);
            if (end-start>=0) ans.add(new int[]{start,end});//根据的题目的意思，[1,1]也是交集
            if (firstList[i][1]<secondList[j][1]) i++;
            else j++;
        }
        return ans.toArray(new int[ans.size()][]);
    }
}
