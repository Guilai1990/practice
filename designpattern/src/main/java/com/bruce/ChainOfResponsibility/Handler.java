package com.bruce.ChainOfResponsibility;

/**
 * @Author: Bruce
 * @Date: 2019/5/5 12:30
 * @Version 1.0
 */

/**
 * 职责的接口，也就是处理请求的接口
 */
public abstract class Handler {

    /**
     * 持有后继的职责对象
     */
    protected Handler successor;

    /**
     * 设置后继的职责对象
     * @param successor 后继的职责对象
     */
    public void setSuccessor(Handler successor) {
        this.successor = successor;
    }

    /**
     * 示意处理请求的方法，虽然这个示意方法是没有传入参数的，但
     * 实际上是可以传入参数的，根据具体需要来选择是否传递参数
     */
    public abstract void handleRequest();
}
