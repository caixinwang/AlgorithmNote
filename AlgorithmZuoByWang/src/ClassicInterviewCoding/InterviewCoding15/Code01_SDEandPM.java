package ClassicInterviewCoding.InterviewCoding15;

import java.util.*;

public class Code01_SDEandPM {

	public static class Program {
		public int index;//方便填答案
		public int pm;//经理的编号
		public int start;//润色出来的时间
		public int rank;//优先级
		public int cost;//花费

		public Program(int index, int pmNum, int begin, int rank, int cost) {
			this.index = index;
			this.pm = pmNum;
			this.start = begin;
			this.rank = rank;
			this.cost = cost;
		}
	}

	public static class ManagerRule implements Comparator<Program> {
		@Override
		public int compare(Program o1, Program o2) {
			if (o1.rank!=o2.rank){//优先级
				return o1.rank-o2.rank;
			}else if (o1.cost!=o2.cost){//花费时间
				return o1.cost-o2.cost;
			}
			return o1.start-o2.start;//开始时间
		}
	}

	/**
	 * 每一个经理有自己的设计池堆。所有的程序员共用一个程序员堆。设计池堆我们就用系统提供的，搞一个队列放进去
	 * 程序员堆我们自己来实现，以应对设计池堆顶变化所产生的堆结构调整
	 * 结构中有两个对外接口，一个是add(项目书),一个是pop弹出一个最受程序员喜爱的项目
	 * 在这个结构中，我们要手动维持设计池堆的堆顶都要出现在程序员堆中。如果设计池堆顶换了，程序员堆中的项目也要相应更换
	 */
	public static class BigQueues {
		private ArrayList<PriorityQueue<Program>> managerHeapList;
		private int size;//程序员堆的大小
		private Program[] heap;//程序员堆
		private int[] indexMap;//indexMap[i]代表i号经理最喜好的设计书在heap中的哪一个位置

		public BigQueues(int managerNum) {
			managerHeapList=new ArrayList<>();
			for (int i = 0; i <= managerNum ; i++) {
				managerHeapList.add(new PriorityQueue<>(new ManagerRule()));
			}
			size=0;
			heap=new Program[managerNum+1];
			indexMap=new int[managerNum+1];
			Arrays.fill(indexMap, -1);
		}

		public boolean isEmpty(){
			return size==0;
		}

		public void add(Program program){
			PriorityQueue<Program> managerHeap = managerHeapList.get(program.pm);
			managerHeap.add(program);
			Program curLike = managerHeap.peek();//目前经理最喜欢的项目
			int heapindex = indexMap[curLike.pm];
			if (heapindex==-1){//这个经理之前没有项目在程序员堆里面
				indexMap[curLike.pm]=size+1;
				heap[++size]=curLike;
				swim(size);
			}else {//新的旧的是不是一样的，不好判断，直接替换了再说
				heap[heapindex]=curLike;//不管旧的和新的一不一样，我在直接换了再调整
				swim(heapindex);//swim和sink其中之一会发生，但不是同时发生
				sink(heapindex);
			}
		}

		public Program pop(){
			Program program = heap[1];
			PriorityQueue<Program> programs = managerHeapList.get(program.pm);
			programs.poll();//这里注意，一定要和程序员堆的堆顶一起弹出了！程序员堆弹出了，对应的经理堆一定也要弹出
			if (programs.isEmpty()){//这个经理已经没有别的项目了
				swap(1,size--);
				sink(1);
				indexMap[program.pm]=-1;
			}else {//如果经理还有项目
				heap[1]=programs.peek();//换个项目，调整堆结构即可
				sink(1);
			}
			return program;
		}

		private boolean better(int a, int b){
			Program p1 = heap[a];
			Program p2 = heap[b];
			if (p1.cost!=p2.cost){
				return p1.cost<p2.cost;
			}
			return p1.pm<p2.pm;
		}

		private void swap(int a,int b){
			indexMap[heap[a].pm]=b;
			indexMap[heap[b].pm]=a;
			Program t = heap[a];
			heap[a]=heap[b];
			heap[b]=t;
		}

		private void swim(int k){
			for (;k>1&&better(k,k>>1);k>>=1) swap(k,k>>1);
		}

		private void sink(int k){
			while(k<<1<=size){//左孩子存在
				int child=k<<1;
				if (child+1<=size&&better(child+1,child)) child++;
				if (better(k,child)) break;
				swap(k,child);
				k=child;
			}
		}

	}

	public static class StartRule implements Comparator<Program> {

		@Override
		public int compare(Program o1, Program o2) {
			return o1.start - o2.start;
		}

	}

	/**
	 *
	 * @param managerNum 经理的数量
	 * @param coderNum 程序员的数量
	 * @param programs 项目数组-二维的
	 * @return 返回每个项目完成的时间
	 */
	public static int[] workFinish(int managerNum, int coderNum, int[][] programs) {
		PriorityQueue<Program> lock=new PriorityQueue<>(new StartRule());//时间早的先解锁
		for (int i = 0; i < programs.length; i++) {
			Program program=new Program(i,programs[i][0],programs[i][1],programs[i][2],programs[i][3]);
			lock.add(program);
		}
		PriorityQueue<Integer> wake=new PriorityQueue<>();//小根堆，模拟时间
		for (int i = 0; i < coderNum; i++) {
			wake.add(1);//默认的世界时间初始点是1
		}
		int[]res=new int[programs.length];
		int finish=0;
		BigQueues bigQueues=new BigQueues(managerNum);
		while(finish!=programs.length){
			Integer time = wake.poll();//醒来的时间点
			while(!lock.isEmpty()&&lock.peek().start<=time){//把解锁的项目全部扔进我们的结构中
				bigQueues.add(lock.poll());
			}
			if (bigQueues.isEmpty()){//没有能做的工作
				wake.add(lock.peek().start);
			}else {//有能做的工作
				Program pop = bigQueues.pop();
				wake.add(time+pop.cost);
				finish++;
				res[pop.index]=time+pop.cost;
			}
		}
		return res;
	}

	public static void printArray(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			System.out.println(arr[i]);
		}
	}

	public static void main(String[] args) {
		int pms = 2;
		int sde = 2;
		int[][] programs = { { 1, 1, 1, 2 }, { 1, 2, 1, 1 }, { 1, 3, 2, 2 }, { 2, 1, 1, 2 }, { 2, 3, 5, 5 } };
		int[] ans = workFinish(pms, sde, programs);
		printArray(ans);
	}

}
