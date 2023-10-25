package Leetcode.AllQuestions;

import TestUtils.ArrayUtil;

import java.util.HashMap;
import java.util.TreeMap;

public class Leetcode_1124_LongestWPI {
    /**
     * 思路:
     * 1.把hours变为代表分数的前缀和数组。把原问题转化为求累加和大于0的最长子数组。
     * 2.求前缀和大于0的最长子数组可以使用单调栈或者是哈希表。这里使用单调栈
     * 3.特殊的地方是这题的单调栈不踢人，只是维持一个只降不升的序列，因为这题求的是最长的，我们在最前面维持只降不升，
     *   那么最长的子数组的左端点一定来自于这些点。那么右端点自然是从右往左枚举，如果可以匹配栈顶就一直匹配，直到匹配不了。
     */
    public static int longestWPI(int[] hours) {//只有这题的1 -1 特性才能用
        int n=hours.length;
        int[] s=new int[n+1];
        for(int i=0;i<n;i++) s[i+1]=s[i]+(hours[i]>8?1:-1);//分数的前缀和数组
        int[] stack=new int[n+1];
        int top=-1,ans=0;
        for(int i=0;i<=n;i++){
            if(top==-1||s[i]<s[stack[top]]) stack[++top]=i;//小的进，大的压在底下，不踢人
        }
        for(int i=n;i>0;i--){
            while(top>=0&&s[i]>s[stack[top]]) ans=Math.max(ans,i-stack[top--]);//这里是while而不是if
        }
        return ans;
    }

    //sum -x >=1  ==>  x <= sum-1
    //sum>0的情况下，0下标就是最好的左端点
    //sum<0,s[x]==sum-1 ，x就是最好的左端点。因为sum-1 肯定比sum-2 sum-3 等等出现的早。
    //针对本题都是1和-1，累加和是连续变化的所以才有哈希表的一次遍历的解法
    public int longestWPI2(int[] hours) {//使用哈希表记录一个累加和最早出现的位置。针对本题都是1和-1才能使用哈希表解。上面更通用
        int n=hours.length,sum=0,ans=0;
        for (int i=0;i<n;i++) hours[i]=hours[i]>8?1:-1;
        HashMap<Integer,Integer> map=new HashMap<>();//题目求最长那么就是记录累加和最早出现的下标
        map.put(0,-1);
        for (int i = 0; i < n; i++) {
            sum+=hours[i];
            ans = Math.max(ans,sum>0?i+1:i-map.getOrDefault(sum-1,i));
            if(!map.containsKey(sum)) map.put(sum,i);//保留最早出现的
        }
        return ans;
    }

    public static int longestWPI3(int[] hours) {//使用二分，更加通用
        int n=hours.length,ans=0,sum=0;
        int[] down=new int[n+1];
        for(int i=0;i<n;i++) {
            sum+=hours[i]>8?1:-1;
            down[i+1]=sum<down[i]?sum:down[i];//维持一个只降不升的序列
        }
        sum=0;
        for (int i=1;i<=n;i++){
            sum+=hours[i-1]>8?1:-1;//代表s[i]
            int l=0,r=i-1,mid;
            while(l<=r){
                mid=l+(r-l>>1);
                if (down[mid]<=sum-1) r=mid-1;
                else l=mid+1;
            }
            ans = Math.max(ans, i-l);
        }
        return ans;
    }

    static ArrayUtil au=new ArrayUtil();
    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            int[] hours=au.generateRandomArr(10,0,16);
            int ans1=longestWPI(hours);
            int ans2=longestWPI3(hours);
            if (ans1!=ans2){
                System.out.println("opps");
                System.out.println(ans1);
                System.out.println(ans2);
            }
        }
    }
}
