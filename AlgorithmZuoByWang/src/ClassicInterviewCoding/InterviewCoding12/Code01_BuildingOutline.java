package ClassicInterviewCoding.InterviewCoding12;

import TestUtils.ArrayUtil;

import java.util.*;
import java.util.Map.Entry;

// lintcode
public class Code01_BuildingOutline {

    // 描述高度变化的对象
    public static class Op {
        public int x; // x轴上的值
        public boolean isAdd;// true为加入，false为删除
        public int h; // 高度

        public Op(int x, boolean isAdd, int h) {
            this.x = x;
            this.isAdd = isAdd;
            this.h = h;
        }
    }

    // 排序的比较策略
    // 1，第一个维度的x值从小到大。
    // 2，如果第一个维度的值相等，看第二个维度的值，“加入”排在前，“删除”排在后
    // 3，如果两个对象第一维度和第二个维度的值都相等，则认为两个对象相等，谁在前都行。
    public static class OpComparator implements Comparator<Op> {
        @Override
        public int compare(Op o1, Op o2) {
            if (o1.x != o2.x) {
                return o1.x - o2.x;
            }
            if (o1.isAdd != o2.isAdd) {
                return o1.isAdd ? -1 : 1;
            }
            return 0;
        }
    }

    // 全部流程的主方法
    // [s,e,h]
    // [s,e,h]
    // { {1,5,3} , {6,8,4}  .. ...  ...    }
    public static List<List<Integer>> buildingOutline(int[][] matrix) {
        int N = matrix.length;
        Op[] ops = new Op[N << 1];
        for (int i = 0; i < matrix.length; i++) {
            ops[i * 2] = new Op(matrix[i][0], true, matrix[i][2]);
            ops[i * 2 + 1] = new Op(matrix[i][1], false, matrix[i][2]);
        }
        // 把描述高度变化的对象数组，按照规定的排序策略排序
        Arrays.sort(ops, new OpComparator());

        // TreeMap就是java中的红黑树结构，直接当作有序表来使用
        // key  某个高度  value  次数
        TreeMap<Integer, Integer> mapHeightTimes = new TreeMap<>();
        // key   x点，   value 最大高度
        TreeMap<Integer, Integer> mapXHeight = new TreeMap<>();

        for (int i = 0; i < ops.length; i++) {
            // ops[i]
            if (ops[i].isAdd) { // 如果当前是加入操作
                if (!mapHeightTimes.containsKey(ops[i].h)) { // 没有出现的高度直接新加记录
                    mapHeightTimes.put(ops[i].h, 1);
                } else { // 之前出现的高度，次数加1即可
                    mapHeightTimes.put(ops[i].h, mapHeightTimes.get(ops[i].h) + 1);
                }
            } else { // 如果当前是删除操作
                if (mapHeightTimes.get(ops[i].h) == 1) { // 如果当前的高度出现次数为1，直接删除记录
                    mapHeightTimes.remove(ops[i].h);
                } else { // 如果当前的高度出现次数大于1，次数减1即可
                    mapHeightTimes.put(ops[i].h, mapHeightTimes.get(ops[i].h) - 1);
                }
            }
            // 根据mapHeightTimes中的最大高度，设置mapXvalueHeight表
            if (mapHeightTimes.isEmpty()) { // 如果mapHeightTimes为空，说明最大高度为0
                mapXHeight.put(ops[i].x, 0);
            } else { // 如果mapHeightTimes不为空，通过mapHeightTimes.lastKey()取得最大高度
                mapXHeight.put(ops[i].x, mapHeightTimes.lastKey());
            }
        }
        // res为结果数组，每一个List<Integer>代表一个轮廓线，有开始位置，结束位置，高度，一共三个信息
        List<List<Integer>> res = new ArrayList<>();
        // 一个新轮廓线的开始位置
        int start = 0;
        // 之前的最大高度
        int preHeight = 0;
        // 根据mapXvalueHeight生成res数组
        for (Entry<Integer, Integer> entry : mapXHeight.entrySet()) {
            // 当前位置
            int curX = entry.getKey();
            // 当前最大高度
            int curMaxHeight = entry.getValue();
            if (preHeight != curMaxHeight) { // 之前最大高度和当前最大高度不一样时
                if (preHeight != 0) {
                    res.add(new ArrayList<>(Arrays.asList(start, curX, preHeight)));
                }
                start = curX;
                preHeight = curMaxHeight;
            }
        }
        return res;
    }

