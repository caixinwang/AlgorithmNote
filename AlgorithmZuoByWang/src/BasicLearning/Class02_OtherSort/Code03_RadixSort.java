package BasicLearning.Class02_OtherSort;

import java.util.Arrays;

public class Code03_RadixSort {
    private static void radixSort(int [] arr){
        if (arr==null||arr.length<2) return;
        int maxDigit= maxArrDigit(arr);//得到数组中最大的数的位置
        if (maxDigit==-1) return;
        radixSort(arr,0,arr.length-1,maxDigit);
    }

    /**
     * @param arr :找出arr数组所有数中最高位数是多少
     */
    private static int maxArrDigit(int[] arr) {
        int max=arr[0];
        boolean flag=false;
        for (int a:arr){//找arr中最大
            if (a<0) flag=true;
            if (a>max) max=a;
        }
        if (flag) return -1;//如果有小于零
        int res=0;
        while(max!=0){
            res++;
            max/=10;
        }
        return res;
    }

    /**思想：利用count来提供映射关系，例如现在倒着遍历到了56，并且是第二轮，那么count[5]的值就是它应该放的位置。
     * @param arr:将数组再[l,r]范围上排序。数组中所有的数都要是大于等于0的数
     * @param maxDigit:确定要出入桶几次，maxDigit是arr数组中最大的位数，个位数是1，十位是2....
     */
    private static void radixSort(int[] arr, int l, int r, int maxDigit) {
        final int radix=10;
        int i,j;
        int[] help=new int[r-l+1];
        for (i=1;i<=maxDigit;i++){//进出桶的轮数控制，i从1开始因为个位数digit是算作1
            int[] count=new int[radix];//十进制0-9,如果在外面声明需要每次清空。
            for (j=l;j<=r;j++){//统计i位上各个数字出现的频率
                count[getDigitVal(arr[j],i)]++;
            }
            for (j=1;j<count.length;j++){//转化成可以提供映射的count数组
                count[j]+= count[j-1];
            }
            for (j=r;j>=l;j--){//从后往前保证排序是稳定的，利用count数组找到数应该放的位置
                help[--count[getDigitVal(arr[j],i)]]=arr[j];
            }
            for (j=0;j< help.length;j++){//从help导入arr中
                arr[l+j]=help[j];
            }
        }
    }

    /**
     * @param num:得到num第digit位的数是多少
     * @param digit:个位是1，十位是2，依次类推
     */
    private static int getDigitVal(int num, int digit) {
        return ((int)(num/Math.pow(10,digit-1)))%10;
    }

    // for test
    public static void comparator(int[] arr) {
        Arrays.sort(arr);
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random());
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
        int testTime = 5000;
        int maxSize = 100;
        int maxValue = 100000;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            radixSort(arr1);
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
        radixSort(arr);
        printArray(arr);

    }
}
