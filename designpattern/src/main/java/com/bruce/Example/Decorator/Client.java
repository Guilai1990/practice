package com.bruce.Example.Decorator;

/**
 * @Author: Bruce
 * @Date: 2019/5/5 18:18
 * @Version 1.0
 */
public class Client {

    public static void main(String[] args) {
        Component c1 = new ConcreteComponent();

        Decorator d1 = new MonthPrizeDecorator(c1);
        Decorator d2 = new SumPrizeDecorator(d1);

        double zs = d2.calcPrize("张三", null, null);
        System.out.println("==========张三应得奖金： " + zs);
        double ls = d2.calcPrize("李四", null, null);
        System.out.println("==========李四应得奖金： " + ls);

        Decorator d3 = new GroupPrizeDecorator(d2);
        double ww = d3.calcPrize("王五", null, null);
        System.out.println("=========王经理应得奖金： " + ww);
    }
}
