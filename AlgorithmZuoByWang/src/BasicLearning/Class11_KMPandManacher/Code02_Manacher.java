package BasicLearning.Class11_KMPandManacher;

public class Code02_Manacher {

    /** 回文半径的大小.12321回文半径为2,4-2=2。
     * pArr[i] = i<=R ? Math.min(pArr[2 * C - i], R - i) : 0;这一句代表了4个分支Parr应该赋值的Parr
     * 1. 当i在R的外面的时候，parr[i]就初始化为0。因为自己到自己的距离为0.
     * 2. 当i在R里面，就可以利用parr数组进行加速，也就是直接把parr[i]设置为parr[i']的值，但是如果i+parr[i']大于R那么就设置为R-i。
     * 3. 简而言之：右边界和i的距离只有R-i,就算i‘ 能帮你也最多帮你到R-i，不能越出去,所以r-i作为下界，用min函数
     * @param s:返回s的最长回文子串的长度
     * @return :返回最长回文子串的长度，例如12321返回5
     */
    public static int manacher(String s) {//直接由right2改过来
        if (s == null || s.length() == 0) return 0;
        char[] str = manacherString(s);//垫上#
        int[] parr=new int[str.length];//每个位置出发匹配的最大回文半径
        int c=-1;//c是目前到达的最右的回文串，这个回文串是从c位置向左右两边扩展出来的
        int r=-1;//r是目前到达的最右的回文串的右边界，包含, ..r]
        int max=0;
        for (int i = 0; i < str.length; i++) {
            int p=i>r?0:Math.min(r-i,parr[2*c-i]);//p代表此时i位置作为中心的回文半径，只有i<=r之前的记录才能帮到你，并且最多帮你到r位置
            while(i-p>0&&i+p<str.length-1&&str[i-p-1]==str[i+p+1]){//越界了或者不等了就出while
                p++;//出来的时候是最后一个合格的位置
            }
            parr[i]=p;//更新辅助结构
            if (i+p>r){//判断一下我们的帮助信息需不需要更新,i出发的回文串比r还右就更新
                r=i+p;
                c=i;
            }
            //越界或者str[r+1]!=str[l-1]
            max = Math.max(max,p);
        }
        return max;
    }

    /**
     * 只返回parr--长度为原始字符串的两倍+1。parr[i+1]=i+1则代表原始字符串s[0...i]的前缀串是回文串。
     * 也就是说只要parr[i]=i,如果i>0,那么原始串中的0~i-1就是回文串
     */
    public static int[] manacherParr(String s) {
        if (s == null || s.length() == 0) return null;
        char[] str = manacherString(s);
        int[] parr=new int[str.length];
        int c=-1,r=-1,p=0;//[c-p,r] r=c+p
        for (int i = 0; i < str.length; i++) {
            p=i<=r?Math.min(r-i,parr[2*c-i]):0;
            while(i-p-1>=0&&i+p+1<str.length&&str[i-p-1]==str[i+p+1])  p++;
            parr[i]=p;
            if (i+p>r){
                c=i;
                r=i+p;
            }
        }
        return parr;
    }

    /**
     * 返回具体的最长的那个回文子串是啥
     */
    public static String longestPalindrome(String s) {
        if (s==null||s.length()==0) return s;
        char[] str = manacherString(s);
        int[] parr=new int[str.length];
        int c=-1;
        int r=-1;
        int maxIndex=0;//从哪个位置扩出来的最长
        int max=0;//在这个位置的回文半径是多少
        for (int i = 0; i < str.length; i++) {
            int p=i<=r?Math.min(parr[2*c-i],r-i):0;//当前位置阔出去的回文半径
            while(i+p+1<str.length&&i-p-1>=0&&str[i+p+1]==str[i-p-1]) p++;//最后一个合格的位置
            parr[i]=p;
            if (i+p>r){
                r=i+p;
                c=i;
            }
            if (p>max){
                max=p;
                maxIndex=i;
            }
        }
        StringBuilder res= new StringBuilder();
        for (int i=maxIndex-max;i<=maxIndex+max;i++){
            if ((i&1)==1) res.append(str[i]);//奇数位置的才是源字符串中的字符
        }
        return res.toString();
    }

    public static char[] manacherString(String str) {
        char[] charArr = str.toCharArray();
        char[] res = new char[str.length() * 2 + 1];
        for (int i = 0; i != res.length; i++) {//#永远都是在偶数位置
            res[i] = (i & 1) == 0 ? '#' : charArr[i>>1];
        }
        return res;
    }


    // for test
    public static int right(String s) {
        if (s == null || s.length() == 0) return 0;
        char[] str = manacherString(s);
        int max = 0;
        for (int i = 0; i < str.length; i++) {
            int L = i - 1;
            int R = i + 1;
            while (L >= 0 && R < str.length && str[L] == str[R]) {
                L--;
                R++;
            }
            max = Math.max(max, R - L - 1);
        }
        return max / 2;
    }

    // for test
    public static int right2(String s) {
        if (s == null || s.length() == 0) return 0;
        char[] str = manacherString(s);
        int max = 0;
        for (int i = 0; i < str.length; i++) {
            int p=0;
            while(i-p>0&&i+p<str.length-1&&str[i-p-1]==str[i+p+1]){//越界了或者不等了就出while
                p++;
            }
            max = Math.max(max, p);
        }
        return max;
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
        int testTimes = 50000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            String str = getRandomString(possibilities, strSize);
            if (manacher(str) != right2(str)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish");
    }
}
