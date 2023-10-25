package Leetcode.AllQuestions;

/**
 * 这题不是26进制，这叫做26伪进制。因为如果是k进制一定是0~k-1的，但是这题是1~26。
 * 这种题你就从低位往高位依次分配1，看看最高到多少，然后再逆着更新回来
 */
public class Leetcode_0171_ExcelSheetColumnNumber {

    // 这道题反过来也要会写
    public static int titleToNumber(String s) {
        char[] str = s.toCharArray();
        int ans = 0;
        for (int i = 0; i < str.length; i++) {
            ans = 26 * (ans) + str[i] - 'A' + 1;//[1,26]，这题是从1开始的，没有0，后面要+1
        }
        return ans;
    }

    //f(n)=26*f(n-1)+str[i]-'A'+1
    //f(n-1)=(f(n)-1)/26
    public static String numberToTitle(int num) {//上面版本的逆过程
        StringBuilder title = new StringBuilder();
        while (num > 0) {
            int remainder = (num - 1) % 26;
            char letter = (char) (remainder + 'A');
            title.insert(0, letter);
            num = (num - 1) / 26;
        }
        return title.toString();
    }


    public static void main(String[] args) {
        System.out.println(numberToTitle(2));
        System.out.println(numberToTitle(27));
        System.out.println(numberToTitle(26));
    }
}
