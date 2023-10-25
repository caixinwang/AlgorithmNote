package OtherCoding.CCF_CSP;//package OtherCoding.CCF_CSP;

import java.util.Scanner;

public class Code_202303_4 {

    public static class DynamicSegmentTree {
        class Node {
            public int max;
            public int min;
            public int lazy;
            public int change;
            public boolean update;
            public Node left;
            public Node right;
        }

        public Node root;
        public int size;

        public DynamicSegmentTree(int max) {
            root = new Node();
            size = max;
        }

        private int merge(Node l,Node r){
            return Math.max(l==null?0:l.max,r==null?0:r.max);
        }

        private void pushUp(Node cur) {
            cur.max = merge(cur.left,cur.right);
        }

        private void pushDown(Node p, int ln, int rn) {
            if (p.left == null) {
                p.left = new Node();
            }
            if (p.right == null) {
                p.right = new Node();
            }
            if (p.update) {
                p.left.update = true;
                p.right.update = true;
                p.left.change = p.change;
                p.right.change = p.change;
                p.left.lazy = 0;
                p.right.lazy = 0;
                p.left.max = p.change;
                p.right.max = p.change;
                p.update = false;
            }
            if (p.lazy != 0) {
                p.left.lazy += p.lazy;
                p.right.lazy += p.lazy;
                p.left.max += p.lazy;
                p.right.max += p.lazy;
                p.lazy = 0;
            }
        }

        public void update(int s, int e, int v) {
            update(root, 1, size, s, e, v);
        }

        private void update(Node c, int l, int r, int s, int e, int v) {
            if (s <= l && r <= e) {
                c.update = true;
                c.change = v;
                c.max = v;
                c.lazy = 0;
            } else {
                int mid = (l + r) >> 1;
                pushDown(c, mid - l + 1, r - mid);
                if (s <= mid) {
                    update(c.left, l, mid, s, e, v);
                }
                if (e > mid) {
                    update(c.right, mid + 1, r, s, e, v);
                }
                pushUp(c);
            }
        }

        public int query(int s, int e) {
            return query(root, 1, size, s, e);
        }

        private int query(Node c, int l, int r, int s, int e) {
            if (s <= l && r <= e) {
                return c.max;
            }
            int mid = (l + r) >> 1;
            pushDown(c, mid - l + 1, r - mid);
            int p1 = Integer.MIN_VALUE;
            int p2 = Integer.MIN_VALUE;
            if (s <= mid) {
                p1 = query(c.left, l, mid, s, e);
            }
            if (e > mid) {
                p2 = query(c.right, mid + 1, r, s, e);
            }
            return Math.max(p1,p2);
        }

    }

    public static void slove(String[] queries){

    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            int n = in.nextInt();//n位二进制的地址
            int q = in.nextInt();
            in.nextLine();
            String[] queries=new String[q];
            for (int i = 0; i < queries.length; i++) {
                queries[i]=in.nextLine();
            }

        }
        in.close();
    }


}
