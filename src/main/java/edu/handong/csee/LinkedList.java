package edu.handong.csee;

import java.util.ArrayList;

public class LinkedList<D> {
	private ListNode head;
	private ListNode tail;
	
	public LinkedList() {
		head = null;
	}

	public void showList() {
		ListNode position = head;
		while(position != null) {
			System.out.println(position.data);
			position = position.link;
		}
	}

	public int length() {
		int count = 0;
		ListNode position = head;
		while(position != null) {
			count++;
			position = position.link;
		}
		return count;
	}
	
	public void addANodeToStart(D addData) {
		ListNode newNode = new ListNode(addData);
	    // 새로운 노드의 다음 노드로 해드를 지정합니다.
	    newNode.link = head;
	    // 헤드로 새로운 노드를 지정합니다.
	    head = newNode;
	    if(head.link == null){
	      tail=head;
	    }
	}
	
	public void deleteHeadNode() {
		if(head != null) {
			head = head.link;
			if(head==null)
				tail=null;
		}	
		else {
			System.out.println("Deleting from an empty list.");
			System.exit(0);
		}
	}
	
	public void addANodeToTail(D addData) {
		ListNode newNode = new ListNode(addData);
		if(length()==0) {
			addANodeToStart(addData);
		}else {
			tail.link=newNode;
			tail=newNode;
		}
	}
	
	public void deleteTailNode() {
		// Get previous node
		ListNode previous = getPreviousNode();
		
		// assing previous node to tail and 
		// make the previous node link to null.
		tail = previous;
		previous.link = null;
		
	}
	
	private ListNode getPreviousNode() {
		ListNode current = head;
		ListNode previous = null;
		while(true) {
			if(current==tail)
				return previous;
			
			previous = current; // set previons with current at it will be changed into the next node.
			current = current.link; // move to next
		}
	}

	public boolean onList(D target) {
		return find(target) != null;
	}
	
	private ListNode find(D target) {
		boolean found = false;
		ListNode position = head;
		while((position != null) && !found) {
			D dataAtPosition = position.data;
			if(dataAtPosition.equals(target))
				found = true;
			else
				position = position.link;
		}
		return position;
	}
	
	public ArrayList<D> toArrayList() {
		ArrayList<D> list = new ArrayList<D>(length());
		ListNode position = head;
		while(position != null) {
			list.add(position.data);
			position = position.link;
		}
		return list;
	}
	
	private class ListNode{
		private D data;
		
		public D getData() {
			return data;
		}

		public ListNode getLink() {
			return link;
		}

		private ListNode link;
		
		public ListNode(D newData) {
			data = newData;
			link = null;
		}
	}
}