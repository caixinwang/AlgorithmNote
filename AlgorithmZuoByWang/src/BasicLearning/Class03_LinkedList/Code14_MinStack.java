package BasicLearning.Class03_LinkedList;

import java.util.Stack;

public class Code14_MinStack {//用栈实现一个专门返回最小值的结构
    public static class MyStack1 {
        private Stack<Integer> dataStack;
        private Stack<Integer> minStack;

        public MyStack1() {
            this.dataStack = new Stack<Integer>();
            this.minStack = new Stack<Integer>();
        }

        public void push(int newNum) {
            if (minStack.isEmpty()||newNum <= getmin()) {
                minStack.push(newNum);
            }
            dataStack.push(newNum);
        }

        public int pop() {
            if (dataStack.isEmpty()) throw new RuntimeException("Your stack is empty.");
            int res= dataStack.pop();
            if (res==getmin()) minStack.pop();
            return res;
        }

        public int getmin() {
            if (minStack.isEmpty()) {
                throw new RuntimeException("Your stack is empty.");
            }
            return minStack.peek();
        }
    }

    public static class MyStack2 {
        private Stack<Integer> dataStack;
        private Stack<Integer> minStack;

        public MyStack2() {
            this.dataStack = new Stack<Integer>();
            this.minStack = new Stack<Integer>();
        }

        public void push(int newNum) {
            dataStack.push(newNum);
            if (minStack.isEmpty()){
                minStack.push(newNum);
            }else{
                minStack.push(Math.min(getmin(),newNum));
            }
        }

        public int pop() {
            if (dataStack.isEmpty()) {
                throw new RuntimeException("Your stack is empty.");
            }
            minStack.pop();
            return dataStack.pop();
        }

        public int getmin() {
            if (minStack.isEmpty()) {
                throw new RuntimeException("Your stack is empty.");
            }
            return minStack.peek();
        }
    }
}
