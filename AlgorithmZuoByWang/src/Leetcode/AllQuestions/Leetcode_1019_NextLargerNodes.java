package Leetcode.AllQuestions;

import Leetcode.LeetClass.ListNode;

public class Leetcode_1019_NextLargerNodes {
    public int[] nextLargerNodes(ListNode head) {
        int top=-1,cnt;
        ListNode cur;
        for(cur=head,cnt=0;cur!=null;cur=cur.next,cnt++);//统计结点个数
        ListNode[] stack=new ListNode[cnt];
        int[] index=new int[cnt];//和stack配合使用，记录结点对应的下标
        int[] ans=new int[cnt];
        for(cur=head,cnt=0;cur!=null;cur=cur.next,cnt++){
            while(top>=0&&cur.val>stack[top].val){
                ans[index[top--]]=cur.val;
            }
            stack[++top]=cur;
            index[top]=cnt;
        }
        return ans;
    }
}
