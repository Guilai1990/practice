package com.bruce.Facade;

/**
 * @Author: Bruce
 * @Date: 2019/5/15 18:57
 * @Version 1.0
 */
public class AModuleImpl implements AModuleApi {
    @Override
    public void testA() {
        System.out.println("现在在A模块中操作testA方法");
    }
}
