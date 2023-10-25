package BasicLearning.Class03_LinkedList;

public class Code05_SmallerEqualBigger {//将单向链表按某值划分成左边小、中间相等、右边大的形式

    public static class Node {
        public int value;
        public Node next;

        public Node(int data) {
            this.value = data;
        }
    }

    /**
     * 思路：把链表中的结点全部放入数组中，然后利用快排的三向切分排好结点的先后顺序，然后从头开始串起来。
     * @param head 链表
     * @param pivot 划分值
     * @return 返回划分过后链表的头节点
     */
    public static Node listPartition1(Node head, int pivot){
        Node temp=head;
        int num=0;
        while (temp!=null){//遍历统计链表的大小
            num++;
            temp=temp.next;
        }
        Node[] arr=new Node[num];
        temp=head;
        int i=0;
        while(temp!=null){//放入数组
            arr[i++]=temp;
            temp=temp.next;
        }
        partition(arr,pivot);//三向切分数组
        for (i=0;i<arr.length-1;i++){
            arr[i].next=arr[i+1];
        }
        arr[i].next=null;
        return arr[0];
    }

    private static void partition(Node[] arr, int pivot) {
        int less=-1;
        int more=arr.length;
        int index=0;
        while(index<more){
            if (arr[index].value<pivot){
                swap(arr,++less,index++);
            }else if (arr[index].value>pivot){
                swap(arr,--more,index);
            }else {
                index++;
            }
        }
    }

    private static void swap(Node[] arr, int a, int b) {
        Node temp=arr[a];
        arr[a]=arr[b];
        arr[b]=temp;
    }

    /**
     *思路：创建三个单向链表，分别用来存储小于、等于、大于的。这样一来就要三个头节点，但是为了速度更快，不需要每次都遍历到尾结点，所以
     *     我们对于每个单向链表还额外增加了一个尾结点。这样一来最后拼接三个链表就很容易，而且中途增加新节点也很容易。创建完了三个单链表
     *     以后，我们从头遍历链表，每次孤立一个结点。孤立一个结点指的是让这个结点和后面的结点脱离联系，也就是让这个结点的next指针置为null
     * @param head:head为头的单向链表
     * @param pivot:以pivot作为划分值，把单向链表划分成三段
     * @return
     */
    public static Node listPartition2(Node head, int pivot) {
        Node ss=null,se=null,es=null,ee=null,bs=null,be=null;
        while(head!=null){
            Node next=head.next;
            head.next=null;//孤立这个结点。如果不置空，可能会导致大于链的最后一个结点和某一个其它结点连接形成闭环，导致无限循环。
            if (head.value<pivot){//不能重新写成一个方法，因为方法传递是值传递
                if (ss==null){//蕴含着tail也为null
                    ss=head;
                    se=head;
                }else {
                    se.next=head;
                    se=se.next;
                }
            }else if (head.value==pivot){
                if (es==null){//蕴含着tail也为null
                    es=head;
                    ee=head;
                }else {
                    ee.next=head;
                    ee=ee.next;
                }
            }else {
                if (bs==null){//蕴含着tail也为null
                    bs=head;
                    be=head;
                }else {
                    be.next=head;
                    be=be.next;
                }
            }
            head=next;
        }
        //开始连接这三个链。如果小于链有东西，就让小于链的末尾连接等于链，然后更新小于链的尾结点，因为最后我们是返回小于链、等于链，大于链
        //中第一个非空链的头节点。
        if (ss!=null){//小于链有东西，最后肯定返回小于链---我们在小于链的我们串上东西
            se.next=es;//小于链末尾连上等于链的头
            se=es==null?se:ee;//如果等于链为空小于链的尾就不更新，而如果等于链不为空就换成等于链的尾
            se.next=bs;//接着连上大于链的头，现在可以返回了
            return ss;
        }
        if (es!=null){//潜台词是小于链已经为空了，最后直接返回等于链的头
            ee.next=bs;
            return es;
        }
        return bs;
    }

    public static void printLinkedList(Node node) {
        System.out.print("Linked List: ");
        while (node != null) {
            System.out.print(node.value + " ");
            node = node.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Node head1 = new Node(7);
        head1.next = new Node(9);
        head1.next.next = new Node(1);
        head1.next.next.next = new Node(8);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = new Node(2);
        head1.next.next.next.next.next.next = new Node(5);
        printLinkedList(head1);
//         head1 = listPartition1(head1, 4);
        head1 = listPartition2(head1, 5);
        printLinkedList(head1);

    }
}
