package OtherCoding.InterView;

import TestUtils.ArrayUtil;

import java.util.HashMap;

// 来自京东
// 把一个01字符串切成多个部分，要求每一部分的0和1比例一样，同时要求尽可能多的划分
// 比如 : 01010101
// 01 01 01 01 这是一种切法，0和1比例为 1 : 1
// 0101 0101 也是一种切法，0和1比例为 1 : 1
// 两种切法都符合要求，但是那么尽可能多的划分为第一种切法，部分数为4
// 比如 : 00001111
// 只有一种切法就是00001111整体作为一块，那么尽可能多的划分，部分数为1
// 给定一个01字符串str，假设长度为N，要求返回一个长度为N的数组ans
// 其中ans[i] = str[0...i]这个前缀串，要求每一部分的0和1比例一样，同时要求尽可能多的划分下，部分数是多少
// 输入: str = "010100001"
// 输出: ans = [1, 1, 1, 2, 1, 2, 1, 1, 3]
public class Code0016_Ratio01Split {

    // 001010010100...
    public static int[] split(int[] arr) {

        // key : 分子
        // value : 属于key的分母表, 每一个分母，及其 分子/分母 这个比例，多少个前缀拥有
        HashMap<Integer, HashMap<Integer, Integer>> pre = new HashMap<>();
        int n = arr.length;
        int[] ans = new int[n];
        int zero = 0; // 0出现的次数
        int one = 0; // 1出现的次数
        for (int i = 0; i < n; i++) {
            if (arr[i] == 0) {
                zero++;
            } else {
                one++;
            }
            if (zero == 0 || one == 0) {
                ans[i] = i + 1;
            } else { // 0和1，都有数量 -> 最简分数
                int gcd = gcd(zero, one);
                int a = zero / gcd;
                int b = one / gcd;
                // a / b 比例，之前有多少前缀拥有？ 3+1 4 5+1 6
                if (!pre.containsKey(a)) {
                    pre.put(a, new HashMap<>());
                }
                if (!pre.get(a).containsKey(b)) {
                    pre.get(a).put(b, 1);
                } else {
                    pre.get(a).put(b, pre.get(a).get(b) + 1);
                }
                ans[i] = pre.get(a).get(b);
            }
        }
        return ans;
    }

    public static int gcd(int m, int n) {
        return n == 0 ? m : gcd(n, m % n);
    }

    public static int[] split2(int[] arr) {
        int N = arr.length;
        int[] ans = new int[N];
        int zeros = 0, ones = 0;
        HashMap<Integer, HashMap<Integer, Integer>> map = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            zeros += arr[i] == 0 ? 1 : 0;
            ones += arr[i] == 1 ? 1 : 0;
            if (zeros == 0 || ones == 0) {
                ans[i] = zeros != 0 ? zeros : ones;
                continue;
            }
            int gcd = gcd(zeros, ones);
            int a = zeros / gcd;
            int b = ones / gcd;
            if (!map.containsKey(a)) map.put(a, new HashMap<>());
            HashMap<Integer, Integer> map2 = map.get(a);
            if (!map2.containsKey(b)) map2.put(b, 1);
            else map2.put(b, 1 + map2.get(b));
            ans[i] = map2.get(b);
        }
        return ans;
    }

    static ArrayUtil au = new ArrayUtil();

    public static void main(String[] args) {
        int t = 1000;
        for (int i = 0; i < t; i++) {
            int[] arr = au.generateRandomArr(au.ran(1, 100), 0, 1);
            int[] ans1 = split(arr);
            int[] ans2 = split2(arr);
            if (!au.isEqual(ans1,ans2)){
                System.out.println("opps");
            }
        }
        System.out.println("ok");

    }

}
