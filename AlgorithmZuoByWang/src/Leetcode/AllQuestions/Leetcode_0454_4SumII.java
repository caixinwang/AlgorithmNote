package Leetcode.AllQuestions;

import java.util.HashMap;

public class Leetcode_0454_4SumII {
	/**
	 * 分治！A和B所有累加和的结果和出现的此时都记到一个map里面。再去调用C和D的所有组合，到A和B里面找相反数即可
	 */
	public static int fourSumCount(int[] A, int[] B, int[] C, int[] D) {
		HashMap<Integer,Integer> map=new HashMap<>();
		for (int i = 0; i < A.length; i++) {
			for (int j = 0; j < B.length; j++) {
				int t=A[i]+B[j];
				if (!map.containsKey(t))map.put(t,1);
				else map.put(t,1+map.get(t));
			}
		}
		int ans=0;
		for (int i = 0; i < C.length; i++) {
			for (int j = 0; j < D.length; j++) {
				int t=-(C[i]+D[j]);
				if (map.containsKey(t)) ans+=map.get(t);
			}
		}
		return ans;
	}

}
