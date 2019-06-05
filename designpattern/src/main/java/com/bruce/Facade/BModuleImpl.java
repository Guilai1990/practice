package com.bruce.Facade;

/**
 * @Author: Bruce
 * @Date: 2019/5/15 18:57
 * @Version 1.0
 */
public class BModuleImpl implements BModuleApi {
    @Override
    public void testB() {
        System.out.println("现在在B模块中操作testB方法");
    }
}
