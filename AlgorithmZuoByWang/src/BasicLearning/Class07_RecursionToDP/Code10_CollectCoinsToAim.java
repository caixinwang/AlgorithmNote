package BasicLearning.Class07_RecursionToDP;

public class Code10_CollectCoinsToAim {

    /**
     * 每一层按照特点一种硬币（index）使用了几个来进行展开
     * @param amount:硬币的面额
     * @param aim:目标
     * @return :返回的是最少的硬币数
     */
    private static int minCoins(int[] amount, int aim) {
        if (amount == null || amount.length == 0 || aim < 0) {
            return 0;
        }
        return process1(amount, 0, aim);
    }

    /** 为什么要保证next!=MAX手动剔除不合法分支，因为在Java程序中MAX+i会变成负数
     * @param amount:固定参数面额
     * @param index:index之前的硬币已经决定使用多少
     * @param rest:需要用index及其往后的硬币凑成rest
     * @return :返回凑成rest需要的最少硬币
     */
    private static int process1(int[] amount, int index, int rest) {
        if (index == amount.length) {//需要用返回值来区分一个调用是否合法
            return rest == 0 ? 0 : Integer.MAX_VALUE;
        }
        int min = Integer.MAX_VALUE;
        for (int i = 0; amount[index] * i <= rest; i++) {//i代表amount[index]硬币选了几个
            int next = process1(amount, index + 1, rest - amount[index] * i);
            min=next != Integer.MAX_VALUE ?Math.min(min,i+next):min;
        }
        return min;
    }

