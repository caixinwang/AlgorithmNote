package Leetcode.SwordToOffer;

import java.util.Stack;

public class SwordToOffer_009_CQueue {
    class CQueue {

        Stack<Integer> add_stack;
        Stack<Integer> del_stack;
        public CQueue() {
            add_stack=new Stack<>();
            del_stack=new Stack<>();
        }

        public void appendTail(int value) {
            add_stack.add(value);
        }

        public int deleteHead() {
            if(del_stack.isEmpty()&&add_stack.isEmpty()) return -1;
            if(del_stack.isEmpty()){
                while(!add_stack.isEmpty()) del_stack.add(add_stack.pop());
            }
            return del_stack.pop();
        }
    }
}
