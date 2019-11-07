package com.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

public class HttpUtil {

    private static Logger log = Logger.getLogger(HttpUtil.class);
    /**
     * http响应码
     */
    private final static int HTTP_RESPONSE_CODE = 300;

    /**
     * 设置http读取超时时间
     */
    private final static int READ_TIME_OUT = 5000;

    /**
     * 设置http连接超时时间5秒
     */
    private final static int CONNECT_TIME_OUT = 5000;


    private static RequestConfig requestConfig;


    /**
     * Post Request
     * form
     *
     * @return
     * @throws Exception
     */
    public static String doPost(String url, String parameterData) throws Exception {
        log.debug("HTTP请求:" + url + "::参数:" + parameterData);
        URL localURL = new URL(url);
        URLConnection connection = localURL.openConnection();
        HttpURLConnection httpURLConnection = (HttpURLConnection) connection;
        httpURLConnection.setConnectTimeout(CONNECT_TIME_OUT);
        httpURLConnection.setReadTimeout(READ_TIME_OUT);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
        httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        httpURLConnection.setRequestProperty("Content-Length", String.valueOf(parameterData.length()));

        OutputStream outputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        StringBuffer resultBuffer = new StringBuffer();
        String tempLine = null;

        try {
            outputStream = httpURLConnection.getOutputStream();
            outputStreamWriter = new OutputStreamWriter(outputStream, "utf-8");

            outputStreamWriter.write(parameterData);
            outputStreamWriter.flush();
            int responseCode = httpURLConnection.getResponseCode();
            String responseMessage = httpURLConnection.getResponseMessage();
            log.debug("HTTP请求响应==responseCode:" + responseCode + "==responseMessage:" + responseMessage);
            if (responseCode >= HTTP_RESPONSE_CODE) {
                throw new Exception("HTTP Request is not success, Response code is " + responseCode + " \n\r Response Message is " + responseMessage);
            }

            inputStream = httpURLConnection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            reader = new BufferedReader(inputStreamReader);
            while ((tempLine = reader.readLine()) != null) {
                resultBuffer.append(tempLine);
            }
            log.debug("HTTP请求响应：" + resultBuffer.toString());
        } finally {
            if (outputStreamWriter != null) {
                outputStreamWriter.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
            if (reader != null) {
                reader.close();
            }
            if (inputStreamReader != null) {
                inputStreamReader.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return resultBuffer.toString();
    }

    /**
     * 发送 POST 请求（HTTP），JSON形式
     *
     * @param url
     * @param json json对象
     * @return
     */
    public static String doPost(String url, Object json) {
        log.debug("HTTP请求:" + url + "::参数:" + JSONObject.toJSONString(json));
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String result = null;
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;
        try {
            httpPost.setConfig(requestConfig);
            StringEntity stringEntity = new StringEntity(json.toString(), "UTF-8");//解决中文乱码问题
            stringEntity.setContentEncoding("UTF-8");
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            log.debug("HTTP响应:" + response.getStatusLine().getStatusCode());
            result = EntityUtils.toString(entity, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public static String doGet(String url) {
        CloseableHttpClient client = HttpClients.createDefault();
        String str = null;
        HttpGet httpGet = new HttpGet(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(CONNECT_TIME_OUT).setConnectionRequestTimeout(1000)
                .setSocketTimeout(5000).build();
        httpGet.setConfig(requestConfig);
        try {
            CloseableHttpResponse Response = client.execute(httpGet);
            // 所有的响应的数据，也全部都是封装在HttpEntity里面
            HttpEntity entity = Response.getEntity();
            str = EntityUtils.toString(entity, "UTF-8");
            Response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

}
