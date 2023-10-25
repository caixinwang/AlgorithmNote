package Leetcode.AllQuestions;

import java.util.Arrays;

public class Leetcode_1288_RemoveCoveredIntervals {
    public int removeCoveredIntervals(int[][] intervals) {
        Arrays.sort(intervals,(a,b)->a[0]!=b[0]?a[0]-b[0]:b[1]-a[1]);
        int preEtime=Integer.MIN_VALUE,ans=0;
        for (int[] cur : intervals) {
            if (cur[1]>preEtime){//结束时间从大到小是为了开始时间一样的时候，我们选择最厉害的那个留下，剩下的相同开头的都干不过它
                ans++;
                preEtime=cur[1];
            }
        }
        return ans;
    }
}
