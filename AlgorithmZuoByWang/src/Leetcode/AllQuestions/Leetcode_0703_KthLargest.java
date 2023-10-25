package Leetcode.AllQuestions;

import java.util.PriorityQueue;

public class Leetcode_0703_KthLargest {
    //维持一个小根堆的门槛，超过了k个就踢出最小的，那么剩下的一直就是k个最大值，k个最大中的最小的那个在堆顶，那个就是第k大的数。
    class KthLargest {
        PriorityQueue<Integer> pq;
        int size;
        public KthLargest(int k, int[] nums) {
            size=k;
            pq=new PriorityQueue<>();
            for(int n:nums){
                pq.add(n);
                if(pq.size()>size)pq.poll();
            }
        }

        public int add(int val) {
            pq.add(val);
            if(pq.size()>size)pq.poll();
            return pq.peek();
        }
    }
}
