package Leetcode.AllQuestions;
import Leetcode.LeetClass.TreeNode;

public class Leetcode_0114_FlattenBinaryTreeToLinkedList {

    //其实就是在前序遍历中，把打印换为了pre的操作。然后最后串一下
    public void flatten(TreeNode root) {
        if (root==null) return;
        TreeNode cur=root,mr=null,pre=null;
        while(cur!=null){
            mr=cur.left;
            if (mr!=null){//有左树
                for (;mr.right!=null&&mr.right!=cur;mr=mr.right);
                if (mr.right!=cur){//第一次来到一个这个结点，去左树
                    if (pre!=null) pre.left=cur;
                    pre=cur;
                    mr.right=cur;
                    cur=cur.left;
                    continue;
                }else {//第二次来到这个结点,把指针改回去
                    mr.right=null;
                }
            }else {//没有左树的结点，直接先序遍历。这里打印行为换为串结点。只能用左指针，因为右指针是用来给morris遍历指路的
                if (pre!=null) pre.left=cur;
                pre=cur;
            }
            cur=cur.right;
        }
        for (cur=root;cur!=null;cur=cur.right){
            cur.right=cur.left;
            cur.left=null;
        }
    }
}
