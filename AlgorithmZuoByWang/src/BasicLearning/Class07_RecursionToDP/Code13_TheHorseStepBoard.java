package BasicLearning.Class07_RecursionToDP;

public class Code13_TheHorseStepBoard {
    private static int steps1(int x,int y,int k){
        return process1(x,y,k);
    }

    /**
     *棋盘的大小是9*10 --->映射到下标就是0~8和0~9
     * 按理来说应该是由五个参数，2个目标位置的参数不变化，3个可变参数，2个现在位置，1个剩余的步数
     * 但是我们考研进行转化，我们的马从(0,0)走向(x,y)，其实反过来看，我们的马可以等价于从(x,y)走向(0,0)，
     * 所以我们可以省略目标参数
     * @param x:目标位置横坐标
     * @param y:目标位置纵坐标
     * @param k:还有k步可以走
     * @return :返回总共有多少种方法可以走到目标位置。这明显是一个收集合格的叶子结点的问题，合格为1，不合格为0.
     *          以及需要将跳出界限的分支杀死--返回0.
     */
    private static int process1(int x, int y, int k) {
        if (k==0) return x==0&&y==0?1:0;//走到了最底下的叶子结点，收集合格的叶子结点
        if (x<0||x>8||y<0||y>9) return 0;//扼杀不合格的分支
        return process1(x+1,y+2,k-1)+process1(x+1,y-2,k-1)+
                process1(x-1,y+2,k-1)+process1(x-1,y-2,k-1)+
                process1(x+2,y+1,k-1)+process1(x+2,y-1,k-1)+
                process1(x-2,y+1,k-1)+process1(x-2,y-1,k-1);
    }

    private static int waysDP(int x,int y,int k){
        int[][][] dp=new int[9][10][k+1];//0~8  0~9  0~k
        dp[0][0][0]=1;//初始化，其它默认就是0，不用管
        for (int level=1;level<=k;level++){//k
            for (int i = 0; i < 9; i++) {//x
                for (int j = 0; j < 10; j++) {//y
                    dp[i][j][level]=getVal(dp,i+1,j+2,level-1)+getVal(dp,i+1,j-2,level-1)+
                            getVal(dp,i-1,j+2,level-1)+getVal(dp,i-1,j-2,level-1)+
                            getVal(dp,i+2,j+1,level-1)+getVal(dp,i+2,j-1,level-1)+
                            getVal(dp,i-2,j+1,level-1)+getVal(dp,i-2,j-1,level-1);
                }
            }
        }
        return dp[x][y][k];
    }

    private static int getVal(int[][][]dp,int x,int y ,int k){
        if (x<0||x>8||y<0||y>9) return 0;
        return dp[x][y][k];
    }

    public static void main(String[] args) {
        int x=8;
        int y=6;
        int k=10;
        System.out.println(steps1(x,y,k));
        System.out.println(waysDP(x,y,k));
    }





}
