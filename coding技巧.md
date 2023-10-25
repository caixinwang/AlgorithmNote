





# Coding技巧

## 观前顾后及考虑特殊性

leetcode10，正则表达式。代码有下面的判断，要考察一个位置pi的后面位置pi+1是不是*，但是这样数组的最后一个位置就不能这样判断了，因为pi+1越界。所以如果代码中有考虑前面的或者后面的这样一些判断，并且第一个数或者最后一个数也是满足条件的，那么就用一个或运算把头或者尾先加上去。

```
if (pi==M-1||p[pi+1]!='*'){//pi后面不是星，pi到头了也算是后面不是星
```

如果你的最后一个数不满足你说的条件。那么就这样写,如此就天然的把最后一个数去掉了，后面的要存在，天然的意思就是pi不能是最后一个。

```
if ( pi+1<M && p[pi+1]!='*'){
```

简单地说，如果最后那个是你想要的，那么就把后面的||上，如果最后那个不是你想要的，那么就加上不要越界的判断即可。



## 暴力递归改动态规划注意事项

### 从哪里开始填写

LT43

看递归，如果依赖的是自己右边的那么就从右边开始填，依赖自己下面的，那么就从下面开始填。概括来就是，依赖哪里就从哪里开始填



```JAVA
public static boolean isMatch(String str, String match) {
		if (str==null||match==null) return true;
		return process(str.toCharArray(),match.toCharArray(),0,0);
	}

	public static boolean process(char[] s,char[] p,int si,int pi){
		int N=s.length,M=p.length;
		if (pi==M){
			return si==N;
		}
		if (si==N){//pi!=M
			return p[pi]=='*'&&process(s,p,si,pi+1);
		}
		if (p[pi]!='*'){
			return (s[si]==p[pi]||p[pi]=='?')&&process(s,p,si+1,pi+1);
		}
		for (int unsolve = si; unsolve <= s.length; unsolve++) {//unsolve:第一个没有解决的位置，前面的[si...unsolve-1]都解决了
			if (process(s,p,unsolve,pi+1)) return true;
		}
		return false;
	}

	//一个格子依赖右下角，所以从下往上，从右往左填即可。
	public static boolean isMatch2(String str, String match) {
		if (str==null||match==null) return true;
		char[] s = str.toCharArray();
		char[] p = match.toCharArray();
		int N=s.length,M=p.length;
		boolean[][]dp=new boolean[N+1][M+1];
		dp[N][M]=true;
		for (int pi=M-1;pi>=0;pi--){
			dp[N][pi]=p[pi]=='*'&&dp[N][pi+1];
		}

		for (int si=N-1;si>=0;si--){
			out: //如果递归里面有break，这里就需要continue，退出继续填表
			for (int pi=M-1;pi>=0;pi--){
				if (p[pi]!='*'){
					dp[si][pi]= (s[si]==p[pi]||p[pi]=='?')&&dp[si+1][pi+1];
					continue out;//return下面要加上continue
				}
				//dp[si][pi]=(dp[unsolve][pi+1]||....||...)
				for (int unsolve = si; unsolve <= s.length; unsolve++) {//unsolve:第一个没有解决的位置，前面的[si...unsolve-1]都解决了
					if (dp[unsolve][pi+1]) {
						dp[si][pi] = true;
						continue out;
					}
				}
				dp[si][pi] = false;
			}
		}
		return dp[0][0];
	}
```



### if里面return的语句

还是LT10，正则表达式。这是此题的暴力递归。前面两个if里面return我们不管，我们当做dp的初始化。看后面的if，如果这里没有else做一个隔断，那么就需要加一个continue。

```JAVA
private static boolean process(char[] s, int si, char[] p, int pi) {
   int N=s.length,M=p.length;
   if (pi==M){
      return si==N;
   }
   if (si==N){//从上面下来，潜台词就是pi不是结尾
      return pi+1<M&&p[pi+1]=='*'&&process(s,si,p,pi+2);
   }
   //pi和si都不是结尾
   if (pi==M-1||p[pi+1]!='*'){//pi后面不是星，pi到头了也算是后面不是星
      return (s[si]==p[pi]||p[pi]=='.')&&process(s,si+1,p,pi+1);
   }
   boolean res=process(s,si,p,pi+2);// c* 组合拳匹配了0个
   while (si<N&&(p[pi]==s[si]||p[pi]=='.')){//si有动你就要判断有没有越界
       res|=process(s,++si,p,pi+2);
   }
   return res;
}
```

这需要加一个continue，否则改动态规划的时候，上面走完还会走下面，这是不对的，因为在暴力递归中，由于return直接不会走下面。但是在暴力递归中，我们把return都变成了赋值语句，如果我们不自己去控制，它就会往下走，所以要格外注意。

当然，如果我们暴力递归在编写的时候有使用else，那么改成动态规划的时候就比较自然，不需要自己加continue。

```JAVA
public static boolean isMatch2(String target, String expression) {//改动态规划
		if (!isValid(target,expression)) return false;
		int N = target.length();
		int M = expression.length();
		char[] s = target.toCharArray();
		char[] p = expression.toCharArray();
		boolean[][] dp=new boolean[N+1][M+1];
		dp[N][M]=true;//最后一列
		for (int pi=M-1;pi>=0;pi--){//最后一行
			dp[N][pi]=pi+1<M&&p[pi+1]=='*'&&dp[N][pi+2];
		}
		for (int si=N-1;si>=0;si--){
			for (int pi=M-1;pi>=0;pi--){
				if (pi==M-1||p[pi+1]!='*'){//pi后面不是星，pi到头了也算是后面不是星
					dp[si][pi]= (s[si]==p[pi]||p[pi]=='.')&&dp[si+1][pi+1];
					continue;//没有else这里就需要continue
				}
			    boolean res=dp[si][pi+2] ;// c* 组合拳匹配了0个
				int t=si;//si要动，用t去动，不然for循环就乱套了
				while (t<N&&(p[pi]==s[t]||p[pi]=='.')){//si有动你就要判断有没有越界
					res|=dp[++t][pi+2] ;
				}
				dp[si][pi]= res;
			}
		}
		return dp[0][0];
	}
```

