package Leetcode.AllQuestions;

public class Leetcode_0036_ValidSudoku {

    /**
     * row[i][1]代表第i行，没有填过数字1
     * col[j][1]代表第j列，没有填过数字1
     * grid[k][1]代表第k个方块，没有填过数字1。board[i][j]到第k块方块的映射为：k= (i/3)*3 + (j/3) = (9*i+j)/9
     *
     * @param board 初始棋盘
     * @return 判断棋盘合不合法
     */
    public static boolean isValidSudoku(char[][] board) {
        boolean[][] row = new boolean[9][9];
        boolean[][] col = new boolean[9][9];
        boolean[][] grid = new boolean[9][9];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] != '.') {
                    int k = (i / 3) * 3 + (j / 3);
                    int index = board[i][j] - '1';
                    if (row[i][index] || col[j][index] || grid[k][index]) return false;
                    row[i][index] = true;
                    col[j][index] = true;
                    grid[k][index] = true;
                }
            }
        }
        return true;
    }

}
