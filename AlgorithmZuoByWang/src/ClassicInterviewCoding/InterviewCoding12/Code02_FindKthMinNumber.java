package ClassicInterviewCoding.InterviewCoding12;

import TestUtils.ArrayUtil;

import java.util.Arrays;

public class Code02_FindKthMinNumber {

    public static int findKthNum(int[] arr1, int[] arr2, int kth) {
        if (arr1 == null || arr2 == null) {
            return -1;
        }
        if (kth < 1 || kth > arr1.length + arr2.length) {
            return -1;
        }
        if (arr1.length==0&&arr2.length==0) return -1;
        if (arr1.length==0) return arr2[kth-1];
        if (arr2.length==0) return arr1[kth-1];
        int[] longs = arr1.length >= arr2.length ? arr1 : arr2;
        int[] shorts = arr1.length < arr2.length ? arr1 : arr2;
        int l = longs.length;
        int s = shorts.length;
        if (kth <= s) {
            return getUpMedian(shorts, 0, kth - 1, longs, 0, kth - 1);
        }
        if (kth > l) {
            if (shorts[kth - l - 1] >= longs[l - 1]) {
                return shorts[kth - l - 1];
            }
            if (longs[kth - s - 1] >= shorts[s - 1]) {
                return longs[kth - s - 1];
            }
            return getUpMedian(shorts, kth - l, s - 1, longs, kth - s, l - 1);
        }
        // 短数组长度 < k <= 长数组长度
        if (longs[kth - s - 1] >= shorts[s - 1]) {
            return longs[kth - s - 1];
        }
        return getUpMedian(shorts, 0, s - 1, longs, kth - s, kth - 1);
    }

    // A[s1...e1]
    // B[s2...e2]
    // 这两段一定等长且都有序
    // 求这两段整体的上中位数，上中位数值返回
    public static int getUpMedian(int[] A, int s1, int e1, int[] B, int s2, int e2) {
        int mid1 = 0;
        int mid2 = 0;
        while (s1 < e1) {
            mid1 = (s1 + e1) / 2;
            mid2 = (s2 + e2) / 2;
            if (A[mid1] == B[mid2]) {
                return A[mid1];
            }
            if (((e1 - s1 + 1) & 1) == 1) { // 奇数长度
                if (A[mid1] > B[mid2]) {
                    if (B[mid2] >= A[mid1 - 1]) {
                        return B[mid2];
                    }
                    e1 = mid1 - 1;
                    s2 = mid2 + 1;
                } else { // A[mid1] < B[mid2]
                    if (A[mid1] >= B[mid2 - 1]) {
                        return A[mid1];
                    }
                    e2 = mid2 - 1;
                    s1 = mid1 + 1;
                }
            } else { // 偶数长度
                if (A[mid1] > B[mid2]) {
                    e1 = mid1;
                    s2 = mid2 + 1;
                } else {
                    e2 = mid2;
                    s1 = mid1 + 1;
                }
            }
        }
        return Math.min(A[s1], B[s2]);
    }

    public static int findKthNum2(int[] arr1, int[] arr2, int kth) {//cx'version
        if (arr1 == null || arr2 == null) {
            return -1;
        }
        if (kth < 1 || kth > arr1.length + arr2.length) {//对数组长度做了限制，数组不可能都为0
            return -1;
        }
        if (arr1.length==0) return arr2[kth-1];
        if (arr2.length==0) return arr1[kth-1];
        if (arr1.length > arr2.length) {//让arr1做短的那个
            int[] t = arr1;
            arr1 = arr2;
            arr2 = t;
        }
        int s = arr1.length, l = arr2.length;
        if (kth <= s) {//[1,s]
            return getUpMedian2(arr1, 0, kth - 1, arr2, 0, kth - 1);
        } else if (kth <= l) {//(s,l]  kth-s-1是arr2能利用到的最左的位置，kth-1位置是arr2能用到的最右的位置
            if (arr2[kth - s - 1] >= arr1[s - 1]) return arr2[kth - s - 1];
            return getUpMedian2(arr1, 0, s-1, arr2, kth - s, kth - 1);//kth-s=kth-s-1+1
        }
        //kth>l 划掉的数都是 arr1和arr2 前面的的数
        if (arr1[kth - l - 1] >= arr2[l - 1]) return arr1[kth - l - 1];//kth-l-1是arr1能用到的最左侧的位置
        if (arr2[kth - s - 1] >= arr1[s - 1]) return arr2[kth - s - 1];//kth-s-1是arr2能用到的最左侧的位置
        return getUpMedian2(arr1, kth - l, s - 1, arr2, kth - s, l - 1);
    }

