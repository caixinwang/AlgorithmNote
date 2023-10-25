package Leetcode.AllQuestions;

import java.util.Arrays;

public class Leetcode_0593_ValidSquare {
    //正方形：四条边都相等，并且对角线也相等
    public boolean validSquare(int[] p1, int[] p2, int[] p3, int[] p4) {
        long[] lens=new long[6];
        lens[0]=len(p1,p2);
        lens[1]=len(p1,p3);
        lens[2]=len(p1,p4);
        lens[3]=len(p2,p3);
        lens[4]=len(p2,p4);
        lens[5]=len(p3,p4);
        Arrays.sort(lens);
        return lens[0]>0&&lens[0]==lens[1]&&lens[1]==lens[2]&&lens[2]==lens[3]&&lens[4]==lens[5];
    }

    public long len(int[] p1,int []p2){
        return (p1[0]-p2[0])*(p1[0]-p2[0])+(p1[1]-p2[1])*(p1[1]-p2[1]);
    }
}
