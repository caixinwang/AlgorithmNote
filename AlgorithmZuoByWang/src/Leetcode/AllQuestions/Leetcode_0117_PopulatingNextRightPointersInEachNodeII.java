package Leetcode.AllQuestions;

import java.util.LinkedList;
import java.util.Queue;

public class Leetcode_0117_PopulatingNextRightPointersInEachNodeII {

    class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right, Node _next) {
            val = _val;
            left = _left;
            right = _right;
            next = _next;
        }
    };
    public Node connect(Node root) {//一层一层遍历
        if(root==null) return null;
        Queue<Node> queue=new LinkedList<>();
        queue.add(root);
        while(!queue.isEmpty()){
            int size=queue.size();
            Node pre=null;
            for(int i=0;i<size;i++){
                Node poll=queue.poll();
                if(poll.left!=null) queue.add(poll.left);
                if(poll.right!=null) queue.add(poll.right);
                if(pre!=null) pre.next=poll;
                pre=poll;
            }
        }
        return root;
    }
}
