package ClassicInterviewCoding.InterviewCoding11;

import java.util.HashSet;

public class Code02_WorldBreak {
	/*
	 * 
	 * 假设所有字符都是小写字母. 大字符串是str. arr是去重的单词表, 每个单词都不是空字符串且可以使用任意次.
	 * 使用arr中的单词有多少种拼接str的方式. 返回方法数.
	 * 
	 */

	public static int ways(String str, String[] arr) {
		HashSet<String> set = new HashSet<>();
		for (String candidate : arr) {
			set.add(candidate);
		}
		return process(str, 0, set);
	}

	// 所有的贴纸，都已经放在了set中
	// str[i....] 能够被set中的贴纸分解的话，返回分解的方法数
	public static int process(String str, int i, HashSet<String> set) {
		if (i == str.length()) {
			return 1;
		}
		int ways = 0;
		// [i ... end] 前缀串 每一个前缀串
		for (int end = i; end < str.length(); end++) {
			String pre = str.substring(i, end + 1);// [)
			if (set.contains(pre)) {
				ways += process(str, end + 1, set);
			}
		}
		return ways;
	}

	public static int ways1(String str, String[] arr) {
		if (str == null || str.length() == 0 || arr == null || arr.length == 0) {
			return 0;
		}
		HashSet<String> map = new HashSet<>();
		for (String s : arr) {
			map.add(s);
		}
		return f(str, map, 0);
	}

	public static int f(String str, HashSet<String> map, int index) {
		if (index == str.length()) {
			return 1;
		}
		int ways = 0;
		for (int end = index; end < str.length(); end++) {
			if (map.contains(str.substring(index, end + 1))) {
				ways += f(str, map, end + 1);
			}
		}
		return ways;
	}

	public static int ways2(String str, String[] arr) {//动态规划
		if (str == null || str.length() == 0 || arr == null || arr.length == 0) {
			return 0;
		}
		char[] s = str.toCharArray();
		HashSet<String> set=new HashSet<>();
		for (int i = 0; i < arr.length; i++) {
			set.add(arr[i]);
		}
		int N=s.length;
		int[] dp=new int[N+1];
		dp[N]=1;//这样就不用单独初始化N-1位置了，这样i~N-1如果也在arr中就不用担心i-1会行不通
		for (int i=N-1;i>=0;i--){
			String t="";
			for (int end=i;end<N;end++){//<N而不是<=N,这样i~N-1在arr的话dp[i]+1，符合
				t+=s[end];
				if (set.contains(t)) dp[i]+=dp[end+1];
			}
		}
		return dp[0];
	}

	public static class Node {
		public boolean end;
		public Node[] nexts;

		public Node() {
			end = false;
			nexts = new Node[26];
		}
	}

	public static int ways3(String str, String[] arr) {
		if (str == null || str.length() == 0 || arr == null || arr.length == 0) {
			return 0;
		}
		Node root = new Node();
		for (String s : arr) {
			char[] chs = s.toCharArray();
			Node node = root;
			int index = 0;
			for (int i = 0; i < chs.length; i++) {
				index = chs[i] - 'a';
				if (node.nexts[index] == null) {
					node.nexts[index] = new Node();
				}
				node = node.nexts[index];
			}
			node.end = true;
		}
		return g(str.toCharArray(), root, 0);
	}

	// str[i...] 被分解的方法数，返回

	public static int g(char[] str, Node root, int i) {
		if (i == str.length) {
			return 1;
		}
		int ways = 0;
		Node cur = root;
		// i...end
		for (int end = i; end < str.length; end++) {
			int path = str[end] - 'a';
			if (cur.nexts[path] == null) {
				break;
			}
			cur = cur.nexts[path];
			if (cur.end) { // i...end
				ways += g(str, root, end + 1);
			}
		}
		return ways;
	}

	public static int ways4(String s, String[] arr) {
		if (s == null || s.length() == 0 || arr == null || arr.length == 0) {
			return 0;
		}
		Node head=new Node();
		for (int i = 0; i < arr.length; i++) {//把arr里面的string全部加前缀树
			Node cur=head;
			char[] chars = arr[i].toCharArray();
			int road=0;
			for (int j = 0; j < chars.length; j++) {
				road=chars[j]-'a';
				if (cur.nexts[road]==null) cur.nexts[road]=new Node();
				cur=cur.nexts[road];
			}
			cur.end=true;
		}
		int N=s.length();
		char[] str = s.toCharArray();
		int[] dp=new int[N+1];
		dp[N]=1;
		for (int i=N-1;i>=0;i--){
			Node cur=head;
			for (int end=i;end<N;end++){
				int road=str[end]-'a';
				if (cur.nexts[road]==null) break;
				cur=cur.nexts[road];
				if (cur.end)dp[i]+=dp[end+1];
			}
		}
		return dp[0];

	}

	// 以下的逻辑都是为了测试
	public static class RandomSample {
		public String str;
		public String[] arr;

		public RandomSample(String s, String[] a) {
			str = s;
			arr = a;
		}
	}

	// 随机样本产生器
	public static RandomSample generateRandomSample(char[] candidates, int num, int len, int joint) {
		String[] seeds = randomSeeds(candidates, num, len);
		HashSet<String> set = new HashSet<>();
		for (String str : seeds) {
			set.add(str);
		}
		String[] arr = new String[set.size()];
		int index = 0;
		for (String str : set) {
			arr[index++] = str;
		}
		StringBuilder all = new StringBuilder();
		for (int i = 0; i < joint; i++) {
			all.append(arr[(int) (Math.random() * arr.length)]);
		}
		return new RandomSample(all.toString(), arr);
	}

	public static String[] randomSeeds(char[] candidates, int num, int len) {
		String[] arr = new String[(int) (Math.random() * num) + 1];
		for (int i = 0; i < arr.length; i++) {
			char[] str = new char[(int) (Math.random() * len) + 1];
			for (int j = 0; j < str.length; j++) {
				str[j] = candidates[(int) (Math.random() * candidates.length)];
			}
			arr[i] = String.valueOf(str);
		}
		return arr;
	}

	public static void main(String[] args) {
		char[] candidates = { 'a', 'b' };
		int num = 20;
		int len = 4;
		int joint = 5;
		int testTimes = 30000;
		boolean testResult = true;
		for (int i = 0; i < testTimes; i++) {
			RandomSample sample = generateRandomSample(candidates, num, len, joint);
			int ans1 = ways1(sample.str, sample.arr);
			int ans2 = ways2(sample.str, sample.arr);
			int ans3 = ways3(sample.str, sample.arr);
			int ans4 = ways4(sample.str, sample.arr);
			if (ans1 != ans2 || ans3 != ans4 || ans2 != ans4) {
				testResult = false;
			}
		}
		System.out.println(testTimes + "次随机测试是否通过：" + testResult);
	}

}
