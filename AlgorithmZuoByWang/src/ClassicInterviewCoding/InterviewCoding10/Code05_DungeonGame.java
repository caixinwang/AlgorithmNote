package ClassicInterviewCoding.InterviewCoding10;

import TestUtils.ArrayUtil;

public class Code05_DungeonGame {
    public static int needMin(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) return 0;
        return process(matrix, 0, 0);
    }

    /**
     * p(i,j)+matrix[i][j]为骑士通关完(i,j)之后剩下的血，这些血需要至少等于往右走或者往下走需要的血量。
     * down是你在(i,j)选择往下走最少需要的血量。你到(i+1,j)至少要达到这个位置需要的血量。也就是down+matrix[i][j]>=p(i+1,j)
     *
     * @param matrix 矩阵
     * @param row    骑士在row,col位置
     * @param col    上
     * @return 返回骑士从（row，col）出发最少需要多少血才能救出公主
     */
    private static int process(int[][] matrix, int row, int col) {
        int N = matrix.length, M = matrix[0].length;
        if (row == N - 1 && col == M - 1) {
            if (matrix[row][col] < 0) return -matrix[row][col] + 1;//例如-4，那么你就需要5滴血
            else return 1;//不扣血，你只需要活着来到这一格即可
        }
        int down = row + 1 < N ? process(matrix, row + 1, col) - matrix[row][col] : Integer.MAX_VALUE;
        int right = col + 1 < M ? process(matrix, row, col + 1) - matrix[row][col] : Integer.MAX_VALUE;
        if (down <= 0) down = 1;//在(i,j)位置不可能血量为负数或者0，为负数说明这关给的血太多了，我们只要有1滴血成功到达(i,j)位置即可
        if (right <= 0) right = 1;
        return Math.min(down, right);//设置为MAX为了不干扰这一步，选出最经济的
    }

    //dp[i][j]含义为从(i,j)位置出发需要多少血才能救到公主
    public static int needMin2(int[][] matrix) {//暴力递归改动态规划
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) return 0;
        int N = matrix.length, M = matrix[0].length;
        int[][] dp = new int[N][M];
        dp[N - 1][M - 1] = matrix[N - 1][M - 1] < 0 ? -matrix[N - 1][M - 1] + 1 : 1;
        for (int i = N - 1; i >= 0; i--) {
            for (int j = M - 1; j >= 0; j--) {
                if (i == N - 1 && j == M - 1) continue;//base case
                int down = i + 1 < N ? dp[i + 1][j] - matrix[i][j] : Integer.MAX_VALUE;
                int right = j + 1 < M ? dp[i][j + 1] - matrix[i][j] : Integer.MAX_VALUE;
                if (down <= 0) down = 1;//在(i,j)位置不可能血量为负数，为负数说明这关给的血太多了，我们只要有1滴血成功到达(i,j)位置即可
                if (right <= 0) right = 1;
                dp[i][j] = Math.min(down, right);
            }
        }
        return dp[0][0];
    }

    public static int needMin3(int[][] matrix) {//空间压缩
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) return 0;
        int N = matrix.length, M = matrix[0].length;
        boolean rowMore = N >= M;
        int less = rowMore ? M : N;
        int more=N+M-less;
        int[] dp = new int[less];
        dp[less - 1] = matrix[N - 1][M - 1] < 0 ? -matrix[N - 1][M - 1] + 1 : 1;
        for (int i = more-1; i >= 0; i--) {//如果rowMore为true，那么i代表行号，竖着滚.否则i代表列
            for (int j = less-1; j >= 0; j--) {
                int row = rowMore ? i : j;
                int col = rowMore ? j : i;
                if (row == N - 1 && col == M - 1) continue;//base case
                int down=row+1<N?(rowMore?dp[j]:dp[j+1])-matrix[row][col]:Integer.MAX_VALUE;
                int right=col+1<M?(rowMore?dp[j+1]:dp[j])-matrix[row][col]:Integer.MAX_VALUE;
                if (down<=0) down=1;//在(i,j)位置不可能血量为负数，为负数说明这关给的血太多了，我们只要有1滴血成功到达(i,j)位置即可
                if (right<=0) right=1;
                dp[j]=Math.min(down,right);
            }
        }
        return dp[0];
    }

    public static void testForIntMatrix() {//参数为int[][]
        ArrayUtil arrayUtil = new ArrayUtil();
        int times = 100;//测试次数
        boolean isok = true;
        int maxN = 20;//matrix大小在[0~maxN][0~maxM]随机
        int maxM = 20;//matrix大小在[0~maxN][0~maxM]随机
        int maxValue = 100;//matrix的值在[0,maxValue]随机
//        int parameter1=0;//测试函数的参数
//        int maxParameter1=1000;//参数1在[0,maxParameter1]随机
        int[][] t1 = null;
//        int[][] t2 = null;
        int res1 = 0, res2 = 0;
        for (int i = 0; i < times; i++) {
//            parameter1=arrayUtil.ran(maxParameter1);

            t1 = arrayUtil.generateRandomMatrix(arrayUtil.ran(maxN), arrayUtil.ran(maxM), -maxValue/2,maxValue);
//            t2 = arrayUtil.generateRandomMatrix(arrayUtil.ran(maxN), arrayUtil.ran(maxM), maxValue);

//            t1=arrayUtil.generateRandomArr(arrayUtil.ran(maxSize),1,maxValue);//正数数组[1,maxValue]
//            t2=arrayUtil.generateRandomArr(arrayUtil.ran(maxSize),1,maxValue);//正数数组[1,maxValue]
            res1 = needMin3(t1);
            res2 = needMin2(t1);
            if (res1 != res2) {
                isok = false;
                break;
            }
        }
        arrayUtil.printMatrix(t1);//打印参数
//        System.out.println("parameter:"+parameter1);//打印参数
        System.out.println(res1);//针对返回值的操作
        System.out.println(res2);//针对返回值的操作
        System.out.println(isok ? "success" : "fail");
    }


    public static void main(String[] args) {
        testForIntMatrix();
    }


}
