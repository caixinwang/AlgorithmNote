/**
 * @param {string} s
 * @return {number}
 */
var lengthOfLongestSubstring = function(s) {
  const max=(a,b)=>a>=b?a:b
  const set=new Set()
  let l=0,r=0,res=0
  for(;r<s.length;r++){//每次进循环都要把r加进来，产生答案的时机在r加入的时候
      while(set.has(s[r])){//不满足加入的条件就一直删
          set.delete(s[l++])
      }
      set.add(s[r])
      res=max(res,r-l+1)       
  }
  return res
};