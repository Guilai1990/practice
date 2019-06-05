package com.bruce.Example.Decorator;

import java.util.Date;

/**
 * @Author: Bruce
 * @Date: 2019/5/5 18:06
 * @Version 1.0
 */
public abstract class Decorator extends Component{

    protected Component c;

    public Decorator(Component c) {
        this.c = c;
    }

    @Override
    public double calcPrize(String user, Date begin, Date end) {
        return c.calcPrize(user, begin, end);
    }
}
