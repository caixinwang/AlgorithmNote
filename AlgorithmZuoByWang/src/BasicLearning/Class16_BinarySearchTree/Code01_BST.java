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
     * 思路：观察运动路径可以得出，运动轨迹就是沿着x结点一直向下直到找到空节点或者key结点才开始返回。
     * 1. 如果在最底层是最先通过key结点返回的话，沿途的结点就直接一路把这个key结点往上扔。
     * 2. 如果在最底层是最先通过空结点返回的话，沿途的结点除了最底层结点的父亲结点是返回自己，
     * 其它的结点都是返回最底层结点的父亲结点，把它一路往上扔。可以看做把null做了替换，
     * 不是扔null，而是通过一个if-else来把null换成了空节点的父亲，然后再把父亲一路扔
     * 3. 这个函数和get很像
     *
     * @param x:找出以x为根节点的子树中小于key的最大结点
     * @param key:
     * @return
     */
    private Node floor(Node x, Key key) {
        if (x == null) return null;
        if (key == null) throw new IllegalArgumentException();
        int compare = key.compareTo(x.key);
        if (compare < 0) {
            return floor(x.left, key);
        } else if (compare == 0) {
            return x;
        } else {
            Node t = floor(x.right, key);
            if (t == null) return x;
            else return t;
        }
    }

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

    public int rank(Key key) {
        return rank(root, key);
    }

    private int rank(Node x, Key key) {
        if (x == null) return 0;
        if (key == null) throw new IllegalArgumentException();
        int compare = key.compareTo(x.key);
        if (compare < 0) return rank(x.left, key);
        if (compare > 0) return size(x.left) + 1 + rank(x.right, key);
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

    private Node delete(Node x, Key key) {
        if (x==null) return null;
        if (key==null) throw new IllegalArgumentException();
        int compare = key.compareTo(x.key);
        if (compare<0){
            x.left=delete(x.left,key);
        } else if (compare > 0) {
            x.right=delete(x.right,key);
        }else {
            if (x.left==null) return x.right;
            if (x.right==null)return x.left;
            Node t=x;
            x=min(x.right);
            x.right=deleteMin(t.right);
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
        if (cmphi>0) keys(x.right,queue,lo,hi);
        if (cmplo<=0&&cmphi>=0) queue.add(x.key);
    }


}
