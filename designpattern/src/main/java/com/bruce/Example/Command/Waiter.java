package com.bruce.Example.Command;

/**
 * @Author: Bruce
 * @Date: 2019/5/9 15:01
 * @Version 1.0
 */
public class Waiter {

    private MenuCommand menuCommand = new MenuCommand();

    public void orderDish(Command cmd) {

        menuCommand.addCommand(cmd);
    }

    public void orderOver() {
        this.menuCommand.execute();
    }
}
