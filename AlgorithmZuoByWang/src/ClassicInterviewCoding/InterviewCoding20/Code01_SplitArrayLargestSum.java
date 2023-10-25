package ClassicInterviewCoding.InterviewCoding20;

import TestUtils.ArrayUtil;

public class Code01_SplitArrayLargestSum {

    /**
     * @param nums 将nums数组划分成m份，求所有划分中m份最大值的最小值
     * @param m    -
     * @return -
     */
    public static int splitArray1(int[] nums, int m) {
        if (nums == null || m < 1 || nums.length < m) return 0;
        return process(nums, 0, m);
    }

    /**
     * @param arr   数组
     * @param index 从arr[index...N-1]自由选择，给我分出part份
     * @param part  ·
     * @return 返回怎么分可以让part份中的最大值最小，把这个最小的最大值返回
     */
    public static int process(int[] arr, int index, int part) {
        if (index == arr.length && part == 0) return 0;
        if (index == arr.length || part == 0) return -1;
        int res = Integer.MAX_VALUE;
        int first = 0;
        for (int e = index; e < arr.length - (part - 1); e++) {
            first += arr[e];//第一部分
            int next = process(arr, e + 1, part - 1);//其他部分
            if (next != -1) res = Math.min(res, Math.max(first, next));//这种划分合理才去更新res
        }
        return res;
    }

    public static int splitArray2(int[] arr, int m) {
        if (arr == null || m < 1 || arr.length < m) return 0;
        int N = arr.length;
        int[][] dp = new int[N + 1][m + 1];
//		for (int i = 0; i <= m; i++) {
//			dp[N][i]=-1;
//		}
//		for (int i = 0; i <= N; i++) {
//			dp[i][0]=-1;
//		}
//		dp[N][0]=0;//这句话放上面就完蛋。有递归了可以不在外面初始化了，直接搬到里面去初始化
        for (int index = N; index >= 0; index--) {//观察递归，依赖下面的和左边的。所以从下往上从左往右
            for (int part = 0; part <= m; part++) {
                if (index == arr.length && part == 0) continue;
                if (index == arr.length || part == 0) {
                    dp[index][part] = -1;
                    continue;
                }
                int res = Integer.MAX_VALUE;
                int first = 0;
                for (int e = index; e < arr.length - (part - 1); e++) {
                    first += arr[e];//第一部分
                    int next = dp[e + 1][part - 1];//其他部分
                    if (next != -1) res = Math.min(res, Math.max(first, next));//这种划分合理才去更新res
                }
                dp[index][part] = res;
            }
        }
        return dp[0][m];
    }

    /**
     * 对上面的动态规划做一个四边形不等式的优化。我们拿右边的作为下界，拿下面的作为上界。所以我们的顺序是从下往上，从右往左。
     * 这题比邮局问题的难点。因为这题有-1的存在，你要多去判断一次。你每次要去拿下面和右边的时候都去看一下那里的best是不是为-1，
     * 如果为-1说明这个地方用不了。你没填好一个有意义的dp点的时候，只要有进到那个if里面，best就会更新，就会变得有意义了。
     * 一开始把best全部初始化为-1是有道理的，我们只去解锁可以帮助我们的best。其它一律不解锁，以防出现奇怪的错误
     * 这里会出现有意义和无意义的讨论，主要是我们是直接从动态规划改过来的。如果我们直接憋动态规划，保证每个点都有意义，那么你就
     * 不需要讨论是否有意义了。
     * dp[i][j]的含义为：i~N-1做选择，凑出j份中最大的最小返回。
     * 上界和下界怎么判断？一般都是拿下边和右边。分析一下，拿右边，j增大，k推的没这么远，所以做下界。拿下面，i增大，k推的肯定比i时远，
     * 所以作为上界。
     */
    public static int splitArray3(int[] arr, int m) {
        if (arr == null || m < 1 || arr.length < m) return 0;
        int N = arr.length;
        int[] sum = new int[N + 1];
        for (int i = 1; i < sum.length; i++) {//arr[i...j] = sum[j+1]-sum[i]
            sum[i] = sum[i - 1] + arr[i - 1];//sum[i]代表arr[0...i-1]
        }
        int[][] dp = new int[N + 1][m + 1];
        int[][] best = new int[N + 1][m + 1];
        for (int i = 0; i <= N; i++) {
            for (int j = 0; j <= m; j++) {
                dp[i][j] = -1;
                best[i][j] = -1;//很重要
            }
        }
        dp[N][0] = 0;//这句话放上面就完蛋。有递归了可以不在外面初始化了，直接搬到里面去初始化
        for (int index = N - 1; index >= 0; index--) {//从倒数第三行开始
            for (int part = m; part >= 1; part--) {//从右往左。拿右边的时候可能会越界，没越界还要看不为-1。
                int res = Integer.MAX_VALUE;
                int bottom = part + 1 > m ? index : best[index][part + 1] == -1 ? index : best[index][part + 1];//考虑越界和-1
                int limit = best[index + 1][part] == -1 ? N - part : best[index + 1][part];//考虑-1
                int first = bottom == index ? 0 : sum[bottom] - sum[index];
                for (int e = bottom; e <= limit; e++) {
                    first += arr[e];//第一部分
                    int next = dp[e + 1][part - 1];//其他部分
                    if (next != -1 && Math.max(first, next) < res) {
                        res = Math.max(first, next);//这种划分合理才去更新res}
                        best[index][part] = e;//但凡进到这里一次，这个格子就有意义了。
                    }
                    dp[index][part] = res;
                }
            }
        }
        return dp[0][m];
    }

