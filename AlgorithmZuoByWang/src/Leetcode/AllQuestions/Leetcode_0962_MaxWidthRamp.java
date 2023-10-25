package Leetcode.AllQuestions;

import TestUtils.ArrayUtil;

import java.util.Deque;
import java.util.LinkedList;

public class Leetcode_0962_MaxWidthRamp {
    public static int maxWidthRamp(int[] nums) {//递减序列二分
        int n=nums.length;
        int[] down=new int[n];
        down[0]=nums[0];
        for(int i=1;i<n;i++)  down[i]=nums[i]<down[i-1]?nums[i]:down[i-1];
        int ans=0;
        for(int i=1;i<n;i++){
            int l=0,r=i-1,m;
            while(l<=r){
                m=l+(r-l>>1);
                if(down[m]<=nums[i]) r=m-1;
                else l=m+1;
            }
            ans=Math.max(ans,i-l);
        }
        return ans;
    }

    public static int maxWidthRamp2(int[] nums) {//单调栈
        int n=nums.length,ans=0,top=-1;
        int[] s=new int[n];
        for (int i=0;i<n;i++) if (top==-1||nums[i]<nums[s[top]]) s[++top]=i;//栈顶放小的，因为更有希望
        for(int i=n-1;i>=0;i--){
            while(top>=0&&nums[i]-nums[s[top]]>=0) ans = Math.max(ans, i-s[top--]);
        }
        return ans;
    }
    
    static ArrayUtil au=new ArrayUtil();

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
//            int[] arr = au.generateRandomArr(10, 0, 3);
            int[] arr = {6,0,8,2,1,5};
            int ans1=maxWidthRamp(arr);
            int ans2=maxWidthRamp2(arr);
            System.out.println(ans1);
            System.out.println(ans2);
            if (ans1!=ans2){
                System.out.println("opps");
                System.out.println(ans1);
                System.out.println(ans2);
            }
        }
    }

    
}
