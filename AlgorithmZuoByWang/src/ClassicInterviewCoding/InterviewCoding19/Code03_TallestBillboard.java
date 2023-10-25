package ClassicInterviewCoding.InterviewCoding19;

import java.util.HashMap;

public class Code03_TallestBillboard {

	public static int tallestBillboard(int[] rods) {
		HashMap<Integer,Integer> cur=new HashMap<>();//<diff,lessNum>
		cur.put(0,0);//空集和空集
		for (int rod : rods) {
			cur=f(cur,rod);
		}
		return cur.get(0);
	}

	/**
	 *
	 * @param cur 原本的差值以及集合
	 * @param rod 新增加一个rod进去，看看能不能搞出新的
	 * @return 返回新的
	 */
	private static HashMap<Integer, Integer> f(HashMap<Integer, Integer> cur, int rod) {
		HashMap<Integer, Integer> res=new HashMap<>(cur);
		for (Integer key : cur.keySet()) {
			int less=cur.get(key);//(less,more)
			int more=less+key;

			int key1=(more+rod)-less;//进左边
			int val1=less;

			int key2=Math.abs(more-(less+rod));//进右边
			int val2=Math.min(more,less+rod);

			if (!res.containsKey(key1)){
				res.put(key1,val1);
			}else {
				res.put(key1,Math.max(res.get(key1),val1));
			}
			if (!res.containsKey(key2)){
				res.put(key2,val2);
			}else {
				res.put(key2,Math.max(res.get(key2),val2));
			}
		}
		return res;
	}

}
