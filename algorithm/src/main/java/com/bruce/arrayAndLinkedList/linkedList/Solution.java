package com.bruce.arrayAndLinkedList.linkedList;

import java.util.Stack;

/**
 * @Author: Bruce
 * @Date: 2019/5/22 16:19
 * @Version 1.0
 */
public class Solution {


        public ListNode swapPairs(ListNode head) {

            Stack<ListNode> stack = new Stack<ListNode>();
            ListNode newHead = head;
            ListNode cur = head;
            ListNode pre = null;
            ListNode next = null;
            while (cur != null) {
                next = cur.next;
                stack.push(cur);
                if (stack.size() == 2) {
                    cur = stack.pop();
                    if (pre != null) {
                        pre.next = cur;
                    }
                    ListNode next1 = null;
                    while (!stack.isEmpty()) {
                        next1 = stack.pop();
                        cur.next = next1;
                        cur = next1;
                    }
                    cur.next = next;
                }
            }

            return newHead;
        }

    static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }



    public ListNode detectCycle(ListNode head) {
        int pos = -1;
        if (head == null || head.next == null) {
            System.out.print("no cycle");
        }
        ListNode tortoise = head;
        ListNode hare = head.next;
        while(hare.next != null && hare.next.next != null && tortoise != hare) {
            hare = hare.next.next;
            tortoise = tortoise.next;
            pos++;
        }
        if (hare == tortoise) {
            System.out.printf("tail connects to node index %s", pos);
        } else {
            System.out.print("no cycle");
        }
        return head;
    }


    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode fakeHead = new ListNode(-1);
        ListNode current = fakeHead;
        int carry = 0;
        while (l1 != null || l2!= null) {
            int value = l1 == null ? 0 : l1.val;
            value += l2 == null ? 0 : l2.val;
            value += carry;
            carry = value / 10;
            value %= 10;
            current.next = new ListNode(value);
            current = current.next;
            l1 = l1 == null ? null : l1.next;
            l2 = l2 == null ? null : l2.next;
        }
        if (carry == 1) {
            current.next = new ListNode(carry);

        }
        return fakeHead.next;
    }


}



