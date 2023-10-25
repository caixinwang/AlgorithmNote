package Leetcode.AllQuestions;

import java.util.*;

public class Leetcode_0391_IsRectangleCover {
    //如果是完美矩形 那么一定满足两点：
    // （1）最左下 最左上 最右下 最右上 的四个点只出现一次 其他点成对出现
    // （2）四个点围成的矩形面积 = 小矩形的面积之和
    public boolean isRectangleCover(int[][] rectangles) {
        int left = Integer.MAX_VALUE;
        int right = Integer.MIN_VALUE;
        int top = Integer.MIN_VALUE;
        int bottom = Integer.MAX_VALUE;
        int n = rectangles.length;

        Set<String> set = new HashSet<>();
        int sumArea = 0;

        for (int i = 0; i < n; i++) {
            //获取四个点的坐标
            left = Math.min(left, rectangles[i][0]);
            bottom = Math.min(bottom, rectangles[i][1]);
            right = Math.max(right, rectangles[i][2]);
            top = Math.max(top, rectangles[i][3]);
            //计算总小矩形的面积
            sumArea += (rectangles[i][3] - rectangles[i][1]) * (rectangles[i][2] - rectangles[i][0]);
            //分别记录小矩形的坐标
            String lt = rectangles[i][0] + " " + rectangles[i][3];
            String lb = rectangles[i][0] + " " + rectangles[i][1];
            String rt = rectangles[i][2] + " " + rectangles[i][3];
            String rb = rectangles[i][2] + " " + rectangles[i][1];
            //如果有就移除 没有就加入
            if (!set.contains(lt)) set.add(lt);
                else set.remove(lt);
            if (!set.contains(lb)) set.add(lb);
                else set.remove(lb);
            if (!set.contains(rt)) set.add(rt);
                else set.remove(rt);
            if (!set.contains(rb)) set.add(rb);
                else set.remove(rb);
        }

        //最后只剩4个大矩形坐标且面积相等即为完美矩形
        if (set.size() == 4 && set.contains(left + " " + top) && set.contains(left + " " + bottom) && set.contains(right + " " + bottom) && set.contains(right + " " + top)) {
            return sumArea == (right - left) * (top - bottom);
        }
        return false;
    }


    //扫描线
    public boolean isRectangleCover2(int[][] rectangles) {
        int n = rectangles.length;
        int[][] rs = new int[n * 2][4];
        for (int i = 0, idx = 0; i < n; i++) {
            int[] re = rectangles[i];
            rs[idx++] = new int[]{re[0], re[1], re[3], 1};
            rs[idx++] = new int[]{re[2], re[1], re[3], -1};
        }
        Arrays.sort(rs, (a, b) -> {
            if (a[0] != b[0]) return a[0] - b[0];
            return a[1] - b[1];
        });
        n *= 2;
        // 分别存储相同的横坐标下「左边的线段」和「右边的线段」 (y1, y2)
        List<int[]> l1 = new ArrayList<>(), l2 = new ArrayList<>();
        for (int l = 0; l < n; ) {
            int r = l;
            l1.clear();
            l2.clear();
            // 找到横坐标相同部分
            while (r < n && rs[r][0] == rs[l][0]) r++;
            for (int i = l; i < r; i++) {
                int[] cur = new int[]{rs[i][1], rs[i][2]};
                List<int[]> list = rs[i][3] == 1 ? l1 : l2;
                if (list.isEmpty()) {
                    list.add(cur);
                } else {
                    int[] prev = list.get(list.size() - 1);
                    if (cur[0] < prev[1]) return false; // 存在重叠
                    else if (cur[0] == prev[1]) prev[1] = cur[1]; // 首尾相连
                    else list.add(cur);
                }
            }
            if (l > 0 && r < n) {
                // 若不是完美矩形的边缘竖边，检查是否成对出现
                if (l1.size() != l2.size()) return false;
                for (int i = 0; i < l1.size(); i++) {
                    if (l1.get(i)[0] == l2.get(i)[0] && l1.get(i)[1] == l2.get(i)[1]) continue;
                    return false;
                }
            } else {
                // 若是完美矩形的边缘竖边，检查是否形成完整一段
                if (l1.size() + l2.size() != 1) return false;
            }
            l = r;
        }
        return true;
    }

    static final int MIN=2<<31,MAX=MIN-1;



}
