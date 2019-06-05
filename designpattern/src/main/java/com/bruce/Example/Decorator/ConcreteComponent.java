package com.bruce.Example.Decorator;

import java.util.Date;

/**
 * @Author: Bruce
 * @Date: 2019/5/5 18:06
 * @Version 1.0
 */
public class ConcreteComponent extends Component {
    @Override
    public double calcPrize(String user, Date begin, Date end) {
        return 0;
    }
}
