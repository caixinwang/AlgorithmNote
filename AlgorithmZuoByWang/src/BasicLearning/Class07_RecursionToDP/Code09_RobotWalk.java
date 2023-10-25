package BasicLearning.Class07_RecursionToDP;

public class Code09_RobotWalk {

    private static int ways(int n, int start, int end, int k){//给用户的接口是从1~N
        if (start>n||start<1||end>n||end<1||k<=0){
            return 0;
        }
        return process1(n,start-1,end-1,k);//转化为我们熟悉的数组下标的表示方法
    }

    private static int process1(int n, int cur, int end, int rest) {//这个方法里面就是从0开始标号
        if (rest==0){//base case，上层收集这些1
            return cur==end?1:0;
        }
        int goLeft=cur>0?process1(n, cur-1, end, rest-1):0;
        int goRight=cur<n-1?process1(n, cur+1, end, rest-1):0;
        return goLeft+goRight;
    }

    private static int ways2(int n, int start, int end, int k){
        if (start>n||start<1||end>n||end<1||k<=0){
            return 0;
        }
        int [][]dp=new int[n][k+1];//0~n-1  0~k
        for (int i=0;i<dp.length;i++){
            for (int j=0;j<dp[0].length;j++){
                dp[i][j]=-1;
            }
        }
        return process2(n,start-1,end-1,k,dp);
    }

    private static int process2(int n, int cur, int end, int rest, int[][] dp) {
        if (rest==0){//base case，上层收集这些1
            return cur==end?1:0;
        }
        if (dp[cur][rest] != -1) {
            return dp[cur][rest];
        }
        int goLeft=cur>0?process1(n, cur-1, end, rest-1):0;
        int goRight=cur<n-1?process1(n, cur+1, end, rest-1):0;
        dp[cur][rest]=goLeft+goRight;
        return goLeft+goRight;
    }

    private static int ways3(int n, int start, int end, int k){
        if (n<2||start>n||start<1||end>n||end<1||k<=0){
            return 0;
        }
        return process3(n,start-1,end -1,k);
    }

    private static int process3(int n, int start, int end, int k){
        if (n<2||start>n||start<1||end>n||end<1||k<=0){
            return 0;
        }
        int dp[][]=new int[n][k+1];//[cur][rest]
        dp[end][0]=1;//rest=0已经初始化
        for (int rest=1;rest<=k;rest++){
            for (int cur=0;cur<n;cur++){
                int goLeft=cur>0?dp[cur-1][rest-1]:0;
                int goRight=cur<n-1?dp[cur+1][rest-1]:0;
                dp[cur][rest]= goLeft+goRight;
            }
        }
        return dp[start][k];
    }

    private static void testMethod(){
        long l = System.currentTimeMillis();
        for (int i = 0; i < 5000; i++) {
            ways(9,2,4,10);
        }
        long l2= System.currentTimeMillis()-l;
        System.out.println(ways(9,2,4,10)+" 运行时间为"+l2/1000+"s");

        l= System.currentTimeMillis();
        for (int i = 0; i < 5000; i++) {
            ways2(9,2,4,10);
        }
        l2= System.currentTimeMillis()-l;
        System.out.println(ways(9,2,4,10)+" 运行时间为"+l2/1000+"s");

        l= System.currentTimeMillis();
        for (int i = 0; i < 5000; i++) {
            ways3(9,2,4,10);
        }
        l2= System.currentTimeMillis()-l;
        System.out.println(ways(9,2,4,10)+" 运行时间为"+l2/1000+"s");

    }

    public static void main(String[] args) {
//        testMethod();
        System.out.println("ways(1,1,4,2) = " + ways(1, 1, 4, 2));
    }

}
