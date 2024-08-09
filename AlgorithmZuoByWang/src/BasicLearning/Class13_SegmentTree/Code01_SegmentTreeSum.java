package BasicLearning.Class13_SegmentTree;

public class Code01_SegmentTreeSum {//sum
    /**
     * 每个线段树都自己定制需要维持的数据，这个经典的线段树维持的就是sum这个数据。
     * lzadd、lzupdate、update都是为了sum维持的懒空间，为了提供更高的效率。
     * 这种带有懒更新的结构，适合范围修改，如果都是单点修改就不需要用懒更新结构了
     */
    public static class SegmentTree {
        private int N;//origin.len
        private int[] sum;// sum[]模拟线段树维护区间和,这里根据
        private int[] lzadd;// lzadd[]为懒增加标记
        private int[] update;// update[]为更新的值,和下面配合使用
        private boolean[] lzupdate;// lzupdate[]为懒更新标记

        /**
         * @param N 创建的线段树管理1~N的任意范围
         */
        public SegmentTree(int N) {//直接开辟4N大小来表示完全二叉树
            this.N=N;
            sum = new int[N<<2]; // 某一个结点的累加和信息
            lzadd = new int[N<<2]; // 某一个结点沒有往下傳遞的纍加任務
            update = new int[N<<2]; // 某一个结点有没有更新操作的任务
            lzupdate = new boolean[N<<2]; // 某一个结点更新成了什么
        }

        /**
         * 构造满二叉树，如果管理1~6，本质上底层是按照1~8来创建的线段树
         * 根据求和公式，满二叉树最后一层如果是all，那么总的结点数量就是all*2-1。
         * all/2是倒二层的最后一个结点，all/2+1就是倒一层的第一个结点，这里从左到右对应origin的每一个数
         * 填完最后一层以后，从倒数第二层倒着pushUp回去，这样就初始化了原始的数据
         * @param origin 原始的数据。有的题目可能没有原始的数据
         */
        public void build(int[] origin){
            //满二叉树
            // int lay=1;
            // for (;lay<N;lay<<=1);//lay出来刚好大于等于N,作为最后一层的数量
            // int all=2*lay-1;//满二叉树总共的节点数。下标从1开始
            // for (int i=all/2+1,j=0;j<origin.length;i++,j++){
            //     sum[i]=origin[j];
            // }
            // for (int i=all>>1;i>=1;i--){
            //     pushUp(i);
            // }
            
            //完全二叉树
            build(1,1,N,origin);
        }

        //构造完全二叉树
        public int build(int rt,int l,int r,int[] origin){
            if(l>r) return 0;
            if(l==r) return sum[rt]=origin[l-1];
            int m=l+(r-l>>1);
            return sum[rt]=build(rt<<1, l, m, origin)+build(rt<<1|1, m+1, r, origin);
        }


        /**
         * 每个不同的题目有不同的pushup，sum的话就是左右孩子相加，如果是max的话就是左右孩子求最大
         * @param rt 此时代表的根节点下标
         */
        private void pushUp(int rt) {//通过左右孩子把自己的值加出来
            sum[rt] = sum[rt << 1] + sum[rt << 1 | 1];
        }

        // l~r这个范围结点之前的所有懒增加和懒更新发给左右两个子范围结点,rt代表这个范围的结点对应的下标
        // 懒更新的if大语句块一定放在懒增加的if语句之前！因为懒更新完了之后是没有懒增加的

        /**
         * 题目一般都会有update，总而言之，update写在前面，如果有add，再往后面写add
         * @param l -
         * @param r -
         * @param rt 范围[l,r]的代表结点的下标
         */
        private void pushDown(int l, int r, int rt) {//结算在rt中缓着的懒东西
            int mid = l + r >> 1;//中点
            if (lzupdate[rt]) {//之前有懒更新--没有更新孩子的只更新了自己的叫做懒更新，也就是没有下发任务
                lzupdate[rt << 1] = true;//左孩子
                lzupdate[rt << 1 | 1] = true;//右孩子
                update[rt << 1] = update[rt];
                update[rt << 1 | 1] = update[rt];
                lzadd[rt << 1] = 0;
                lzadd[rt << 1 | 1] = 0;
                sum[rt << 1] = (mid - l + 1) * update[rt];//[l,mid]的个数
                sum[rt << 1 | 1] = (r - mid) * update[rt];//[mid+1,r]的个数
                lzupdate[rt] = false;
            }
            if (lzadd[rt] != 0) {//之前有懒增加
                lzadd[rt << 1] += lzadd[rt];//注意这里是+=而不是覆盖
                lzadd[rt << 1 | 1] += lzadd[rt];
                sum[rt << 1] += lzadd[rt] * (mid - l + 1);//[l,mid]的个数
                sum[rt << 1 | 1] += lzadd[rt] * (r - mid);//[mid+1,r]的个数
                lzadd[rt] = 0;
            }
        }

