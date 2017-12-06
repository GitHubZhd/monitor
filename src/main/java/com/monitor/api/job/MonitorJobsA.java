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
 * Created by ps on 2017/11/13.
 * @author zhd
 */
@Component
public class MonitorJobsA{

    public final static long ONE_Minute =   120*1000;
    private static final Logger logger= LoggerFactory.getLogger(MonitorJobsA.class);

    @Scheduled(fixedRate = ONE_Minute)
    public void fixedDelayJob(){
        jobs();
    }


    void jobs(){
        List<String> list=new ArrayList();
        list.add("http://10.76.81.61:8080/api/Init?category=e_business");
        list.add("http://10.76.81.62:8080/api/Init?category=e_business");
        list.add("http://10.76.81.63:8080/api/Init?category=e_business");
        list.add("http://10.76.81.64:8080/api/Init?category=e_business");
        list.add("http://10.76.81.65:8080/api/Init?category=e_business");
        list.add("http://10.76.81.66:8080/api/Init?category=e_business");
        list.add("http://10.76.81.67:8080/api/Init?category=e_business");
        list.add("http://10.76.81.68:8080/api/Init?category=e_business");
        list.add("http://10.76.81.69:8080/api/Init?category=e_business");
        list.add("http://10.76.81.70:8080/api/Init?category=e_business");
        list.add("http://10.76.81.71:8080/api/Init?category=e_business");
        list.add("http://10.76.81.72:8080/api/Init?category=e_business");
        list.add("http://10.76.81.73:8080/api/Init?category=e_business");
        list.add("http://10.76.81.74:8080/api/Init?category=e_business");
        list.add("http://10.76.81.75:8080/api/Init?category=e_business");
        list.add("http://10.76.81.76:8080/api/Init?category=e_business");
        list.add("http://10.76.81.77:8080/api/Init?category=e_business");
        list.add("http://10.76.81.78:8080/api/Init?category=e_business");
        list.add("http://10.76.81.79:8080/api/Init?category=e_business");
        list.add("http://10.76.81.80:8080/api/Init?category=e_business");
        list.add("http://10.76.81.81:8080/api/Init?category=e_business");
        list.add("http://10.76.81.82:8080/api/Init?category=e_business");
        list.add("http://10.76.81.83:8080/api/Init?category=e_business");
        list.add("http://10.76.81.84:8080/api/Init?category=e_business");
        list.add("http://10.76.81.85:8080/api/Init?category=e_business");


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
                logger.info("A环境该任务url:"+url);
                logger.info("A环境执行该任务共耗时"+(end-start)+"毫秒");
                String result =HttpClientUtil.getResponseResult(response);

                logger.info("A环境该任务响应内容："+result);
            } catch (Exception e) {
                logger.info("A环境该任务出现异常：");
                e.printStackTrace();
                HttpRequestHelper.createGetHttpResponse(url.substring(7,18));
            }
        }
    }

}
