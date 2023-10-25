package Leetcode.AllQuestions;

import java.util.HashMap;

/**
 * 思路：搞两张表，结构里面有个size决定你进来的元素的编号。然后map1放着用户给你的数据对应的编号。map2存在编号对应的用户的数据。
 * 你要加，那么就到map1查看有没有重复的，没有重复就加。要get随机，那么就用你的size去算一个随机的编号，然后到map2取。
 * 如果要删呢？我要保证我的map2中的编号连续。所以我总是拿最后一个记录去填这个洞！
 */

public class Leetcode_0380_InsertDeleteGetRandom {

	class RandomizedSet {

		HashMap<Integer,Integer> keyNo;
		HashMap<Integer,Integer> noKey;
		int size;

		public RandomizedSet() {
			size=0;
			keyNo=new HashMap<>();
			noKey=new HashMap<>();
		}

		public boolean insert(int val) {
			if (!keyNo.containsKey(val)){
				++size;
				keyNo.put(val,size);
				noKey.put(size,val);
				return true;
			}
			return false;
		}

		public boolean remove(int val) {
			if (keyNo.containsKey(val)){
				int delNo=keyNo.get(val);
				int lastKey=noKey.get(size);
				keyNo.put(lastKey,delNo);
				keyNo.remove(val);
				noKey.put(delNo,lastKey);
				noKey.remove(size);
				size--;
				return true;
			}
			return false;
		}

		public int getRandom() {
			if (size==0) return -1;
			return noKey.get(1+(int)(Math.random()*(size)));
		}
	}

}
