package BasicLearning.Class03_LinkedList;

public class Code12_QueueAndStack {//利用单链表实现栈和队列
    public static class Node<V>{
        public V value;
        public Node<V> next;

        public Node(V value){
            this.value=value;
        }
    }

    public static class MyQueue<V>{
        public Node<V> head;//从头出
        public Node<V> tail;//从尾进
        public int size;

        public MyQueue(){
            head=null;
            tail=null;
            size=0;
        }

        public int size(){
            return size;
        }

        public boolean isEmpty(){
            return size==0;
        }

        public void EnQueue(V value){
            Node<V> node=new Node<>(value);
            if (tail==null){
                head=node;
                tail=node;
            }else {

            }
            size++;
        }

        public V DeQueue(){
            V ans=null;
            if (head!=null){
                ans=head.value;
                head=head.next;
                size--;
            }
            if (head==null){
                tail=null;
            }
            return ans;
        }

        public V peek(){
            V ans=null;
            if (head!=null){
                ans=head.value;
            }
            return ans;
        }
    }

    /**
     * 栈用头插来实现。从头部出
     * @param <V>
     */
    public static class MyStack<V>{
        Node<V> head;
        int size;

        public MyStack(){
            head=null;
            size=0;
        }

        public boolean isEmpty(){
            return 0==size;
        }

        public int size() {
            return size;
        }

        public void push(V value){
            Node<V> node=new Node<>(value);
            if (head != null) {
                node.next = head;
            }
            head=node;
            size++;
        }

        public V pop(){
            V value=null;
            if (head!=null){
                value=head.value;
                head=head.next;
                size--;
            }
            return value;
        }

        public V peek(){
            return head!=null?head.value:null;
        }

    }
}
