package BasicLearning.Class08_SlidingWindowAndMonotoneStack;

import java.util.LinkedList;

/**
 * 题目：假设一个固定大小为W的窗口，依次划过arr。返回每一次滑出状况的最大值
 * 例如, arr = [4,3.5,4,3,3,6,7],W = 3
 * 返回:[5.5,5,4,6,7]
 */
public class Code01_SlidingWindowMaxArray {

    /**
     * 滑动窗口本来应该有两个变量，一个是l一个是r，但是这题由于窗口的大小是固定的，所以l和r可以相互推算，所以只需要设置一个r即可
     *
     * @param arr :窗口划过的数组
     * @param w :窗口维持恒定的大小为w
     * @return 返回每一次滑出状况的最大值
     */
    public static int[] getMaxWindow(int[] arr, int w){
        if (arr == null || arr.length == 0||w<=0) {
            return null;
        }
        int[] res=new int[arr.length-w+1];//长度为3对应1个结果，3-w+1
        int index=0;//专门给res数组用
        //这是一个双端队列。队头放大的值，需要谁就把谁放在队头。里面的值是下标
        LinkedList<Integer> qmax=new LinkedList<>();
        for (int R=0;R<arr.length;R++){//arr的每一个数一个一个进入窗口
            //不断的循环，直到当前队尾比R对应的值大，或者一直到队列为空。
            while (!qmax.isEmpty()&&arr[R]>=arr[qmax.peekLast()]){//把队列里面值比R小的先踢出去,arr[r]比尾部的“好”
                qmax.pollLast();//比不过R就从尾巴出去，位置留给R
            }//while循环还有下面的add合起来是一个加数的过程
            qmax.addLast(R);

            if (R>=w-1){//从窗口扩大到w开始之后。不仅要进去一个，还要过期一个。在过期一个之前顺便得到一个结果
                res[index++]=arr[qmax.peekFirst()];//先得到一个结果再去过期一个
                if (R-w+1==qmax.peekFirst()){//过期操作。R-w+1是过期的位置，去判断当前队头是不是过期了！
                    qmax.pollFirst();//过期就从队头出去
                }
            }
        }
        return res;
    }

    //没有对l和r进行优化的版本
    public static int[] getMaxWindow2(int[] arr, int w){
        if (arr == null || w < 1 || arr.length < w) {
            return null;
        }
        int l=0,r=-1,i=0,count=w;//窗口[l,r]
        LinkedList<Integer> list=new LinkedList<>();//维持最大值,存的是下标
        while(count!=0){
            r++;//窗口进数
            while(!list.isEmpty()&&arr[r]>=arr[list.peekLast()]){
                list.pollLast();
            }
            list.addLast(r);
            count--;
        }
        int[] res=new int[arr.length-w+1];//N-(w-1)
        res[i++]=arr[list.peekFirst()];
        while(r!=arr.length-1){//每次窗口都进一个出一个
            l++;//出一个
            if (!list.isEmpty()&&list.peekFirst()==l-1) list.pollFirst();
            r++;//进一个
            while(!list.isEmpty()&&arr[r]>=arr[list.peekLast()]){
                list.pollLast();
            }
            list.addLast(r);
            res[i++]=arr[list.peekFirst()];
        }
        return res;
    }

    // for test
    public static int[] rightWay(int[] arr, int w) {
        if (arr == null || w < 1 || arr.length < w) {
            return null;
        }
        int[] res = new int[arr.length - w + 1];
        int index = 0;
        int L = 0;
        int R = w - 1;
        while (R < arr.length) {
            int max = arr[L];
            for (int i = L + 1; i <= R; i++) {
                max = Math.max(max, arr[i]);

            }
            res[index++] = max;
            L++;
            R++;
        }
        return res;
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1));
        }
        return arr;
    }

    // for test
    public static boolean isEqual(int[] arr1, int[] arr2) {
        if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
            return false;
        }
        if (arr1 == null && arr2 == null) {
            return true;
        }
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int testTime = 100000;
        int maxSize = 100;
        int maxValue = 100;
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            int w = (int) (Math.random() * (arr.length + 1));
            int[] ans1 = getMaxWindow(arr, w);
            int[] ans2 = rightWay(arr, w);
            if (!isEqual(ans1, ans2)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish");
    }


}