再来看LT43的一段代码

这里的return语句是在枚举中的，所以你如果直接continue不能直接回到第二层for中，所以就手动加了一个out，可以直接continue到这个out的地方。但是实际上这里不用out也可以，直接break掉就行，因为后面设置完false就没有逻辑了。

```java
public static boolean isMatch(String str, String match) {
   if (str==null||match==null) return true;
   return process(str.toCharArray(),match.toCharArray(),0,0);
}

public static boolean process(char[] s,char[] p,int si,int pi){
   int N=s.length,M=p.length;
   if (pi==M){
      return si==N;
   }
   if (si==N){//pi!=M
      return p[pi]=='*'&&process(s,p,si,pi+1);
   }
   if (p[pi]!='*'){
      return (s[si]==p[pi]||p[pi]=='?')&&process(s,p,si+1,pi+1);
   }
   for (int unsolve = si; unsolve <= s.length; unsolve++) {//unsolve:第一个没有解决的位置，前面的[si...unsolve-1]都解决了
      if (process(s,p,unsolve,pi+1)) return true;
   }
   return false;
}

//一个格子依赖右下角，所以从下往上，从右往左填即可。
public static boolean isMatch2(String str, String match) {
   if (str==null||match==null) return true;
   char[] s = str.toCharArray();
   char[] p = match.toCharArray();
   int N=s.length,M=p.length;
   boolean[][]dp=new boolean[N+1][M+1];
   dp[N][M]=true;
   for (int pi=M-1;pi>=0;pi--){
      dp[N][pi]=p[pi]=='*'&&dp[N][pi+1];
   }

   for (int si=N-1;si>=0;si--){
      out: //如果递归里面有break，这里就需要continue，退出继续填表
      for (int pi=M-1;pi>=0;pi--){
         if (p[pi]!='*'){
            dp[si][pi]= (s[si]==p[pi]||p[pi]=='?')&&dp[si+1][pi+1];
            continue out;//return下面要加上continue
         }
         //dp[si][pi]=(dp[unsolve][pi+1]||....||...)
         for (int unsolve = si; unsolve <= s.length; unsolve++) {//unsolve:第一个没有解决的位置，前面的[si...unsolve-1]都解决了
            if (dp[unsolve][pi+1]) {
               dp[si][pi] = true;
               continue out;
            }
         }
         dp[si][pi] = false;
      }
   }
   return dp[0][0];
}
```

### 是否有对参数的移动

还是这段代码。观察到下面的while循环中si作为参数是有移动的，所以在改动态规划的时候需要注意。

```JAVA
private static boolean process(char[] s, int si, char[] p, int pi) {
   int N=s.length,M=p.length;
   if (pi==M){
      return si==N;
   }
   if (si==N){//从上面下来，潜台词就是pi不是结尾
      return pi+1<M&&p[pi+1]=='*'&&process(s,si,p,pi+2);
   }
   //pi和si都不是结尾
   if (pi==M-1||p[pi+1]!='*'){//pi后面不是星，pi到头了也算是后面不是星
      return (s[si]==p[pi]||p[pi]=='.')&&process(s,si+1,p,pi+1);
   }
   boolean res=process(s,si,p,pi+2);// c* 组合拳匹配了0个
   while (si<N&&(p[pi]==s[si]||p[pi]=='.')){//si有动你就要判断有没有越界
       res|=process(s,++si,p,pi+2);
   }
   return res;
}
```

我们用一个变量t去代替si本身的移动。否则si会因为移动而导致for循环乱套。

```JAVA
public static boolean isMatch2(String target, String expression) {//改动态规划
		if (!isValid(target,expression)) return false;
		int N = target.length();
		int M = expression.length();
		char[] s = target.toCharArray();
		char[] p = expression.toCharArray();
		boolean[][] dp=new boolean[N+1][M+1];
		dp[N][M]=true;//最后一列
		for (int pi=M-1;pi>=0;pi--){//最后一行
			dp[N][pi]=pi+1<M&&p[pi+1]=='*'&&dp[N][pi+2];
		}
		for (int si=N-1;si>=0;si--){
			for (int pi=M-1;pi>=0;pi--){
				if (pi==M-1||p[pi+1]!='*'){//pi后面不是星，pi到头了也算是后面不是星
					dp[si][pi]= (s[si]==p[pi]||p[pi]=='.')&&dp[si+1][pi+1];
					continue;//没有else这里就需要continue
				}
			    boolean res=dp[si][pi+2] ;// c* 组合拳匹配了0个
				int t=si;//si要动，用t去动，不然for循环就乱套了
				while (t<N&&(p[pi]==s[t]||p[pi]=='.')){//si有动你就要判断有没有越界
					res|=dp[++t][pi+2] ;
				}
				dp[si][pi]= res;
			}
		}
		return dp[0][0];
	}
```

### 省掉枚举行为

直接看，方法2中，枚举行为等价于dp[si] [pi]一路异或自己右边的一列往下，所以自己就依赖自己下面的格子，以及自己右边的格子。下面的格子肯定不越界，因为我们已经初始化了最后一行和最后一列。

总结：往哪枚举就依赖哪边的格子。例如从上往下枚举，那么就依赖自己下面的格子，当然还要补上和自己水平的格子，因为下面的格子够不到。

