package ClassicInterviewCoding.InterviewCoding04;

import java.util.TreeMap;

public class Code01_GetFolderTree {
	static class Node{
		public TreeMap<String,Node> nexts=new TreeMap<>();//指路
		public String value;//走哪条路下来这个结点的就记录一下

		public Node(String value) {
			this.value = value;
		}
	}

	static class TriTree{
		public Node head;

		public TriTree(Node head) {
			this.head = head;
		}

		public void add(String s){//s:"a\b\cd"
			if (s==null||s.length()==0) return;
			String[] split = s.split("\\\\");
			add(split);
		}
		private void add(String[] s){//s:["a","b","cd"]
			Node cur=head;
			for (int i = 0; i < s.length; i++) {
				if (cur.nexts.containsKey(s[i])){//有路
					cur=cur.nexts.get(s[i]);//往下走
				}else {//没有就新建,然后继续往下走
					cur.nexts.put(s[i],new Node(s[i]));
					cur=cur.nexts.get(s[i]);//往下走
				}
			}
		}

		public void print(){
			print(head,0);
		}

		private void print(Node head,int level){//打印以head为头的子树，子树的深度在level
			if (level!=0){//根节点不参与
				printSpace(level);
				System.out.println(head.value);
			}
			for (Node value : head.nexts.values()) {
				print(value,level+1);
			}
		}

		private void printSpace(int level){
			for (int i = 0; i < level-1; i++) {//注意这个-1，只有2层及以上才会打印空格
				System.out.print("  ");
			}
		}
	}

	public static void main(String[] args) {
		TriTree triTree=new TriTree(new Node(""));
		String[] strings=new String[]{"a\\b\\dbv","a\\c\\de","a\\c\\da\\a","bcx\\a\\b\\edf","bax\\a"};
		for (int i = 0; i < strings.length; i++) {
			triTree.add(strings[i]);
		}
		triTree.print();
	}
}
