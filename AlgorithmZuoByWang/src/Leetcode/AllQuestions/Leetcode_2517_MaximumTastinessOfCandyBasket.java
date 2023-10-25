package Leetcode.AllQuestions;

import java.util.Arrays;

public class Leetcode_2517_MaximumTastinessOfCandyBasket {
    public static int maximumTastiness(int[] price, int k) {
        Arrays.sort(price);
        int min=Integer.MAX_VALUE,max=Integer.MIN_VALUE;
        for (int n:price) {
            min = Math.min(min, n);
            max = Math.max(max, n);
        }
        int l=0,r=max-min;
        while(l<=r){//l<=r是找满足条件的最极限的位置，这题是找最大
            int mid=l+(r-l>>1);
            if (can(price,k,mid)){
                l=mid+1;
            }else {
                r=mid-1;
            }
        }
        return r;
    }

    /**
     *
     * @param price 递增
     * @param k 子序列的长度
     * @param happy 在递增的数组中，让选出来的每一个元素和前面一个选择的pre差距大于happy，那么如果按照这个标准选出了k个
     *              那么这个数组的甜蜜度就至少可以达到happy
     * @return 能否达到happy的甜蜜度
     */
    public static boolean can(int[] price, int k,int happy) {
        int pre=price[0];
        k--;
        for (int i = 1; i < price.length&&k>0; i++) {
            if (price[i]-pre>=happy) {
                pre=price[i];
                k--;
            }
        }
        return k==0;
    }

    public static void main(String[] args) {
        int[] price=new int[]{13,5,1,8,21,2};
        int k=3;
        System.out.println(maximumTastiness(price,k));
    }

}