```JAVA
	public static boolean isMatch2(String str, String match) {
		if (str==null||match==null) return true;
		char[] s = str.toCharArray();
		char[] p = match.toCharArray();
		int N=s.length,M=p.length;
		boolean[][]dp=new boolean[N+1][M+1];
		dp[N][M]=true;
		for (int pi=M-1;pi>=0;pi--){
			dp[N][pi]=p[pi]=='*'&&dp[N][pi+1];
		}

		for (int si=N-1;si>=0;si--){
			out: //如果递归里面有break，这里就需要continue，退出继续填表
			for (int pi=M-1;pi>=0;pi--){
				if (p[pi]!='*'){
					dp[si][pi]= (s[si]==p[pi]||p[pi]=='?')&&dp[si+1][pi+1];
					continue out;//return下面要加上continue
				}
				//dp[si][pi]=(dp[unsolve][pi+1]||....||...)
				for (int unsolve = si; unsolve <= s.length; unsolve++) {//unsolve:第一个没有解决的位置，前面的[si...unsolve-1]都解决了
					if (dp[unsolve][pi+1]) {
						dp[si][pi] = true;
						continue out;
					}
				}
				dp[si][pi] = false;
			}
		}
		return dp[0][0];
	}

	//斜率优化，枚举的时候一个格子依赖自己右边往下一列，所以找自己下面的格子帮自己省事，补上自己右边的格子即可
	public static boolean isMatch3(String str, String match) {
		if (str==null||match==null) return true;
		char[] s = str.toCharArray();
		char[] p = match.toCharArray();
		int N=s.length,M=p.length;
		boolean[][]dp=new boolean[N+1][M+1];
		dp[N][M]=true;
		for (int pi=M-1;pi>=0;pi--){
			dp[N][pi]=p[pi]=='*'&&dp[N][pi+1];
		}

		for (int si=N-1;si>=0;si--){
			out: //如果递归里面有break，这里就需要continue，退出继续填表
			for (int pi=M-1;pi>=0;pi--){
				if (p[pi]!='*'){
					dp[si][pi]= (s[si]==p[pi]||p[pi]=='?')&&dp[si+1][pi+1];
					continue out;//return下面要加上continue
				}
				//dp[si][pi]=(dp[unsolve][pi+1]||....||...),
				dp[si][pi]=dp[si][pi+1]||dp[si+1][pi];
			}
		}
		return dp[0][0];
	}
```

当然上面这个例子中，每一个位置天然的都依赖自己右边的一列到底，并且每次(i,j+1)及其往下都是有东西的，也就是说，每一个位置都可以去自己的下面寻求省掉枚举。

但是有的题目枚举行为并不是到底的，并且有的题目中也不是每一个位置都会有斜率优化的机会，这种情况就需要判断了

---

LT55。这个题目中，我们观察到枚举行为发生在（i+1.....i+x），也就是，如果i位置的nums为0，那么就不会有枚举的机会，所以省掉枚举行为的时候外面需要判断nums不为0。并且我们发现，每一个位置不都是枚举到底的，所以能不能枚举是不一定的，这题是可以枚举的。因为一个dp[i]如果有枚举,依赖dp[i+1]||dp[i+2]||dp[i+3]||dp[i+x]。dp[i+1]=dp[i+2]||dp[i+3]||...||dp[i+y]。如果这里y大于等于x，那么后面的dp[i+2]||...||dp[i+x]会全部都被dp[i+1]吃掉，答案就是dp[i+1]。如果y<x，那么只能吃掉前面一部分，所以还需要去||dp[i+y+1]||...||dp[i+x]。

这题只是刚好发现可以化简消掉

```JAVA
public static boolean canJump(int[] nums) {
        if (nums == null || nums.length <= 1) return true;
        boolean[] dp = new boolean[nums.length];
        dp[nums.length - 1] = true;
        for (int i = nums.length - 2; i >= 0; i--) {
            for (int step = 1; step <= nums[i] && i + step < nums.length; step++) {
                if (dp[i + step]) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[0];
    }

	public static boolean canJump2(int[] nums) {//省枚举
        if (nums == null || nums.length <= 1) return true;
        boolean[] dp = new boolean[nums.length];
        dp[nums.length - 1] = true;
        out:
        for (int i = nums.length - 2; i >= 0; i--) {
            if (nums[i] > 0) {//至少能跳一步才能借用后面的
                dp[i] = dp[i + 1];
                if (nums[i] > 1) {//至少能跳两步才开始有枚举行为，发现枚举行为会被前面的这个dp[i+1]吃掉
                    for (int step = 1 + nums[i + 1]; step <= nums[i] && i + step < nums.length; step++) {
                        if (dp[i + step]) {
                            dp[i] = true;
                            continue out;
                        }
                    }
                }
            }
        }
        return dp[0];
    }
```

---

LT45就没有这么幸运了。

这段代码，就不能够进行斜率优化了。因为不是枚举到底的，并且发现是套了一个取最小的函数，dp[i-1]存的信息就很乱了！

```JAVA
 	public int jump(int[] nums) {
        if (nums == null || nums.length <= 1) return 0;
        int[] dp = new int[nums.length];
        Arrays.fill(dp,-1);//-1代表跳不到
        dp[nums.length - 1] = 0;
        for (int i = nums.length - 2; i >= 0; i--) {
            if (nums[i]==0) continue;
            for (int step = 1; step <= nums[i] && i + step < nums.length; step++) {
                if (dp[i + step]!=-1) {
                    dp[i] = Math.min(dp[i]==-1?Integer.MAX_VALUE:dp[i], dp[i+step]+1);
                }
            }
        }
        return dp[0];
    }
```

---

LT10

这里就不是所有的位置都能枚举。能枚举的条件是至少能配一个。也就是要有这个条件`(p[pi]==s[si]||p[pi]=='.'`。

意思就是你要看一下，是不是每个位置都有依赖dp[i] [j+1]的格子，如果不是的话要记得加上条件，只去把有枚举的位置的枚举省了。

```JAVA
public static boolean isMatch3(String target, String expression) {//省去枚举行为
		if (!isValid(target,expression)) return false;
		int N = target.length();
		int M = expression.length();
		char[] s = target.toCharArray();
		char[] p = expression.toCharArray();
		boolean[][] dp=new boolean[N+1][M+1];
		dp[N][M]=true;//最后一列
		for (int pi=M-1;pi>=0;pi--){//最后一行
			dp[N][pi]=pi+1<M&&p[pi+1]=='*'&&dp[N][pi+2];
		}
		for (int si=N-1;si>=0;si--){
			for (int pi=M-1;pi>=0;pi--){
				if (pi==M-1||p[pi+1]!='*'){//pi后面不是星，pi到头了也算是后面不是星
					dp[si][pi]= (s[si]==p[pi]||p[pi]=='.')&&dp[si+1][pi+1];
				}else {//(p[pi]==s[si]||p[pi]=='.')条件带上，因为只有这个条件满足才会进入到枚举行为中
					dp[si][pi]=dp[si][pi+2]||((p[pi]==s[si]||p[pi]=='.')&&dp[si+1][pi]) ;// c* 组合拳匹配了0个
				}
			}
		}
		return dp[0][0];
	}
```



