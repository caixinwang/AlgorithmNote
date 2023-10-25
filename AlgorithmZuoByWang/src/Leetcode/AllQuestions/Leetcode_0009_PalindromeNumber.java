package Leetcode.AllQuestions;

public class Leetcode_0009_PalindromeNumber {

    static int[] arr=new int[]{1,10,100,1000,10000,10000_0,10000_00,10000_000,10000_0000,10000_0000_0};

    /**
     * 一股脑取出x的前面一半和后面一半，如果后面一半reverse等于前面一半，那么就是回文的。
     * 但是要注意了，如果后面一半取出来高位是00，reverse完要补回去。并且需要讨论奇偶时候后一半取的位置
     * @param x 判断x是不是回文整数
     * @return -
     */
    public static boolean isPalindrome(int x) {
        if(x<0) return false;
        int len=getDigits(x);
        if(len==1) return true;
        int num1=x/arr[len/2];
        int num2=0;
        int multi=0;
        if((len&1)==1){//奇数位
            num2=x%arr[len/2+1];
            multi=(len/2+1)-getDigits(num2);
        }else{//偶数
            num2=x%arr[len/2];
            multi=(len/2)-getDigits(num2);
        }
        return reverse(num2)*arr[multi]==num1;//少了多少0要补回去
    }

    public static int getDigits(int x){//十进制有几位
        int n=0;
        while(x!=0){
            x/=10;
            n++;
        }
        return n;
    }

    public static int swap(int x,int L,int R){//交换x上面的L位和R位
        int l=x/arr[L]%10;
        int r=x/arr[R]%10;
        return x-(l-r)*arr[L]-(r-l)*arr[R];
    }

//    public static int reverse(int x){//利用类似数组的reverse
//        int len=getDigits(x);
//        int l=0,r=len-1;
//        while(l<r){
//            x=swap(x,l++,r--);
//        }
//        return x;
//    }

    public static int reverse(int x){
        int ans=0;
        while(x!=0){//从低位倒着这样算回去就reverse了
            ans=ans*10+x%10;
            x/=10;
        }
        return ans;
    }

    /**
     * 翻转一个整数其实就是从低往高算一遍。12345你从5开始玩下面的过程就是翻转了
     * 12345=((((0*10+1)*10+2)*10+3)*10+4)*10+5
     * 这题其实就是变翻转边让x减小，直到cur>=x了就退出
     * 这时候就可以返回了，如果cur直接等于x那么就是true，如果不等但是是奇数的话，此时cur/10==x。
     */
    public static boolean isPalindrome2(int x) {//和上面reverse的方法一样，不过只reverse一半
        if (x==0) return true;
        if (x<0||x%10==0) return false;
        int cur=0;
        while(x>cur){
            cur=cur*10+x%10;
            x/=10;
        }
        return x==cur||x==cur/10;//偶数如果对，前面就返回了，奇数在后面返回
    }

    public boolean isPalindrome3(int x) {//全部reverse
        long reverse=0;
        for(long cur=x;cur!=0;cur/=10) reverse=(reverse*10)+(cur%10);
        return x>=0&&reverse==(0L+x);
    }

    public static void main(String[] args) {
        int x=1221;
        System.out.println(reverse(123));
//        System.out.println(getDigits(123));
        System.out.println(isPalindrome(1001));
    }
}
