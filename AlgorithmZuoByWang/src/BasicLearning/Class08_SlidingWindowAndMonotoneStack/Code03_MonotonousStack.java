package BasicLearning.Class08_SlidingWindowAndMonotoneStack;

import java.util.LinkedList;
import java.util.Stack;

public class Code03_MonotonousStack {

    public static int[][] getNearLessNoRepeat(int[] arr){//求小的，栈顶放大的
        int [][] res=new int[arr.length][2];//一左一右
        Stack<Integer> stack =new Stack<>();//存放下标
        for (int i=0;i<arr.length;i++){
            while(!stack.isEmpty()&&arr[i]<arr[stack.peek()]){//无重复值，要么大于要么小于
                int index=stack.pop();//弹出
                //设置
                int left=stack.isEmpty()?-1:stack.peek();//前面一步有弹出，这里要检查
                int right=i;
                res[index][0]=left;
                res[index][1]=right;
            }
            stack.push(i);
        }
        while(!stack.isEmpty()){
            int index=stack.pop();//弹出
            //设置
            int left=stack.isEmpty()?-1:stack.peek();
            int right=-1;
            res[index][0]=left;
            res[index][1]=right;
        }
        return res;
    }


    /**
     * 逻辑：1.空栈，直接创建列表入栈然后把数插进去。2.栈非空且数组中的数大于栈顶列表代表的数，创建列表入栈然后把数插进去
     * 3. 栈非空且数组中的数等于栈顶列表代表的数，直接把数插进栈顶的的列表中。
     * 4. 栈非空且数组中的数小于栈顶列表代表的数，循环弹出栈顶的列表并且设置直到跳出循环进入到123的逻辑分支
     * @param arr:目标数组
     * @return :数组的行号代表arr数组中的每一个数的下标，列一左一右放左右两边的最小
     */
    public static int[][] getNearLess(int[] arr){
        Stack<LinkedList<Integer>> stack=new Stack<>();
        int[][] res=new int[arr.length][2];
        for (int i=0;i<arr.length;i++){
            while (!stack.isEmpty() &&arr[i]<arr[stack.peek().peekLast()]){
                LinkedList<Integer> list = stack.pop();
                for (Integer integer : list) {//整个列表一起设置
                    int left=stack.isEmpty()?-1:stack.peek().peekLast();
                    int right=i;
                    res[integer][0]=left;
                    res[integer][1]=right;
                }
            }
            //栈为空或者大于等于栈顶,这个条件的取反就是while的条件
            if (stack.isEmpty()||arr[i]>arr[stack.peek().peekLast()]){
                LinkedList<Integer> list=new LinkedList<>();
                list.addLast(i);
                stack.push(list);
            }else {//等于
                stack.peek().addLast(i);
            }
        }
        while(!stack.isEmpty()){
            LinkedList<Integer> list = stack.pop();
            for (Integer integer : list) {
                int left=stack.isEmpty()?-1:stack.peek().peekLast();
                int right=-1;
                res[integer][0]=left;
                res[integer][1]=right;
            }
        }
        return res;
    }

    // for test
    public static int[] getRandomArrayNoRepeat(int size) {
        int[] arr = new int[(int) (Math.random() * size) + 1];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }
        for (int i = 0; i < arr.length; i++) {
            int swapIndex = (int) (Math.random() * arr.length);
            int tmp = arr[swapIndex];
            arr[swapIndex] = arr[i];
            arr[i] = tmp;
        }
        return arr;
    }

    // for test
    public static int[] getRandomArray(int size, int max) {
        int[] arr = new int[(int) (Math.random() * size) + 1];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * max) - (int) (Math.random() * max);
        }
        return arr;
    }

    // for test
    public static int[][] rightWay(int[] arr) {
        int[][] res = new int[arr.length][2];
        for (int i = 0; i < arr.length; i++) {
            int leftLessIndex = -1;
            int rightLessIndex = -1;
            int cur = i - 1;
            while (cur >= 0) {
                if (arr[cur] < arr[i]) {
                    leftLessIndex = cur;
                    break;
                }
                cur--;
            }
            cur = i + 1;
            while (cur < arr.length) {
                if (arr[cur] < arr[i]) {
                    rightLessIndex = cur;
                    break;
                }
                cur++;
            }
            res[i][0] = leftLessIndex;
            res[i][1] = rightLessIndex;
        }
        return res;
    }

    // for test
    public static boolean isEqual(int[][] res1, int[][] res2) {
        if (res1.length != res2.length) {
            return false;
        }
        for (int i = 0; i < res1.length; i++) {
            if (res1[i][0] != res2[i][0] || res1[i][1] != res2[i][1]) {
                return false;
            }
        }

        return true;
    }

    // for test
    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int size = 10;
        int max = 20;
        int testTimes = 200000;
        long t1 =System.currentTimeMillis();
        for (int i = 0; i < testTimes; i++) {
            int[] arr1 = getRandomArrayNoRepeat(size);
            int[] arr2 = getRandomArray(size, max);
            if (!isEqual(getNearLessNoRepeat(arr1), rightWay(arr1))) {
                System.out.println("Oops!");
                printArray(arr1);
                break;
            }
            if (!isEqual(getNearLess(arr2), rightWay(arr2))) {
                System.out.println("Oops!");
                printArray(arr2);
                break;
            }
        }
        long t2 =System.currentTimeMillis();
        System.out.println(t2-t1);
    }

}
