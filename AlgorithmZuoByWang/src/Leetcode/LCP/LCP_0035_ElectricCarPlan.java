package Leetcode.LCP;

import java.util.*;
public class LCP_0035_ElectricCarPlan {
	//用一个三元组{cur,time,rest}代表从start到cur花费time的时间，还剩下rest的电量
	//用优先级队列来保证每次选出一个目前距离最近的结点来尝试移动，前提是rest电量足够
	public int electricCarPlan(int[][] paths, int cnt, int start, int end, int[] charge) {
		final int n=charge.length;
		List<int[]>[] map=new List[n];//map[i]是一个List，里面放的是i结点的边，{to,distance}
		for(int i=0;i<n;i++) map[i]=new ArrayList<>();
		for(var p:paths){//p={cityA,cityB,distance}
			map[p[0]].add(new int[]{p[1],p[2]});
			map[p[1]].add(new int[]{p[0],p[2]});
		}
		var visit=new boolean[n][cnt+1];//visit[i][j]：是否已经从start到达过i城市,到达时有j电
		var pq=new PriorityQueue<int[]>((a, b)->a[1]-b[1]);//按照第二维度time来做小根堆
		pq.add(new int[]{start,0,0});//当前在start结点，时间0，还剩下0的电
		while(!pq.isEmpty()){
			var node=pq.poll();
			int cur=node[0],time=node[1],rest=node[2];
			if(visit[cur][rest]) continue;
			visit[cur][rest]=true;
			if(cur==end) return time;
			//上面如果没有返回，说明没到end，那么在当前结点就有两种选择，一种是充电，一种是走路
			for(int i=rest+1;i<=cnt;i++){//充到i的电量才走
				pq.add(new int[]{cur,time+(i-rest)*charge[cur],i});
			}
			var nexts=map[cur];//从cur出发能到哪些结点
			for(var next:nexts){//next={to,distance}
				int to=next[0],distance=next[1];
				if(rest>=distance&&!visit[to][rest-distance]){//to没去过 且 当前剩余的电量足够开到to
					pq.add(new int[]{to,time+distance,rest-distance});
				}
			}
		}
		return -1;
	}
}
