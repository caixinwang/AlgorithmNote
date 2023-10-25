package Leetcode.AllQuestions;

import java.util.LinkedList;

// 本实现比leetcode要求的要难
// 返回了所有节点是不是良好出发点
public class Leetcode_0134_GasStation {

	public static int canCompleteCircuit1(int[] gas, int[] cost) {
		boolean[] good = goodArray(gas, cost);
		for (int i = 0; i < gas.length; i++) {
			if (good[i]) {
				return i;
			}
		}
		return -1;
	}

	/**
	 *
	 * @param g i加油站可以加多少油
	 * @param c 到下一个加油站i+1的消耗
	 * @return 返回哪些点是良好出发点.[i]代表i位置是不是良好出发点
	 */
	public static boolean[] goodArray(int[] g, int[] c) {
		for (int i = 0; i < g.length; i++) {
			g[i]-=c[i];//变成储能数组
		}
		int[] sum=new int[g.length<<1];//两倍长的累加和数组
		sum[0]=g[0];
		for (int i = 1; i < sum.length; i++) {
			sum[i]=sum[i-1]+i<g.length?g[i]:g[i-g.length];
		}
		boolean[] ans=new boolean[g.length];
		int l=0,r=0,index=0;
		LinkedList<Integer> mq=new LinkedList<>();//放下标
		for (int i = 0; i < g.length; i++) {//把窗口扩大到含len个数
			while(!mq.isEmpty()&&sum[r]<=sum[mq.peekLast()]) mq.pollLast();
			mq.addLast(r++);
		}
		ans[index++]=sum[mq.peekFirst()]>=0;
		for (int i = 1; i < g.length; i++) {//再生成后面的答案，每次都是加一个减一个
			while(!mq.isEmpty()&&sum[r]<=sum[mq.peekLast()]) mq.pollLast();
			mq.addLast(r++);
			if (l==mq.peekFirst()) mq.pollFirst();//过期
			l++;
			ans[index++]=sum[mq.peekFirst()]-sum[i-1]>=0;//减掉前面的累加和，代表从i点开始
		}
		return ans;
	}

	// 这个方法的时间复杂度O(N)，额外空间复杂度O(1) 训练营讲了
	public static int canCompleteCircuit2(int[] gas, int[] cost) {
		if (gas == null || gas.length == 0) {
			return -1;
		}
		if (gas.length == 1) {
			return gas[0] < cost[0] ? -1 : 0;
		}
		boolean[] good = stations(cost, gas);
		for (int i = 0; i < gas.length; i++) {
			if (good[i]) {
				return i;
			}
		}
		return -1;
	}

	public static boolean[] stations(int[] cost, int[] gas) {
		if (cost == null || gas == null || cost.length < 2 || cost.length != gas.length) {
			return null;
		}
		int init = changeDisArrayGetInit(cost, gas);
		return init == -1 ? new boolean[cost.length] : enlargeArea(cost, init);
	}

	public static int changeDisArrayGetInit(int[] dis, int[] oil) {
		int init = -1;
		for (int i = 0; i < dis.length; i++) {
			dis[i] = oil[i] - dis[i];
			if (dis[i] >= 0) {
				init = i;
			}
		}
		return init;
	}

	public static boolean[] enlargeArea(int[] dis, int init) {
		boolean[] res = new boolean[dis.length];
		int start = init;
		int end = nextIndex(init, dis.length);
		int need = 0;
		int rest = 0;
		do {
			// 当前来到的start已经在连通区域中，可以确定后续的开始点一定无法转完一圈
			if (start != init && start == lastIndex(end, dis.length)) {
				break;
			}
			// 当前来到的start不在连通区域中，就扩充连通区域
			if (dis[start] < need) { // 当前start无法接到连通区的头部
				need -= dis[start];
			} else { // 当前start可以接到连通区的头部，开始扩充连通区域的尾巴
				rest += dis[start] - need;
				need = 0;
				while (rest >= 0 && end != start) {
					rest += dis[end];
					end = nextIndex(end, dis.length);
				}
				// 如果连通区域已经覆盖整个环，当前的start是良好出发点，进入2阶段
				if (rest >= 0) {
					res[start] = true;
					connectGood(dis, lastIndex(start, dis.length), init, res);
					break;
				}
			}
			start = lastIndex(start, dis.length);
		} while (start != init);
		return res;
	}

	// 已知start的next方向上有一个良好出发点
	// start如果可以达到这个良好出发点，那么从start出发一定可以转一圈
	public static void connectGood(int[] dis, int start, int init, boolean[] res) {
		int need = 0;
		while (start != init) {
			if (dis[start] < need) {
				need -= dis[start];
			} else {
				res[start] = true;
				need = 0;
			}
			start = lastIndex(start, dis.length);
		}
	}

	public static int lastIndex(int index, int size) {
		return index == 0 ? (size - 1) : index - 1;
	}

	public static int nextIndex(int index, int size) {
		return index == size - 1 ? 0 : (index + 1);
	}

}
