package BasicLearning.Class07_RecursionToDP;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**打印一个字符串的全部子序列
 * 子序列：前后的相对次序不变。例如123456789的子序列可以是1245/456/479但是不能是432
 */
public class Code03_PrintAllSubsequences {

    private static List<String> subs(String str){
        if (str==null) return null;
        char[] arr=str.toCharArray();
        List<String> list=new ArrayList<>();
        process(arr,0,list,"");
        return list;
    }

    /**
     * 在index位置分两种情况，一种是要了index位置，一种是不要index位置。
     * @param chars:这是一个固定参数
     * @param index:从index位置开始把后面的决定做完。0~index-1位置已经做好了决定
     * @param res:不需要返回值，我们把决定的结果放到res中
     * @param path:记录之前做决定的状态
     */
    private static void process(char[] chars, int index, List<String> res, String path) {
        if (index==chars.length)res.add(path);//base case,把做好决策的加入到集合中
        else {
            process(chars,index+1,res,path+ chars[index]);
            process(chars,index+1,res,path);
        }
    }

    private static LinkedList<String> subsNoRepeat(String str){
        if (str==null) return null;
        char[] arr=str.toCharArray();
        HashSet<String> set=new HashSet<>();//去重
        process2(arr,0,set,"");
        return new LinkedList<>(set);
    }

    private static void process2(char[] chars, int index, HashSet<String>res, String path) {
        if (index==chars.length)res.add(path);//base case,把做好决策的加入到集合中
        else {
            process2(chars,index+1,res,path+ chars[index]);
            process2(chars,index+1,res,path);
        }
    }

    public static void main(String[] args) {
        String  str="abcabcbc";
        for (String s:subsNoRepeat(str)){
            System.out.println(s);
        }
        System.out.println("===================");
        for (String s:subs(str)){
            System.out.println(s);
        }
        System.out.println("===================");

    }

}
