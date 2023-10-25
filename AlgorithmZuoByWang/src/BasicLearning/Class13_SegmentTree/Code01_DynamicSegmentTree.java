package BasicLearning.Class13_SegmentTree;

public class Code01_DynamicSegmentTree {//累加和

    /**
     * 支持单点增加 + 范围查询的动态开点线段树（累加和）
     * 线段树，从1开始下标!
     * 不在结点内部维持范围属性，通过入参来维持。
     */
    public static class DynamicSegmentTree {
        class Node {
            public int sum;
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
                c.sum += v;
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
                c.sum = (c.left != null ? c.left.sum : 0) + (c.right != null ? c.right.sum : 0);
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
            if (c == null) {//从来没有动过你
                return 0;
            }
            if (s <= l && r <= e) { // 3~6  1~100任务
                return c.sum;
            }
            // 有影响，但又不是全影响
            // l ~ r
            // l~mid    mid+1~r
            int mid = (l + r) / 2;
            // 1~100
            // 1~50  51 ~ 100
            // 任务  s~e  53~76
            if (e <= mid) {
                return query(c.left, l, mid, s, e);
            } else if (s > mid) {
                return query(c.right, mid + 1, r, s, e);
            } else {
                return query(c.left, l, mid, s, e) + query(c.right, mid + 1, r, s, e);
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
            public int sum;
            public int lazy;
            public int change;
            public boolean update;
            public Node left;
            public Node right;
        }

        public Node root;
        public int size;

        public DynamicSegmentTree2(int max) {
            root = new Node();
            size = max;
        }

        private void pushUp(Node c) {
            c.sum = c.left.sum + c.right.sum;
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
                p.left.sum = p.change * ln;
                p.right.sum = p.change * rn;
                p.update = false;
            }
            if (p.lazy != 0) {
                p.left.lazy += p.lazy;
                p.right.lazy += p.lazy;
                p.left.sum += p.lazy * ln;
                p.right.sum += p.lazy * rn;
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
                c.sum = v * (r - l + 1);
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

        public void add(int s, int e, int v) {
            add(root, 1, size, s, e, v);
        }

        private void add(Node c, int l, int r, int s, int e, int v) {
            if (s <= l && r <= e) {
                c.sum += v * (r - l + 1);
                c.lazy += v;
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
                return c.sum;
            }
            int mid = (l + r) >> 1;
            pushDown(c, mid - l + 1, r - mid);
            int ans = 0;
            if (s <= mid) {
                ans += query(c.left, l, mid, s, e);
            }
            if (e > mid) {
                ans += query(c.right, mid + 1, r, s, e);
            }
            return ans;
        }

    }

    public static class Right {
        public int[] arr;

        public Right(int size) {
            arr = new int[size + 1];
        }

        public void add(int i, int v) {
            arr[i] += v;
        }

        public int query(int s, int e) {
            int sum = 0;
            for (int i = s; i <= e; i++) {
                sum += arr[i];
            }
            return sum;
        }

    }

    public static void main(String[] args) {
        int size = 10000;
        int testTime = 50000;
        int value = 500;
        DynamicSegmentTree dst = new DynamicSegmentTree(size);
        Right right = new Right(size);
        System.out.println("测试开始");
        for (int k = 0; k < testTime; k++) {
            if (Math.random() < 0.5) {
                int i = (int) (Math.random() * size) + 1;
                int v = (int) (Math.random() * value);
                dst.add(i, v);
                right.add(i, v);
            } else {
                int a = (int) (Math.random() * size) + 1;
                int b = (int) (Math.random() * size) + 1;
                int s = Math.min(a, b);
                int e = Math.max(a, b);
                int ans1 = dst.query(s, e);
                int ans2 = right.query(s, e);
                if (ans1 != ans2) {
                    System.out.println("出错了!");
                    System.out.println(ans1);
                    System.out.println(ans2);
                }
            }
        }
        System.out.println("测试结束");
    }

}
