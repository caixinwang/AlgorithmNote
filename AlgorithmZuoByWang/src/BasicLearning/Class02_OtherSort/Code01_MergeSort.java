package BasicLearning.Class02_OtherSort;

import java.util.Arrays;

public class Code01_MergeSort {
    private static int[] help2;

    private static void mergeSort(int[] arr){
        if (arr==null||arr.length<2)
            return;
        mergeSort(arr,0,arr.length-1);
    }

    private static void mergeSort(int[] arr, int l, int r) {
        if (l>=r)return;
        int mid=l+((r-l)>>1);
        mergeSort(arr,l,mid);
        mergeSort(arr,mid+1,r);
        merge(arr,l,mid+1,r);
    }

    /**
     * 缺点：是在归并的过程中，需要频繁申请和释放数组
     * @param arr:在arr数组的相应下标进行merge
     * @param ls:merge的左边数组的开始下标
     * @param rs:merge的右边数组的开始下标
     * @param re：merge的右边数组的结束下标
     */
    private static void merge(int[] arr, int ls, int rs, int re) {
        int[] help =new int[re-ls+1];
        int p1=ls;
        int p2=rs;
        int i=0;//help的辅助指针
        while(p1<=rs-1&&p2<=re){
            help[i++]=arr[p1]<=arr[p2]?arr[p1++]:arr[p2++];
        }
        //出来就是p1和p2有其中一个越界了，下面的两个while循环只会进入一个
        while(p1<=rs-1)
            help[i++]=arr[p1++];
        while(p2<=re)
            help[i++]=arr[p2++];
        for (i=0;i<help.length;i++){//别忘了把help数组倒回arr数组
            arr[ls+i]=help[i];
        }
    }

    private static void merge1(int[] arr,int l,int m,int r){
        int[] help=new int[r-l+1];
        int p0=l,p1=m+1,index=0;
        while(p0<=m||p1<=r){
            if(p0<=m&&p1<=r) help[index++]=arr[p0]<=arr[p1]?arr[p0++]:arr[p1++];
            else help[index++]=p0<=m?arr[p0++]:arr[p1++];
        }
        for(int i=0;i<help.length;i++) arr[i+l]=help[i];
    }

    private static void mergeSort2(int[] arr){
        if (arr==null||arr.length<2)
            return;
        help2=new int[arr.length];//help2数组是类变量，这里初始化之后就可以在方法中直接用
        mergeSort2(arr,0,arr.length-1);
    }

    private static void mergeSort2(int[] arr, int l, int r) {
        if (l>=r)return;
        int mid=l+((r-l)>>1);
        mergeSort2(arr,l,mid);
        mergeSort2(arr,mid+1,r);
        merge2(arr,l,mid+1,r);
    }

    /**
     *算法第四版的实现方法，本质是一样的。但是这里的help数组是在类中声明的，不需要频繁的申请和释放
     * @param arr:在arr数组的相应下标进行merge
     * @param ls:merge的左边数组的开始下标
     * @param rs:merge的右边数组的开始下标
     * @param re：merge的右边数组的结束下标
     */
    private static void merge2(int[] arr,int ls,int rs,int re){
        for (int i = ls; i <=re; i++) {
            help2[i]=arr[i];
        }
        int p1=ls;
        int p2=rs;
        for (int i = ls; i <=re ; i++) {
            if(p1>rs-1){
                arr[i]=help2[p2++];
            }else if (p2>re){
                arr[i]=help2[p1++];
            }else if (help2[p1]<=help2[p2]){
                arr[i]=help2[p1++];
            }else{
                arr[i]=help2[p2++];
            }
        }
    }

    /** 适合链表组织的数据
     * 自底向上的归并排序。也就是归并排序的非递归版本！最后一个子数组的大小很可能会小于i，所以需要min函数防止越界
     * @param arr 对arr数组进行排序
     */
    private static void mergeSort3(int[]arr){
        if (arr==null||arr.length==0) return;
        int N=arr.length;
        help2=new int[N];
        for (int i=1;i<N;i*=2)//控制子数组的大小
            for (int j=0;j+i<N;j+=2*i)//j+i<N保证了用来merge的第二个子数组有元素可以进行merge
                merge2(arr,j,j+i,Math.min(j+2*i-1,N-1));//这里的min函数保证了不会越界
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
        int testTime = 500;
        int maxSize = 100;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            mergeSort3(arr1);
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
        mergeSort(arr);
        printArray(arr);

    }
}
