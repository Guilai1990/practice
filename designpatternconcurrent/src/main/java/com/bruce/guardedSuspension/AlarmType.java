package com.bruce.guardedSuspension;

/**
 * @Author: Bruce
 * @Date: 2019/5/30 22:04
 * @Version 1.0
 */
public enum AlarmType {

    FAULT("fault"), RESUME("resume");

    private final String name;

    AlarmType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
