package Leetcode.AllQuestions;

public class Leetcode_0687_longestUnivaluePath {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public int longestUnivaluePath(TreeNode root) {
        if (root==null) return 0;
        Info f = f(root);
        return f.max-1;
    }

    public Info f(TreeNode x) {
        if (x == null) return new Info(0, 0, 0,0);
        Info left = f(x.left);
        Info right = f(x.right);
        int no=0,gol=1,gor=1,golr=1;
        no=Math.max(left.max, right.max);
        if (x.left!=null&&x.left.val==x.val) gol+=Math.max(left.gol, left.gor);
        if (x.right!=null&&x.right.val==x.val)gor+=Math.max(right.gol, right.gor);
        if (x.left!=null&&x.left.val==x.val&&x.right!=null&&x.right.val==x.val) {
            golr+=Math.max(left.gol, left.gor)+Math.max(right.gol, right.gor);
        }
        return new Info(no,gol,gor,golr);
    }

    static class Info {
        public int no;//与头无关,经过的最多结点数

        public int gol;//头有关，只往左下角扎
        public int gor;//头有关，只往右下角扎
        public int golr;//头有关，左右两边都扎

        public int max;//所有中的最大

        public Info(int no, int gol, int gor, int golr) {
            this.no = no;
            this.gol = gol;
            this.gor = gor;
            this.golr = golr;
            max=Math.max(no,Math.max(gol,Math.max(gor,golr)));
        }
    }

}