---

股票问题4

看下面的枚举行为，依赖的是自己左边一列上面的格子。所以看看自己上面的格子能否给自己提供帮助，因为dp[i] [j]后面加上了一个arr[i]，所以不能直接使用dp[i-1] [j]来省掉枚举，需要从上面的格子迭代下来帮助。

```java
for (int i = 1; i < arr.length; i++) {
   for (int j = 1; j <= k; j++) {
      dp[i][j] = dp[i - 1][j];//初值为i位置不参与交易
      int max = Integer.MIN_VALUE;//i位置参与交易的最大获益
      for (int buy = i; buy >= 0; buy--) {//枚举买入点
         max = Math.max(max, dp[buy][j - 1] - arr[buy]);
      }
      dp[i][j] = Math.max(max + arr[i], dp[i][j]);
   }
}
```

观察到，此题i从1开始，所以依赖0行的格子，初始化为0行的信息。

```java
for (int j = 1; j <= k; j++) {
   int max = dp[0][j-1]-arr[0];//不能初始化为系统最小
   for (int i = 1; i < arr.length; i++) {
      dp[i][j] = dp[i - 1][j];
      max = Math.max(max,dp[i][j-1]-arr[i]);
      dp[i][j] = Math.max(max + arr[i], dp[i][j]);
   }
}
```



#### 总结

省掉枚举需要注意的点

1. 如果是枚举到底的话，一般是可以斜率优化的
2. 如果不是枚举到底，列出关系，看看能不能化简，不能化简就不能优化了
3. 要看看是不是每一个位置都可以进行斜率优化，不是的话加上一些条件判断，让这个位置达到可以枚举的条件。



## 递归

### 返回值、恢复现场

n皇后问题。题意只要收集答案的个数，所以要让每一个递归充分展开，并且设置一个返回值类型int。n皇后问题，要填0~n-1，所以到n的时候是bas e case。

这里不需要恢复现场，因为record[index]被赋值了不会影响isOk(record, index, i)的判断。

```java
private static int num1(int n) {//n皇后
    if (n < 1) {
        return 0;
    }
    int[] record = new int[n];
    return process1(n, 0, record);
}

private static int process1(int n, int index, int[] record) {
    if (index == n) {//顶层递归收集底层递归的这些1
        return 1;
    }
    int sum = 0;
    for (int i = 0; i < n; i++) {//index行的每一列都下去试试看。
        if (isOk(record, index, i)) {
            record[index] = i;
            sum += process1(n, index + 1, record);
        }
    }
    return sum;
}

private static boolean isOk(int[] record, int index, int colum) {
    for (int i = 0; i < index; i++) {
        if (record[i] == colum || index - i == Math.abs(colum - record[i])) {//判断是否共列和共斜线
            return false;
        }
    }
    return true;
}
```

解数独LT37。

这题只需要在源board上进行修改，修改成答案。所以需要设置成boolean来防止进入别的递归里面把答案改乱了，所以我们在里面，一旦process某一个分支返回true了，立马return，不会进入到别的分支。这和n皇后问题不一样，n皇后问题需要把所有的可能分支跑完，找到所有的解。而这题只有一个解，所以找到了之后立即返回。

这题需要恢复现场。row、col、grid的状态的改变会影响条件if的判断！并且board的修改也会影响条件的判断，所以都需要恢复现场。

```java
public void solveSudoku(char[][] board) {
    boolean[][] row = new boolean[9][9];
    boolean[][] col = new boolean[9][9];
    boolean[][] grid = new boolean[9][9];
    for (int i = 0; i < board.length; i++) {
        for (int j = 0; j < board[0].length; j++) {
            if (board[i][j] != '.') {
                int k = (i / 3) * 3 + (j / 3);
                int index = board[i][j] - '1';
                row[i][index] = true;
                col[j][index] = true;
                grid[k][index] = true;
            }
        }
    }
    process(board, 0, 0, row, col, grid);
}


private boolean process(char[][] board, int i, int j, boolean[][] row, boolean[][] col, boolean[][] grid) {
    if (i == 9) return true;
    if (board[i][j] == '.') {
        for (char num = '1'; num <= '9'; num++) {
            int index = num - '1';
            int k = (i / 3) * 3 + (j / 3);
            if (!row[i][index] && !col[j][index] && !grid[k][index]) {
                board[i][j] = num;
                row[i][index] = true;
                col[j][index] = true;
                grid[k][index] = true;
                if (process(board, j == 8 ? i + 1 : i, j == 8 ? 0 : j + 1, row, col, grid)) 						return true;
                row[i][index] = false;
                col[j][index] = false;
                grid[k][index] = false;
                board[i][j] = '.';
            }
        }
        return false;//说明这个空格子说明都填不了。因为如果填了，会在for循环中就return了
    } else {
        return process(board, j == 8 ? i + 1 : i, j == 8 ? 0 : j + 1, row, col, grid);
    }
}
```

LT17。此题的返回值void即可，因为题目没有让你统计几种，也没有只叫你返回一种，也没有叫你判断一下有没有解。那么你就是设置为void去跑完所有的分支然后收集答案。除了看看是否影响条件的判断，还需要看看是否对添加答案产生影响。此题首先path的改变不影响任何的条件判断。但是注意，如果path是一个链表类型的话，就不是直接覆盖了，如果不是直接覆盖的，也需要去恢复现场。

