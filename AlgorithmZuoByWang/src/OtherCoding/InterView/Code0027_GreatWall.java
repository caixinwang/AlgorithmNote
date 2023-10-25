package OtherCoding.InterView;

import TestUtils.ArrayUtil;

// 360笔试题
// 长城守卫军
// 题目描述：
// 长城上有连成一排的n个烽火台，每个烽火台都有士兵驻守。
// 第i个烽火台驻守着ai个士兵，相邻峰火台的距离为1。另外，有m位将军，
// 每位将军可以驻守一个峰火台，每个烽火台可以有多个将军驻守，
// 将军可以影响所有距离他驻守的峰火台小于等于x的烽火台。
// 每个烽火台的基础战斗力为士兵数，另外，每个能影响此烽火台的将军都能使这个烽火台的战斗力提升k。
// 长城的战斗力为所有烽火台的战斗力的最小值。
// 请问长城的最大战斗力可以是多少？
// 输入描述
// 第一行四个正整数n,m,x,k(1<=x<=n<=10^5,0<=m<=10^5,1<=k<=10^5)
// 第二行n个整数ai(0<=ai<=10^5)
// 输出描述 仅一行，一个整数，表示长城的最大战斗力
// 样例输入
// 5 2 1 2
// 4 4 2 4 4
// 样例输出
// 6
public class Code0027_GreatWall {

    public static int maxForce(int[] wall, int m, int x, int k) {
        long L = 0;
        long R = 0;
        for (int num : wall) {
            R = Math.max(R, num);
        }
        R += m * k;
        long ans = 0;
        while (L <= R) {
            long M = (L + R) / 2;
            if (can(wall, m, x, k, M)) {
                ans = M;
                L = M + 1;
            } else {
                R = M - 1;
            }
        }
        return (int) ans;
    }


    public static class SegmentTree {
        private int MAXN;
        private int[] arr;
        private int[] sum;
        private int[] lazy;
        private int[] change;
        private boolean[] update;

        public SegmentTree(int[] origin) {
            MAXN = origin.length + 1;
            arr = new int[MAXN];
            for (int i = 1; i < MAXN; i++) {
                arr[i] = origin[i - 1];
            }
            sum = new int[MAXN << 2];
            lazy = new int[MAXN << 2];
            change = new int[MAXN << 2];
            update = new boolean[MAXN << 2];
        }

        private void pushUp(int rt) {
            sum[rt] = sum[rt << 1] + sum[rt << 1 | 1];
        }

        private void pushDown(int rt, int ln, int rn) {
            if (update[rt]) {
                update[rt << 1] = true;
                update[rt << 1 | 1] = true;
                change[rt << 1] = change[rt];
                change[rt << 1 | 1] = change[rt];
                lazy[rt << 1] = 0;
                lazy[rt << 1 | 1] = 0;
                sum[rt << 1] = change[rt] * ln;
                sum[rt << 1 | 1] = change[rt] * rn;
                update[rt] = false;
            }
            if (lazy[rt] != 0) {
                lazy[rt << 1] += lazy[rt];
                sum[rt << 1] += lazy[rt] * ln;
                lazy[rt << 1 | 1] += lazy[rt];
                sum[rt << 1 | 1] += lazy[rt] * rn;
                lazy[rt] = 0;
            }
        }

        public void build(int l, int r, int rt) {
            if (l == r) {
                sum[rt] = arr[l];
                return;
            }
            int mid = (l + r) >> 1;
            build(l, mid, rt << 1);
            build(mid + 1, r, rt << 1 | 1);
            pushUp(rt);
        }

        public void update(int L, int R, int C, int l, int r, int rt) {
            if (L <= l && r <= R) {
                update[rt] = true;
                change[rt] = C;
                sum[rt] = C * (r - l + 1);
                lazy[rt] = 0;
                return;
            }
            int mid = (l + r) >> 1;
            pushDown(rt, mid - l + 1, r - mid);
            if (L <= mid) {
                update(L, R, C, l, mid, rt << 1);
            }
            if (R > mid) {
                update(L, R, C, mid + 1, r, rt << 1 | 1);
            }
            pushUp(rt);
        }

        public void add(int L, int R, int C, int l, int r, int rt) {
            if (L <= l && r <= R) {
                sum[rt] += C * (r - l + 1);
                lazy[rt] += C;
                return;
            }
            int mid = (l + r) >> 1;
            pushDown(rt, mid - l + 1, r - mid);
            if (L <= mid) {
                add(L, R, C, l, mid, rt << 1);
            }
            if (R > mid) {
                add(L, R, C, mid + 1, r, rt << 1 | 1);
            }
            pushUp(rt);
        }

        public long query(int L, int R, int l, int r, int rt) {
            if (L <= l && r <= R) {
                return sum[rt];
            }
            int mid = (l + r) >> 1;
            pushDown(rt, mid - l + 1, r - mid);
            long ans = 0;
            if (L <= mid) {
                ans += query(L, R, l, mid, rt << 1);
            }
            if (R > mid) {
                ans += query(L, R, mid + 1, r, rt << 1 | 1);
            }
            return ans;
        }

    }

