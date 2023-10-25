package Leetcode.AllQuestions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Leetcode_0539_FindMinDifference {
    public static int findMinDifference(List<String> timePoints) {
        if (timePoints.size()>60*24) return 0;
        int[] arr=new int[timePoints.size()];
        int i=0;
        for (String s:timePoints){
            String[] split = s.split(":");
            arr[i++]=Integer.parseInt(split[0])*60+Integer.parseInt(split[1]);
        }
        int[] bucket=new int[60*24];
        Arrays.fill(bucket,-1);
        for (int j = 0; j < arr.length; j++) {
            if (bucket[arr[j]]!=-1) return 0;
            bucket[arr[j]]=arr[j];
        }
        int ans=Integer.MAX_VALUE,pre=-1,first=-1;
        for (int j = 0; j < bucket.length; j++) {
            if (bucket[j]!=-1) {
                if (first==-1) first=j;
                if (pre!=-1) ans = Math.min(ans, j - pre);
                pre=j;
            }
        }
        ans = Math.min(first-pre+60*24,ans);
        return ans;
    }

    public static void main(String[] args) {
        List<String> timePoints=new ArrayList<>();
        timePoints.addAll(List.of("23:59","00:00"));
        System.out.println(findMinDifference(timePoints));
    }
}
