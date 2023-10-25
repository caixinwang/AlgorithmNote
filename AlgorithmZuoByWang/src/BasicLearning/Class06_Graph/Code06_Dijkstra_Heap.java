package BasicLearning.Class06_Graph;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Code06_Dijkstra_Heap {//邻接矩阵表示--手写堆
    static final int MAX = 999999;

    static class Node {//邻接点
        public int idx;//结点下标,不重复
        public int weight;//邻接表中指向它的权重
        public Node next;//指向下一个邻接点

        public Node(int idx, int weight, Node next) {
            this.idx = idx;
            this.weight = weight;
            this.next = next;
        }

        public Node(int idx, int weight) {
            this.idx = idx;
            this.weight = weight;
        }
    }

    static class Edge {//边
        public int weight;
        public int from;
        public int to;

        public Edge(int weight, int from, int to) {
            this.weight = weight;
            this.from = from;
            this.to = to;
        }

    }

    static class TNode {//邻接表，本质就是一个装着链表的数组
        public Node firstNode;//这就是一个链表的头节点
        //Data

        public TNode(Node firstNode) {
            this.firstNode = firstNode;
        }
    }

    static class Graph {
        public int nodeNum;
        public int edgeNum;
        public List<TNode> tNodeList;

        public Graph(int nodeNum) {
            this.nodeNum = nodeNum;
            edgeNum = 0;
            tNodeList = new LinkedList<>();
            for (int i = 0; i < nodeNum + 1; i++) {//从1开始记
                tNodeList.add(new TNode(null));
            }
        }

        public void insert(Edge edge) {
            Node node = new Node(edge.to, edge.weight);
            node.next = tNodeList.get(edge.from).firstNode;
            tNodeList.get(edge.from).firstNode = node;
        }
    }

    /**
     * 堆结构实现Dijkstra
     * 注意额外的结构会影响swap和less中的哪一个。只有less函数会用到dmap，所以在swim和sink之前要注意dmap是否合理更新
     * xmap不影响任何swap和less的任意一个，所以放在哪里更新都可以
     */
    static class Heap {//定制的小根堆
        private int[] nodes;//用下标来代表那个结点
        private int size;
        private HashMap<Integer, Integer> dmap;//<idx,distance>  distance map : 表示从源结点到当前结点的最短路径. less
        private HashMap<Integer, Boolean> xmap;//有两种左右，一是判断结点是否解锁--contain，二是判断结点是否加入X集合--true

        public Heap(int size) {
            this.size = 0;
            nodes = new int[size + 1];//下标从1开始,0位置弃而不用
            dmap = new HashMap<>();
            xmap = new HashMap<>();
        }

        private void swap(int a, int b) {//交换
            int tmp = nodes[a];
            nodes[a] = nodes[b];
            nodes[b] = tmp;
        }

        private boolean less(int a, int b) {//比较的规则：距离小才是真的小
            return dmap.get(nodes[a]) < dmap.get(nodes[b]);
        }

        private void swim(int k) {//从k下标位置开始向上调整为小根堆
            for (; k > 1 && less(k, k >> 1); k >>= 1) swap(k, k >> 1);
        }

        private void sink(int k) {
            while (k << 1 <= size) {
                int child = k << 1;//两个孩子中最优秀的那个，优秀指的是“小”
                if (child + 1 <= size && less(child + 1, child)) child++;//右孩子优于左孩子
                if (less(k, child)) break;
                swap(k, child);
                k = child;
            }
        }

        private int pop() {//把最小的结点弹出，加入x集合中
            int res = nodes[1];
            swap(1, size--);
            sink(1);
            xmap.put(res, true);//xmap中设置为true，方便以后确定一个结点是不是已经加入x集合了
            dmap.remove(res);//dmap的作用就是用来查询距离的，现在这个结点进入了x集合，用不到它的距离信息了。
            return res;
        }

        public void addAndUpdate(int to, int d) {//d是否小于dmap.get(to)
            if (!xmap.containsKey(to)) {//如果to结点从来没有解锁过，就加入堆中，并且放入xmap中代表这个结点解锁了
                nodes[++size] = to;
                dmap.put(to,d);//一定一定要在swim函数的上面！！否则距离没有更新，less函数无法运作！
                xmap.put(to,false);//为false代表结点还没有被加入到x集合中，但是这个结点已经解锁了，因为在xmap中能找到这个结点
                swim(size);
            } else if (!xmap.get(to)&&d < dmap.get(to)) {//to结点不在x集合中才进行update,在x集合中就略过
                dmap.put(to, d);
            }
        }

        private boolean isEmpty(){
            return size==0;
        }

        private int getDistance(int node){
            return dmap.get(node);
        }

        private int peek(){
            return nodes[1];
        }
    }

    //每次从已解锁的结点中拿出一个距离最短的node到X集合中，那么start到这个node的最短距离就确定了
    public static int[] dijkstra(Graph graph,int start){//O(NlogN)
        Heap heap=new Heap(graph.nodeNum);
        int[] res=new int[graph.nodeNum+1];
        heap.addAndUpdate(start,0);
        while(!heap.isEmpty()){
            int dis= heap.getDistance(heap.peek());//不能先pop，先pop的话dmap就把距离删了，拿不到了
            int pop = heap.pop();//栈顶加入X集合，设置为true
            Node head=graph.tNodeList.get(pop).firstNode;//拿到邻居链表的头结点
            while(head!=null){//一个一个往里面加，要么解锁，要么更新，都在addAndUpdate方法里面完成
                heap.addAndUpdate(head.idx,dis+head.weight);
                head=head.next;
            }
            res[pop]=dis;//加入X集合的这个结点，start到它距离已经是最小了，放入答案
        }
        return res;
    }

    public static int[] dijkstra2(Graph graph,int start){//O(N²)
        int N=graph.nodeNum+1;
        int[] res=new int[N];
        boolean[] unlock=new boolean[N];//判断一个结点有没有被解锁
        boolean[] xset=new boolean[N];//判断一个结点属不属于x集合，结点x只有unlock[x]==true && xset[x]==true才是x集合中的点
        int[] darr=new int[N];

        unlock[start]=true;//先把出发点解锁了
        darr[start]=0;//出发点到自己的距离是0
        for (int i = 1; i <res.length; i++) {
            int minIndex=MAX;
            int minDistance=MAX;
            for (int j = 0; j < darr.length; j++) {//从已经解锁的并且未加入x集合的结点中选一个距离最小的出来
                if (unlock[j]&&!xset[j]&&darr[j]<minDistance){//只有已经解锁的结点并且没有加入X集合我才看
                    minDistance=darr[j];
                    minIndex=j;
                }
            }
            res[minIndex]=minDistance;
            xset[minIndex]=true;//每次收录一个距离最小的结点
            Node head=graph.tNodeList.get(minIndex).firstNode;//获取收录结点的邻接链表的头节点
            while(head!=null){//继续去解锁它的邻接结点，或看看能够更新最小值
                if (!unlock[head.idx]){//从未解锁就将结点解锁
                    unlock[head.idx]=true;
                    darr[head.idx]=minDistance+ head.weight;
                } else if (unlock[head.idx]&&!xset[head.idx]&&darr[minIndex]+ head.weight<darr[head.idx]) {//已经解锁但是还未加入x集合的结点看看能否更新
                    darr[head.idx]=darr[minIndex]+ head.weight;
                }
                head=head.next;
            }
        }
        return res;
    }



    public static void main(String[] args) {
        Graph graph = new Graph(12);
        graph.insert(new Edge(9, 1, 2));
        graph.insert(new Edge(7, 1, 3));
        graph.insert(new Edge(3, 1, 4));
        graph.insert(new Edge(2, 1, 5));

        graph.insert(new Edge(4, 2, 6));
        graph.insert(new Edge(2, 2, 7));
        graph.insert(new Edge(1, 2, 8));

        graph.insert(new Edge(2, 3, 6));
        graph.insert(new Edge(7, 3, 7));

        graph.insert(new Edge(11, 4, 8));

        graph.insert(new Edge(11, 5, 7));
        graph.insert(new Edge(8, 5, 8));

        graph.insert(new Edge(6, 6, 9));
        graph.insert(new Edge(5, 6, 10));

        graph.insert(new Edge(4, 7, 10));
        graph.insert(new Edge(3, 7, 9));

        graph.insert(new Edge(5, 8, 10));
        graph.insert(new Edge(6, 8, 11));

        graph.insert(new Edge(4, 9, 12));
        graph.insert(new Edge(2, 10, 12));
        graph.insert(new Edge(5, 11, 12));

        int[] dijkstra = dijkstra(graph,1);
        System.out.println(dijkstra[12]);
        int[] dijkstra2 = dijkstra2(graph,1);
        System.out.println(dijkstra2[12]);

    }


}
