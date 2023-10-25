package Leetcode.AllQuestions;


/**
 * 用位运算实现加法、加法、乘法、除法
 */
public class Leetcode_0029_DivideTwoIntegers {

    /**
     * 要手动判断参数为MIN的情况，因为负数的域比正数大一个
     * @param dividend 被除数 不区分正负
     * @param divisor 除数 不区分正负
     * @return 返回dividend/divisor
     */
    public static int divide(int dividend, int divisor) {
        if (divisor==Integer.MIN_VALUE) return dividend==Integer.MIN_VALUE?1:0;
        if (dividend==Integer.MIN_VALUE){
            if (divisor==-1) return Integer.MAX_VALUE;
            int res=div(add(dividend,1),divisor);//分半算，因为我们的div处理不了MIN
            return add(res,div(minus(dividend,divisor*res),divisor));
        }
        return div(dividend,divisor);
    }

    /**
     * 思路，b去左移相应的位数，位移到最接近a的位置，然后a-=b,然后res对应的位上填1
     * @param a 任意整数，但是绝对值不能是系统最小的绝对值，因为我们要转化为正数来做
     * @param b 任意整数，但是绝对值不能是系统最小的绝对值，因为我们要转化为正数来做
     * @return 返回a/b的结果
     */
    private static int div(int a,int b){
        boolean isNeg=(a>0)^(b>0);//不同为负，相同为正
        a=a<0?-a:a;
        b=b<0?-b:b;
        int res=0;
        for (int i = 31; i >= 0 ; i--) {//如果 b<<i <= a 那么就可以填答案。但是b<<i会溢出，导致判断错误，所以转化为b <= a>>i
            if (b<=(a>>i)){
                res|=(1<<i);
                a=minus(a,b<<i);//能进if说明不会溢出，b可以放心左移
            }
        }
        return isNeg?getNeg(res):res;
    }

    /**
     * @param a 任意整数
     * @param b 任意整数
     * @return 返回a+b的结果。这个算法a和b是正还是负都是对的
     */
    private static int add(int a,int b){//a+b  a看成不进位相加 b看成进位 一直搞到进位为0
        while(b!=0){
            int t=a^b;//不进位相加
            b=(a&b)<<1;//进位
            a=t;//不进位相加
        }
        return a;
    }

    /**
     *
     * @param a 任意整数
     * @param b 任意整数
     * @return 返回a-b的结果。这个算法a和b是正还是负都是对的
     */
    private static int minus(int a,int b){//a-b
        return add(a,getNeg(b));
    }

    private static int getNeg(int num){//返回-num,-num=(~num)+1=add(~num,1)
        return add(~num,1);
    }


    /**
     * 思路：依次取出b从低到高的位，a位移相应的位置，然后加到res上。具体过程看下面的show函数
     * @param a 任意整数
     * @param b 任意整数
     * @return 返回a*b的结果。这个算法a和b是正还是负都是对的
     */
    private static int multi(int a,int b){
        int res=0;
        while(b!=0){
            if ((b&1)==1){//只有b最低位是1的情况下res才会加
                res=add(res,a);
            }
            b>>>=1;
            a<<=1;
        }
        return res;
    }



    public static void showAdd(){//展示计算的过程
        int num1=0b111;
        int num2=0b1010;
        System.out.printf("num1   = %8s\n", Integer.toBinaryString(num1));
        System.out.printf("num2 = %8s\n", Integer.toBinaryString(num2));
        System.out.println("=========开始相加=============");
        int i=0;
        while((num1&num2)!=0){
            System.out.println("===========第"+(++i)+"轮===========");
            int carry=(num1&num2)<<1;//进位不为0
            int add=num1^num2;
            System.out.printf("add   = %8s\n", Integer.toBinaryString(add));
            System.out.printf("carry = %8s\n", Integer.toBinaryString(carry));
            num1=carry;
            num2=add;
        }
        System.out.println("=========相加结束=============");
        int res=num1^num2;
        System.out.println("res="+res);
        System.out.println(Integer.toBinaryString(res));
    }

    public static void showMulti(){
        int num1=0b111;//7
        int num2=0b1010;//10
        System.out.printf("num1 = %8s\n", Integer.toBinaryString(num1));
        System.out.printf("num2 = %8s\n", Integer.toBinaryString(num2));
        System.out.println("=========开始相乘=============");
        int i=0;
        int res=0;
        int move=0;
        while(num2!=0){//从低位到高位取
            System.out.println("===========第"+(++i)+"轮===========");
            if ((num2&1)==1){
                res+=num1<<move;
            }
            num2>>=1;
            move++;
            System.out.printf("num2= %8s\n", Integer.toBinaryString(num2));
            System.out.printf("res = %8s\n", Integer.toBinaryString(res));
        }
        System.out.println("=========相乘结束=============");
        System.out.println("res="+res);
        System.out.println(Integer.toBinaryString(res));
    }

    public static void main(String[] args) {
        System.out.println(add(-9,3));
        System.out.println(add(9,3));
        System.out.println(minus(9,3));
        System.out.println(minus(9,-3));
        System.out.println(multi(9,-3));
        System.out.println(multi(9,3));
        System.out.println(div(9,3));
        System.out.println(div(9,-3));
        System.out.println(Integer.MIN_VALUE/2);
    }
}
