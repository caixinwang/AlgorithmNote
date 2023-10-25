package BasicLearning.Class10_ReservoirAndbfprt;

public class Code02_ReservoirSampling {

	public static class RandomBox {
		private int[] bag;
		private int N;//袋子的大小
		private int count;//已经过了count个球

		public RandomBox(int capacity) {
			bag = new int[capacity];
			N = capacity;
			count = 0;
		}

		private int rand(int max) {
			return (int) (Math.random() * max) + 1;
		}

		public void add(int num) {
			count++;
			if (count <= N) {
				bag[count - 1] = num;
			} else {
				if (rand(count) <= N) {
					bag[rand(N) - 1] = num;
				}
			}
		}

		public int[] choices() {
			int[] ans = new int[N];
			for (int i = 0; i < N; i++) {
				ans[i] = bag[i];
			}
			return ans;
		}

	}

	public static void main(String[] args) {
		System.out.println("hello");
		int all = 100;//总共100
		int choose = 10;//抽10个
		int testTimes = 500000;//重复500000次100抽10的过程
		int[] counts = new int[all + 1];//统计500000次重复中的中奖次数
		for (int i = 0; i < testTimes; i++) {
			RandomBox box = new RandomBox(choose);//抽10个
			for (int num = 1; num <= all; num++) {
				box.add(num);//总共有100个球
			}
			int[] ans = box.choices();
			for (int j = 0; j < ans.length; j++) {
				counts[ans[j]]++;//把抽取到的放进count中统计
			}
		}

		for (int i = 0; i < counts.length; i++) {
			System.out.println(i + " times : " + counts[i]);
		}

	}
}
