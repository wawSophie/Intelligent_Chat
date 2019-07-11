package com.yx.Sophie_Intelligent_Chat;

import java.io.File;
import java.nio.file.Paths;

/**
 * Author:Sophie
 * Created: 2019/7/6
 */
public class Sophie {

    public static void main(String[] args) {

        new Synthesis().doSyn("在，有什么都可以和我说哦",new Synthesis());
        System.out.println("在，有什么都可以和我说哦");

        String text;
        while (true){
            String path = "D:\\IDEA代码\\Maven项目\\sophie_Intelligent_chat\\record.wav";
            if (Paths.get(path).toFile().exists()){
                Paths.get(path).toFile().delete();
                System.out.println("删除成功");
            }
            String path1 = "D:\\IDEA代码\\Maven项目\\sophie_Intelligent_chat\\output.mp3";
            if (Paths.get(path1).toFile().exists()){
                Paths.get(path1).toFile().delete();
            }
            text=null;
            //录音
            VoiceRecorder voiceRecorder=new VoiceRecorder();
            voiceRecorder.menu(voiceRecorder);
            //语音识别
            Recognition recognition=new Recognition();
            if (recognition.getText()) {
//                 text = recognition.doRec();
                text=recognition.getResultText();
                //文本传输到图灵机器人
                System.out.println(text);
                tuLinRobot tuLinRobot=new tuLinRobot();
                String message=tuLinRobot.chat(text);
                //将图灵机器人传回的文本进行语音合成
                Synthesis synthesis=new Synthesis();
                synthesis.doSyn(message,synthesis);
                System.out.println(text);
                recognition.resultText=null;
                continue;
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }else {
                System.out.println("没有听到说话");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }
        }
    }
}
