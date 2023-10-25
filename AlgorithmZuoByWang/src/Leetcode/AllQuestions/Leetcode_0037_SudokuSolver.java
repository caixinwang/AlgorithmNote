package Leetcode.AllQuestions;

public class Leetcode_0037_SudokuSolver {

    public void solveSudoku(char[][] board) {
        boolean[][] row = new boolean[9][9];
        boolean[][] col = new boolean[9][9];
        boolean[][] grid = new boolean[9][9];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] != '.') {
                    int k = (i / 3) * 3 + (j / 3);
                    int index = board[i][j] - '1';
                    row[i][index] = true;
                    col[j][index] = true;
                    grid[k][index] = true;
                }
            }
        }
        process(board, 0, 0, row, col, grid);
    }

    /**
     * 注意恢复现场
     * @param board 数独棋盘
     * @param i 当前填到了i行j列
     * @param j -
     * @param row row[i][num]代表board的i行已经填过num这个数字了
     * @param col col[j][num]代表board的j列已经填过num这个数字了
     * @param grid grid[k][num]代表board的第k块已经填过num这个数字了
     * @return 返回当前状态下，从(i,j)位置往下填，能不能成功填好
     */
    private boolean process(char[][] board, int i, int j, boolean[][] row, boolean[][] col, boolean[][] grid) {
        if (i == 9) return true;
        if (board[i][j] == '.') {
            for (char num = '1'; num <= '9'; num++) {
                int index = num - '1';
                int k = (i / 3) * 3 + (j / 3);
                if (!row[i][index] && !col[j][index] && !grid[k][index]) {
                    board[i][j] = num;
                    row[i][index] = true;
                    col[j][index] = true;
                    grid[k][index] = true;
                    if (process(board, j == 8 ? i + 1 : i, j == 8 ? 0 : j + 1, row, col, grid)) return true;
                    row[i][index] = false;
                    col[j][index] = false;
                    grid[k][index] = false;
                    board[i][j] = '.';
                }
            }
            return false;//说明这个空格子说明都填不了。因为如果填了，会在for循环中就return了
        } else {
            return process(board, j == 8 ? i + 1 : i, j == 8 ? 0 : j + 1, row, col, grid);
        }
    }

}
