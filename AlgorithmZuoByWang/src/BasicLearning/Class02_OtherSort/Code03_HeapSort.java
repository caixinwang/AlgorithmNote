package BasicLearning.Class02_OtherSort;

import java.util.Arrays;

public class Code03_HeapSort {

    /**
     * 这里arr是从0下标开始放的，所以more函数还有swap函数中所有下标都要-1
     */
    public static void heapSort(int[] arr){
        int N=arr.length;
        for (int k=N/2;k>=1;k--) sink(arr,k,N);//构建堆
        while(N>1){
            swap(arr,1,N--);
            sink(arr,1,N);
        }
    }

    /**
     * 从数组的k位置开始向上调整，也就是说k位置的值可能大于父节点
     * @param k:这里的k是从下标1~N的范围。
     */
    private static void swim(int[] arr, int k) {
        for (; k > 1 && more(arr, k, k / 2); k /= 2) swap(arr, k, k / 2);
    }

    /**
     * coding技巧：和swim类似的过程，但是因为这里的child比较难求，所以break只能放在中间
     * 从数组k位置开始向下调整，也就是说k位置的值可能小于子节点
     * @param k:范围从1~N
     * @param N:堆中元素的个数
     */
    private static void sink(int[] arr, int k, int N) {
        while (2 * k <= N) {
            int child = 2 * k;//左孩子
            if (child + 1 <= N && more(arr, child + 1, child)) child++;//右孩子存在并且比左孩子大的时候j才等于2*k+1
            if (more(arr, k, child)) break;//如果比最大的孩子还大那么就不需要交换了
            else swap(arr,k,child);
            k=child;
        }
    }

    /**
     * 所有下标的位置都-1就能对应从0位置开始放的数组
     */
    private static boolean more(int[] arr, int a, int b) {
        return arr[a - 1] > arr[b - 1];
    }

    /**
     * 所有下标的位置都-1就能对应从0位置开始放的数组
     */
    private static void swap(int[] arr, int a, int b) {
        int temp = arr[a - 1];
        arr[a - 1] = arr[b - 1];
        arr[b - 1] = temp;
    }

    // for test
    public static void comparator(int[] arr) {
        Arrays.sort(arr);
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        return arr;
    }

    // for test
    public static int[] copyArray(int[] arr) {
        if (arr == null) {
            return null;
        }
        int[] res = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = arr[i];
        }
        return res;
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

    // for test
    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // for test
    public static void main(String[] args) {
        int testTime = 10000;
        int maxSize = 100;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            heapSort(arr1);
            comparator(arr2);
            if (!isEqual(arr1, arr2)) {
                succeed = false;
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");

        int[] arr = generateRandomArray(maxSize, maxValue);
        printArray(arr);
        heapSort(arr);
        printArray(arr);

    }
}
