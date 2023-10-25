package Leetcode.AllQuestions;

import java.util.*;

public class Leetcode_0207_CourseSchedule {//看Code0210
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        HashMap<Integer, List<Integer>> nodes=new HashMap<>();//邻接表表示
        int[] in=new int[numCourses];//入度
        for (int i = 0; i < numCourses; i++) nodes.put(i,new ArrayList<>());
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
        int[] ans=new int[numCourses];//选课顺序
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
        if (index!=numCourses) return false;
        return true;
    }

    //{ai,bi}代表bi要先修，那么就是bi指向ai
    public boolean canFinish2(int n, int[][] pre) {
        List<Integer>[] map=new List[n];
        for(int i=0;i<n;i++) map[i]=new ArrayList<>();
        var in=new int[n];
        for(var p:pre){
            int from=p[1],to=p[0];
            map[from].add(to);
            in[to]++;
        }
        Queue<Integer> q=new LinkedList<>();
        for(int i=0;i<n;i++) if(in[i]==0) q.add(i);
        while(!q.isEmpty()){
            int poll=q.poll();
            for(int next:map[poll]) if(--in[next]==0) q.add(next);
        }
        for(var v:in) if(v!=0) return false;
        return true;
    }

}
