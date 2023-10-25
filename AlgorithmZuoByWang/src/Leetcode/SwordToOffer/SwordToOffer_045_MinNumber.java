package Leetcode.SwordToOffer;

import java.util.Arrays;

public class SwordToOffer_045_MinNumber {
    //自定义排序规则，a+b和b+a谁小谁排前面
    public String minNumber(int[] nums) {
        String[] strings=new String[nums.length];
        for(int i=0;i<nums.length;i++) strings[i]=""+nums[i];
        Arrays.sort(strings,(a, b)->{return (a+b).compareTo(b+a);});
        String ans="";
        for(String s:strings) ans+=s;
        return ans;
    }
}
