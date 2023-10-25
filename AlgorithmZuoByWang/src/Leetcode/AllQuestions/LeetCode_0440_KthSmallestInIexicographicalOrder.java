package Leetcode.AllQuestions;

public class LeetCode_0440_KthSmallestInIexicographicalOrder {
    /**
     * 反过来怎么做，问你num排第几个。
     * @param n 从1写到n
     * @param k 按照字典序排，第k个是多少？
     * @return -
     */
    public static int findKthNumber(int n, int k) {//(lng n)^2
        int cur = 1;//从第一个结点 1 出发
        while (k > 0) {
            int nodes = getNodes(n, cur);//以cur为头的子树的结点个数
            if (k - nodes > 0) {// >0就可以把这整棵树跳过
                k -= nodes;
                cur++;//右走
            }else {//就算是等于0，也要往下走，最终是往右走到撞上
                k--;
                if (k>0) cur *= 10;//往下走
            }
        }
        return cur;
    }

    private static int getNodes(int n, long cur) {//以cur为头的整棵树有多少结点
        int ans = 0;
        int base = 1;
        while (cur <= n) {
//            if (cur + base - 1 <= n) ans += base;
//            else ans += n - cur + 1;
            ans+=Math.min(n - cur + 1,base);
            base *= 10;
            cur *= 10;
        }
        return ans;
    }

    /**
     * 下面这种解法思路：
     *  1.如果以1开头，4位，没有限制，总共有多少？1~9 10~19 100~199 1000~1999 ==>1+10+100+1000=1111
     *  所以如果以一个数开头有n位，没有限制，那么总共就是11111总共n个1。
     *  2.任意给你一个数 679，679= 5*111 + ? + 3*11  这三个部分。
     *  那么你叫我求第k位，那么我就可以看这个k来自于这三个部分的哪一个部分。分治！
     *  举个例子：假设你要我求1~6655的第100位，我们知道第一部分包括了1~5555，那么我们就知道第一百个应该去左边找！
     *  4.那么问题来了中间的部分怎么知道有多少个，例如6655，中间部分就是必须以6开头<=3位的有111个，加上严格4位的有655+1个，加起来即可。
     *  5.为了递归的含义的传递，我们怎么知道去左边找以谁开头的第几个呢？
     *
     * 这个方法看看就行，太难写了
     */

    public static int[] offset = { 0, 1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000, 1000000000 };

    public static int[] number = { 0, 1, 11, 111, 1111, 11111, 111111, 1111111, 11111111, 111111111, 1111111111 };

    public static int findKthNumber2(int n, int k) {//log n
        // 数字num，有几位，len位
        // 65237, 5位，len = 5
        int len = len(n);
        // 65237, 开头数字，6，first
        int first = n / offset[len];
        // 65237，左边有几个？
        int left = (first - 1) * number[len];
        int pick = 0;
        int already = 0;
        if (k <= left) {
            // k / a 向上取整-> (k + a - 1) / a
            pick = (k + number[len] - 1) / number[len];
            already = (pick - 1) * number[len];
            return kth((pick + 1) * offset[len] - 1, len, k - already);
        }
        int mid = number[len - 1] + (n % offset[len]) + 1;
        if (k - left <= mid) {
            return kth(n, len, k - left);
        }
        k -= left + mid;
        len--;
        pick = (k + number[len] - 1) / number[len] + first;
        already = (pick - first - 1) * number[len];
        return kth((pick + 1) * offset[len] - 1, len, k - already);
    }

    public static int len(int n) {
        int len = 0;
        while (n != 0) {
            n /= 10;
            len++;
        }
        return len;
    }

    public static int kth(int max, int len, int kth) {
        // 中间范围还管不管的着！
        // 有任何一步，中间位置没命中，左或者右命中了，那以后就都管不着了！
        // 但是开始时，肯定是管的着的！
        boolean closeToMax = true;
        int ans = max / offset[len];
        while (--kth > 0) {
            max %= offset[len--];
            int pick = 0;
            if (!closeToMax) {
                pick = (kth - 1) / number[len];
                ans = ans * 10 + pick;
                kth -= pick * number[len];
            } else {
                int first = max / offset[len];
                int left = first * number[len];
                if (kth <= left) {
                    closeToMax = false;
                    pick = (kth - 1) / number[len];
                    ans = ans * 10 + pick;
                    kth -= pick * number[len];
                    continue;
                }
                kth -= left;
                int mid = number[len - 1] + (max % offset[len]) + 1;
                if (kth <= mid) {
                    ans = ans * 10 + first;
                    continue;
                }
                closeToMax = false;
                kth -= mid;
                len--;
                pick = (kth + number[len] - 1) / number[len] + first;
                ans = ans * 10 + pick;
                kth -= (pick - first - 1) * number[len];
            }
        }
        return ans;
    }


}
