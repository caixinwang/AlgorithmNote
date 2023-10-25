package Leetcode.AllQuestions;

import Leetcode.LeetClass.TreeNode;

public class Leetcode_0222_CountCompleteTreeNodes {
    public static int countNodes(TreeNode root) {
        if(root==null) return 0;
        int level=0;
        for(TreeNode cur=root;cur!=null;cur=cur.left) level++;//统计层数，三个结点就是2层
        int l=(1<<(level-1));//最少的结点，就是最后一层只有一个结点
        int r=(1<<level)-1;//最多的结点，最后一层放满了
        while(l<=r){
            int mid=l+(r-l>>1);
            if(existNode(root,mid)){
                l=mid+1;
            }else {
                r=mid-1;
            }
        }
        return r;
    }

    /**
     * 第四号结点 100 ,去掉最左侧的1，剩下00，那么就是往左走两次。最左侧的1是 2<<2
     * @param k 以head为头的完全二叉树是否有第k个结点
     * @return -
     */
    public static Boolean existNode(TreeNode head,int k){
        if(k==1) return head!=null;
        int cnt=0;
        for(int cur=k;cur>0;cur>>>=1)cnt++;//算出总共有几位二进制位，4=100就是三位。第n位就是,1<<(n-1)
        cnt--;//去掉最高位
        for(;cnt>=1;cnt--){
            int mask=1<<(cnt-1);//1000 0100 0010 0001
            if((k&mask)==mask){
                if(head.right==null) return false;
                head=head.right;
            }else {
                if(head.left==null) return false;
                head=head.left;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        TreeNode head=new TreeNode(1);
        head.left=new TreeNode(2);
        head.right=new TreeNode(3);
        head.left.left=new TreeNode(4);
        head.left.right=new TreeNode(5);
        head.right.left=new TreeNode(6);
        System.out.println(existNode(head,1));
        System.out.println(existNode(head,2));
        System.out.println(existNode(head,3));
        System.out.println(existNode(head,4));
        System.out.println(existNode(head,5));
        System.out.println(existNode(head,6));
        System.out.println(existNode(head,7));
        System.out.println("=========");
        System.out.println(countNodes(head));
    }


}
