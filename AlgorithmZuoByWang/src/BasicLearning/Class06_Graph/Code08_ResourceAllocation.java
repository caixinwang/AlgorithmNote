package BasicLearning.Class06_Graph;

public class Code08_ResourceAllocation {
    /**
     * @param g:[projects][investment+1]
     * @return :int[] res，res[i]代表i项目应该投入多少可以使得整体收益最大
     */
    public static int[] resourceAllocation(double[][] g) {
        double[][] f = new double[g.length][g[0].length];//f[i][j]代表j元资金只投资0~i的项目可以达到的最大收益
        int[][] p = new int[g.length][g[0].length];//p[i][j]代表f[i][j]达到最大收益的时候i项目投资了多少钱
        for (int i = 0; i < g[0].length; i++) {
            f[0][i] = g[0][i];//只投资第0个项目
            p[0][i]=i;
        }
        for (int i = 1; i < g.length; i++) {//投资0~i个项目
            for (int j = 0; j < g[0].length; j++) {//0~i项目共有j元可以投资
                int path = 0;//记录f[i][j]达到最大收益的时候i项目投资了多少钱
                for (int k = 0; k <= j; k++) {//投资k元给i项目，如果可以使得f[i][j]更大的话就更新f[i][j]以及path
                    if (g[i][k] + f[i - 1][j - k] > f[i][j]) {
                        path = k;
                        f[i][j] = g[i][k] + f[i - 1][j - k];
                    }
                }
                p[i][j] = path;
            }
        }
        int[] res=new int[g.length];//res[i]代表应该给i工程投资多少才能达到最大收益
        int rest=g[0].length-1;
        for (int i=f.length-1;i>=0;i--){
            res[i]=p[i][rest];
            rest-=res[i];
        }
        return res;
    }

    public static void main(String[] args) {
        double[][] g = {
                {0, 1.2, 1.5, 1.85, 2.4, 2.8, 3.3},
                {0, 1.8, 2.0, 2.25, 2.4, 2.5, 2.6},
                {0, 1.3, 1.9, 2.2, 2.45, 2.7, 3.0},
        };
        int[] x = resourceAllocation(g);
        for (int i = 0; i < x.length; i++) {
            System.out.printf("工程"+i+"分配%d万元\n",x[i]);
        }
    }
}
