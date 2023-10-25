package BasicLearning.Class03_LinkedList;

public class Code08_DeleteGivenValue {//给你一个链表和一个值val，删除这个链表里面所有值为val的结点

    public static class Node {
        public int value;
        public Node next;

        public Node(int data) {
            this.value = data;
        }
    }

    public static Node removeValue(Node head, int num) {
        while(head!=null&&head.value==num) head= head.next;
        //现在来到了第一个不用删除的非空结点
        Node pre=head;//记录离cur最近的不用删除的结点，此时的head本身就是一个不需要删除的结点
        Node cur=head;
        while(cur!=null){
            if (cur.value==num){//如果cur结点要删除，就让pre指向cur的下一个
                pre.next=cur.next;
            }else {//cur不需要删除，更新pre
                pre=cur;
            }
            cur=cur.next;
        }
        return head;
    }

    public static Node removeValue2(Node head, int num) {
        if (head==null) return null;
        Node p=head;
        while(p!=null&&p.value==num) p=p.next;
        Node res=p;
        while(p!=null){
            while(p.next!=null&&p.next.value==num){
                p.next=p.next.next;
            }
            p=p.next;//出循环，p的next的value！=num
        }
        return res;
    }

    public static void main(String[] args) {
        Node head=new Node(1);
        head.next=new Node(1);
        head.next.next=new Node(1);
        head.next.next.next=new Node(2);
        head.next.next.next.next=new Node(3);
        head.next.next.next.next.next=new Node(1);
        head.next.next.next.next.next.next=new Node(1);
        head.next.next.next.next.next.next.next=new Node(4);
        head=removeValue2(head,1);
        while(head!=null) {
            System.out.println(head.value);
            head=head.next;
        }
    }

}
