package BasicLearning.Class13_SegmentTree;

public class Code01_DynamicSegmentTree2 {//max

    /**
     * 支持单点增加 + 范围查询的动态开点线段树（max）
     * arr[0] -> 1
     * 线段树，从1开始下标!
     */
    public static class DynamicSegmentTree {
        class Node {
            public int max;
            public Node left;
            public Node right;
        }

        public Node root;
        public int size;

        public DynamicSegmentTree(int max) {
            root = new Node();
            size = max;
        }

        // 下标i这个位置的数，增加v
        public void add(int i, int v) {
            add(root, 1, size, i, v);
        }

        // c-> cur 当前节点！表达的范围 l~r
        // i位置的数，增加v
        // 潜台词！i一定在l~r范围上！
        private void add(Node c, int l, int r, int i, int v) {
            if (l == r) {
                c.max += v;
            } else { // l~r 还可以划分
                int mid = (l + r) / 2;
                if (i <= mid) { // l ~ mid
                    if (c.left == null) {
                        c.left = new Node();
                    }
                    add(c.left, l, mid, i, v);
                } else {  // mid + 1 ~ r
                    if (c.right == null) {
                        c.right = new Node();
                    }
                    add(c.right, mid + 1, r, i, v);
                }
                c.max = Math.max((c.left != null ? c.left.max : 0), (c.right != null ? c.right.max : 0));
            }
        }

        // s~e范围的累加和，告诉我！
        public int query(int s, int e) {
            return query(root, 1, size, s, e);
        }

        // 当前节点c，表达的范围l~r
        // 收到了一个任务，s~e这个任务！
        // s~e这个任务，影响了多少l~r范围的数，把答案返回！
        private int query(Node c, int l, int r, int s, int e) {
            if (c == null) return 0;
            if (s <= l && r <= e) { // 3~6  1~100任务
                return c.max;
            }
            int mid = (l + r) / 2;
            if (e <= mid) {
                return query(c.left, l, mid, s, e);
            } else if (s > mid) {
                return query(c.right, mid + 1, r, s, e);
            } else {
                return Math.max(query(c.left, l, mid, s, e), query(c.right, mid + 1, r, s, e));
            }
        }

    }

    /**
     * 同时支持范围增加 + 范围修改 + 范围查询的动态开点线段树（累加和）
     * 真的用到！才去建立
     * 懒更新，及其所有的东西，和普通线段树，没有任何区别！
     */
    public static class DynamicSegmentTree2 {
        class Node {
            public int max;
            public int lzadd;
            public int update;
            public boolean lzupdate;
            public Node left;
            public Node right;
        }

        public Node root;
        public int size;

        public DynamicSegmentTree2(int max) {
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
            if (p.lzupdate) {
                p.left.lzupdate = true;
                p.right.lzupdate = true;
                p.left.update = p.update;
                p.right.update = p.update;
                p.left.lzadd = 0;
                p.right.lzadd = 0;
                p.left.max = p.update;
                p.right.max = p.update;
                p.lzupdate = false;
            }
            if (p.lzadd != 0) {
                p.left.lzadd += p.lzadd;
                p.right.lzadd += p.lzadd;
                p.left.max += p.lzadd;
                p.right.max += p.lzadd;
                p.lzadd = 0;
            }
        }

        public void update(int s, int e, int v) {
            update(root, 1, size, s, e, v);
        }

        private void update(Node c, int l, int r, int s, int e, int v) {
            if (s <= l && r <= e) {
                c.lzupdate = true;
                c.update = v;
                c.max = v;
                c.lzadd = 0;
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

        public void add(int s, int e, int v) {
            add(root, 1, size, s, e, v);
        }

        private void add(Node c, int l, int r, int s, int e, int v) {
            if (s <= l && r <= e) {
                c.max += v;
                c.lzadd += v;
            } else {
                int mid = (l + r) >> 1;
                pushDown(c, mid - l + 1, r - mid);
                if (s <= mid) {
                    add(c.left, l, mid, s, e, v);
                }
                if (e > mid) {
                    add(c.right, mid + 1, r, s, e, v);
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


}
