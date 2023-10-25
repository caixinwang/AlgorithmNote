package OtherCoding.CCF_CSP;

import java.util.Scanner;

public class Code_202303_1 {//分田地

    public static int solve(int a,int b,int[][] rectangles){
        int res=0;
        for (int[] rectangle : rectangles) {
            int x1=rectangle[0];
            int y1=rectangle[1];
            int x2=rectangle[2];
            int y2=rectangle[3];
            if (x2>a) x2=a;
            if (y2>b) y2=b;
            if (x1<0) x1=0;
            if (y1<0) y1=0;
            if (x1<x2&&y1<y2){
                res+=(y2-y1)*(x2-x1);
            }
        }
        return res;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            int n= in.nextInt();
            int a=in.nextInt();
            int b=in.nextInt();
            int[][] rectangles=new int[n][4];
            for (int i = 0; i < rectangles.length; i++) {
                for (int j = 0; j < rectangles[0].length; j++) {
                    rectangles[i][j]=in.nextInt();
                }
            }
            System.out.println(solve(a,b,rectangles));
        }
        in.close();
    }

}
