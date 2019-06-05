package com.bruce.Observer;

/**
 * @Author: Bruce
 * @Date: 2019/5/16 0:40
 * @Version 1.0
 */

/**
 * 具体观察者对象，实现更新的方法，使自身的状态和目标的状态保持一致
 */
public class ConcreteObserver implements Observer {
    /**
     * 示意，观察者的状态
     */
    private String observerState;

    /**
     * 具体的更新实现
     * @param subject
     */
    @Override
    public void update(Subject subject) {
        //这里需要更新观察者的状态，使其与目标的状态保持一致
        observerState = ((ConcreteSubject)subject).getSubjectStata();
    }
}
