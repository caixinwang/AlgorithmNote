package Leetcode.AllQuestions;

public class Leetcode_0426_treeToDoublyList {
    static class  Node {
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
    };
    public static Node treeToDoublyList(Node root) {
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
            if (pre==null) root=cur;//中序第一个打印的结点不是root,需要换头
            if (pre!=null) pre.left=cur;
            pre=cur;
            cur=cur.right;
        }
        pre.left=null;//出来之后pre是最后一个结点，最后一个结点没有后继，所以需要置为空
        for (cur=root,pre=null;cur!=null;pre=cur,cur=cur.right){
          cur.right=cur.left;
          cur.left=pre;
        }
        pre.right=root;
        root.left=pre;
        return root;
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.left = new Node(2);
//        head.right = new Node(3);
//        head.left.left = new Node(4);
//        head.left.right = new Node(5);
//        head.right.left = new Node(6);
//        head.right.right = new Node(7);
//        for (Node cur=treeToDoublyList(head);cur!=null;cur=cur.left){
//            System.out.println(cur.val);
//        }
    }
}
