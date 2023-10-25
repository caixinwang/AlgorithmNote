package ClassicInterviewCoding.InterviewCoding05;

import java.util.HashMap;

public class Code04_LeastRecentlyUsedCache {
	static class Node<K,V>{//结点里面放着用户要存的键值对数据，以及还有两个指向前后的指针
		public K key;
		public V value;
		public Node<K,V> next;
		public Node<K,V> last;

		public Node(K key, V value) {//next和last自己去连
			this.key = key;
			this.value = value;
		}
	}

	/**
	 * LRU结构，需要get和put。在get的时候需要链表将get的Node挪到链表尾部代表最新--removeToTail(Node node)。
	 * put的时候可能需要删除链表的头部那个最不常用的Node--DeleteHead()，并且需要将新的结点放到尾部去--addToTail(Node node)。
	 * 或者put可能是修改值，改完也要放到后面去
	 * 所以需要如上的三种方法
	 * @param <K> 用户自己指定key的类型
	 * @param <V> 用户自己指定value的类型
	 */
	static class DoubleLinkedList<K,V>{
		public Node<K,V> head;
		public Node<K,V> tail;

		public DoubleLinkedList() {
			head=null;
			tail=null;
		}

		public void addToTail(Node<K,V> node){//把node加到链表尾部中去
			if (node==null) return;
			if (head==null&&tail==null) {//链表没结点
				head=node;
				tail=node;
			}else {//链表有结点
				node.last=tail;
				tail.next=node;
				tail=node;
			}
		}

		public Node<K,V> deleteHead(){//外部调用要保证不会去叫一个空链表删结点，外部结构自己维持一个size
			Node<K,V> res=head;
			if (head==tail) {
				head=null;
				tail=null;
			}else {//原本的head的next不用断，会被垃圾回收掉
				res.next.last=null;
				head=res.next;
			}
			return res;//不管外面要不要，我们这里都先返回删除的结点
		}

		/**
		 *
		 * 链表中至少有一个结点。这个node一定要是链表里面有的，通过外部结构传参的时候自己注意
		 * 无论是node在中间还是在头的情况，都是先将node的左右两边连好。然后就可以去和tail连接了。
		 * node有两个指针要设置，tail有一个next指针要设置。总共三个指针设置完毕，更新tail
		 * @param node 将该节点挪到链表的尾部
		 */
		public void moveToTail(Node<K,V> node){
			if (node==tail) return;//如果只有一个结点或者这个结点就是尾巴
			if (node==head){//只有前环境，并且要移动head
				node.next.last=node.last;
				head=head.next;
			}else {//node在中间
				node.next.last=node.last;//有前后环境
				node.last.next=node.next;
			}
			node.next=null;
			node.last=tail;
			tail.next=node;
			tail=node;
		}

	}

	public static class MyCache<K, V> {
		private HashMap<K, Node<K, V>> keyNodeMap;
		private DoubleLinkedList<K, V> nodeList;
		private final int capacity;

		public MyCache(int cap) {
			if (cap < 1) {
				throw new RuntimeException("should be more than 0.");
			}
			keyNodeMap = new HashMap<K, Node<K, V>>();
			nodeList = new DoubleLinkedList<K, V>();
			capacity = cap;
		}

		public V get(K key) {
			if (keyNodeMap.containsKey(key)) {
				Node<K, V> res = keyNodeMap.get(key);
				nodeList.moveToTail(res);
				return res.value;
			}
			return null;
		}

		public void set(K key, V value) {
			if (keyNodeMap.containsKey(key)) {
				Node<K, V> node = keyNodeMap.get(key);
				node.value = value;
				nodeList.moveToTail(node);
			} else { // 这是一个新加的记录，有可能出现替换
				if (keyNodeMap.size() == capacity) {
					removeLeastUsedCache();
				}
				Node<K, V> newNode = new Node<K, V>(key, value);
				keyNodeMap.put(key, newNode);
				nodeList.addToTail(newNode);
			}
		}

		private void removeLeastUsedCache() {//移出最不常用的结点
			Node<K, V> removeNode = nodeList.deleteHead();
			keyNodeMap.remove(removeNode.key);
		}

	}

	public static void main(String[] args) {
		MyCache<String, Integer> testCache = new MyCache<String, Integer>(3);
		testCache.set("A", 1);
		testCache.set("B", 2);
		testCache.set("C", 3);
		System.out.println(testCache.get("B"));
		System.out.println(testCache.get("A"));
		testCache.set("D", 4);
		System.out.println(testCache.get("D"));
		System.out.println(testCache.get("C"));

	}
}
