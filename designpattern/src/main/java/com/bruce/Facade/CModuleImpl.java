package com.bruce.Facade;

/**
 * @Author: Bruce
 * @Date: 2019/5/15 18:57
 * @Version 1.0
 */
public class CModuleImpl implements CModuleApi {
    @Override
    public void testC() {
        System.out.println("现在在C模块中操作testC方法");
    }
}
