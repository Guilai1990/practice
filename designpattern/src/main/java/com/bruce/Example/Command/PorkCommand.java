package com.bruce.Example.Command;

import java.io.Serializable;

/**
 * @Author: Bruce
 * @Date: 2019/5/9 14:33
 * @Version 1.0
 */
public class PorkCommand implements Command, Serializable {

    private CookApi cookApi = null;

    private int tableNum;

    @Override
    public void setCookApi(CookApi cookApi) {
        this.cookApi = cookApi;
    }

    public PorkCommand(int tableNum) {
        this.tableNum = tableNum;
    }

    @Override
    public int getTableNum() {
        return this.tableNum;
    }

    @Override
    public void execute() {
        this.cookApi.cook(tableNum,"蒜泥白肉");
    }
}
