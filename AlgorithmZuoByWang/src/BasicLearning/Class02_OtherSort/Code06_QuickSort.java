package BasicLearning.Class02_OtherSort;

import TestUtils.ArrayUtil;

import java.util.Arrays;

public class Code06_QuickSort {
    /**
     * 使用了三向切分的快速排序。对于重复元素比较多的数组，效果比一般的快速排序好。
     * 对于包含大量重复元素的数组，它将排序时间从线性对数级降低到了线性级。
     * @param arr 对arr数组进行排序
     */
    private static void quickSort(int[] arr) {
        if (arr == null || arr.length < 2) return;
        quickSort(arr, 0, arr.length - 1);
    }

    /**
     * 使用了随机选取一个切分元素的方法来分摊最差情况的开销。
     * @param arr:
     * @param l:
     * @param r:
     */
    private static void quickSort(int[] arr, int l, int r) {
        if (l >= r) return;
        swap(arr, l, l + (int) ((r - l + 1) * Math.random()));
        int[] a = partition(arr, l, r);
        quickSort(arr, l, a[0] - 1);
        quickSort(arr, a[1] + 1, r);
    }

    /**
     * 荷兰国旗问题---三向切分
     * @param arr:将arr数组三项切分，切分的元素是arr[r]
     * @param l:下标
     * @param r:下标
     * @return :返回两个数a,b。arr[a]~arr[b]相等。
     */
    private static int[] partition(int[] arr, int l, int r) {
        int less = l ;//arr[0]~arr[less]是小于切分元素的，闭区间
        int more = r+1;//设置为r而不是r+1是因为r位置为我们的切分元素。arr[more]~arr[N-1]是大于切分元素的
        int p=l+1,num=arr[l];
        while (p < more) {
            if (arr[p] < num) {
                swap(arr, ++less, p++);
            } else if (arr[p] > num) {
                swap(arr, --more, p);
            } else {
                p++;
            }
        }
        //[less+1~more-1]是等于切分元素的。r与more交换之后，变为[less+1~more]是等于切分元素的
        swap(arr, less, l);
        return new int[]{less, more-1};
    }

    private static void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    /**
     * 标准快排
     * @param arr 对arr数组进行排序
     */
    private static void quickSort2(int[] arr){
        quickSort2(arr,0,arr.length-1);
    }

    private static void quickSort2(int[] arr, int a, int b) {
        if (a>=b||arr==null) return;
        swap(arr,a,a+(int)(Math.random()*(b-a+1)));
        int i = partition2(arr, a, b);
        quickSort2(arr,a,i-1);
        quickSort2(arr,i+1,b);
    }

    /**
     * 标准快排的切分函数。切分元素默认是arr[l]
     * @param arr：将数组切分成a[l...i-1] a[i] a[i+1...r],这里的a[i]的值实际上是切分前arr[l]的值
     * @param l：进行切分的数组范围的左边界
     * @param r：进行切分的数组范围的右边界
     * @return ：返回切分元素在切分之后排在数组的哪一个位置index
     * p1和p2交叉的时候一定满足p2是<=num区间的最后一个数，直接与arr[l]交换即可
     * arr[p1]<num而不是<=，为了防止p2和p1不止错了1格，这样可以保证p2在的位置就是arr[l]应该放的位置。
     * while(++p1<=r&&arr[p1]<num);p1是可能超出r的，这种情况也可以被妥善处理，因为r一定会往左边越一位
     * while(--p2>=l-1&&arr[p2]>num);p2也可能超出l-1来到l位置，此时p2也恰好是arr[l]应该放的位置
     * 关键：p1和p2交叉的时机就是算法完成的时机，需要妥当的进行swap
     * A: 如果arr数组中有且仅有一个数等于num，那么在两个while结束以后，p1 p2如果碰撞也只能是p2<p1
     *      若碰撞时候的数等于num（言外之意不止一个num），那么此时p1=p2.在p1=p2的这种碰撞情况下，大while的条件是否取等号对应的情况有所不同
     *      如果while取了等号，那么p1=p2的时候和arr[l]换了一次，又进了一次循环，再次前移一次，又和arr[l]换了一次，但是这是不影响的，
     *          因为两个num紧挨着-不止一个num
     *      如果while不取等号，那么p1=p2的时候和arr[l]换了一次就退出循环，这也是正确的
     */
    private static int partition2(int[] arr,int l,int r){//由于是在快排中调用，所以r>l，即至少两个元素
        int num=arr[l];//切分元素
        int p1=l;//p1左边（包括自己）都是<=num
        int p2=r+1;//p2右边（包括自己）都是>=num
        //使用while(++p1<=r&&arr[p1]<num) 而不是while(p1<=r&&arr[p1]<num) p1++;
        //因为后者如果p1和p2都是等于num的就会陷入无限循环，因为两个循环都不会改变p1和p2的值，走不出大循环
        while(p1<p2){//这里等号加和不加都可以 原因A
            while(++p1<=r&&arr[p1]<num);//越界了说明最后一个位置是arr[l]应该放的位置，p2一定会左移一位来到这个位置
            while(--p2>=l&&arr[p2]>num);//越界了说明此时p2也是arr[l]应该放的位置
            swap(arr,p1<p2?p1:l,p2);
        }
        //以下两种p1++的写法，不适用于 <num ，因为如果arr[p1]=arr[p2]的时候会卡住不动
        //和上面项目，p1++的这种写法由于只能是<=num,在重复元素比较多的时候容易走极端，难以划分到中间位置
        // int p1=l,p2=r;//如果大循环是p1<p2，则p1需要设置为l，而不是l+1，因为当只有两个元素的时候进不去大循环
        // while(p1<p2){
        //     while(p1<=r&&arr[p1]<=num)p1++;
        //     while(p2>=l+1&&arr[p2]>=num)p2--;//p2不能越过l位置
        //     swap(arr,p1<p2?p1:l,p2);
        // }
        // int p1=l+1,p2=r;//如果大循环是p1<=p2,则p1初始值可以为l+1也可以为l
        // while(p1<=p2){
        //     while(p1<=r&&arr[p1]<=num)p1++;
        //     while(p2>=l+1&&arr[p2]>=num)p2--;//p2不能越过l位置
        //     swap(arr,p1<p2?p1:l,p2);
        // }
        return p2;
    }


