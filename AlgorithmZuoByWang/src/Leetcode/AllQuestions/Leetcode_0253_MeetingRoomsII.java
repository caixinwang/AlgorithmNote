package Leetcode.AllQuestions;

import TestUtils.ArrayUtil;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Leetcode_0253_MeetingRoomsII {

	public static int minMeetingRooms(int[][] m) {
		Line[] lines = new Line[m.length];
		for (int i = 0; i < m.length; i++) {
			lines[i] = new Line(m[i][0], m[i][1]);
		}
		Arrays.sort(lines, new StartComparator());//按照开始时间从小到大排序
		PriorityQueue<Line> heap = new PriorityQueue<>(new EndComparator());//按照结束时间组织最小堆，谁早结束谁在堆顶
		int max = 0;
		for (int i = 0; i < lines.length; i++) {
			while (!heap.isEmpty() && heap.peek().end <= lines[i].start) {
				heap.poll();
			}
			heap.add(lines[i]);
			max = Math.max(max, heap.size());
		}
		return max;
	}

	public static class Line {
		public int start;
		public int end;

		public Line(int s, int e) {
			start = s;
			end = e;
		}
	}

	public static class StartComparator implements Comparator<Line> {

		@Override
		public int compare(Line o1, Line o2) {
			return o1.start - o2.start;
		}

	}

	public static class EndComparator implements Comparator<Line> {

		@Override
		public int compare(Line o1, Line o2) {
			return o1.end - o2.end;
		}

	}

	//这种解法的核心思路就是我不关心具体是哪个会议的开始时间和结束时间，我只关心什么时候我们需要一间空会议室，
	//什么时候可以放出一间空会议室。
	public static int minMeetingRooms2(int[][] m) {//这种方法如果有捣蛋会议会出问题，会少算，捣蛋会议就是开始时间和结束时间一样的
		int N=m.length;
		int[] starts=new int[N],ends=new int[N];
		for (int i=0;i<N;i++){
			starts[i]=m[i][0];
			ends[i]=m[i][1];
		}
		Arrays.sort(starts);
		Arrays.sort(ends);
		int ans=0;
		for (int i = 0,ei=0; i < starts.length; i++) {
			if (starts[i]<ends[ei]) ans++;
			else ei++;
		}
		return ans;
	}

	//下面是扫描线的方法，只关心起始时间和结束时间。算出最大的重合度
	public static int minMeetingRooms3(int[][] m) {
		int N=m.length;
		int[][] infos=new int[N <<1][2];//info -> [位置，权重]
		for (int i = 0; i < m.length; i++) {
			infos[i <<1]=new int[] {m[i][0],1};//会议开始的时候权重为1
			infos[i <<1|1]=new int[]{m[i][1],-1};//结束时间权重为-1
		}
		Arrays.sort(infos,(a,b)->a[0]!=b[0]?a[0]-b[0]:a[1]-b[1]);//这里权重一定是从小到大，因为结束时间和开始时间相同可以进入统一会议
		int ans=0,count=0;
		for (int[] info:infos){
			count+=info[1];
			ans = Math.max(ans, count);
		}
		return ans;
	}

	static ArrayUtil au=new ArrayUtil();

	public static int[][] generateMeeting(int size){
		int[][] meetings=new int[size][2];
		for (int[] meeting : meetings) {
			meeting[0]=au.ran(0,100);
			meeting[1]=au.ran(meeting[0]+1,101);
		}
		return meetings;
	}


	public static void main(String[] args) {
		for (int i = 0; i < 1000; i++) {
			int[][] meetings = generateMeeting(au.ran(1,50));
			int ans1=minMeetingRooms3(meetings);
			int ans2=minMeetingRooms2(meetings);
			if (ans1!=ans2) {
				System.out.printf("oops!\nans1=%d\nans2=%d\n",ans1,ans2);
			}
		}
	}

}
