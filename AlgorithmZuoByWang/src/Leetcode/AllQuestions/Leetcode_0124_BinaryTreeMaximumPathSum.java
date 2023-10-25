package Leetcode.AllQuestions;

public class Leetcode_0124_BinaryTreeMaximumPathSum {
	//类似于子数组累加和的做法不能用在这题，因为这题的路线会拐弯
	public class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		TreeNode() {}
		TreeNode(int val) { this.val = val; }
		TreeNode(int val, TreeNode left, TreeNode right) {
			this.val = val;
			this.left = left;
			this.right = right;
		}
	}

	static class Info{
		public int no;//和头节点无关
		public int gol;//和头节点有关，只往左边窜
		public int gor;//和头节点有关，只往右边窜
		public int golr;//和头节点有关，同时往左右两边窜
		public int max;

		public Info(int no, int gol, int gor, int golr) {
			this.no = no;
			this.gol = gol;
			this.gor = gor;
			this.golr = golr;
			max=Math.max(Math.max(Math.max(no,gol),gor),golr);
		}

	}

	public static int maxPathSum(TreeNode root) {
		return f(root).max;
	}

	public static Info f(TreeNode head){
		if (head.left==null&&head.right==null) return new Info(Integer.MIN_VALUE,head.val,head.val,head.val);
		int no=Integer.MIN_VALUE,gol=head.val,gor=head.val,golr=head.val;
		Info l = null,r=null;
		if (head.left!=null&&head.right==null){
			l=f(head.left);
			no=l.max;
			gol = Math.max(gol, head.val+Math.max(l.gol, l.gor));
			gor=head.val;
			golr=gol;
		}else if (head.left==null&&head.right!=null){
			r=f(head.right);
			no=r.max;
			gor = Math.max(gor, head.val+Math.max(r.gol,r.gor));
			gol=head.val;
			golr=gor;
		}else {
			l=f(head.left);
			r=f(head.right);
			no=Math.max(l.max, r.max);
			gol = Math.max(gol, head.val+Math.max(l.gol, l.gor));
			gor = Math.max(gor, head.val+Math.max(r.gol,r.gor));
			golr = Math.max(golr, head.val+Math.max(l.gol, l.gor))+Math.max(0,Math.max(r.gol,r.gor));
		}
		return new Info(no,gol,gor,golr);
	}

	public static int maxPathSum2(TreeNode root) {
		return f2(root).max;
	}

	public static Info f2(TreeNode head){
		if (head.left==null&&head.right==null) return new Info(Integer.MIN_VALUE,head.val,head.val,head.val);
		int no=Integer.MIN_VALUE,gol=head.val,gor=head.val,golr=head.val;
		Info l=null ,r=null;
		if (head.left!=null) l=f2(head.left);
		if (head.right!=null) r=f2(head.right);
		no=Math.max(l!=null?l.max:Integer.MIN_VALUE,r!=null?r.max:Integer.MIN_VALUE);
		gol = Math.max(gol, head.val+(l!=null?Math.max(l.gol,l.gor):0));
		gor = Math.max(gor, head.val+(r!=null?Math.max(r.gol,r.gor):0));
		golr = Math.max(golr, head.val+(r!=null?Math.max(r.gol,r.gor):0)+(l!=null?Math.max(l.gol,l.gor):0));
		return new Info(no,gol,gor,golr);
	}

	static class Info2{//省信息，你找左右孩子要的信息只要有这两个，头结点的no，gol，gor，golr，max都可以求出来。
		public int go;//和头节点有关，只能往左或者往右窜的最大值
		public int max;//全部情况的最大
		public Info2(int go,int max) {
			this.go=go;
			this.max=max;
		}
	}

	public static int maxPathSum3(TreeNode root) {
		return f3(root).max;
	}

	public static Info2 f3(TreeNode head){
		if (head.left==null&&head.right==null) return new Info2(head.val,head.val);
		Info2 l=head.left!=null?f3(head.left):null;
		Info2 r=head.right!=null?f3(head.right):null;
		int no=Math.max(l!=null?l.max:Integer.MIN_VALUE,r!=null?r.max:Integer.MIN_VALUE);
		int go= head.val;//默认值只有自己
		go = Math.max(go,head.val+Math.max((l!=null?l.go:Integer.MIN_VALUE),(r!=null?r.go:Integer.MIN_VALUE)));
		int glr=head.val;
		glr = Math.max(glr,head.val+(l!=null?l.go:0)+(r!=null?r.go:0));
		return new Info2(go,Math.max(Math.max(no,go),glr));
	}

	int MIN=1<<31,MAX=MIN-1;

	public int maxPathSum4(TreeNode root) {
		return f4(root).max;
	}

	public Info2 f4(TreeNode root){
		if(root==null) return null;
		if(root.left==null&&root.right==null) return new Info2(root.val,root.val);
		Info2 l=f4(root.left);
		Info2 r=f4(root.right);
		if(l==null){
			return new Info2(root.val+max(0,r.go),max(r.max,root.val+max(0,r.go)));
		}
		if(r==null){
			return new Info2(root.val+max(0,l.go),max(l.max,root.val+max(0,l.go)));
		}
		int go=root.val+max(0,max(l.go,r.go));//有利可图我才走下去
		int max=Math.max(Math.max(l.max,r.max),root.val+Math.max(0,l.go)+Math.max(0,r.go));
		return new Info2(go,max);
	}

	public Info2 f5(TreeNode root){//这个写法和f4等价
		if(root==null) return new Info2(0,MIN);//空节点不能参与max的构成
		Info2 l=f5(root.left);
		Info2 r=f5(root.right);
		int go=root.val+max(0,max(l.go,r.go));
		int max=Math.max(Math.max(l.max,r.max),root.val+Math.max(0,l.go)+Math.max(0,r.go));
		return new Info2(go,max);
	}

	public int max(int a,int b){return a>b?a:b;}


}
