package com.yx.Sophie_Intelligent_Chat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

/**
 * Author:Sophie
 * Created: 2019/7/14
 */

/**
 * 加载配置文件，对语音合成的文本进行判断
 * 1、若配置文件中key存在这个文本，则这个文本是命令，执行这条命令
 * 2、若不是命令，则将文本传给图灵机器人
 */
public class LoadConfig {
    Logger logger=LoggerFactory.getLogger(LoadConfig.class);

    public String isOrder(String text,Properties properties){
        String values = null;
        logger.info("进入到加载配置文件中");
        String fileName="D:\\IDEA代码\\Maven项目\\sophie_Intelligent_chat\\src\\main\\resources\\Config.properties";

        try {
            InputStream is=new FileInputStream(new File(fileName));
            properties.load(is);
            System.out.println(text);
            System.out.println(properties.getProperty("打开计算器"));
            values=properties.getProperty(text);
            System.out.println(properties.getProperty(text));
         } catch (Exception e) {
            e.printStackTrace();
        }
        return values;
    }

//    public static void main(String[] args) {
//        LoadConfig loadConfig=new LoadConfig();
//        String values=loadConfig.isOrder("打开计算器");
//        System.out.println(values);
//    }
}




