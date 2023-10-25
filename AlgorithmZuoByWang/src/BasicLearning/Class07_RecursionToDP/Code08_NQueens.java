package BasicLearning.Class07_RecursionToDP;

public class Code08_NQueens {

    private static int num1(int n) {//n皇后
        if (n < 1) {
            return 0;
        }
        int[] record = new int[n];
        return process1(n, 0, record);
    }

    /**
     * @param n:总共有n个皇后要排
     * @param index:目前已经把0~index-1的皇后排好了，现在要看看在之前决策的基础上，从index~n-1有多少种排法 index对应于行
     * @param record:index之前的皇后的布局放置在record中，用来判断index位置的皇后能放在哪里。record[i]=j代表的是第i行的皇后放在了j列位置
     * @return :返回从index~n-1有多少种排法---在之前的基础上
     */
    private static int process1(int n, int index, int[] record) {
        if (index == n) {//顶层递归收集底层递归的这些1
            return 1;
        }
        int sum = 0;
        for (int i = 0; i < n; i++) {//index行的每一列都下去试试看。
            if (isOk(record, index, i)) {
                record[index] = i;
                sum += process1(n, index + 1, record);
            }
        }
        return sum;
    }

    /**
     * @param record:0~index-1的皇后在棋盘上的位置
     * @param index:index行想放入一个皇后。我们的结构天生决定了两个皇后不可能共行
     * @param colum:判断把皇后放在index行的colum位置可不可行。
     * @return 判断在record的排放下，(index,colum)位置能不能再放一个皇后
     */
    private static boolean isOk(int[] record, int index, int colum) {
        for (int i = 0; i < index; i++) {
            if (record[i] == colum || index - i == Math.abs(colum - record[i])) {//判断是否共列和共斜线
                return false;
            }
        }
        return true;
    }

    private static int num2(int n) {
        if (n < 1) {
            return 0;
        }
        int limit = (1 << n) - 1;//得到右边n位全是1，其它都是0
        return process2(limit, 0, 0, 0);
    }

    /**
     *
     * @param limit:对于n皇后问题所对应的特定限制
     * @param col:列限制
     * @param left:左斜线限制
     * @param right:右斜线限制
     * @return:
     */
    private static int process2(int limit, int col, int left, int right) {
        if (col==limit){//当col==limit也就意味着每一列都放好了皇后
            return 1;
        }
        int sum=0;//累加种数
        int pos=~(col|left|right)&limit;//limit作用是让未越界的位保持不变，越界的位清零，因为我们我们要从右到左一个一个取1
        int mostRightOne=0;
        while(pos!=0){//还有1给我们取。循环的含义是在当前行把每一列都试一遍。
            mostRightOne=(~pos+1)&pos;//pos最右边的1被我们拿到。补数与上自己可以拿到最右边的1
            pos-=mostRightOne;//最右边的1被我们取了，减去。
            sum+=process2(limit,col|mostRightOne,(left|mostRightOne)<<1,(right|mostRightOne)>>1);
        }
        return sum;//所有位置都取不了了就返回0，说明这种情况不行。
    }

    public static void main(String[] args) {
        System.out.println(num1(8));
        System.out.println(num2(8));
    }


}
