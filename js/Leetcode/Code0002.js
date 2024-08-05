const {ListNode}=require('../utils/NodeList')
/**
 * @param {ListNode} l1
 * @param {ListNode} l2
 * @return {ListNode}
 */
var addTwoNumbers = function(l1, l2) {
  let dummy=new ListNode(0,null),cur=dummy,p1=l1,p2=l2
  let carry=0,add=0;
  while(p1||p2||carry){
      add=(p1?p1.val:0)+(p2?p2.val:0)+carry
      carry=Math.floor(add/10)
      cur.next=new ListNode(add%10,null)
      cur=cur.next
      p1=p1&&p1.next
      p2=p2&&p2.next
  }
  return dummy.next;
};
// ListNode.travel(addTwoNumbers(ListNode.create(2,4,3),ListNode.create(5,6,4)))