    public static int getUpMedian2(int[] A, int s1, int e1, int[] B, int s2, int e2) {
        if (s1==e1) return Math.min(A[s1],B[s2]);
        int mid1 = s1 + e1 >> 1, mid2 = s2 + e2 >> 1;
        if (A[mid1] == B[mid2]) return A[mid1];
        if (((e1 - s1 + 1) & 1) == 1) {//奇数
            if (A[mid1] > B[mid2]) {
                return getUpMedian2(A, s1, mid1, B, mid2, e2);//多划一个A[mid1]进去
            } else {
                return getUpMedian2(A, mid1, e1, B, s2, mid2);//多划一个A[mid2]进去
            }
        } else {//偶数
            if (A[mid1] > B[mid2]) {
                return getUpMedian2(A, s1, mid1, B, mid2 + 1, e2);
            } else {
                return getUpMedian2(A, mid1 + 1, e1, B, s2, mid2);
            }
        }
    }


    public static int findKthMinNumber(int[] arr1,int[] arr2,int kth){
        if (arr1==null||arr2==null||kth>arr1.length+arr2.length||kth<1) return -1;
        if (arr1.length==0) return arr2[kth-1];
        if (arr2.length==0) return arr1[kth-1];
        if (arr1.length>arr2.length){
            int[] t=arr1;
            arr1=arr2;
            arr2=t;
        }
        int s= arr1.length,l=arr2.length;
        if (kth<=s){
            return getMiddle(arr1,0,kth-1,arr2,0,kth-1);
        }else if (kth<=l){
            if (arr2[kth-s-1]>=arr1[s-1]) return arr2[kth-s-1];
            return getMiddle(arr1,0,s-1,arr2,kth-s,kth-1);
        }
        if (arr2[kth-s-1]>=arr1[s-1]) return arr2[kth-s-1];
        if (arr1[kth-l-1]>=arr2[l-1]) return arr1[kth-l-1];
        return getMiddle(arr1,kth-l,s-1,arr2,kth-s,l-1);
    }

    public static int getMiddle(int[] A,int s1,int e1,int[] B,int s2,int e2){
        if (s1==e1) return Math.min(A[s1],B[s2]);
        int mid1=s1+e1>>1,mid2=s2+e2>>1;
        if (A[mid1]==B[mid2]) return A[mid1];
        if ((e1-s1+1&1)==1){
            if (A[mid1]>B[mid2]) return getMiddle(A,s1,mid1,B,mid2,e2);
            else return getMiddle(A,mid1,e1,B,s2,mid2);
        }else {
            if (A[mid1]>B[mid2]) return getMiddle(A,s1,mid1,B,mid2+1,e2);
            else return getMiddle(A,mid1+1,e1,B,s2,mid2);
        }
    }
    public static void testForArr() {//参数为arr
        ArrayUtil arrayUtil = new ArrayUtil();
        int times = 1000;//测试次数
        long time1=0,time2=0;
        boolean isok = true;
        int maxSize = 10;//数组大小在[0~maxSize]随机
        int maxValue = 100;//数组的值在[0,maxValue]随机
        int parameter1=0;//测试函数的参数
        int maxParameter1=50;//参数1在[0,maxParameter1]随机
        int[] t1 = null, t2 = null;

        int res1 = 0, res2 = 0;
        for (int i = 0; i < times; i++) {


            t1 = arrayUtil.generateRandomArr(arrayUtil.ran(maxSize), maxValue);
            t2=arrayUtil.generateRandomArr(arrayUtil.ran(maxSize),maxValue);
            parameter1=arrayUtil.ran(-100,t1.length+t2.length);
            Arrays.sort(t1);
            Arrays.sort(t2);

//            t1=arrayUtil.generateRandomArr(arrayUtil.ran(maxSize),1,maxValue);//正数数组[1,maxValue]
//            t2 = arrayUtil.generateRandomArr(arrayUtil.ran(maxSize), 1, maxValue);//正数数组[1,maxValue]

            long l = System.currentTimeMillis();
            res1 = findKthNum(t1,t2,parameter1);
            time1+=System.currentTimeMillis()-l;
            l=System.currentTimeMillis();
            res2 = findKthMinNumber(t1,t2,parameter1);
            time2+=System.currentTimeMillis()-l;
            if (res1 != res2) {
                isok = false;
                break;
            }
        }
        arrayUtil.printArr(t1);//打印参数
        arrayUtil.printArr(t2);//打印参数
        System.out.println("parameter1:"+parameter1);//打印参数
        if (isok) System.out.println("m1 cost "+time1+"ms");
        System.out.println(res1);//针对返回值的操作
        if (isok)System.out.println("m2 cost "+time2+"ms");
        System.out.println(res2);//针对返回值的操作
        System.out.println(isok ? "success" : "fail");
    }

    public static void main(String[] args) {
        testForArr();
    }

}
