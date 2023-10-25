package Leetcode.AllQuestions;

public class Leetcode_1144_MovesToMakeZigzag {
    //总共就两种情况：1.偶数下标小 2.奇数下标小
    int MIN=1<<31,MAX=MIN-1;
    public int movesToMakeZigzag(int[] nums) {
        int n=nums.length,f1=0,f2=0;
        for(int i=1;i<n;i+=2){
            f1+=max(0,nums[i]-min(nums[i-1]-1,i+1<n?nums[i+1]-1:MAX));
        }
        for(int i=0;i<n;i+=2){
            f2+=max(0,nums[i]-min(i-1>=0?nums[i-1]-1:MAX,i+1<n?nums[i+1]-1:MAX));
        }
        return min(f1,f2);
    }
    public int min(int a,int b){return a<b?a:b;}
    public int max(int a,int b){return a>b?a:b;}
}
