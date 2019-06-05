package com.bruce.Example.Command;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @Author: Bruce
 * @Date: 2019/5/9 14:34
 * @Version 1.0
 */
public class MenuCommand implements Command {

    private Collection<Command> col = new ArrayList<Command>();

    public void addCommand(Command command) {
        col.add(command);
    }

    @Override
    public void execute() {
       CommandQueue.addMenu(this);
    }

    @Override
    public void setCookApi(CookApi cookApi) {

    }

    @Override
    public int getTableNum() {
        return 0;
    }

    public Collection<Command> getCommands() {
        return this.col;
    }

}
