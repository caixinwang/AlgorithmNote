package ClassicInterviewCoding.InterviewCoding03;

import java.util.*;

public class Code01_ChooseWork {

	public static class Job {
		public int hard;//工作的难度
		public int money;//工作的钱数

		public Job(int hard,int money) {
			this.hard = hard;
			this.money = money;
		}
	}

	public static class JobComparator implements Comparator<Job> {
		@Override
		public int compare(Job o1, Job o2) {//先按照工作的难度升序，再按照钱降序排列
			return o1.hard != o2.hard ? (o1.hard - o2.hard) : (o2.money - o1.money);
		}
	}

	/**
	 * 一个人的能力值大于工作的难度值就可以胜任这个工作
	 * @param job 所有工作放在这个数组中
	 * @param ability ability[i]代表第i个人的能力值
	 * @return res[i]代表第i个人所能选到的最高报酬。res和ability中的人一一对应
	 */
	public static int[] getMoneys(Job[] job, int[] ability) {
		if (job==null||job.length==0||ability==null||ability.length==0) return null;//确保至少有一个工作/人
		Arrays.sort(job,new JobComparator());//按照难度递增钱数递减排序
		TreeMap<Integer,Integer> map=new TreeMap<>();//将所有的合格的组长放入有序表中,<难度，钱数>
		map.put(job[0].hard,job[0].money);//第一个肯定是合格的队长
		//当job[i].hard>job[i-1].hard时就换队长了，job[i].money>job[i-1].money时这个队长就是合格的
		for (int i = 1; i < job.length; i++) {
			if (job[i].hard>job[i-1].hard&&job[i].money>job[i-1].money){//难度越难钱越多才行
				map.put(job[i].hard,job[i].money);
			}
		}
		int[] res=new int[ability.length];
		for (int i = 0; i < ability.length; i++) {
			Integer key = map.floorKey(ability[i]);
			res[i]=key!=null?map.get(key):0;//null的时候就是没有找到工作
		}
		return res;
	}


	public static void test(){
		// java中有序表是红黑树
		// 有序表可以被：红黑树、avl树、跳表、size-balanced-tree(SB树)实现
		// 不同的实现有什么区别：在使用层次上和性能上看，没区别。只有常数时间的区别。
		// 所有接口的性能O(logN)
		// 设计细节：扩展班最后一节
		// 有序表和哈希表的区别：
		// 哈希表的所有功能有序表一定有，哈希表的key（散乱组织，哈希函数）
		// 有序表所有的key有序组织，比哈希表的功能多。
		// 哈希表所有操作，使用时认为时间复杂度O(1)，有序表所有接口的性能O(logN)
		TreeMap<Integer, String> map = new TreeMap<>();
		map.put(7, "我是7"); // （key,value） 所有的key按顺序组织
		map.put(3, "我是3");
		map.put(9, "我是9");
		map.put(2, "我是2");
		map.put(8, "我是8");
		map.put(5, "我是5");

		map.put(5, "我还是5");// (5, 我还是5)

		System.out.println(map.containsKey(2));
		System.out.println(map.get(7));
		map.remove(9);

		System.out.println(map.firstKey());
		System.out.println(map.lastKey());
		// <= num 离num最近的key
		System.out.println(map.floorKey(6));
		System.out.println(map.floorKey(-2));
		// >= num 离num最近的东西
		System.out.println(map.ceilingKey(6));
		System.out.println(map.ceilingKey(100));

		// 时间复杂度全是O(logN)级别
	}

	public static void main(String[] args) {
		List<Job> list=new LinkedList<>();
		list.add(new Job(1,2));
		list.add(new Job(1,1));
		list.add(new Job(2,6));
		list.add(new Job(1,5));
		list.add(new Job(3,2));
		list.add(new Job(4,8));
		Job[] job=new Job[list.size()];
		int i=0;
		for (Job job1 : list) {
			job[i++]=job1;
		}
		int[] ability=new int[]{1,2,1,3,4,5,3,2,1};
		int[] moneys = getMoneys(job, ability);
		for (int money : moneys)
			System.out.println(money);
	}

}
