package Leetcode.SwordToOffer;

public class SwordToOffer_036_TreeToDoublyList {

    class Node {
        public int val;
        public Node left;
        public Node right;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val,Node _left,Node _right) {
            val = _val;
            left = _left;
            right = _right;
        }
    }
    public Node treeToDoublyList(Node root) {
        if(root==null) return null;
        Node cur=root,mr=null,pre=null;
        while(cur!=null){
            mr=cur.left;
            if (mr!=null){//处理左子树
                for (;mr.right!=null&&mr.right!=cur;mr=mr.right);
                if (mr.right==null){//第一次来到
                    mr.right=cur;
                    cur=cur.left;
                    continue;
                }else {
                    mr.right=null;
                }
            }
            if (pre==null) root=cur;//中序第一个打印的结点不是root,需要换根
            if (pre!=null) pre.left=cur;
            pre=cur;
            cur=cur.right;
        }
        pre.left=null;
        for (cur=root,pre=null;cur!=null;pre=cur,cur=cur.right){
            cur.right=cur.left;
            cur.left=pre;
        }
        pre.right=root;
        root.left=pre;
        return root;
    }
}
