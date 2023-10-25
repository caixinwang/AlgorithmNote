package Leetcode.AllQuestions;

public class Leetcode_0543_DiameterOfBinaryTree {

	public static class TreeNode {
		public int val;
		public TreeNode left;
		public TreeNode right;
	}

	class Info{
		int no;//和根节点无关
		int gol;//和根节点有关，但是只往左边走
		int gor;//和根节点有关，但是只往右边走
		int golr;//和根节点有关，同时往左右两边走
		int max;
		public  Info(int n,int l,int r,int lr){
			no=n;
			gol=l;
			gor=r;
			golr=lr;
			max=Math.max(Math.max(Math.max(no,gol),gor),golr);
		}
	}

	public int diameterOfBinaryTree(TreeNode root) {
		return f(root).max-1;//结点个数-1
	}

	public Info f(TreeNode head){
		if (head==null) return new Info(0,0,0,0);
		Info l=f(head.left);
		Info r=f(head.right);
		int no=Math.max(l.max,r.max);
		int gol=1+Math.max(l.gol,l.gor);
		int gor=1+Math.max(r.gol,r.gor);
		int golr=1+Math.max(l.gol,l.gor)+Math.max(r.gol,r.gor);
		return new Info(no,gol,gor,golr);
	}

	class Info2{
		int no;//和根节点无关
		int yes;//和根节点有关，但是只是一条路窜到底，没有分叉。相当于上面情况的max{gol,gor}只往一边方向走的最大值
		int max;//全部情况的最大值
		public  Info2(int n,int y,int m){
			no=n;
			yes=y;
			max=m;
		}
	}

	public int diameterOfBinaryTree2(TreeNode root) {
		return g(root).max-1;//结点个数-1
	}

	public Info2 g(TreeNode head){
		if (head==null) return new Info2(0,0,0);
		Info2 l=g(head.left);
		Info2 r=g(head.right);
		int no=Math.max(l.max,r.max);
		int yes=1+Math.max(l.yes,r.yes);//看看往哪边单条路窜最有前途
		int max=Math.max(no,1+l.yes+r.yes);//同时往左右两边窜肯定是包含头部的时候的最优解
		return new Info2(no,yes,max);
	}


}
