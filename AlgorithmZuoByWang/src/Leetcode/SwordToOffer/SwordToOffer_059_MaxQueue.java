package Leetcode.SwordToOffer;

import java.util.LinkedList;
import java.util.Queue;

public class SwordToOffer_059_MaxQueue {
    class MaxQueue {
        Queue<Integer> queue;
        LinkedList<Integer> dq;

        public MaxQueue() {
            queue=new LinkedList<>();
            dq=new LinkedList<>();
        }

        public int max_value() {
            if(queue.isEmpty()) return -1;
            return dq.peekFirst();
        }

        public void push_back(int value) {
            queue.add(value);
            while(!dq.isEmpty()&&dq.peekLast()<value) dq.pollLast();
            dq.addLast(value);
        }

        public int pop_front() {
            if(queue.isEmpty()) return -1;
            Integer poll= queue.poll();
            if(dq.peekFirst().equals(poll)) dq.pollFirst();//注意这里，如果都是Integer对象，需要使用equals方法
            return poll;
        }
    }
}
