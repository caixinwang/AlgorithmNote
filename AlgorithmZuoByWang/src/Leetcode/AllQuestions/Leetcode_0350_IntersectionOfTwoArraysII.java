package Leetcode.AllQuestions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Leetcode_0350_IntersectionOfTwoArraysII {

    public static int[] intersect(int[] nums1, int[] nums2) {
        HashMap<Integer, Integer> map1 = new HashMap<Integer, Integer>();
        HashMap<Integer, Integer> map2 = new HashMap<Integer, Integer>();
        for (int num : nums1) {
            if (!map1.containsKey(num)) {
                map1.put(num, 1);
            } else {
                map1.put(num, 1 + map1.get(num));
            }
        }
        for (int num : nums2) {
            if (!map2.containsKey(num)) {
                map2.put(num, 1);
            } else {
                map2.put(num, 1 + map2.get(num));
            }
        }
        List<Integer> list = new ArrayList<>();
        for (int num : map1.keySet()) {
            if (map2.containsKey(num)) {
                for (int i = 0; i < Math.min(map1.get(num), map2.get(num)); i++) {
                    list.add(num);
                }
                map2.remove(num);
            }
        }
        int[] ans = new int[list.size()];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = list.get(i);
        }
        return ans;
    }

}
