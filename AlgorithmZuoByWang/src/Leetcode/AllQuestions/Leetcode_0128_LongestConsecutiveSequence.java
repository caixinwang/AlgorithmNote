package Leetcode.AllQuestions;

import java.util.HashMap;

public class Leetcode_0128_LongestConsecutiveSequence {

	public static int longestConsecutive(int[] nums) {
		if (nums==null||nums.length==0) return 0;
		HashMap<Integer,Integer> map=new HashMap<>();//一个数所在的区间有多少数
		int ans=1;
		for (int num : nums) {
			if (!map.containsKey(num)){//只更新区间的头和尾
				map.put(num,1);
				int preLen=map.containsKey(num-1)?map.get(num-1):0;
				int posLen=map.containsKey(num+1)?map.get(num+1):0;
				map.put(num-preLen,1+posLen+preLen);
				map.put(num+posLen,1+posLen+preLen);
				ans = Math.max(ans,1+posLen+preLen);
			}
		}
		return ans;
	}

	public static int longestConsecutive2(int[] nums) {
		if (nums==null||nums.length==0) return 0;
		HashMap<Integer,Integer> smap=new HashMap<>();
		HashMap<Integer,Integer> emap=new HashMap<>();
		int ans=1;
		for (int num : nums) {
			if (!smap.containsKey(num)){
				smap.put(num,1);
				emap.put(num,1);
				int preLen=emap.getOrDefault(num-1,0);
				int posLen=smap.getOrDefault(num+1,0);
				ans = Math.max(ans, 1+preLen+posLen);
				smap.put(num-preLen,1+preLen+posLen);
				emap.put(num+posLen,1+preLen+posLen);
			}
		}
		return ans;
	}

}
