package ClassicInterviewCoding.InterviewCoding21;

import java.util.HashMap;

public class Code02_LongestConsecutive {

	/**
	 * 使用一个map即可，key为一个数，val为它所在区间的长度。原理像并查集，不区分开头的结尾。区分开头结尾的用两张map
	 * @param arr arr如果排序完之后最长的连续区间的长度
	 * @return -
	 */
	public static int longestConsecutive(int[] arr) {
		if (arr==null||arr.length==0) return 0;
		HashMap<Integer,Integer> map=new HashMap<>();//一个数所在的区间有多少数
		int ans=1;
		for (int num : arr) {
			if (!map.containsKey(num)){//只更新区间的头和尾
				map.put(num,1);
				int preLen= map.getOrDefault(num - 1, 0);
				int posLen= map.getOrDefault(num + 1, 0);
				map.put(num-preLen,1+posLen+preLen);
				map.put(num+posLen,1+posLen+preLen);
				ans = Math.max(ans,1+posLen+preLen);
			}
		}
		return ans;
	}


	public static void main(String[] args) {
		int[] arr = { 100, 4, 200, 1, 3, 2,101,201,205,203,202,204 };
		System.out.println(longestConsecutive(arr));
	}

}
