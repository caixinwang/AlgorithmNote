package Leetcode.AllQuestions;

import java.util.LinkedList;
import java.util.List;

public class Leetcode_0140_WordBreakII {
	public static List<String> wordBreak(String s, List<String> wordDict) {
		Node head = new Node();
		for (String word : wordDict) {
			Node cur = head;
			for (char c : word.toCharArray()) {
				if (cur.nodes[c - 'a'] == null) cur.nodes[c - 'a'] = new Node();
				cur = cur.nodes[c - 'a'];
			}
			cur.end = true;
		}
		List<String> ans=new LinkedList<>();
		f(s.toCharArray(), 0, new LinkedList<String>(), head,ans);
		return ans;
	}

	private static void f(char[] str, int index,LinkedList<String> path, Node head,List<String> ans) {
		if (index == str.length) {
			StringBuilder builder=new StringBuilder();
			for (String s : path) {
				if (builder.length()!=0) builder.append(' ');
				builder.append(s);
			}
			ans.add(builder.toString());
			return;
		}
		Node cur = head;
		String s="";
		for (int i = index; i < str.length; i++) {
			s+=str[i];
			int road = str[i] - 'a';
			if (cur.nodes[road] == null) return;
			cur = cur.nodes[road];
			if (cur.end) {
				path.addLast(s);
				f(str, i + 1, path, head, ans);
				path.pollLast();
			}
		}
	}

	static class Node {
		public boolean end;
		public Node[] nodes;

		public Node() {
			nodes = new Node[26];
			end = false;
		}
	}
}
