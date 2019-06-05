package com.bruce.Example.Decorator;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Bruce
 * @Date: 2019/5/5 18:11
 * @Version 1.0
 */
public class TempDB {

    private TempDB() {}

    public static Map<String, Double> mapMonthSaleMoney = new HashMap<String, Double>();

    static {
        mapMonthSaleMoney.put("张三", 10000.0);
        mapMonthSaleMoney.put("李四", 30000.0);
        mapMonthSaleMoney.put("王五", 60000.0);
    }
}
