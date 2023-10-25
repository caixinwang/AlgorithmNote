package Leetcode.AllQuestions;

import java.util.HashMap;

public class Leetcode_0437_PathSumIII {

	public class TreeNode {
		public int val;
		public TreeNode left;
		public TreeNode right;
	}

	public static int pathSum(TreeNode root, int sum) {
		HashMap<Long,Integer> map=new HashMap<>();//<累加和，出现次数>
		map.put(0L,1);//代表0~i自己这个累加和出现了1次
		return f(root,0,sum,map);
	}

	/**
	 * 其实就是一个先序遍历，把打印行为换成map的更新行为，这样我们就有之前路径的所有累加和信息。
	 * 记得恢复现场，这样才能保证map存的是单条线路，也就是一个深度优先遍历的线路。
	 * @param head 当前来到head
	 * @param path 之前的累加和
	 * @param target 要凑的目标
	 * @param map 之前路径有的前缀和以及对应的次数都给你了。累加和是long类型，怕溢出
	 * @return -
	 */
	public static int f(TreeNode head,long path,int target,HashMap<Long,Integer> map){
		if (head==null) return 0;
		long cur=path+head.val;
		int ans=0;
		ans+=map.getOrDefault(cur-target,0);
		if (!map.containsKey(cur)){
			map.put(cur,1);
		}else {
			map.put(cur,1+map.get(cur));
		}
		ans+=f(head.left,cur,target,map);
		ans+=f(head.right,cur,target,map);
		if (map.get(cur)==1){
			map.remove(cur);
		}else {
			map.put(cur,-1+map.get(cur));
		}
		return ans;
	}

}