```java
public List<String> letterCombinations(String digits) {
   if (digits==null||digits.length()==0) return new LinkedList<>();
   List<String> res=new LinkedList<>();
   char[][] map=new char[][]{//[10][] 只会按下2~9所以0~1弃而不用
      {},//0
      {},//1
      {'a','b','c'},//2
      {'d','e','f'},//3
      {'g','h','i'},//4
      {'j','k','l'},//5
      {'m','n','o'},//6
      {'p','q','r','s'},//7
      {'t','u','v'},//8
      {'w','x','y','z'},//9
   };
   char[] str = digits.toCharArray();
   char[] path=new char[str.length];
   process(str,0,path,res,map);
   return res;
}

private void process(char[] tail, int index, char[] path, List<String> res, char[][] map) {
   if (index==tail.length) {
      res.add(String.valueOf(path));
      return;
   }
   char c=tail[index];//当前拨了哪个数字
   char[] chars = map[c - '0'];//这个数字能对应的字符集拿出来
   for (char t : chars) {//当前位置index要放什么字符，每个都去试
      path[index] = t;//不需要恢复现场，因为赋值行为本身就会覆盖，等价于恢复现场
      process(tail, index + 1, path, res, map);
   }
}
```

#### 总结

返回值：

1. 题目问你总共有几种可能的解，就设置为int类型，这样可以在base case的时候返回1。
2. 题目叫你判断没有可能的解，或者是在源数组上修改成答案叫你直接返回，那么你就用boolean类型，在中间进行截断，不让别的分支进行跑下去破坏答案
3. 题目只叫你收集所有可能的答案，那么就直接一个void类型，在base case的时候收录到res中即可，去跑完所有的分支。
4. 题目只叫你收集所有可能的答案，但是如果你可以利用一个返回值进行剪枝，那么就可以设计为int。



恢复现场：

下面几点，有任意一点影响到了，都要去恢复现场

1. 有没有影响到条件判断。
2. 有没有影响到传进来的固定参数，有时候我们会在固定参数上做交换，这时候也需要恢复现场。
3. 有没有影响到res答案的收集。如果是数组类型直接覆盖的话那么就不需要恢复，如果是链表类型，就需要恢复。
4. 拿不准就去恢复现场，肯定没错。



### 加入答案

加入答案的时候需要自己new一个再加到ans里面。否则直接把path加进去最后都会被改掉。

```java
public static List<List<Integer>> subsets2(int[] nums) {
   List<List<Integer>> ans=new ArrayList<>();
   f2(nums,0,new LinkedList<Integer>(),ans);
   return ans;
}

/**
 *
 * @param nums 数组
 * @param index 从index往后随意去选
 * @param path 路径
 * @param ans 选满了rest个的path加入ans中
 */
private static void f2(int[] nums, int index, LinkedList<Integer> path,List<List<Integer>> ans) {
   if (index==nums.length){
      ans.add(new LinkedList<>(path));
      return;
   }
   f2(nums,index+1,path,ans);//不选index位置的数
   path.addLast(nums[index]);
   f2(nums,index+1,path,ans);//选了index位置的
   path.pollLast();
}
```



### 剪枝

不要每次都留到最后一步再去判断，你看看能不能设计参数进行剪枝

可以进行这样的剪枝，如果满足了条件就不继续递归了。一般来说我们都是等到index==n了之后才验证，这里是我们中途验证通过了，就不进入递归。这样写的话，需要自己注意控制index<n才进入递归。

```
    public static void dfs(int[] rest,int n,int index,int fix){
        if(canFix(rest,fix)){
            int len=fixToLen(fix,n);
            if(len<min){
                min=len;
                best=fix;
            }
        }else if(index<n){
            dfs(rest,n,index+1,fix|(1<<index));
            dfs(rest,n,index+1,fix);
        }   
    }
```





### 参数设计

尽量让一个简单类型的参数进来，如果你让一个复杂参数进到最后才去转化，这样常数时间会很大。





## 移动的艺术

### 找第一个你想要的位置

这段是两和问题的代码

```java
	public static List<List<Integer>> twoSum(int[] nums,int left,int right,int k){
		List<List<Integer>> res=new LinkedList<>();
		int l=left,r= right;
		while(l<r){//撞上就没有二元组了
			if (nums[l]+nums[r]<k){
				while(++l<right&&nums[l]==nums[l-1]);//不等于前面了或者是越界了
			}else if (nums[l]+nums[r]>k){
				while(--r>left&&=nums[r+1]);//不等于后面了或者是越界了
			}else {// ==
				List<Integer> list=new LinkedList<>();
				list.add(nums[l]);
				list.add(nums[r]);
				res.add(list);
				while(++l<r&&nums[l]==nums[l-1]);//不等于前面了或者是和r撞上了
				while(l<--r&&nums[r]==nums[r+1]);//不等于后面了或者是和r撞上了
			}
		}
		return res;
	}
```

我们主要看这两段代码。

(nums[l]!=nums[l-1])是第一个你想要的位置。

```
			while(++l<right&&nums[l]==nums[l-1]);//不等于前面了或者是越界了
			while(--r>left&&nums[r]==nums[r+1]);//不等于后面了或者是越界了
```

正常来说，我们就是直接l++,r--即可。但是这题，我们移动到的下一个位置不能和前面的位置相等，所以我们用一个空体的while来解决这个问题，它会一直移动到越界或者一个合适的位置。越界会在外循环while判断完成

这样就来到了第一个达标的位置。

这题交叉了之后直接退出，不需要做额外的操作。

---

快排的partition

```JAVA
    private static int partition2(int[] arr,int l,int r){//由于是在快排中调用，所以r>l，即至少两个元素
        int num=arr[l];//切分元素
        int p1=l;//从while出来的时候都是小于等于num的，同时p1指向的是>=num的元素。
        int p2=r+1;//从while出来的时候p2+1~r都是大于等于num的，同时p2指向的是<=num的元素。
//        while(true){
//            while(arr[++p1]<num) if (p1==r) break;//防止越界。如果if条件成立，说明数组中所有的数都比num小
//            while(arr[--p2]>num) if (p2==l) break;//这里的if冗余，因为arr[l]!>arr[l]
//            if (p1>=p2){//此时arr[p2+1...r]>=num,arr[l,p2-1]<=num，因为[l,p2-1]范围比[l,p1-1]小，后者满足<=num
//                swap(arr,l,p2);//进行了尾部的定制，使用while(true),当p1>=p2的时候是p1和p2交换了。
//                break;
//            }else {
//                swap(arr,p1,p2);
//            }
//        }

        while(p1<p2){//上面那段代码和下面这段代码都ok
            while(++p1<r&&arr[p1]<num);
            while(--p2>l&&arr[p2]>num);
            if (p1<p2)swap(arr,p1,p2);//移动完自己去判断外循环条件
            else swap(arr,l,p2);//在while里面的最后一次循环进这个分支
        }
        return p2;
    }
```

