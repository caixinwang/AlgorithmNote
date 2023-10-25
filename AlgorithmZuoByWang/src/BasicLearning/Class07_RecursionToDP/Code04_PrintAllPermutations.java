package BasicLearning.Class07_RecursionToDP;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Code04_PrintAllPermutations {// 打印一个字符串的全部排列

    /**
     * @param str
     * @return
     */
    private static LinkedList<String> permutation1(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        char[] s = str.toCharArray();
        ArrayList<Character> rest = new ArrayList<>();
        for (char c : s) {
            rest.add(c);
        }
        String path = "";
        LinkedList<String> res = new LinkedList<>();
        process1(rest, path, res);
        return res;
    }

    /**
     * 不需要返回值，这里相当于返回值加到res里面了
     *
     * @param rest:从rest中选一个放到path中。当rest空了就说明path就是其中一个答案
     * @param path:记录之前选过的路径
     * @param res:当rest为空的时候把path放入到res中
     */
    private static void process1(ArrayList<Character> rest, String path, LinkedList<String> res) {
        if (rest.isEmpty()) {//base case
            res.add(path);
            return;
        }
        for (int i = 0; i < rest.size(); i++) {//相当于yes和on做选择。只不过这里不止两种情况，这个要把整个list都循环一遍
            char c = rest.get(i);
            rest.remove(i);
            process1(rest, path + c, res);//这里我们不写成path=path+c然后里面是path。如果这样写我们也需要恢复现场
            rest.add(i, c);//上面破坏了rest，这里从递归中返回需要恢复现场
        }
    }


    private static LinkedList<String> permutation2(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        char[] s = str.toCharArray();
        LinkedList<String> res = new LinkedList<>();
        process2(s, 0, res);
        return res;
    }

    /**
     *
     * @param s:直接在s上面做操作
     * @param index:index~len-1位置选一个和index做交换
     * @param res：index走到了最后一个坐标len的时候就是一个结果
     */
    private static void process2(char[] s, int index, LinkedList<String> res) {
        if (index == s.length) {
            res.add(String.valueOf(s));
        }else {
            for (int i=index;i<s.length;i++){//从这些位置中选一个和index做交换
                swap(s,index,i);
                process2(s,index+1,res);
                swap(s,index,i);
            }
        }
    }

    private static void swap(char[] s, int a, int b) {
        char temp=s[a];
        s[a]=s[b];
        s[b]=temp;
    }


    /**
     * 和上面的不一样的是这个版本给出的是不重复的排列
     * @param str
     * @return
     */
    private static LinkedList<String> permutation3(String str){
        if (str == null || str.length() == 0) {
            return null;
        }
        char[] s = str.toCharArray();
        LinkedList<String> res = new LinkedList<>();
        process3(s, 0, res);
        return res;
    }

    private static void process3(char[] s, int index, LinkedList<String> res) {
        if (index == s.length) {
            res.add(String.valueOf(s));
        }else {
            boolean[] visited=new boolean[26];//26个字母，如果量大可以用集合来代替
            for (int i=index;i<s.length;i++){
                if (!visited[s[i]-'a']){
                    visited[s[i]-'a']=true;
                    swap(s,index,i);
                    process3(s,index+1,res);
                    swap(s,index,i);
                }
            }
        }
    }


    public static void main(String[] args) {
        String s = "acc";
        List<String> ans1 = permutation1(s);
        for (String str : ans1) {
            System.out.println(str);
        }
        System.out.println("=======");
        List<String> ans2 = permutation2(s);
        for (String str : ans2) {
            System.out.println(str);
        }
        System.out.println("=======");
        List<String> ans3 = permutation3(s);
        for (String str : ans3) {
            System.out.println(str);
        }

    }

}
