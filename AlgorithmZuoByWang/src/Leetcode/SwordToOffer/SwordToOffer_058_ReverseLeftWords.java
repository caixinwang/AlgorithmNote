package Leetcode.SwordToOffer;

public class SwordToOffer_058_ReverseLeftWords {
    //和189题类似，只不过这题的reverse是反着的
    public String reverseLeftWords(String s, int n) {//利用reverse函数
        char[] str = s.toCharArray();
        reverse(str, 0, n - 1);
        reverse(str, n, str.length - 1);
        reverse(str, 0, str.length - 1);
        return String.valueOf(str);
    }

    public void reverse(char[] str, int l, int r) {
        for (char t; l < r; t = str[l], str[l] = str[r], str[r] = t, l++, r--) ;
    }

    public String reverseLeftWords2(String s, int n) {//下标循环怼
        char[] str = s.toCharArray();
        int len = str.length;
        int count=0;//用来控制下标循环怼的轮数，到了就退出。
        for (int i = 0; i < len&&count<len; i++) {
            int cur = i, next;
            char pre, t=str[cur];
            do {
                next = next(cur, len, n);
                pre=t;
                t = str[next];
                str[next] = pre;
                cur = next;
                count++;
            } while (cur != i);
        }
        return String.valueOf(str);
    }

    public int next(int index, int len, int n) {
        return (index - n + len) % len;
    }

}