    /**
     * partition2的低配版本，只从一侧开始扫描数组，交换次数比较多。
     * 思想是把数组切分成一侧全部<=num，另一侧全部>num
     * @param arr:将数组切分成a[l...i-1] a[i] a[i+1...r],这里的a[i]的值实际上是切分前arr[l]的值
     * @param l：进行切分的数组范围的左边界,默认选择arr[l]作为划分元素
     * @param r：进行切分的数组范围的右边界
     * @return ：返回切分元素在切分之后排在数组的哪一个位置（index）
     */
    private static int partition3(int[] arr,int l,int r){
        int num=arr[l];//划分
        int i=l+1;
        int less=l;//小于等于的边界,闭区间--包住的都是小于等于num的
        while(i<=r){//出while时，less为小于等于num的最右边界
            if (arr[i]<=num) swap(arr,i,++less);//左边界外扩，然后和i位置交换。
            i++;
        }
        swap(arr,less,l);
        return less;
    }

    private static void quickSort3(int[] arr){
        quickSort3(arr,0,arr.length-1);
    }

    private static void quickSort3(int[] arr, int a, int b) {
        if (a>=b||arr==null) return;
        swap(arr,a,a+(int)(Math.random()*(b-a+1)));
        int i = partition3(arr, a, b);
        quickSort3(arr,a,i-1);
        quickSort3(arr,i+1,b);
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
        if (arr1 == null && arr2 == null) {
            return true;
        }
        if (arr1==null||arr2==null) {
            return false;
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

    public static void testForArr() {//参数为arr
        ArrayUtil arrayUtil = new ArrayUtil();
        int times = 100000;//测试次数
        long time1 = 0, time2 = 0;
        boolean isok = true;
        int maxSize = 100;//数组大小在[0~maxSize]随机
        int maxValue = 1000;//数组的值在[0,maxValue]随机
        int[] t1 = null, t2 = null;
        int res1 = 0, res2 = 0;
        for (int i = 0; i < times; i++) {
            t1 = arrayUtil.generateRandomArr(arrayUtil.ran(maxSize), maxValue);
            t2= arrayUtil.copyArray(t1);
            long l = System.currentTimeMillis();
            quickSort(t1);
            time1 += System.currentTimeMillis() - l;
            l = System.currentTimeMillis();
            quickSort2(t2);
            time2 += System.currentTimeMillis() - l;
            if (!isEqual(t1,t2)) {
                isok = false;
                break;
            }
        }
        arrayUtil.printArr(t1);//打印参数
        if (isok) System.out.println("m1 cost " + time1 + "ms");
        System.out.println(res1);//针对返回值的操作
        if (isok) System.out.println("m2 cost " + time2 + "ms");
        System.out.println(res2);//针对返回值的操作
        System.out.println(isok ? "success" : "fail");
    }
    // for test
    public static void main(String[] args) {
        testForArr();
    }
}
