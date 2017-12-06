package com.monitor.api.http;

/**
 * Created by Pan on 16-12-7.
 * Description:
 * Version:
 */
public class HttpConst {
    /**
     * Http请求常量
     */
    public static final class Method {
        public static final String GET = "GET";
        public static final String POST = "POST";
        public static final String PUT = "PUT";
        public static final String DELETE = "DELETE";
        public static final String HEAD = "HEAD";
        public static final String TRACE = "TRACE";
        public static final String CONNECT = "CONNECT";
    }

    /**
     * Http头部字段名常量
     */
    public static final class Header {
        public static final String REFERER = "Referer";
        public static final String USER_AGENT = "User-Agent";
        public static final String PRAGMA = "Pragma";
        public static final String HOST = "Host";
        public static final String ACCEPT = "Accept";
        public static final String CONNECTION = "Connection";
        public static final String ACCEPTEncoding = "Accept-Encoding";
        public static final String STATUS_CODE = "statusCode";
        public static final String CONTENT_TYPE="Content-type";
    }
}
