package BasicLearning.Class06_GreedUnionFind;

import java.util.Arrays;
import java.util.Comparator;

public class Code01_BestArrange {
    public static class Program {
        int start;
        int end;

        public Program(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    public static int bestArrange1(Program[] arr) {
        if (arr==null||arr.length==0){
            return 0;
        }
        return process1(arr,0,0);
    }

    /**
     *
     * @param arr:还剩下的没安排的会议
     * @param done:已经安排了几个会议
     * @param timeLine:现在来到的时间点
     * @return
     */
    public static int process1(Program[] arr,int done,int timeLine){
        if (arr==null||arr.length==0){
            return 0;
        }
        int best=done;
        for (int i=0;i<arr.length;i++){
            if (arr[i].start>=timeLine){
                Program[] p=copyButExcept(arr,i);
                best=Math.max(best,process1(arr,done+1,arr[i].end));//利用递归for，让best一直更新
            }
        }
        return best;
    }

    public static Program[] copyButExcept(Program[] arr,int index){
        int i=0;//给p用
        Program[] p=new Program[arr.length-1];
        for (int j=0;j<arr.length;j++){//遍历arr
            if (j!=index){
                p[i++]=arr[j];
            }
        }
        return p;
    }

    public static int bestArrange2(Program[] arr){
        int timeLine=0;
        int best=0;
        Arrays.sort(arr,new ProgramComparator());//按照结束时间先后来排序
        for (int i=0;i<arr.length;i++){
            if (arr[i].start>=timeLine){
                best++;
                timeLine=arr[i].end;
            }
        }
        return best;
    }

    public static class ProgramComparator implements Comparator<Program>{
        public int compare(Program o1,Program o2){
            return o1.end-o2.end;
        }
    }
    // for test
    public static Program[] generatePrograms(int programSize, int timeMax) {
        Program[] ans = new Program[(int) (Math.random() * (programSize + 1))];
        for (int i = 0; i < ans.length; i++) {
            int r1 = (int) (Math.random() * (timeMax + 1));
            int r2 = (int) (Math.random() * (timeMax + 1));
            if (r1 == r2) {
                ans[i] = new Program(r1, r1 + 1);
            } else {
                ans[i] = new Program(Math.min(r1, r2), Math.max(r1, r2));
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int programSize = 12;
        int timeMax = 20;
        int timeTimes = 1000000;
        for (int i = 0; i < timeTimes; i++) {
            Program[] programs = generatePrograms(programSize, timeMax);
            if (bestArrange1(programs) != bestArrange2(programs)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
