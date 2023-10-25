package Leetcode.AllQuestions;

public class Leetcode_0538_ConvertBSTToGreaterTree {
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
    public TreeNode convertBST(TreeNode root) {//morris原本是左中右，现在改成右中左,因为要求后缀累加和
        TreeNode cur=root,ml=null;
        int sum=0;
        while(cur!=null){
            ml=cur.right;//改成右中左，那么就是要来到右树的最左
            if (ml!=null){//这段逻辑是遍历右树
                for (;ml.left!=null&&ml.left!=cur;ml=ml.left);//走到右树的最左
                if (ml.left!=cur){//第一次来到cur结点
                    ml.left=cur;
                    cur=cur.right;//先遍历右树
                    continue;
                }else {
                    ml.left=null;
                }
            }
            sum+=cur.val;//这两句是改变的中序的打印时机，也就是修改时机
            cur.val=sum;
            cur=cur.left;//最后遍历左树
        }
        return root;
    }
    int sum=0;

    public TreeNode convertBST2(TreeNode root) {//右中左,求后缀累加和
        if (root==null) return null;
        convertBST2(root.right);
        sum+=root.val;
        root.val=sum;
        convertBST2(root.left);
        return root;
    }

}
