package com.bruce.activeObject;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author: Bruce
 * @Date: 2019/6/4 21:31
 * @Version 1.0
 */
public class Recipient implements Serializable {

    private static final long serialVersionUID = 2208629750371068868L;
    private Set<String> to = new HashSet<String>();

    public void addTo(String msisdn) {
        to.add(msisdn);
    }

    public Set<String> getToList() {
        return (Set<String>) Collections.unmodifiableCollection(to);
    }
}
