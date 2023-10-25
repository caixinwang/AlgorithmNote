package ClassicInterviewCoding.InterviewCoding17;

import TestUtils.RandomUtil;

import java.util.HashMap;
import java.util.Objects;

public class Problem03_LFU {

    // 节点的数据结构
    public static class Node {
        public Integer key;
        public Integer value;
        public Integer times; // 这个节点发生get或者set的次数总和
        public Node up; // 节点之间是双向链表所以有上一个节点
        public Node down;// 节点之间是双向链表所以有下一个节点

        public Node(int key, int value, int times) {
            this.key = key;
            this.value = value;
            this.times = times;
        }
    }

    // 桶结构
    public static class NodeList {
        public Node head; // 桶的头节点
        public Node tail; // 桶的尾节点
        public NodeList last; // 桶之间是双向链表所以有前一个桶
        public NodeList next; // 桶之间是双向链表所以有后一个桶

        public NodeList(Node node) {
            head = node;
            tail = node;
        }

        // 把一个新的节点加入这个桶，新的节点都放在顶端变成新的头部
        public void addNodeFromHead(Node newHead) {
            newHead.down = head;
            head.up = newHead;
            head = newHead;
        }

        // 判断这个桶是不是空的
        public boolean isEmpty() {
            return head == null;
        }

        // 删除node节点并保证node的上下环境重新连接
        public void deleteNode(Node node) {
            if (head == tail) {
                head = null;
                tail = null;
            } else {
                if (node == head) {
                    head = node.down;
                    head.up = null;
                } else if (node == tail) {
                    tail = node.up;
                    tail.down = null;
                } else {
                    node.up.down = node.down;
                    node.down.up = node.up;
                }
            }
            node.up = null;
            node.down = null;
        }
    }

    // 总的缓存结构
    public static class LFUCache {
        private int capacity; // 缓存的大小限制，即K
        private int size; // 缓存目前有多少个节点
        private HashMap<Integer, Node> records;// 表示key(Integer)由哪个节点(Node)代表
        private HashMap<Node, NodeList> heads; // 表示节点(Node)在哪个桶(NodeList)里
        private NodeList headList; // 整个结构中位于最左的桶

        public LFUCache(int K) {
            this.capacity = K;
            this.size = 0;
            this.records = new HashMap<>();
            this.heads = new HashMap<>();
            headList = null;
        }

        // removeNodeList：刚刚减少了一个节点的桶
        // 这个函数的功能是，判断刚刚减少了一个节点的桶是不是已经空了。
        // 1）如果不空，什么也不做
        //
        // 2)如果空了，removeNodeList还是整个缓存结构最左的桶(headList)。
        // 删掉这个桶的同时也要让最左的桶变成removeNodeList的下一个。
        //
        // 3)如果空了，removeNodeList不是整个缓存结构最左的桶(headList)。
        // 把这个桶删除，并保证上一个的桶和下一个桶之间还是双向链表的连接方式
        //
        // 函数的返回值表示刚刚减少了一个节点的桶是不是已经空了，空了返回true；不空返回false
        private boolean modifyHeadList(NodeList removeNodeList) {
            if (removeNodeList.isEmpty()) {
                if (headList == removeNodeList) {
                    headList = removeNodeList.next;
                    if (headList != null) {
                        headList.last = null;
                    }
                } else {
                    removeNodeList.last.next = removeNodeList.next;
                    if (removeNodeList.next != null) {
                        removeNodeList.next.last = removeNodeList.last;
                    }
                }
                return true;
            }
            return false;
        }

        // 函数的功能
        // node这个节点的次数+1了，这个节点原来在oldNodeList里。
        // 把node从oldNodeList删掉，然后放到次数+1的桶中
        // 整个过程既要保证桶之间仍然是双向链表，也要保证节点之间仍然是双向链表
        private void move(Node node, NodeList oldNodeList) {
            oldNodeList.deleteNode(node);
            // preList表示次数+1的桶的前一个桶是谁
            // 如果oldNodeList删掉node之后还有节点，oldNodeList就是次数+1的桶的前一个桶
            // 如果oldNodeList删掉node之后空了，oldNodeList是需要删除的，所以次数+1的桶的前一个桶，是oldNodeList的前一个
            NodeList preList = modifyHeadList(oldNodeList) ? oldNodeList.last
                    : oldNodeList;
            // nextList表示次数+1的桶的后一个桶是谁
            NodeList nextList = oldNodeList.next;
            if (nextList == null) {
                NodeList newList = new NodeList(node);
                if (preList != null) {
                    preList.next = newList;
                }
                newList.last = preList;
                if (headList == null) {
                    headList = newList;
                }
                heads.put(node, newList);
            } else {
                if (nextList.head.times.equals(node.times)) {
                    nextList.addNodeFromHead(node);
                    heads.put(node, nextList);
                } else {
                    NodeList newList = new NodeList(node);
                    if (preList != null) {
                        preList.next = newList;
                    }
                    newList.last = preList;
                    newList.next = nextList;
                    nextList.last = newList;
                    if (headList == nextList) {
                        headList = newList;
                    }
                    heads.put(node, newList);
                }
            }
        }

        public void put(int key, int value) {
            if (capacity == 0) {
                return;
            }
            if (records.containsKey(key)) {
                Node node = records.get(key);
                node.value = value;
                node.times++;
                NodeList curNodeList = heads.get(node);
                move(node, curNodeList);
            } else {
                if (size == capacity) {
                    Node node = headList.tail;
                    headList.deleteNode(node);
                    modifyHeadList(headList);
                    records.remove(node.key);
                    heads.remove(node);
                    size--;
                }
                Node node = new Node(key, value, 1);
                if (headList == null) {
                    headList = new NodeList(node);
                } else {
                    if (headList.head.times.equals(node.times)) {
                        headList.addNodeFromHead(node);
                    } else {
                        NodeList newList = new NodeList(node);
                        newList.next = headList;
                        headList.last = newList;
                        headList = newList;
                    }
                }
                records.put(key, node);
                heads.put(node, headList);
                size++;
            }
        }

