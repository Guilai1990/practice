package com.bruce.Example.Command;

/**
 * @Author: Bruce
 * @Date: 2019/5/9 14:32
 * @Version 1.0
 */
public interface Command {

    public void execute();

    public void setCookApi(CookApi cookApi);

    public int getTableNum();
}
