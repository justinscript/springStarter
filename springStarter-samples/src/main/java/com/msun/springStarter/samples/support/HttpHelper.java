/*
 * Copyright 2015-2020 msun.com All right reserved.
 */
package com.msun.springStarter.samples.support;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.lamfire.json.JSON;

/**
 * 封装常用http post get put等请求
 * 
 * @author zxc Mar 16, 2016 6:35:33 PM
 */
public class HttpHelper {

    private static final Logger logger = LoggerFactory.getLogger(HttpHelper.class);

    public static void main(String[] args) {
        String jsonStr = excutePost("https://www.baidu.com", "key=zxc337");
        JSON json = JSON.fromJSONString(jsonStr);
        System.out.println(((JSON) json.get("result")).getJSONArray("list"));
    }

    public static String excutePost(String targetURL, String urlParameters) {
        HttpURLConnection connection = null;
        try {
            // Create connection
            URL url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", Integer.toString(targetURL.getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US");
            connection.setUseCaches(false);
            connection.setDoOutput(true);

            // Send request
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            if (!StringUtils.isEmpty(urlParameters)) {
                wr.writeBytes(urlParameters);
            }
            wr.close();

            // Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if not Java 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();
        } catch (Exception e) {
            logger.error("excutePost error!", e);
            return null;
        } finally {
            if (connection != null) connection.disconnect();
        }
    }
}
