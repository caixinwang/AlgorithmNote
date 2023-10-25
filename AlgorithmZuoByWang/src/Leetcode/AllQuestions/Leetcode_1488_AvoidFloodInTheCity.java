package Leetcode.AllQuestions;

import java.util.*;

public class Leetcode_1488_AvoidFloodInTheCity {
	//在我到达晴天之前，会在set累积一些下过雨的湖泊。等到晴天的时候，我们肯定在set里面选一个最近要下雨的湖泊去抽干。
	//如何快速的找到最近的发生水灾的湖泊？提前做出一个结构，是一个HashMap<Integer,List<Integer>>结构，代表每一个湖泊i在哪些天会下雨
	//然后准备两个set，一个是Hashset一个TreeSet，HashSet是下雨表，TreeSet是干活表。干活表按照下雨天数谁最近来排序。
	//干活表用小根堆也行。
	public int[] avoidFlood(int[] rains) {
		int n=rains.length;
		int[] ans=new int[n];
		Arrays.fill(ans,-1);
		HashSet<Integer> set=new HashSet<>();//当前有哪些湖泊是满的,里面放着lake编号
		HashMap<Integer,LinkedList<Integer>> map=new HashMap<>();//<lake,会在哪些天下雨>
		PriorityQueue<int[]> pq=new PriorityQueue<>((a,b)->a[1]-b[1]);
		for(int day=0;day<n;day++){//lake=rains[day]
			if(!map.containsKey(rains[day])) map.put(rains[day],new LinkedList<>());
			map.get(rains[day]).add(day);
		}
		for(int day=0;day<n;day++){
			if(rains[day]!=0){
				if(set.contains(rains[day])) return new int[]{};//lake如果之前已经满了，并且此时还要再lake下雨，那么就发洪水
				if(map.get(rains[day]).size()<2) continue;//如果lake湖没有满，但是后面不会下雨达到两次，直接不管
				set.add(rains[day]);//装到容器里面的意思就是当前装满的，并且后面还可能下雨的lake
				map.get(rains[day]).poll();//当前天被装到set里面，就弹出
				pq.add(new int[]{rains[day],map.get(rains[day]).peek()});//peek了解一下后面最近哪一天会下雨
			}else{
				if(pq.isEmpty()) {
					ans[day]=1;//说明没有活干，题目要求没有活干就去抽1号湖的水
				}else{
					int lake=pq.poll()[0];//抽干这个湖，这个湖最近会下雨
					set.remove(lake);//抽干了，现在不是满的了，从set中移除
					ans[day]=lake;
				}
			}
		}
		return ans;
	}


}