        public Integer get(int key) {
            if (!records.containsKey(key)) {
                return null;
            }
            Node node = records.get(key);
            node.times++;
            NodeList curNodeList = heads.get(node);
            move(node, curNodeList);
            return node.value;
        }

    }

    public static class LFUCache2 {
        private int capacity; // 缓存的大小限制，即K
        private int size; // 缓存目前有多少个节点
        private HashMap<Integer, Node> records;// 表示key(Integer)由哪个节点(Node)代表
        private HashMap<Node, NodeList> heads; // 表示节点(Node)在哪个桶(NodeList)里
        private NodeList headList; // 整个结构中位于最左的桶

        public LFUCache2(int K) {
            this.capacity = K;
            this.size = 0;
            this.records = new HashMap<>();
            this.heads = new HashMap<>();
            headList = null;
        }

        /**
         *
         * @param removeNodeList 刚刚有过删除操作的桶
         * @return 对刚刚有过删除操作的桶做一遍调整，如果把桶删了就返回TRUE，否则就返回false
         */
        private boolean modifyHeadList(NodeList removeNodeList) {
            if (!removeNodeList.isEmpty()) return false;
            if (removeNodeList == headList) {//为头时分为两种小情况，一种是头后面有东西，一种是头后面没东西
                headList = headList.next;
                if (headList != null) headList.last = null;
            } else {//不为头也分为两种小情况，一种是node后面有东西，一种是node后面没东西
                removeNodeList.last.next = removeNodeList.next;
                if (removeNodeList.next != null) removeNodeList.next.last = removeNodeList.last;
            }
            return true;
        }

        /**
         * @param node        node的频次已经++了，你给我去调整一下，把node从之前的老桶删掉，放到一个合适的桶里面
         * @param oldNodeList node所在的老桶
         */
        private void move(Node node, NodeList oldNodeList) {
            oldNodeList.deleteNode(node);
            boolean deleted = modifyHeadList(oldNodeList);
            NodeList pre = deleted ? oldNodeList.last : oldNodeList;//pre是未来node所在桶的前一个桶
            NodeList next = oldNodeList.next;//老桶的下一个，未来node要么在这个桶里，要么在pre和next中间的桶里
            if (next!=null&&next.head.times.equals(node.times)){//只有这种情况不用创新新桶
                next.addNodeFromHead(node);
                heads.put(node,next);
            }else {//其它情况都要创新新桶,插在pre和next的中间
                NodeList nodeList=new NodeList(node);
                heads.put(node,nodeList);
                nodeList.next=next;
                nodeList.last=pre;
                if (next!=null) next.last=nodeList;
                if (pre!=null) pre.next=nodeList;
                if (headList==null||headList.last==nodeList)headList=nodeList;//考虑换头的情况
            }
        }

        public void put(int key, int value) {
            if (capacity==0) return;
            if (records.containsKey(key)){
                Node node = records.get(key);
                NodeList nodeList = heads.get(node);
                node.value=value;
                node.times++;
                move(node,nodeList);
            }else {//结构里面没有这个key
                Node node=new Node(key,value,1);
                if (size==capacity){
                    Node p = headList.tail;
                    headList.deleteNode(p);
                    records.remove(p.key);
                    heads.remove(p);
                    size--;
                    modifyHeadList(headList);
                }
                if (headList!=null&&headList.head.times.equals(1)){//只有这种情况才不需要建桶
                    headList.addNodeFromHead(node);
                }else {//要建桶，并且不用考虑pre，需要考虑next--headList
                    NodeList nodeList=new NodeList(node);
                    nodeList.next=headList;
                    if (headList!=null)headList.last=nodeList;
                    headList=nodeList;
                }
                heads.put(node,headList);
                records.put(key,node);
                size++;
            }
        }

        public Integer get(int key) {
            if (!records.containsKey(key)) {
                return null;
            }
            Node node = records.get(key);
            node.times++;
            NodeList curNodeList = heads.get(node);
            move(node, curNodeList);
            return node.value;
        }

    }
    public static void testForStructure() {//测试证明两个UnionFind都是正确的
        RandomUtil r = new RandomUtil();
        int times = 100;
        int maxSize = 10;
        int maxKey=20;
        int maxVal=20;
        boolean isok = true;
        for (int i = 0; i < times; i++) {//进行times次测试
            int tests = 10000;
            int size=r.ran(1,maxSize);
            LFUCache structure1 = new LFUCache(size);
            LFUCache2 structure2 =  new LFUCache2(size);
            for (int j = 0; j < tests; j++) {//每次测试随机进行times次的数据操作并且检查数据的正确性
//                if (j==0) System.out.println("size:"+size);
                int key=r.ran(maxKey);
                int val=r.ran(maxVal);
                if (r.oneOrZero()){
                    structure1.put(key,val);
                    structure2.put(key,val);
//                    System.out.println("put("+key+","+val+")");
                }
                if (r.oneOrZero()){
                    Integer res1 = structure1.get(key);
                    Integer res2 = structure2.get(key);
//                    System.out.println("get key "+key+" => ("+key+","+res1+"),("+key+","+res2+")");
                    if (!Objects.equals(res1,res2)){
                        isok=false;
                        break;
                    }
                }
            }
            if (!isok) break;
        }
        System.out.println(isok ? "success" : "fail");
    }
    public static void main(String[] args) {
        testForStructure();
    }

}