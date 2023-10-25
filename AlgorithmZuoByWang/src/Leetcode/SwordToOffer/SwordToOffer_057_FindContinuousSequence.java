package Leetcode.SwordToOffer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class SwordToOffer_057_FindContinuousSequence {
    public int[][] findContinuousSequence(int target) {
        List<List<Integer>> ans = new ArrayList<>();
        f(target, 0, new LinkedList<>(), ans);
        int[][] res = new int[ans.size()][];
        for (int i = 0; i < res.length; i++) {
            List<Integer> list = ans.get(i);
            res[i] = new int[list.size()];
            int index = 0;
            for (int num : list) {
                res[i][index++] = num;
            }
        }
        return res;
    }

    public void f(int rest, int pre, LinkedList<Integer> path, List<List<Integer>> ans) {
        if (rest == 0 && path.size() >= 2) ans.add(new LinkedList<>(path));
        if (rest <= pre) return;
        if (pre == 0) {
            for (int i = pre + 1; i <= rest; i++) {
                path.addLast(i);
                f(rest - i, i, path, ans);
                path.pollLast();
            }
        } else {
            path.addLast(pre + 1);
            f(rest - pre - 1, pre + 1, path, ans);
            path.pollLast();
        }
    }

    public static int[][] findContinuousSequence2(int target) {//二分
        List<List<Integer>> ans = new ArrayList<>();
        for (int start = 1; start < target; start++) {
            int l = 1, r = (int) Math.sqrt(target << 1),mid=l + (r - l >> 1);//r这里直接赋值target-1
            long compute=0;
            while (l <= r) {
                mid = l + (r - l >> 1);
                compute = (2L * start + mid) * (mid + 1L);//等差数列求和公式
                if (compute == target<<1) break;
                else if (compute > target<<1) {
                    r = mid - 1;
                } else {
                    l = mid + 1;
                }
            }
            if (compute==target<<1){
                List<Integer> list=new ArrayList<>();
                for (int i=start;i<=start+mid;i++) list.add(i);
                ans.add(list);
            }
        }
        int[][] res = new int[ans.size()][];
        for (int i = 0; i < res.length; i++) {
            List<Integer> list = ans.get(i);
            res[i] = new int[list.size()];
            int index = 0;
            for (int num : list) {
                res[i][index++] = num;
            }
        }
        return res;
    }

}
