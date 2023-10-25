package BasicLearning.Class05_DaBiaoFaHeJuZhenChuLi;

public class Code01_AppleMinBags {
    /**
     * 暴力解法。先从最极限的开始，要最少的袋子总数，肯定是8号袋要偏多。从最极限的情况开始。也就是全部都要八号袋
     * N/8得到八号袋的数量，然后慢慢减少到0，在这过程中如果还是不能满足相加为N。那么就返回-1。否则在这过程中最先
     * 满足的情况就是最少的情况。
     *
     * @param N
     * @return
     */
    public static int minBags(int N) {
        if (N < 0) {
            return -1;
        }
        int bag8 = N / 8;
        int bag6 = 0;
        int rest = N - bag8 * 8;//剩下的苹果数

        while (bag8 >= 0 && rest < 24) {//24是6和8的最小公倍数
            if (rest % 6 == 0) {
                bag6 = rest / 6;
                return bag6 + bag8;
            }
            bag8--;
            rest = N - bag8 * 8;
        }
        return -1;
    }

    public static int minBagAwesome(int apple) {
        if ((apple & 1) != 0) { // 如果是奇数，返回-1
            return -1;
        }
        if (apple < 18) {
            return apple == 0 ? 0 : (apple == 6 || apple == 8) ? 1
                    : (apple == 12 || apple == 14 || apple == 16) ? 2 : -1;
        }
        return (apple - 18) / 8 + 3;
    }

    public static void main(String[] args) {
        for (int apple = 1; apple < 100; apple++) {
            System.out.println(apple + " : " + minBags(apple));
        }

    }
}
