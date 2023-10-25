package Leetcode.SwordToOffer;

public class SwordToOffer_020_IsNumber {
    public boolean isNumber(String s) {
        s = s.trim();//首尾空格
        boolean numFlag = false, dotFlag = false, eFlag = false;
        for (int i = 0; i < s.length(); i++) {
            if (Character.isDigit(s.charAt(i))) numFlag = true;
            else if (s.charAt(i) == '.' && !dotFlag && !eFlag) dotFlag = true;//点号不能重复出现，并且不能在必须在e前面
            else if ((s.charAt(i) == 'e' || s.charAt(i) == 'E') && !eFlag && numFlag) {//e不重复出现且前面需要有数字
                numFlag = false;//e出现了之后需要出现过数字
                eFlag = true;
            } else if ((s.charAt(i) == '+' || s.charAt(i) == '-')
                    && (i == 0 || s.charAt(i - 1) == 'e' || s.charAt(i - 1) == 'E')) {
            } else return false;
        }
        return numFlag;
    }
}
