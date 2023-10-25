package Leetcode.AllQuestions;

import TestUtils.ArrayUtil;

public class Leetcode_0055_JumpGame {
    public static boolean canJump(int[] nums) {
        if (nums == null || nums.length <= 1) return true;
        boolean[] dp = new boolean[nums.length];
        dp[nums.length - 1] = true;
        for (int i = nums.length - 2; i >= 0; i--) {
            if (nums[i]==0) continue;
            for (int step = 1; step <= nums[i] && i + step < nums.length; step++) {
                if (dp[i + step]) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[0];
    }

    public static boolean canJump2(int[] nums) {//省枚举
        if (nums == null || nums.length <= 1) return true;
        boolean[] dp = new boolean[nums.length];
        dp[nums.length - 1] = true;
        out:
        for (int i = nums.length - 2; i >= 0; i--) {
            if (nums[i] > 0) {//至少能跳一步才能借用后面的
                dp[i] = dp[i + 1];
                if (nums[i] > 1) {//至少能跳两步才开始有枚举行为，发现枚举行为会被前面的这个dp[i+1]吃掉
                    for (int step = 1 + nums[i + 1]; step <= nums[i] && i + step < nums.length; step++) {
                        if (dp[i + step]) {
                            dp[i] = true;
                            continue out;
                        }
                    }
                }
            }
        }
        return dp[0];
    }

    public static boolean canJump3(int[] nums) {//用有限几个变量
        if (nums == null || nums.length <= 1) return true;
        int R = 0;//当前能来到的最右边界
        int i = 0;
        for (; i < nums.length && i <= R && R < nums.length - 1; i++) {
            R = Math.max(R, i + nums[i]);
        }
        return R >= nums.length - 1;
    }

    public static void testForArr() {//参数为arr
        ArrayUtil arrayUtil = new ArrayUtil();
        int times = 100000;//测试次数
        long time1 = 0, time2 = 0;
        boolean isok = true;
        int maxSize = 30;//数组大小在[0~maxSize]随机
        int maxValue = 3;//数组的值在[0,maxValue]随机
//        int parameter1=0;//测试函数的参数
//        int maxParameter1=100;//参数1在[0,maxParameter1]随机
        int[] t1 = null, t2 = null;

        boolean res1 = false, res2 = false;
        for (int i = 0; i < times; i++) {

//            parameter1=arrayUtil.ran(maxParameter1);

            t1 = arrayUtil.generateRandomArr(arrayUtil.ran(maxSize), maxValue);
//            t2=arrayUtil.generateRandomArr(arrayUtil.ran(maxSize),maxValue);

//            t1=arrayUtil.generateRandomArr(arrayUtil.ran(maxSize),1,maxValue);//正数数组[1,maxValue]
//            t2 = arrayUtil.generateRandomArr(arrayUtil.ran(maxSize), 1, maxValue);//正数数组[1,maxValue]

            long l = System.currentTimeMillis();
            res1 = canJump2(t1);
            time1 += System.currentTimeMillis() - l;
            l = System.currentTimeMillis();
            res2 = canJump3(t1);
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
        testForArr();
    }
}
