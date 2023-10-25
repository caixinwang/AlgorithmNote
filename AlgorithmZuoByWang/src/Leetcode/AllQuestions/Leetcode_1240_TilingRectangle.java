package Leetcode.AllQuestions;

public class Leetcode_1240_TilingRectangle {
    //模拟：dfs。直接整一个二维矩阵，从左上角开始填，一行一行的填。
    //1.从大正方形开始尝试，方便剪枝

    int ans;

    public int tilingRectangle(int n, int m) {
        ans = max(n, m);
        boolean[][] matrix = new boolean[n][m];
        dfs(matrix, n, m, 0, 0, 0);
        return ans;
    }

    public void dfs(boolean[][] matrix, int n, int m, int row, int col, int cnt) {
        if (cnt >= ans) return;
        if (row >= n) {
            ans = cnt;
            return;
        }
        if (col >= m) {
            dfs(matrix, n, m, row + 1, 0, cnt);
            return;
        }
        if (matrix[row][col]) {
            dfs(matrix, n, m, row, col + 1, cnt);
            return;
        }
        int len = min(n - row, m - col);
        for (int i = len; i >= 1; i--) {
            if (check(matrix, row, col, i)) {
                fill(matrix, row, col, i, true);
                dfs(matrix, n, m, row, col + i, cnt + 1);
                fill(matrix, row, col, i, false);
            }
        }
    }

    //len是正方形的边长，从1开始;返回能不能在row,col位置放一个len长度的瓷砖
    public boolean check(boolean[][] matrix, int row, int col, int len) {
        for (int i = row; i < row + len; i++) {
            for (int j = col; j < col + len; j++) {
                if (matrix[i][j]) return false;
            }
        }
        return true;
    }

    public void fill(boolean[][] matrix, int row, int col, int len, boolean v) {
        for (int i = row; i < row + len; i++) {
            for (int j = col; j < col + len; j++) {
                matrix[i][j] = v;
            }
        }
    }

    public int min(int a, int b) {
        return a < b ? a : b;
    }

    public int max(int a, int b) {
        return a > b ? a : b;
    }

    public static void main(String[] args) {
        new Leetcode_1240_TilingRectangle().tilingRectangle(3,4);
    }
}
