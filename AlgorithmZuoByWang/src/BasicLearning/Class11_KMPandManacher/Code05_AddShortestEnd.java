package BasicLearning.Class11_KMPandManacher;

public class Code05_AddShortestEnd {//在任意一个字符串的末尾加上一个最短的字符串使得这个字符串是回文序列

    private static String m(String s) {
        if (s == null || s.length() == 0) return null;
        char[] str = manacherArr(s);
        int[] parr=new int[str.length];
        int c=-1;
        int r=-1;
        int i=0;//出循环有用
        for (; i < str.length; i++) {
            if (i<=r)parr[i]=Math.min(r-i,parr[2*c-i]);//在R的内部才有有用的信息给我利用,在外部就是0，自己到自己的距离0
            while(i-parr[i]-1>=0&&i+parr[i]+1<= str.length-1){
                if (str[i-parr[i]-1]==str[i+parr[i]+1]){
                    parr[i]++;
                }else {
                    break;
                }
            }
            if (i+parr[i]==str.length-1) break;
            if (i+parr[i]>r){
                r=i+parr[i];
                c=i;
            }
        }
//        String res="";
//        int end=i-parr[i]-1;//最大回文的最左边的左边一个字符
//        for (int index=end;index>=0;index-=2){
//            res+=str[index];
//        }
//        return res;
        char[] res=new char[s.length()-parr[i]];//两段代码等价
        for (int j = 0; j < res.length; j++) {
            res[j]=str[2*j+1];
        }
        return String.valueOf(res);
    }

    private  static char[] manacherArr(String s) {
        char[] str = s.toCharArray();
        char[] res = new char[s.length() * 2 + 1];
        for (int i = 0, j = 0; i < res.length; i++) {
            res[i]=(i&1)==0?'#':str[j++];
        }
        return res;
    }

    public static void main(String[] args) {
        String str1 = "khabcdljk123321";
        System.out.println(m(str1));
    }
}
