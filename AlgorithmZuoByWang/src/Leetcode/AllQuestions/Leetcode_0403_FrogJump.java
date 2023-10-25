package Leetcode.AllQuestions;

import java.util.HashMap;
import java.util.HashSet;

public class Leetcode_0403_FrogJump {

	public static boolean canCross(int[] stones) {
		HashSet<Integer> set = new HashSet<>();
		for (int num : stones) {
			set.add(num);
		}
		HashMap<Integer, HashMap<Integer, Boolean>> dp = new HashMap<>();
		return jump(1, 1, stones[stones.length - 1], set, dp);
	}

	public static boolean jump(int cur, int pre, int end, HashSet<Integer> set,
			HashMap<Integer, HashMap<Integer, Boolean>> dp) {
		if (cur == end) {
			return true;
		}
		if (!set.contains(cur)) {
			return false;
		}
		if (dp.containsKey(cur) && dp.get(cur).containsKey(pre)) {
			return dp.get(cur).get(pre);
		}
		boolean ans = (pre > 1 && jump(cur + pre - 1, pre - 1, end, set, dp)) 
				|| jump(cur + pre, pre, end, set, dp)
				|| jump(cur + pre + 1, pre + 1, end, set, dp);
		if (!dp.containsKey(cur)) {
			dp.put(cur, new HashMap<>());
		}
		if (!dp.get(cur).containsKey(pre)) {
			dp.get(cur).put(pre, ans);
		}
		return ans;
	}

	 HashMap<Integer,HashMap<Integer,Boolean>> map=new HashMap<>();
	 public boolean canCross2(int[] stones) {
	     return f(stones,0,0);
	 }

	 public boolean f(int[] stones,int index,int pre){
	     if(!map.containsKey(index)) map.put(index,new HashMap<>());
	     if(map.get(index).containsKey(pre)) return map.get(index).get(pre);
	     if(index==stones.length-1) return true;
	     boolean ans=false;
	     for(int jump=max(1,pre-1);jump<=pre+1;jump++){
	         int s=find(stones,index+1,stones[index]+jump);//二分可以用哈希表优化，事先把石头都放进去
	         if(s!=-1) ans|=f(stones,s,jump);
	     }
	     map.get(index).put(pre,ans);
	     return ans;
	 }

	 public int find(int[] stones,int index,int num){
	     int n=stones.length,l=index,r=n-1;
	     while(l<=r){
	         int mid=l+(r-l>>1);
	         if(stones[mid]==num) return mid;
	         else if(stones[mid]>num) r=mid-1;
	         else l=mid+1;
	     }
	     return -1;
	 }

	 public int max(int a,int b){return a>b?a:b;}

}
