package BasicLearning.Class07_RecursionToDP;

import java.util.HashMap;

public class Code11_StickersToSpellWord {

    private static int minStickers1(String[] sticker, String aim) {
        if (sticker == null || sticker.length == 0 || aim == null) {
            return 0;
        }
        return process1(sticker, aim);
    }

    private static int process1(String[] sticker, String rest) {
        if (rest.length() == 0) {
            return 0;
        }
        int min = Integer.MAX_VALUE;
        for (String str : sticker) {
            String s = minus(rest, str);
            if (s.length() != rest.length()) {//贴纸有用才递归
                int next = process1(sticker, s);
                min = next == Integer.MAX_VALUE ? min : Math.min(min, next + 1);//和上面这段可以互换
            }
        }
        return min;
    }

    /**
     * 观察到这里的减法是先转化成数组才进行的。所以自然可以想到，如果我们一开始就把贴纸和目标转化成数组的话就可以省下转化的时间。
     *
     * @param a
     * @param b
     * @return
     */
    private static String minus(String a, String b) {//a-b
        String res = "";
        char[] charsA = a.toCharArray();
        char[] charsB = b.toCharArray();
        int[] alphabet = new int[26];
        for (char c : charsA) {
            alphabet[c - 'a']++;
        }
        for (char c : charsB) {
            alphabet[c - 'a']--;
        }
        for (int i = 0; i < alphabet.length; i++) {
            if (alphabet[i] > 0) {
                while (alphabet[i]-- != 0) {
                    res += (char) (i + 'a');
                }
            }
        }
        return res;
    }

    /**
     * 这里不把aim也转化成char[]是因为，如果转化了就不能通过每次消除第一个字符来做贪心算法了
     * @param stickers
     * @param aim
     * @return
     */
    private static int minStickers2(String[] stickers, String aim) {
        if (stickers == null || stickers.length == 0 || aim == null) {
            return 0;
        }
        int[][] charsSticker = new int[stickers.length][26];
        for (int i = 0; i < stickers.length; i++) {//对每一个字符串
            for (char c : stickers[i].toCharArray()) {//转化成字符类型的数组，然后对此数组进行遍历
                charsSticker[i][c - 'a']++;//第i的字符串对应的特定字符的位置+1
            }
        }
        return process2(charsSticker, aim);
    }

    private static int process2(int[][] stickers, String aim) {
        if (aim.length() == 0) {
            return 0;
        }
        char[] charsAim = aim.toCharArray();
        int min = Integer.MAX_VALUE;
        for (int[] charsSticker : stickers) {//每张贴纸都试
            if (charsSticker[charsAim[0] - 'a'] > 0) {//目标的第一个字符先变为0.这是一步贪心。只有能消去目标的第一个字符的贴纸我才选
                int next = process2(stickers, minus2(charsSticker, aim));//改变目标，继续删除它的第一个字符
                min = next == Integer.MAX_VALUE ? min : Math.min(min, next + 1);
            }
        }
        return min;
    }

    private static String minus2(int[] sticker, String rest) {
        char[] charsRest = rest.toCharArray();
        int[] restCount = new int[26];
        for (char c : charsRest) {
            restCount[c - 'a']++;
        }
        for (int i = 0; i < sticker.length; i++) {
            if (sticker[i] > 0) {
                restCount[i] -= sticker[i];
            }
        }
        String res = "";
        for (int i = 0; i < restCount.length; i++) {//对于每一个字母
            for (int j = 0; j < restCount[i]; j++) {//把字母加restCount[i]次到res上
                res += (char) (i + 'a');
            }
        }
        return res;
    }

    private static int minStickers3(String[] stickers, String aim) {
        if (stickers == null || stickers.length == 0 || aim == null) {
            return 0;
        }
        int[][] charsSticker = new int[stickers.length][26];
        for (int i = 0; i < stickers.length; i++) {//对每一个字符串
            for (char c : stickers[i].toCharArray()) {//转化成字符类型的数组，然后对此数组进行遍历
                charsSticker[i][c - 'a']++;//第i的字符串对应的特定字符的位置+1
            }
        }
        HashMap<String, Integer> dp = new HashMap<>();
        dp.put("",0);
        return process3(charsSticker, aim, dp);
    }

    private static int process3(int[][] stickers, String aim, HashMap<String, Integer> dp) {
        if (dp.containsKey(aim)){
            return dp.get(aim);
        }
        char[] charsAim = aim.toCharArray();
        int min = Integer.MAX_VALUE;
        for (int[] charsSticker : stickers) {//每张贴纸都试
            if (charsSticker[charsAim[0] - 'a'] > 0) {//目标的第一个字符先变为0.这是一步贪心。只有能消去目标的第一个字符的贴纸我才选
                int next = process2(stickers, minus2(charsSticker, aim));//改变目标，继续删除它的第一个字符
                min = next == Integer.MAX_VALUE ? min : Math.min(min, next + 1);
            }
        }
        dp.put(aim,min);
        return min;
    }


    public static void main(String[] args) {
        String[] arr = {"abc", "aaa", "bbbbbccccc"};
        String aim = "abcbbbbccccc";
        System.out.println(minStickers1(arr, aim));
        System.out.println(minStickers2(arr, aim));
        System.out.println(minStickers3(arr, aim));
    }
}
