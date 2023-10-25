package Leetcode.AllQuestions;
import java.util.*;
public class Leetcode_0743_NetworkDelayTime {
    public int networkDelayTime(int[][] times, int n, int k) {
        int ans=-1;
        List<int[]>[] map=new List[n];
        for(int i=0;i<n;i++) map[i]=new ArrayList<>();//{to,time}
        for(var t:times){//t = (from, to, time)
            map[t[0]-1].add(new int[]{t[1]-1,t[2]});
        }
        Queue<int[]> queue=new PriorityQueue<>((a,b)->a[1]-b[1]);//{cur,allTime}
        var visit=new boolean[n];
        queue.add(new int[]{k-1,0});
        while(!queue.isEmpty()){
            var node=queue.poll();
            int cur=node[0],allTime=node[1];
            if(visit[cur]) continue;
            visit[cur]=true;
            if(allTime>ans) ans=allTime;
            for(var next:map[cur]){
                int to=next[0],time=next[1];
                if(!visit[to]){
                    queue.add(new int[]{to,allTime+time});
                }
            }
        }
        for(var v:visit) if(!v) return -1;
        return ans;
    }
}
