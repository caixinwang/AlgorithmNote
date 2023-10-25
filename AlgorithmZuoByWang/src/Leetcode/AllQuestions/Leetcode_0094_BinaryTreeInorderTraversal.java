package Leetcode.AllQuestions;

import java.util.ArrayList;
import java.util.List;

public class Leetcode_0094_BinaryTreeInorderTraversal {

	public static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
	}

	public static List<Integer> inorderTraversal(TreeNode root) {
		List<Integer> ans=new ArrayList<>();
		if (root==null) return ans;
		TreeNode cur=root,mr=null;
		while(cur!=null){
			mr=cur.left;
			if (mr!=null){
				for (;mr.right!=null&&mr.right!=cur;mr=mr.right) ;
				if (mr.right==null){//说明此时左子树没有走过
					mr.right=cur;
					cur=cur.left;
					continue;
				}else {//说明左子树走过了
					mr.right=null;//我知道我要往右走了，之前改了你的信息给我判断提供方便，现在改回来
				}
			}
			ans.add(cur.val);//上面那一大坨if做的事情就是处理左树
			cur=cur.right;
		}
		return ans;
	}

}
