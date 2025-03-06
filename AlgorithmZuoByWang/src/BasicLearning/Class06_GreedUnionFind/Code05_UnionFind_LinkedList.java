package BasicLearning.Class06_GreedUnionFind;

import java.util.*;

public class Code05_UnionFind_LinkedList {//链表实现

    /**
     * 集合里面的元素是类型是K，随意指定。如果元素类型刚好是整数，从0开始编号的话，那么可以使用数组的实现
     * nodes表用来记录每一个结点
     * parents表用来记录每一个结点的代表结点,用于实现向上指
     * size表只有当一个结点是代表结点的时候才会有记录
     * 所有方法中的K key，默认都是在并查集里面的
     * @param <K>
     */
    static class UnionFind<K> {

        public static class Node<V> {
            public V value;//weight
            public Node parent;
            public Node(V value) {
                this.value = value;
            }
        }

        private HashMap<K, Node> nodes;//通过给定的V，快速找到对应的Node
        private HashMap<Node, Integer> size;//只有代表结点才有size值，一个代表结点代表一个集合

        public UnionFind(List<K> values) {
            nodes = new HashMap<>();
            size = new HashMap<>();

            for (K key : values) {
                Node node = new Node<>(null);//可以放权重等
                node.parent=node;//指向自己说明是代表结点
                nodes.put(key, node);
                size.put(node, 1);//初始状态默认一个结点一个集合
            }
        }

        /**
         *
         * @param node 如果node不在nodes表里面，那么会返回自己
         * @return 返回node所在集合的代表结点
         */
        private Node findFather(Node node) {
            if (node==node.parent) return node;
            else return node.parent=findFather(node.parent);//打扁平
        }

        public boolean isSameSet(K a, K b) {//代表结点一样说明是同一个集合
            return findFather(nodes.get(a)) == findFather(nodes.get(b));
        }

        /**
         * 小挂大，所以第一步先确认是不是一个集合。然后确定大集合和小集合。
         * @param a 某个结点的值
         * @param b 另一个结点的值
         */
        public void union(K a, K b) {
            Node head1 = findFather(nodes.get(a));
            Node head2 = findFather(nodes.get(b));
            if (head1 == head2) {//确认不是一个集合
                return;
            }
            if (size.get(head1)<size.get(head2)){//让head1指针永远指向大的
                Node tmp=head1;
                head1=head2;
                head2=tmp;
            }
            head2.parent=head1;//小挂大
            size.put(head1,size.get(head1)+size.get(head2) );//更新大集合的size
            size.remove(head2);//小集合的size移除
        }

        public int sets() {//返回集合的个数。集合的个数实际上就是sizeMap中键（代表结点）的个数
            return size.size();
        }

        public int maxSize(){//返回元素个数最多的集合的元素个数
            int max=Integer.MIN_VALUE;
            for (Integer value : size.values()) {
                max = Math.max(max, value);
            }
            return max;
        }

    }


}
