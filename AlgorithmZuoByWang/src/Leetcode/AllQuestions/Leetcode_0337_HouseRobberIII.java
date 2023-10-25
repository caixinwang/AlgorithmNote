package Leetcode.AllQuestions;

public class Leetcode_0337_HouseRobberIII {
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


    public int rob(TreeNode root) {
        return f(root).max;
    }

    class Info{
        int no;//根节点被抢了的最大值
        int yes;//根节点没有被抢的最大值
        int max;

        public Info(int no, int yes) {
            this.no = no;
            this.yes = yes;
            max=Math.max(no,yes);
        }
    }

    public Info f(TreeNode head){
        if (head==null) return new Info(0,0);
        Info left=f(head.left);
        Info right=f(head.right);
        int yes=head.val+left.no+right.no;
        int no=left.max+right.max;
        return new Info(no,yes);
    }

}
