package Leetcode.AllQuestions;

import java.util.Stack;

public class Leetcode_0150_EvaluateReversePolishNotation {
	public int evalRPN(String[] tokens) {
		Stack<String> stack=new Stack<>();
		for (int i = 0; i < tokens.length; i++) {
            if (!isOp(tokens[i])){
                stack.push(tokens[i]);
            }else {
                compute(stack,tokens[i]);
            }
		}
        return Integer.parseInt(stack.pop());
	}

	public boolean isOp(String s){
		return s.equals("+")||s.equals("-")||s.equals("*")||s.equals("/");
	}

    public void compute(Stack<String> stack,String op){
        int num2=Integer.parseInt(stack.pop());//先出来的是num1
        int num1=Integer.parseInt(stack.pop());
        char o=op.charAt(0);
        switch (o){
            case '+':
                stack.push(String.valueOf(num1+num2));
                break;
            case '-':
                stack.push(String.valueOf(num1-num2));
                break;
            case '*':
                stack.push(String.valueOf(num1*num2));
                break;
            case '/':
                stack.push(String.valueOf(num1/num2));
                break;
        }
    }
}
