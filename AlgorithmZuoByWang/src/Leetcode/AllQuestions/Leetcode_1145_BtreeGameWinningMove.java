package Leetcode.AllQuestions;

import Leetcode.LeetClass.TreeNode;

public class Leetcode_1145_BtreeGameWinningMove {
    int lsz,rsz;
    public boolean btreeGameWinningMove(TreeNode root, int n, int x) {
        dfs(root,x);
        return Math.max(Math.max(lsz,rsz),n-1-lsz-rsz)*2>n;
    }

    //贪心，直接贪到x的家门口，x的家门口有三个，x的左子树、右子树、父节点。我们分别计算出这三个的大小，选出最大的那个
    //如果选出的最大的那个的两倍大于n，那么二号玩家就可以获胜！
    //拓展：如果1号玩家要稳赢：选在树的重心！
    //技巧：后序遍历求树的结点个数的基础上，假设一个if，抓住x为头的数的结点个数，这个是我们想要的。
    public int dfs(TreeNode root,int x){//去掉root的if，dfs就是求一棵树的结点个数，加上了之后就是到x才结算
        if(root==null) return 0;
        int p1=dfs(root.left,x);
        int p2=dfs(root.right,x);
        if(root.val==x){
            lsz=p1;
            rsz=p2;
        }
        return p1+p2+1;
    }
}
