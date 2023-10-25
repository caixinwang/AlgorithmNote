package Leetcode.AllQuestions;

import java.util.*;

public class Leetcode_0210_CourseScheduleII {

	/**
	 * 使用宽度优先遍历的方式，将入度为零的结点入队。这种方法不需要visit数组在遍历的过程中判环，因为如果有环，
	 * 那个环中就没有入度为0的点，也就是最终将收集不到numCourses个课程。
	 * @param numCourses 课程数量
	 * @param prerequisites 先修课程信息
	 * @return 返回一种选课顺序，也就是一种拓扑排序
	 */
	public int[] findOrder(int numCourses, int[][] prerequisites) {
		HashMap<Integer, List<Integer>> nodes=new HashMap<>();//邻接表表示
		int[] in=new int[numCourses];//入度
		for (int i = 0; i < numCourses; i++) {
			nodes.put(i,new ArrayList<>());
		}
		for (int[] pre : prerequisites){
			int form=pre[1];
			int to=pre[0];
			in[to]++;
			List<Integer> list = nodes.get(form);//from的邻接表拿出来
			list.add(to);//把能到达的结点to加入
		}
		Queue<Integer> queue=new LinkedList<>();
		for (int i = 0; i < in.length; i++) {
			if (in[i]==0)queue.add(i);//如果i结点的入度为0，那么就把i结点入队
		}
		int[] ans=new int[numCourses];
		int index=0;
		while(!queue.isEmpty()){
			int cur = queue.poll();
			ans[index++]=cur;
			List<Integer> list = nodes.get(cur);//cur的邻接表
			for (int to:list){//cur能到达的邻居
				if (--in[to]==0){
					queue.add(to);
				}
			}
		}
		if (index!=numCourses) return new int[0];
		return ans;
	}

	/**
	 * 深度优先遍历，遍历到的最深处是你应该最晚修的课程，应该放到最后去，在这个遍历过程中需要有一个visit数组帮你记录
	 * 深度优先遍历的状态，帮助你判断是否有环。
	 * @param numCourses 课程数
	 * @param prerequisites 先修课程
	 * @return 返回一种修课的方案
	 */
	public int[] findOrder2(int numCourses, int[][] prerequisites) {
		HashMap<Integer, List<Integer>> nodes=new HashMap<>();//邻接表表示
		byte[] visit=new byte[numCourses];//0:未遍历 1:正在遍历 2:已完成
		for (int i = 0; i < numCourses; i++) {
			nodes.put(i,new ArrayList<>());
		}
		for (int[] pre : prerequisites){
			int form=pre[1];
			int to=pre[0];
			List<Integer> list = nodes.get(form);//from的邻接表拿出来
			list.add(to);//把能到达的结点to加入
		}
		int[] ans=new int[numCourses];
		int[] index=new int[1];
		index[0]=numCourses-1;
		for (int i = 0; i < numCourses; i++) {
			if (visit[i]==0)dfs(nodes,i,visit,ans,index);
		}
		if (index[0]!=0) return new int[0];
		return ans;
	}

	/**
	 *
	 * @param nodes 邻接表表示法的图
	 * @param cur 当前正在遍历的结点
	 * @param visit 每个结点的状态记录下来
	 * @param ans 从孩子返回的时候才放入自己，因为孩子要先修
	 * @param index 其实就是一个引用，表示当前ans填到了哪里
	 */
	public void dfs(HashMap<Integer, List<Integer>> nodes,int cur,byte[] visit,int[] ans,int[] index){
		visit[cur]=1;//上来标记为正在遍历的状态
		List<Integer> list = nodes.get(cur);
		if (list.size()!=0){//有孩子就去看看能否深入
			for (int next:list){
				if (visit[next]!=2){//如果孩子没有完成就看看能否深入
					if (visit[next]==1) return;//出现环，直接退出，不然死循环，并且这种情况下永远都没办法填满ans了
					dfs(nodes,next,visit,ans,index);//深度优先遍历
				}
			}
		}
		visit[cur]=2;//回到自己的时候标记为已经完成
		ans[index[0]--]=cur;//并且填入答案
	}
}
