package ClassicInterviewCoding.InterviewCoding13;

import java.util.LinkedList;
import java.util.Stack;

public class Code03_ExpressionCompute {

    public static int getValue(String str) {
        return value(str.toCharArray(),0)[0];
    }

    /**
     * 这个递归不用写明显的base case，因为在传入的时候会保证表达式是合法的，因为递归在while里面，是在从左往右
     * 遍历的过程中进入递归的，总有进入最后一次递归的时候，进入递归是为了消去所有的左括号，所以不可能你进入到递归
     * 以后立马面对右括号这种极端情况。因为这种递归不是那种无脑枚举的递归，能进入到递归的条件本身就是苛刻的，所以
     * 不用担心边界条件 像 -3*4这种-号在最前面的，会转化为0-（3*4），因为最开始cur就初始化为0的缘故
     * @param str 表达式
     * @param i   从i往后算到一个合适的位置
     * @return 返回算到了哪一个位置index停了，返回算到那个位置答案ans是多少[ans,index]
     */
    public static int[] value(char[] str, int i) {
        int cur = 0;//利用cur=10*cur+(str[i]-'1')，从高位到低位累加出数字
        int N = str.length;
        Stack<String> stack = new Stack<>();
        while (i < N && str[i] != ')') {
            if ('0' <= str[i] && str[i] <= '9') {
                cur = 10 * cur + (str[i++] - '0');
            } else if (str[i] != '(') {
                add(stack,cur);
                cur=0;
                stack.push(""+str[i++]);
            } else {
                int[] value = value(str, i + 1);
                cur = value[0];
                i=value[1]+1;
            }
        }
        add(stack,cur);//表达式最后面的数字遇不到运算符，出循环需要单独加
        return new int[]{compute(stack),i};
    }

    /**
     *
     * @param stack 将stack里面的表达式算出来，要从栈底开始算才是从左往右，所以需要逆序
     * @return 算出答案。表达式里面只有加减，并且没有括号
     */
    public static int compute (Stack<String> stack){
        if (stack.isEmpty()) return 0;
        Stack<String> s=new Stack<>();
        while (!stack.isEmpty()){//需要先逆序一下，否则算出来的就是从右往左算的结果了。不用要迭代器来实现逆序
            s.push(stack.pop());
        }
        while(s.size()>1){
            int a=Integer.parseInt(s.pop());
            String op=s.pop();
            int b=Integer.parseInt(s.pop());
            if (op.equals("+")){
                s.push(String.valueOf(a+b));
            }else {
                s.push(String.valueOf(a-b));
            }
        }
        return Integer.parseInt(s.peek());
    }

    /**
     * num进栈条件，栈为空或者栈顶不为乘号和除号
     * @param stack 加入栈中
     * @param num 将数加到stack里面
     */
    public static void add (Stack<String> stack,int num){
        if (stack.isEmpty()){
            stack.push(String.valueOf(num));
            return;
        }
        String op = stack.pop();
        if (op.equals("/")||op.equals("*")){
            int cur = Integer.parseInt(stack.pop());
            int ans = op.equals("/")?cur/num:cur*num;
            stack.push(String.valueOf(ans));
        }else {
            stack.push(op);
            stack.push(String.valueOf(num));
        }
    }

    public static int getValue2(String str) {
        return value2(str.toCharArray(),0)[0];
    }

    public static int[] value2(char[] str, int i) {
        int cur=0;
        LinkedList<Integer> dq=new LinkedList<>();
        while(i<str.length&&str[i]!=')'){
            if ('0'<=str[i]&&str[i]<='9'){
                cur=cur*10+str[i++]-'0';
            } else if (str[i] != '(') {//说明是运算符
                add(dq,cur);
                dq.addLast((int)str[i++]);
                cur=0;
            }else {//说明是左括号
                int[] v = value2(str, i + 1);
                cur=v[0];
                i=v[1]+1;
            }
        }
        add(dq,cur);
        return new int[]{compute(dq),i};
    }

    /**
     *
     * @param dq 将stack里面的表达式算出来，要从栈底开始算才是从左往右，所以需要逆序
     * @return 算出答案
     */
    public static int compute (LinkedList<Integer> dq){
        if(dq.isEmpty()) return 0;
        int ans=dq.pollFirst();//到真正计算的时候，需要从左往右
        while(!dq.isEmpty()){
            ans=dq.pollFirst()=='+'?ans+dq.pollFirst():ans-dq.pollFirst();
        }
        return ans;
    }

    public static void add (LinkedList<Integer> dq,int cur){
        if(!dq.isEmpty()&&(dq.peekLast()=='*'||dq.peekLast()=='/')){
            cur=dq.pollLast()=='*'?dq.pollLast()*cur:dq.pollLast()/cur;
        }
        dq.addLast(cur);
    }

    public static void main(String[] args) {
        String exp = "48*((70-65)-43)+8*1";
        System.out.println(getValue(exp));
        System.out.println(getValue2(exp));

        exp = "4*(6+78)+53-9/2+45*8";
        System.out.println(getValue(exp));
        System.out.println(getValue2(exp));

        exp = "10-5*3";
        System.out.println(getValue(exp));
        System.out.println(getValue2(exp));

        exp = "-3*4";
        System.out.println(getValue(exp));
        System.out.println(getValue2(exp));

        exp = "3+1*4";
        System.out.println(getValue(exp));
        System.out.println(getValue2(exp));

    }

}
