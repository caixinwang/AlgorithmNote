package Leetcode.AllQuestions;

import java.util.ArrayList;
import java.util.List;

public class Leetcode_0095_UniqueBinarySearchTreesII {
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

    public List<TreeNode> generateTrees(int n) {
        return f(1,n);
    }

    public List<TreeNode> f(int start,int end){
        List<TreeNode> ans=new ArrayList<>();
        if (start>end){
            ans.add(null);
            return ans;
        }
        for (int i=start;i<=end;i++){//枚举头部
            List<TreeNode> ltrees = f(start, i - 1);
            List<TreeNode> rtrees = f(i+1,end);
            for (TreeNode ltree : ltrees) {//枚举左右树
                for (TreeNode rtree : rtrees) {
                    TreeNode head=new TreeNode(i);
                    head.left=ltree;
                    head.right=rtree;
                    ans.add(head);
                }
            }
        }
        return ans;
    }

}

