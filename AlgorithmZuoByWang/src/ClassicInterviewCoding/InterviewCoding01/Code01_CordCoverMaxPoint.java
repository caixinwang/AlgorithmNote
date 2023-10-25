package ClassicInterviewCoding.InterviewCoding01;

import java.util.Arrays;

/**
 * 给定一个有序数组arr,从左到右依次表示X轴上从左往右点的位置
 * 给定一个正整数K,返回如果有一根长度为K的绳子，最多能盖住几个点
 * 绳子的边缘点碰到X轴上的点，也算盖住
 */
public class Code01_CordCoverMaxPoint {

    /**
     *
     * @param arr :arr[i]代表一个具体的点
     * @param L 代表绳子的长度
     * @return 返回绳子能盖住的最多的点的个数
     */
    public static int maxPoint1(int[] arr, int L) {
        int res = 0;
        for (int i = 0; i < arr.length; i++) res = Math.max(res, nearestIndex(arr, i, arr[i] + L) - i + 1);
        return res;
    }
    public static int nearestIndex(int[] arr, int L, int value) {//在[L,arr.len-1]范围上找到小于value的最大值对应的下标
        int l = L, r = arr.length - 1, mid = 0;
        while (l <= r) {
            mid = (r - l) / 2 + l;
            if (arr[mid] <= value) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }

        }
        return r < L ? -1 : r;
    }

    /**
     * @param arr :arr[i]代表一个具体的点
     * @param L 代表绳子的长度
     * @return 返回绳子能盖住的最多的点的个数
     */
    public static int maxPoint2(int[] arr, int L) {
        int l = 0, r = 0, res = 0;//window:[l,r)
        while (r < arr.length) {//退出循环需要再更新一次
            if (arr[r] <= arr[l] + L) {
                r++;
            } else {
                res = Math.max(res, r - l);//r不能再扩了就更新
                l++;
            }
        }
        res = Math.max(res, r - l);//r走到头了，直接最后更新一波答案
        return res;
    }

    // for test
    public static int test(int[] arr, int L) {
        int max = 0;
        for (int i = 0; i < arr.length; i++) {
            int pre = i - 1;
            while (pre >= 0 && arr[i] - arr[pre] <= L) {
                pre--;
            }
            max = Math.max(max, i - pre);
        }
        return max;
    }

    // for test
    public static int[] generateArray(int len, int max) {
        int[] ans = new int[(int) (Math.random() * len) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (int) (Math.random() * max);
        }
        Arrays.sort(ans);
        return ans;
    }

    public static void main(String[] args) {
        int len = 100;
        int max = 1000;
        int testTime = 1000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int L = (int) (Math.random() * max);
            int[] arr = generateArray(len, max);
            int ans1 = maxPoint1(arr, L);
            int ans2 = maxPoint2(arr, L);
            int ans3 = test(arr, L);
            if (ans1 != ans2 || ans2 != ans3) {
                System.out.println("oops!");
                break;
            }
        }

    }

}
