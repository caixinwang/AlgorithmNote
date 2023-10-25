package Leetcode.AllQuestions;

import java.util.HashMap;

public class Leetcode_0001_TowSum {
    public int[] twoSum(int[] nums, int target) {//两数之和--Hash
        HashMap<Integer,Integer> map=new HashMap<>();//存int和下标
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target-nums[i])) return new int[]{map.get(target-nums[i]),i};
            else map.put(nums[i],i );
        }
        return null;
    }
}
