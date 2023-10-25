package Leetcode.AllQuestions;

public class Leetcode_2106_MaxTotalFruits {
    public int maxTotalFruits(int[][] fruits, int startPos, int k) {
        int n=fruits.length,left=findMostLeft(fruits,startPos-k),right=left,ans=0,sum=0;
        for(;left<n&&fruits[left][0]<=startPos+k;){
            while(right<n&&fruits[right][0]<=startPos+k&&
                    ((startPos-fruits[left][0]*2+fruits[right][0]<=k)||
                            (fruits[right][0]*2-startPos-fruits[left][0]<=k))){
                sum+=fruits[right][1];
                if(sum>ans) ans=sum;
                right++;
            }
            sum-=fruits[left++][1];
        }
        return ans;
    }

    //找>=0 pos 最左的位置；pos是在x轴的位置，不是下标。返回的是下标
    public int findMostLeft(int[][] fruits,int pos){
        int n=fruits.length,left=0,right=n-1,mid;
        while(left<=right){
            mid=left+(right-left>>1);
            if(fruits[mid][0]<pos) left=mid+1;
            else right=mid-1;
        }
        return left;
    }
}
