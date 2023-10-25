package ClassicInterviewCoding.InterviewCoding07;

import java.util.Arrays;
import java.util.HashMap;

// 本题为leetcode原题
// 测试链接：https://leetcode.com/problems/largest-component-size-by-common-factor/
// 方法1会超时，但是方法2直接通过
public class Code04_LargestComponentSizebyCommonFactor {

	public static int largestComponentSize1(int[] arr) {
		int N = arr.length;
		UnionFind set = new UnionFind(N);
		for (int i = 0; i < N; i++) {
			for (int j = i + 1; j < N; j++) {
				if (gcd(arr[i], arr[j]) != 1) {//最大公约数大于1就把两个下标对应的集合union
					set.union(i, j);
				}
			}
		}
		return set.maxSize();//看题目需要，返回maxSize还是sets
	}

	public static int largestComponentSize2(int[] arr) {
		if (arr==null||arr.length==0) return 0;
		UnionFind unionFind=new UnionFind(arr.length);//用下标来代表每一个元素
		HashMap<Integer,Integer> factorMap=new HashMap<>();//<因子，最早出现的下标>
		for (int i = 0; i < arr.length; i++) {
			int num=arr[i];
			if (!factorMap.containsKey(num)){//1对应的那个因子就是num，单独判断
				factorMap.put(num,i);
			}else {
				unionFind.union(factorMap.get(num),i);
			}
			for (int k=2;k*k<=num;k++){//从2开始列举num所有的因子对
				if (num%k==0){//整除，说明k是一个因子，num/k是另一个因子
					if (!factorMap.containsKey(k)){//1对应的那个因子单独判断
						factorMap.put(k,i);
					}else {
						unionFind.union(factorMap.get(k),i);
					}
					if (!factorMap.containsKey(num/k)){//1对应的那个因子单独判断
						factorMap.put(num/k,i);
					}else {
						unionFind.union(factorMap.get(num/k),i);
					}
				}
			}
		}
		return unionFind.maxSize();
	}

	public static int gcd(int m, int n) {
		return n == 0 ? m : gcd(n, m % n);
	}

	/**
	 * parent[i]如果为负数，则i是一个代表结点，i集合的大小为 -parent[i]
	 *
	 */
	public static class UnionFind {
		private int [] parent;//给元素编号，默认从0开始,下标i就代表是第i号元素

		public UnionFind(int N) {//初始状态有N个独立的集合
			parent=new int[N];
			Arrays.fill(parent, -1);//全部填成-1代表是一开始所有的结点都是代表结点
		}

		public int maxSize(){//返回最大的集合有多少个元素
			int max=Integer.MIN_VALUE;
			for (int i = 0; i < parent.length; i++) {
				if (parent[i]<0){//代表结点我才看
					max = Math.max(max, -parent[i] );
				}
			}
			return max;
		}

		public int find(int i){//找i所在集合的代表结点
			if (parent[i]<0) return i;
			else return parent[i]=find(parent[i]);//递归的含义就是找代表结点，且一路打扁平，尾递归，效率很高
		}

		public void union(int i,int j){//i和j所在的集合合并去，小的挂在大的下面
			int f1 = find(i);
			int f2 = find(j);
			if (f1==f2) return;//相同的集合，合并个毛
			if (-parent[f2]>-parent[f1]){//保证f1代表最大，f2代表最小
				int tmp=f1;
				f1=f2;
				f2=tmp;
			}
			parent[f1] += parent[f2];// 更新大集合的大小，-2 + (-3) =-5 ,含义没变
			parent[f2]=f1;//小集合f2挂在大集合f1上面
		}

	}



}
