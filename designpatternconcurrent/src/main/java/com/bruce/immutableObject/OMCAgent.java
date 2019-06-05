package com.bruce.immutableObject;

/**
 * @Author: Bruce
 * @Date: 2019/5/30 19:19
 * @Version 1.0
 */
public class OMCAgent extends Thread {

    @Override
    public void run() {
        boolean isTableModificationMsg = false;
        String updatedTableName = null;

        while (true) {
            System.out.println("OMCAgent running");
            if (isTableModificationMsg) {
                if ("MMSCInfo".equals(updatedTableName)) {
                    MMSCRouter.setInstance(new MMSCRouter());
                }
            }
        }
    }

}
