package OtherCoding.InterView;

import java.util.HashMap;


public class Code0001_AddDevideNum {

	public static HashMap<Integer, Integer> map = new HashMap<>();

	public final static int[] arr = { 1, 1_0, 1_00, 1_000, 1_0000, 1_0000_0, 1_0000_00, 1_0000_000, 1_0000_0000 };

	public static int ways(int n) {
		if (map.size()==0){
			process(987654321,0);
		}
		if (map.containsKey(n)) return map.get(n);
		return 0;
	}

	/**
	 * base case里面去对map进行修改
	 * num:123456789,一共有9位，最高位是0位，最低位是8位
	 * @param num 带着一个num跑，而不是一个String，这样到base case里面算的快，常数时间省了
	 * @param index 当前正在在决定index位应该和谁换
	 */
	public static void process(int num, int index) {
		if (index==9) {
			for (int add = 8; add >= 2 ; add--) {
				int num1=num/arr[add];
				int rest=num%arr[add];
				for (int divide=add>>1;divide>=1;divide--){
					int num2=rest/arr[divide];
					int num3=rest%arr[divide];
					if (num2%num3==0){//可以整除
						int ans=num1+num2/num3;
						if (!map.containsKey(ans)){
							map.put(ans,0);
						}
						map.put(ans,map.get(ans)+1);
					}
				}
			}
			return;
		}
		for (int swap=index;swap<9;swap++){
			process(swap(num,index,swap),index+1);//不涉及恢复现场，因为都是新的东西进去的
		}
	}

	/**
	 * 交换，其实就是把原本位置上的先减成0，然后再加上要交换过来的
	 * num- l*arr[L]+r*arr[L] -r*arr[R]+l*arr[R]
	 * @param num 123456789 的第L位和第R位交换
	 * @param L 从0开始标号
	 * @param R -
	 * @return 返回交换后的num变为多少
	 */
	public static int swap(int num, int L, int R) {
		int l=num/arr[L]%10;
		int r=num/arr[R]%10;
		return num-(l-r)*arr[L]-(r-l)*arr[R];
	}

	public static void main(String[] args) {
		int N = 100;
		long start;
		long end;
		// 第一次跑要去生成map，需要100多毫秒，
		// 但是只需要在第一次生成，以后都是直接查询的
		start = System.currentTimeMillis();
		System.out.println(N + "用带分数表示的方法数 : " + ways(N));
		end = System.currentTimeMillis();
		System.out.println("运行了(毫秒) : " + (end - start));
		// 第二次跑map已经在上面生成好了，自然很快
		N = 10000;
		start = System.currentTimeMillis();
		System.out.println(N + "用带分数表示的方法数 : " + ways(N));
		end = System.currentTimeMillis();
		System.out.println("运行了(毫秒) : " + (end - start));
	}


}
