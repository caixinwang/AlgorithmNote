package Leetcode.AllQuestions;

import java.util.LinkedList;

public class Leetcode_0227_BasicCalculatorII {
	public int calculate(String s) {
		if (s==null||s.length()==0) return -1;
		char[] str = s.toCharArray();
		int cur=0;
		LinkedList<Integer> list=new LinkedList<>();
		for (char c:str){
			if (c=='+'||c=='-'||c=='*'||c=='/'){
				addNum(cur,list);//加数
				list.add((int)c);//加符号
				cur=0;
			}else if ('0'<=c&&c<='9'){
				cur=cur*10+c-'0';
			}
		}
		addNum(cur,list);
		return compute(list);
	}

	public void addNum(int cur,LinkedList<Integer> list){//加数字，检查前面是不是乘除
		if (list.isEmpty()){
			list.add(cur);
			return;
		}
		int op = list.pollLast();
		if (op=='*'||op=='/'){
			int num1=list.pollLast();
			if (op=='*')list.addLast(num1*cur);
			else list.addLast(num1/cur);
		}else {
			list.add(op);
			list.add(cur);
		}
	}

	public int compute(LinkedList<Integer> list){//只有加减没有乘除
		while(list.size()!=1){
			int num1=list.pollFirst();//从左往右算
			int op=list.pollFirst();
			int num2=list.pollFirst();
			if (op=='+'){
				list.addFirst(num1+num2);
			}else if (op=='-'){
				list.addFirst(num1-num2);
			}
		}
		return list.pollFirst();
	}

}
