package ClassicInterviewCoding.InterviewCoding05;

import java.util.*;

public class Code05_WordMinPaths {
    /**
     *
     * 思路：首先把给出的list，变成一张图，里面的每个结点代表string，边代表两个字符串之间差一个字符可以变到
     * 这个图其实就是一个装List的HashMap。图做出来之后，通过一个宽度优先遍历把start到其它结点的最短距离都求出来
     * 然后用深度优先遍历收集start到end的答案。
     */

    /**
     * 1.生成图---是一个邻接链表，用一个HashMap表示
     * 2.宽度优先遍历把start到图中任何一个结点的距离记到一个HashMap中---getDistanceMap()
     * 3.广度优先遍历，把所有符合要求的答案收集起来，利用distanceMap进行剪支
     *
     * @param start 开始的字符串
     * @param end   结束的字符串
     * @param list  所有的字符串都在list里面，start和end也包括在内。start到end途经的string也在list中
     * @return 返回start到end的所有最短的路径，是一个List的List
     */
    public static List<List<String>> getPaths(String start, String end, List<String> list) {
        List<List<String>> res = new LinkedList<>();
        HashMap<String, List<String>> map = getMap(list);//生成图
        HashMap<String, Integer> distanceMap = getDistanceMap(start, map);
        getAnswerList(map, distanceMap, res, new LinkedList<String>(), start, end);
        return res;
    }

    /**
     * 生成每个string对应的边的List的方法：先把list中所有的string放到一个set中。假设现在要生成s对应的边的list
     * 那么就把s[0...N-1]位置上的字符依次改成'a~z'，然后和set里面的比对，如果有那么就加入list中。可以把这个过程
     * 写在一个方法里面getList(String s,Set<String> set).
     * 图的表示方法就是邻接表，每个String对应一个List--里面是它的邻居
     *
     * @param list 字符串集
     * @return 把list中的字符串当成一个一个的结点，只相差一个字符的字符串之间有边。我们把一个字符串能到达的所有的字符串
     * 也就是有之间有边的，都放到一个List中，例如abc这个字符串，就对应[abd,abe]等等
     */
    public static HashMap<String, List<String>> getMap(List<String> list) {
        HashMap<String, List<String>> res = new HashMap<>();//
        Set<String> set = new HashSet<>(list);//list里面所有的string都塞进去
        for (String s : list) {//生成s对应的边的list，放入res
            res.put(s, getList(s, set));
        }
        return res;
    }

    public static List<String> getList(String s, Set<String> set) {
        char[] str = s.toCharArray();
        List<String> res = new LinkedList<>();
        for (int i = 0; i < str.length; i++) {//str的每一个位置都去替换26个字母
            for (char j = 'a'; j <= 'z'; j++) {//'a'~'z'都去换一遍
                if (str[i] != j) {//不相等的才去换
                    char tmp = str[i];//保存原本的值，方便恢复现场
                    str[i] = j;//换掉
                    if (set.contains(String.valueOf(str))) res.add(String.valueOf(str));//检查，如果set有就加入
                    str[i] = tmp;//恢复现场
                }
            }
        }
        return res;
    }

    /**
     * 思想：层序遍历，利用一个set不走回头路
     *
     * @param start 出发点
     * @param map   图
     * @return 返回出发点到图中点的最短距离。(k,v)代表start到k结点的最短距离是v。由于边的权重都是1，所以不用Dijkstra算法
     */
    public static HashMap<String, Integer> getDistanceMap(String start, HashMap<String, List<String>> map) {
        HashMap<String, Integer> distanceMap = new HashMap<>();
        Set<String> set = new HashSet<>();//不走回头路
        Queue<String> queue = new LinkedList<>();//层序遍历
        distanceMap.put(start, 0);
        set.add(start);
        queue.add(start);
        while (!queue.isEmpty()) {
            String poll = queue.poll();
            for (String s : map.get(poll)) {//拿到pool的所有邻居
                if (!set.contains(s)) {//不走回头路
                    queue.add(s);//如果没走过就进队列
                    distanceMap.put(s, distanceMap.get(poll) + 1);//值为start到poll的距离+1
                    set.add(s);//代表你已经得到了一个最小值了，后面不能再走向你了
                }
            }
        }
        return distanceMap;
    }

    /**
     * 宽度优先只是得到了最短的距离，并没有把所有的路走一遍。
     * 我们利用深度优先来抓住所有能得到这个最短距离的路径，并且利用distanceMap来淘汰一些没有的分支！
     * @param map         图，邻接链表
     * @param distanceMap 表示start到任意一个结点的最小值
     * @param res         如果path符合答案，就把path收入res中
     * @param path        path是一个String的List，而不是String
     * @param cur         当前来到的结点，加到Path里面，记得恢复现场
     * @param end         最终要到达的结点，如果到了就把path收入答案
     */
    public static void getAnswerList(
            HashMap<String, List<String>> map,
            HashMap<String, Integer> distanceMap,
            List<List<String>> res,
            LinkedList<String> path,
            String cur,
            String end) {
        path.add(cur);//将当前结点加入path中
        if (cur.equals(end)) {
            res.add(new LinkedList<>(path));//不能直接res.add(path)，因为path在内存中是一直被用的
        } else {
            for (String s : map.get(cur)) {
                if (distanceMap.get(s) == distanceMap.get(cur) + 1) {//并不是所有分支都进去，要进行过滤
                    getAnswerList(map, distanceMap, res, path, s, end);
                }
            }
        }
        path.pollLast();//恢复现场
    }

    public static void main(String[] args) {
        String start = "abc";
        String end = "cab";
        String[] test = {"abc", "cab", "acc", "cbc", "ccc", "cac", "cbb",
                "aab", "abb"};
        ArrayList<String> list = new ArrayList<>();
        list.addAll(Arrays.asList(test));
        List<List<String>> res = getPaths(start, end, list);
        for (List<String> obj : res) {
            for (String str : obj) {
                System.out.print(str + " -> ");
            }
            System.out.println();
        }

    }
}
