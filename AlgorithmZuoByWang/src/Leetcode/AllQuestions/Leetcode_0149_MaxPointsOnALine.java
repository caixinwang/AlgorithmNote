package Leetcode.AllQuestions;

import java.util.HashMap;

public class Leetcode_0149_MaxPointsOnALine {

	/**
	 * 技巧，分子和分母分开算，同时除以gcd就可以避开一正一负不一样的情况，因为同时除了之后分母永远都是正数，gcd的正负取决于分母
	 * @param points 点不重合
	 * @return 返回一条直线能穿过的最大点数
	 */
	public int maxPoints(int[][] points) {
		if (points==null||points.length==0) return 0;
		if (points.length<=2) return points.length;
		int ans=0;
		for (int i=0;i<points.length-1;i++) {
			int[] point=points[i];
			int x1 = point[0];
			int y1 = point[1];
			int samex=1;
			int samey=1;
			int max=1;//斜率
			HashMap<Integer,HashMap<Integer,Integer>>map=new HashMap<>();//<分子，<分母,次数>>，对应斜率
			for (int j = i+1; j <points.length; j++) {
				int x2=points[j][0];
				int y2=points[j][1];
				if (x2==x1){
					samex++;
				}else if (y1==y2){
					samey++;
				}else {
					int a=y2-y1;//分子
					int b=x2-x1;//分母
					long gcd = gcd(a, b);
					a/=gcd;
					b/=gcd;
					if (!map.containsKey(a)){
						map.put(a,new HashMap<>());
					}
					HashMap<Integer, Integer> tmap = map.get(a);
					if (!tmap.containsKey(b)){
						tmap.put(b,1);//把自己算在这里
					}
					int t=tmap.get(b)+1;
					max = Math.max(max, t);
					tmap.put(b, t);
				}
			}
			ans = Math.max(ans,Math.max(Math.max(samex,samey),max));
		}
		return ans;
	}
	public static long gcd(long a,long b){//求出来的数的符号和b相同，所以b/gcd都是正数。
		return b==0?a:gcd(b,a%b);
	}
	public static void main(String[] args) {
		System.out.println(gcd(6,2));
		System.out.println(gcd(6,-2));
		System.out.println(gcd(-6,2));
		System.out.println(gcd(-6,-2));
	}
}
