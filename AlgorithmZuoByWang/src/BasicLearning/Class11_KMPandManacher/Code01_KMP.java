package BasicLearning.Class11_KMPandManacher;

public class Code01_KMP {
    private static int kmp(String str, String match) {
        if (str==null||match==null||match.length()<1||str.length()<match.length()) return -1;
        char[] s1 = str.toCharArray();
        char[] s2 = match.toCharArray();
        int[] next=getNext2(match);
        int p1=0;
        int p2=0;
        while(p1!=str.length()&&p2!=match.length()){//不越界
            if (s1[p1]==s2[p2]){
                p1++;
                p2++;
            }else if (next[p2]!=-1){//p2还可以回退  p2!=0也行，因为0位置的next的值就是-1
                p2=next[p2];
            }else{//p2==0,回退不了了，说明p1现在这个位置不对
                p1++;
            }
        }
        return p2==match.length()?p1-p2:-1;
    }

    private static int[] getNext(String match) {//next[i]代表0~i-1子串的最长前缀后缀匹配长度,也代表第一个不匹配的下标
        if (match.length() == 1) return new int[]{-1};
        char[] chars = match.toCharArray();
        int[] next = new int[match.length()];
        next[0] = -1;
        next[1] = 0;
        int i=2;
        int p=next[1];//当前要从2位置开始填，所以p要等于next[2-1]
        while(i!=match.length()){
            if (chars[i-1]==chars[p]){//i-1位置和p位置相等,
                next[i++]=++p;//长度是i-1位置长度+1，p即代表要比较的位置，也代表前缀子串的长度
            }else if (p==0){//p前面无路可跳
                next[i++]=0;
            }else {//p前面还有路可以跳
                p=next[p];
            }
        }
        return next;
    }

    private static int[] getNext2(String match) {//next[i]代表0~i-1子串的最长前缀后缀匹配长度,也代表第一个不匹配的下标
        if (match.length()==1) return new int[]{-1};
        char[] str = match.toCharArray();
        int[] res=new int[str.length];
        res[0]=-1;
        for(int i=1;i<res.length;i++){
            int p=res[i-1];
            while(p!=-1&&str[i-1]!=str[p]) p=res[p];//注意是[i-1]和p位置比较，因为p的含义是0~i-2的子串的前缀后缀的最长匹配长度
            res[i]=p+1;
            //p==-1或者str[i]==str[p] 才可以确定res[i]
//            if (p==-1) res[i]=0;
//            else res[i]=p+1;
        }
        return res;
    }

    private static int baoli(String str,String match){
        if (str==null||match==null||match.length()<1||str.length()<match.length()) return -1;
        char[] s1 = str.toCharArray();
        char[] s2 = match.toCharArray();
        int p1=0;
        int p2=0;
        for (int i = 0; i < str.length(); i++) {
            p1=i;
            p2=0;
            while(p1!=str.length()&&p2!=match.length()){
                if (s1[p1]==s2[p2]){
                    p1++;
                    p2++;
                }else
                    break;
            }
            if (p2==match.length())return p1-p2;
        }
        return -1;
    }

    // for test
    public static String getRandomString(int possibilities, int size) {
        char[] ans = new char[(int) (Math.random() * size) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (char) ((int) (Math.random() * possibilities) + 'a');
        }
        return String.valueOf(ans);
    }

    public static void main(String[] args) {
        int possibilities = 5;
        int strSize = 20;
        int matchSize = 5;
        int testTimes = 5000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            String str = getRandomString(possibilities, strSize);
            String match = getRandomString(possibilities, matchSize);
            if (kmp(str, match) != baoli(str,match)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish");
    }


}
