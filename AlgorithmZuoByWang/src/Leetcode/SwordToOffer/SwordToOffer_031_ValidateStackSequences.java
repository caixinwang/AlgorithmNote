package Leetcode.SwordToOffer;

import java.util.Stack;

public class SwordToOffer_031_ValidateStackSequences {
    public boolean validateStackSequences(int[] pushed, int[] popped) {
        Stack<Integer> stack=new Stack<>();
        int i=0;
        for(int v:pushed){
            stack.push(v);
            while(!stack.isEmpty()&&popped[i]==stack.peek()){
                stack.pop();
                i++;
            }
        }
        return stack.isEmpty();
    }
}
