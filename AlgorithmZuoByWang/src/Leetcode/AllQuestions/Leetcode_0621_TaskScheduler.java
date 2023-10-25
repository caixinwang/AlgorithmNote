package Leetcode.AllQuestions;

/**
 * 总的时间就是N加上空格数。我们目标是让空格尽量少。做法：找出出现次数最高的，先让次数最高的合法，
 * 然后再让次数少的插在给次数最高分配的每个区域里面。
 * 让别的任务插在这些_上。如果消耗完了，那么时间就是N，否则就是N+空格。
 * A________A________A________A
 * 最多次数的额外多分一组，前面的k-1组是一定要占这么多时间的。
 */
public class Leetcode_0621_TaskScheduler {

	public static int leastInterval(char[] tasks, int free) {
		int[] count = new int[256];
		int maxCount = 0;//出现最多次的任务，到底是出现了几次
		int all=0;//总的任务数
		for (char task : tasks) {
			count[task]++;
			all++;
			maxCount = Math.max(maxCount, count[task]);
		}
		int maxSame=0;
		for (int num:count) if (num==maxCount) maxSame++;
//		if ((maxCount-1)*(free+1-maxSame)<=(all-(maxCount*maxSame))) return tasks.length;//空格数小于剩下的个数
//		else return (free+1)*(maxCount-1)+maxSame;//大于剩下的个数
		return maxSame+Math.max(all-maxSame,(free+1)*(maxCount-1));//这句逻辑是最后一组加上前面的k-1组
	}


}