    public static List<List<Integer>> buildingOutline2(int[][] matrix) {
        int N = matrix.length;
        Op[] ops = new Op[N << 1];
        for (int i = 0; i < matrix.length; i++) {
            ops[i * 2] = new Op(matrix[i][0], true, matrix[i][2]);
            ops[i * 2 + 1] = new Op(matrix[i][1], false, matrix[i][2]);
        }
        // 把描述高度变化的对象数组，按照规定的排序策略排序
        Arrays.sort(ops, new OpComparator());

        // TreeMap就是java中的红黑树结构，直接当作有序表来使用
        // key  某个高度  value  次数
//        TreeMap<Integer, Integer> mapHeightTimes = new TreeMap<>();
        Heap heap=new Heap(ops.length);
        // key   x点，   value 最大高度
        TreeMap<Integer, Integer> mapXHeight = new TreeMap<>();

        for (int i = 0; i < ops.length; i++) {
            // ops[i]
            heap.operate(ops[i]);
            // 根据mapHeightTimes中的最大高度，设置mapXvalueHeight表
            if (heap.isEmpty()) { // 如果mapHeightTimes为空，说明最大高度为0
                mapXHeight.put(ops[i].x, 0);
            } else { // 如果mapHeightTimes不为空，通过mapHeightTimes.lastKey()取得最大高度
                mapXHeight.put(ops[i].x, heap.peek());
            }
        }
        // res为结果数组，每一个List<Integer>代表一个轮廓线，有开始位置，结束位置，高度，一共三个信息
        List<List<Integer>> res = new ArrayList<>();
        // 一个新轮廓线的开始位置
        int start = 0;
        // 之前的最大高度
        int preHeight = 0;
        // 根据mapXvalueHeight生成res数组
        for (Entry<Integer, Integer> entry : mapXHeight.entrySet()) {
            // 当前位置
            int curX = entry.getKey();
            // 当前最大高度
            int curMaxHeight = entry.getValue();
            if (preHeight != curMaxHeight) { // 之前最大高度和当前最大高度不一样时
                if (preHeight != 0) {
                    res.add(new ArrayList<>(Arrays.asList(start, curX, preHeight)));
                }
                start = curX;
                preHeight = curMaxHeight;
            }
        }
        return res;
    }

    public static class Heap{
        private int size;
        private int[] heap;
        private HashMap<Integer,Integer> timesMap;//记录一个值对应的次数
        private HashMap<Integer,Integer> indexMap;//记录某一个值在哪个下标
        public Heap(int N){
            size=0;
            heap=new int[N+1];
            timesMap=new HashMap<>();
            indexMap=new HashMap<>();
        }

        private boolean more(int a,int b){
            return heap[a]>heap[b];
        }

        private void swap(int a,int b){
            indexMap.put(heap[a],b);
            indexMap.put(heap[b],a);
            int t=heap[a];
            heap[a]=heap[b];
            heap[b]=t;
        }

        private void swim(int k){
            for (;k>1&&more(k,k>>1);k>>=1) swap(k,k>>1);
        }

        private void sink(int k){
            while(k<<1<=size){
                int child=k<<1;
                if (child+1<=size&&more(child+1,child))child++;
                if (more(k,child))break;
                swap(k,child);
                k=child;
            }
        }

        public int peek(){
            return heap[1];
        }

        boolean isEmpty(){
            return size==0;
        }

        public void operate(Op op){
            if (op.isAdd){
                if (timesMap.containsKey(op.h)){
                    timesMap.put(op.h,1+timesMap.get(op.h));
                }else {
                    timesMap.put(op.h,1);
                    indexMap.put(op.h,size+1);
                    heap[++size]=op.h;
                    swim(size);
                }
            }else {
                if (timesMap.get(op.h)==1){//保证减之前一定加了
                    Integer k = indexMap.get(op.h);
                    swap(k,size--);
                    sink(k);//把中间删了，需要同时去sink和swim
                    swim(k);
                    indexMap.remove(op.h);
                    timesMap.remove(op.h);
                }else {
                    timesMap.put(op.h,-1+timesMap.get(op.h));
                }
            }
        }


    }

