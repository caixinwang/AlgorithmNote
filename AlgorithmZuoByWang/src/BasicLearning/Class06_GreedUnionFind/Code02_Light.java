package BasicLearning.Class06_GreedUnionFind;

import java.util.HashSet;

/**
 * 给定一个字符串，里面有叉和点。返回至少需要几盏灯才能点亮全部的居民区。
 */
public class Code02_Light {
    /**
     * 利用暴力递归来实现
     *
     * @param road:只带有叉和点的字符串
     * @return
     */
    public static int minLight1(String road) {
        if (road == null || road.length() == 0) {
            return 0;
        }
        return process1(road.toCharArray(), 0, new HashSet<>());
    }

    /**
     * 因为index之前的位置已经决定好了，我们只需要决定index之后的位置是否放灯即可，最后lights中的元素个数就是灯的总数。
     * 每到一个'点'都有两种情况,放灯或者不放灯，我们让它递归下去。然后判断‘点’自己有没有被点亮，点亮说明左右两边或者自己位置有灯
     * 注意：在递归的时候不要把叉和点区别对待，不然不好递归
     *
     * @param road:只带有叉和点的字符数组
     * @param index:在index之前的位置已经做好了是否放灯的决定---不包括index自己
     * @param lights:决定放灯的位置记录在light中
     * @return :返回能够照亮所有居民点的最少灯数
     */
    public static int process1(char[] road, int index, HashSet<Integer> lights) {
        if (index == road.length) {//结束--base case//说明[0,road.length-1]做完决定
            for (int i = 0; i < road.length; i++) {//对于数组里面的每一个点都进行判断
//                if (road[i] == '.' && !lights.contains(i - 1) && !lights.contains(i) && !lights.contains(i + 1)) {
//                    return Integer.MAX_VALUE;//说明没有照亮所有的居民区
//                }
                if (road[i] != 'x') {
                    if (!lights.contains(i - 1) && !lights.contains(i) && !lights.contains(i + 1)) {
                        return Integer.MAX_VALUE;//说明没有照亮所有的居民区
                    }
                }
            }
            return lights.size();
        } else {//还未结束
            int no = process1(road, index + 1, lights);//index位置不放灯
            int yes = Integer.MAX_VALUE;//index位置放灯,如果index位置是x那么将一直保持MAX_VALUE
            if (road[index] == '.') {//只有index位置是'点'这个yes才有意义。因为墙不能放灯
                lights.add(index);
                yes = process1(road, index + 1, lights);
                lights.remove(index);
            }
            return Math.min(no, yes);

        }
    }

    public static int minLights2(String road) {
        if (road == null || road.length() == 0) {
            return 0;
        }
        return process2(road.toCharArray());
    }

    /**
     * 潜台词：index前面的影响不到index
     *
     * @param road:数组空不空在minLights2控制，这里默认是非空
     * @return
     */
    public static int process2(char[] road) {
        int index = 0;
        int res = 0;
        while (index < road.length) {//因为index后跳的距离不规律所以不用for循环
            if (road[index] == 'x') {
                index++;
            } else {//index是点
                res++;//写在这里更节约。
                if (index + 1 == road.length) {//index后面没有了，那么只能在index放灯
                    break;
                } else {//index后面还有
                    if (road[index + 1] == 'x') {//index+1是x
                        index += 2;
                    } else {//index+1是点,不管index+2是x还是‘点’我们都要在index+1的位置放灯，然后跳到index+3
                        index += 3;
                    }
                }
            }
        }
        return res;
    }

    public static int generate01() {
        if (Math.random() < 0.5) {
            return 0;
        } else {
            return 1;
        }
    }

    public static String generateCase() {
        int maxSize = 10;
        char[] arr = new char[(int) ((Math.random() * (maxSize + 1)))];
        for (int i = 0; i < arr.length; i++) {
            arr[i]=generate01()==0?'x':'.';
        }
        return String.valueOf(arr);
    }

    public static void main(String[] args) {

        int times = 10000;
        boolean success = true;
        for (int i = 0; i < times; i++) {
            String str = generateCase();
            if (minLight1(str) != minLights2(str)) {
                success = false;
            }
        }
        if (success) {
            System.out.println("yes");
        } else {
            System.out.println("no!");
        }


    }

}
