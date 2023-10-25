package BasicLearning.Class16_BinarySearchTree;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

public class Code09_BinarySearchST<Key extends Comparable<Key>, Value> {
    private static final int INIT_CAPACITY = 2;
    private Key[] keys;
    private Value[] values;
    private int N = 0;//默认初始状态为0

    /**
     * 写成这样方便后面修改初始大小。而不是直接写死在程序中。
     */
    public Code09_BinarySearchST() {
        this(INIT_CAPACITY);
    }

    public Code09_BinarySearchST(int capacity) {
        keys = (Key[]) new Comparable[capacity];
        values = (Value[]) new Object[capacity];
    }

    public void put(Key key, Value value) {
        if (key == null) throw new IllegalArgumentException("输入参数错误");
        if (value == null) {
            delete(key);
            return;
        }
        int i = rank(key);//找出key在数组中是第几位，从0开始标号。
        if (i < N && keys[i].compareTo(key) == 0) {//进入if说明key确实存在于有序符号表中
            //rank()在key很大的时候可能返回N；rank返回的i可能只是key接近的位置，并不是key实际存在，所以需要比较
            values[i] = value;
        } else {
            if (N == keys.length) resize(2 * N);//调整数组大小
            for (int j = N - 1; j >= i; j--) {//把i位置挪出来放置新键值对,也就是把原本的N-1~i位置上的元素全部后挪
                keys[j + 1] = keys[j];
                values[j + 1] = values[j];
            }
            keys[i] = key;
            values[i] = value;
            N++;
//            assert check();
        }
    }

    private void resize(int capacity) {
        assert capacity >= N;//无论怎么调整大小，都至少要能够装下当前的数据量
        Key[] a1 = (Key[]) new Comparable[capacity];
        Value[] a2 = (Value[]) new Object[capacity];
        for (int i = 0; i < N; i++) {
            a1[i] = keys[i];
            a2[i] = values[i];
        }
        keys = a1;
        values = a2;
    }

    public Value get(Key key) {
        if (key==null) throw new IllegalArgumentException("输入参数错误");
        if (isEmpty()) return null;
        int i = rank(key);
        if (i<N&&key.compareTo(keys[i])==0){
            return values[i];
        }else {
            return null;
        }
    }

    public void delete(Key key) {
        if (key==null) throw new IllegalArgumentException("输入参数错误");
        if (isEmpty()) return;
        int i = rank(key);
        if (i<N&&key.compareTo(keys[i])==0){
            for (int j=i+1;j<=N-1;j++){//实现删除。i后面的所有元素覆盖前面的
                keys[j-1]=keys[j];
                values[j-1]=values[j];
            }
            keys[N-1]=null;//删除之后设置为空，是好的编程习惯
            values[N-1]=null;
            N--;
            if (N>0&&N==keys.length/4){
                resize(keys.length/2);
            }
//            assert check();
        }else {
            return;
        }
    }

    public boolean contains(Key key) {
        if (key==null) throw new IllegalArgumentException("输入参数错误");
        return get(key)!=null;//
    }

    public boolean isEmpty() {
        return N==0;
    }

    public int size() {
        return N;
    }

    public Key min() {
        if (isEmpty())return null;
        return keys[0];
    }

    public Key max() {
        if (isEmpty())return null;
        return keys[N-1];
    }

    public Key floor(Key key) {
        if (key==null) throw new IllegalArgumentException("输入参数错误");
        if (isEmpty()) return null;
        int i = rank(key);
        if (i<N&&keys[i].compareTo(key)==0) return keys[i];//存在一个与key相同的键
        if (i==0) return null;//说明没有人比你小，无法向下取了
        return keys[i-1];
    }

    public Key ceiling(Key key) {
        if (key==null) throw new IllegalArgumentException("输入参数错误");
        if (isEmpty()) return null;
        int i = rank(key);
        if (i==N) return null;//说明没有人比你大了，无法向下取
        return keys[i];//正好命中和不命中的合并了
    }

    public int rank(Key key) {
        if (key==null) throw new IllegalArgumentException("输入参数错误");
        if (isEmpty()) throw new NoSuchElementException("不能在空的符号表中排序");
        int lo=0,hi=N-1;
        while(lo<=hi){
            int mid=lo+(hi-lo)/2;
            int cmp=key.compareTo(keys[mid]);
            if (cmp<0){
                hi=mid-1;
            }else if (cmp>0){
                lo=mid+1;
            }else {
                return mid;//确实存在
            }
        }
        return lo;//不存在，返回它应该在的位置
    }

    public Key select(int k) {
        return keys[k];
    }

    public void deleteMin() {
        if (isEmpty())throw new NoSuchElementException();
        delete(min());
    }

    public void deleteMax() {
        if (isEmpty())throw new NoSuchElementException();
        delete(max());
    }

    public int size(Key lo, Key hi) {
        if (lo == null) throw new IllegalArgumentException("first argument to size() is null");
        if (hi == null) throw new IllegalArgumentException("second argument to size() is null");

        if (lo.compareTo(hi) > 0) return 0;
        if (contains(hi)) return rank(hi) - rank(lo) + 1;
        else              return rank(hi) - rank(lo);//hi太大了就为N，跑出数组范围了
    }

    /**
     *
     * @param lo:low
     * @param hi:high
     * @return
     */
    public Iterable<Key> keys(Key lo, Key hi) {
        if (lo == null) throw new IllegalArgumentException("first argument to keys() is null");
        if (hi == null) throw new IllegalArgumentException("second argument to keys() is null");

        Queue<Key> queue = new LinkedList<>();
        if (lo.compareTo(hi) > 0) return queue;
        for (int i = rank(lo); i < rank(hi); i++)
            queue.add(keys[i]);
        if (contains(hi)) queue.add(keys[rank(hi)]);
        return queue;
    }

    public Iterable<Key> keys() {
        return keys(min(), max());
    }


}
