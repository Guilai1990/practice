package com.bruce.Example.Decorator;

import java.util.Date;

/**
 * @Author: Bruce
 * @Date: 2019/5/5 18:09
 * @Version 1.0
 */
public class MonthPrizeDecorator extends Decorator {

    public MonthPrizeDecorator(Component c) {
        super(c);
    }

    @Override
    public double calcPrize(String user, Date begin, Date end) {
        double money = super.calcPrize(user, begin, end);
        double prize = TempDB.mapMonthSaleMoney.get(user) * 0.03;
        System.out.println(user+"当月业务奖金"+prize);
        return money + prize;
    }
}
