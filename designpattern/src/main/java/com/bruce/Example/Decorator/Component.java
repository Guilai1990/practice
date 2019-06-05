package com.bruce.Example.Decorator;

import java.util.Date;

/**
 * @Author: Bruce
 * @Date: 2019/5/5 18:05
 * @Version 1.0
 */
public abstract class Component {

    public abstract double calcPrize(String user, Date begin, Date end);
}
