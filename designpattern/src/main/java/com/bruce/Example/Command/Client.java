package com.bruce.Example.Command;

/**
 * @Author: Bruce
 * @Date: 2019/5/9 15:33
 * @Version 1.0
 */
public class Client {

    public static void main(String[] args) {

        CookManager.runCookManager();

        for (int i = 0; i < 5; i++) {
            Waiter waiter = new Waiter();
            Command chop = new ChopCommand(i);
            Command duck = new DuckCommand(i);
            Command pork = new PorkCommand(i);

            waiter.orderDish(chop);
            waiter.orderDish(duck);
            waiter.orderDish(pork);

            waiter.orderOver();
        }

    }
}
