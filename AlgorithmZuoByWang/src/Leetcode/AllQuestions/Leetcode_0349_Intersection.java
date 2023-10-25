package Leetcode.AllQuestions;

import java.util.Arrays;

public class Leetcode_0349_Intersection {
    public int[] intersection(int[] nums1, int[] nums2) {
        boolean[] set=new boolean[1001];
        int[] ans=new int[1001];
        int size=0;
        for(int num:nums1)set[num]=true;
        for(int num:nums2){
            if(set[num]) {
                set[num]=false;
                ans[size++]=num;
            }
        }
        return Arrays.copyOf(ans,size);
    }
}
