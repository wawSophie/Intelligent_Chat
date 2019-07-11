package com.yx.Sophie_Intelligent_Chat;

import net.sf.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;

/**
 * Author:Sophie
 * Created: 2019/7/5
 */
public class tuLinRobot {
    private static final String api_key="fa3806b1f58c43dabafcfe1baca82adb";
    private static final String user_id="12323u28671";
    private static final String url="http://openapi.tuling123.com/openapi/api/v2";
    static int code;
    public static String getJson(String str){
        JSONObject root=new JSONObject();
        JSONObject first=new JSONObject();
        JSONObject second=new JSONObject();
        JSONObject second2=new JSONObject();
        root.put("reqType",0);
        second2.put("apiKey",api_key);
        second2.put("userId",user_id);
        second.put("text",str);
        first.put("inputText",second);
        root.put("perception",first);
        root.put("userInfo",second2);
        return root.toString();
    }
    public static String tuLinPost(String str){
        BufferedReader in1 = null;
        String reponseStr = "";
        OutputStreamWriter out = null;
        try {
            URL realURL=new URL(url);
            HttpURLConnection connection= (HttpURLConnection) realURL.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept","application/json");
            connection.setRequestProperty("Content-Type","application/json");
            connection.connect();
            out=new OutputStreamWriter(connection.getOutputStream());
            out.write(str);
            out.close();
            connection.getOutputStream().flush();

            //响应码
             code=connection.getResponseCode();
            if (code==200){
                in1=new BufferedReader(new InputStreamReader(connection.getInputStream()));
            }else {
                in1=new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            }
            //读取响应，获取长度
            String line;
            while ((line=in1.readLine())!=null){
                reponseStr+=line;
                System.out.println("存在对话");
                System.out.println(reponseStr);
            }
        } catch (IOException e) {
            LOGGER.info("Exception occur When send http post request");
        }finally {
                try {
                    if (in1!=null){in1.close();}
                    if (out!=null){ out.close();}
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        return reponseStr;
    }

    //为什么我的就没有fromObject呢？要导入另一个包，net
    public static String parseMessage(String responseStr){
        JSONObject jsonObject=JSONObject.fromObject(responseStr);
        List<Object> results = (List<Object>) jsonObject.get("results");
        JSONObject resultObject= (JSONObject) results.get(0);
        JSONObject values= (JSONObject) resultObject.get("values");
        return values.get("text").toString();
    }
    public static String chat(String text) {
//        Scanner scanner=new Scanner(System.in);
//        while (scanner.hasNextLine()){
//            String str=scanner.nextLine();
            String object=getJson(text.toString());//将字符串变为JSON串
//            System.out.println(object);//JSON串
            String reponseStr=tuLinPost(object);//发送请求
//            System.out.println(reponseStr);//回复的内容
            String message=parseMessage(reponseStr);//找到内容中对应的文本
//            System.out.println("Robot Say:"+message);//打印输出
            return message;
//        }

    }
}
