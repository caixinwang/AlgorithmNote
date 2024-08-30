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
     * 若内循环的不变量为：p1停在>=num的位置i或者越界的位置i，i之前的位置都是<=num的。
     * 则根据循环不变量，p1和p2交叉的时候已经满足l~p2均是<=num的数，p2+1~r都是>=num的数，这样就可以将l与p2交换，实现划分的功能
     * 
     * 关键：p1和p2交叉的时机就是算法完成的时机，需要妥当的进行swap
     * 外循环的作用：1.确保第一次能进去 2.确保划分完毕之后（swap(arr,p2,l)）可以通过它退出循环
     *     根据这两个作用，如果我们自己控制退出外循环，那么就while(true)即可。
     */
    private static int partition2(int[] arr,int l,int r){//由于是在快排中调用，所以r>l，即至少两个元素
        //根据循环不变量，从内循环走出的时候，p1所在位置是第一个>=num的第一个数（或越界），p2同理，如果没有碰撞则还没有划分完毕，交换p1和p2位置
        //如果产生了碰撞，碰撞有两种情况，第一种是交错碰撞，也就是p2<p1，第二种是同位碰撞，也就是p2==p1，同位配置仅当有与num相同元素的时候产生
        //对于两种情况，此时已经产生了划分答案，p2与l位置交换即可退出外循环。退出外循环可以break，也可以通过外循环控制，如果需要立即退出外循环
        //那么外循环就是p1<p2这样的条件，如果外循环写成了p1<=p2，那么就会多进一次循环，p2会继续左移，但是由于右边有相同元素，所以再次与
        //l位置交换之后返回的p2也是其中一个正确的位置
        //内循环需要保证不越界即可，--p2>=l&&arr[p2]>num其中也可以是>=l-1，但是其实p2不可能走到l左边，因为第二个条件卡死
        //可以分别验证，[l+1,r]范围内如果都是>num或者<num的数，算法也是正确处理了
        int num=arr[l];//切分元素
        int p1=l;//p1左边（包括自己）都是<=num
        int p2=r+1;//p2右边（包括自己）都是>=num
        while(p1<p2){//这里等号加和不加都可以 原因A
            while(++p1<=r&&arr[p1]<num);//越界了说明最后一个位置是arr[l]应该放的位置，p2一定会左移一位来到这个位置
            while(--p2>=l&&arr[p2]>num);//越界了说明此时p2也是arr[l]应该放的位置
            swap(arr,p1<p2?p1:l,p2);
        }
        //使用p1++的形式，实现<num的算法会更加复杂。循环不变量与上面相同，出内循环，p1为出界或者是第一个>=num的位置
        //与上面的区别是，如果p1和p2位置的数如果都恰好等于num，如果仅仅swap，则p1和p2都没有改变。需要自己手动去给他们递增递减
        //与++p不同，p++这种写法不会上来就修改p的值，所以p1==p2的时候需要swap之后手动退出。外循环取了等号，因为要使只有两个元素
        //的时候也能进入外循环
        // int p1=l+1,p2=r;
        // while(p1<=p2){//while(true)
        //     while(p1<=r&&arr[p1]<num) p1++;
        //     while(p2>=l&&arr[p2]>num) p2--;
        //     if(p1<=p2){
        //         swap(arr,p1,p2);
        //         if(p1<=r&&arr[p1]==num&&p2>=l&&arr[p2]==num){
        //             p1++;p2--;
        //         }
        //     }else{
        //         swap(arr,p2,l);
        //         break;
        //     }
        // }

        
        //和<num的算法相比，下面的写法在重复元素比较多的时候容易走极端，难以划分到中间位置
        //与上面的算法不同之处在于，内循环条件为>=num的时候不能保证不越界，所以需要严格控制p2出了内循环只能落在范围内（p1越界关系不大）
        //外循环最好理解的是统一设置为p1<=p2,因为内循环是>=num的情况，所以不可能出现换两次
        //如果外循环设置为了p1<p2,那么需要注意p1需要设置为l，因为内循环取了等号，所以一上来就会离开，就和设置为l+1的效果一样
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
