package com.bruce.arrayAndLinkedList.linkedList;

/**
 * @Author: Bruce
 * @Date: 2019/5/22 13:19
 * @Version 1.0
 */
public class DoubleNode {
    public int value;
    public DoubleNode last;
    public DoubleNode next;
    public DoubleNode(int data) {
        this.value = data;
    }

    public DoubleNode reverseList(DoubleNode head) {
        DoubleNode pre = null;
        DoubleNode next = null;
        while (head != null) {
            next = head.next;
            head.next = pre;
            head.last = next;
            pre = head;
            head = next;
        }
        return pre;
    }
}
