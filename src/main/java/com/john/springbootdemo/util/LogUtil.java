package com.john.springbootdemo.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Author: John
 * E-mail: 634930172@qq.com
 * Date: 2019/2/28 20:09
 * <p/>
 * Description:
 */
public class LogUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger("test");

    public static void log(String msg) {
        LOGGER.info(msg);
    }

}