快排指针移动完了之后还需要对指针交叉进行一些额外的操作。指针交叉的操作放在了else里面。

```JAVA
	 while(p1<p2){//上面那段代码和下面这段代码都ok
            while(++p1<r&&arr[p1]<num);
            while(--p2>l&&arr[p2]>num);
            if (p1<p2)swap(arr,p1,p2);//移动完自己去判断外循环条件
            else swap(arr,l,p2);//在while里面的最后一次循环进这个分支
        }
```

这题需要交叉了之后立马进行对应的操作，所以不能使用for循环，因为使用for我们不得不去手动的位移p1和p2，这样会使得交叉的逻辑乱掉。

进入while前p1的含义是跳过此处p1的检查，去检查p1往后的，刚从while出来之后，p1的含义就是第一个不合规的位置。

进入for之前p1的含义是不确定的，也就是p1不能跳过，从for出来之后含义和while的才一样。

很显然这里swap(arr,p1,p2);之后，我们显然希望p1和p2的检查可以直接跳过。for循环不能跳过，你就只能手动跳过，但是手动去跳过会影响下次进入外层while循环的逻辑。

所以如果有外层循环，需要考虑交叉，希望这个交叉的处理是及时的，那么就使用while，不要使用for。



---

LT08

```JAVA
public static int myAtoi(String s) {
      if (s==null||s.length()==0) return 0;
      char[] str = s.toCharArray();
      int N= str.length;
//    int fi=-1;
//    while (++fi<N&&(str[fi]<'0'||str[fi]>'9'));//fi跳到第一个为数字的地方
      int fi=0;
      for (;fi<N&&(str[fi]<'0'||str[fi]>'9');fi++) ;
      if (fi==N) return 0;//压根没有数，不合法
      for (int i = 0; i < fi; i++) {//检查从第一个字符到fi-1处的字符只能是空格，除了fi-1能是+-，其它位置只要出现了非空格就是非法的
         //如果i位置不为空格大概率是不合法，但是有一个例外在i-1位置是+-，让这个例外通过&&!(condition)跳出这个if，继续下面的逻辑
         if (str[i]!=' '&&!(i==fi-1&&(str[i]=='-'||str[i]=='+'))) return 0;
      }
      boolean isNeg=fi-1>=0&&str[fi-1]=='-';//只有fi-1位置是'-'结果才是负数，其它' '或者'+'都为正数
      int res=0;
      int minq=Integer.MIN_VALUE/10;//-214748364 商
      int minr=Integer.MIN_VALUE%10;//-8 余数
      for (int i = fi; i < N&&(str[i]>='0'&&str[i]<='9'); i++) {
         if ((res<minq)||(res==minq&&'0'-str[i]<minr)) {//判断越界
            return isNeg?Integer.MIN_VALUE:Integer.MAX_VALUE;
         }
         res=res*10+('0'-str[i]);
      }
      if (res==Integer.MIN_VALUE&&!isNeg) return Integer.MAX_VALUE;//防止“+2147483648”这样的测试用例，直接取相反数会溢出
      return isNeg?res:-res;
   }
```

看这段。上下两种写法都是来到第一个达标的位置。用while的需要将位置提前移出一个，也就是设置为-1，用for的话就可以设置为0。

```JAVA
//    int fi=-1;
//    while (++fi<N&&(str[fi]<'0'||str[fi]>'9'));//fi跳到第一个为数字的地方

      int fi=0;
      for (;fi<N&&(str[fi]<'0'||str[fi]>'9');fi++) ;
```

但是要注意，用for循环写的话，对交叉条件的处理是延迟的，如果需要处理交叉，不要使用for循环。

这里没有外层循环，也没交叉，所以用for也可以。



上面的都是来到第一个不达标的位置。如果你要来到最后一个达标的位置呢？

```JAVA
  int fi=-1;
  while (fi+1<N&&(str[fi+1]<'0'||str[fi+1]>'9')) fi++;
```

---

LT38

```JAVA
public static String countAndSay(int n) {
   return process(n);
}

private static String process(int n) {
   if (n==1) return "1";
   char[] str = process(n - 1).toCharArray();
   StringBuilder builder=new StringBuilder();
   for (int i = 0,p=i; i < str.length; i=p) {
      char num = str[i];
      while(++p< str.length&&str[p]==str[p-1]);//来到第一个不等于i位置的字符的位置,(p-i)就是长度
      builder.append(p-i).append(num);
   }
   return builder.toString();
}
```

while的特性就是跳过自己不检查，这里显然符合这个意思

```JAVA
      while(++p< str.length&&str[p]==str[p-1]);//来到第一个不等于i位置的字符的位置,(p-i)就是长度
```



---

