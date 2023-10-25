package TestUtils;

import java.util.List;

public class CompareUtil {
    public static boolean compare(List<List<Integer>> lists1, List<List<Integer>> lists2) {
        if (lists1 == null && lists2 == null) return true;
        if (lists1 == null || lists2 == null) return false;
        if (lists1.size() != lists2.size()) return false;
        for (int i = 0; i < lists1.size(); i++) {
            List<Integer> list1 = lists1.get(i);
            List<Integer> list2 = lists2.get(i);
            if (list1 == null && list2 == null) break;
            if (list1 == null || list2 == null) return false;
            if (list1.size() != list2.size()) return false;
            for (int j = 0; j < list1.size(); j++) {
                Integer integer1 = list1.get(j);
                Integer integer2 = list2.get(j);
                if (integer1 != integer2) return false;
            }
        }
        return true;
    }
}
