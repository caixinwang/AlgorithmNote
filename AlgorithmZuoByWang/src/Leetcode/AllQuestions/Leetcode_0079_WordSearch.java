package Leetcode.AllQuestions;

public class Leetcode_0079_WordSearch {

    public static boolean exist(char[][] board, String word) {
        char[] w = word.toCharArray();
        int N = board.length;
        int M = board[0].length;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == w[0]) {
                    boolean[][] walked = new boolean[N][M];
                    walked[i][j] = true;
                    if (f(board, i, j, walked, 1, w)) return true;
                }
            }
        }
        return false;
    }

    /**
     * 登上去之前检查
     * @param board -
     * @param sr 当前在（sr，sc）位置，该位置已经匹配
     * @param sc -
     * @param walked 建立不走回头路的机制
     * @param index index~len-1的位置需要匹配
     * @param word 匹配串
     * @return 返回能否凑出来
     */
    public static boolean f(char[][] board, int sr, int sc, boolean[][] walked, int index, char[] word) {
        if (index == word.length) return true;
        int N = board.length, M = board[0].length;
        char c = word[index];//当前要凑的字符
        if (sr + 1 < N && !walked[sr + 1][sc] && board[sr + 1][sc] == c) {
            walked[sr + 1][sc] = true;
            if (f(board, sr + 1, sc, walked, index + 1, word)) return true;
            walked[sr + 1][sc] = false;
        }
        if (sr - 1 >= 0 && !walked[sr - 1][sc] && board[sr - 1][sc] == c) {
            walked[sr - 1][sc] = true;
            if (f(board, sr - 1, sc, walked, index + 1, word)) return true;
            walked[sr - 1][sc] = false;
        }
        if (sc + 1 < M && !walked[sr][sc + 1] && board[sr][sc + 1] == c) {
            walked[sr][sc + 1] = true;
            if (f(board, sr, sc + 1, walked, index + 1, word)) return true;
            walked[sr][sc + 1] = false;
        }
        if (sc - 1 >= 0 && !walked[sr][sc - 1] && board[sr][sc - 1] == c) {
            walked[sr][sc - 1] = true;
            if (f(board, sr, sc - 1, walked, index + 1, word)) return true;
            walked[sr][sc - 1] = false;
        }
        return false;
    }

    public static boolean exist2(char[][] board, String word) {
        char[] w = word.toCharArray();
        int N = board.length;
        int M = board[0].length;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (f2(board,i,j,0,w)) return true;
            }
        }
        return false;
    }

    /**
     * 登上去之后检查
     * 不走回头路的机制用board本身就行，因为只包含字母
     * @param board -
     * @param sr (sr,sc)位置还没有检查
     * @param sc -
     * @param index -
     * @param word -
     * @return -
     */
    public static boolean f2(char[][] board, int sr, int sc, int index, char[] word) {
        if (index==word.length) return true;
        if (sr<0||sr>= board.length||sc<0||sc>=board[0].length) return false;
        if (board[sr][sc]!=word[index]) return false;
        char c=board[sr][sc];
        board[sr][sc]=0;
        boolean ans=false;
        ans=f2(board,sr+1,sc,index+1,word)||f2(board,sr-1,sc,index+1,word)
            ||f2(board,sr,sc+1,index+1,word)||f2(board,sr,sc-1,index+1,word);
        board[sr][sc]=c;
        return ans;
    }

}
