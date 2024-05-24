package com.example.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 停止服务回调
 */
public class JVMCallBack {
    static Logger logger = LoggerFactory.getLogger(JVMCallBack.class);

    public static void JVMCallBack(Integer time) {
        Integer time1 = time == null ? 5 : time;
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                logger.info("***********************************************停止服务中");
                Thread.sleep(1000 * time1);
            } catch (InterruptedException e) {
                e.printStackTrace();
                logger.info("***********************************************停止失败", e);
            }
            logger.info("***********************************************停止成功");
        }));
    }
}
