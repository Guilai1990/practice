package com.bruce.Example.Command;

/**
 * @Author: Bruce
 * @Date: 2019/5/9 15:50
 * @Version 1.0
 */
public class CookManager {

    private static boolean runFlag = false;

    public static void runCookManager() {
        if (!runFlag) {
            runFlag = true;

            HotCook cook1 = new HotCook("张三");
            HotCook cook2 = new HotCook("李四");
            HotCook cook3 = new HotCook("王五");

            Thread t1 = new Thread(cook1);
            t1.start();

            Thread t2 = new Thread(cook2);
            t2.start();

            Thread t3 = new Thread(cook3);
            t3.start();
        }
    }
}
