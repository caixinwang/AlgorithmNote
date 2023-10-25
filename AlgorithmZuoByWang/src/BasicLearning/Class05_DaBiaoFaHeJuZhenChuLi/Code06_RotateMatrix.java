package BasicLearning.Class05_DaBiaoFaHeJuZhenChuLi;

public class Code06_RotateMatrix {

    public static void rotate(int[][] matrix) {
        int a=0;
        int b=0;
        int rEnd=matrix.length-1;
        int cEnd=matrix[0].length-1;
        while(a<=rEnd){
            rotateEdge(matrix,a++,b++,rEnd--,cEnd--);
        }
    }

    public static void rotateEdge(int[][] m, int a, int b, int c, int d) {
        if(m==null||d-b!=c-a){
            return;
        }
        for (int i=0;i<c-a;i++){//一共有c-a组
            int temp=m[a][i+b];//第i组的一号
            m[a][i+b]=m[c-i][b];//第i组的4号赋值给第i组的1号
            m[c-i][b]=m[c][d-i];//第i组的3号赋值给第i组的4号
            m[c][d-i]=m[a+i][d];
            m[a+i][d]=temp;
        }
    }

    public static void printMatrix(int[][] matrix) {
        for (int i = 0; i != matrix.length; i++) {
            for (int j = 0; j != matrix[0].length; j++) {
                System.out.printf("%3d",matrix[i][j]);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int[][] matrix = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 }, { 13, 14, 15, 16 } };
        printMatrix(matrix);
        rotate(matrix);
        System.out.println("=========");
        printMatrix(matrix);

    }

}
