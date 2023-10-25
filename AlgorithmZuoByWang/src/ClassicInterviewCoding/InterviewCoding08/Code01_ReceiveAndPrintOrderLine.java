package ClassicInterviewCoding.InterviewCoding08;

import java.util.HashMap;

public class Code01_ReceiveAndPrintOrderLine {

	public static class Node {
		public String info;
		public Node next;

		public Node(String info) {
			this.info = info;
		}
	}

	public static class MessageBox {
		private HashMap<Integer,Node> headMap;
		private HashMap<Integer,Node> tailMap;
		private int waitPoint;

		public MessageBox() {
			headMap=new HashMap<>();
			tailMap=new HashMap<>();
			waitPoint=1;
		}


		public void receive(int sequence, String info) {
			Node node=new Node(info);
			headMap.put(sequence,node);
			tailMap.put(sequence,node);
			if (tailMap.containsKey(sequence-1)){
				tailMap.remove(sequence-1).next=headMap.remove(sequence);
			}
			if (headMap.containsKey(sequence+1)){
				tailMap.remove(sequence).next=headMap.remove(sequence+1);
			}
			if (sequence==waitPoint) print();
		}

		private void print(){
			Node node=headMap.remove(waitPoint);
			while(node!=null){
				System.out.print(node.info+" ");
				node=node.next;
				waitPoint++;
			}
			tailMap.remove(waitPoint-1);
			System.out.println();
		}

	}

	public static void main(String[] args) {
		// MessageBox only receive 1~N
		MessageBox box = new MessageBox();

		box.receive(2,"B"); // - 2"
		box.receive(1,"A"); // 1 2 -> print, trigger is 1

		box.receive(4,"D"); // - 4
		box.receive(5,"E"); // - 4 5
		box.receive(7,"G"); // - 4 5 - 7
		box.receive(8,"H"); // - 4 5 - 7 8
		box.receive(6,"F"); // - 4 5 6 7 8
		box.receive(3,"C"); // 3 4 5 6 7 8 -> print, trigger is 3

		box.receive(9,"I"); // 9 -> print, trigger is 9

		box.receive(10,"J"); // 10 -> print, trigger is 10

		box.receive(12,"L"); // - 12
		box.receive(13,"M"); // - 12 13
		box.receive(11,"K"); // 11 12 13 -> print, trigger is 11

	}
}
