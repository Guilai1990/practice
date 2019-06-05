package com.bruce.promise;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Bruce
 * @Date: 2019/5/31 19:49
 * @Version 1.0
 */
public class CaseRunner {

    public static void main(String[] args) {
        Map<String, String> params = new HashMap<String, String>();

        params.put("server", "serverIP");

        params.put("userName", "username");

        params.put("password", "password");

        params.put("serverDir", "serverDir");

        System.setProperty("", "");

        DataSyncTask dst;
        dst = new DataSyncTask(params);
        Thread t = new Thread(dst);
        t.start();

    }

}
