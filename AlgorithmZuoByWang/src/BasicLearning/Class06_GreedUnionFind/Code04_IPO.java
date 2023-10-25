package BasicLearning.Class06_GreedUnionFind;

import java.util.Comparator;
import java.util.PriorityQueue;

public class Code04_IPO {

    public static class Program {
        public int cost;
        public int capital;

        public Program(int cost, int capital) {
            this.cost = cost;
            this.capital = capital;
        }
    }

    public static class LessCostComparator implements Comparator<Program>{
        @Override
        public int compare(Program o1, Program o2) {
            return o1.cost-o2.cost;
        }
    }

    public static class MoreCostComparator implements Comparator<Program>{
        @Override
        public int compare(Program o1, Program o2) {
            return o2.capital-o1.capital;
        }
    }

    /**
     * cost数组一定和capital数组等长
     * @param k:最多接收k个项目
     * @param w:是初始资金
     * @param cost:数组下标i代表第i个项目的花费
     * @param capital:第i个项目的净利润
     * @return
     */
    public static int findMax(int k, int w, int[] cost, int[] capital) {
        PriorityQueue<Program> costHeap=new PriorityQueue<>(new LessCostComparator());
        PriorityQueue<Program> capitalHeap=new PriorityQueue<>(new MoreCostComparator());
        for (int i=0;i<cost.length;i++){//全部放入costHeap等待解锁
            costHeap.add(new Program(cost[i],capital[i]));
        }
        for (int i=0;i<k;i++){
            while(!costHeap.isEmpty()&&w>=costHeap.peek().cost){//有有待解锁的项目，并且我有能力启动
                capitalHeap.add(costHeap.poll());
            }
            if (capitalHeap.isEmpty()){
                return w;
            }
            w+=capitalHeap.poll().capital;
        }
        return w;
    }

    public static void main(String[] args) {
        int cost[]={1,1,3};
        int capital[]={2,1,2};
        System.out.println(findMax(2,1,cost,capital));
    }
}
