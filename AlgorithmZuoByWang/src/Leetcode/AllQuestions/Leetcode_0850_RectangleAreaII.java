package Leetcode.AllQuestions;

import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

public class Leetcode_0850_RectangleAreaII {

    static final int MOD = (int) 1e9 + 7;

    public static int rectangleArea(int[][] rectangles) {//扫描线
        int[][] lines = new int[rectangles.length << 1 | 1][4];//扫描线数组，两倍长度，[x,y1,y2,A]
        TreeSet<Integer> set = new TreeSet<>();//离散化
        int i = 0;
        for (int[] rec : rectangles) {
            int x1 = rec[0], y1 = rec[1], x2 = rec[2], y2 = rec[3];
            lines[i++] = new int[]{x1, y1, y2, 1};
            lines[i++] = new int[]{x2, y1, y2, -1};
            set.addAll(List.of(y1, y2));
        }
        Arrays.sort(lines, (a, b) -> a[0] - b[0]);//根据x坐标排序
        int size = set.size();//y坐标的不同个数有size个
        int[] Y = new int[size + 1];//下标从1开始
        i = 1;
        for (int n : set) {
            Y[i++] = n;
        }
        SegmentTree segmentTree = new SegmentTree(size,Y);
        segmentTree.build();
        long ans = 0;
        for (i = 0; i + 1 < lines.length; i++) {
            int[] line = lines[i];
            int[] line2 = lines[i + 1];
            segmentTree.add(line[1], line[2], line[3]);
            ans += (long) (line2[0] - line[0]) * segmentTree.queryAll();
        }
        return (int) (ans % MOD);
    }

    static class SegmentTree {//外部传的l和r是离散化的参数，我们内部封装了其对应的真实值

        class Node {
            int len, l, r, cnt;
        }

        int N;
        Node[] nodes;
        int[] Y;//离散化的y坐标的真实值 Y[i] -> xxxx

        public SegmentTree(int n,int[] Y) {
            N = n;
            nodes = new Node[N << 3];
            this.Y=Y;
            for (int i = 0; i < nodes.length; i++) {
                nodes[i] = new Node();
            }
        }

        public void build() {
            build(1, N, 1);
        }

        //这里的l和r范围是1~N
        public void build(int l, int r, int rt) {//将映射关系初始化到我们的node里面
            nodes[rt].l = Y[l];
            nodes[rt].r = Y[r];
            if (r == l + 1) return;//叶子结点的宽度为2
            int mid = l + (r - l >> 1);
            build(l, mid, rt << 1);//这里的左右孩子是有交集的
            build(mid, r, rt << 1 | 1);
        }

        private void pushUp(int rt) {
            if (nodes[rt].cnt >= 1) nodes[rt].len = nodes[rt].r - nodes[rt].l;//如果标记大于1次，那么长度就是rt代表的区间的长度
            else nodes[rt].len = nodes[rt << 1].len + nodes[rt << 1 | 1].len;//否则有效长度就从左右孩子来
        }

        private void add(int L, int R, int A, int rt) {//内部接口不再是l,r,rt了，l和r被我们藏到了node里面
            int r = nodes[rt].r, l = nodes[rt].l;//l和r进来了才知道，因为我们在内部做了映射
            if (r <= L || l >= R) return;//判越界
            if (L <= l && r <= R) {
                nodes[rt].cnt += A;
                pushUp(rt);
                return;
            }
            add(L, R, A, rt << 1);
            add(L, R, A, rt << 1 | 1);
            pushUp(rt);
        }

        public void add(int L, int R, int A) {//外部调用接口的时候，传入的y是没有离散过的，值可能很大
            add(L, R, A, 1);
        }

        public int queryAll() {//总是查根结点
            return nodes[1].len;
        }
    }

    public static int rectangleArea2(int[][] rectangles) {//扫描线
        int[][] lines = new int[rectangles.length << 1][4];//扫描线数组，两倍长度，[x,y1,y2,A]
        int i = 0;
        for (int[] rec : rectangles) {
            int x1 = rec[0], y1 = rec[1]+1, x2 = rec[2], y2 = rec[3]+1;//线段树表示的范围从1开始
            lines[i++] = new int[]{x1, y1, y2, 1};
            lines[i++] = new int[]{x2, y1, y2, -1};
        }
        Arrays.sort(lines, (a, b) -> a[0] - b[0]);//根据x坐标排序
        SegmentTree2 segmentTree = new SegmentTree2((int)(1e9+7));
        long ans = 0;
        for (i = 0; i + 1 < lines.length; i++) {
            int[] line = lines[i];//计算加载line和line2之间的面积
            int[] line2 = lines[i + 1];
            segmentTree.add(line[1], line[2], line[3]);//线段树表示的范围从1开始
            ans += (long) (line2[0] - line[0]) * segmentTree.queryAll();
        }
        return (int) (ans % MOD);
    }

    static class SegmentTree2 {//动态开点线段树，直接传进原始值，不需要离散化
        class Node{
            Node left,right;
            int len,cnt;
        }
        int N;
        Node root;

        public SegmentTree2(int maxSize) {
           N=maxSize;
           root=new Node();
        }

        private void pushUp(Node cur,int l,int r) {
            if (cur.cnt>=1) cur.len=r-l;//标记不为0，那么就是自己所代表区间的长度
            else cur.len=(cur.left!=null?cur.left.len:0)+(cur.right!=null?cur.right.len:0);
        }

        private void add(int L, int R, int A, int l,int r,Node cur) {//内部接口不再是l,r,rt了，l和r被我们藏到了node里面
            if (l>=R||r<=L) return;//越界，这句不加的话也可以在下面卡，加上mid>L  mid<R这样的条件，这样叶子的长度才会大于等于2
            if (L <= l && r <= R) {
                cur.cnt+=A;
                pushUp(cur,l,r);
                return;
            }
            int mid=l+(r-l>>1);
            if (cur.left==null) cur.left=new Node();
            if (cur.right==null) cur.right=new Node();
            add(L,R,A,l,mid,cur.left);//有重合
            add(L,R,A,mid,r,cur.right);
            pushUp(cur,l,r);
        }

        public void add(int L, int R, int A) {//外部调用接口的时候，传入的y是没有离散过的，值可能很大。
            add(L, R, A, 1,N,root);
        }

        public int queryAll() {//总是查根结点
            return root.len;
        }

    }


    public static void main(String[] args) {
        int[][] rectangles = new int[][]{
//                {0,0,2,2},
//                {1,0,2,3},
//                {1,0,3,1}
                {0, 0, 1000000000, 1000000000}
        };
        System.out.println(rectangleArea2(rectangles));
    }

}
