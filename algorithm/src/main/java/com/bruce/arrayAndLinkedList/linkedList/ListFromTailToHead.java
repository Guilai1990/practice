package com.bruce.arrayAndLinkedList.linkedList;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * @Author: Bruce
 * @Date: 2019/5/23 22:01
 * @Version 1.0
 * https://www.nowcoder.com/practice/d0267f7f55b3412ba93bd35cfa8e8035?tpId=13&tqId=11156&tPage=1&rp=1&ru=%2Fta%2Fcoding-interviews&qru=%2Fta%2Fcoding-interviews%2Fquestion-ranking
 */
public class ListFromTailToHead {

    public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
    /*    ArrayList<Integer> arr = new ArrayList<Integer>();
        Stack<Integer> stack = new Stack<Integer>();

        if (listNode == null) {
            return arr;
        }

        while (listNode != null) {
            stack.push(listNode.val);
            listNode = listNode.next;
        }

        while (!stack.empty()) {
            arr.add(stack.pop());
        }
        return arr;*/

    ArrayList<Integer> arr = new ArrayList<Integer>();

    if (listNode == null) {
        return arr;
    }

    ListNode head = listNode;
    ListNode cur = listNode;
    while (cur != null) {
        ListNode tmp = cur.next;
        cur.next = head;
        head = cur;
        cur = tmp;
    }

    return arr;

    }

    public class ListNode {
        int val;
        ListNode next = null;
        ListNode(int val) {
           this.val = val;
        }
    }

    List list = new LinkedList();
}
