package Leetcode.AllQuestions;

import java.util.ArrayList;
import java.util.HashMap;

public class Leetcode_0446_ArithmeticSlicesIISubsequence {
	//1.首先定义dp[i]为以i位置结尾的等差数列有几个。对于i位置，我们枚举它前面的每一个位置j,得到等差数列的公差d
	//得到公差之后我们关心位置j有多少个以j结尾的公差为d的等差数列。所以我们需要记录前面每一个位置j，对于每一个
	//公差来说一共有几个等差数列。所以这是一个List<HashMap>的结构
	//2.题目要求等差数列至少有3个数。也是对于i位置来说，它考察的前面的j的记录，只要等差数列长度达到2的长度就行了。
	//  也就是说我们map里面记录的是2的长度，但是结算答案的时机是在i位置的时候，因为i位置加进去了，前面的j就都是
	//	大于等于3的长度了，符合题目的要求。但是需要注意，更新的时候需要把长度2的答案放到map里面
	//3.总结来说就是map里面放的是>=2长度的等差数列，但是结算答案的时候是结算3的长度。

	// 时间复杂度是O(N^2)，最优解的时间复杂度
	public static int numberOfArithmeticSlices(int[] arr) {
		int N = arr.length;
		int ans = 0;
		ArrayList<HashMap<Integer, Integer>> maps = new ArrayList<>();
		for (int i = 0; i < N; i++) {
			maps.add(new HashMap<>());
			//  ....j...i（结尾）
			for (int j = i - 1; j >= 0; j--) {
				long diff = (long) arr[i] - (long) arr[j];
				if (diff <= Integer.MIN_VALUE || diff > Integer.MAX_VALUE) {
					continue;
				}
				int dif = (int) diff;
				int count = maps.get(j).getOrDefault(dif, 0);
				ans += count;
				maps.get(i).put(dif, maps.get(i).getOrDefault(dif, 0) + count + 1);
			}
		}
		return ans;
	}

}
