package com.bruce.Command;

/**
 * @Author: Bruce
 * @Date: 2019/5/8 15:06
 * @Version 1.0
 */

/**
 * 命令接口，声明执行的操作
 */
public interface Command {
    /**
     * 执行命令对应的操作
     */
    public void execute();
}
