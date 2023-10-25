package Leetcode.AllQuestions;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class Leetcode_0057_InsertInterval {
    public int[][] insert(int[][] intervals, int[] newInterval) {
        TreeMap<Integer, Integer> map = new TreeMap<>();
        for (int[] interval : intervals) {
            map.put(interval[0], interval[1]);
        }
        int left = newInterval[0], right = newInterval[1];
        Integer L = map.floorKey(left);
        if (L != null && left <= map.get(L)) left = L;
        Integer cur = map.ceilingKey(left);
        while (cur != null && cur <= right) {
            right = Math.max(right, map.get(cur));
            map.remove(cur);
            cur = map.ceilingKey(cur);
        }
        map.put(left, right);
        int[][] ans = new int[map.size()][2];
        int i = 0;
        for (Integer l : map.keySet()) {
            ans[i][0] = l;
            ans[i++][1] = map.get(l);
        }
        return ans;
    }

    //思路，和newInterval没有关系的一股脑丢进去，有关系的合并完丢进去
    public int[][] insert2(int[][] intervals, int[] newInterval) {
        List<int[]> ans = new ArrayList<>();
        boolean add=false;
        for (int[] cur : intervals) {
            if (cur[1]<newInterval[0])ans.add(cur);//第一阶段，还早！
            else if (cur[0]>newInterval[1]){//第三阶段，要把第二阶段merge完之后的newInterval加进来
                if (!add){
                    ans.add(newInterval);
                    add=true;
                }
                ans.add(cur);
            }else {//第二阶段 merge
                newInterval[0] = Math.min(newInterval[0],cur[0]);
                newInterval[1]=Math.max(newInterval[1],cur[1]);
            }
        }
        if (!add) ans.add(newInterval);//防止没有第三阶段，所以这里要单独判断
        return ans.toArray(new int[ans.size()][]);
    }

    public int[][] insert3(int[][] intervals, int[] newInterval) {//对2方法的写法进行了简化
        List<int[]> ans = new ArrayList<>();
        for (int[] cur : intervals) {
            if (newInterval==null||cur[1]<newInterval[0])ans.add(cur);//第一阶段，还早！判空，第一阶段和第三阶段融合
            else if (cur[0]>newInterval[1]){//第三阶段，要把第二阶段merge完之后的newInterval加进来,这个阶段只会进来一次，后面都去第一阶段
                ans.addAll(List.of(newInterval,cur));
                newInterval=null;
            }else {//第二阶段 merge
                newInterval[0] = Math.min(newInterval[0],cur[0]);
                newInterval[1]=Math.max(newInterval[1],cur[1]);
            }
        }
        if (newInterval!=null) ans.add(newInterval);//防止没有第三阶段，所以这里要单独判断
        return ans.toArray(new int[ans.size()][]);
    }
}
