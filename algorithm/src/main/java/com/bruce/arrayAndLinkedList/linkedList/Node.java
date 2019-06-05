package com.bruce.arrayAndLinkedList.linkedList;

/**
 * @Author: Bruce
 * @Date: 2019/5/22 0:02
 * @Version 1.0
 */
public class Node {

    public int value;
    public Node next;
    public Node(int data) {
        this.value = data;
    }

    public Node reverseList(Node head) {
        Node pre = null;
        Node next = null;
        while (head != null) {
            next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }
        return pre;
    }

}
