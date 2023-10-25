package ClassicInterviewCoding.InterviewCoding11;

import java.util.HashMap;

public class Code03_LongestSumEqualK {

	public static class Node {
		int value;
		public Node left;
		public Node right;

		public Node(int v) {
			value = v;
		}
	}

//	public static int ans = 0; // 收集累加和为K的，最长路径有多少个节点

	/**
	 * 回想求子数组的累加和为k的最长路径的时候，我们需要一个hashmap来维持某一个累加和前缀最早出现的位置。
	 * 在遍历子数组的时候，利用一个变量迭代来维持0~i的累加和。现在在树中，无非就是树有很多的路径，我们要在
	 * 树的深度优先遍历中做这件事情。那么我们需要传的参数就要有当前所在的层数，因为要对标数组问题时候的下标
	 * 在树深度优先遍历的时候我们是不知道层数的，所以要传参进去。还需要传一个参数presum，在数组问题的时候我们
	 * 用一个变量就维持住了，但是这里需要传参。还需要一个HashMap，记录某一个累加和最早出现的层数。这样就够了。
	 * 注意：HashMap记得恢复现场。我们把答案当做一个只有一个元素的一维数组传进去更新，不用全局的变量
	 * @param head 头节点
	 * @param K 累加和为k
	 * @return 返回以head为头的树，累加和为k的最长路径，路径只能一路往下
	 */
	public static int longest(Node head, int K) {
		int[] ans = new int[1];
		// key ： 前缀和
		// value : 该前缀和，最早出现在哪一层
		// sumMap：只维持，从头节点出发到当前节点，这条路径上的前缀和
		HashMap<Integer, Integer> sumMap = new HashMap<>();
		sumMap.put(0, -1);//维持住0~i一整个都是子路径时候转换公式的含义
		process(head, 0, 0, K, sumMap,ans);
		return ans[0];
	}

	// 节点X在level层，从头节点到X的父节点形成的累加和是preSum，
	// 从头节点到X的路径上，每一个前缀和都存在sumMap里(key)，记录的是该前缀和最早出现的层数(value)
	// 求出必须以X节点结尾的、累加和是K的所有路径中，含有最多的节点是多少？
	// 并看看能不能更新全局的ans
	public static void process(Node x, int level, int preSum, int k, HashMap<Integer, Integer> sumMap,int[] ans) {
		if (x!=null){
			int sum=preSum+x.value;
			boolean needRepair=false;//需不需要恢复现场
			if (sumMap.containsKey(sum-k)) {
				ans[0] = Math.max(ans[0], level - sumMap.get(sum - k));
			}
			if (!sumMap.containsKey(sum)){
				needRepair=true;
				sumMap.put(sum,level);
			}
			process(x.left,level+1,sum,k,sumMap,ans);
			process(x.right,level+1,sum,k,sumMap,ans);
			if (needRepair) sumMap.remove(sum);
		}
	}

	public static void main(String[] args) {
		//                   3
		//           -2             3
		//        1      4      5      -7
		//       3 5   2 -5  -5  -3   1   5
		int K = 0;
		Node head = new Node(3);
		head.left = new Node(-2);
		head.left.left = new Node(1);
		head.left.right = new Node(4);
		head.left.left.left = new Node(3);
		head.left.left.right = new Node(5);
		head.left.right.left = new Node(2);
		head.left.right.right = new Node(5);

		head.right = new Node(3);
		head.right.left = new Node(5);
		head.right.right = new Node(-7);
		head.right.left.left = new Node(-5);
		head.right.left.right = new Node(-3);
		head.right.right.left = new Node(1);
		head.right.right.right = new Node(5);

		System.out.println(longest(head, K));

	}

}
