package BasicLearning.Class04_Tree;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Code03_TreeMaxWidth {

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int data) {
			this.value = data;
		}
	}

	/**
	 * 使用hashmap来实现最大宽度的求解
	 * @param head:根节点
	 * @return :返回树所有层中结点数最多的层有多少个结点
	 */
	public static int getMaxWidth(Node head) {
		if (head==null) return 0;
		int max=-1,curLay=1,curNum=0;
		Queue<Node> queue=new LinkedList<>();
		queue.add(head);
		HashMap<Node,Integer> hashMap=new HashMap<>();
		hashMap.put(head,1);
		while(!queue.isEmpty()){
			head=queue.poll();
			if (hashMap.get(head)!=curLay){//这段代码一定要在递增节点数前面，因为要先确定当前结点的层数。后面存进map才不会错！
				max=Math.max(max,curNum);
				curLay++;
				curNum=0;
			}
			curNum++;
			if (head.left!=null){
				queue.add(head.left);
				hashMap.put(head.left,curLay+1);
			}
			if (head.right!=null){
				queue.add(head.right);
				hashMap.put(head.right,curLay+1);
			}
		}
		max=Math.max(max,curNum);
		return max;
	}

	/**
	 * 不使用哈希表实现最大宽度的求解，整体思路还是层序遍历。
	 * 思路：求解这个问题需要知道本层最后一个节点是谁，以及到达最后一个结点在本层累计的结点个数。现在我们知道根节点是第一层
	 * 的最后一个结点，如果我们知道中间某一层的最后一个结点，我们能不能知道下一层的结点方便下次迭代呢？答案是肯定的，下一层的最后一个结点
	 * 可以随着上一层一直更新，知道上一层结束，下一层的最后一个结点就成了真正的最后一个结点了。
	 * @param head
	 * @return
	 */
	public static int getMaxWidth2(Node head){
		if (head==null) return 0;
		int max =-1,curNum=0;
		Node cur=null;
		Node curLevel=head;//记录当前层的最后一个，一开始默认是头节点，因为头节点很自然的是第一层的最后一个结点
		Node nextLevel=null;//记录下一层的最后一个节点
		Queue<Node>queue=new LinkedList<>();
		queue.add(head);
		while(!queue.isEmpty()){
			cur=queue.poll();//每次弹出一个节点，把他的左右孩子入队（如果有的话）
			if (cur.left!=null){
				queue.add(cur.left);
				nextLevel= cur.left;//nextLevel的值就是最新入队的结点的值
			}
			if (cur.right!=null){
				queue.add(cur.right);
				nextLevel=cur.right;
			}
			curNum++;//把打印行为替代为统计当前层的节点数
			if (cur==curLevel){//说明已经到了当前层的最后一个结点了,结算max，并且迭代curLevel和nextLevel
				max=Math.max(max,curNum);
				curNum=0;//归零
				curLevel=nextLevel;
			}
		}
		return max;
	}

	// for test
	public static Node generateRandomBST(int maxLevel, int maxValue) {
		return generate(1, maxLevel, maxValue);
	}

	// for test
	public static Node generate(int level, int maxLevel, int maxValue) {
		if (level > maxLevel || Math.random() < 0.5) {//一半的概率产生以head为头的子树
			return null;
		}
		Node head = new Node((int) (Math.random() * maxValue));
		head.left = generate(level + 1, maxLevel, maxValue);
		head.right = generate(level + 1, maxLevel, maxValue);
		return head;
	}


	public static void main(String[] args) {
		int maxLevel = 4;
		int maxValue = 100;
		int testTimes = 100000;
		for (int i = 0; i < testTimes; i++) {
			Node head = generateRandomBST(maxLevel, maxValue);
			if (getMaxWidth(head) != getMaxWidth2(head)) {
				System.out.println("Oops!");
			}
		}
		System.out.println("finish!");
	}

}
