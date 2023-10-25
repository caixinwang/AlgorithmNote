package BasicLearning.Class07_RecursionToDP;

import java.util.Stack;

public class Code01_Hanoi {

    private static void Hanoi1(int n){
        LeftToRight(n);
    }

    private static void LeftToRight(int n){
        if (n==1){
            System.out.println("left to right");
            return;
        }
        LeftToMid(n-1);
        System.out.println("left to right");
        MidToRight(n-1);
    }

    private static void MidToRight(int n) {
        if (n==1){
            System.out.println("mid to right");
            return;
        }
        MidToLeft(n-1);
        System.out.println("mid to right");
        LeftToRight(n-1);
    }

    private static void MidToLeft(int n) {
        if (n==1){
            System.out.println("mid to left");
            return;
        }
        MidToRight(n-1);
        System.out.println("mid to left");
        RightToLeft(n-1);
    }

    private static void LeftToMid(int n) {
        if (n==1){
            System.out.println("left to mid");
            return;
        }
        LeftToRight(n-1);
        System.out.println("left to mid");
        RightToMid(n-1);
    }

    private static void RightToMid(int n) {
        if (n==1){
            System.out.println("right to mid");
            return;
        }
        RightToLeft(n-1);
        System.out.println("right to mid");
        LeftToMid(n-1);
    }

    private static void RightToLeft(int n) {
        if (n==1){
            System.out.println("right to left");
            return;
        }
        RightToMid(n-1);
        System.out.println("right to mid");
        MidToLeft(n-1);
    }
    //==========================================================================

    private static void Hanoi2(int  n){
        process(n,"left","right","mid");
    }

    private static void  process(int n,String from,String to,String other){
        if (n==1){
            System.out.println(from+" to "+to);
            return;
        }
        process(n-1,from,other,to);
        System.out.println(from+" to "+to);
        process(n-1,other,to,from);
    }

    static class Info{//模拟系统递归的时候存放的信息
        public int n;
        public String from;
        public String to;
        public String other;

        public Info(int n, String from, String to, String other) {
            this.n = n;
            this.from = from;
            this.to = to;
            this.other = other;
        }
    }

    private static void Hanoi3(int  n){//汉诺塔非递归实现
        process3(new Info(n,"left","right","middle"));
    }

    private static void  process3(Info info){
        if (info==null) return;
        Stack<Info> stack=new Stack<>();//递归栈
        while(info.n!=0||!stack.isEmpty()){//观察汉诺塔的递归实现，发现和树的中序遍历相似。参考树结构中序遍历的非递归实现
            while(info.n!=0){//将左边界一条线压栈。将递归过程按照左边界进行分解
                stack.push(info);
                info=new Info(info.n-1,info.from,info.other, info.to);
            }
            info=stack.pop();
            System.out.println(info.from+" to "+info.to);//中序打印的时机
            info=new Info(info.n-1,info.other,info.to, info.from);//脑补，看成树的右子树
        }
    }

    public static void main(String[] args) {
        Hanoi3(4);
        System.out.println("==================");
        Hanoi2(4);
    }
}