    public static boolean compare(List<List<Integer>> lists1, List<List<Integer>> lists2) {
        if (lists1 == null && lists2 == null) return true;
        if (lists1 == null || lists2 == null) return false;
        if (lists1.size() != lists2.size()) return false;
        for (int i = 0; i < lists1.size(); i++) {
            List<Integer> list1 = lists1.get(i);
            List<Integer> list2 = lists2.get(i);
            if (list1 == null && list2 == null) break;
            if (list1 == null || list2 == null) return false;
            if (list1.size() != list2.size()) return false;
            for (int j = 0; j < list1.size(); j++) {
                Integer integer1 = list1.get(j);
                Integer integer2 = list2.get(j);
                if (integer1 != integer2) return false;
            }
        }
        return true;
    }

    public static void test1(){
        int[][] m=new int[][]{
                {1,4,5},
                {2,6,8},
                {6,1,2},
                {3,7,9},
        };
        int[][] m2=new int[][]{
                {1,4,5},
                {2,6,8},
                {2,7,2},
                {3,7,9},
        };
        List<List<Integer>> lists1=new ArrayList<>();
        List<List<Integer>> lists2=new ArrayList<>();
        for (int i = 0; i < m.length; i++) {
            List<Integer> list1=new ArrayList<>();
            List<Integer> list2=new ArrayList<>();
            for (int j = 0; j < m[0].length; j++) {
                list1.add(m[i][j]);
                list2.add(m2[i][j]);
            }
            lists1.add(list1);
            lists2.add(list2);
        }
        System.out.println(compare(lists1,lists2));
    }

    public static void test2(){
        int[][] m=new int[][]{
                {  13,   20,   16 },
        };
        List<List<Integer>> lists1 = buildingOutline(m);
        List<List<Integer>> lists2 = buildingOutline2(m);
        System.out.println(lists1);
        System.out.println(lists2);
        System.out.println(compare(lists1,lists2));
    }

    public static void testForIntMatrix() {//参数为int[][]
        ArrayUtil arrayUtil = new ArrayUtil();
        int times = 10_0000;//测试次数
        long time1=0,time2=0;
        boolean isok = true;
        int maxN = 10;//matrix大小在[0~maxN][0~maxM]随机
        int maxM = 3;//matrix大小在[0~maxN][0~maxM]随机
        int maxValue = 20;//matrix的值在[0,maxValue]随机
//        int parameter1=0;//测试函数的参数
//        int maxParameter1=1000;//参数1在[0,maxParameter1]随机
        int[][] t1 = null;
//        int[][] t2 = null;
        List<List<Integer>> res1 = null, res2 = null;
        for (int i = 0; i < times; i++) {
//            parameter1=arrayUtil.ran(maxParameter1);

            t1 = generateRandomMatrix(arrayUtil.ran(1,maxN), arrayUtil.ran(maxM,maxM), maxValue);
//            t2 = arrayUtil.generateRandomMatrix(arrayUtil.ran(maxN), arrayUtil.ran(maxM), maxValue);

//            t1=arrayUtil.generateRandomArr(arrayUtil.ran(maxSize),1,maxValue);//正数数组[1,maxValue]
//            t2=arrayUtil.generateRandomArr(arrayUtil.ran(maxSize),1,maxValue);//正数数组[1,maxValue]
            long l = System.currentTimeMillis();
            res1 = buildingOutline(t1);
            time1+=System.currentTimeMillis()-l;
            l=System.currentTimeMillis();
            res2 = buildingOutline2(t1);
            time2+=System.currentTimeMillis()-l;
            if (!compare(res1,res2)) {
                isok = false;
                break;
            }
        }
        arrayUtil.printMatrix(t1);//打印参数
//        System.out.println("parameter:"+parameter1);//打印参数
        if (isok)System.out.println("m1 cost "+time1+"ms");
        System.out.println(res1);//针对返回值的操作
        if (isok)System.out.println("m2 cost "+time2+"ms");
        System.out.println(res2);//针对返回值的操作
        System.out.println(isok ? "success" : "fail");
    }

    public static int[][] generateRandomMatrix(int N, int M, int max) {
        if (N < 0 || M < 0 || max < 0) return null;
        int[][] res = new int[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                do {
                    res[i][j] = ran(1,max);
                }while (j==1&&res[i][j]<res[i][j-1]);//保证立起来的位置在结束的位置前面
            }
        }
        return res;
    }

    public static int ran(int max) {//[0,max]
        return (int) (Math.random() * (max + 1));
    }


    public static int ran(int l, int r) {//[l,r]
        return l + ran(r - l);
    }

    public static void main(String[] args) {
//        test1();
        test2();
//        testForIntMatrix();

    }

}
