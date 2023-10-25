package Leetcode.AllQuestions;

import java.util.*;

public class Leetcode_0056_MergeIntervals {
    static class Node {
        public int start;
        public int end;

        public Node(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    static class NodeComparator implements Comparator<Node> {//区间开头小的在前面，区间开头相同，区间结尾大的在前面

        @Override
        public int compare(Node o1, Node o2) {
            return o1.start != o2.start ? o1.start - o2.start : o2.end - o1.end;
        }
    }

    public int[][] merge(int[][] intervals) {
        if (intervals == null || intervals.length == 0) return null;
        if (intervals.length == 1) return new int[][]{{intervals[0][0], intervals[0][1]}};
        LinkedList<List<Integer>> ans = new LinkedList<>();
        Node[] nodes = new Node[intervals.length];
        for (int i = 0; i < intervals.length; i++) {
            nodes[i] = new Node(intervals[i][0], intervals[i][1]);
        }
        Arrays.sort(nodes, new NodeComparator());
        LinkedList<Node> difStart = new LinkedList<>();
        int i = -1;
        while (i < nodes.length) {
            while (++i < nodes.length && !(i == 0 || nodes[i].start != nodes[i - 1].start)) ;//跳到第一个达标的位置，达标的条件在！后面
            if (i < nodes.length) difStart.add(nodes[i]);
        }
        while (difStart.size() >= 2) {
            Node node1 = difStart.pollFirst();//node1 --  node2
            Node node2 = difStart.pollFirst();
            if (node2.start <= node1.end) {//合并完再放回去
                node1.end = Math.max(node1.end, node2.end);
                difStart.addFirst(node1);
            } else {//合并不了，结算node1，把node2放回去
                addNode(ans, node1);
                difStart.addFirst(node2);
            }
        }
        addNode(ans, difStart.pollFirst());
        int[][] res = new int[ans.size()][2];
        for (int k = 0; k < res.length; k++) {
            List<Integer> list = ans.pollFirst();
            res[k][0] = list.get(0);
            res[k][1] = list.get(1);
        }
        return res;
    }

    public void addNode(List<List<Integer>> ans, Node node) {
        List<Integer> list = new ArrayList<>();
        list.add(node.start);
        list.add(node.end);
        ans.add(list);
    }

    public int[][] merge2(int[][] intervals) {
        TreeMap<Integer, Integer> tmap = new TreeMap<>();
        for (int[] interval : intervals) addInterval(tmap, interval);
        int[][] ans = new int[tmap.size()][2];
        int i = 0;
        for (Map.Entry<Integer, Integer> interval : tmap.entrySet()) {
            ans[i][0] = interval.getKey();
            ans[i++][1] = interval.getValue();
        }
        return ans;
    }

    public void addInterval(TreeMap<Integer, Integer> tmap, int[] interval) {//这个算法用途广，空降一个区间都可以搞定
        int left = interval[0], right = interval[1];
        Integer L = tmap.floorKey(left);
        if (L != null && left <= tmap.get(L)) left = L;//left左扩成功
        Integer cur = tmap.ceilingKey(left);//去右边吞并
        while (cur != null && cur <= right) {//cur代表一个开始端点，在right的左侧说明可以吞并！
            right = Math.max(right, tmap.get(cur));
            tmap.remove(cur);
            cur = tmap.ceilingKey(cur);
        }
        tmap.put(left, right);
    }

    public int[][] merge3(int[][] intervals) {//这种做法是先排序，然后从左到右去merge，由于区间不是空降，所以简化了逻辑
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        List<int[]> ans = new ArrayList<>();
        int[] cur = intervals[0];
        for (int i = 1; i < intervals.length; i++) {
            int[] next = intervals[i];
            if (next[0] <= cur[1]) {
				cur[1] = Math.max(cur[1], next[1]);
			} else {
                ans.add(cur);
                cur = next;
            }
        }
        ans.add(cur);//由于添加时机取决于后面的interval，所以出来的时候需要再添加一次
        return ans.toArray(new int[ans.size()][]);
    }
}
