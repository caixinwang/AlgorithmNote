package Leetcode.AllQuestions;

import java.util.Map;
import java.util.TreeMap;

public class Leetcode_0715_RangeModule {
     class RangeModule {
        class Node {
            int sum;
            int lzadd;
            boolean lzupdate;
            int update;
            Node left;
            Node right;

            public Node() {
                sum = 0;
                lzadd = 0;
                lzupdate=false;
                update=0;
                left = null;
                right = null;
            }
        }

        Node root;
        int N;

        public RangeModule() {
            this.N = (int)1e9+10;
            root = new Node();
        }

        private int merge(int l, int r) {
            return l + r;
        }

        private int merge(Node l, Node r) {
            return merge(l == null ? 0 : l.sum, r == null ? 0 : r.sum);
        }

        private void pushUp(Node cur) {
            cur.sum = merge(cur.left, cur.right);
        }

        private void pushDown(Node cur, int ln, int rn) {
            if (cur.left == null) cur.left = new Node();
            if (cur.right == null) cur.right = new Node();
            if (cur.lzupdate){
                cur.left.sum=ln*cur.update;
                cur.left.lzadd=0;
                cur.left.update=cur.update;
                cur.left.lzupdate=true;
                cur.right.sum=rn*cur.update;
                cur.right.lzadd=0;
                cur.right.update=cur.update;
                cur.right.lzupdate=true;
                cur.lzupdate=false;
            }
            if (cur.lzadd != 0) {
                cur.left.sum += cur.lzadd * ln;
                cur.left.lzadd += cur.lzadd;
                cur.right.sum += cur.lzadd * rn;
                cur.right.lzadd += cur.lzadd;
                cur.lzadd = 0;
            }
        }

        private void add(int L, int R, int A, int l, int r, Node cur) {
            if (L <= l && r <= R) {
                cur.lzadd += A;
                cur.sum += A*(r-l+1);
                return;
            }
            int mid = l + (r - l >> 1);
            pushDown(cur,mid-l+1,r-mid);
            if (mid >= L) add(L, R, A, l, mid, cur.left);
            if (mid < R) add(L, R, A, mid + 1, r, cur.right);
            pushUp(cur);
        }

        public void add(int L, int R, int A) {
            add(L, R, A, 1, N, root);
        }

        private void update(int L, int R, int U, int l, int r, Node cur){
            if (L <= l && r <= R) {
                cur.lzupdate=true;
                cur.update=U;
                cur.lzadd=0;
                cur.sum=U*(r-l+1);
                return;
            }
            int mid = l + (r - l >> 1);
            pushDown(cur,mid-l+1,r-mid);
            if (mid >= L) update(L, R, U, l, mid, cur.left);
            if (mid < R) update(L, R, U, mid + 1, r, cur.right);
            pushUp(cur);
        }
        public void update(int L, int R, int U) {
            update(L, R, U, 1, N, root);
        }

        private int query(int L, int R, int l, int r, Node cur) {
            if (L <= l && r <= R) {
                return cur.sum;
            }
            int mid = l + (r - l >> 1), p1 = 0, p2 = 0;
            pushDown(cur,mid-l+1,r-mid);
            if (mid >= L) p1 = query(L, R, l, mid, cur.left);
            if (mid < R) p2 = query(L, R, mid + 1, r, cur.right);
            return merge(p1, p2);
        }

        public int query(int L, int R) {
            return query(L, R, 1, N, root);
        }

        public void addRange(int left, int right) {
            int L = left, R = right - 1;
            update(L, R, 1, 1, N, root);
        }

        public boolean queryRange(int left, int right) {
            int L = left, R = right - 1;
            return query(L, R, 1, N, root) == right-left;
        }

        public void removeRange(int left, int right) {
            int L = left, R = right - 1;
            update(L, R, 0, 1, N, root);
        }
    }

    /**
     * 添加逻辑：就是让left~right，先往左扩，再往右扩，中间的全删，left~right最后加入
     * 删除逻辑：我们知道被left~right压中的都要删除，只有左右边界是特殊的，所以先把左右边界搞定，
     *          然后中间的一并删除即可。处理左边界的时候会修改值，所以右边界先处理
     */
     static class RangeModule2 {
        TreeMap<Integer,Integer> map;
        public RangeModule2(){
            map=new TreeMap<>();
        }
        public void addRange(int left, int right) {
            right--;
            Integer L = map.floorKey(left);//看看能否往左边阔
            if (L!=null&&left<=map.get(L)+1) left=L;//确定新区间的左边界
            Integer cur=map.ceilingKey(left);//去右边吞并区间
            while(cur!=null&&cur<=right+1){//cur可以被我们的新区间吞掉
                right = Math.max(right, map.get(cur));
                map.remove(cur);
                cur=map.ceilingKey(cur);
            }
            map.put(left,right);
        }

        public boolean queryRange(int left, int right) {
            right--;
            Integer L=map.floorKey(left);
            if (L!=null&&map.get(L)>=right) return true;
            return false;
        }

        public void removeRange(int left, int right) {
            right--;
            Integer L2=map.floorKey(right);
            if (L2!=null&&right<map.get(L2)) map.put(right+1,map.get(L2));
            Integer L1 = map.lowerKey(left);//找lowerKey，因为被left压中就没了
            if (L1!=null&&left<=map.get(L1)) map.put(L1,left-1);
            Integer cur=map.ceilingKey(left);//首尾边界搞定，中间的删即可
            while(cur!=null&&cur<=right){
                map.remove(cur);
                cur=map.ceilingKey(cur);
            }
        }
    }

    //["addRange","removeRange"]
    //[,[2,3],[1,8]]
    public static void main(String[] args) {
        RangeModule2 r=new RangeModule2();
        r.removeRange(4,8);
        r.addRange(1,10);
        System.out.println(r.queryRange(1,7));
        r.addRange(2,3);
        r.removeRange(2,3);
        System.out.println(r.queryRange(8,9));
        System.out.println(r.queryRange(6,9));
        r.addRange(2,3);
        r.removeRange(1,8);
    }

}
