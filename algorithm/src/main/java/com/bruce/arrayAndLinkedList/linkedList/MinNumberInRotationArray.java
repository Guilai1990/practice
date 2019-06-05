package com.bruce.arrayAndLinkedList.linkedList;

/**
 * @Author: Bruce
 * @Date: 2019/5/24 11:08
 * @Version 1.0
 * https://www.nowcoder.com/practice/9f3231a991af4f55b95579b44b7a01ba?tpId=13&tqId=11159&tPage=1&rp=1&ru=%2Fta%2Fcoding-interviews&qru=%2Fta%2Fcoding-interviews%2Fquestion-ranking
 */
public class MinNumberInRotationArray {

    public int minNumberInRotateArray(int [] array) {

        int len = array.length;
        if (len == 0) {
            return 0;
        }
        int low = 0;
        int high = len - 1;
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (array[mid] > array[high]) {
                low = mid + 1;
            } else if (array[mid] == array[high]) {
                high = high - 1;
            } else {
                high = mid;
            }
        }
        return array[low];

    }

}
