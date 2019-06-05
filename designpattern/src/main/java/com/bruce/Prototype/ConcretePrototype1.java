package com.bruce.Prototype;

/**
 * @Author: Bruce
 * @Date: 2019/5/7 17:36
 * @Version 1.0
 */

/**
 * 克隆的具体对象
 */
public class ConcretePrototype1 implements Prototype, Cloneable {

    @Override
    public Prototype clone() {
        //最简单的克隆，新建一个自身对象，由于没有属性，就不再复制值了
        Prototype prototype = new ConcretePrototype1();
        return prototype;
    }
}
