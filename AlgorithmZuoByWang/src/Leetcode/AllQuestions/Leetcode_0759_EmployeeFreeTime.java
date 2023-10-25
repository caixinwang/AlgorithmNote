package Leetcode.AllQuestions;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Leetcode_0759_EmployeeFreeTime {
    class Interval {
        public int start;
        public int end;

        public Interval() {}

        public Interval(int _start, int _end) {
            start = _start;
            end = _end;
        }
    };

    public List<Interval> employeeFreeTime(List<List<Interval>> schedule) {
        PriorityQueue<Interval> queue=new PriorityQueue<>((a,b)->a.start-b.start);//开始时间的小根堆
        for (List<Interval> intervals : schedule) queue.addAll(intervals);
        Interval cur=queue.poll();//拿着开始时间最早的开始合并
        List<Interval> ans=new ArrayList<>();
        while(!queue.isEmpty()){
            if (cur.end>=queue.peek().start){//有重合
                cur.end = Math.max(cur.end, queue.poll().end);//合并
            }else {
                ans.add(new Interval(cur.end,queue.peek().start));//没有重合说明出答案了
                cur=queue.poll();
            }
        }
        return ans;//直接就是答案了，因为[xx,inf]不算一个有效区间
    }

    public List<Interval> employeeFreeTime2(List<List<Interval>> schedule) {//用数组做
        List<Interval> intervals=new ArrayList<>();
        for (List<Interval> list:schedule) intervals.addAll(list);//打扁平
        intervals.sort((a, b) -> a.start - b.start);
        Interval cur=intervals.get(0);
        List<Interval> ans=new ArrayList<>();
        for (Interval next:intervals){
            if (cur.end>=next.start) cur.end = Math.max(cur.end, next.end);
            else {
                ans.add(new Interval(cur.end,next.start));
                cur=next;
            }
        }
        return ans;
    }


}