    public static boolean can(int[] wall, int m, int x, int k, long limit) {
        int N = wall.length;
        // 注意：下标从1开始
        SegmentTree st = new SegmentTree(wall);
        st.build(1, N, 1);
        int need = 0;
        for (int i = 0; i < N; i++) {
            // 注意：下标从1开始
            long cur = st.query(i + 1, i + 1, 1, N, 1);
            if (cur < limit) {
                int add = (int) ((limit - cur + k - 1) / k);
                need += add;
                if (need > m) {
                    return false;
                }
                st.add(i + 1, Math.min(i + x, N), add * k, 1, N, 1);
            }
        }
        return true;
    }

    public static int maxForce2(int[] wall, int m, int x, int k) {
        long l = 0, r = 0;
        long min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        for (int num : wall) {
            min = Math.min(min, num);
            max = Math.max(max, num);
        }
        l = min;//战力的下限
        r = max + (long) m * k;//战力的上限
        while (l <= r) {//二分找到能提升的最大值
            long mid = l + (r - l >> 1);
            if (can2(wall, m, x, k, mid)) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return (int) r;
    }

    /**
     * @param wall  wall[i]代表i位置守军的初始战斗力
     * @param m     m个将军
     * @param x     将军可以影响距离自己 <=x 的烽火台，假设是单边影响
     * @param k     将军可以提升影响到的范围的烽火台k的战斗力
     * @param limit 当前的配置能不能将长城的战斗力刷到limit
     * @return 返回 true or false
     */
    public static boolean can2(int[] wall, int m, int x, int k, long limit) {
        int N=wall.length;
        SegmentTree2 segmentTree=new SegmentTree2(N);
        segmentTree.build(wall);
        for (int i=1;i<=N;i++){
            int force=segmentTree.query(i,i);
            if (force<limit){
                long times=(limit-force+k-1)/k;
                m-=times;
                if (m<0) return false;
                segmentTree.add(i,Math.min(N,i+x-1),(int)times*k);
            }
        }
        return true;
    }

    static class SegmentTree2 {
        int N;
        int[] sum;
        int[] lzadd;

        public SegmentTree2(int N) {
            this.N = N;
            sum = new int[N << 2];
            lzadd = new int[N << 2];
        }

        private int merge(int l, int r) {
            return l+r;
        }

        private void pushUp(int rt) {
            sum[rt] = merge(sum[rt << 1], sum[rt << 1 | 1]);
        }

        private void pushDown(int rt, int ln, int rn) {
            if (lzadd[rt] != 0) {
                sum[rt << 1] += ln*lzadd[rt];
                lzadd[rt << 1] += lzadd[rt];
                sum[rt << 1 | 1] += rn*lzadd[rt];
                lzadd[rt << 1 | 1] += lzadd[rt];
                lzadd[rt]=0;
            }
        }

        public void build(int[] origin){
            build(origin,1,origin.length,1);
        }

        private void build(int[] origin,int l,int r,int rt){
            if (l==r){//递归到底
                sum[rt]=origin[l-1];
                return;
            }
            int mid=l+(r-l>>1);
            build(origin,l,mid,rt<<1);
            build(origin,mid+1,r,rt<<1|1);
            pushUp(rt);
        }

        public void add(int L,int R,int A){
            add(L,R,A,1,N,1);
        }

        private void add(int L,int R,int A,int l,int r,int rt){
            if (L<=l&&r<=R){
                sum[rt]+=A*(r-l+1);
                lzadd[rt]+=A;
                return;
            }
            int mid=l+(r-l>>1);
            pushDown(rt,mid-l+1,r-mid);
            if (mid>=L) add(L,R,A,l,mid,rt<<1);
            if (mid<R) add(L,R,A,mid+1,r,rt<<1|1);
            pushUp(rt);
        }

        public int query(int L,int R){
            return query(L,R,1,N,1);
        }

        private int query(int L,int R,int l,int r,int rt){
            if (L<=l&&r<=R){
                return sum[rt];
            }
            int mid=l+(r-l>>1);
            pushDown(rt,mid-l+1,r-mid);
            int p1=0,p2=0;
            if (mid>=L) p1=query(L,R,l,mid,rt<<1);
            if (mid<R) p2=query(L,R,mid+1,r,rt<<1|1);
            return merge(p1,p2);
        }

    }
    static ArrayUtil au = new ArrayUtil();

    public static void main(String[] args) {
        int timr = 100;
        for (int i = 0; i < timr; i++) {
            int[] wall = au.generateRandomArr(au.ran(2, 1000), 1, 1000);
            int m = au.ran(1, 1000);
            int x = au.ran(1, 1000);
            int k = au.ran(1, 1000);
            int ans2 = maxForce2(au.copyArray(wall), m, x, k);
            int ans1 = maxForce(wall, m, x, k);
            if (ans1 != ans2) {
                System.out.println("opps!");
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }

    }

}
