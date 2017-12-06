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
 * Created by ps on 2017/11/14.
 * @author zhd
 */
@Component
public class MonitorJobsOld {

    public final static long ONE_Minute =   120*1000;
    private static final Logger logger= LoggerFactory.getLogger(MonitorJobsOld.class);

//    @Scheduled(fixedRate=ONE_Minute)
    public void fixedDelayJob(){
        jobs();
    }



    void jobs(){
        List<String> list=new ArrayList();
        list.add("http://10.76.84.21:9090/api/Init?category=e_business");
        list.add("http://10.76.84.22:9090/api/Init?category=e_business");
        list.add("http://10.76.84.23:9090/api/Init?category=e_business");
        list.add("http://10.76.84.24:9090/api/Init?category=e_business");
        list.add("http://10.76.84.25:9090/api/Init?category=e_business");
        list.add("http://10.76.84.26:9090/api/Init?category=e_business");
        list.add("http://10.76.84.27:9090/api/Init?category=e_business");
        list.add("http://10.76.84.28:9090/api/Init?category=e_business");
//        list.add("http://10.76.84.29:9090/api/Init?category=e_business");
        list.add("http://10.76.84.30:9090/api/Init?category=e_business");
        list.add("http://10.76.84.31:9090/api/Init?category=e_business");
        list.add("http://10.76.84.32:9090/api/Init?category=e_business");
        list.add("http://10.76.84.33:9090/api/Init?category=e_business");
        list.add("http://10.76.84.34:9090/api/Init?category=e_business");
        list.add("http://10.76.84.35:9090/api/Init?category=e_business");
        list.add("http://10.76.84.36:9090/api/Init?category=e_business");
        list.add("http://10.76.84.37:9090/api/Init?category=e_business");
        list.add("http://10.76.84.38:9090/api/Init?category=e_business");
        list.add("http://10.76.84.39:9090/api/Init?category=e_business");
        list.add("http://10.76.84.40:9090/api/Init?category=e_business");
        list.add("http://10.76.84.41:9090/api/Init?category=e_business");
        list.add("http://10.76.84.42:9090/api/Init?category=e_business");
        list.add("http://10.76.84.43:9090/api/Init?category=e_business");
        list.add("http://10.76.84.44:9090/api/Init?category=e_business");
        list.add("http://10.76.84.45:9090/api/Init?category=e_business");
        list.add("http://10.76.84.46:9090/api/Init?category=e_business");
        list.add("http://10.76.84.47:9090/api/Init?category=e_business");
        list.add("http://10.76.84.48:9090/api/Init?category=e_business");
        list.add("http://10.76.84.49:9090/api/Init?category=e_business");
        list.add("http://10.76.84.50:9090/api/Init?category=e_business");
        list.add("http://10.76.84.51:9090/api/Init?category=e_business");
        list.add("http://10.76.84.52:9090/api/Init?category=e_business");


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
                logger.info("该任务url:"+url);
                logger.info("执行该任务共耗时"+(end-start)+"毫秒");
                String result =HttpClientUtil.getResponseResult(response);

                logger.info("该任务响应内容："+result);
            } catch (Exception e) {
                logger.info("该任务出现异常：");
                e.printStackTrace();
                HttpRequestHelper.createGetHttpResponse(url.substring(7,18));
            }
        }
    }
}
