package Leetcode.AllQuestions;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

public class Leetcode_0352_DataStreamAsDisjointIntervals {
    class SummaryRanges {//使用TreeSet
        TreeSet<int[]> tset;
        public SummaryRanges() {
            tset=new TreeSet<>((a,b)->a[0]!=b[0]?a[0]-b[0]:a[1]-b[1]);
        }
        /**
         * 分类讨论：需要调整的情况就只有三种，其它情况直接插入。
         * @param value 区间为{value,value}
         */
        public void addNum(int value) {
            int[] cur=new int[]{value,value};
            int[] low=tset.floor(cur),high=tset.ceiling(cur);
            if (low!=null&&low[1]>=value||high!=null&&high[0]<=value) return;//被之前的区间含住了。
            if (low!=null&&low[1]+1==value&&high!=null&&high[0]==value+1){//下面是三种需要合并的情况
                low[1]=high[1];
                tset.remove(high);
            }else if (low!=null&&low[1]+1==value) low[1]=value;
            else if (high!=null&&high[0]==value+1) high[0]=value;
            else tset.add(cur);//没有任何合并的空间
        }

        public int[][] getIntervals() {
            return new ArrayList<>(tset).toArray(new int[tset.size()][]);
        }
    }

    class SummaryRanges2 {//使用TreeMap
        TreeMap<Integer,Integer> tmap;
        public SummaryRanges2() {
            tmap=new TreeMap<>();
        }

        public void addNum(int value) {
            int left=value,right=value;
            Integer L=tmap.floorKey(left);
            if (L!=null&&tmap.get(L)+1>=left) left=L;
            Integer cur=tmap.ceilingKey(left);
            while(cur!=null&&right+1>=cur){
                right = Math.max(right, tmap.get(cur));
                tmap.remove(cur);
                cur=tmap.ceilingKey(cur);
            }
            tmap.put(left,right);
        }

        public int[][] getIntervals() {
            List<int[]> ans=new ArrayList<>();
            for (Integer left : tmap.keySet()) {
                ans.add(new int[]{left,tmap.get(left)});
            }
            return ans.toArray(new int[ans.size()][]);
        }
    }

}
