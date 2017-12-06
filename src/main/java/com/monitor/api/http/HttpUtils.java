package com.monitor.api.http;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Pan on 16-12-7.
 * Description:
 * Version:
 */
public class HttpUtils {
    /**
     * 根据参数Map返回参数字符串
     *
     * @param params
     * @return
     */
    public static String convertPostParamsMaptoString(Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        String strParams;

        for (Map.Entry<String, String> entry : params.entrySet()) {
            sb.append(entry.getKey() + "=" + entry.getValue());
            sb.append("&");
        }

        if (sb.length() > 0) {
            sb.delete(sb.length() - 1, 1);
            strParams = sb.toString();
        } else {
            strParams = "";
        }

        return strParams;
    }

    public static Map<String, String> convertPostParamsStringToMap(String params) {
        Map<String, String> paramMap = new HashMap<String, String>();

        String[] paramsArr = params.split("&");
        String[] paramArr;

        for (String str : paramsArr) {
            paramArr = str.split("=");
            paramMap.put(paramArr[0], paramArr.length > 1 ? paramArr[1] : "");
        }

        return paramMap;
    }
}
