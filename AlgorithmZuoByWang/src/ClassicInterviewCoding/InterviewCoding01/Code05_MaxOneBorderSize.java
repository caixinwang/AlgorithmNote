package ClassicInterviewCoding.InterviewCoding01;

/**
 * 题目：
 * 给定一个N*N的矩阵matiⅸ，只有0和1两种值，返回边框全是1的最大正方形的边长长度。
 * 例如：
 * 01111
 * 01001
 * 01001
 * 01111
 * 01011
 * 其中边框全是1的最大正方形的大小为4*4，所以返回4。
 */
public class Code05_MaxOneBorderSize {

    /**
     * 根据m矩阵，把传进来的right矩阵和down矩阵填好
     *
     * @param m     预处理数组对应的矩阵
     * @param right right[i][j]代表(i,j)这个点右边包括自己有多少个连续的1
     * @param down  down[i][j]代表(i,j)这个点下边包括自己有多少个连续的1
     */
    public static void setBorderMap(int[][] m, int[][] right, int[][] down) {
        int r = m.length;
        int c = m[0].length;
        for (int i = r - 1; i >= 0; i--) {
            for (int j = c - 1; j >= 0; j--) {
                if (m[i][j] == 1) {
                    right[i][j] = 1 + (j + 1 < c ? right[i][j + 1] : 0);
                    down[i][j] = 1 + (i + 1 < r ? down[i + 1][j] : 0);
                }
            }
        }
//        System.out.println("=========right========");
//        printMatrix(right);
//        System.out.println("=========right========");
//        System.out.println("=========down========");
//        printMatrix(down);
//        System.out.println("=========down========");
    }

    /**
     * 正方形的最大边长会被长和宽两者中较短的所限制，正方形的边长最大是 Math.min(m.length, m[0].length)
     * 从最大的边长开始往下递减去试依次变小的边长，看看在矩阵中能不能找到一个正方形是合法的，如果找到了就直接返回，因为别的肯定<=它
     */
    public static int getMaxSize(int[][] m) {
        int[][] right = new int[m.length][m[0].length];
        int[][] down = new int[m.length][m[0].length];
        setBorderMap(m, right, down); // O(N^2);
        for (int size = Math.min(m.length, m[0].length); size != 0; size--) {
            if (hasSizeOfBorder(size, right, down)) {
                return size;//找到就返回
            }
        }
        return 0;
    }

    public static boolean hasSizeOfBorder(int size, int[][] right, int[][] down) {
        for (int i = 0; i + size - 1 < right.length; i++) {
            for (int j = 0; j + size - 1 < right[0].length; j++) {
                if (right[i][j] >= size && down[i][j] >= size
                        && right[i + size - 1][j] >= size
                        && down[i][j + size - 1] >= size) {
                    return true;
                }
            }
        }
        return false;
    }

    public static int[][] generateRandom01Matrix(int rowSize, int colSize) {
        int[][] res = new int[rowSize][colSize];
        for (int i = 0; i != rowSize; i++) {
            for (int j = 0; j != colSize; j++) {
                res[i][j] = (int) (Math.random() * 2);
            }
        }
        return res;
    }

    public static void printMatrix(int[][] matrix) {
        for (int i = 0; i != matrix.length; i++) {
            for (int j = 0; j != matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int[][] matrix = generateRandom01Matrix(7, 8);
//        int[][] matrix = {{1, 1, 0, 1}, {1, 0, 1, 1}, {0, 0, 1, 1}};
        printMatrix(matrix);
        System.out.println(getMaxSize(matrix));
    }
}
