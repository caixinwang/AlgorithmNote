package BasicLearning.Class08_SlidingWindowAndMonotoneStack;

import java.util.LinkedList;

/**
 * 题目：给定一个整型数组arr，和一个整数num。某个arr中的子数组sub，如果想达标，必须满足∶
 * sub中最大值-sub中最小值<= num,
 * 返回arr中达标子数组的数量
 */
public class Code02_AllLessNumSubArray {

    /**
     * @param arr:
     * @param num:子数组的最大值和最小值的差<=num
     * @return :返回满足条件的子数组的个数
     */
    public static int getNum(int[] arr, int num){
        if (arr==null||arr.length==0||num<0){
            return 0;
        }
        int res=0;
        LinkedList<Integer> qmin=new LinkedList<>();
        LinkedList<Integer> qmax=new LinkedList<>();
        int R=0;//一直向前不回退
        //求出以每一个位置作为开头的达标子数组的数量
        for (int start=0;start<arr.length;start++){
            while(R<arr.length){//对于每一个start，都尝试把r扩到最右
                //R进到窗口中,维持最大值和最小值的结构
                while(!qmax.isEmpty()&&arr[R]>=arr[qmax.peekLast()]) qmax.pollLast();
                qmax.addLast(R);
                while(!qmin.isEmpty()&&arr[R]<=arr[qmin.peekLast()]) qmin.pollLast();
                qmin.addLast(R);
                //判断是否达标。下面出窗口需要peek，所以这里不能因为不达标就直接出了。在上面重复进队的时候while会帮我们重置。
                if (arr[qmax.peekFirst()]-arr[qmin.peekFirst()]>num){//不达标---加入了R之后变得不达标了。
                    break;//如果走了这个分支，下次R会重复进，但是不影响，上次进的会被替换。
                }
                R++;
            }
            //start出窗口，过期，更新结构
            if (start==qmin.peekFirst()) qmin.pollFirst();
            if (start==qmax.peekFirst()) qmax.pollFirst();
            res+=(R-start);//偏移了R-start次到达了不达标的位置。算上自己的位置刚好就是偏移量。
        }
        return res;
    }


    // for test
    public static int[] getRandomArray(int len) {
        if (len < 0) {
            return null;
        }
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * 10);
        }
        return arr;
    }

    // for test
    public static void printArray(int[] arr) {
        if (arr != null) {
            for (int i = 0; i < arr.length; i++) {
                System.out.print(arr[i] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int[] arr = {1,6,7,9,12,16,42,12,13,45,1,34,12,41,16};
        int[] arr2 = {1,6,7,9,12,16,42,12,13,45,1,34,12,41,16};
        int num = 1;
        printArray(arr);
        System.out.println(getNum(arr, num));

    }

}
