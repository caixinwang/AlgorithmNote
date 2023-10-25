package Leetcode.AllQuestions;

import java.util.Stack;

/*
 * 在leetcode上提交时，把文字替换成下面的代码
 * 然后把类名、构造方法名从Problem_0155_MinStack改为MinStack即可
 */
public class Leetcode_0155_MinStack {//基础课-链表

    class MinStack {//min栈和data栈不等高，min栈里面栈顶是小的

        Stack<Integer> data;
        Stack<Integer> min;

        public MinStack() {
            data = new Stack<>();
            min = new Stack<>();
        }

        public void push(int val) {
            data.push(val);
            if (min.isEmpty() || val <= min.peek()) min.push(val);//一定是小于等于，不然data里面的连续的相同的数就被丢掉了
        }

        public void pop() {
            if (data.isEmpty()) return;
            int pop = data.pop();
            if (pop == min.peek()) min.pop();
        }

        public int top() {
            if (data.isEmpty()) return -1;
            return data.peek();
        }

        public int getMin() {
            if (min.isEmpty()) return -1;
            return min.peek();
        }
    }

    class MinStack2 {//min栈和data栈等高

        Stack<Integer> data;
        Stack<Integer> min;

        public MinStack2() {
            data = new Stack<>();
            min = new Stack<>();
        }

        public void push(int val) {
            data.push(val);
            if (min.isEmpty()) min.push(val);
            else min.push(Math.min(min.peek(), val));//谁小放谁
        }

        public void pop() {
            if (data.isEmpty()) return;
            data.pop();
            min.pop();
        }

        public int top() {
            if (data.isEmpty()) return -1;
            return data.peek();
        }

        public int getMin() {
            if (min.isEmpty()) return -1;
            return min.peek();
        }
    }
}
