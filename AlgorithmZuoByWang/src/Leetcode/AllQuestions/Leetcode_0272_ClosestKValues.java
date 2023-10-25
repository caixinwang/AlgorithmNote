package Leetcode.AllQuestions;

import Leetcode.LeetClass.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Leetcode_0272_ClosestKValues {
    //充分利用二插搜索树的性质加上前驱和后继的运用。我们先找到<=target最右x，>=target最左y。如果x和y是同一个，那么x就走到前驱去。
    //起始状态就是x y，x!=y。然后比较x和y与target的差距，谁的差距小加谁。加了x就跳前驱，加了y就跳后继。直到加满了k个
    //怎么找后继：维持一个栈，cur要往左走之前把cur加进去，以及cur为target加进去并退出。按照这么操作，最终要么是找到了target退出，
    //要么是走到了叶子结点退出。如果是找到了target退出，那么栈顶的结点可能有右孩子，如果有右孩子，那么就找到最右孩子的最左，就是你的后继。
    //如果栈顶的结点没有右孩子，那么栈顶的结点退出，新的栈顶就是你的后继。
    //怎么找前驱：维持一个栈，把所有往右走的结点记下来。
    //原理1：为什么可以这样找前驱？我们知道一个结点如果有左子树，那么自己的前驱一定是左子树的最右。如果一个结点没有左子树怎么办？
    //     这就是栈的作用了。栈把之前往右走的结点记下来了，它就是你的前驱。
    //原理2：为什么可以这样找后继？我们知道一个结点如果有右子树，那么自己的后继一定是右子树的最左。如果一个结点没有右子树怎么办？
    //      这就是栈的作用了。栈把之前往左走的结点记下来了，它就是你的前驱。
    //利用这两个栈，后继栈以后每次都按照右树的左边界分解。前驱栈每次按照左树的右边界分解。
    public List<Integer> closestKValues(TreeNode root, double target, int k) {
        List<Integer> ans=new ArrayList<>();
        Stack<TreeNode> less_stack=new Stack<>();//往左走，找前驱用
        Stack<TreeNode> more_stack=new Stack<>();//往右走，找后继用
        initStacks(root,target,less_stack,more_stack);
        if (!less_stack.isEmpty()&&!more_stack.isEmpty()&&less_stack.peek()==more_stack.peek()) getPredecessor(less_stack);
        while(k-->0){
            if (more_stack.isEmpty()){
                ans.add((int)getPredecessor(less_stack));
            }else if (less_stack.isEmpty()){
                ans.add((int)getSuccessor(more_stack));
            }else{
                if (Math.abs(less_stack.peek().val-target)<Math.abs(more_stack.peek().val-target)){
                    ans.add((int)getPredecessor(less_stack));
                }else {
                    ans.add((int)getSuccessor(more_stack));
                }
            }
        }
        return ans;
    }

    private double getSuccessor(Stack<TreeNode> moreStack) {
        TreeNode cur=moreStack.pop();
        double ans=cur.val;
        for (cur=cur.right;cur!=null;cur=cur.left) moreStack.push(cur);
        return ans;
    }

    private double getPredecessor(Stack<TreeNode> lessStack) {
        TreeNode cur=lessStack.pop();
        double ans=cur.val;
        for (cur=cur.left;cur!=null;cur=cur.right) lessStack.push(cur);
        return ans;
    }

    private void initStacks(TreeNode root, double target, Stack<TreeNode> lessStack, Stack<TreeNode> moreStack) {
        while(root!=null){
            if (root.val==target){
                moreStack.push(root);
                lessStack.push(root);
                return ;
            }else if (root.val>target){//对于moreStack需要保存往左边走的结点
                moreStack.push(root);
                root=root.left;
            }else {//对于lessStack需要保存往右边走的结点
                lessStack.push(root);
                root=root.right;
            }
        }
    }


}
