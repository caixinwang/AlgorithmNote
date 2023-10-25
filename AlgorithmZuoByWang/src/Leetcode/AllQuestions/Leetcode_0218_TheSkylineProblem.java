package Leetcode.AllQuestions;

import java.util.*;

public class Leetcode_0218_TheSkylineProblem {

    class Node {
        int x;
        boolean add;//崛起还是坠落
        int height;

        public Node(int x, boolean add, int height) {
            this.x = x;
            this.add = add;
            this.height = height;
        }
    }

    class NodeComparator implements Comparator<Node> {
        public int compare(Node o1, Node o2) {
            if (o1.x != o2.x) return o1.x - o2.x;
            if (o1.add) return -1;//返回-1 o1在前面
            if (o2.add) return 1;// 1 o2在前面
            return 0;
        }
    }

    /**
     * 因为我们需要每一个点算完崛起下落之后的最大高度，所以我们需要TreeMap并且把高度作为key，因为一个高度存不存在取决于崛起的次数
     * 所以value是次数。另外我们需要每一个点x的最大高度，需要存起来，并且x也需要从小到大排序以便于我们生成答案，所以我们还需要另外
     * 一张TreeMap帮我们存储x对应最大高度
     *
     * @param buildings (崛起位置，下落位置，高度)
     * @return 返回[(x点 ， 最大高度)]
     */
    public List<List<Integer>> getSkyline(int[][] buildings) {
        if (buildings == null || buildings.length == 0) return null;
        Node[] nodes = new Node[buildings.length << 1];
        for (int i = 0; i < buildings.length; i++) {
            nodes[i << 1] = new Node(buildings[i][0], true, buildings[i][2]);//崛起
            nodes[i << 1 | 1] = new Node(buildings[i][1], false, buildings[i][2]);//下落
        }
        Arrays.sort(nodes,new NodeComparator());
        TreeMap<Integer, Integer> timesMap = new TreeMap<>();//<高度，次数>
        TreeMap<Integer, Integer> xMap = new TreeMap<>();//<x,高度>
        for (Node node : nodes) {
            if (node.add) {
                if (!timesMap.containsKey(node.height)) {
                    timesMap.put(node.height, 1);
                } else {
                    timesMap.put(node.height, 1 + timesMap.get(node.height));
                }
            } else {
                if (timesMap.get(node.height) == 1) {
                    timesMap.remove(node.height);
                } else {
                    timesMap.put(node.height, -1 + timesMap.get(node.height));
                }
            }
            if (timesMap.isEmpty()) {//timesMap可能变为空了
                xMap.put(node.x, 0);
            } else {
                xMap.put(node.x, timesMap.lastKey());
            }
        }
        List<List<Integer>> ans = new ArrayList<>();
        for (int x : xMap.keySet()) {
            int h=xMap.get(x);
            if (ans.isEmpty()||ans.get(ans.size()-1).get(1)!=h){
                ans.add(Arrays.asList(x,h));
            }
        }
        return ans;
    }

    public List<List<Integer>> getSkyline2(int[][] buildings) {
        List<List<Integer>> ans=new ArrayList<>();
        List<int[]> infos=new ArrayList<>();
        for (int[] building:buildings){
            infos.add(new int[]{building[0],building[2]});
            infos.add(new int[]{building[1],-building[2]});
        }
        infos.sort((a,b)->a[0]!=b[0]?a[0]-b[0]:b[1]-a[1]);//x从小到大，weight从大到小，在同一个点我们总是先加一个高度
        int premax=0;
//        PriorityQueue<Integer> queue=new PriorityQueue<>((a,b)->b-a);//大根堆
        Heap queue=new Heap(infos.size());
        queue.add(0);//还没开始就有一个高度是0
        for (int[] info : infos) {
            if (info[1]>0){
                queue.add(info[1]);
            }else {//<0
                queue.remove(-info[1]);//这里时间复杂度是O(N)，想达到时间复杂度O(log N)需要自己手写堆
            }
            if (premax!=queue.peek()){
                ans.add(List.of(info[0],queue.peek()));
                premax=queue.peek();
            }
        }
        return ans;
    }

    class Heap{
        int size;
        int[] heap;
        HashMap<Integer,Integer> indexMap;//<元素，下标>
        HashMap<Integer,Integer> timesMap;//<元素，次数>

        public Heap(int maxSize){
            heap=new int[maxSize+1];
            size=0;
            indexMap=new HashMap<>();
            timesMap=new HashMap<>();
        }

        private void swap(int a,int b){
            indexMap.put(heap[a],b);
            indexMap.put(heap[b],a);
            int t=heap[a];
            heap[a]=heap[b];
            heap[b]=t;
        }

        private boolean more(int a,int b){
            return heap[a]>heap[b];
        }

        private void sink(int k){
            while(k<<1<=size){
                int child=k<<1;
                if (child+1<=size&&more(child+1,child))  child++;//右孩子存在并且比左孩子好
                if (more(k,child)) break;
                swap(k,child);
                k=child;
            }
        }

        private void swim(int k){
            for (;k>1&&more(k,k>>1);swap(k,k>>1),k>>=1);
        }

        public void add(int num){
            if (!timesMap.containsKey(num)){
                timesMap.put(num,1);
            }else {
                timesMap.put(num,1+timesMap.get(num));
                return;
            }
            indexMap.put(num,size+1);//放在swim之前
            heap[++size]=num;
            swim(size);
        }

        public void remove(int num){
            if (!timesMap.containsKey(num)) return;
            if (timesMap.get(num)>1) {
                timesMap.put(num,-1+timesMap.get(num));
                return;
            }
            int index=indexMap.get(num);
            swap(index,size--);
            sink(index);
            swim(index);
            indexMap.remove(num);//插前删后，放在sink、swim之后
            timesMap.remove(num);
        }

        public int peek(){
            return heap[1];
        }
    }
}