```java
public String minWindow(String big, String small) {//滑动窗口
    char[] s = big.toCharArray();
    char[] t = small.toCharArray();
    HashMap<Character, Integer> map = new HashMap<>();
    for (int i = 0; i < t.length; i++) {
        if (!map.containsKey(t[i])) map.put(t[i], 0);
        map.put(t[i], map.get(t[i]) + 1);
    }
    int l = 0, r = 0;//[l,r)
    int start = 0, end = 0;
    int ans = Integer.MAX_VALUE;
    while (r < s.length) {
            while (r < s.length&&!isok(map)) {
                if (map.containsKey(s[r])) map.put(s[r], map.get(s[r]) - 1);
                r++;
            }
        if (isok(map)){
            while(l < s.length&&isok(map)){
                if (map.containsKey(s[l])) map.put(s[l], map.get(s[l]) + 1);
                l++;
            }
            if (r - l < ans) {
                ans = r - l+1;
                start = l-1;
                end = r;
            }
        }
    }
    return big.substring(start, end);
}

public String minWindow2(String big, String small) {//滑动窗口
    char[] s = big.toCharArray();
    char[] t = small.toCharArray();
    HashMap<Character, Integer> map = new HashMap<>();
    for (int i = 0; i < t.length; i++) {
        if (!map.containsKey(t[i])) map.put(t[i], 0);
        map.put(t[i], map.get(t[i]) + 1);
    }
    int l = 0, r = -1;
    int start = 0, end = 0;
    int ans = Integer.MAX_VALUE;
    while (r < s.length) {
        while(++r<s.length&&!isok(map)) if (map.containsKey(s[r])) map.put(s[r], map.get(s[r]) - 1);
        if (isok(map)){
            while(l < s.length&&isok(map)){
                if (map.containsKey(s[l])) map.put(s[l], map.get(s[l]) + 1);
                l++;
            }
            if (r - l < ans) {
                ans = r - l+1;
                start = l-1;
                end = r;
            }
            r--;
        }
    }
    return big.substring(start, end);
}
```

上面代码中，还加上了对不达标位置的操作，如果是这样的话，最好是在while里面去移动，因为这样不会跳过r所在的位置没有被操作。放里面和放外面都是走到第一个达标的位置，区别就是赋初值的时候，++r的while初值需要在检查位置的外面，而放在里面++的while初值则是设置在里面。并且如果涉及多次移动，也就是while套while，放在条件里面的移动就容易出错了。

下面这一段，一开始的时候确实是维持住含义的。但是r此时在第一个成功的位置。后续再进来的时候r需要再--，否则这个位置会被漏掉。而下面这个下次再次启动的时候就不会漏掉r位置，是直接从r位置开始的。

注意了，如果r位置的东西不能跳过检查，那么在while里面移动

```JAVA
int l = 0, r = -1;
while(++r<s.length&&!isok(map)) if (map.containsKey(s[r])) map.put(s[r], map.get(s[r]) - 1);

int l = 0, r = 0;
while (r < s.length&&!isok(map)) {
    if (map.containsKey(s[r])) map.put(s[r], map.get(s[r]) - 1);
    r++;
}
```





#### 总结

condition是你要找的第一个位置

```java
while(++p<str.len&&!(condition(p)));
```

例如你要找第一个不等于9的位置。如果while循环体里面加了东西，那么就是可以对之前的所有不达标的位置做操作。

```JAVA
while(--i>=0&&!(digits[i]!=9)) digits[i]=0;
```

++或者--放到了while里面的话默认跳过了最开始i所在的位置，如果while后面有操作的话，i一开始的位置也不会被操作。

如果你希望i位置也被检查，也被操作，那么就把移动放在while体里面。也就是想跳过当前的位置就把++和--放到条件里面，否则就放到循环里面。







### 用while还是for

LT45

用for的话i每次都在移动，while是自己控制i移动。

```java
    public int jump2(int[] nums) {
        if (nums == null || nums.length <= 1) return 0;
        int step=0;//当前是第几步
        int R=0;//当前步数能到达的最远边界
        int nextR=0;//下一步能到达的最远边界
        int i=0;
//        for (; i < nums.length i++) {//保证能跳到，for每步都动，while可以不动
//            if (i<=R){
//                nextR = Math.max(nextR,i+nums[i]);
//            }else {//i位置在step步内来不了
//                step++;
//                R=nextR;
//                nextR = Math.max(nextR,i+nums[i]);
//            }
//        }
        while(i<nums.length){
            if (i<=R){
                nextR = Math.max(nextR,i+nums[i++]);
            }else {
                step++;
//                if (i>nextR) return -1;//根本到不了
                R=nextR;
            }
        }
        return step;
    }
```

## 条件判断的艺术

LT08

```JAVA
public static int myAtoi(String s) {
      if (s==null||s.length()==0) return 0;
      char[] str = s.toCharArray();
      int N= str.length;
//    int fi=-1;
//    while (++fi<N&&(str[fi]<'0'||str[fi]>'9'));//fi跳到第一个为数字的地方
      int fi=0;
      for (;fi<N&&(str[fi]<'0'||str[fi]>'9');fi++) ;
      if (fi==N) return 0;//压根没有数，不合法
      for (int i = 0; i < fi; i++) {//检查从第一个字符到fi-1处的字符只能是空格，除了fi-1能是+-，其它位置只要出现了非空格就是非法的
         //如果i位置不为空格大概率是不合法，但是有一个例外在i-1位置是+-，让这个例外通过&&!(condition)跳出这个if，继续下面的逻辑
         if (str[i]!=' '&&!(i==fi-1&&(str[i]=='-'||str[i]=='+'))) return 0;
      }
      boolean isNeg=fi-1>=0&&str[fi-1]=='-';//只有fi-1位置是'-'结果才是负数，其它' '或者'+'都为正数
      int res=0;
      int minq=Integer.MIN_VALUE/10;//-214748364 商
      int minr=Integer.MIN_VALUE%10;//-8 余数
      for (int i = fi; i < N&&(str[i]>='0'&&str[i]<='9'); i++) {
         if ((res<minq)||(res==minq&&'0'-str[i]<minr)) {//判断越界
            return isNeg?Integer.MIN_VALUE:Integer.MAX_VALUE;
         }
         res=res*10+('0'-str[i]);
      }
      if (res==Integer.MIN_VALUE&&!isNeg) return Integer.MAX_VALUE;//防止“+2147483648”这样的测试用例，直接取相反数会溢出
      return isNeg?res:-res;
   }
```

看下面代码。这段代码是为了检查0~fi-2位置上的字符都是空格，fi-1位置上的字符可能是空格、加、减。可以发现如果0~fi-1上任何一个位置，只要发现不是空格了，那么大概率是不合格。但是如果这个位置是fi-1且为加号或者减号，那么就可以幸免遇难。

