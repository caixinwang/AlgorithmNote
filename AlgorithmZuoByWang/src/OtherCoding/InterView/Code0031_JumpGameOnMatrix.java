package OtherCoding.InterView;

import TestUtils.ArrayUtil;

import java.util.Arrays;

// 来自京东
// 给定一个二维数组matrix，matrix[i][j] = k代表:
// 从(i,j)位置可以随意往右跳<=k步，或者从(i,j)位置可以随意往下跳<=k步
// 如果matrix[i][j] = 0，代表来到(i,j)位置必须停止
// 返回从matrix左上角到右下角，至少要跳几次
// 已知matrix中行数n <= 5000, 列数m <= 5000
// matrix中的值，<= 5000
// 最弟弟的技巧也过了。最优解 -> dp+枚举优化(线段树，体系学习班)
public class Code0031_JumpGameOnMatrix {

    public static int jump1(int[][] map) {
        return f(map, 0, 0);
    }

    static final int MAX = Integer.MAX_VALUE;

    private static int f(int[][] map, int row, int col) {
        if (row == map.length - 1 && col == map[0].length - 1) {
            return 0;//算几步都一般都是在最后return0，因为在终点不需要跳
        }
        if (map[row][col] == 0) return MAX;//跳到了0位置说明没救了
        int N = map.length, M = map[0].length;
        int ans = MAX;//所有可能都去试，决出一个最好的ans
        for (int step = 1; step <= map[row][col]; step++) {
            if (row + step <= N - 1) ans = Math.min(ans, f(map, row + step, col));//往下跳
            if (col + step <= M - 1) ans = Math.min(ans, f(map, row, col + step));//往右跳
        }
        return ans == MAX ? MAX : ans + 1;
    }

    //从下往上，从右往左填，观察递归函数的依赖关系
    public static int jump2(int[][] map) {
        int N = map.length, M = map[0].length;
        int[][] dp = new int[N][M];
        for (int row = N - 1; row >= 0; row--) {
            for (int col = M - 1; col >= 0; col--) {
                if (row == N - 1 && col == M - 1) {
                    dp[row][col] = 0;
                    continue;
                }
                if (map[row][col] == 0) {
                    dp[row][col] = MAX;
                    continue;
                }
                int ans = MAX;//所有可能都去试，决出一个最好的ans
                for (int step = 1; step <= map[row][col]; step++) {
                    if (row + step <= N - 1) ans = Math.min(ans, dp[row + step][col]);//往下跳
                    if (col + step <= M - 1) ans = Math.min(ans, dp[row][col + step]);//往右跳
                }
                dp[row][col] = ans == MAX ? MAX : ans + 1;
            }
        }
        return dp[0][0];
    }

    public static int jump3(int[][] map) {
        int N = map.length, M = map[0].length;
        int[][] dp = new int[N][M];
        SegmentTree[] rowTrees = new SegmentTree[N];//找列的最小
        for (int i = 0; i < rowTrees.length; i++) rowTrees[i] = new SegmentTree(M);
        SegmentTree[] colTrees = new SegmentTree[M];//找行的最小
        for (int i = 0; i < colTrees.length; i++) colTrees[i] = new SegmentTree(N);
        for (int row = N - 1; row >= 0; row--) {
            for (int col = M - 1; col >= 0; col--) {
                if (row == N - 1 && col == M - 1) {
                    dp[row][col] = 0;
                } else if (map[row][col] == 0) {
                    dp[row][col] = MAX;
                } else {
                    int ans = Math.min(
                            rowTrees[row].query(col + 1, Math.min(M - 1, col + map[row][col])),
                            colTrees[col].query(row + 1, Math.min(N - 1, row + map[row][col])));
                    dp[row][col] = ans == MAX ? MAX : ans + 1;
                }
                colTrees[col].update(row, dp[row][col]);
                rowTrees[row].update(col, dp[row][col]);
            }
        }
        return dp[0][0];
    }

    static class SegmentTree {
        int N;
        int[] min;

        public SegmentTree(int size) {
            N = size;
            min = new int[N << 2];
            Arrays.fill(min, MAX);
        }

        private int merge(int l, int r) {
            return Math.min(l, r);
        }

        private void pushUp(int rt) {
            min[rt] = merge(min[rt << 1], min[rt << 1 | 1]);
        }

        public void update(int index, int U) {//外面调的是以0开头，我们手动+1
            update(index + 1, U, 1, N, 1);
        }

        private void update(int index, int U, int l, int r, int rt) {
            if (l == r) {
                min[rt] = U;
                return;
            }
            int mid = l + (r - l >> 1);
            if (mid >= index) update(index, U, l, mid, rt << 1);
            else update(index, U, mid + 1, r, rt << 1 | 1);
            pushUp(rt);
        }

        public int query(int L, int R) {
            return query(L + 1, R + 1, 1, N, 1);
        }

        private int query(int L, int R, int l, int r, int rt) {
            if (L <= l && r <= R) {
                return min[rt];
            }
            int mid = l + (r - l >> 1);
            int p1 = MAX, p2 = MAX;
            if (mid >= L) p1 = query(L, R, l, mid, rt << 1);
            if (mid < R) p2 = query(L, R, mid + 1, r, rt << 1 | 1);
            return merge(p1, p2);
        }
    }


    // 为了测试
    public static int[][] randomMatrix(int n, int m, int v) {
        int[][] ans = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                ans[i][j] = (int) (Math.random() * v);
            }
        }
        return ans;
    }

    public static void testForArr() {//参数为arr
        ArrayUtil au = new ArrayUtil();
        int times = 1;//测试次数
        long time1 = 0, time2 = 0;
        boolean isok = true;
        int maxSize = 100;//数组大小在[0~maxSize]随机
        int maxValue = 100;//数组的值在[0,maxValue]随机
        int[][] t1 = null, t2 = null;
        int res1 = 0, res2 = 0;
        for (int i = 0; i < times; i++) {
            int N=au.ran(1000,2000),M=au.ran(1000,2000);
            t1 = au.generateRandomMatrix(N,M,au.ran(0,2000));
            long l = System.currentTimeMillis();
            res1 = jump2(t1);
            time1 += System.currentTimeMillis() - l;
            l = System.currentTimeMillis();
            res2 = jump3(t1);
            time2 += System.currentTimeMillis() - l;
            if (res1 != res2) {
                isok = false;
                break;
            }
        }
//        au.printMatrix(t1);//打印参数
//        arrayUtil.printArr(t2);//打印参数
//        System.out.println("parameter1:"+parameter1);//打印参数
        if (isok) System.out.println("m1 cost " + time1 + "ms");
        System.out.println(res1);//针对返回值的操作
        if (isok) System.out.println("m2 cost " + time2 + "ms");
        System.out.println(res2);//针对返回值的操作
        System.out.println(isok ? "success" : "fail");
    }

    // 为了测试
    public static void main(String[] args) {
        testForArr();
    }

}
