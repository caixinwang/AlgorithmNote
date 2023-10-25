package Leetcode.AllQuestions;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Leetcode_0090_SubsetsWithDup {
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        var cnt = new int[21];//num -> cnt[num+10]
        for (var c : nums) cnt[c + 10]++;
        List<List<Integer>> ans = new ArrayList<>();
        f(cnt, 0, new LinkedList<>(), ans);
        return ans;
    }

    //这里的index其实代表的是数值,index-10就是真实要加入的数值
    public void f(int[] cnt, int index, LinkedList<Integer> path, List<List<Integer>> ans) {
        if (index == 21) {
            ans.add(new LinkedList<>(path));
            return;
        }
        for (int take = 0; take <= cnt[index]; take++) {
            for (int i = 0; i < take; i++) path.addLast(index - 10);
            f(cnt, index + 1, path, ans);
            for (int i = 0; i < take; i++) path.pollLast();
        }
    }

    public static void main(String[] args) {
        List<String> list = List.of("1", "2", "3", "4", "5", "6", "7", "8");
        list.stream()
                .filter(i -> Integer.parseInt(i) % 2 == 0)
                .map(i -> i.repeat(2))
                .sorted((a,b)->b.compareTo(a))
                .forEach(i -> System.out.println(i));
    }
}
