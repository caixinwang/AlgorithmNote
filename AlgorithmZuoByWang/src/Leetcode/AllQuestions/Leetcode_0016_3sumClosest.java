package Leetcode.AllQuestions;

import java.util.Arrays;

public class Leetcode_0016_3sumClosest {

    public int threeSumClosest(int[] arr, int target) {
        Arrays.sort(arr);
        int ans= arr[0]+arr[1]+arr[2];
        for (int i = 0; i < arr.length-2; i++) {//留两个
            if (i==0||arr[i]!=arr[i-1]){//和前面不相等的位置才进来
                int l=i+1,r=arr.length-1;
                while (l<r){
                    if (arr[i]+arr[l]+arr[r]>target){
                        if (Math.abs(arr[i]+arr[l]+arr[r]-target)<Math.abs(ans-target)){
                            ans=arr[i]+arr[l]+arr[r];
                        }
                        while(l<--r&&arr[r]==arr[r+1]);//跳到一个和之前位置不等的位置
                    }else if (arr[i]+arr[l]+arr[r]==target){
                        return target;
                    }else {
                        if (Math.abs(arr[i]+arr[l]+arr[r]-target)<Math.abs(ans-target)){
                            ans=arr[i]+arr[l]+arr[r];
                        }
                        while (++l<r&&arr[l]==arr[l-1]);
                    }
                }
            }
        }
        return ans;
    }

}
