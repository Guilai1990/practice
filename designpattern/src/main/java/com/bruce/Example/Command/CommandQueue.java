package com.bruce.Example.Command;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Bruce
 * @Date: 2019/5/9 15:39
 * @Version 1.0
 */
public class CommandQueue {

    private final static String FILE_NAME = "CmdQueue.txt";

    private static List<Command> cmds = new ArrayList<Command>();

    static {
        cmds = FileOpeUtil.readFile(FILE_NAME);
        if (cmds == null) {
            cmds = new ArrayList<Command>();
        }
    }

    public synchronized static void addMenu(MenuCommand menu) {
        for (Command cmd : menu.getCommands()) {
            cmds.add(cmd);
        }
        FileOpeUtil.writeFile(FILE_NAME, cmds);
    }

    public synchronized static Command getOneCommand() {
        Command cmd = null;
        if (cmds.size() > 0) {
            cmd = cmds.get(0);
            cmds.remove(0);
            FileOpeUtil.writeFile(FILE_NAME, cmds);
        }
        return cmd;
    }
}
