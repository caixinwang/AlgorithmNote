package BasicLearning.Class13_SegmentTree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

/**
 * 这题并不是直接给你一堆的方块，然后叫你一次性初始化在线段树了，它需要每一个方块落下的时候的最高的高度。
 * 所以不需要一个arr来装。因为这题其实是需要你把build移动到目标函数，动态的记录max变化的过程。
 * 这题的max其实就是经典实现中的sum的地位，change和update都是为它准备的懒东西
 */
public class Code02_FallingSquares {//俄罗斯方块问题

	public static class SegmentTree {
		private int N;
		private int[] height;//维持区间最大值
		private boolean[] update;
		private int[] change;

		public SegmentTree(int size) {
			N=size;
			int len=size,digit=0;
			while(len!=0){
				digit++;
				len>>=1;
			}
			size=(1<<digit+1);
			height=new int[size];
			update=new boolean[size];
			change=new int[size];
		}

		private void pushUp(int rt){
			height[rt]=Math.max(height[rt<<1],height[rt<<1|1]);
		}

		private void pushDown(int rt){
			if (update[rt]){
				update[rt<<1]=true;
				update[rt<<1|1]=true;
				change[rt<<1]=change[rt];
				change[rt<<1|1]=change[rt];
				height[rt<<1]=change[rt];
				height[rt<<1|1]=change[rt];
				update[rt]=false;
			}
		}

		public void update(int L,int R,int C){
			if (L>R||L<1||R>N) return;
			update(L,R,C,1,N,1);
		}

		public void update(int L,int R,int C,int l,int r,int rt){
			if (L<=l&&r<=R){
				update[rt]=true;
				change[rt]=C;
				height[rt]=C;
			}else {
				pushDown(rt);
				int mid=l+r>>1;
				if (L<=mid) update(L,R,C,l,mid,rt<<1);
				if (R> mid) update(L,R,C,mid+1,r,rt<<1|1);
				pushUp(rt);
			}
		}

		public int query(int L,int R){
			if (L>R||L<1||R>N) return -1;
			return query(L,R,1,N,1);
		}

		private int query(int L, int R, int l, int r, int rt) {
			if (L<=l&&r<=R) return height[rt];
			else {
				pushDown(rt);
				int mid=l+r>>1;
				int res=Integer.MIN_VALUE;
				if (L<=mid) res = Math.max(res, query(L,R,l,mid,rt<<1));
				if (R>mid) res = Math.max(res, query(L,R,mid+1,r,rt<<1|1));
				return res;
			}
		}


	}

	// positions
	// [2,7] -> 2 , 8
	// [3, 10] -> 3, 12

	/**
	 * 由于方块的坐标可能很大，所以我们不可能把方块的坐标进行精细化。我们应该把方块这些坐标统计起来，看看有多少不一样的坐标。
	 * 根据有多少不一样的下标来建立相应的线段树大小。具体做法就是把这些方块坐标换算成我们线段树中的下标。
	 * 但是要注意如果方块是[2,3]那么应该对应[2,4]这个区间而不是[2,5]因为5其实这个位置是可以继续落方块的。
	 * 所以有这句pos.add(arr[0] + arr[1] - 1); 方块这些离散的区间边界点需要先排序，所以先用TreeSet，
	 * 然后从TreeSet中放到HashSet中，Hash表中是方块这些离散坐标和我们线段树坐标的映射，例如可能方块的100坐标映射到
	 * 线段树中的10坐标。TreeSet中最小的方块下标当然对应线段树中的1下标，这是显然的。
	 * @param positions 一堆方块
	 * @return 返回这些方块下标（杂乱）对应在线段数中的些些下标（1~N）
	 */
	public HashMap<Integer, Integer> index(int[][] positions) {
		TreeSet<Integer> pos = new TreeSet<>();
		for (int[] arr : positions) {
			pos.add(arr[0]);
			pos.add(arr[0] + arr[1] - 1);
		}
		HashMap<Integer, Integer> map = new HashMap<>();
		int count = 0;
		for (Integer index : pos) {
			map.put(index, ++count);
		}
		return map;
	}

	public List<Integer> fallingSquares(int[][] positions) {
		HashMap<Integer, Integer> map = index(positions);
		int N = map.size(); // 1 ~ 	N
		SegmentTree segmentTree = new SegmentTree(N);
		int max = 0;
		List<Integer> res = new ArrayList<>();
		// 每落一个正方形，收集一下，所有东西组成的图像，最高高度是什么
		for (int[] arr : positions) {
			int L = map.get(arr[0]);
			int R = map.get(arr[0] + arr[1] - 1);
			int height = segmentTree.query(L, R) + arr[1];
			max = Math.max(max, height);
			res.add(max);
			segmentTree.update(L, R, height);
		}
		return res;
	}

}
