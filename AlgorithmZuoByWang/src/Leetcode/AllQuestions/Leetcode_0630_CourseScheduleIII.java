package Leetcode.AllQuestions;

import java.util.Arrays;
import java.util.PriorityQueue;

public class Leetcode_0630_CourseScheduleIII {
	//还是大根堆作为门槛的一个题，因为我们肯定希望修的课程都是时间少的，那么大根堆的堆顶就是那个门槛。
	//Other Coding 35 , 会议的最大收益

	public static int scheduleCourse(int[][] courses) {
		// courses[i]  = {花费，截止}
		Arrays.sort(courses, (a, b) -> a[1] - b[1]);
		// 花费时间的大根堆
		PriorityQueue<Integer> heap = new PriorityQueue<>((a, b) -> b - a);
		// 时间点
		int time = 0;
		for (int[] c : courses) {
			if (time + c[0] <= c[1]) { // 当前时间 + 花费 <= 截止时间的
				heap.add(c[0]);
				time += c[0];
			} else { // 当前时间 + 花费 > 截止时间的, 只有淘汰掉某课，当前的课才能进来！
				if (!heap.isEmpty() && heap.peek() > c[0]) {
//					time -= heap.poll();
//					heap.add(c[0]);
//					time += c[0];
					heap.add(c[0]);
					time += c[0] - heap.poll();
				}
			}
		}
		return heap.size();
	}

	public int scheduleCourse2(int[][] courses) {
		Arrays.sort(courses,(a,b)->a[1]-b[1]);//按照截止时间从小到大排序
		PriorityQueue<Integer> pq=new PriorityQueue<>((a,b)->b-a);//大根堆作为一个门槛
		int time=0;//当前来到的时间点
		for(int[] c:courses){
			if(time+c[0]<=c[1]){//可以在截止时间之前完成就入堆
				pq.add(c[0]);
				time+=c[0];
			}else if(!pq.isEmpty()){
				if(c[0]<pq.peek()){
					time+=-pq.poll()+c[0];
					pq.add(c[0]);
				}
			}
		}
		return pq.size();
	}

}
