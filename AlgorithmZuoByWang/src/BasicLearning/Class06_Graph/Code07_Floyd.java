package BasicLearning.Class06_Graph;

public class Code07_Floyd {

    static final int MAX=Integer.MAX_VALUE;

    public static int[][] floyd(int[][] gragh){
        int[][] minDist=gragh;//邻接矩阵
        for (int k=0;k< minDist.length;k++)//每次加入一个结点
            for (int i=0;i< minDist.length;i++)
                for (int j=0;j< minDist.length;j++) {//双层for循环更新minDist
                    if (minDist[i][k]==MAX||minDist[k][j]==MAX) continue;
                    minDist[i][j] = Math.min(minDist[i][j], minDist[i][k]+minDist[k][j]);
                }
        return minDist;
    }

    public static void main(String[] args) {
        int[][]test={
                {0,2,3,6,MAX,MAX},
                {2,0,MAX,MAX,4,6},
                {3,MAX,0,2,MAX,MAX},
                {6,MAX,2,0,1,3},
                {MAX,4,MAX,1,0,MAX},
                {MAX,6,MAX,3,MAX,0},
        };
        test=floyd(test);
        for (int i=0;i< test.length;i++) {
            System.out.printf("结点"+i+"为：");
            for (int j : test[i]) {
                System.out.printf("%d ",j);
            }
            System.out.println();
        }
    }


}
