package Leetcode.AllQuestions;

import java.util.Arrays;
import java.util.HashMap;

public class Leetcode_0496_NextGreaterElement {
    //遍历nums2，维持一个底大的单调栈，判断弹出的元素在不在nums1中，如果在就填写答案。
    //要判断元素在不在nums1中需要额外的一个HashMap。
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        int n=nums1.length,m=nums2.length,top=-1;
        int[] ans=new int[n];
        Arrays.fill(ans,-1);
        int[] s=new int[m];
        HashMap<Integer,Integer> map=new HashMap<>();//值和下标的映射，方便确定nums2中有哪些值在nums1中存在
        for(int i=0;i<n;i++) map.put(nums1[i],i);
        for(int i=0;i<m;i++){
            while(top!=-1&&nums2[i]>nums2[s[top]]){
                if(map.containsKey(nums2[s[top]])) {//nums2[i]把此时的栈顶弹出，它就是栈顶的下一个更大的元素
                    ans[map.get(nums2[s[top]])]=nums2[i];
                }
                top--;
            }
            s[++top]=i;
        }
        return ans;
    }
}
