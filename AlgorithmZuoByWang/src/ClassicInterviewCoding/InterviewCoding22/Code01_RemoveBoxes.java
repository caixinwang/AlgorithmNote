package ClassicInterviewCoding.InterviewCoding22;


public class Code01_RemoveBoxes {

    public static int removeBoxes1(int[] boxes) {
        if (boxes == null || boxes.length == 0) return 0;
        return process1(boxes, 0, boxes.length - 1, 0);
    }

    private static int process1(int[] boxes, int l, int r, int k) {
        if (l == r) return (k + 1) * (k + 1);
        int p1 = 0;//l位置保留，即和前面的k一起存起来。且新的递归后面接的开头一定和l一样
//        if (boxes[l+1]==boxes[l]){//要保证递归含义的延续，所以后面的一定要和l位置相等
//            p1=process1(boxes, l + 1, r, k + 1);
//        }
//        for (int i = l+2; i <= r; i++) {//确保l位置保留，可以延续递归含义，l位置和后序递归的新的l要一样才行
//            if (boxes[i]==boxes[l])p1 = Math.max(p1, process1(boxes,i,r,k+1)+process1(boxes,l+1,i-1,0));
//        }
        for (int i = l+1; i <= r; i++){//合并逻辑，保证中间夹着东西
            if (boxes[i]==boxes[l])p1 = Math.max(p1, process1(boxes,i,r,k+1)+(l+1<=i-1?process1(boxes,l+1,i-1,0):0));
        }
        int p2 = process1(boxes, l + 1, r, 0) + (k + 1) * (k + 1);//l位置不保留，直接和前面消了
        return Math.max(p1,p2);
    }

    public static int removeBoxes2(int[] boxes) {
        if (boxes == null || boxes.length == 0) return 0;
        int N = boxes.length;
        int[][][] dp = new int[N][N][N];
        int ans = process2(boxes, 0, N - 1, 0, dp);
        return ans;
    }

    public static int process2(int[] boxes, int L, int R, int K, int[][][] dp) {
        if (L > R) {
            return 0;
        }
        if (dp[L][R][K] > 0) {
            return dp[L][R][K];
        }
        int last = L;
        while (last + 1 <= R && boxes[last + 1] == boxes[L]) {//和后面的1的小集团合并，小贪心
            last++;
        }
        int pre = K + last - L;
        int ans = (pre + 1) * (pre + 1) + process2(boxes, last + 1, R, 0, dp);
        for (int i = last + 2; i <= R; i++) {
            if (boxes[i] == boxes[L] && boxes[i - 1] != boxes[L]) {//这边其实还能贪，看你想不想，我们只需枚举集团的开头即可
                ans = Math.max(ans, process2(boxes, last + 1, i - 1, 0, dp) + process2(boxes, i, R, pre + 1, dp));
            }
        }
        dp[L][R][K] = ans;
        return ans;
    }

    public static void test1() {
        int[] box = new int[]{1, 2, 1, 1, 1, 2, 2, 3, 4, 4, 4, 3, 3};
        System.out.println(removeBoxes1(box));
        System.out.println(removeBoxes2(box));
    }

    public static void main(String[] args) {
        test1();
    }

}
