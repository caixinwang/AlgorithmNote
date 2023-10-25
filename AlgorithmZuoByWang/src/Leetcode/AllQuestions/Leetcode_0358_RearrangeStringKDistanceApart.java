package Leetcode.AllQuestions;

import TestUtils.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;

// 本题的解法思路与leetcode 621题 TaskScheduler 问题是一样的
public class Leetcode_0358_RearrangeStringKDistanceApart {

    public static String rearrangeString(String s, int k) {
        if (s == null || s.length() < k) {
            return s;
        }
        char[] str = s.toCharArray();
        int[][] cnts = new int[256][2];
        for (int i = 0; i < 256; i++) {
            cnts[i] = new int[]{i, 0};
        }
        int maxCount = 0;
        for (char task : str) {
            cnts[task][1]++;
            maxCount = Math.max(maxCount, cnts[task][1]);
        }
        int maxKinds = 0;
        for (int task = 0; task < 256; task++) {
            if (cnts[task][1] == maxCount) {
                maxKinds++;
            }
        }
        int N = str.length;
        if (!isValid(N, k, maxCount, maxKinds)) {
            return "";
        }
        ArrayList<StringBuilder> ans = new ArrayList<>();
        for (int i = 0; i < maxCount; i++) {
            ans.add(new StringBuilder());
        }
        Arrays.sort(cnts, (a, b) -> (b[1] - a[1]));
        int i = 0;
        for (; i < 256 && cnts[i][1] == maxCount; i++) {
            for (int j = 0; j < maxCount; j++) {
                ans.get(j).append((char) cnts[i][0]);
            }
        }
        int out = 0;
        for (; i < 256; i++) {
            for (int j = 0; j < cnts[i][1]; j++) {
                ans.get(out).append((char) cnts[i][0]);
                out = out == ans.size() - 2 ? 0 : out + 1;
            }
        }
        StringBuilder builder = new StringBuilder();
        for (StringBuilder b : ans) {
            builder.append(b.toString());
        }
        return builder.toString();
    }

    public static boolean isValid(int N, int k, int maxCount, int maxKinds) {
        int restTasks = N - maxKinds;
        int spaces = k * (maxCount - 1);
        return spaces - restTasks <= 0;
    }

    public static String rearrangeString2(String s, int k) {
        if (k == 0) return s;
        char[] str = s.toCharArray();
        int[][] cnt = new int[26][2];//[[次数，字符]......]
        for (int i=0;i<26;i++) cnt[i][1]='a'+i;//初始化字符，次数默认都是0
        int maxcnt = 0, maxsame = 0, n = str.length;
        for (char c : str) cnt[c - 'a'][0]++;//统计词频
        Arrays.sort(cnt,(a,b)->b[0]-a[0]);//从大到小排完序之后，这个次数对应的字符是什么，就需要二维的信息来确定了
        maxcnt=cnt[0][0];//总共有maxcnt组
        for (int[] times : cnt) if (times[0] == maxcnt) maxsame++;
        if (n - maxsame < k * (maxcnt - 1)) return "";//说明去除掉最后一组，剩下的没有办法填平间隙
        StringBuilder[] sbs = new StringBuilder[maxcnt];
        for (int i = 0; i < sbs.length; i++) sbs[i] = new StringBuilder();
        for (int i=0;i<maxsame;i++){//次数最多的几个可以填到最后一组，其余的都不行
            for (int j = 0; cnt[i][0]-- > 0; j++) {
                sbs[j].append((char) (cnt[i][1]));
            }
        }
        for (int i=maxsame,j=0; i < cnt.length; i++) {
            for (; cnt[i][0]-- > 0; j=j+1<maxcnt-1?j+1:0) {
                sbs[j].append((char) (cnt[i][1]));
            }
        }
        StringBuilder ans=new StringBuilder();
        for (StringBuilder sb : sbs)ans.append(sb);
        return ans.toString();
    }


    public static int max(int a, int b) {
        return a > b ? a : b;
    }

    static StringUtil stringUtil=new StringUtil();
    public static void main(String[] args) {
        for (int t=0;t<100000;t++){
            String s=stringUtil.generateRandom_a_z_String(stringUtil.ran(6,8));
            int k=stringUtil.ran(2,3);
            String ans1= rearrangeString(s,k);
            String ans2= rearrangeString2(s,k);
            if (!ans1.equals(ans2)){
                System.out.println(s);
                System.out.println(k);
                System.out.println("========");
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
    }
}
