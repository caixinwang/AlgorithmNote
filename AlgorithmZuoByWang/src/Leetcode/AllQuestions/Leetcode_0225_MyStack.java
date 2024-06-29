package Leetcode.AllQuestions;
//import Leetcode.LeetClass.*;
import java.util.*;
public class Leetcode_0225_MyStack {
    //关键：状态的转化。push的时候需要index^1是个空队列。pop和peek的时候需要转移得剩下最后一个
    class MyStack {
        LinkedList<Integer>[] ls;
        int index=0;//index ^ 1 为栈头所在的队列
        int size=0;

        public MyStack() {// 1 2 3
            ls=new LinkedList[2];
            ls[0]=new LinkedList<Integer>();
            ls[1]=new LinkedList<Integer>();
        }

        public void push(int x) {// 1 2 3
            if(!ls[index^1].isEmpty()) ls[index].add(ls[index^1].poll());
            ls[index].add(x);
            size++;
        }

        public int pop() {
            if(size==0) return -1;
            if(size==1) {
                size--;
                return ls[index].poll();
            }
            if(ls[index^1].size()==0){
                while(ls[index].size()>1){
                    ls[index^1].add(ls[index].poll());
                }
                index^=1;
            }
            size--;
            return ls[index^1].poll();
        }

        public int top() {
            if(size==0) return -1;
            if(size==1) return ls[index].peek();
            if(ls[index^1].size()==0){
                while(ls[index].size()>1){
                    ls[index^1].add(ls[index].poll());
                }
                index^=1;
            }
            return ls[index^1].peek();
        }

        public boolean empty() {
            return size==0;
        }
    }
}
