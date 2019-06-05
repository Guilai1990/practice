package com.bruce.ChainOfResponsibility;

/**
 * @Author: Bruce
 * @Date: 2019/5/5 12:34
 * @Version 1.0
 */
public class ConcreteHandler2 extends Handler {

    @Override
    public void handleRequest() {
        //根据某些条件来判断是否属于自己处理的职责范围
        //判断条件比如，从外部传入的参数，或者这里主动去获取的外部数据
        //如从数据库中获取等
        boolean someCondition = false;

        if (someCondition) {
            //如果属于自己处理的职责范围，就在这里处理请求
            //具体的处理代码
            System.out.println("ConcreteHandler1 handle request");
        } else {
            //如果不属于自己处理的职责范围，那就判断是否还有后继的职责对象
            //如果有，就转发请求给后继的职责对象
            //如果没有，什么都不做，自然结束
            if (this.successor != null) {
                this.successor.handleRequest();
            }
        }
    }
}
