package Leetcode.AllQuestions;

import java.util.*;

// 来自三七互娱
// Leetcode原题 : https://leetcode.com/problems/bus-routes/
//记录每一个结点所拥有的线路，每次宽度优先遍历都去新的线路。这题的宽度优先遍历是以公交线路做宽度优先遍历
//利用我们的一次搞一层的方法去做宽度优先遍历，一次性把所有的新线路搞出来，作为下一层。
public class Leetcode_0815_BusRoutes {//宽度优先遍历，求最短距离。

	/**
	 * 公交车其实就是线路，所以这题我们按照线路来遍历，所以我们需要知道一个线路可以通往哪些线路。
	 * 但是由于这题给的是每条线路经过哪些车站，所以我们不好直接转化为一个线路可以通往哪些线路，所以我们只能先
	 * 得到车站到线路的映射，然后动态的去走线路去经过一个一个的车站，然后动态的判断是否经过target。
	 * 经过的线路以及车站都不要重复进入，不然会很慢。
	 * @param routes route[i]代表i线路所经过的所有的车站
	 * @param source 出发的车站
	 * @param target 目标车站
	 * @return 返回最少座几辆公交车（线路）可以到目标车站
	 */
	public static int numBusesToDestination(int[][] routes, int source, int target) {
		if (source==target) return 0;
		HashMap<Integer,ArrayList<Integer>> map=new HashMap<>();//<车站，线路>
		for (int i = 0; i < routes.length; i++) {//i号线路
			int[] route=routes[i];
			for (int station:route){
				if (!map.containsKey(station)){
					map.put(station,new ArrayList<>());
				}
				map.get(station).add(i);
			}
		}
		Queue<Integer> queue=new LinkedList<>();
		Set<Integer> routeSet=new HashSet<>();
		Set<Integer> stationSet=new HashSet<>();
		for (Integer route : map.get(source)) {
			queue.add(route);
			routeSet.add(route);
		}
		int len=1;
		while(!queue.isEmpty()){
			int size= queue.size();
			for (int i = 0; i < size; i++) {
				int[] route=routes[queue.poll()];//线路拿出去来，实际去走，然后看看解锁了哪些新的线路
				for (int station:route){//线路实际去走
					if (station==target) return len;
					if (stationSet.contains(station)) continue;//这里是continue，可不是break
					stationSet.add(station);
					ArrayList<Integer> hasRoutes = map.get(station);
					for (int hasRoute:hasRoutes){//经过车站看看它能否解锁一些新线路。
						if (!routeSet.contains(hasRoute)){
							queue.add(hasRoute);//新线路加入队列
							routeSet.add(hasRoute);
						}
					}
				}
			}
			len++;
		}
		return -1;
	}

}
