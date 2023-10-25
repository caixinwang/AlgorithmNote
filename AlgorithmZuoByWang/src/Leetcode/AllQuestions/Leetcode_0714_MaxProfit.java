package Leetcode.AllQuestions;

public class Leetcode_0714_MaxProfit {
    public int maxProfit(int[] p, int fee) {
        int N=p.length;
        int[][] f = new int[N+1][2];
        for (int i = N-1; i >= 0; i--) {
            f[i][1] = Math.max(f[i + 1][1], p[i] + f[i + 1][0]);
            f[i][0] = Math.max(f[i + 1][0], -fee-p[i] + f[i + 1][1]);//改这里
        }
        return f[0][0];
    }
}
