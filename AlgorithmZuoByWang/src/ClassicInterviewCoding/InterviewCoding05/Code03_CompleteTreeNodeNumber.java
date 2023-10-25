package ClassicInterviewCoding.InterviewCoding05;

public class Code03_CompleteTreeNodeNumber {
	//满二叉树计算公式，如果高度为n（单个结点高度为1），(1<<n)-1

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int data) {
			this.value = data;
		}
	}

	// 请保证head为头的树，是完全二叉树
	public static int nodeNum(Node head) {
		if (head == null) {
			return 0;
		}
		return bs(head, 1, mostLeftLevel(head, 1));
	}

	// node在第level层，h是总的深度（h永远不变，全局变量
	// 以node为头的完全二叉树，节点个数是多少
	public static int bs(Node node, int level, int h) {
		if (level==h) return 1;//在最后一层，说明是叶子结点
		int p = mostLeftLevel(node.right, level+1);
		if (p==h){//右子树如果到了h，说明左子树一定满了，左子树是h-level层的满二叉树
			return (1<<h-level)+bs(node.right,level+1,h);
		}else {//右子树如果是h-1，那么说明右子树满了
			return (1<<h-level-1)+bs(node.left,level+1,h);
		}
	}

	// 如果node在第level层，
	// 求以node为头的子树，最大深度是多少
	// node为头的子树，一定是完全二叉树
	public static int mostLeftLevel(Node node, int level) {
//		while(node!=null&&node.left!=null){ //这种不行，因为node为空的时候需要返回level-1，因为不能给空节点算高度
//			node=node.left;
//			level++;
//		}
//		return level;
		while (node != null) {
			level++;
			node = node.left;
		}
		return level - 1;
	}

	public static int nodeNum2(Node head) {
		if (head == null) {
			return 0;
		}
		int left= leftHeight(head);
		int right= rightHeight(head);
		if (left==right){//一直往左走的高度等于一直往右走的高度，说明整棵树是满二叉树
			return (1<<left)-1;
		}else {//left>right,左子树的满二叉树，总的结点数为左子树(1<<left-1)-1加上根结点1加上右侧子树的个数递归
			return (1<<left-1)+nodeNum2(head.right);
		}
	}

	public static int leftHeight(Node head){//得到一直往左走的最大高度
		if (head==null) return 0;
		int height=0;
		while(head!=null){//如果head是空，空树高度为0
			height++;
			head=head.left;
		}
		return height;
	}

	public static int rightHeight(Node head){//得到一直往右走的最大高度
		if (head==null) return 0;
		int height=0;
		while(head!=null){//如果head是空，空树高度为0
			height++;
			head=head.right;
		}
		return height;
	}

	public static void main(String[] args) {
		Node head = new Node(1);
		head.left = new Node(2);
		head.right = new Node(3);
		head.left.left = new Node(4);
		head.left.right = new Node(5);
		head.right.left = new Node(6);
		System.out.println(nodeNum(head));
		System.out.println(nodeNum2(head));

	}

}
