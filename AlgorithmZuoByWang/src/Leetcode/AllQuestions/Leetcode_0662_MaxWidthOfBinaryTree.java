package Leetcode.AllQuestions;

import Leetcode.LeetClass.TreeNode;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Leetcode_0662_MaxWidthOfBinaryTree {
    public int widthOfBinaryTree(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        Queue<Integer> queue2 = new LinkedList<>();//存放下标
        if (root != null) {
            queue.add(root);
            queue2.add(1);
        }
        int ans = 0;
        while (!queue.isEmpty()) {
            int size = queue.size(), fidx = 0, lidx = 0;
            for (int i = 0; i < size; i++) {
                TreeNode cur = queue.poll();
                lidx = queue2.poll();//记录最晚出现的结点
                if (fidx == 0) fidx =lidx;//记录最早出现的结点
                if (cur.left != null) {
                    queue.add(cur.left);
                    queue2.add(lidx << 1);
                }
                if (cur.right != null) {
                    queue.add(cur.right);
                    queue2.add(lidx << 1 | 1);
                }
            }
            ans = Math.max(ans, lidx-fidx+1);
        }
        return ans;
    }

    HashMap<Integer,Integer> map =new HashMap<>();//<层数，第一个结点的下标>
    public int widthOfBinaryTree2(TreeNode root) {
        return dfs(root,1,1);
    }

    //为什么可以用dfs呢？本质上就是一个先序遍历，先序遍历会先把左边界全部放到map里面，放完之后以后不再改变了
    //然后其它结点带着index去先序遍历，遍历到每一个结点都算一个宽度然后返回，最后抓到的就是全局的最大宽度
    public int dfs(TreeNode head,int depth,int index){
        if (head==null) return 0;
        if (!map.containsKey(depth)) map.put(depth,index);
        int p1=index- map.get(depth)+1;
        int p2=Math.max(dfs(head.left,depth+1,index<<1),dfs(head.right,depth+1,index<<1|1));
        return Math.max(p1,p2);
    }


}
