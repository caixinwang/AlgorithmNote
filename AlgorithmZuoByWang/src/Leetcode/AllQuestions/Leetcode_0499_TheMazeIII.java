package Leetcode.AllQuestions;

import java.util.*;

public class Leetcode_0499_TheMazeIII {
    //用一次搞一层的宽度优先遍历。一个点关心四个指标，从四个方向的来的球拨了几次
    //但是这个宽度优先遍历，你要改方向的时候只有你撞墙了才能。也就是假设你从左边来到(x,y)，如果(x,y)的右边是墙，这时候才可以改方向
    //再来一个表记录防止重复入队
    static class Node {
        int row;
        int col;
        int direction;//上一个位置走了这个方向来到当前结点
        String path;

        public Node(int row, int col, int direction, String path) {
            this.row = row;
            this.col = col;
            this.direction = direction;
            this.path = path;
        }
    }

    static int[] trans = new int[]{0, 1, 0, -1, 0};//0 1 0 -1 0
    static String[] dire = new String[]{"r", "d", "l", "u"};

    public static String findShortestWay(int[][] maze, int[] ball, int[] hole) {
        int n = maze.length, m = maze[0].length;
        Queue<Node> queue = new LinkedList<>();
        boolean[][][] visit = new boolean[n][m][4];
        queue.add(new Node(ball[0], ball[1], -1, ""));//这个-1遇到了也要四个方向去入队
        String ans = null;
        while (!queue.isEmpty()) {
            List<int[]> updates=new ArrayList<>();
            for (int size = queue.size(), i = 0; i < size; i++) {//一层搞完之后我才去设置visit
                Node cur = queue.poll();
                int row = cur.row, col = cur.col, dire = cur.direction;
                if (row == hole[0] && col == hole[1]) {
                    if (ans == null || cur.path.compareTo(ans) < 0) {
                        ans = cur.path;
                    }
                    continue;
                }
                if (dire==-1 ||row + trans[dire] < 0 || col + trans[dire + 1] < 0
                        || row + trans[dire] >= n || col + trans[dire + 1] >= m
                        || maze[row + trans[dire]][col + trans[dire + 1]] == 1) {//越界或者遇到障碍就可以上下左右去加
                    updates.addAll(add(queue, visit, maze, cur));
                } else {
                    cur.row += trans[dire];
                    cur.col += trans[dire + 1];
                    if (visit[cur.row][cur.col][dire]) continue;
                    updates.add(new int[]{cur.row, cur.col, cur.direction});
                    queue.add(cur);
                }
            }
            for (int[] u : updates)visit[u[0]][u[1]][u[2]]=true;
            if (ans != null) return ans;
        }
        return "impossible";
    }

    //dire[i]代表一个方向，i从0~4分别代表右下左上
    public static List<int[]> add(Queue<Node> queue, boolean[][][] visit, int[][] maze, Node cur) {//上下左右随便走
        int n = maze.length, m = maze[0].length;
        List<int[]> updates=new ArrayList<>();
        for (int to = 0; to < 4; to++) {
            int row = cur.row + trans[to], col = cur.col + trans[to + 1];//去的地方
            if (row >= 0 && col >= 0 && row < n && col < m && !visit[row][col][to] && maze[row][col] == 0) {
                String path = cur.path + dire[to];
                updates.add(new int[]{row,col,to});
                queue.add(new Node(row, col, to, path));
            }
        }
        return updates;
    }

}