技巧就是在大概率淘汰的condition1的后面加上一个可以跳出if的语句，`CONDITION1 && !CONDITION2`,如果condition2这个特殊的条件成立，那么就可以跳出这个if。继续下面的逻辑

```java
      for (int i = 0; i < fi; i++) {
         if (str[i]!=' '&&!(i==fi-1&&(str[i]=='-'||str[i]=='+'))) return 0;
      }
```



## 链表

### 找倒数第n结点

LT19

```java
public ListNode removeNthFromEnd2(ListNode head, int n) {//更短
   if (head==null||head.next==null) return null;
   ListNode pre=null;//来到要删除结点的前一个
   ListNode cur=head;
   while(cur!=null){//大逻辑是n--，cur往下走，但是在n减后做一些事情
      n--;
      if (n==-1) pre=head;
      if (n<-1) pre=pre.next;
      cur=cur.next;
   }
   if (n>0) return head;
   if (n==0) return head.next;
   pre.next=pre.next.next;
   return head;
}
```

看这段代码，就是在找倒数n+1个结点。大逻辑就是cur一直往后走，n一直减。但是在中途加了一些赋值和判断操作，if (n==-1) pre=head;意思就是让cur先走n+1步，然后给pre赋值，后面pre再跟着cur一起走。如果pre为null，那么说明不够n+1个。用n也可以等价判断一些情况，例如n如果>0，那么说明链表个数不够n个，n==0说明链表搞好够n个，此时倒n就是头结点。n<0说明链表个数够n个，并且倒n结点有前面的结点。

```JAVA
   while(cur!=null){//大逻辑是n--，cur往下走，但是在n减后做一些事情
      n--;
      if (n==-1) pre=head;
      if (n<-1) pre=pre.next;
      cur=cur.next;
   }
```



## 动态规划

### dp[i]的含义定义

经验：子数组，子串，就定义为必须以i结尾。组合、子序列问题就定义为0~i范围的答案。





## 滑动窗口

### 条件&更新时机

这段代码求的是最大值，在括的时候产生答案，所以在r括的前面加上while。如果r前面有while，那么最外层的while就给l

更新时机在r的话，r++完了之后结算答案。

```java
	   while(l<str.length){
			while (r<str.length&&(diff<k||(diff==k&&map[str[r]]!=0))){//扩到不符合条件
				if (map[str[r]]++==0) diff++;
				r++;
				ans = Math.max(ans, r-l);
			}
//			if(!(r<str.length&&(diff<k||(diff==k&&map[str[r]]!=0)))){//缩到符合条件
				if (--map[str[l]]==0) diff--;
				l++;
//			}
		}
```

这段代码求的是最小值，在缩的时候求答案，所以在缩的前面加上while。l加上while了，所以外层的while给r。

更新时机在l的话，l++之前结算

```java
while (r < s.length) {
    if (!(all==0)) {//扩，扩到符合条件停。all==0是符合的条件
        map[s[r]]--;
        if (map[s[r]] >= 0) all--;//减完之后大于0才是有效还款
        r++;
    }
    while (all == 0) {//缩,缩到不符合条件
        if (r - l < min) {
            min = r - l;
            start = l;
            end = r;
        }
        map[s[l]]++;
        if (map[s[l]] > 0) all++;
        l++;
    }
}
```

还有一种写法，直接用一个for循环，每次计算出以r作为右端点的答案。713

```JAVA
    public static int numSubarrayProductLessThanK(int[] nums, int k) {
        int n=nums.length;
        int ans=0;
        long mul=1;
        for (int r=0,l=0;r<n;r++){
            mul*=nums[r];
            while(l<=r&&mul>=k) mul/=nums[l++];
            if (mul<k)ans+=r-l+1;//需要if判断么，因为以r作为右端点不一定有答案。
        }
        return ans;
    }
```





总结：

- 在扩的时候出答案，一般是求max。这时候就把r放在while里面，l放在外层的循环。反之在缩的时候出答案，就把while套在l上，外层循环控制r。
- 并且r和l递增的条件是互斥的。



## 单调栈

下面两个写法都可以，第一个由于是for循环，所以循环结束的时候你要保证i已经进入了，所以里面还有一个循环。第二个写法由于是while，我们可以自己控制i的变化时机，所以不需要在里面有一个while循环。

第二种写法在需要动态添加一些条件的时候比较方便，直接在最外层的循环添加即可。

```
	for (int i=0;i<N;i++){//枚举左边的作为最小值
			while (top!=-1&&arr[i]<stack[top]){
				ans+=nums[top]+cn2(nums[top]);
				top--;
			}
			if (top!=-1&&arr[i]==stack[top]){
				nums[top]++;
			}else {
				stack[++top]=arr[i];
				nums[top]=1;
			}
	}
```

```
	 int i=N-1;
	 while (i>=0){
			if (top!=-1&&arr[i]<stack[top]){
				ans+=nums[top];
				top--;
			}else if (top!=-1&&arr[i]==stack[top]){
				nums[top]++;
				i--;
			}else {
				stack[++top]=arr[i--];
				nums[top]=1;
			}
		}
```

下面的需求是栈里面的字符和栈str中剩余的字符数量需要大于k。所以只需要在外面循环加一个条件即可。但是是用for循环的话，那么就需要在for循环里面的while添加条件，退出之后再一次截断判断。

```
	while(i<N&&(top+1+N-i)>k){
			if (top!=-1&&str[i]>str[stack[top]]){
				top--;
			}else {
				stack[++top]=i++;
			}
		}
```

```
	for (; i < N; i++) {
            while (top != -1 && str[i] > str[stack[top]] && (top + 1 + N - i) > k) {
                top--;
            }
            if ((top + 1 + N - i) <= k) break;
            stack[++top] = i;
        }
```



## 位运算

### 判断某一位是不是1

```
正确：(best&(1<<i))!=0
错误：(best&(1<<i))==1
正确：(best&(1<<i))==(1<<i)
```