        private void update(int L, int R, int U, int l, int r, int rt) {
            if (L <= l && r <= R) {//任务把我包住了，说明我要为这个任务付出一切,并且不需要通知下级了
                lzupdate[rt] = true;
                update[rt] = U;
                lzadd[rt] = 0;//更新任务来了，说明我之前攒的懒增加也不需要告诉下级了，因为他们也要更新了
                sum[rt] = (r - l + 1) * U;
            } else {//当前任务没有把我包住，说明我不用亲自出门，交给下面的小弟来做即可，交给小弟做之前得把之前自己攒的懒东西告诉小弟
                pushDown(l, r, rt);//把自己私藏的小弟的信息都告诉给小弟
                int mid = l + r >> 1;
                if (L <= mid) update(L, R, U, l, mid, rt << 1);//如果任务有部分落在左半边,把任务交给左孩子去做
                if (R >= mid + 1) update(L, R, U, mid + 1, r, rt << 1 | 1);//如果任务有部分落在右半边，把任务交给右孩子去做
                pushUp(rt);//让左右孩子更新完了sum之后别忘了把自己的sum也更新好。
            }
        }

        public void update(int L, int R, int U) {//对外提供的接口
            if (L < 1 || R > N) return;
            update(L, R, U, 1, N, 1);
        }

        // L..R -> 任务范围 ,所有的值累加上C
        // l,r -> 表达的范围
        // rt  去哪找l，r范围上的信息
        private void add(int L, int R, int A, int l, int r, int rt) {
            if (L <= l && r <= R) {//自己是要更新的，但是小弟不给他更新，等需要用到小弟的时候再告诉他--pushDown
                lzadd[rt] += A;
                sum[rt] += (r - l + 1) * A;
            } else {
                pushDown(l, r, rt);
                int mid = l + r >> 1;
                if (L <= mid) add(L, R, A, l, mid, rt << 1);//如果任务有部分落在左半边,把任务交给左孩子去做
                if (R >= mid + 1) add(L, R, A, mid + 1, r, rt << 1 | 1);//如果任务有部分落在右半边，把任务交给右孩子去做
                pushUp(rt);//让左右孩子更新完了sum之后别忘了把自己的sum也更新好。
            }
        }

        public void add(int L, int R, int A) {//对外提供的接口，用户也要提供从1开始的下标
            if (L < 1 || R > N) return;
            add(L, R, A, 1, N, 1);
        }

        //   1~6 累加和是多少？ 1~8   rt
        private long query(int L, int R, int l, int r, int rt) {
            if (L <= l && r <= R) {
                return sum[rt];
            } else {
                pushDown(l, r, rt);
                long ans = 0;
                int mid = l + r >> 1;
                if (L <= mid) ans += query(L, R, l, mid, rt << 1);//如果任务有部分落在左半边,把任务交给左孩子去做
                if (R >= mid + 1) ans += query(L, R, mid + 1, r, rt << 1 | 1);//如果任务有部分落在右半边，把任务交给右孩子去做
                return ans;
            }
        }


        public long query(int L, int R) {
            if (L < 1 || R > N) throw new RuntimeException();
            return query(L, R, 1, N, 1);
        }
    }

    static class SegmentTree2{//无懒惰
        int[] sum;
        int N;

        public SegmentTree2(int N){
            this.N=N;
            sum = new int[N<<2];
        }

        public void build(int[] origin){
            for (int i = 0; i < origin.length; i++) {
                update(i+1,i+1,origin[i]);
            }
        }

