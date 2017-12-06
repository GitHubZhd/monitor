package com.monitor.api.job;

import com.monitor.api.http.HttpClientUtil;
import com.monitor.api.http.HttpConst;
import com.monitor.api.http.Request;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLDecoder;

/**
 * Created by ps on 2017/8/24.
 */
public class HttpRequestHelper {

    private static final Logger logger= LoggerFactory.getLogger(HttpRequestHelper.class);

    public static String createGetHttpResponse(String message){

        try{
            String url="http%3a%2f%2f10.76.81.53%3a8088%2fSMS%2fSmsSubmit%3fPhones%3d13832393263%26MsgText%3d%7b0%7d";
            url=URLDecoder.decode(url,"UTF-8");
            logger.info("GET请求url："+url);

            url=UtilHelper.formatString(url,message);

            Request request = new Request();
            request.setMethod(HttpConst.Method.GET)
                    .putHeaderField(HttpConst.Header.ACCEPT, "application/json")
                    .putHeaderField(HttpConst.Header.CONTENT_TYPE, "application/json")
                    .setTimeOut(120000)
                    .setUrl(url);

            CloseableHttpResponse response = HttpClientUtil.createHttpClient(HttpClientUtil.HTTP_TYPE.HTTP)
                    .execute(HttpClientUtil.getHttpUriRequest(request));

            String result=HttpClientUtil.getResponseResult(response);
            logger.info("GET请求响应结果"+result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
