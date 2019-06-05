package com.bruce.arrayAndLinkedList.linkedList;

import java.util.Scanner;

/**
 * @Author: Bruce
 * @Date: 2019/5/23 16:39
 * @Version 1.0
 * https://www.hackerrank.com/challenges/array-left-rotation/problem
 */
public class LeftRotation {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        String[] nd = scanner.nextLine().split(" ");

        int n = Integer.parseInt(nd[0]);

        int d = Integer.parseInt(nd[1]);

        int[] a = new int[n];

        String[] aItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < n; i++) {
            int aItem = Integer.parseInt(aItems[i]);
            a[i] = aItem;
        }

        for (int i = 0; i < n ; i++) {
            System.out.print(a[(i+d)%n] + " ");
        }

        scanner.close();
    }
}
