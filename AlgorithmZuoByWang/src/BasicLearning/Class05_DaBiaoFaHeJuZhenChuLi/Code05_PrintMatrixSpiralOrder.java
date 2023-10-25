package BasicLearning.Class05_DaBiaoFaHeJuZhenChuLi;

public class Code05_PrintMatrixSpiralOrder {

    public static void spiralOrderPrint(int[][] matrix) {
        int a=0;
        int b=0;
        int c=matrix.length-1;
        int d=matrix[0].length-1;
        while(a<=c){//直到他们碰上
            printEdge(matrix,a++,b++,c--,d--);
        }
    }

    public static void printEdge(int[][] m, int a, int b, int c, int d) {
        if(m==null){
            return;
        }
        if (a==c){//横线和单点的逻辑
            while(b!=d+1){
                System.out.print(m[a][b++]+" ");
            }
        }else if(b==d){//竖线的逻辑
            while(a!=c+1){
                System.out.print(m[a++][b]+" ");
            }
        }else{//长方形的逻辑
            //打印上面
            int b2=b;
            while (b2!=d){//不让碰上
                System.out.print(m[a][b2++]+" ");
            }
            //打印右边
            int a2=a;
            while (a2!=c){

                System.out.print(m[a2++][d]+" ");
            }
            //打印下边
            int d2=d;
            while (d2!=b){
                System.out.print(m[c][d2--]+" ");
            }
            //打印左边
            int c2=c;
            while(c2!=a){
                System.out.print(m[c2--][b]+" ");
            }
        }
    }

    public static void main(String[] args) {
        int[][] matrix = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 },
                { 13, 14, 15, 16 } };
        spiralOrderPrint(matrix);

    }

}
