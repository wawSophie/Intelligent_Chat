package com.yx.Sophie_Intelligent_Chat;

import java.io.File;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Author:Sophie
 * Created: 2019/7/6
 */
public class Sophie {

    public  void start() {
        /**
         * 1、录音
         * 2、语音识别，返回text
         * 3、对text进行判断
         *        3.1 若是命令，则执行命令，结束本次循环
         *        3.2 若不是命令，则转到第四步
         * 4、图灵机器人接收到text，返回对话内容
         * 5、将对话内容进行语音合成，进行播放
         */
        VoiceRecorder voiceRecorder=new VoiceRecorder();
        Recognition recognition=new Recognition();
        LoadConfig loadConfig=new LoadConfig();
        Properties properties=new Properties();
        Exec exec=new Exec();
        Synthesis synthesis = new Synthesis();
        tuLinRobot tuLinRobot = new tuLinRobot();
        synthesis.doSyn("在，有什么都可以和我说哦",synthesis);
//        System.out.println("在，有什么都可以和我说哦");
        while (true){
//            String path = "D:\\IDEA代码\\Maven项目\\sophie_Intelligent_chat\\record.wav";
//            if (Paths.get(path).toFile().exists()){
//                Paths.get(path).toFile().delete();
//                System.out.println("删除成功");
//            }
//            String path1 = "D:\\IDEA代码\\Maven项目\\sophie_Intelligent_chat\\output.mp3";
//            if (Paths.get(path1).toFile().exists()){
//                Paths.get(path1).toFile().delete();
//            }
            //录音
            voiceRecorder.menu(voiceRecorder);
            //如果文本不为空，则判断文本是命令还是简单的对话
            if (recognition.getText()) {
                String text=recognition.getResultText();
                System.out.println(text);
                String str1=text.replaceAll("\\p{P}","");
                System.out.println(str1);
                //如果文本是命令，则执行命令，语音合成“好的”，然后退出本次循环
                //否则，传给图灵机器人，进行文本解析，将结果进行语音合成
                String values=loadConfig.isOrder(str1,properties);
                if (values!=null){
                    System.out.println("进入命令执行部分");
                    exec.exec(values);
                    synthesis.doSyn("好的",synthesis);
                    continue;
                }else {
//                    文本传输到图灵机器人
                    String message = tuLinRobot.chat(text);
                    //将图灵机器人传回的文本进行语音合成
                    synthesis.doSyn(message, synthesis);
                    System.out.println(text);
                    recognition.resultText = null;
                    continue;
                }
            }
            else {
                System.out.println("没有听到说话");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }
        }
    }
}
