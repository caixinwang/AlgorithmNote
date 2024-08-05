const IndexTree=function(size){
  this.indexTree=new Array(size+1)
  this.size=size
  this.indexTree.fill(0)
}
IndexTree.prototype.add=function(index,d){
  for(;index<=this.size;index+=index&-index){
      this.indexTree[index]+=d
  }
}
IndexTree.prototype.query=function(r){
  let res=0
  for(;r>=1;r-=r&-r){
      res+=this.indexTree[r]
  }
  return res
}
IndexTree.prototype.queryRange=function(l,r){
  return this.query(r)-this.query(l-1)
}
/**
* @param {number[]} nums
* @return {number}
* 使用树状数组统计每一个数出现了几次，map(num)->index∈[0,49999]
* 每次出现一个num，对应num的数量增加一个，调整树状数组的结构
*/
var reversePairs = function(nums) {
  let res=0
  let map=new Map()
  let tmp=[...new Set([].concat(nums,nums.map(a=>a*2)))]
  tmp.sort((a,b)=>a<=b?-1:1)
  for(let i=0;i<tmp.length;i++){
      map.set(tmp[i],i+1)
  }
  let indexTree=new IndexTree(map.size)
  for(let j=0;j<nums.length;j++){
      res+=indexTree.queryRange(map.get(nums[j]*2)+1,indexTree.size)
      indexTree.add(map.get(nums[j]),1)
  }
  return res

};
console.log(reversePairs([2,4,3,5,1]))
