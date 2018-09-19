package com.example.footballclub.service;

import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.UUID;

@Service
public class HttpService {

    public static String httpPostWithJson(JSONObject jsonObj, String url, String appId) throws IOException {
        HttpPost post = null;
        String responseString = null;
        try {
            HttpClient httpClient = new DefaultHttpClient();

            // 设置超时时间
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 2000);
            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 2000);

            post = new HttpPost(url);

            // 构造消息头
            post.setHeader("Content-type", "application/json; charset=utf-8");
            post.setHeader("Connection", "Close");
            String sessionId = getSessionId();
            post.setHeader("SessionId", sessionId);
            post.setHeader("appid", appId);

            // 构建消息实体
            StringEntity entity = new StringEntity(jsonObj.toString(), Charset.forName("UTF-8"));
            entity.setContentEncoding("UTF-8");
            // 发送Json格式的数据请求
            entity.setContentType("application/json");
            post.setEntity(entity);

            HttpResponse response = httpClient.execute(post);

            return HttpService.doResponse(response);
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(post != null){
                try {
                    post.releaseConnection();
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return responseString;
    }

    public static String httpGetWithJson(String url, String appId){
        HttpGet get = null;
        String responseString = null;
        try {
            HttpClient httpClient = new DefaultHttpClient();

            // 设置超时时间
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 2000);
            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 2000);

            get = new HttpGet(url);

            // 构造消息头
            get.setHeader("Content-type", "application/json; charset=utf-8");
            get.setHeader("Connection", "Close");
            String sessionId = getSessionId();
            get.setHeader("SessionId", sessionId);
            get.setHeader("appid", appId);

            HttpResponse response = httpClient.execute(get);

            responseString = HttpService.doResponse(response);
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(get != null){
                try {
                    get.releaseConnection();
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return responseString;
    }

    // 构建唯一会话Id
    public static String getSessionId(){
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString();
        return str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23) + str.substring(24);
    }

    public static String doResponse(HttpResponse response) throws IOException{
        boolean isSuccess = false;
        String responseString = null;

        // 检验返回码
        int statusCode = response.getStatusLine().getStatusCode();
        if(statusCode != HttpStatus.SC_OK){
            //LogUtil.info("请求出错: "+statusCode);
            isSuccess = false;
        }else{
            isSuccess = true;

            //读取返回报文正文
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                System.out.println("  Get ResponseContentEncoding():"+ responseEntity.getContentEncoding());
                System.out.println("  Content Length():"+responseEntity.getContentLength());
                //getResponse
                InputStream in=responseEntity.getContent();
                int count = 0;
                while (count == 0) {
                    count = Integer.parseInt(""+responseEntity.getContentLength());//in.available();
                }
                byte[] bytes = new byte[count];
                int readCount = 0; // 已经成功读取的字节的个数
                while (readCount <= count) {
                    if(readCount == count)break;
                    readCount += in.read(bytes, readCount, count - readCount);
                }

                //转换成字符串
                responseString= new String(bytes, 0, readCount, "UTF-8"); // convert to string using bytes

                System.out.println("2.Get Response Content():\n"+responseString);
            }
        }

        return responseString;
    }
}
