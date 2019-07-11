package com.yx.Sophie_Intelligent_Chat;

import com.baidu.aip.speech.AipSpeech;
import org.json.JSONObject;

import java.nio.file.Paths;

/**
 * Author:Sophie
 * Created: 2019/7/5
 */
public class Recognition {
    //设置APPID/AK/SK
    public static final String APP_ID = "16693786";
    public static final String API_KEY = "uVTEZHxUo0l1Q6Og0OmaZKDv";
    public static final String SECRET_KEY = "jLt3ghAlUBgtDGkaV0bsYdQgV7WtRtN1";

    public static final AipSpeech aipSpeech = getAipSpeech();
    public static String resultText;

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


    //语音转文本
    public static boolean getText() {
        // 对本地语音文件进行识别
        String path = "D:\\IDEA代码\\Maven项目\\sophie_Intelligent_chat\\record.wav";
        if (Paths.get(path).toFile().exists()) {
            JSONObject asrRes = aipSpeech.asr(path, "wav", 16000, null);

            System.out.println(asrRes);
            // 对语音二进制数据进行识别
            if (asrRes.getString("err_msg").equals("success.")) {
                resultText = asrRes.getJSONArray("result").getString(0);
                return true;
            } else {
                return false;
            }
        }
        return false;

    }

        public static String getResultText () {
            System.out.println("识别到文字");
            return resultText;
        }

//    public static String doRec() {
//        /**
//         * 步骤：
//         * 1、录音并存储为wav文件
//         * 2、将音频转为文本
//         * 3、打印文本
//         */
////        Recognition test=new Recognition();
//        if (getText()){
//            System.out.println("结果为："+getResultText());
//            return getResultText();
//        }else {
//            System.out.println("识别错误");
//            return null;
//
//        }
    }

