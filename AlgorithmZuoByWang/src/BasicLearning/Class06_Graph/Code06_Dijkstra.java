package BasicLearning.Class06_Graph;

import java.util.HashMap;
import java.util.HashSet;

public class Code06_Dijkstra {//另外一种表示，不是邻接矩阵

    /**
     * 从distanceMap中选一个距离最小的结点，并且这个结点不在set里面。
     * @param distanceMap
     * @param set
     * @return
     */
    public static Node find(HashMap<Node,Integer> distanceMap, HashSet<Node> set){
        int distance=Integer.MAX_VALUE;
        Node minNode=null;//如果distanceMap中的结点都已经被选过了，那么就返回null
        for (Node node:distanceMap.keySet()){
            if (!set.contains(node)&&node.value<distance){
                minNode=node;
                distance= node.value;
            }
        }
        return minNode;
    }

    public static HashMap<Node,Integer> Dijkstra(Node from){
        HashMap<Node,Integer> distanceMap=new HashMap<>();
        HashSet<Node> set=new HashSet<>();
        distanceMap.put(from,0);
        Node cur=from;
        while(cur!=null){//开始一个一个选最小距离的结点
            int distance=distanceMap.get(cur);
            for (Edge edge:cur.edges){
                if (!distanceMap.containsKey(edge.to)){//这个点本来到达不了
                    distanceMap.put(edge.to,distance+edge.weight);
                }else {//如果可以到达，就更新原本map里面的值
                    distanceMap.put(edge.to,Math.min(distanceMap.get(edge.to),distance+ edge.weight));
                }
            }
            set.add(cur);
            cur=find(distanceMap,set);
        }
        return distanceMap;
    }

    public static class Record{
        public int distance;
        public Node node;

        public Record(int distance, Node node) {
            this.distance = distance;
            this.node = node;
        }
    }

    public static class Heap{
        public Node[] nodes;
        public HashMap<Node,Integer> distanceMap;//用来实现Node之间的映射比较
        public HashMap<Node,Integer> indexMap;//用来实现更新调整
        public int size;

        public Heap(int size) {
            this.size = size;
            nodes=new Node[size];
            distanceMap=new HashMap<>();
            indexMap=new HashMap<>();
        }

        /**
         * @param index:从index这个位置开始，向上调整为小根堆结构
         */
        private void upHeapify(int index){
            int child=index;
            Node temp=nodes[child];
            for(;distanceMap.get(nodes[child-1>>1])>distanceMap.get(temp)&&child>0;child=child-1>>1){
                indexMap.put(nodes[child-1>>1],child);
                nodes[child]=nodes[child-1>>1];
            }
            indexMap.put(temp,child);
            nodes[child]=temp;
        }

        /**
         *
         * @param index:从index开始向下调整成最小堆
         * @param maxIndex:nodes数组的最大下标为maxIndex
         */
        private void downHeapify(int index, int maxIndex){
            Node temp=nodes[index];
            int parent=index;
            int child;
            for (;parent*2+1<=maxIndex;parent=child){
                child=parent*2+2<=maxIndex
                        &&distanceMap.get(nodes[parent*2+2])<distanceMap.get(nodes[parent*2+1])?parent*2+2:parent*2+1;
                if (distanceMap.get(nodes[child])<distanceMap.get(temp)){
                    indexMap.put(nodes[child],parent);
                    nodes[parent]=nodes[child];
                }else {
                    break;//写在for循环的（）里面太长了。换成写在for循环里面需要break
                }
            }
            indexMap.put(temp,parent);
            nodes[parent]=temp;
        }

        private Record pop(){
            Record record=new Record(distanceMap.get(nodes[0]),nodes[0]);
            indexMap.put(nodes[0],-1);//改成曾经来过，现在不在的状态
            distanceMap.remove(nodes[0]);//出去了，并且以后也回不来，彻底的不要了
            swap(0,size-1);
            nodes[size-1]=null;//释放
            downHeapify(0,--size-1);
            return record;
        }

        private void addOrUpdateOrIgnore(Node node,int distance){
            if (isInHeap(node)&&distance<distanceMap.get(node)){
                distanceMap.put(node,distance);
            }else if (!isEntered(node)){
                distanceMap.put(node,distance);
                indexMap.put(node,size);
                nodes[size++]=node;
                upHeapify(size-1);
            }
        }


        private boolean isEmpty(){
            return size==0;
        }

        private boolean isEntered(Node node){
            return indexMap.containsKey(node);
        }

        private boolean isInHeap(Node node){
            return isEntered(node)&&indexMap.get(node)!=-1;
        }

        private void swap(int index1,int index2){
            indexMap.put(nodes[index1],index2);
            indexMap.put(nodes[index2],index1);
            Node temp=nodes[index1];
            nodes[index1]=nodes[index2];
            nodes[index2]=temp;
        }

    }

    public static HashMap<Node,Integer> Dijkstra2(Node from,int size){
        HashMap<Node,Integer> res=new HashMap<>();
        Heap heap=new Heap(size);
        heap.addOrUpdateOrIgnore(from,0);
        while(!heap.isEmpty()){
            Record record=heap.pop();
            int distance=record.distance;
            Node node=record.node;
            for (Edge edge:node.edges){
                heap.addOrUpdateOrIgnore(edge.to,distance+edge.weight);
            }
            res.put(node,distance);
        }
        return res;
    }

}
