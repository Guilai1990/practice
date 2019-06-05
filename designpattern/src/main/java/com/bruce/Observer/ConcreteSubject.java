package com.bruce.Observer;

/**
 * @Author: Bruce
 * @Date: 2019/5/16 0:37
 * @Version 1.0
 */

/**
 * 具体的目标对象，负责把有关状态存入到相应的观察者对象
 * 并在自己状态发生改变时，通知各个观察者
 */
public class ConcreteSubject extends Subject {
    /**
     * 示意，目标对象的状态
     */
    private String subjectStata;

    public String getSubjectStata() {
        return subjectStata;
    }

    public void setSubjectStata(String subjectStata) {
        this.subjectStata = subjectStata;
        //状态发生了改变，通知各个观察者
        this.notifyObservers();
    }
}
