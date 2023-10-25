package Leetcode.AllQuestions;

/*
 * 提交时把类名、构造函数名从Problem_0208_Trie改为Trie
 * 
 * */
public class Leetcode_0208_Trie {
	class Trie {
		Node head;
		public Trie() {
			head=new Node();
		}

		public void insert(String word) {
			if (word==null||word.length()==0) return;
			char[] str = word.toCharArray();
			Node cur=head;
			for (char c:str){
				int road=c-'a';
				if (cur.nexts[road]==null) cur.nexts[road]=new Node();//无路新建，有路复用
				cur=cur.nexts[road];
			}
			cur.end=true;
		}

		public boolean search(String word) {
			char[] str = word.toCharArray();
			Node cur=head;
			for (char c:str){
				int road=c-'a';
				if (cur.nexts[road]==null) return false;
				cur=cur.nexts[road];
			}
			return cur.end;
		}

		public boolean startsWith(String prefix) {
			char[] str = prefix.toCharArray();
			Node cur=head;
			for (char c:str){
				int road=c-'a';
				if (cur.nexts[road]==null) return false;
				cur=cur.nexts[road];
			}
			return true;
		}

		class Node{
			Node[] nexts;
			boolean end;

			public Node() {
				nexts=new Node[26];
				end=false;
			}
		}

	}
}
