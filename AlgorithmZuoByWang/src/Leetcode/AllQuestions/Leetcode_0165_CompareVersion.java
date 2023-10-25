package Leetcode.AllQuestions;

public class Leetcode_0165_CompareVersion {
    public static int compareVersion(String version1, String version2) {
        String[] strs1=version1.split("\\.");//要转义，.号在正则表达式中有特殊含义,java中\也是特殊的，所以\也得转义
        String[] strs2=version2.split("\\.");
//         for(int i=0;i<strs1.length;i++){//使用api，不需要操心前导零
//             strs1[i]=strs1[i].replaceAll("^0+(?!$)","");
//         }
//         for(int i=0;i<strs2.length;i++){
//             strs2[i]=strs2[i].replaceAll("^0+(?!$)","");
//         }
        for(int i=0,j=0;i<strs1.length||j<strs2.length;){
            int v1=i<strs1.length?Integer.parseInt(strs1[i++]):0;
            int v2=j<strs2.length?Integer.parseInt(strs2[j++]):0;
            if(v1<v2) return -1;
            else if(v1>v2) return 1;
        }
        return 0;
    }

    public static void main(String[] args) {
        compareVersion("1.000","1.000");
    }
}
