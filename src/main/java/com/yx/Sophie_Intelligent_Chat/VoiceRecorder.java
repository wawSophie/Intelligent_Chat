package com.yx.Sophie_Intelligent_Chat;

import javax.sound.sampled.*;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Author:Sophie
 * Created: 2019/7/6
 */
public class VoiceRecorder {
    static TargetDataLine targetDataLine;
    static AudioFormat audioFormat;
    /**
     * 步骤：
     * 1、设置录音格式
     * 2、开始录音（怎么录）
     * 3、结束录音
     */

    public static void menu(VoiceRecorder voiceRecorder) {

//        VoiceRecorder test = new VoiceRecorder();
//        System.out.println("输入y，开始录音...     输入n，结束录音...  ");
//        Scanner in = new Scanner(System.in);
//        String str = in.next();
        //录音开始时间
        long testTime = System.currentTimeMillis();
//        if (str.equals("y") || str.equals("Y")) {
        System.out.println("录音中...");
        voiceRecorder.newRecorder();
//        }
//        Scanner in2=new Scanner(System.in);
//        String str2=in2.nextLine();
//        if(str2.equals("N") || str2.equals("n")){
//            System.out.println("录音退出");
//            voiceRecorder.endRecorder();
//        }
        long endTime = System.currentTimeMillis();
//        System.out.println("录音了" + (endTime - testTime) / 1000 + "秒");
    }


    private static void newRecorder() {
        /**
         *设置音频的格式
         */
        //采样率，从8000,11025,16000,22050,44100
        float sampleRate = 8000F;
        //每个样本的中位数 8,16
        int sampleSizeInBits = 16;
        //单声道为1，立体声为2
        int channels = 2;
        //singned
        boolean signed = true;
        //以大端还是小端的顺序来存储音频数据
        boolean bigEndian = true;
        audioFormat = new AudioFormat(sampleRate, sampleSizeInBits,
                channels, signed, bigEndian);

        //构造数据行的信息对象，这个信息包括单个音频格式
        //lineClass-该信息对象所描述的数据行的类
        //format：所需的格式
        DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
        try {
            targetDataLine = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
            new RecorderThread().start();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    static class RecorderThread extends Thread {
        @Override
        public void run() {
            //指定的文件类型
            AudioFileFormat.Type fileType = null;
            //设置文件类型和文件扩展名
            File audioFile = null;
            //wav格式文件
            fileType = AudioFileFormat.Type.WAVE;
            audioFile = new File("record.wav");

            try {
                targetDataLine.open(audioFormat);
                //开始音频捕获，回放，生成start事件
                targetDataLine.start();
                AudioInputStream in = new AudioInputStream(targetDataLine);
                AudioSystem.write(in, fileType, audioFile);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void endRecorder() {
        targetDataLine.stop();
        targetDataLine.close();
    }
}

