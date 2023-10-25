package TestUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class StringUtil {


    /**
     * @return 返回一个规模为listSize的，装着随机String的List,且里面的String不重复
     */
    public ArrayList<String> generateRandomStringListNoRepeat(int listSize, int stringSize) {
        if (listSize==0) return null;
        ArrayList<String> res = new ArrayList<>();
        HashSet<String> set = new HashSet<>();
        int i = 0;
        if (Math.pow(26, stringSize) < listSize) return null;//尽量让stringSize大一点,避免list把所有可能的string耗尽
        while (i < listSize) {//每次进while，生成一个长度随机的String
            int realLen = ran(stringSize);//该随机字符串的长度
            String str = generateRandom_a_z_String(realLen);//字符串的字符在a~z随机
//            String str = generateRandom_all_String(realLen);//字符串的字符在a~z随机
            if (!set.contains(str)) {
                set.add(str);
                res.add(str);
                i++;//只有生成的字符串不一样的时候才i才++,才把str添加到res
            }
        }
        return res;
    }

    //生成不重复的字符串数组
    public String[] generateRandomStringArrNoRepeat(int arrSize, int stringSize) {
        ArrayList<String> strings = generateRandomStringListNoRepeat(arrSize, stringSize);
        if (strings==null) return null;
        String[] res = new String[arrSize];
        int i = 0;
        for (String string : strings) {
            res[i++] = string;
        }
        return res;
    }


    /**
     * @return 返回一个规模为listSize的，装着随机String的List,且里面的String可能会重复
     */
    public ArrayList<String> generateRandomStringList(int listSize, int stringSize) {
        ArrayList<String> res = new ArrayList<>();
        int i = 0;
        while (i < listSize) {//每次进while，生成一个长度随机的String
            int realLen = ran(stringSize);//该随机字符串的长度
            String str = generateRandom_a_z_String(realLen);//字符串的字符在a~z随机
//            String str = generateRandom_all_String(realLen);//字符串的字符在a~z随机
            res.add(str);
            i++;//只有生成的字符串不一样的时候才i才++,才把str添加到res
        }
        return res;
    }

    //字符串数组，字符串可能会重复
    public String[] generateRandomStringArr(int arrSize, int stringSize) {
        String[] res = new String[arrSize];
        ArrayList<String> strings = generateRandomStringList(arrSize, stringSize);
        int i = 0;
        for (String string : strings) {
            res[i++] = string;
        }
        return res;
    }


    /**
     * @param size 生成大小为size的随机字符串
     * @return 大小为size的随机字符串, 字符在a~z随机，都是小写字母
     */
    public String generateRandom_a_z_String(int size) {
        StringBuilder builder = new StringBuilder();
        for (int j = 0; j < size; j++) {//用随机字符拼出一个随机字符串
            int ran = (int) ran('z' - 'a');//[0,25]随机，共26个字母
            char c = (char) (ran + 'a');//[a,z]
//                int ran=(int) (Math.random()*(127+1));//[0,127]随机，共128个字符
//                char c=(char) ran;
            builder.append(c);
        }
        return builder.toString();
    }

    /**
     * @param size 生成大小为size的随机字符串
     * @return 大小为size的随机字符串, 字符在0~127的ASCII中随机
     */
    public String generateRandom_all_String(int size) {
        StringBuilder builder = new StringBuilder();
        for (int j = 0; j < size; j++) {//用随机字符拼出一个随机字符串
            int ran = ran(127);//[0,127]随机，共128个字符
            char c = (char) ran;
            builder.append(c);
        }
        return builder.toString();
    }

    public void printStrList(List<String> strList) {
        if (strList == null || strList.size() == 0) return;
        for (String s : strList) {
            System.out.print(s + " ");
        }
        System.out.println();
    }

    public void printStrArr(String[] strArr) {
        if (strArr == null || strArr.length == 0) return;
        int i = 0;
        System.out.printf("{");
        for (String s : strArr) {
            if (i != 0) System.out.printf(",");
            System.out.print(s + " ");
            i++;
            if (i != 0 && i % 10 == 0) System.out.println();
        }
        System.out.println("}");
    }

    public int ran(int max) {//[0,max]
        return (int) (Math.random() * (max + 1));
    }

    public int ran(int l, int r) {//[l,r]
        return l + ran(r - l);
    }


}
