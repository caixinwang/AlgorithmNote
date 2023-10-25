package ClassicInterviewCoding.InterviewCoding14;

import java.util.HashMap;
import java.util.Map;

public class Code03_BiggestBSTTopologyInTree {

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int data) {
			this.value = data;
		}
	}

	/**
	 * 拓扑结构的头节点不一定是head。但是我们要枚举所有的结点作为头所求出来的最大拓扑结构。
	 * 整体就是一个先序遍历，先求以head为头的最大拓扑结构，然后递归去，每个结点都要求一遍以自己为头的最大拓扑结构
	 * @param head 求以head为头的整棵树的二叉搜索树最大拓扑结构
	 * @return 返回最大拓扑结构有几个结点
	 */
	public static int bstTopoSize1(Node head) {
		if (head==null) return 0;
		int max=maxTopo(head,head);//先求以自己为头的最大拓扑，当前所在结点是head
		max = Math.max(max, bstTopoSize1(head.left));//不一定以自己为头的最大拓扑，左树
		max = Math.max(max, bstTopoSize1(head.right));//不一定以自己为头的最大拓扑，右树
		return max;
	}

	/**
	 * node先判断自己是不是属于head为头的二插搜索树，如果是就继续遍历自己的子树
	 * @param head 求必须以head为头的最大拓扑结构，head是一个固定参数，告诉下面遍历到的结点要和谁去判断二插搜索树结构
	 * @param node node是以head为头的时候求拓扑结构下面会经过的点，node是当前所在的点
	 * @return 必须以head为头的最大拓扑结构的结点个数
	 */
	public static int maxTopo(Node head, Node node) {
		if (head==null||node==null||!isBSTNode(head,node)) return 0;
		return 1+maxTopo(head,node.left)+maxTopo(head, node.right);//说明node结点是属于二插搜索树结构的，看看node的孩子的情况
	}


	/**
	 * maxTopo调用的时候保证了不会传空进来
	 * @param head 判断从head出发能不能以二叉搜索树的方式找到node
	 * @param node 是固定参数，head查找的目标
	 * @return 能就返回true，否则返回false
	 */
	public static boolean isBSTNode(Node head, Node node) {
		if (head==null) return false;//说明走到最后也没找到
		if (head==node) return true;//找到了
		if (head.value<node.value){//比你小，我到我的右树找你，因为我右树大
			return isBSTNode(head.right,node);
		}else {
			return isBSTNode(head.left,node);
		}
	}

	public static class Info {
		public int l;//左子树对某一个特定结点的拓扑贡献记录
		public int r;//右子树对某一个特定结点的拓扑贡献记录
		public int maxHead;//必须以当前结点开头的最大二叉搜索树拓扑结构
		public int maxAll;//不一定以当前结点开头的最大二叉搜索树拓扑结构

		public Info(int left, int right) {
			this.l = left;
			this.r = right;
		}

		public Info(int l, int r, int maxHead, int maxAll) {
			this.l = l;
			this.r = r;
			this.maxHead = maxHead;
			this.maxAll = maxAll;
		}
	}

	public static int bstTopoSize2(Node head) {
		if (head==null) return 0;
		HashMap<Node,Info> map=new HashMap<>();
		Info info = process(head,map);
		return info.maxAll;
	}

	/**
	 * 一开始得到的info1和info2里面的l和r都是针对左子树和右子树自己的头节点的，而不是针对当前的头节点head
	 * 所以不能直接通过info1和info2加工出我们要的。我们需要对head的左右子树进行调整，删除一些没有帮助的结点，
	 * 把数据更新对我们再去重新获取。map.get我们不能忽略，因为info1和info2可能不是我们后面重新获得的left和right
	 * 我们后面获得的left和right完全有可能是null，因为左右孩子可能直接不符合要求，直接被淘汰了。但是我们仍然需要
	 * info1和info2的maxall帮助我们得到正确的数据。所以这里我们搞了四个info
	 * @param head 以head为头的整棵树的最大拓扑结构
	 * @param map Info信息记录在里面
	 * @return ~
	 */
	public static Info process(Node head,HashMap<Node,Info> map){
		if (head==null) return null;//空树没有左右子树，所以不能返回info(0,0)
		Info info1 = process(head.left,map);//主要是要得到他们的maxAll
		Info info2 = process(head.right,map);
		modifyLeft(head,head.left,map);
		modifyRight(head,head.right,map);
		Info left = map.get(head.left);
		Info right = map.get(head.right);
		int l=left!=null?1+ left.l+left.r:0;
		int r=right!=null?1+ right.l+ info2.r:0;
		int max=l+r+1;
		int maxALl=Math.max(Math.max(max,info1!=null?info1.maxAll:0),info2!=null?info2.maxAll:0);
		map.put(head,new Info(l,r,max,maxALl));
		return map.get(head);
	}

	/**
	 * 技巧，把这个函数设置为有返回值的，找到第一个不和规定的结点之后算出需要删除多少，然后让上游的结点接到这个值
	 * 以方便修改自己的贡献记录。一路减的原理就是这个递归函数的返回值定义为了int
	 * @param head 针对head，Record全部变为指向head的含义
	 * @param cur 当前所在的结点
	 * @param map 映射
	 * @return ~
	 */
	public static int modifyLeft(Node head,Node cur,HashMap<Node,Info> map){
		if (cur==null||!map.containsKey(cur)) return 0;
		Info info = map.get(cur);
		if (cur.value>head.value){
			map.remove(cur);
			return 1+ info.l+info.r;//找到第一个不达标结点，知道要删除的量是多少，往上返回，一路删除
		}else {
			int minus=modifyLeft(head,cur.right,map);
			info.r-=minus;//说明我的r有这么多是不对新头负责的，减掉

			return minus;//一路更新，上面的结点都要减去这个量
		}
	}

	public static int modifyRight(Node head,Node cur,HashMap<Node,Info> map){
		if (cur==null||!map.containsKey(cur)) return 0;
		Info info = map.get(cur);
		if (cur.value<head.value){
			map.remove(cur);//这个结点没有用了，不对我的新头负责
			return 1+ info.l+info.r;//找到第一个不达标结点，知道要删除的量是多少，往上返回，一路删除
		}else {
			int minus=modifyRight(head,cur.left,map);
			info.l-=minus;//说明我的l有这么多是不对新头负责的，减掉
			return minus;//一路更新，上面的结点都要减去这个量
		}
	}


	public static class Record {//更加简洁，我们不用二叉树的递归套路。只是在我们的递归过程中维持好对当前结点有帮助的Record
		public int l;
		public int r;

		public Record(int left, int right) {
			this.l = left;
			this.r = right;
		}
	}

	public static int bstTopoSize3(Node head) {
		Map<Node, Record> map = new HashMap<Node, Record>();
		return posOrder(head, map);
	}

	/**
	 * 我们在遍历的过程中，遍历到每一个结点，我们都维持好它左右孩子对应Record都是指向自己的的。利用modifyMap
	 * @param h 头结点
	 * @param map Node和 Record的对应
	 * @return 返回h为头整棵树的最大二叉搜索树拓扑结构
	 */
	public static int posOrder(Node h, Map<Node, Record> map) {
		if (h == null) {
			return 0;
		}
		int ls = posOrder(h.left, map);
		int rs = posOrder(h.right, map);
		modifyMap(h.left, h.value, map, true);//左子树往下所有的Record给我维持好
		modifyMap(h.right, h.value, map, false);//右子树往下所有的Record给我维持好
		Record lr = map.get(h.left);//获取左子树对自己有帮助的数据，贡献记录已经全部转为对自己了
		Record rr = map.get(h.right);
		int lbst = lr == null ? 0 : lr.l + lr.r + 1;//此时的左子树的全部贡献记录，都是属于自己的
		int rbst = rr == null ? 0 : rr.l + rr.r + 1;
		map.put(h, new Record(lbst, rbst));//创建一条此时属于自己的贡献记录
		return Math.max(lbst + rbst + 1, Math.max(ls, rs));//决出自己为头和不以自己为头的最大
	}

	public static int modifyMap(Node n, int v, Map<Node, Record> m, boolean s) {
		if (n == null || (!m.containsKey(n))) {
			return 0;
		}
		Record r = m.get(n);
		if ((s && n.value > v) || ((!s) && n.value < v)) {
			m.remove(n);
			return r.l + r.r + 1;
		} else {
			int minus = modifyMap(s ? n.right : n.left, v, m, s);
			if (s) {
				r.r = r.r - minus;
			} else {
				r.l = r.l - minus;
			}
//			m.put(n, r);
			return minus;
		}
	}

	// for test -- print tree
	public static void printTree(Node head) {
		System.out.println("Binary Tree:");
		printInOrder(head, 0, "H", 17);
		System.out.println();
	}

	public static void printInOrder(Node head, int height, String to, int len) {
		if (head == null) {
			return;
		}
		printInOrder(head.right, height + 1, "v", len);
		String val = to + head.value + to;
		int lenM = val.length();
		int lenL = (len - lenM) / 2;
		int lenR = len - lenM - lenL;
		val = getSpace(lenL) + val + getSpace(lenR);
		System.out.println(getSpace(height * len) + val);
		printInOrder(head.left, height + 1, "^", len);
	}

	public static String getSpace(int num) {
		String space = " ";
		StringBuffer buf = new StringBuffer("");
		for (int i = 0; i < num; i++) {
			buf.append(space);
		}
		return buf.toString();
	}

	public static void main(String[] args) {
		Node head = new Node(6);
		head.left = new Node(1);
		head.left.left = new Node(0);
		head.left.right = new Node(3);
		head.right = new Node(12);
		head.right.left = new Node(10);
		head.right.left.left = new Node(4);
		head.right.left.left.left = new Node(2);
		head.right.left.left.right = new Node(5);
		head.right.left.right = new Node(14);
		head.right.left.right.left = new Node(11);
		head.right.left.right.right = new Node(15);
		head.right.right = new Node(13);
		head.right.right.left = new Node(20);
		head.right.right.right = new Node(16);
		printTree(head);

		System.out.println(bstTopoSize1(head));
		System.out.println(bstTopoSize2(head));

	}

}
