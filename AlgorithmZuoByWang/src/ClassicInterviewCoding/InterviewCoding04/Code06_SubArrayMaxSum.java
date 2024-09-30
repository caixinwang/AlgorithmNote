package ClassicInterviewCoding.InterviewCoding04;

import java.util.TreeMap;
import java.util.TreeSet;

public class Code06_SubArrayMaxSum {

	/**
	 * 1.假设最大累加和的子数组为res，则res的任意前缀数组的累加和一定大于0
	 * 		反证法：如果存在一个前缀数组i~j'的累加和<0，则j'~j大累加和大于res的累加和，矛盾
	 * 		运用：要找以i为头的子数组的最大累加和，如果从i出发累加到j累加和小于0，可以大胆舍弃以i为头，以j'（j'>j）为尾的答案。
	 * 			因为之后的答案已经不满足1的结论
	 * 算法：算法用一个sum一路累加，一路更新max。sum<0了就重置为0。
	 * 2.本质上一路上可能有多个出发点：（s0=0，s1,s2...）。需要证明si~sj中间的出发点都不是答案。
	 * 		反证法：假设m~t取到最大累加和 ，其中si<m<si'，算法决定了si~m-1累加和大于0，所以si~t累加和大于m~t，矛盾。
	 * @param arr 返回arr的子数组最大累加和
	 */
	public static int maxSum(int[] arr) {
		int max=Integer.MIN_VALUE,sum=0;
		for (int i = 0; i < arr.length; i++) {
			sum+=arr[i];
			max=Math.max(max,sum);
			if (sum<0) sum=0;
		}
		return max;
	}

	/**
	 * 单调队列
	 * 先形成一个前缀和数组s[0,num1,num1+num2,.....]
	 * 0<=i<j<N , s[j]-s[i]就是子数组的累加和，问题转化为找到一对（i，j）使得s[j]-[i]达到最大值
	 * 用单调队列维持一个从小到大的序列，从左往右遍历s数组对于一个新数s[x],即与队头产生一个候选答案，s[x]从尾入队，继续维持从小到大
	 * 利用单调队列 每个位置作为子数组开头的候选答案都产生了 如果s[x]比队尾小，那么s[x]和队头产生一个候选答案之后会把一路把队尾踢掉，
	 * 直到队列为空或者比队尾大时s[x]才入队。后面要遍历到的s[x']与s[x]的差一定大于与s[tail]的差，所以候选答案的子数组端点不可能
	 * 出现在被踢掉的tail。
	 * 利用单调队列维系的从小到大的单调性，从左往右遍历s数组，对于每一个位置s[x]作为右端点,x>0,都可以找到队头作为左端点为最优解。
	 * 	遍历结束可以抓取到全局的最优解
	 *
	 */
	public static int maxSum2(int[] arr) {
		int N=arr.length,h=0,t=-1,max=1<<31;
		int[] dq=new int[N+1],s=new int[N+1];
		for (int i = 0; i < arr.length; i++) {
			s[i+1]=arr[i]+s[i];
		}
		dq[++t]=s[0];
		for (int i = 1; i < s.length; i++) {
			if (s[i]-dq[h]>max) max=s[i]-dq[h];
			while(h!=t+1&&s[i]<dq[t]) t--;
			dq[++t]=s[i];
		}
		return max;
	}

	//N logN 复杂度 使用有序表 暴力拿出最小值     s数组也可以省去
	//有序表还可以解决<=K子数组累加和最大值的问题
	public static int maxSum3(int[] arr) {
		int N=arr.length,max=1<<31,sum=0;
		TreeSet<Integer> treeSet=new TreeSet<>();
		treeSet.add(sum);
		for (int i = 0; i < N; i++) {
			sum+=arr[i];
			int tmp=sum-treeSet.first();
			if (tmp>max) max=tmp;
			treeSet.add(sum);
		}
		return max;
	}


	public static void main(String[] args) {
		boolean ok=true;
		out:
		for (int i = 0; i < 10000; i++) {
			int[] arr=new int[g(1,10)];
			for (int j = 0; j < arr.length; j++) {
				arr[j]=g(-100,100);
			}
			int[] res=new int[]{maxSum(arr),maxSum2(arr),maxSum3(arr)};
			for (int j = 0; j < res.length; j++) {
				if (res[j]!=res[0]){
					ok=false;
					break out;
				}
			}
		}
		System.out.println(ok?"success":"error");
	}

	public static int g(int l ,int r) {
		return l+((int)(Math.random()*(r-l+1)));
	}

}
