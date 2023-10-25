package ClassicInterviewCoding.InterviewCoding07;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Code07_MaxPointsOneLine {

	public static class Point {
		public int x;
		public int y;

		Point() {
			x = 0;
			y = 0;
		}

		Point(int a, int b) {
			x = a;
			y = b;
		}

		@Override
		public String toString() {
			return "{" +
					"" + x +
					", " + y +
					'}';
		}
	}

	public static int maxPoints(Point[] points) {//左
		if (points == null) {
			return 0;
		}
		if (points.length <= 2) {
			return points.length;
		}
		// key : 分子  value : 分母表
		Map<Integer, Map<Integer, Integer>> map = new HashMap<Integer, Map<Integer, Integer>>();
		int result = 0;
		for (int i = 0; i < points.length; i++) {
			map.clear();
			int samePosition = 1;
			int sameX = 0;
			int sameY = 0;
			int line = 0;
			for (int j = i + 1; j < points.length; j++) {
				int x = points[j].x - points[i].x;
				int y = points[j].y - points[i].y;
				if (x == 0 && y == 0) {
					samePosition++;
				} else if (x == 0) {
					sameX++;
				} else if (y == 0) {
					sameY++;
				} else {
					////y是负数返回负数，其它都是正数，
					int gcd = gcd(x, y);
					x /= gcd;//如果x/y结果是负数，则x带负数
					y /= gcd;
					if (!map.containsKey(x)) {
						map.put(x, new HashMap<Integer, Integer>());
					}
					if (!map.get(x).containsKey(y)) {
						map.get(x).put(y, 0);
					}
					map.get(x).put(y, map.get(x).get(y) + 1);
					line = Math.max(line, map.get(x).get(y));
				}
			}
			result = Math.max(result, Math.max(Math.max(sameX, sameY), line) + samePosition);
		}
		return result;
	}

	public static int maxPoints2(Point[] points) {
		if (points==null) return 0;
		if (points.length<=2) return points.length;
		int res=0;
		for (int i = 0; i < points.length; i++) {
			//对每一个点i和后面的点进行组合成直线，看看哪条直线过的最多，更新res
			int same=1;
			int sameX=0;
			int sameY=0;
			int diff_xy=0;//不共x不共y
			HashMap<Integer,HashMap<Integer,Integer>> map=new HashMap<>();//<分子,<分母,点的个数>>,负数放分子
			for (int j=i+1;j<points.length;j++){//和后面的点进行组合
				Point a=points[i],b=points[j];
				if (a.x==b.x&&a.y==b.y){
					same++;
				}else if (a.x==b.x){
					sameX++;
				}else if (a.y==b.y){
					sameY++;
				}else {
					int[] slop = slop(a, b);//斜率y/x，并且保证如果为负数，y带负号
					if (!map.containsKey(slop[0])){
						map.put(slop[0],new HashMap<>());
					}
					if (!map.get(slop[0]).containsKey(slop[1])){
						map.get(slop[0]).put(slop[1],1);
						diff_xy = Math.max(diff_xy, map.get(slop[0]).get(slop[1]));//有put就带上
					}else {
						map.get(slop[0]).put(slop[1],1+map.get(slop[0]).get(slop[1]));
						diff_xy = Math.max(diff_xy, map.get(slop[0]).get(slop[1]));
					}
				}
			}
			res = Math.max(res,Math.max(diff_xy,Math.max(sameX,sameY))+same);
		}
		return res;
	}

	// 保证初始调用的时候，a和b不等于0，b为负数时，gcd为负数
	private static int gcd(int a, int b) {
		return b == 0 ? a : gcd(b, a % b);
	}

	// 保证初始调用的时候，a和b不等于0
	private static int ngcd(int a, int b) {//ab有负数,gcd完了直接去除也可以制造等价的效果
		if (a<0&&b<0) return gcd(-a,-b);
		else if (a > 0 && b > 0) {
			return gcd(a,b);
		} else if (a < 0) {
			return gcd(-a,b);
		}else return gcd(a,-b);
	}

	//返回两个点之间的斜率，斜率为负的话负数在前,-1/3斜率就返回[-1,3],不能给我传共x和共y的，调用者自己注意
	private static int[] slop(Point a,Point b){
		int x=a.x-b.x;
		int y=a.y-b.y;
//		if (x<0&&y<0) {
//			x=-x;
//			y=-y;
//		} else if (x < 0) {
//			x=-x;
//			y=-y;
//		}
//		int gcd = ngcd(x,y);
		int gcd=gcd(y,x);
		return new int[]{y/gcd,x/gcd};
	}


	//for test
	public static Point[] generatePointArr(int size,int max){//产一个大小为size的，值在[-max~+max]随机的数
		int realSize=ran(size);
		Point[] res=new Point[realSize];
		for (int i = 0; i < res.length; i++) {
			int x=oneInTwo()?ran(max):-ran(max);
			int y=oneInTwo()?ran(max):-ran(max);
			res[i]=new Point(x,y);
		}
		return res;
	}

	//for test
	public static boolean oneInTwo(){
		return Math.random()>=0.5F;
	}

	//for test
	public static int ran(int size){//[0,size]随机
		return (int)(Math.random()*(size+1));
	}
	//for test
	public static void test1(){//测试
		int times=10000;
		int size=100;
		int max=20;
		boolean isok=true;
		Point[] f=generatePointArr(ran(size),max);
		for (int i = 0; i < times; i++) {
			Point[] t=generatePointArr(ran(size),max);
			if (maxPoints2(t)!=maxPoints(t)){
				isok=false;
				f=t;
				break;
			}
		}
//		System.out.println(f);
		System.out.println(Arrays.toString(f));
		System.out.println(maxPoints(f));
		System.out.println(maxPoints2(f));
		System.out.println(isok?"success":"fail");
	}

	public static void test2(){//slop
		Point[] points = generatePointArr(20, 4000);
		for (int i = 0; i < points.length; i++) {
			for (int j = 1; j < points.length; j++) {
				if (points[i].x-points[j].x==0||points[i].y-points[j].y==0) break;
				System.out.println(Arrays.toString(slop(points[i],points[j])));
			}
		}
	}

	public static void test3(){
		System.out.println(gcd(-9, -3));
		System.out.println(gcd(9, 3));
		System.out.println(gcd(9, -3));
		System.out.println(gcd(-9, 3));
	}


	public static void main(String[] args) {
		test1();
//		System.out.println(-3/-1);
//		test2();
//		test3();
	}
}
