package Leetcode.AllQuestions;

public class Leetcode_0707_MyLinkedList {
    //1.在某一个下标add或者是del，情况有：head==tail、在head操作，在tail操作、越界、不需要操作
    //2.下面的实现没有加上size，如果有size，那么在addindex或者delinedx的时候就可以在一开始确认是否addHead或者addTail
    //3.往下还有一个单链表实现，关键点就是带上一个pre去遍历。依然是head==tail、在head操作，在tail操作、越界、不需要操作分开讨论
    static class MyLinkedList {
        class Node{
            int val;
            Node next,pre;
            public Node(int v){
                val=v;
            }
        }
        Node head,tail;
        public MyLinkedList() {
            head=tail=null;
        }

        public int get(int index) {
            Node cur;
            for(cur=head;index!=0&&cur!=null;cur=cur.next,index--);
            if(index!=0||cur==null) return -1;
            return cur.val;
        }

        public void addAtHead(int val) {
            Node node=new Node(val);
            if(head==null){
                head=tail=node;
                return;
            }
            node.next=head;
            head.pre=node;
            head=node;
        }

        public void addAtTail(int val) {
            Node node=new Node(val);
            if(head==null){
                head=tail=node;
                return;
            }
            tail.next=node;
            node.pre=tail;
            tail=node;
        }

        public void addAtIndex(int index, int val) {
            if(index==0) {
                addAtHead(val);
                return;
            }
            Node cur;
            for(cur=head;cur!=null&&index!=0;cur=cur.next,index--);
            if(index!=0) return;
            if(cur==null) {
                addAtTail(val);
                return;
            }
            Node node=new Node(val);
            node.next=cur;
            node.pre=cur.pre;
            node.pre.next=node;
            node.next.pre=node;
        }

        public void deleteAtIndex(int index) {
            Node cur;
            for(cur=head;cur!=null&&index!=0;cur=cur.next,index--);
            if(index>0||cur==null) return;
            if (cur.pre==null) deleteAtHead();
            else if(cur.next==null)deleteAtTail();
            else {
                cur.pre.next=cur.next;
                cur.next.pre = cur.pre;
            }
        }

        public void deleteAtHead(){
            if(head==tail){
                head=tail=null;
                return;
            }
            head=head.next;
            head.pre=null;
        }

        public void deleteAtTail(){
            if(head==tail){
                head=tail=null;
                return;
            }
            tail=tail.pre;
            tail.next=null;
        }
    }

    static class MyLinkedList2{//单链表
        class Node{
            int val;
            Node next;
            public  Node(int v){
                val=v;
            }
        }

        Node head,tail;
        int size;
        public MyLinkedList2() {
            head=tail=null;
            size=0;
        }

        public int get(int index) {
            if (index<0||index>=size) return -1;
            Node cur;
            for (cur=head;cur!=null&&index>0;cur=cur.next,index--);
            return cur.val;
        }

        public void addAtHead(int val) {
            size++;
            Node node=new Node(val);
            if (head==null){
                head=tail=node;
                return;
            }
            node.next=head;
            head=node;
        }

        public void addAtTail(int val) {
            size++;
            Node node=new Node(val);
            if (head==null){
                head=tail=node;
                return;
            }
            tail.next=node;
            tail=node;
        }

        public void addAtIndex(int index, int val) {
            if (index<0||index>size) return;
            if (index==0) addAtHead(val);
            else if (index==size) addAtTail(val);
            else {
                size++;
                Node cur,pre;
                for (cur=head,pre=null;cur!=null&&index>0;pre=cur,cur=cur.next,index--);
                Node node=new Node(val);
                node.next=cur;
                pre.next=node;
            }
        }

        public void deleteAtIndex(int index) {
            if (index<0||index>=size) return;
            size--;
            if (head==tail){
                head=tail=null;
                return;
            }
            Node cur,pre;
            for (cur=head,pre=null;cur!=null&&index>0;pre=cur,cur=cur.next,index--);
            if (pre==null){//说明删除头节点
                head=head.next;
            }else{
                pre.next=cur.next;
                if (cur.next==null) tail=pre;//说明删除了最后一个结点
            }
        }
    }

    public static void main(String[] args) {
        MyLinkedList2 list=new MyLinkedList2();
        list.addAtHead(1);
        list.addAtTail(3);
        list.addAtIndex(1,2);
        list.get(1);
        list.deleteAtIndex(1);
        list.get(1);
    }

}
