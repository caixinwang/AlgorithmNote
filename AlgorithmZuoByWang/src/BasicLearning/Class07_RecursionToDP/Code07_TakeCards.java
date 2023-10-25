package BasicLearning.Class07_RecursionToDP;

public class Code07_TakeCards {

    private static int scoreOfWinner1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int first = f1(arr, 0, arr.length - 1);
        int second = s1(arr, 0, arr.length - 1);
        return Math.max(first, second);
    }

    private static int f1(int[] arr, int l, int r) {
        if (r == l) {
            return arr[l];
        }
        int case1 = s1(arr, l + 1, r) + arr[l];
        int case2 = s1(arr, l, r - 1) + arr[r];
        return Math.max(case1, case2);//一定给自己最有利的情况
    }

    private static int s1(int[] arr, int l, int r) {
        if (r == l) {
            return 0;
        }
        int case1 = f1(arr, l + 1, r);
        int case2 = f1(arr, l, r - 1);
        return Math.min(case1, case2);//对手是坏蛋，一定给你最差的情况
    }

    /**
     * 使用傻缓存方法
     *
     * @param arr
     * @return
     */
    private static int scoreOfWinner2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int N = arr.length;
        int dpf[][] = new int[N][N];
        int dps[][] = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                dpf[i][j] = -1;
                dps[i][j] = -1;
            }
        }

        int first = f2(arr, 0, arr.length - 1, dpf, dps);
        int second = s2(arr, 0, arr.length - 1, dpf, dps);
        return Math.max(first, second);
    }

    private static int f2(int[] arr, int l, int r, int[][] dpf, int[][] dps) {
        if (r == l) {
            return arr[l];
        }
        if (dpf[l][r] != -1) {
            return dpf[l][r];
        }
        int case1 = s2(arr, l + 1, r, dpf, dps) + arr[l];
        int case2 = s2(arr, l, r - 1, dpf, dps) + arr[r];

        dpf[l][r] = Math.max(case1, case2);//一定给自己最有利的情况
        return dpf[l][r];
    }

    private static int s2(int[] arr, int l, int r, int[][] dpf, int[][] dps) {
        if (r == l) {
            return 0;
        }
        if (dps[l][r] != -1) {
            return dps[l][r];
        }
        int case1 = f2(arr, l + 1, r, dpf, dps);
        int case2 = f2(arr, l, r - 1, dpf, dps);

        dps[l][r] = Math.min(case1, case2);//对手是坏蛋，一定给你最差的情况
        return dps[l][r];
    }

    /**
     * 动态规划版本
     *
     * @param arr
     * @return
     */
    private static int scoreOfWinner3(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int N = arr.length;
        int dpf[][] = new int[N][N];
        int dps[][] = new int[N][N];
        for (int i = 0; i < N; i++) {
            dpf[i][i] = arr[i];
        }
        for (int l = N - 2; l >= 0; l--) {//从下往上
            for (int r = l+1; r < N; r++) {//从左往右
                dpf[l][r] = Math.max(dps[l + 1][r] + arr[l], dps[l][r - 1] + arr[r]);
                dps[l][r] = Math.min(dpf[l + 1][r], dpf[l][r - 1]);
            }
        }
        return Math.max(dps[0][N - 1], dpf[0][N - 1]);
    }


    public static void main(String[] args) {
        int[] arr = {1, 2, 3,5,7,1,3,5};
        System.out.println(scoreOfWinner1(arr));
        System.out.println(scoreOfWinner2(arr));
        System.out.println(scoreOfWinner3(arr));
    }

}