    /**
     * 上面的动态规划是从暴力递归直接改的，有-1的讨论。我们这里自己憋动态规划，直接保证我们填的格子都是有意义的。
     * 这样可以通过一些初始化轻易的保证best可以直接取到有意义的值，从而不需要去讨论无意义的情况
     * dp[i][j] arr[0...i]自由选择，在所有严格凑出j份的方法中，把所有情况中j份中最大值的最小情况返回
     * 具体做法就是初始化前两列。以及i+1=j这条斜线，斜线往上都是无意义的。第一列也是无意义的。这样一来我们从中间
     * 的位置开始填写，由于一开始左边的第二列初始化了，best有意义，并且斜线也被我们初始化了，所以右边也有意义。
     * 所以只要右边不越界，我们就可以拿到有意义的best。
     * 第二列初始化的时候，肯定k都推到0位置了，所以默认就是初始化好的。初始化斜线的时候，k肯定只能推到自己。
     */
    public static int splitArray4(int[] arr, int M) {
        if (arr == null || M < 1 || arr.length < M) return 0;
        int N = arr.length;
        int[] sum = new int[N + 1];
        for (int i = 1; i < sum.length; i++) {//arr[i...j] = sum[j+1]-sum[i]
            sum[i] = sum[i - 1] + arr[i - 1];//sum[i]代表arr[0...i-1]
        }
        int[][] dp = new int[N][M + 1];
        int[][] best = new int[N][M + 1];
        for (int i = 0; i < N; i++) {//第二列
            dp[i][1] = sum[i + 1] - sum[0];
        }
        for (int i = 1; i + 1 <= M && i < N; i++) {//i+1是列的坐标,i是行的坐标
            dp[i][i + 1] = Math.max(dp[i - 1][i], arr[i]);
            best[i][i + 1] = i;
        }
        for (int i = 2; i < N; i++) {
            for (int j = Math.min(i, M); j >= 2; j--) {
                int limit = j + 1 <= M ? best[i][j + 1] : i;
                int bottom = best[i - 1][j];
                int res = Integer.MAX_VALUE;
                int first = (limit == i ? 0 : sum[i + 1] - sum[limit + 1]);//arr[k...i]
                for (int k = limit; k >= bottom && k >= j - 1; k--) {
//                    first+=arr[k];
                    first = sum[i + 1] - sum[k];//这句也行，上句也行
                    if (Math.max(dp[k - 1][j - 1], first) < res) {
                        res = Math.max(dp[k - 1][j - 1], first);
                        best[i][j] = k;
                    }
                }
                dp[i][j] = res;
            }
        }
        return dp[N - 1][M];
    }

    public static int splitArray5(int[] arr, int M) {
        long sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
        }
        long res = 0;
        long l = 0;
        long r = sum;
        while (l <= r) {
            long mid = l + (r - l >> 1);
            int needParts = getNeedParts(arr, mid);
            if (needParts > M) {
                l = mid + 1;
            } else {
                res = mid;//最后这个mid就会很小了，刚好卡在那个最大值的最小
                r = mid - 1;
            }
        }
        return (int) res;
    }

    /**
     * @param arr arr里面的数，去分块，要求每一块的累加和都<=aim，返回最少需要多少块
     * @param aim -
     * @return -
     */
    public static int getNeedParts(int[] arr, long aim) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > aim) return Integer.MAX_VALUE;
        }
        int parts = 0;
        int sum = 0;
        int i = 0;
        while (i < arr.length) {
            if (sum + arr[i] <= aim) {
                sum += arr[i++];
            } else {
                parts++;
                sum = 0;
            }
        }
        return ++parts;//最后一个sum没有超出，要算出来
    }

    public static void testForArr() {//参数为arr
        ArrayUtil arrayUtil = new ArrayUtil();
        int times = 100;//测试次数
        long time1 = 0, time2 = 0;
        boolean isok = true;
        int maxSize = 100;//数组大小在[0~maxSize]随机
        int maxValue = 100;//数组的值在[0,maxValue]随机
        int parameter1 = 0;//测试函数的参数
        int maxParameter1 = 20;//参数1在[0,maxParameter1]随机
        int[] t1 = null, t2 = null;

        int res1 = 0, res2 = 0;
        for (int i = 0; i < times; i++) {

            t1 = arrayUtil.generateRandomArr(arrayUtil.ran(maxSize), maxValue);
            parameter1 = arrayUtil.ran(2, t1.length);
//            t2=arrayUtil.generateRandomArr(arrayUtil.ran(maxSize),maxValue);

//            t1=arrayUtil.generateRandomArr(arrayUtil.ran(maxSize),1,maxValue);//正数数组[1,maxValue]
//            t2 = arrayUtil.generateRandomArr(arrayUtil.ran(maxSize), 1, maxValue);//正数数组[1,maxValue]

            long l = System.currentTimeMillis();
            res1 = splitArray3(t1, parameter1);
            time1 += System.currentTimeMillis() - l;
            l = System.currentTimeMillis();
            res2 = splitArray4(t1, parameter1);
            time2 += System.currentTimeMillis() - l;
            if (res1 != res2) {
                isok = false;
                break;
            }
        }
        arrayUtil.printArr(t1);//打印参数
//        arrayUtil.printArr(t2);//打印参数
//        System.out.println("parameter1:"+parameter1);//打印参数
        if (isok) System.out.println("m1 cost " + time1 + "ms");
        System.out.println(res1);//针对返回值的操作
        if (isok) System.out.println("m2 cost " + time2 + "ms");
        System.out.println(res2);//针对返回值的操作
        System.out.println(isok ? "success" : "fail");
    }

    public static void main(String[] args) {
        testForArr();
    }
}
