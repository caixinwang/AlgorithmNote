package BasicLearning.Class05_DaBiaoFaHeJuZhenChuLi;

public class Code04_ZigZagPrintMatrix {

    public static void printMatrixZigZag(int[][] matrix) {
        int rEnd = matrix.length-1;//最大行数下标--有多少个一维数组
        int cEnd = matrix[0].length-1;//最大列数下标--每一个一维数组有多少个元素
        int a = 0;//(a,b)在上右边运动
        int b = 0;
        int c = 0;//(c,d)在左下边运动
        int d = 0;
        boolean up = true;
        while (a != rEnd+1) {
            printLevel(matrix, a, b, c, d, up);
            a = b == cEnd ? a + 1 : a;//a先更新，因为a的更新会收列的影响
            b = b == cEnd ? b : b + 1;
            d = c == rEnd ? d + 1 : d;
            c = c == rEnd ? c : c + 1;
            up = !up;
        }
        System.out.println();
    }

    /**
     * @param m:矩阵
     * @param a:(a,b)在上面和右面的边运动
     * @param b:
     * @param c:(c,d)在左边和下面的边运动
     * @param d:
     * @param up:如果up为true就自下往上打印
     */
    public static void printLevel(int[][] m, int a, int b, int c, int d, boolean up) {
        if (m == null) {
            return;
        }
        if (up) {//自下往上打印
            while (c != a - 1) {
                System.out.print(m[c--][d++] + " ");
            }
        } else {//自上往下打印
            while (a!=c+1) {
                System.out.print(m[a++][b--] + " ");
            }
        }
    }

    public static void main(String[] args) {
        int[][] matrix = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}};
        printMatrixZigZag(matrix);

    }
}
