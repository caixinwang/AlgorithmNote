package OtherCoding.InterView;

import java.util.Arrays;

// 来自小红书
// 一场电影开始和结束时间可以用一个小数组来表示["07:30","12:00"]
// 已知有2000场电影开始和结束都在同一天，这一天从00:00开始到23:59结束
// 一定要选3场完全不冲突的电影来观看，返回最大的观影时间
// 如果无法选出3场完全不冲突的电影来观看，返回-1
public class Code0012_WatchMovieMaxTime {

    // 暴力方法，枚举前三场所有的可能全排列
    public static int maxEnjoy1(int[][] movies) {
        if (movies.length < 3) {
            return -1;
        }
        return f1(movies, 0);
    }

    //暴力递归，把要选的三个换到前面来。
    public static int f1(int[][] movies, int index) {
        if (index == 3) {
            int ans = 0;
            int start = 0;
            for (int i = 0; i < 3; i++) {
                if (movies[i][0] < start) return -1;
                ans += movies[i][1] - movies[i][0];
                start=movies[i][1];
            }
            return ans;
        }
        int ans=-1;
        for (int i=index;i<movies.length;i++){
            swap(movies,index,i);
            ans = Math.max(ans, f1(movies,index+1));
            swap(movies,index,i);
        }
        return ans;
    }

    public static void swap(int[][] movies, int i, int j) {
        int[] tmp = movies[i];
        movies[i] = movies[j];
        movies[j] = tmp;
    }

    // 优化后的递归解
    public static int maxEnjoy2(int[][] movies) {
        Arrays.sort(movies, (a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);
        return f2(movies, 0, 0, 3);
    }

    /**
     * movies已经排序了，这样我们从左到右安排电影的流程才能正确
     *
     * @param movies movies[i]是一个整数，代表着一天中的分钟时刻
     * @param index  当前在index位置的电影做选择
     * @param time   当前来到的时间点是time
     * @param rest   还剩下3场电影要选择
     * @return 返回看三场电影的最长观影时间。看不了三场电影就返回-1
     */
    private static int f2(int[][] movies, int index, int time, int rest) {
        if (index == movies.length) return rest == 0 ? 0 : -1;
        int p1 = -1, p2 = -1, next = -1;//选 or 不选
        p1 = f2(movies, index + 1, time, rest);
        if (movies[index][0] >= time && rest > 0) next = f2(movies, index + 1, movies[index][1], rest - 1);
        if (next != -1) p2 = movies[index][1] - movies[index][0] + next;
        return Math.max(p1, p2);
    }

    static int[][][] dp;

    public static int maxEnjoy3(int[][] movies) {
        Arrays.sort(movies, (a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);
        dp = new int[movies.length + 1][1441][4];
        for (int i = 0; i < dp.length; i++) {
            for (int i1 = 0; i1 < dp[0].length; i1++) {
                for (int i2 = 0; i2 < dp[0][0].length; i2++) {
                    dp[i][i1][i2] = -2;
                }
            }
        }
        return f3(movies, 0, 0, 3);
    }

    private static int f3(int[][] movies, int index, int time, int rest) {
        if (dp[index][time][rest] != -2) return dp[index][time][rest];
        if (index == movies.length) {
            dp[index][time][rest] = rest == 0 ? 0 : -1;
            return dp[index][time][rest];
        }
        int p1 = -1, p2 = -1, next = -1;//选 or 不选
        p1 = f3(movies, index + 1, time, rest);
        if (movies[index][0] >= time && rest > 0) next = f3(movies, index + 1, movies[index][1], rest - 1);
        if (next != -1) p2 = movies[index][1] - movies[index][0] + next;
        dp[index][time][rest] = Math.max(p1, p2);
        return dp[index][time][rest];
    }


    // 为了测试
    public static int[][] randomMovies(int len, int time) {
        int[][] movies = new int[len][2];
        for (int i = 0; i < len; i++) {
            int a = (int) (Math.random() * time);
            int b = (int) (Math.random() * time);
            movies[i][0] = Math.min(a, b);
            movies[i][1] = Math.max(a, b);
        }
        return movies;
    }

    public static void main(String[] args) {
        int n = 10;
        int t = 20;
        int testTime = 10000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * n) + 1;
            int[][] movies = randomMovies(len, t);
            int ans1 = maxEnjoy1(movies);
            int ans2 = maxEnjoy2(movies);
            int ans3 = maxEnjoy3(movies);
            if (ans1 != ans2 || ans1 != ans3) {
                for (int[] m : movies) {
                    System.out.println(m[0] + " , " + m[1]);
                }
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("出错了");
            }
        }
        System.out.println("测试结束");
    }

}
