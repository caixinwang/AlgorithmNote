package OtherCoding.InterView;

import TestUtils.StringUtil;

import java.util.Arrays;

// 来自腾讯
// 给定一个只由0和1组成的字符串S，假设下标从1开始，规定i位置的字符价值V[i]计算方式如下 : 
// 1) i == 1时，V[i] = 1
// 2) i > 1时，如果S[i] != S[i-1]，V[i] = 1
// 3) i > 1时，如果S[i] == S[i-1]，V[i] = V[i-1] + 1
// 你可以随意删除S中的字符，返回整个S的最大价值
// 字符串长度<=5000
public class Code0028_01AddValue {

    public static int max1(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = s.toCharArray();
        int[] arr = new int[str.length];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = str[i] == '0' ? 0 : 1;
        }
        return process1(arr, 0, 0, 0);
    }

    // 递归含义 :
    // 目前在arr[index...]上做选择, str[index...]的左边，最近的数字是lastNum
    // 并且lastNum所带的价值，已经拉高到baseValue
    // 返回在str[index...]上做选择，最终获得的最大价值
    // index -> 0 ~ 4999
    // lastNum -> 0 or 1
    // baseValue -> 1 ~ 5000
    // 5000 * 2 * 5000 -> 5 * 10^7(过!)
    public static int process1(int[] arr, int index, int lastNum, int baseValue) {
        if (index == arr.length) {
            return 0;
        }
        int curValue = lastNum == arr[index] ? (baseValue + 1) : 1;
        // 当前index位置的字符保留
        int next1 = process1(arr, index + 1, arr[index], curValue);
        // 当前index位置的字符不保留
        int next2 = process1(arr, index + 1, lastNum, baseValue);
        return Math.max(curValue + next1, next2);
    }

    static int[][][] dp;

    //从左到右的尝试模型，不能修改原始串，需要记录累积到的分数以及对应的字符是什么
    public static int max2(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = s.toCharArray();
        int[] arr = new int[str.length];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = str[i] == '0' ? 0 : 1;
        }
        dp = new int[arr.length + 1][arr.length + 1][2];
        for (int a[][] : dp) for (int b[] : a) Arrays.fill(b, -1);
        return f(arr, 0, 0, 0);
    }


    private static int f(int[] arr, int index, int preScore, int preVal) {
        if (dp[index][preScore][preVal] != -1) return dp[index][preScore][preVal];
        if (index == arr.length) {
            dp[index][preScore][preVal] = 0;
            return dp[index][preScore][preVal];
        }
        int p1 = -1, p2 = -1;
        int curScore = preScore == 0 ? 1 : arr[index] == preVal ? preScore + 1 : 1;//preScore为零代表当前字符如果选了就是第一个个，那么分数自然是1
        p1 = curScore + f(arr, index + 1, curScore, arr[index]);
        p2 = f(arr, index + 1, preScore, preVal);
        dp[index][preScore][preVal] = Math.max(p1, p2);
        return dp[index][preScore][preVal];
    }

    // 请看体系学习班，动态规划章节，把上面的递归改成动态规划！看完必会

    static StringUtil su = new StringUtil();

    public static String generateStr(int size) {
        char[] str = new char[size];
        for (int i = 0; i < str.length; i++) {
            str[i] = su.ran(0, 1) == 0 ? '0' : '1';
        }
        return str.toString();
    }

    public static void main(String[] args) {
        int times = 1000;
        for (int i = 0; i < times; i++) {
            String s = generateStr(su.ran(1, 30));
            int ans1 = max1(s);
            int ans2 = max2(s);
            if (ans1 != ans2) {
                System.out.println("oops!");
                System.out.println(ans1);
                System.out.println(ans2);
            }
        }
    }

}
