package ClassicInterviewCoding.InterviewCoding16;

import java.util.ArrayList;
import java.util.HashMap;

public class Code02_FreedomTrail {

	public static int walk(int i, int j, int m) {
		return Math.min(j-i<0?j-i+m:j-i,i-j<0?i-j+m:i-j);
	}

	/**
	 * process需要知道什么？
	 * 需要知道每一个字符在ring上的位置-map-固定
	 * ring有多大，用于算出需要几步走到-固定
	 * 当前来到了ring上的哪一个位置-可变
	 * 你要从哪里开始解决后面整串的钥匙-可变
	 * 钥匙串是什么-固定
	 * @param ring 环
	 * @param key 钥匙
	 * @return 返回最少需要几步可以转出钥匙
	 */
	public static int findRotateSteps1(String ring, String key) {
		if (ring==null||ring.length()==0||key==null||key.length()==0) return 0;
		char[] r = ring.toCharArray();
		HashMap<Character,ArrayList<Integer>> map=new HashMap<>();//每次字符出现在哪些位置r
		for (int i = 0; i < r.length; i++) {
			if (!map.containsKey(r[i])) map.put(r[i],new ArrayList<>());
			map.get(r[i]).add(i);//字符r[i]出现在i位置
		}
		return process(0,0,key.toCharArray(),map,ring.length());
	}

	/**
	 * 这是一个深度优先遍历，如果你当前需要keyIndex位置的字符，那么就去map里面查ring中有哪些位置有这个字符，然后每条路深度优先遍历
	 * 走到底，决策出一个最短的.
	 * @param ringIndex 当前来到了ring的什么位置，也就是ring的正上方是什么字符
	 * @param keyIndex 当前需要解决keyIndex~key.len-1的钥匙串
	 * @param key 钥匙串字符
	 * @param map 代表ring的结构，可以告诉我们每一个字符在ring上出现的位置
	 * @param ringSize ring的大小，方便算出需要走几步到指定的位置
	 * @return 返回从ringIndex出发搞定keyIndex~key.len-1的钥匙串最少需要走几步
	 */
	private static int process(int ringIndex,
							   int keyIndex,
							   char[] key,
							   HashMap<Character, ArrayList<Integer>> map,
							   int ringSize) {
		if (keyIndex==key.length) return 0;//说明都搞定了，不需要走了
		if (!map.containsKey(key[keyIndex])) return Integer.MAX_VALUE;
		int ans=Integer.MAX_VALUE;
		for (Integer index : map.get(key[keyIndex])) {//在index位置上可以得到key[keyIndex]字符
			ans = Math.min(ans,walk(ringIndex,index,ringSize)+1+process(index,keyIndex+1,key,map,ringSize));
		}
		return ans;
	}

	public static int findRotateSteps2(String ring, String key) {//暴力递归改傻缓存
		if (ring==null||ring.length()==0||key==null||key.length()==0) return 0;
		char[] r = ring.toCharArray();
		HashMap<Character,ArrayList<Integer>> map=new HashMap<>();//每次字符出现在哪些位置r
		for (int i = 0; i < r.length; i++) {
			if (!map.containsKey(r[i])) map.put(r[i],new ArrayList<>());
			map.get(r[i]).add(i);//字符r[i]出现在i位置
		}
		int[][] dp=new int[ring.length()][key.length()+1];
		for (int i = 0; i < dp.length; i++) {
			for (int j = 0; j < dp[0].length; j++) {
				dp[i][j]=Integer.MAX_VALUE;
			}
		}
		return process2(0,0,key.toCharArray(),map,ring.length(),dp);
	}

	private static int process2(int ringIndex,
							   int keyIndex,
							   char[] key,
							   HashMap<Character, ArrayList<Integer>> map,
							   int ringSize,
								int[][] dp) {
		if (dp[ringIndex][keyIndex]!=Integer.MAX_VALUE) return dp[ringIndex][keyIndex];
		if (keyIndex==key.length) {//说明都搞定了，不需要走了
			dp[ringIndex][keyIndex]=0;
			return dp[ringIndex][keyIndex];}
		if (!map.containsKey(key[keyIndex])) {
			dp[ringIndex][keyIndex]=Integer.MAX_VALUE;
			return dp[ringIndex][keyIndex];
		}
		int ans=Integer.MAX_VALUE;
		for (Integer index : map.get(key[keyIndex])) {//在index位置上可以得到key[keyIndex]字符
			ans = Math.min(ans,walk(ringIndex,index,ringSize)+1+process2(index,keyIndex+1,key,map,ringSize,dp));
		}
		dp[ringIndex][keyIndex]=ans;
		return dp[ringIndex][keyIndex];
	}

	public static int findRotateSteps3(String ring, String key) {//动态规划--省不掉枚举
		if (ring==null||ring.length()==0||key==null||key.length()==0) return 0;
		char[] r = ring.toCharArray();
		char[] k = key.toCharArray();
		HashMap<Character,ArrayList<Integer>> map=new HashMap<>();//每次字符出现在哪些位置r
		for (int i = 0; i < r.length; i++) {
			if (!map.containsKey(r[i])) map.put(r[i],new ArrayList<>());
			map.get(r[i]).add(i);//字符r[i]出现在i位置
		}
		int N=ring.length(),M=key.length();
		int[][] dp=new int[N][M+1];
		for (int i = 0; i < N; i++) {
			dp[i][M]=0;
		}
		for (int keyIndex = M-1; keyIndex >=0; keyIndex--) {//从右往左
			for (int ringIndex = 0; ringIndex < N; ringIndex++) {//从上往下还是从下往上都无所谓
				dp[ringIndex][keyIndex]=Integer.MAX_VALUE;
				for (Integer index : map.get(k[keyIndex])) {//在index位置上可以得到key[keyIndex]字符
					dp[ringIndex][keyIndex] = Math.min(dp[ringIndex][keyIndex],
							walk(ringIndex,index,N)+1+dp[index][keyIndex+1]);
				}
			}
		}
		return dp[0][0];
	}


}
