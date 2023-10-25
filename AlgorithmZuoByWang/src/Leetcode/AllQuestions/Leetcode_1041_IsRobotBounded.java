package Leetcode.AllQuestions;

public class Leetcode_1041_IsRobotBounded {
    //最多循环四次，因为一次指令执行完，会有位置的位移以及有角度的转动。我们重复四次可以消除角度的转动
    int[][] pos=new int[][]{{0,1},{1,0},{0,-1},{-1,0}};
    public boolean isRobotBounded(String instructions) {
        var s=instructions.repeat(4).toCharArray();
        int n=s.length,x=0,y=0,d=0;
        for(var c:s){
            if(c=='G'){
                x+=pos[d][0];
                y+=pos[d][1];
            }else if(c=='L'){
                if(--d<0) d=3;
            }else{
                if(++d>3) d=0;
            }
        }
        return x==0&&y==0;
    }
}