        public int merge(int left,int right){
            return left+right;
        }

        public int query(int L,int R){
            return query(L,R,1,N,1);
        }

        private int query(int L, int R, int l, int r, int rt) {
            if (L<=l&&r<=R){
                return sum[rt];
            }
            int mid=l+(r-l>>1);
            int p1=0,p2=0;
            if (mid>=L) p1=query(L,R,l,mid,rt<<1);
            if (mid+1<=R) p2=query(L,R,mid+1,r,rt<<1|1);
            return merge(p1,p2);
        }

        public void update(int L,int R,int U){
            update(L,R,U,1,N,1);
        }

        //递归到底的去修改
        private void update(int L, int R, int U, int l, int r, int rt) {
            if(l==r){//递归到底
                sum[rt]=U;
                return;
            }
            int mid=l+(r-l>>1);
            if (mid>=L) update(L,R,U,l,mid,rt<<1);
            if (mid+1<=R) update(L,R,U,mid+1,r,rt<<1|1);
            sum[rt]=merge(sum[rt<<1], sum[rt<<1|1]);
        }

        public void add(int L,int R,int A){
            add(L,R,A,1,N,1);
        }

        private void add(int L, int R, int A, int l, int r, int rt){
            if(l==r){//递归到底
                sum[rt]+=A;
                return;
            }
            int mid=l+(r-l>>1);
            if (mid>=L) add(L,R,A,l,mid,rt<<1);
            if (mid+1<=R) add(L,R,A,mid+1,r,rt<<1|1);
            sum[rt]=merge(sum[rt<<1], sum[rt<<1|1]);
        }

    }


    public static int[] genarateRandomArray(int len, int max) {
        int size = (int) (Math.random() * len) + 1;
        int[] origin = new int[size];
        for (int i = 0; i < size; i++) {
            origin[i] = (int) (Math.random() * max) - (int) (Math.random() * max);
        }
        return origin;
    }

    public static boolean test() {
        int len = 10;
        int max = 1000;
        int testTimes = 5000;
        int addOrUpdateTimes = 1000;
        int queryTimes = 500;
        for (int i = 0; i < testTimes; i++) {
            int[] origin = genarateRandomArray(len, max);
            SegmentTree seg = new SegmentTree(origin.length);//1
            seg.build(origin);
            int S = 1;
            int N = origin.length;
            int root = 1;
            SegmentTree2 rig = new SegmentTree2(origin.length);//2
            rig.build(origin);
            for (int j = 0; j < addOrUpdateTimes; j++) {
                int num1 = (int) (Math.random() * N) + 1;
                int num2 = (int) (Math.random() * N) + 1;
                int L = Math.min(num1, num2);
                int R = Math.max(num1, num2);
                int C = (int) (Math.random() * max) - (int) (Math.random() * max);
                if (Math.random() < 0.5) {
                    seg.add(L, R, C);
                    rig.add(L, R, C);
                } else {
                    seg.update(L, R, C);
                    rig.update(L, R, C);
                }
            }
            for (int k = 0; k < queryTimes; k++) {
                int num1 = (int) (Math.random() * N) + 1;
                int num2 = (int) (Math.random() * N) + 1;
                int L = Math.min(num1, num2);
                int R = Math.max(num1, num2);
                long ans1 = seg.query(L, R);
                long ans2 = rig.query(L, R);
                if (ans1 != ans2) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int[] origin = {2, 1, 1, 2, 3, 4};
        SegmentTree seg = new SegmentTree(origin.length);
        seg.build(origin);
        int L = 2; // 操作区间的开始位置 -> 可变
        int R = 5; // 操作区间的结束位置 -> 可变
        int C = 4; // 要加的数字或者要更新的数字 -> 可变
        // 区间生成，必须在[S,N]整个范围上build
        // 区间修改，可以改变L、R和C的值，其他值不可改变
        seg.add(L, R, C);
        // 区间更新，可以改变L、R和C的值，其他值不可改变
        seg.update(L, R, C);
        // 区间查询，可以改变L和R的值，其他值不可改变
        long sum = seg.query(L, R);
        System.out.println(sum);

        System.out.println("对数器测试开始...");
        System.out.println("测试结果 : " + (test() ? "通过" : "未通过"));

    }
}
