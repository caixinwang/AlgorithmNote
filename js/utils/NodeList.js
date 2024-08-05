function ListNode(val, next) {
  this.val = (val===undefined ? 0 : val)
  this.next = (next===undefined ? null : next)
}
ListNode.create=function(... nums){
  let dummy=new ListNode(0,null),cur=dummy;
  for(let i=0;i<nums.length;i++){
    cur.next=new ListNode(nums[i],null)
    cur=cur.next
  }
  return dummy.next;
}
ListNode.travel=function(l){
  while(l){
    console.log(l.val)
    l=l.next
  }
}

module.exports={
  ListNode,
}
