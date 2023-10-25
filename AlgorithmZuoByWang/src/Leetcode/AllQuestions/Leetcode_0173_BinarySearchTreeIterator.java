package Leetcode.AllQuestions;

import java.util.ArrayList;
import java.util.Stack;

public class Leetcode_0173_BinarySearchTreeIterator {

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
    class BSTIterator {
        TreeNode cur;
        Stack<TreeNode> stack;
        public BSTIterator(TreeNode root) {
            cur=root;
            stack=new Stack<>();
        }

        public int next() {
            int ans=0;
            while(cur!=null){
                stack.push(cur);
                cur=cur.left;
            }
            cur=stack.pop();
            ans=cur.val;
            cur=cur.right;
            return ans;
        }

        public boolean hasNext() {
            return cur!=null||!stack.isEmpty();
        }
    }

    class BSTIterator2 {
        int index;
        ArrayList<Integer> arr;
        public BSTIterator2(TreeNode root) {
            arr=new ArrayList<>();
            index=0;
            int i=0;
            Stack<TreeNode> stack=new Stack<>();
            while(root!=null||!stack.isEmpty()){
                while(root!=null){
                    stack.push(root);
                    root=root.left;
                }
                root=stack.pop();
                arr.add(i++,root.val);
                root=root.right;
            }
        }

        public int next() {
            return arr.get(index++);
        }

        public boolean hasNext() {
            return index<arr.size();
        }
    }

}
