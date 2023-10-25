package TestUtils;

import java.util.Arrays;

public class TestTemplates {

    public static void testForArr() {//参数为arr
        ArrayUtil arrayUtil = new ArrayUtil();
        int times = 1000;//测试次数
        long time1 = 0, time2 = 0;
        boolean isok = true;
        int maxSize = 100;//数组大小在[0~maxSize]随机
        int maxValue = 100;//数组的值在[0,maxValue]随机
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
            res1 = m1(t1);
            time1 += System.currentTimeMillis() - l;
            l = System.currentTimeMillis();
            res2 = m2(t1);
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

    public static void testForIntMatrix() {//参数为int[][]
        ArrayUtil arrayUtil = new ArrayUtil();
        int times = 10_0000;//测试次数
        long time1 = 0, time2 = 0;
        boolean isok = true;
        int maxN = 20;//matrix大小在[0~maxN][0~maxM]随机
        int maxM = 20;//matrix大小在[0~maxN][0~maxM]随机
        int maxValue = 1000;//matrix的值在[0,maxValue]随机
//        int parameter1=0;//测试函数的参数
//        int maxParameter1=1000;//参数1在[0,maxParameter1]随机
        int[][] t1 = null;
//        int[][] t2 = null;
        int res1 = 0, res2 = 0;
        for (int i = 0; i < times; i++) {
//            parameter1=arrayUtil.ran(maxParameter1);

            t1 = arrayUtil.generateRandomMatrix(arrayUtil.ran(maxN), arrayUtil.ran(maxM), maxValue);
//            t2 = arrayUtil.generateRandomMatrix(arrayUtil.ran(maxN), arrayUtil.ran(maxM), maxValue);

//            t1=arrayUtil.generateRandomArr(arrayUtil.ran(maxSize),1,maxValue);//正数数组[1,maxValue]
//            t2=arrayUtil.generateRandomArr(arrayUtil.ran(maxSize),1,maxValue);//正数数组[1,maxValue]
            long l = System.currentTimeMillis();
            res1 = m1(t1);
            time1 += System.currentTimeMillis() - l;
            l = System.currentTimeMillis();
            res2 = m2(t1);
            time2 += System.currentTimeMillis() - l;
            if (res1 != res2) {
                isok = false;
                break;
            }
        }
        arrayUtil.printMatrix(t1);//打印参数
//        System.out.println("parameter:"+parameter1);//打印参数
        if (isok) System.out.println("m1 cost " + time1 + "ms");
        System.out.println(res1);//针对返回值的操作
        if (isok) System.out.println("m2 cost " + time2 + "ms");
        System.out.println(res2);//针对返回值的操作
        System.out.println(isok ? "success" : "fail");
    }

    public static void testForCharMatrix() {//参数为char[][]
        ArrayUtil arrayUtil = new ArrayUtil();
        int times = 10_0000;//测试次数
        long time1 = 0, time2 = 0;
        boolean isok = true;
        int maxN = 10;//matrix大小在[0~maxN][0~maxM]随机
        int maxM = 10;//matrix大小在[0~maxN][0~maxM]随机
//        int parameter1=0;//测试函数的参数
//        int maxParameter1=1000;//参数1在[0,maxParameter1]随机
        char[][] t1 = null;
//        char[][] t2 = null;
        int res1 = 0, res2 = 0;
        for (int i = 0; i < times; i++) {
//            parameter1=arrayUtil.ran(maxParameter1);

            t1 = arrayUtil.generateRandomChar_a_z_Matrix(arrayUtil.ran(maxN), arrayUtil.ran(maxM));
//            t2 = arrayUtil.generateRandomChar_a_z_Matrix(arrayUtil.ran(maxN), arrayUtil.ran(maxM));

//            t1 = arrayUtil.generateRandomChar_all_Matrix(arrayUtil.ran(maxN), arrayUtil.ran(maxM));
//            t2 = arrayUtil.generateRandomChar_all_Matrix(arrayUtil.ran(maxN), arrayUtil.ran(maxM));

            long l = System.currentTimeMillis();
            res1 = m1(t1);
            time1 += System.currentTimeMillis() - l;
            l = System.currentTimeMillis();
            res2 = m2(t1);
            time2 += System.currentTimeMillis() - l;
            if (res1 != res2) {
                isok = false;
                break;
            }
        }
        arrayUtil.printMatrix(t1);//打印参数
//        System.out.println("parameter:"+parameter1);//打印参数
        if (isok) System.out.println("m1 cost " + time1 + "ms");
        System.out.println(res1);//针对返回值的操作
        if (isok) System.out.println("m2 cost " + time2 + "ms");
        System.out.println(res2);//针对返回值的操作
        System.out.println(isok ? "success" : "fail");
    }

    public static void testForString() {//参数为String
        StringUtil stringUtil = new StringUtil();
        int times = 1000;//测试次数
        long time1 = 0, time2 = 0;
        boolean isok = true;
        int maxSize = 10;//String长度在[0~maxSize]随机

//        int parameter1=0;//测试函数的参数
//        int maxParameter1=100;//参数1在[0,maxParameter1]随机

        String t1 = null;
//        String t2=null;

        int res1 = 0, res2 = 0;
        for (int i = 0; i < times; i++) {
//            parameter1=arrayUtil.ran(maxParameter1);
            t1 = stringUtil.generateRandom_a_z_String(stringUtil.ran(maxSize));
//        t2= stringUtil.generateRandom_a_z_String(stringUtil.ran(maxSize));

//        t1= stringUtil.generateRandom_all_String(stringUtil.ran(maxSize));
//        t2= stringUtil.generateRandom_all_String(stringUtil.ran(maxSize));

            long l = System.currentTimeMillis();
            res1 = m1(t1);
            time1 += System.currentTimeMillis() - l;
            l = System.currentTimeMillis();
            res2 = m2(t1);
            time2 += System.currentTimeMillis() - l;


            if (res1 != res2) {
                isok = false;
                break;
            }
        }
        System.out.println("t1:" + t1);
//        System.out.println("t2:"+t2);
//        System.out.println("parameter1:"+parameter1);//打印参数
        if (isok) System.out.println("m1 cost " + time1 + "ms");
        System.out.println(res1);//针对返回值的操作
        if (isok) System.out.println("m2 cost " + time2 + "ms");
        System.out.println(res2);//针对返回值的操作
        System.out.println(isok ? "success" : "fail");
    }

    public static void testForStringArr() {//参数为String[]
        StringUtil stringUtil = new StringUtil();
        int times = 1000;//测试次数
        long time1 = 0, time2 = 0;
        boolean isok = true;
        int maxSize = 10;//String长度在[0~maxSize]随机
        int arrSize = 40;

//        int parameter1=0;//测试函数的参数
//        int maxParameter1=100;//参数1在[0,maxParameter1]随机

        String[] t1 = null;
//        String[] t2=null;

        int res1 = 0, res2 = 0;
        for (int i = 0; i < times; i++) {
//            parameter1=arrayUtil.ran(maxParameter1);
            t1 = stringUtil.generateRandomStringArrNoRepeat(stringUtil.ran(arrSize), stringUtil.ran(maxSize));
//        t2= stringUtil.generateRandomStringArrNoRepeat(stringUtil.ran(arrSize), stringUtil.ran(maxSize));

//        t1= stringUtil.generateRandomStringArr(stringUtil.ran(arrSize), stringUtil.ran(maxSize));
//        t2= stringUtil.generateRandomStringArr(stringUtil.ran(arrSize), stringUtil.ran(maxSize));

            long l = System.currentTimeMillis();
            res1 = m1(t1);
            time1 += System.currentTimeMillis() - l;
            l = System.currentTimeMillis();
            res2 = m2(t1);
            time2 += System.currentTimeMillis() - l;


            if (res1 != res2) {
                isok = false;
                break;
            }
        }
        System.out.println("t1:" + t1);
//        System.out.println("t2:"+t2);
//        System.out.println("parameter1:"+parameter1);//打印参数
        if (isok) System.out.println("m1 cost " + time1 + "ms");
        System.out.println(res1);//针对返回值的操作
        if (isok) System.out.println("m2 cost " + time2 + "ms");
        System.out.println(res2);//针对返回值的操作
        System.out.println(isok ? "success" : "fail");
    }




    public static void testForStructure() {//测试证明两个UnionFind都是正确的
        RandomUtil r = new RandomUtil();
        int times = 100;
        int size = 10000;
        boolean isok = true;
        for (int i = 0; i < times; i++) {//进行times次测试
            int realSize = (int) (Math.random() * (size + 1));//[0,1000]随机数据规模
            int tests = 10000;
//            S structure1=;
//            S structure2=;
            for (int j = 0; j < tests; j++) {//每次测试随机进行times次的数据操作并且检查数据的正确性
                if (r.oneInTen()) {//1的比例去union，9的比例去isSameSet

//                    if (){
//                        isok=false;
//                        break;
//                    }
//                    if (){
//                        isok=false;
//                        break;
//                    }
                } else {
//                    if (){
//                        isok=false;
//                        break;
//                    }
                }
            }
            if (!isok) break;
        }
        System.out.println(isok ? "success" : "fail");
    }

    public static int m1(int[] arr, int parameter1) {
        return 0;
    }

    public static int m2(int[] arr, int parameter1) {
        return 0;
    }

    public static int m1(int[] arr) {
        return 0;
    }

    public static int m2(int[] arr) {
        return 0;
    }

    public static int m1(int[][] matrix, int parameter1) {
        return 0;
    }

    public static int m2(int[][] matrix, int parameter1) {
        return 0;
    }

    public static int m1(int[][] matrix) {
        return 0;
    }

    public static int m2(int[][] matrix) {
        return 0;
    }

    public static int m1(String str) {
        return 0;
    }

    public static int m2(String str) {
        return 0;
    }

    public static int m1(String str, int parameter1) {
        return 0;
    }

    public static int m2(String str, int parameter1) {
        return 0;
    }

    public static int m1(String str1, String str2) {
        return 0;
    }

    public static int m2(String str1, String str2) {
        return 0;
    }

    private static int m1(char[][] t1) {
        return 0;
    }

    private static int m1(char[][] t1, char[][] t2) {
        return 0;
    }

    private static int m2(char[][] t1) {
        return 0;
    }

    private static int m2(char[][] t1, char[][] t2) {
        return 0;
    }
    private static int m2(String[] t1) {
        return 0;
    }

    private static int m1(String[] t1) {
        return 0;
    }
}
