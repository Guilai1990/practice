package com.bruce.Facade;

/**
 * @Author: Bruce
 * @Date: 2019/5/15 18:59
 * @Version 1.0
 */

/**
 * 外观对象
 */
public class Facade {

    public void test() {
        AModuleApi a = new AModuleImpl();
        a.testA();
        BModuleApi b = new BModuleImpl();
        b.testB();
        CModuleApi c = new CModuleImpl();
        c.testC();
    }
}
