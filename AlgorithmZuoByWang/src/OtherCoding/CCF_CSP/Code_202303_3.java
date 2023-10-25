//package OtherCoding.CCF_CSP;
//
//import java.util.*;
//
//public class Code_202303_3 {
//
//    public static void solve(List<Integer> users,HashMap<Integer, HashMap<Integer, Integer>> attrsMap, String[] exps) {
//        for (String exp : exps) {
//            List<Integer> list = usersPassExp(attrsMap, exp);
//            if (list != null) {
//                int i = 0;
//                for (Integer integer : list) {
//                    System.out.printf("%s%d", i++ != 0 ? " " : "", integer);
//                }
//            }
//            System.out.println();
//        }
//    }
//
//    //返回所有满足exp表达式的用户编号,exp= &(xxxxx:yyyyyyyy)(xxxx:yyy)
//    public static List<Integer> usersPassExp(HashMap<Integer, HashMap<Integer, Integer>> userMap, String exp) {
//        List<Integer> res=new LinkedList<>();
//        for (Integer integer : userMap.keySet()) {
//            HashMap<Integer, Integer> attrMap = userMap.get(integer);
//            if (passExp(attrMap,exp)) res.add(integer);
//        }
//        Collections.sort(res);
//        return res;
//    }
//
//    public static boolean passExp(HashMap<Integer, Integer> attrMap,String exp){
//        return passExp(attrMap,exp.toCharArray(),0)[0]==1;
//    }
//
//    /**
//     *
//     * @param attrMap 用户,用来判断用户是否满足表达式
//     * @param exp 表达式
//     * @param i 从i往后算到一个合适的位置返回。合适的位置指的是遇到右括号或者到了结尾
//     * @return 返回算出的结果以及算到的位置。结果0代表false，1代表true
//     */
//    public static int[] passExp(HashMap<Integer, Integer> attrMap, char[] exp, int i){
//        String s=String.valueOf(exp);
//        boolean pass;
//        boolean and;
//        if (exp[i]=='|') {//或操作
//            pass=false;
//            and=false;
//        }else {// '&' '其它' 都是与操作
//            pass=true;
//            and=true;
//        }
//        if (exp[i]=='|'||exp[i]=='&') i++;
//        while(i<exp.length&&exp[i]!=')'){// | ( &(1:33)(22:444) )( |(1:22)(3:444) )
//            if (exp[i]=='('){
//                int[] info = passExp(attrMap, exp, i + 1);
//                pass=and?pass&&info[0]==1:pass||info[0]==1;
//                i=info[1]+1;
//            }else {
//                int r=i;
//                while(++r< exp.length&&exp[r]!=')');//找右括号
//                boolean passBase = passBaseExp(attrMap, s.substring(i, r));
//                pass=and?pass&&passBase:pass||passBase;
//                i=r;
//            }
//        }
//        return new int[]{pass?1:0,i};
//    }
//
////    //判断一个用户是否通过baseExp  "12:3333"
////    public static boolean passBaseExp(HashMap<Integer, List<Integer> dns> attrValMap, String baseExp){
////        int[] ints = TransBaseExp(baseExp);//"12:3333" -> [12,':',3333]
////        int attr=ints[0];//12
////        char op=(char)ints[1];//:
////        int attrVal=ints[2];//3333
////        if (!attrValMap.isEmpty()&&attrValMap.containsKey(attrVal)&&
////                (
////                        (op==':'&&attrValMap.get(attrVal)==dn)
////                      ||(op=='~'&&attrValMap.get(attr)!=dn)
////                )
////        ){
////            return true;
////        }
////        return false;
////    }
//
//
//    public static int[] TransBaseExp(String baseExp){//  "xxx:yyyy" "xx~y" => [xxx,:,yyyy] [xx,~,y]
//        char[] str = baseExp.toCharArray();//[x,x,~,y]
//        int i=-1;
//        while(str[++i]!='~'&&str[i]!=':');//str[i]='~'
//        char op = str[i];
//        int attr = Integer.parseInt(baseExp.substring(0,i));
//        int attrVal = Integer.parseInt(baseExp.substring(i+1));
//        return new int[]{attr,op,attrVal};
//    }
//
//    public static void main(String[] args) {
////        testGetBaseExps();
//        Scanner in = new Scanner(System.in);
//        while (in.hasNext()) {
//            int n = in.nextInt();//用户数目
//            HashMap<Integer, HashMap<Integer, Integer>> attrsMap = new HashMap<>();//<属性编号,<属性值，dn>>
//            List<Integer> users=new LinkedList<>();//dn
//            for (int i = 0; i < n; i++) {//每一个用户
//                int dn = in.nextInt();
//                users.add(dn);
//
//                int attributes = in.nextInt();
//                for (int j = 0; j < attributes; j++) {//填属性
//                    int attr=in.nextInt();
//                    int attrVal=in.nextInt();
//                    HashMap<Integer,Integer> attrValMap=new HashMap<>();
//                    attrValMap.put(attrVal,dn);
//                    attrsMap.put(attr,attrValMap);
//                }
//            }
//            int m = 0;//表示匹配表达式的数目
//            m = in.nextInt();
//            String[] exps = new String[m];
//            for (int i = 0; i < exps.length; i++) {
//                exps[i] = in.next();
//            }
////            test(userMap,exps);
//            solve(users,attrsMap, exps);
//        }
//    }
//
//    public static void test(HashMap<Integer, HashMap<Integer, Integer>> userMap, String[] exps) {
//        System.out.println("userMap" + ":");
//        for (Integer dn : userMap.keySet()) {
//            System.out.printf("dn=" + dn + " ");
//            HashMap<Integer, Integer> attributeMap = userMap.get(dn);
//            for (Integer attr : attributeMap.keySet()) {
//                System.out.printf("(attr-" + attr + ",val-" + attributeMap.get(attr) + ")");
//            }
//            System.out.println();
//        }
//        System.out.println("================");
//        for (int i = 0; i < exps.length; i++) {
//            System.out.printf("exp%d:%s\n", i + 1, exps[i]);
//        }
//    }
//
//}
