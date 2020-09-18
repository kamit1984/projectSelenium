package com.eqs.tmp;

import org.apache.log4j.Logger;

/**
 * Create by Amit on 9/17/20
 */
public class tmp {

    static Logger log = Logger.getLogger(tmp.class.getName());
    public static void main(String[] args) {

        String x = "$16.40";
        log.info("Logging to logger");
        System.out.println(x.replaceAll("\\$",""));
    }
}
