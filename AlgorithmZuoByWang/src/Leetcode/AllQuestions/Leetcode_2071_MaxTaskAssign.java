package Leetcode.AllQuestions;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.TreeMap;

public class Leetcode_2071_MaxTaskAssign {
    public static int maxTaskAssign(int[] tasks, int[] workers, int pills, int strength) {
        Arrays.sort(tasks);//让最厉害的工人看看能不能搞定几个最简单的任务
        Arrays.sort(workers);
        int n=tasks.length,m= workers.length;
        reverse(workers,0,m-1);//从大到小
        int l=0,r=n;
        while(l<=r){
            int mid=l+(r-l>>1);
            if (solveKTasks(tasks,workers,pills,strength,mid)) l=mid+1;
            else r=mid-1;
        }
        return r;
    }

    public static boolean solveKTasks(int[] tasks, int[] workers, int pills, int strength,int k){
        int n=tasks.length,m= workers.length;
        if (k>m) return false;
        TreeMap<Integer,Integer> map=new TreeMap<>();//记录每一种力量的工人有几个
        for (int i = 0; i < k; i++) {
            if (!map.containsKey(workers[i])) map.put(workers[i],1 );
            else map.put(workers[i],1+map.get(workers[i]));
        }
        for (int i = k-1; i >=0; i--) {//从最 难 的任务开始
            Integer wok = map.ceilingKey(tasks[i]);//看看我们最厉害的工人能不能搞定最难的任务
            if (wok!=null){//如果可以搞定最难的任务，那么就让它去搞定这个最难的任务，这和他去搞定一个简单的任务是等价的
                if (map.get(wok)==1) map.remove(wok);
                else map.put(wok,-1+map.get(wok));
                continue;
            }
            //走到这，潜台词是我们最厉害的工人没有办法搞定最难的任务，那么我们需要吃药去搞定这个任务。收益最大的肯定是让能力值小的吃药
            if (pills==0) return false;//已经没有药了，说明这个任务搞不定了
            wok=map.ceilingKey(tasks[i]-strength);
            if (wok==null) return false;//就算吃药也没有工人能搞定这个任务，k任务宣告失败
            pills--;//给工人吃药
            if (map.get(wok)==1) map.remove(wok);
            else map.put(wok,-1+map.get(wok));
        }
        return true;
    }

    public static int maxTaskAssign2(int[] tasks, int[] workers, int pills, int strength) {
        Arrays.sort(tasks);//让最笨的工人去完成最简单的任务
        Arrays.sort(workers);
        int n=tasks.length,m= workers.length;
        int l=0,r=n;
        while(l<=r){
            int mid=l+(r-l>>1);
            if (solveKTasks2(tasks,workers,pills,strength,mid)) l=mid+1;
            else r=mid-1;
        }
        return r;
    }

    //单调性：维持一个任务难度从小到大的序列
    // 1.笨工人能完成简单的任务没有必要让厉害的工人完成简单的任务
    // 2.由于工人从小到大遍历，队列里面如果有任务，那么当前工人磕了药一定足够完成那个最难的任务。因为队列里面的任务之前的工人嗑药也能完成。
    // 贪心：笨工人不嗑药只需完成一个最简单的队头任务。如果嗑药需要完成一个队头的最难队尾的任务。
    // 前面我们让最厉害的工人搞定最难的任务，等价于最笨的工人搞定最简单的任务。
    public static boolean solveKTasks2(int[] tasks, int[] workers, int pills, int strength,int k){
        int n=tasks.length,m= workers.length;
        if (k>m) return false;
        LinkedList<Integer> dq=new LinkedList<>();//存值；不需要踢人，由于排序过的原因，自动就是单调的
        int ti=0;//从最简单的任务开始
        for (int i = m-k; i < m; i++) {//从前k个厉害的工人中的比较笨的工人开始遍历
            while (ti<k&&workers[i]>=tasks[ti]) dq.addLast(tasks[ti++]);//将当前工人能完成的任务扔到队列里面
            if (!dq.isEmpty()&&workers[i]>=dq.peekFirst()){//最笨的工人不吃药的情况下完成队头简单的任务即可
                dq.pollFirst();
                continue;
            }
            //走到这里，潜台词是最笨的工人无法完成最简单的任务，那么这个工人要吃药才能完成一个任务，这个工人需要挑走队尾的那个最难的任务
            if (pills-- == 0) return false;
            while (ti<k&&workers[i]+strength>=tasks[ti]) dq.addLast(tasks[ti++]);//将当前工人吃药之后能完成的任务入队
            if (dq.isEmpty()) return false;//说明笨工人吃了药也没有办法完成任务，宣告失败
            dq.pollLast();
        }
        return true;//遍历结束，说明每一个工人完成一个任务。
    }

    public static void reverse(int[] arr,int l,int r){
        for (int t;l<r;t=arr[l],arr[l]=arr[r],arr[r]=t,l++,r--);
    }

    public static void main(String[] args) {
        int[] task=new int[]{3,2,1};
        int[] workers=new int[]{0,3,3};
        System.out.println(maxTaskAssign(task,workers,1,1));
    }
}
