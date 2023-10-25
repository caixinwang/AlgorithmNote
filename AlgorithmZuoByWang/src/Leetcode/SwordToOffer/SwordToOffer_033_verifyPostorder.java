package Leetcode.SwordToOffer;

public class SwordToOffer_033_verifyPostorder {

    //分治法，左子树和右子树都是二叉搜索树，并且根节点大于左子树，小于右子树。由于是后序，所以根节点在数组最后。
    public boolean verifyPostorder(int[] postorder) {//方法1-f1、f2 递归分治，最坏情况下平方阶复杂度。
        return f(postorder,0,postorder.length-1);
    }

    private boolean f(int[] postorder, int s, int e) {
        if (s>=e) return true;//在这里控制，最后一行就不需要判断条件了
        int root=postorder[e];
        int l=s,r=e-1;
        while(l<=r){//找到小于root的最右的位置x，那么[s,x]就是左子树,[x+1,e-1]就是右子树
            int mid=l+(r-l>>1);
            if(postorder[mid]<root)l=mid+1;
            else r=mid-1;
        }
        int x=r;//没有左子树的时候，第一个for循环天然卡住不会进去
        for (int i=s;i<=x;i++){
            if (postorder[i]>root) return false;
        }
        for (int i=x+1;i<=e-1;i++){
            if (postorder[i]<root) return false;
        }
        return f(postorder,s,x)&&f(postorder,x+1,e-1);
    }

    private boolean f2(int[] postorder, int s, int e) {//改进，更短
        if (s>=e) return true;//在这里控制，最后一行就不需要判断条件了
        int rv=postorder[e],i,l;
        for(l=s;l<=e-1&&postorder[l]<rv;l++);//<的部分都认为是左子树
        for(i=l;i<=e-1;i++) if(postorder[i]<=rv) return false;//右子树必须全部大于rv
        return f2(postorder,s,l-1)&&f2(postorder,l,e-1);
    }

    public boolean verifyPostorder2(int[] pos) {//单调栈解法：O(N)&O(N)
        int n=pos.length,top=-1;
        int[] stack=new int[n];
        int root=Integer.MAX_VALUE;//当前的根节点的值
        for(int i=n-1;i>=0;i--){
            if(pos[i]>=root) return false;//当前遍历的结点认为是在root的左子树
            while(top>=0&&pos[i]<pos[stack[top]]){//栈底小
                root=pos[stack[top--]];
            }
            stack[++top]=i;
        }
        return true;
    }

}
