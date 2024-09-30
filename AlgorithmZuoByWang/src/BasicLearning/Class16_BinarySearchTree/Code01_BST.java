package BasicLearning.Class16_BinarySearchTree;

import java.util.LinkedList;
import java.util.Queue;

public class Code01_BST<Key extends Comparable<Key>, Value> {
    private Node root;

    private class Node {
        private Node left;
        private Node right;
        private Key key;
        private Value val;
        private int N;

        public Node(Key key, Value val, int n) {
            this.key = key;
            this.val = val;
            N = n;
        }
    }

    public int size() {
        return size(root);
    }

    public int size(Node node) {
        if (node == null) return 0;
        return node.N;
    }

    public Value get(Key key) {
        return get(root, key);
    }

    /**
     * @param x:从以node为根节点的子树查找与key对应的value
     * @param key:
     * @return :走到空说明在子树中没有找到key。在过程中如果命中了就会一路返回，最终把命中的node的value值返回
     * 如果一直走到底都没有命中，如果走到了空子树也是一路返回，最终返回null。
     */
    private Value get(Node x, Key key) {
        if (key == null) throw new IllegalArgumentException();
        if (x == null) return null;//走到空了说明没有找到这个key
        int compare = key.compareTo(x.key);
        if (compare < 0) {
            return get(x.left, key);
        } else if (compare > 0) {
            return get(x.right, key);
        } else {
            return x.val;
        }
    }

    public void put(Key key, Value val) {
        root = put(root, key, val);
    }

    /**
     * 思路：从x结点开始向下寻找合适的位置插入键值对。
     * 1. 如果最终走到空节点就说明子树没有对应的键，那么就创建一个新节点返回给他的父亲，此时父亲的孩子就变化了--本来是空
     * 2. 只有空节点会返回一个新节点给父亲。其它情况会把自己返回给父亲，此时父亲结点的孩子不变。
     * 最后更新 N 来保证插入新节点的话搜索树的N是对的
     *
     * @param x:向以x为根节点的子树插入键值对
     * @param key:
     * @param val:
     * @return
     */
    private Node put(Node x, Key key, Value val) {
        if (key == null || val == null) throw new IllegalArgumentException();
        if (x == null) return new Node(key, val, 1);//返回给它的父节点
        int compare = key.compareTo(x.key);
        if (compare < 0) {
            x.left = put(x.left, key, val);
        } else if (compare > 0) {
            x.right = put(x.right, key, val);
        } else {
            return x;
        }
        x.N = x.left.N + x.right.N + 1;
        return x;
    }

    public Key min() {
        return min(root).key;
    }

    private Node min(Node x) {
        if (x.left == null) return x;
        return min(x.left);
    }

    public Key max() {
        return max(root).key;
    }

    private Node max(Node x) {
        if (x.right == null) return x;
        return max(x.right);
    }


    public Key floor(Key key) {
        return floor(root, key).key;
    }

    /**
     *
     * @param x:找出以x为根节点的子树中小于等于key的最大结点
     * @param target:
     * @return
     */
    private Node floor(Node x, Key target) {
        if (x == null) return null;
        if (target == null) throw new IllegalArgumentException();
        int compare = x.key.compareTo(target);
        if (compare > 0) {
            //说明x结点以及它的右树都是大于target的,只能往左树去找
            return floor(x.left, target);
        } else if (compare == 0) {
            //右树大于x 也大于target 不符合条件 x本身此时就是答案
            return x;
        } else {
            //x<target 右树有可能也小于target 所以还需要递归往右边去找
            Node t = floor(x.right, target);
            if (t == null) return x;//为空说明右边找不到 返回x
            else return t;//否则说明右树也有满足条件的 返回右树的那个答案
        }
    }

    //返回第k大的key (从0开始计数)
    public Key select(int k) {
        return select(root, k).key;
    }

    private Node select(Node x, int k) {
        if (x == null) return null;
        int t = size(x.left);
        if (k < t) return select(x.left, k);
        if (k > t) return select(x.right, k - t - 1);
        return x;
    }

    //判断target排第几 (从0开始算)
    public int rank(Key target) {
        return rank(root, target);
    }

    private int rank(Node x, Key target) {
        if (x == null) return 0;
        if (target == null) throw new IllegalArgumentException();
        int compare = x.key.compareTo(target);
        if (compare > 0) return rank(x.left, target);
        if (compare < 0) return size(x.left) + 1 + rank(x.right, target);
        return size(x.left);
    }

    public void deleteMin() {
        deleteMin(root);
    }

    /**
     * 通过使得没有引用指向x结点来实现删除，JVM
     * @param x
     * @return
     */
    private Node deleteMin(Node x) {
        if (x == null) throw new IllegalArgumentException();
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        x.N=size(x.left)+size(x.right)+1;
        return x;
    }

    private void delete(Key key){
        delete(root , key);
    }

    private Node delete(Node x, Key target) {
        if (x==null) return null;
        if (target==null) throw new IllegalArgumentException();
        int compare = x.key.compareTo(target);
        if (compare > 0){
            x.left=delete(x.left,target);
        } else if (compare < 0) {
            x.right=delete(x.right,target);
        }else {
            if (x.left==null) return x.right;
            if (x.right==null) return x.left;
            Node t=x;
            x=min(x.right);
            x.right=deleteMin(t.right);//x还在t.right上面 需要删除 脱离出t的子树 才能做t的父节点
            x.left=t.left;
        }
        x.N=size(x.left)+size(x.right)+1;
        return x;
    }

    public Iterable<Key> keys(){
        return keys(min(),max());
    }

    private Iterable<Key> keys(Key lo, Key hi) {
        Queue<Key> queue= new LinkedList();
        keys(root,queue,lo,hi);
        return queue;
    }

    private void keys(Node x, Queue<Key> queue, Key lo, Key hi) {
        if (x==null)return;
        int cmplo = lo.compareTo(x.key);
        int cmphi = hi.compareTo(x.key);
        if (cmplo<0) keys(x.left,queue,lo,hi);
        if (cmplo<=0&&cmphi>=0) queue.add(x.key);
        if (cmphi>0) keys(x.right,queue,lo,hi);
    }


}
