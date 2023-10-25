package Leetcode.SwordToOffer;

public class SwordToOffer_056_SingleNumbers {
    //异或+利用最右边的1分组
    public int[] singleNumbers(int[] nums) {
        int a=0,b=0;
        for(int num:nums) a^=num;
        int rightOne=a&-a;
        for(int num:nums) if((num&rightOne)!=0) b^=num;
        return new int[]{a^b,b};
    }
}
