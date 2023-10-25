package Leetcode.AllQuestions;

import Leetcode.LeetClass.TreeNode;

public class Leetcode_0235_LowestCommonAncestorOfABinarySearchTree {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        return f(root,p,q).ans;
    }

    class Info{
        boolean findp;
        boolean findq;
        TreeNode ans;
        public Info(boolean p,boolean q,TreeNode a){
            findp=p;
            findq=q;
            ans=a;
        }
    }

    public Info f(TreeNode head,TreeNode p,TreeNode q){
        if(head==null) return new Info(false,false,null);
        Info left=f(head.left,p,q);
        Info right=f(head.right,p,q);
        boolean findp=left.findp||right.findp||head==p;
        boolean findq=left.findq||right.findq||head==q;
        TreeNode ans=left.ans!=null?left.ans:right.ans;
        if(findp&&findq&&left.ans==null&&right.ans==null){//如果左右孩子已经有ans了，那么就不需要更新答案了。
            ans=head;
        }
        return new Info(findp,findq,ans);
    }

    public TreeNode lowestCommonAncestor2(TreeNode root, TreeNode p, TreeNode q) {//这种做法使用二插搜索树的性质
        if(p.val>q.val) return lowestCommonAncestor(root,q,p);//让p做小的那个结点
        while (root!=null){
            if(root.val>=p.val&&root.val<=q.val) return root;
            else if (root.val>q.val) root=root.left;
            else root=root.right;
        }
        return null;
    }

    public TreeNode lowestCommonAncestor3(TreeNode root, TreeNode p, TreeNode q) {//方法2的递归写法
        if (root.val>Math.max(p.val,q.val)) return lowestCommonAncestor3(root.left,p,q);
        if (root.val<Math.min(p.val,q.val)) return lowestCommonAncestor3(root.right,p,q);
        return root;
    }
}
