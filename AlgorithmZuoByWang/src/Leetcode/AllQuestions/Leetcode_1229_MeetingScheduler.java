package Leetcode.AllQuestions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Leetcode_1229_MeetingScheduler {
    public List<Integer> minAvailableDuration(int[][] slots1, int[][] slots2, int duration) {
        Arrays.sort(slots1,(a,b)->a[0]-b[0]);//按照空闲的开始时间从小到大排序，同一个人不可能有开始时间一样的情况
        Arrays.sort(slots2,(a,b)->a[0]-b[0]);
        for (int i=0,j=0;i<slots1.length&&j<slots2.length;){
            int start=Math.max(slots1[i][0],slots2[j][0]);//开始时间取最晚
            int end=Math.min(slots1[i][1],slots2[j][1]);//结束时间取最早
            if (end-start>=duration) return List.of(start,start+duration);
            if (slots1[i][1]<slots2[j][1]) i++;//结束时间早的移动
            else j++;
        }
        return new ArrayList<>();
    }

}