    /**为什么要保证next!=MAX,因为在Java程序中MAX+i会变成负数，这是底层决定的。
     * 没有优化过的动态规划，里面有for循环
     * @param amount
     * @param aim
     * @return
     */
    private static int minCoinsDp1(int[] amount, int aim) {
        if (amount == null || amount.length == 0 || aim < 0) {
            return 0;
        }
        int N = amount.length;
        int[][] dp = new int[N + 1][aim + 1];//[index][rest]
        for (int rest = 1; rest <= aim; rest++) {//初始化第N行
            dp[N][rest] = Integer.MAX_VALUE;
        }
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                int min = Integer.MAX_VALUE;
                for (int i = 0; amount[index] * i <= rest; i++) {//i代表amount[index]硬币选了几个
                    int next = dp[index + 1][rest - amount[index] * i];
                    min=next != Integer.MAX_VALUE ?Math.min(min,i+next):min;
                }
                dp[index][rest] = min;
            }
        }
        return dp[0][aim];
    }

    /**dp[index][rest - amount[index]] != Integer.MAX_VALUE需要保证，因为MAX+1是没有意义的
     * 优化过的动态规划，去掉了for循环
     * @param amount
     * @param aim
     * @return
     */
    private static int minCoinsDp2(int[] amount, int aim) {
        if (amount == null || amount.length == 0 || aim < 0) {
            return 0;
        }
        int N = amount.length;
        int[][] dp = new int[N + 1][aim + 1];//[index][rest]
        for (int rest = 1; rest <= aim; rest++) {//初始化第N行
            dp[N][rest] = Integer.MAX_VALUE;
        }
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                dp[index][rest] = dp[index + 1][rest];//一张都不选
                if (rest - amount[index] >= 0 && dp[index][rest - amount[index]] != Integer.MAX_VALUE) {//至少有一张的话利用空间感可以优化
                    dp[index][rest] = Math.min(dp[index][rest], dp[index][rest - amount[index]] + 1);
                }
            }
        }
        return dp[0][aim];
    }

    /**
     * 用另一种递归过程来实现。每一层按照是否能够使用每一种硬币来展开，每层都只能使用一个.这种递归方式会导致调用得很深
     * 会有非常非常多的重复计算，数据量大的话很可能跑不完。所以一定要改成动态规划。
     *
     * @param amount:面额数组
     * @param aim:要凑成的金额
     * @return
     */
    private static int minCoins2(int[] amount, int aim) {
        if (amount == null || amount.length == 0 || aim < 0) {
            return 0;
        }
        return process3(amount, aim);
    }

    /**
     * 注意：此题是利用递归的深度来解题。并不是收集最底层的1来进行解题。所以rest==0的时候return 0.如果return 1的话边数会多一个
     * @param amount:面值
     * @param rest:还剩下多少钱要凑
     * @return
     */
    private static int process3(int[] amount, int rest) {
        if (rest == 0) {
            return 0;
        }
//        if (!restIsOk(amount, rest)) {//这段代码可以省略，因为这里的min进去之后如果还是MAX也就代表着现在没有合适的硬币了
//            return Integer.MAX_VALUE;
//        }
        int min = Integer.MAX_VALUE;
        for (int coin : amount) {
            if (rest - coin >= 0) {//每一层选择性展开
                int next = process3(amount, rest - coin);
                min = next!= Integer.MAX_VALUE?Math.min(min,next+1):min;
            }
        }
        return min;
    }

    private static boolean restIsOk(int[] amount, int rest) {//至少有一个硬币可以用
        for (int coin : amount)
            if (rest >= coin) return true;
        return false;
    }

    private static int minCoins2Dp(int[] amount, int aim) {
        if (amount == null || amount.length == 0 || aim < 0) {
            return 0;
        }
        int []dp=new int[aim+1];//dp[rest]=0,
        for (int rest=1;rest<aim+1;rest++){
            if (!restIsOk(amount, rest)) {
                dp[rest]= Integer.MAX_VALUE;
            }
            int min = Integer.MAX_VALUE;
            for (int coin : amount) {
                if (rest - coin >= 0) {//每一层选择性展开
                    int next = dp[rest-coin];
                    min = next!= Integer.MAX_VALUE?Math.min(min,next+1):min;
                }
            }
            dp[rest]=min;
        }

        return dp[aim];
    }

    //====================================================================================================================================

    /**
     * @param amount:面额数组，每种硬币可以使用无数个
     * @param aim:要凑成的目标
     * @return :凑成aim一共有多少种方法
     */
    private static int ways(int[] amount, int aim) {
        if (amount == null || amount.length == 0 || aim < 0) {
            return 0;
        }
        return process2(amount, 0, aim);
    }

    /**
     * @param amount:
     * @param index:
     * @param rest:
     * @return :返回的是有多少种方法凑成rest
     */
    private static int process2(int[] amount, int index, int rest) {
        if (index == amount.length) {
            return rest == 0 ? 1 : 0;
        }
        int sum = 0;
        for (int i = 0; amount[index] * i <= rest; i++) {//i代表amount[index]硬币选了几个
            int next = process2(amount, index + 1, rest - amount[index] * i);
            sum += next;
        }
        return sum;
    }

    private static int waysDp1(int[] amount, int aim) {
        if (amount == null || amount.length == 0 || aim < 0) {
            return 0;
        }
        int N = amount.length;
        int dp[][] = new int[N + 1][aim + 1];
        dp[N][0] = 1;//第N行初始化完毕
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                int sum = 0;
                for (int i = 0; amount[index] * i <= rest; i++) {//i代表amount[index]硬币选了几个
                    int next = dp[index + 1][rest - amount[index] * i];
                    sum += next;
                }
                dp[index][rest] = sum;
            }
        }
        return dp[0][aim];
    }

    /**
     * 去for循环的方法：如果至少可以用一个硬币就可以使用规律。如果一个都不用就保留dp[index+1][rest]
     *
     * @param amount:
     * @param aim:
     * @return
     */
    private static int waysDp2(int[] amount, int aim) {
        if (amount == null || amount.length == 0 || aim < 0) {
            return 0;
        }
        int N = amount.length;
        int dp[][] = new int[N + 1][aim + 1];
        dp[N][0] = 1;//第N行初始化完毕
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                dp[index][rest] = dp[index + 1][rest];
                if (rest - amount[index] >= 0) {
                    dp[index][rest] += dp[index][rest - amount[index]];
                }
            }
        }
        return dp[0][aim];
    }

    public static void main(String[] args) {
        int[] amount = {1,5,10,20,50};
        int aim = 50;

        long start = System.currentTimeMillis();
        System.out.println(minCoins(amount, aim));
        long end = System.currentTimeMillis();
        System.out.println("cost time: " + (end - start) + "ms");

        start = System.currentTimeMillis();
        System.out.println(minCoinsDp1(amount, aim));
        end = System.currentTimeMillis();
        System.out.println("cost time: " + (end - start) + "ms");

        start = System.currentTimeMillis();
        System.out.println(minCoinsDp2(amount, aim));
        end = System.currentTimeMillis();
        System.out.println("cost time: " + (end - start) + "ms");

        start = System.currentTimeMillis();
        System.out.println(minCoins2(amount, aim));
        end = System.currentTimeMillis();
        System.out.println("cost time: " + (end - start) + "ms");

        start = System.currentTimeMillis();
        System.out.println(minCoins2Dp(amount, aim));
        end = System.currentTimeMillis();
        System.out.println("cost time: " + (end - start) + "ms");

        System.out.println("====================");

        System.out.println(minCoins(amount, aim));
        System.out.println(minCoinsDp1(amount, aim));
        System.out.println(minCoinsDp2(amount, aim));
        System.out.println(minCoins2(amount, aim));
        System.out.println(minCoins2Dp(amount, aim));
        System.out.println("====================");
        System.out.println(ways(amount, aim));
        System.out.println(waysDp1(amount, aim));
        System.out.println(waysDp2(amount, aim));

        }

    }
