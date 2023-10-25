package BasicLearning.Class07_RecursionToDP;

import java.util.Stack;

public class Code02_ReverseStack {

    /**
     * 抓住栈底的元素，其它的元素往下沉。利用递归栈帮我们存信息。reverse函数控制不会传空栈
     * 思想：上来先弹出一个，用一个res存着。然后看看这个元素是不是最后一个元素。如果是就返回，如果不是就递归，如果抓到了栈底
     * 就会一层一层把结果扔上来，原来存起来的元素压回去。
     * @param stack
     * @return
     */
    private static int f(Stack<Integer> stack) {
        int res = stack.pop();

        if (stack.isEmpty()) {
            return res;//栈的最后一个元素返回
        } else {
            int last = f(stack);//最终会last会抓住最后一个元素
            stack.push(res);
            return last;//把最后一层递归抓到的栈底元素一路往上扔到最开始的递归
        }
    }

    /**
     * 从宏观的来看，空栈不用翻。先从栈底抽一个，然后把剩下的翻转，然后再放回去。
     * @param stack
     */
    private static void reverse(Stack<Integer> stack) {
        if (stack.isEmpty()) {
            return;
        }
        int p=f(stack);
        reverse(stack);
        stack.push(p);
    }

}
