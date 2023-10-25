package ClassicInterviewCoding.InterviewCoding18;

import TestUtils.ArrayUtil;

public class Code02_CandyProblem {

    public static int candy1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int index = nextMinIndex1(arr, 0);
        int res = rightCands(arr, 0, index++);
        int lbase = 1;
        int next = 0;
        int rcands = 0;
        int rbase = 0;
        while (index != arr.length) {
            if (arr[index] > arr[index - 1]) {
                res += ++lbase;
                index++;
            } else if (arr[index] < arr[index - 1]) {
                next = nextMinIndex1(arr, index - 1);
                rcands = rightCands(arr, index - 1, next++);
                rbase = next - index + 1;
                res += rcands + (rbase > lbase ? -lbase : -rbase);
                lbase = 1;
                index = next;
            } else {
                res += 1;
                lbase = 1;
                index++;
            }
        }
        return res;
    }

    public static int candy1_cx(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int i = getLow(arr, 0);
        int res = count(i + 1);//第一个下坡先算
        i++;
        int l = 1;//左边已经有一个比i大的了
        int r = 0;//右边目前不知道
        while (i < arr.length) {
            if (arr[i - 1] < arr[i]) {//在上升
                res += ++l;//左边有l个小孩，我要比他们多的糖
                i++;
            } else if (arr[i - 1] == arr[i]) {
                res += 1;//左边现在没有比我大的了,我要1个糖
                l = 1;//左边现在有一个孩子，我自己
                i++;
            } else {//在下降，在下降的拐点我才结算
                int low = getLow(arr, i);
                res += count(low - i + 2);
                r = low - i + 2;//右边的孩子数量
                res -= Math.min(l, r);
                l = 1;
                i = low + 1;
            }
        }
        return res;
    }

    public static int count(int n) {
        return (1 + n) * n / 2;
    }

    //一直递增最后会返回len-1位置
    public static int getHigh(int[] arr, int i) {
        while (i + 1 < arr.length && arr[i] < arr[i + 1]) {
            i++;
        }
        return i;
    }

    public static int getLow(int[] arr, int i) {
        while (i + 1 < arr.length && arr[i] > arr[i + 1]) {
            i++;
        }
        return i;
    }

    //跳过相同的,一路都相等，就跳到len-1位置
    public static int skip(int[] arr, int i) {
        while (i + 1 < arr.length && arr[i] == arr[i + 1]) {
            i++;
        }
        return i;
    }

    public static int nextMinIndex1(int[] arr, int start) {
        for (int i = start; i != arr.length - 1; i++) {
            if (arr[i] <= arr[i + 1]) {
                return i;
            }
        }
        return arr.length - 1;
    }

    public static int rightCands(int[] arr, int left, int right) {
        int n = right - left + 1;
        return n + n * (n - 1) / 2;
    }

    public static int candy2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int index = nextMinIndex2(arr, 0);
        int[] data = rightCandsAndBase(arr, 0, index++);
        int res = data[0];
        int lbase = 1;
        int same = 1;
        int next = 0;
        while (index != arr.length) {
            if (arr[index] > arr[index - 1]) {
                res += ++lbase;
                same = 1;
                index++;
            } else if (arr[index] < arr[index - 1]) {
                next = nextMinIndex2(arr, index - 1);
                data = rightCandsAndBase(arr, index - 1, next++);
                if (data[1] <= lbase) {
                    res += data[0] - data[1];
                } else {
                    res += -lbase * same + data[0] - data[1] + data[1] * same;
                }
                index = next;
                lbase = 1;
                same = 1;
            } else {
                res += lbase;
                same++;
                index++;
            }
        }
        return res;
    }

    public static int candy2_cx(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int i = getLow2(arr, 0);
        int res = count2(arr, 0, i++)[0];//第一个下坡先算
        int l = 1;//左边已经有一个比i大的了
        int r = 0;//右边目前不知道
        int same = 0;
        while (i < arr.length) {
            if (arr[i - 1] < arr[i]) {//在上升
                same = 0;
                res += ++l;//左边有l个小孩，我要比他们多的糖
                i++;
            } else if (arr[i - 1] == arr[i]) {
                same++;
                res += l;//左边现在没有比我大的了,我要1个糖
                i++;
            } else {//在下降，在下降的拐点我才结算
                int low = getLow2(arr, i - 1);
                int[] data = count2(arr, i - 1, low);
                r = data[1];//右边的孩子数量
                res += data[0];
                res -= Math.min(l, r);
                if (r > l) res += same * (r - l);
                l = 1;
                i = low + 1;
                same = 0;
            }
        }
        return res;
    }


    public static int[] count2(int[] arr, int l, int r) {
        int res = 1;
        int base = 1;
        for (r--; r >= l; r--) {
            if (arr[r] > arr[r + 1]) {
                res += ++base;
            } else {
                res += base;
            }
        }
        return new int[]{res, base};
    }

    //一直递增最后会返回len-1位置
    public static int getHigh2(int[] arr, int i) {
        while (i + 1 < arr.length && arr[i] <= arr[i + 1]) {
            i++;
        }
        return i;
    }

    public static int getLow2(int[] arr, int i) {
        while (i + 1 < arr.length && arr[i] >= arr[i + 1]) {
            i++;
        }
        return i;
    }

    public static int nextMinIndex2(int[] arr, int start) {
        for (int i = start; i != arr.length - 1; i++) {
            if (arr[i] < arr[i + 1]) {
                return i;
            }
        }
        return arr.length - 1;
    }

    public static int[] rightCandsAndBase(int[] arr, int left, int right) {
        int base = 1;
        int cands = 1;
        for (int i = right - 1; i >= left; i--) {
            if (arr[i] == arr[i + 1]) {
                cands += base;
            } else {
                cands += ++base;
            }
        }
        return new int[]{cands, base};
    }

    public static void test1() {
        int[] test1 = {3, 0, 5, 5, 4, 4, 0};
        System.out.println(candy1_cx(test1));

        int[] test2 = {3, 0, 5, 5, 4, 4, 0};
        System.out.println(candy2(test2));
    }


    public static void test2() {
        int[] test1 = {3, 0, 5, 5, 5, 4, 4, 4};
        System.out.println(skip(test1, 5));//7
        System.out.println(skip(test1, 2));//4
        System.out.println(getHigh(test1, 0));
        System.out.println(getLow(test1, 0));
        int[] test2 = {1, 2, 3, 4, 6, 6, 7, 8, 9, 4, 3, 2, 3};
        System.out.println(getHigh(test2, 0));//4
        System.out.println(getHigh(test2, 5));//8
        System.out.println(getLow(test2, 9));//11

    }

    public static void testForArr() {//参数为arr
        ArrayUtil arrayUtil = new ArrayUtil();
        int times = 1000;//测试次数
        long time1 = 0, time2 = 0;
        boolean isok = true;
        int maxSize = 10;//数组大小在[0~maxSize]随机
        int maxValue = 10;//数组的值在[0,maxValue]随机
//        int parameter1=0;//测试函数的参数
//        int maxParameter1=100;//参数1在[0,maxParameter1]随机
        int[] t1 = null, t2 = null;

        int res1 = 0, res2 = 0;
        for (int i = 0; i < times; i++) {

//            parameter1=arrayUtil.ran(maxParameter1);

            t1 = arrayUtil.generateRandomArr(arrayUtil.ran(maxSize), maxValue);
//            t2=arrayUtil.generateRandomArr(arrayUtil.ran(maxSize),maxValue);

//            t1=arrayUtil.generateRandomArr(arrayUtil.ran(maxSize),1,maxValue);//正数数组[1,maxValue]
//            t2 = arrayUtil.generateRandomArr(arrayUtil.ran(maxSize), 1, maxValue);//正数数组[1,maxValue]

            long l = System.currentTimeMillis();
            res1 = candy2_cx(t1);
            time1 += System.currentTimeMillis() - l;
            l = System.currentTimeMillis();
            res2 = candy2(t1);
            time2 += System.currentTimeMillis() - l;
            if (res1 != res2) {
                isok = false;
                break;
            }
        }
        arrayUtil.printArr(t1);//打印参数
//        arrayUtil.printArr(t2);//打印参数
//        System.out.println("parameter1:"+parameter1);//打印参数
        if (isok) System.out.println("m1 cost " + time1 + "ms");
        System.out.println(res1);//针对返回值的操作
        if (isok) System.out.println("m2 cost " + time2 + "ms");
        System.out.println(res2);//针对返回值的操作
        System.out.println(isok ? "success" : "fail");
    }


    public static void main(String[] args) {
//        test2();
//        test1();
//        testForArr();
        int[] arr = new int[]{10, 4, 4, 1, 1, 7};
        System.out.println(candy2_cx(arr));
    }


}
