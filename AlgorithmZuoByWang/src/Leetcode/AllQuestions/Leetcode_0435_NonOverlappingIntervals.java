package Leetcode.AllQuestions;

import java.util.Arrays;

public class Leetcode_0435_NonOverlappingIntervals {
    //逆向思维，删除其实就是保留，题目要求尽量少删，那么我们就尽量多保留，让保留的相互之间不会重叠。
    //贪心：优先保留早结束的，那么就有更多的空间留给后面的区间。
    public int eraseOverlapIntervals(int[][] intervals) {
        Arrays.sort(intervals,(a,b)->a[1]-b[1]);
        int ans=0,pretime=Integer.MIN_VALUE;
        for (int[] cur : intervals) {
            if (cur[0]>=pretime) pretime=cur[1];
            else ans++;//说明cur不能留下，那么就作为要remove的区间
        }
        return ans;
    }
}
