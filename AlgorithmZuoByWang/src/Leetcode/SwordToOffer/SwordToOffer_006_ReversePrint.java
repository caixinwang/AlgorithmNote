package Leetcode.SwordToOffer;

import Leetcode.LeetClass.ListNode;

public class SwordToOffer_006_ReversePrint {
    public int[] reversePrint(ListNode head) {
        int cnt=0;
        ListNode cur,pre,next;
        for(cur=head,pre=null;cur!=null;next=cur.next,cur.next=pre,pre=cur,cur=next,cnt++);
        int[] ans=new int[cnt];
        for(cur=pre,cnt=0;cur!=null;ans[cnt++]=cur.val,cur=cur.next);
        return ans;
    }
}
