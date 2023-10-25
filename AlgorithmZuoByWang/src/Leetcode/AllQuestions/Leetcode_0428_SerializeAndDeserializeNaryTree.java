package Leetcode.AllQuestions;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Leetcode_0428_SerializeAndDeserializeNaryTree {

	// 不要提交这个类
	public static class Node {
		public int val;
		public List<Node> children;

		public Node() {
			children = new ArrayList<>();
		}

		public Node(int _val) {
			val = _val;
			children = new ArrayList<>();
		}

		public Node(int _val, List<Node> _children) {
			val = _val;
			children = _children;
		}
	};

	// 提交下面这个类
	public static class Codec {
		// Encodes a tree to a single string.
		public String serialize(Node root) {
			if(root==null) return "#";
			StringBuilder sb=new StringBuilder();
			f(sb,root);
			return sb.toString();
		}

		private void f(StringBuilder sb,Node root){//调用的时候保证root不是空
			sb.append(root.val+",");
			if(!root.children.isEmpty()){
				sb.append("[,");//打印孩子之前把孩子们括在[]里面
				for(Node node:root.children) f(sb,node);
				sb.append("],");
			}
		}

		// Decodes your encoded data to tree.
		public Node deserialize(String data) {
			if(data.equals("#")) return null;//提前检查，g函数不处理空
			String[] splits=data.split(",");
			Queue<String> queue=new LinkedList<>();
			for(String s:splits) queue.add(s);//加到队列里面，仅使用队列即可
			return g(queue);
		}

		private Node g(Queue<String> queue){//调用的时候保证queue不是空
			Node root=new Node(Integer.parseInt(queue.poll()));//保证了不为空，直接创建
			root.children=new ArrayList<>();
			if(!queue.isEmpty()&&queue.peek().equals("[")){//里面的三个语句分别对应我们序列化时候的语句
				queue.poll();//弹出[
				while(!queue.isEmpty()&&!queue.peek().equals("]")){
					root.children.add(g(queue));
				}
				queue.poll();//弹出]
			}
			return root;
		}

	}


}
