package com.monitor.api.job;

import com.monitor.api.http.HttpClientUtil;
import com.monitor.api.http.HttpConst;
import com.monitor.api.http.Request;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ps on 2017/11/17.
 * @author zhd
 */
@Component
public class MonitorJobsB {

    public final static long ONE_Minute =   120*1000;
    private static final Logger logger= LoggerFactory.getLogger(MonitorJobsB.class);

    @Scheduled(fixedRate = ONE_Minute)
    public void fixedDelayJob(){
        jobs();
    }


    void jobs(){
        List<String> list=new ArrayList();
        list.add("http://10.76.81.161:8080/api/Init?category=e_business");
        list.add("http://10.76.81.162:8080/api/Init?category=e_business");
        list.add("http://10.76.81.163:8080/api/Init?category=e_business");
        list.add("http://10.76.81.164:8080/api/Init?category=e_business");
        list.add("http://10.76.81.165:8080/api/Init?category=e_business");
        list.add("http://10.76.81.166:8080/api/Init?category=e_business");
        list.add("http://10.76.81.167:8080/api/Init?category=e_business");
        list.add("http://10.76.81.168:8080/api/Init?category=e_business");
        list.add("http://10.76.81.169:8080/api/Init?category=e_business");
        list.add("http://10.76.81.170:8080/api/Init?category=e_business");
        list.add("http://10.76.81.171:8080/api/Init?category=e_business");
        list.add("http://10.76.81.172:8080/api/Init?category=e_business");
        list.add("http://10.76.81.173:8080/api/Init?category=e_business");
        list.add("http://10.76.81.174:8080/api/Init?category=e_business");
        list.add("http://10.76.81.175:8080/api/Init?category=e_business");
        list.add("http://10.76.81.176:8080/api/Init?category=e_business");
        list.add("http://10.76.81.177:8080/api/Init?category=e_business");
        list.add("http://10.76.81.178:8080/api/Init?category=e_business");
        list.add("http://10.76.81.179:8080/api/Init?category=e_business");
        list.add("http://10.76.81.180:8080/api/Init?category=e_business");
        list.add("http://10.76.81.181:8080/api/Init?category=e_business");
        list.add("http://10.76.81.182:8080/api/Init?category=e_business");
        list.add("http://10.76.81.183:8080/api/Init?category=e_business");
        list.add("http://10.76.81.184:8080/api/Init?category=e_business");
        list.add("http://10.76.81.185:8080/api/Init?category=e_business");


        for (String url:list){

            Request request = new Request();
            request.setMethod(HttpConst.Method.GET)
                    .putHeaderField(HttpConst.Header.ACCEPT, "application/json")
                    .putHeaderField(HttpConst.Header.CONTENT_TYPE, "application/json")
                    .setTimeOut(60000)
                    .setUrl(url);

            try {
                long start=System.currentTimeMillis();
                CloseableHttpResponse response = HttpClientUtil.createHttpClient(HttpClientUtil.HTTP_TYPE.HTTP)
                        .execute(HttpClientUtil.getHttpUriRequest(request));
                long end=System.currentTimeMillis();
                logger.info("B环境该任务url:"+url);
                logger.info("B环境执行该任务共耗时"+(end-start)+"毫秒");
                String result =HttpClientUtil.getResponseResult(response);

                logger.info("B环境该任务响应内容："+result);
            } catch (Exception e) {
                logger.info("B环境该任务出现异常：");
                e.printStackTrace();
                HttpRequestHelper.createGetHttpResponse(url.substring(7,19));
            }
        }
    }
}
