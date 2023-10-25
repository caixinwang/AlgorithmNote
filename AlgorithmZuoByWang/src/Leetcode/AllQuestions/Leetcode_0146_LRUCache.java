package Leetcode.AllQuestions;


import java.util.HashMap;

public class Leetcode_0146_LRUCache {
    //1.需要一个hash表快速知道一个key对应的node在哪里
    //2.需要一个双向链表把中间位置移动到后面位置。
    class LRUCache {
        HashMap<Integer,Node> map;//<Key,Node>
        LinkedDoubleList doubleList;
        int capacity;

        public LRUCache(int capacity) {
            if (capacity<1) return;
            this.capacity=capacity;
            map=new HashMap<>();
            doubleList=new LinkedDoubleList();
        }

        public int get(int key) {
            if (!map.containsKey(key)) return -1;
            Node node = map.get(key);
            doubleList.moveToTail(node);
            return node.value;
        }

        public void put(int key, int value) {
            if (!map.containsKey(key)){
                if (map.size()==capacity) {//使用率最低的，也就是链表的头结点可以删掉了
                    Node node = doubleList.removeHead();
                    map.remove(node.key);//这里要将map中的key也删掉，所以Node中k和v都需要有
                }
                Node node=new Node(key,value);
                map.put(key,node);
                doubleList.addLast(node);
            }else {
                Node node = map.get(key);
                node.value=value;
                doubleList.moveToTail(node);
            }
        }

        class  Node{
            Node pre,next;
            int key,value;//这里需要定义k,v

            public Node(int key, int value) {
                this.key = key;
                this.value = value;
            }
        }

        class LinkedDoubleList{
            Node head;
            Node tail;

            public LinkedDoubleList() {
                head=null;
                tail=null;
            }

            public void addLast(Node node){
                if (node==null) return;
                if (head==null&&tail==null){//这里条件只写一个也行，因为有一个为空就代表两个都为空
                    head=tail=node;
                }else {
                    node.pre=tail;
                    tail.next=node;
                    tail=node;
                }
            }

            public Node removeHead(){//这里需要返回Node，因为map也需要移除
                Node ans=head;
                if (head==tail){
                    head=tail=null;
                }else {
                    head.next.pre=null;
                    head= head.next;
                }
                return ans;
            }

            public void moveToTail(Node node){
                if (node==tail) return;
                if (node==head){//是head，只需要修改后结点的指针
                    node.next.pre=node.pre;
                    head=head.next;
                }else {//有前后两个结点，指针都需要修改
                    node.next.pre=node.pre;
                    node.pre.next=node.next;
                }
                node.next=null;//套路：先搞定node--两个指针域都需要设置
                node.pre=tail;
                tail.next=node;//最后搞定tail
                tail=node;
            }
        }
    }
}
