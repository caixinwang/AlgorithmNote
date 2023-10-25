package ClassicInterviewCoding.InterviewCoding18;

public class Code01_PathsToNums {

    public static void pathsToNums(int[] paths) {
        if (paths == null || paths.length == 0) {
            return;
        }
        // citiesPath -> distancesArray
        pathsToDistans2(paths);

        // distancesArray -> countsArray
        distansToNums(paths);
    }

    /**
     * coding技巧：我们这里是用下一条path[p]!=cap这种形式来判断的，所以可能一次while都进不去，所以需要单独讨论这种情况。
     * 如果是p！=cap这种的话，那么while必定进去一次，这样不需要单独讨论了。但是共同点是，进去了之后最后一个点，也就是退出点
     * 都是需要单独讨论的
     *
     * @param paths 把path[i]=j从原本的i到j的含义变为i到首都的距离为-x。
     */
    public static void pathsToDistans(int[] paths) {
        int cap = 0;//首都
        for (int i = 0; i < paths.length; i++) {//找首都
            if (paths[i] == i) {
                cap = i;
                break;
            }
        }
        paths[cap] = 0;
        for (int i = 0; i < paths.length; i++) {
            if (i != cap && paths[i] >= 0) {//i位置不是首都，且还没有扭转过含义
                int p = i;//记录一下i
                int next = -1;
                int pre = -1;
                while (paths[paths[p]] >= 0 && paths[p] != cap) {//在首都或者改过含义的前一个退出
                    next = paths[p];//next用完改next
                    if (pre != -1) paths[p] = pre;//上面用完paths[p]这里就可以改了
                    pre = p;//用完就改
                    p = next;//用完就改，逻辑是连着的
                }//退出的地方，此时的paths[p]含义不是pre。退出的点p要单独讨论一下
                if (p == i) paths[p] = paths[paths[p]] - 1;//一次都没进去过要单独讨论一下，因为此时pre是没有含义的
                else {
                    int dis = paths[paths[p]];//下一跳。要么是改过含义的，要么是首都
                    paths[p] = pre;//退出点p没有进入到while循环体，所以paths[p]的含义不是pre，手动更新一下
                    while (p != i) {
                        next = paths[p];//往回跳
                        paths[p] = --dis;
                        p = next;
                    }
                    paths[i] = --dis;//i位置出来设置
                }

            }
        }

    }

    /**
     * 与上面相比，while的结束条件是p本身而不是下一跳，所以while一定至少进一次。退出点就是cap或者改过含义的点。
     * 省掉了一次也不进去时候的单独判断。直接利用pre回到前面一个结点，利用好之前维持好的path[p]=pre的含义
     */
    public static void pathsToDistans2(int[] paths) {
        int cap = 0;//首都
        for (int i = 0; i < paths.length; i++) {//找首都
            if (paths[i] == i) {
                cap = i;
                break;
            }
        }
        paths[cap] = 0;
        for (int i = 0; i < paths.length; i++) {
            if (i != cap && paths[i] >= 0) {//i位置不是首都，且还没有扭转过含义
                int p = i;//记录一下i
                int next = -1;
                int pre = -1;
                while (p != cap && paths[p] >= 0) {//至少进一次，退出点是cap或者是改过含义的点
                    next = paths[p];//next用完改next
                    if (pre != -1) paths[p] = pre;//上面用完paths[p]这里就可以改了
                    pre = p;//用完就改
                    p = next;//用完就改，逻辑是连着的
                }
                int dis = paths[p];//拿到退出点的值
                p = pre;//从退出点回到前面的那个点
                while (p != i) {//i位置需要单独处理
                    next = paths[p];//往回跳
                    paths[p] = --dis;
                    p = next;
                }
                paths[i] = --dis;//i位置出来设置
            }
        }
    }

    /**
     * 首都到首都距离为0，我们单独处理。
     * @param disArr 从过渡态变为我们要的，到首都距离为i的有j座城市
     */
    public static void distansToNums(int[] disArr) {
        for (int i = 0; i < disArr.length; i++) {
            if (disArr[i]<0){
                int p=i;
                int next=-1;
                int set=-1;
                while (disArr[p]<0){//停止点是统计含义
                    next=-disArr[p];//先把值存下，再去修改
                    disArr[p]=0;//含义我们用了，清空不要了，首都位置的0，认为也是清空过了。
                    if (set!=-1) disArr[p]=set;//第二次进来或者以上都设置为1
                    set=1;
                    p=next;//继续往下跳
                }
                disArr[p]++;//p是统计含义，直接++
            }
            disArr[0]=1;//首都位置手动设置
        }
    }


    public static void printArray(int[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }
        for (int i = 0; i != arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[] paths = {9, 1, 4, 9, 0, 4, 8, 9, 0, 1};

        printArray(paths);
        pathsToDistans2(paths);
        printArray(paths);
        distansToNums(paths);
        printArray(paths);
//        pathsToNums(paths);
//        printArray(paths);

    }

}
