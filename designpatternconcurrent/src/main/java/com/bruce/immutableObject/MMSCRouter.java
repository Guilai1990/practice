package com.bruce.immutableObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Bruce
 * @Date: 2019/5/30 19:10
 * @Version 1.0
 */
public final class MMSCRouter {

    private static volatile MMSCRouter instance = new MMSCRouter();

    private final Map<String, MMSCInfo> routeMap;

    public MMSCRouter() {
        this.routeMap = MMSCRouter.retrieveRouteMapFromDB();
    }

    private static Map<String, MMSCInfo> retrieveRouteMapFromDB() {
        Map<String, MMSCInfo> map = new HashMap<String, MMSCInfo>();
        System.out.println("Retrieve data from DB");
        return map;
    }

    public static MMSCRouter getInstance() {
        return instance;
    }

    public MMSCInfo getMMSC(String msisdnPrefix) {
        return routeMap.get(msisdnPrefix);
    }

    public static void setInstance(MMSCRouter newInstance) {
        instance = newInstance;
    }

    private static Map<String, MMSCInfo> deepCopy(Map<String, MMSCInfo> m) {
        Map<String, MMSCInfo> result = new HashMap<String, MMSCInfo>();
        for (String key : m.keySet()) {
            result.put(key, new MMSCInfo(m.get(key)));
        }
        return result;
    }

    public Map<String, MMSCInfo> getRouteMap() {
        return Collections.unmodifiableMap(deepCopy(routeMap));
    }
}
