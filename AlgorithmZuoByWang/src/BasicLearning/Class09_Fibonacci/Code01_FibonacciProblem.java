package BasicLearning.Class09_Fibonacci;

public class Code01_FibonacciProblem {
    //普通递归
    private static int f1(int n){
        if (n<1)return 0;
        if (n==1||n==2)return 1;
        return f1(n-1)+f1(n-2);
    }

    /**
     * 利用动态规划
     * @param n:返回斐波那契数列的第n项
     * @return :
     */
    private static int f2(int n){
        if (n<1)return 0;
        if (n==1||n==2)return 1;
        int res=0;
        int n1=1;
        int n2=1;
        for (int i = 0; i < n - 2; i++) {//res迭代n-2次就是答案
            res=n1+n2;
            n1=n2;
            n2=res;
        }
        return res;
    }

    private static int f3(int n){
        if (n<1)return 0;
        if (n==1||n==2)return 1;
        int[][] m=new int[][]{{1,1},{1,0}};
        m=matrixPower(m,n-2);
        return m[0][0]+m[1][0];
    }

    /**
     *
     * @param a:矩阵
     * @param n:矩阵a的n次方
     * @return :返回结果
     */
    private static int[][] matrixPower(int[][] a,int n){
        if (n<=0) return null;
        int[][] res=new int[a.length][a[0].length];
        for (int i = 0; i < res.length; i++) {//初始化为单位矩阵
            res[i][i]=1;
        }
        int [][]m=a;//一次方
        for (;n!=0;n>>=1){
            if ((n&1)==1) res=mulMatrix(res,m);
            m=mulMatrix(m,m);//m平方
        }
        return res;
    }


    private static int[][] mulMatrix(int[][]a,int[][]b){
        int[][] res=new int[a.length][b[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b.length; j++) {
                for (int k = 0; k < b[0].length; k++) {
                    res[i][j]+=a[i][k]*b[k][j];
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        int n =20;
        long l1,l2;
//        l1= System.currentTimeMillis();
//        for (int i = 0; i < 1000000; i++) {
//            f1(n);
//        }
//        System.out.print(f1(n)+"  ");
//        l2=System.currentTimeMillis();
//        System.out.println(l2-l1);


        l1= System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            f2(n);
        }
        System.out.print(f2(n)+"  ");
        l2=System.currentTimeMillis();
        System.out.println(l2-l1);

        l1= System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            f3(n);
        }
        System.out.print(f3(n)+"  ");
        l2=System.currentTimeMillis();
        System.out.println(l2-l1);
        System.out.println("===");
    }

}
