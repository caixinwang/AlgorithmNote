package BasicLearning.Class11_KMPandManacher;

public class Code04_IsRotation {//判断a和b是否互为旋转词
    public static boolean isRotation(String a, String b) {
        if (a==null||b==null||a.length()!=b.length()) return false;
        return getIndexOf(a+a,b)!=-1;
    }

    // KMP Algorithm
    public static int getIndexOf(String s, String m) {
        if (s==null||m==null||m.length()<1||m.length()>s.length()) return -1;
        char[] str = s.toCharArray();
        char[] match = m.toCharArray();
        int i=0;
        int j=0;
        int[] next = getNextArray(match);
        while(i<s.length()&&j<m.length()){
            if (str[i]==match[j]){
                i++;
                j++;
            }else if (next[j]!=-1){
                j=next[j];
            }else {
                i++;
            }
        }
        return j==match.length?i-j:-1;
    }

    public static int[] getNextArray(char[] ms) {
        if (ms.length==1) return new int[]{-1};
        int[] next= new int[ms.length];
        next[0]=-1;
        next[1]=0;
        int cur=next[1];
        int i=2;//从2开始
        while (i < ms.length) {
            if (ms[i-1]==ms[cur]){
                next[i++]=++cur;
            }else if (cur>0){
                cur=next[cur];
            }else {
                next[i++]=0;
            }
        }
        return next;
    }

    public static void main(String[] args) {
        String str1 = "yunzuocheng";
        String str2 = "zuochengyun";
        System.out.println(isRotation(str1, str2));

    }
}
