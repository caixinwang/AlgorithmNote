package ClassicInterviewCoding.InterviewCoding06;

public class Code03_JumpGame {

    public static int jump(int[] arr) {
        if (arr==null||arr.length==0) return 0;
        int step=0,cur=0,next=arr[0];//当前步数step的最右，next为下一步的最右
        for (int i = 0; i < arr.length; i++) {//看看每个位置能不能来到
            if (cur<i){//step步走不到这个位置
                step++;//加步数
                cur=next;
            }
            next = Math.max(next, i+arr[i]);
        }
        return step;
    }

    public static void main(String[] args) {
        int[] arr = {3, 2, 3, 1, 1, 4, 1, 2, 2, 3, 1, 1, 1, 1, 4, 1, 1, 1, 1, 5, 1, 1, 2, 2, 3, 4, 5, 2, 1, 1, 1};
        System.out.println(jump(arr));

    }

}
