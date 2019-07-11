package com.yx.Sophie_Intelligent_Chat;


import com.baidu.aip.speech.AipSpeech;
import com.baidu.aip.speech.TtsResponse;
import com.baidu.aip.util.Util;
//import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

/**
 * Author:Sophie
 * Created: 2019/7/5
 */
public class Synthesis {
    //设置APPID/AK/SK
    public static final String APP_ID = "16693786";
    public static final String API_KEY = "uVTEZHxUo0l1Q6Og0OmaZKDv";
    public static final String SECRET_KEY = "jLt3ghAlUBgtDGkaV0bsYdQgV7WtRtN1";

    public static final AipSpeech aipSpeech = getAipSpeech();
    public static String filename = "output.mp3";
    public static Player player;

    public static void doSyn(String text,Synthesis test) {
//        Synthesis test = new Synthesis();
//        String text = "今天是2019年7月6日";
        if (test.getMp3(text.toString())) {
            test.playMp3();
        } else {
            System.out.println("转换失败");
        }
        System.out.println(test.playStatus());
    }

    //构建一个语音识别的Java客户端，提供交互方法
    private static AipSpeech getAipSpeech() {
        // 初始化一个AipSpeech
        AipSpeech aipSpeech = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        aipSpeech.setConnectionTimeoutInMillis(2000);
        aipSpeech.setSocketTimeoutInMillis(60000);

//            // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
//            client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
//            client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

        // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
        // 也可以直接通过jvm启动参数设置此环境变量
//            System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");
        return aipSpeech;
    }

    //将文字转换为MP3文件
    public static boolean getMp3(String text) {
        player = null;

        // 设置可选参数
        HashMap<String, Object> options = new HashMap<String, Object>();
        //语速，取值0-9，默认为5中语速
        options.put("spd", "5");
        //音调，取值0-9，默认为5中语调
        options.put("pit", "5");
        //发音人选择，0为女生，1为男生，3为度逍遥，4为度丫丫
        options.put("per", "4");
        //音量0-15,5为中音量
        options.put("vol", 5);

        //调用接口，进行语音合成
        TtsResponse res = aipSpeech.synthesis(text, "zh", 1, options);
//            //错误码
//            System.out.println(res.getErrorCode());
        JSONObject res1 = res.getResult();
        //生成的音频数据
        byte[] data = res.getData();
        if (data != null) {
            try {
                //写入到文件中去
                Util.writeBytesToFileSystem(data, filename);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        //indentFactor缩进因子
        if (res1 != null) {
            System.out.println(res1.toString(2));
        }
        return true;
    }

    public void playMp3() {
        try {
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(filename));
            player = new Player(in);
            player.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String playStatus() {
        //音频为空，音频播放完毕，正在播放中
        if (player == null) {
            return null;
        } else if (player.isComplete()) {
            return "played";
        } else {
            return "playing";
        }
    }
}
