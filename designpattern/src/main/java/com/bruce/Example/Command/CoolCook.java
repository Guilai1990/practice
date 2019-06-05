package com.bruce.Example.Command;

/**
 * @Author: Bruce
 * @Date: 2019/5/9 14:10
 * @Version 1.0
 */
public class CoolCook implements CookApi, Runnable {

    private String name;

    public CoolCook(String name) {
        this.name = name;
    }

    @Override
    public void cook(int tableNum, String name) {
        int cookTime = (int) (10 * Math.random());
        System.out.println(this.name + "厨师正在为" + tableNum + "号桌做： " + name);

        try {
            Thread.sleep(cookTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(this.name + "厨师为" + tableNum + "号桌做好了： " + name + ", 共耗时=" + cookTime + "秒");
    }

    @Override
    public void run() {
        while (true) {
            Command cmd = CommandQueue.getOneCommand();
            if (cmd != null) {
                cmd.setCookApi(this);
                cmd.execute();
            }
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
