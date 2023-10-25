package Leetcode.SwordToOffer;

import java.util.Stack;

public class SwordToOffer_033_MinStack {
    class MinStack {
        Stack<Integer> ele_stack;
        Stack<Integer> min_stack;
        /** initialize your data structure here. */
        public MinStack() {
            ele_stack=new Stack<>();
            min_stack=new Stack<>();
        }

        public void push(int x) {
            ele_stack.push(x);
            if(min_stack.isEmpty()||x<=min_stack.peek()){
                min_stack.push(x);
            }
        }

        public void pop() {
            if(ele_stack.isEmpty()) return;
            int pop=ele_stack.pop();
            if(pop==min_stack.peek()) min_stack.pop();
        }

        public int top() {
            return ele_stack.peek();
        }

        public int min() {
            return min_stack.peek();
        }
    }
}
