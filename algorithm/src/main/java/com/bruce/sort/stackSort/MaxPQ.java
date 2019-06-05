package com.bruce.sort.stackSort;

/**
 * @Author: Bruce
 * @Date: 2019/5/17 22:51
 * @Version 1.0
 */
//public class MaxPQ<Key extends Comparable<Key>>{
//
//    private Key[] pq;
//    private int N = 0;
//
//    public MaxPQ(int maxN) {
//        pq = (Key[]) new Comparable[maxN+1];
//    }
//
//    private void swim(int k) {
//        while (k > 1 && less(k/2, k)) {
//            exch(k/2, k);
//            k = k/2;
//        }
//    }
//
//    private void sink(int k) {
//        while (2*k <= N) {
//            int j = 2*k;
//            if (j < N && less(j, j+1)) {
//                j++;
//            }
//            if (!less(k, j)) {
//                break;
//            }
//            exch(k, j);
//            k = j;
//        }
//    }
//
//    private boolean less(int i, int j) {
//        return pq[i].compareTo(pq[j] < 0);
//    }
//
//    private void exch(int i, int j) {
//        Key t = pq[i];
//        pq[i] = pq[j];
//        pq[j] = t;
//    }
//
//    private static void show(Comparable[] a) {
//        for (int i = 0; i < a.length; i++) {
//            System.out.print(a[i] + " ");
//        }
//    }
//
//    public static void main(String[] args) {
//
//        String[] a = "E X A M P L E".split(" ");
//        //sort(a);
//        show(a);
//    }
//
//
//}
