package Leetcode.AllQuestions;

import java.util.Arrays;

public class Leetcode_0503_NextGreaterElements {
    public int[] nextGreaterElements(int[] nums) {
        int n=nums.length,top=-1;
        int[] s=new int[n<<1];//空间翻倍，因为要遍历两遍
        int[] ans=new int[n];
        Arrays.fill(ans,-1);//暂时先存下标，方便判断有没有填过
        for(int t=0;t<2;t++){//循环数组那就遍历两遍数组
            for(int i=0;i<n;i++){
                while(top!=-1&&nums[i]>nums[s[top]]){
                    if(ans[s[top]]==-1) ans[s[top]]=i;//先存下标
                    top--;
                }
                s[++top]=i;
            }
        }
        for(int i=0;i<n;i++) if(ans[i]!=-1)ans[i]=nums[ans[i]];//变为值
        return ans;
    }
}
