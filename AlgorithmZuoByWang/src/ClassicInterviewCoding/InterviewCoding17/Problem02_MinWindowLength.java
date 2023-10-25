package ClassicInterviewCoding.InterviewCoding17;

import TestUtils.StringUtil;

public class Problem02_MinWindowLength {

    public static int minLength(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() < s2.length() || s2.length() == 0) {
            return Integer.MAX_VALUE;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        int[] map = new int[256]; // map[37] = 4  37  4次
        for (int i = 0; i != str2.length; i++) {
            map[str2[i]]++;
        }
        int left = 0;
        int right = 0;
        int all = str2.length;
        int minLen = Integer.MAX_VALUE;
        // [left, right)  [left, right-1]    [0,0)
        // R右扩   L ==0  R
        while (right != str1.length) {
            map[str1[right]]--;
            if (map[str1[right]] >= 0) {
                all--;
            }
            if (all == 0) { // 还完了
                while (map[str1[left]] < 0) {
                    map[str1[left++]]++;
                }
                // [L..R]
                minLen = Math.min(minLen, right - left + 1);
                all++;
                map[str1[left++]]++;
            }
            right++;
        }
        return minLen == Integer.MAX_VALUE ? 0 : minLen;
    }

    /**
     * 要加限制，s2等于0，all就等于0，直接就乱套了！
     */
    public static int minLength2(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() < s2.length() || s2.length() == 0) return Integer.MAX_VALUE;
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        int[] map = new int[128];
        for (int i = 0; i < str2.length; i++) {
            map[str2[i]]++;
        }
        int all = str2.length;
        int l = 0;
        int r = 0;
        int res = Integer.MAX_VALUE;
        while (r < str1.length) {
            if (all > 0) {//还需要还款
                if (map[str1[r]] > 0) {//有效还款
                    all--;
                }
                map[str1[r]]--;
                r++;
            }
            if (all == 0) {
                while (map[str1[l]] < 0) {
                    map[str1[l]]++;
                    l++;
                }
                res = Math.min(res, r - l);
                map[str1[l]]++;
                all++;
                l++;
            }
        }
        return res == Integer.MAX_VALUE ? 0 : res;
    }

    public static void testForString() {//参数为String
        StringUtil stringUtil = new StringUtil();
        int times = 1000;//测试次数
        long time1 = 0, time2 = 0;
        boolean isok = true;
        int maxSize = 10;//String长度在[0~maxSize]随机

//        int parameter1=0;//测试函数的参数
//        int maxParameter1=100;//参数1在[0,maxParameter1]随机

        String t1 = null;
        String t2 = null;

        int res1 = 0, res2 = 0;
        try {
            for (int i = 0; i < times; i++) {
                //            parameter1=arrayUtil.ran(maxParameter1);
                t2 = stringUtil.generateRandom_a_z_String(stringUtil.ran(maxSize / 2));
                t1 = stringUtil.generateRandom_a_z_String(stringUtil.ran(t2.length() + 1, maxSize));

                //        t1= stringUtil.generateRandom_all_String(stringUtil.ran(maxSize));
                //        t2= stringUtil.generateRandom_all_String(stringUtil.ran(maxSize));

                long l = System.currentTimeMillis();
                res1 = minLength(t1, t2);
                time1 += System.currentTimeMillis() - l;
                l = System.currentTimeMillis();
                res2 = minLength2(t1, t2);
                time2 += System.currentTimeMillis() - l;


                if (res1 != res2) {
                    isok = false;
                    break;
                }
            }
        } finally {
            System.out.println("t1:" + t1);
            System.out.println("t2:" + t2);
        }

//        System.out.println("parameter1:"+parameter1);//打印参数
        if (isok) System.out.println("m1 cost " + time1 + "ms");
        System.out.println(res1);//针对返回值的操作
        if (isok) System.out.println("m2 cost " + time2 + "ms");
        System.out.println(res2);//针对返回值的操作
        System.out.println(isok ? "success" : "fail");
    }

    public static void main(String[] args) {
//		String str1 = "adabbca";
//		String str2 = "acb";
//		System.out.println(minLength(str1, str2));
//		System.out.println(minLength2(str1, str2));
        testForString();

    }

}
