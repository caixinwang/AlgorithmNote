package Leetcode.AllQuestions;

import Leetcode.LeetClass.TreeNode;

import java.util.*;

public class Leetcode_0257_BinaryTreePaths {

    public List<String> binaryTreePaths(TreeNode root) {
        List<String> ans = new LinkedList<>();
        LinkedList<String> path = new LinkedList<>();
        dfs(root, path, ans);
        return ans;
    }

    //这种题目都是在先序、中序、后序的基础上，前后加一些条件收集题目所需要的答案
    public void dfs(TreeNode root, LinkedList<String> path, List<String> ans) {
        if (root == null) return;
        if (root.left == null && root.right == null) {//在先序遍历的基础上增加了添加答案的时机
            ans.add(pathToString(path) + (path.size() != 0 ? "->" + root.val : "" + root.val));//别忘了把当前结点加上
            return;
        }
        path.addLast(path.size() != 0 ? "->" + root.val : "" + root.val);
        dfs(root.left, path, ans);
        dfs(root.right, path, ans);
        path.pollLast();//恢复现场
    }

    public String pathToString(LinkedList<String> path) {
        StringBuilder sb = new StringBuilder();
        for (String s : path) {
            sb.append(s);
        }
        return sb.toString();
    }
}
