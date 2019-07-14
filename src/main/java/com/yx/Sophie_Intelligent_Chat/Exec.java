package com.yx.Sophie_Intelligent_Chat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Author:Sophie
 * Created: 2019/7/14
 */
public class Exec {
    Logger logger=LoggerFactory.getLogger(Exec.class);
    public Exec(){
    }
    public  void exec(String str){
        Runtime run=Runtime.getRuntime();
        try {
            run.exec(str);
            logger.info("命令执行结束");
        } catch (IOException e) {
            logger.info("执行配置文件命令出错");
            e.printStackTrace();
        }
    }
}

