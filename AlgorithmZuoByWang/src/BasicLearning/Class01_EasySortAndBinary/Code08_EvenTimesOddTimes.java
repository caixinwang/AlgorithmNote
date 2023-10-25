package BasicLearning.Class01_EasySortAndBinary;

public class Code08_EvenTimesOddTimes {
    public static void printOddTimesOneNumber(int[] arr){
        int eor=0;
        for(int i:arr)  eor^=i;//出来之后eor就是我们要的
        System.out.println(eor);
    }

    public static void printOddTimesTwoNumber(int[] arr){
        int eor=0;
        for(int i:arr)  eor^=i;//出来之后eor=a^b
        /**
         * 我们异或了所有的数得到了eor=a^b，现在要分别得到a和b还缺少一个条件。
         * 一个思路就是不去异或所有的数，我们只异或一部分的数，并且这一部分只包含 a和b的其中一个
         * 方法是找出a与b不同的那一位，利用掩码将a和b划分成两个子集，每个子集异或进去可以达到a或者是b。
         * 问题关键来到如何找出a和b不同的那一位，事实上a^b为1的那些位都是a和b不同的位，这里我们找a^b最右边的1
         * 小结论：一个数和它的补码的与运算得到的就是最右边的那一位---想想补码是怎么求的
         *      补码的求法就是找到最右边的1，这个1不动，左边的数全部取反
         */
        int mask=eor&(~eor+1);//得到了最右边的1
        int aORb=0,another=0 ;
        for(int i:arr)  {
            if((i&mask)==mask) aORb^=i;//该位置为1的全部异或起来
        }
        another=eor^aORb;
        System.out.println(another+" "+aORb);
    }

    public static void main(String[] args) {
        int [] arr1={1,1,1,6,6,2,2};
        int [] arr2={1,1,1,6,6,6,2,2};
        printOddTimesOneNumber(arr1);
        printOddTimesTwoNumber(arr2);
    }
}
