package BasicLearning.Class05_DaBiaoFaHeJuZhenChuLi;

public class Code02_EatGrass {
    // n份青草放在一堆
    // 先手后手都绝顶聪明
    // string "先手" "后手"
    public static String winner1(int n) {
        if (n==0){
            return "后手";
        }
        int eat=1;
        while(true){
            if (winner1(n-eat).equals("后手")){
                return "先手";
            }
            if (eat>n/4){
                break;//退出条件放在这里是为了防止越界
            }
            eat*=4;
        }
        return "后手";
    }

    public static String winner2(int n) {
        if (n==0){
            return "后手";
        }
        return ((n-1)%5==2||(n-1)%5==4)?"后手":"先手";
    }

    public static void main(String[] args) {
        for (int i = 0; i <= 50; i++) {
            System.out.print(i + " : " + winner1(i)+" "+winner2(i)+"\n");
        }
    }
}
