package ClassicInterviewCoding.InterviewCoding11;

import java.util.Arrays;

public class Code01_MaxGap {

    public static int maxGap(int[] nums) {
        if (nums == null || nums.length < 2) return 0;
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            min = Math.min(min, nums[i]);
            max = Math.max(max, nums[i]);
        }
        if (min == max) return 0;//说明所有值都一样大
        int len = nums.length;
        boolean[] isExist = new boolean[len + 1];//下标来代表桶，总共有len+1的桶
        int[] minBucket = new int[len + 1];
        int[] maxBucket = new int[len + 1];
        for (int i = 0; i < nums.length; i++) {
            int index = bucket(nums[i], len, min, max);//[0,len]
            if (!isExist[index]) {
                isExist[index] = true;
                minBucket[index] = nums[i];
                maxBucket[index] = nums[i];
            } else {
                minBucket[index] = Math.min(minBucket[index], nums[i]);
                maxBucket[index] = Math.max(maxBucket[index], nums[i]);
            }
        }
        int res = Integer.MIN_VALUE;
        int preMax = maxBucket[0];//维持一个之前的最大
        for (int i = 1; i <= len; i++) {
            if (isExist[i]) {//所有有数的桶都进去算一遍
                res = Math.max(res, minBucket[i] - preMax);
                preMax = maxBucket[i];
            }
        }
        return res;
    }

    /**
     * @param num 求num在第几个桶，桶的个数为len+1个，下标为0~len
     * @param len [min~max]范围内arr真实存在len个数
     * @param min 最小值
     * @param max 最大值
     * @return 返回num在第几个桶
     */
    public static int bucket(long num, long len, long min, long max) {
        return (int) ((num - min) * len / (max - min));
    }

    public static int bucket2(long num, long len, long min, long max) {
        return (int) ((num - min) /  ((max - min)/len));
    }

    public static int bucket(int num, int len, int min, int max) {
        return (int) ((num - min) * len / (max - min));
    }

    public static int bucket2(int num, int len, int min, int max) {
        return (int) ((num - min) /  ((max - min)/len));
    }

    public static void testBuket(){//两个不一样！下面那个可能搞出len+2
//        ArrayUtil au=new ArrayUtil();
//        int[] arr = au.generateRandomArr(10000, -10000, 10000);
//        long min=Integer.MAX_VALUE,max=Integer.MIN_VALUE;
//        for (int i = 0; i < arr.length; i++) {
//            min = Math.min(min, arr[i]);
//            max = Math.max(max, arr[i]);
//        }
//        boolean isok=true;
//        for (int i = 0; i < arr.length; i++) {
//            int len=au.ran(1,10000);
//            if (bucket(arr[i], len,min,max )
//                    !=bucket2(arr[i], len,min,max )){
//                isok=false;
//                break;
//            }
//        }
//        System.out.println(isok);
        System.out.println(bucket(9,3,1,9));
        System.out.println(bucket2(9,3,1,9));
    }

    // for test
    public static int comparator(int[] nums) {
        if (nums == null || nums.length < 2) {
            return 0;
        }
        Arrays.sort(nums);
        int gap = Integer.MIN_VALUE;
        for (int i = 1; i < nums.length; i++) {
            gap = Math.max(nums[i] - nums[i - 1], gap);
        }
        return gap;
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
    public static void main(String[] args) {
//        int testTime = 500000;
//        int maxSize = 100;
//        int maxValue = 100;
//        boolean succeed = true;
//        for (int i = 0; i < testTime; i++) {
//            int[] arr1 = generateRandomArray(maxSize, maxValue);
//            int[] arr2 = copyArray(arr1);
//            if (maxGap(arr1) != comparator(arr2)) {
//                succeed = false;
//                break;
//            }
//        }
//        System.out.println(succeed ? "Nice!" : "Fucking fucked!");
        testBuket();
    }

}
