package BasicLearning.Class03_LinkedList;

public class Code13_QueueByDoubleLinkedList {//双向链表实现队列
    public static class Node<V> {
        public V value;
        public Node<V> last;
        public Node<V> next;

        public Node(V v) {
            value = v;
            last = null;
            next = null;
        }
    }

    public static class MyDeque<V> {
        private Node<V> head;
        private Node<V> tail;
        private int size;

        public MyDeque() {
            head = null;
            tail = null;
            size = 0;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public int size() {
            return size;
        }

        public void pushHead(V value) {
            Node<V> node = new Node<>(value);
            if (head == null) {
                head = node;
                tail = node;
            } else {
                node.next = head;
                head.last = node;
                head=head.last;
            }
            size++;
        }

        public void pushTail(V value) {
            Node<V> node=new Node<>(value);
            if (head==null){
                head=node;
                tail=node;
            }else{
                tail.next=node;
                node.last=tail;
                tail= tail.next;
            }
            size++;
        }

        public V pollHead() {
            V value=null;
            if (head!=null){//至少有1个元素
                value= head.value;//把值存下来
                head= head.next;//head后移
                if (head==null){//防止下面出现空指针异常，进入这个if语句说明只有一个元素
                    tail=null;
                }else {//能执行这一段代码说明至少有两个元素。确保前面的next不指向任何结点，让JVM释放
                    head.last=null;
                }
                size--;
            }
            return value;
        }

        public V pollTail() {
            V value=null;
            if (tail!=null){
                value=tail.value;
                tail= tail.last;
                if (tail==null){
                    head=null;
                }else{
                    tail.next=null;
                }
                size--;
            }
            return value;
        }

        public V peekHead() {
            return head!=null?head.value:null;
        }

        public V peekTail() {
            return tail!=null? tail.value:null;
        }

    }
}
