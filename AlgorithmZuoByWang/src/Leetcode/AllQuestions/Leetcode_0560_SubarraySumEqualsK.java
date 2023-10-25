package Leetcode.AllQuestions;

import java.util.HashMap;

public class Leetcode_0560_SubarraySumEqualsK {

	public int subarraySum(int[] nums, int k) {
		HashMap<Integer,Integer> map=new HashMap<>();//题目要个数，<累加和，个数>
		map.put(0,1);//还没开始就有自己这一个累加和
		int sum=0;
		int ans=0;
		for (int num:nums){
			sum+=num;
			ans+=map.getOrDefault(sum-k,0);
			if (!map.containsKey(sum)){
				map.put(sum,1);
			}else {
				map.put(sum,1+map.get(sum));
			}
		}
		return ans;
	}
}
