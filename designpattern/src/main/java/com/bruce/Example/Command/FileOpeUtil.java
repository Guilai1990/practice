package com.bruce.Example.Command;

import org.omg.CORBA.PUBLIC_MEMBER;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Bruce
 * @Date: 2019/5/9 16:19
 * @Version 1.0
 */
public class FileOpeUtil {

    private FileOpeUtil() {}

    public static List readFile(String pathName) {
        List list = new ArrayList<>();
        ObjectInputStream oin = null;
        try {
            File f = new File(pathName);
            if (f.exists()) {
                oin = new ObjectInputStream(
                        new BufferedInputStream(
                                new FileInputStream(f)
                        )
                );
                list = (List)oin.readObject();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (oin != null) {
                    oin.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public static void writeFile(String pathName, List list) {
        File f = new File(pathName);
        ObjectOutputStream oout = null;
        try {
            oout = new ObjectOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream(f)
                    )
            );
            oout.writeObject(list);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                oout.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
