package Leetcode.AllQuestions;

public class LeetCode_0135_Candy {
    //N-1-(i)代表从后面数的第i个
    public static int candy(int[] arr) {
        int N=arr.length;
        int[] h1=new int[N];//顺着
        int[] h2=new int[N];//逆着
        for (int i=0;i<N;i++){
            h1[i]=(i>0&&arr[i-1]<arr[i])?h1[i-1]+1:1;//前面的人分数比你低，你要的糖果要比他多
            h2[N-1-i]=(i>0&&arr[N-1-(i-1)]<arr[N-1-i])?h2[N-1-(i-1)]+1:1;//后面的人分数比你低，你要的糖果要比他多
        }
        int ans=0;
        for (int i=0;i<N;i++){
            ans += Math.max(h1[i],h2[i]);
        }
        return ans;
    }

    /**
     * 1,2,3,2,1,0,-1,-2,-3,-4
     * all=(1+2+3)+(1+2+3+4+5+6+7)-3
     *    =(1+2+3)+(1+2+3+5+6+7+8) = (1+2+3)+(1+2+3)+(5+6+7+8)
     * 发现其实就是递减序列长度和递增序列的长度一样的时候，递减序列长度+1。
     */
    public static int candy2(int[] arr) {
        int ans=1,pre=1,down=0,up=1;//把第一个人算在第一个递增序列上。up的递增序列的最后分配的最大糖果数。down是严格递减序列长度
        for (int i = 1; i < arr.length; i++) {
            if (arr[i]>=arr[i-1]){//在递增序列上
                pre=arr[i]==arr[i-1]?1:pre+1;
                ans+=pre;
                down=0;
                up=pre;
            }else {//严格递减
                if (++down==up) ++down;
                ans+=down;
                pre=1;//下坡加多少糖果完全取决于严格递减序列的长度，pre更新为1，给下一次上坡使用
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] arr=new int[]{1,0,2};
        System.out.println(candy2(arr));
        System.out.println(candy(arr));
    }
}
