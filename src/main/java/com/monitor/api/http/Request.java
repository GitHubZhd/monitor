package com.monitor.api.http;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Pan on 16-12-7.
 * Description:
 * Version:
 */
public class Request {
    private String url;
    private String method = HttpConst.Method.GET;
    private Charset sendCharset;
    private Charset receiveCharset;
    private int statusCode;
    private int timeOut = 5000;
    private boolean isNeedCookie = false;

    private String jsonContent;

    private Map<String, String> _headField;
    private Map<String, String> _params;

    //region  构造函数
    public Request() {
    }

    public Request(String url) {
        this.url = url;
    }
    //endregion

    //region Url属性

    /**
     * 获得请求Url
     *
     * @return
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置请求Url
     *
     * @param url
     * @return
     */
    public Request setUrl(String url) {
        this.url = url;
        return this;
    }
    //endregion

    //region 头字段名

    /**
     * 根据头字段名获取字段值
     *
     * @param key
     * @return
     */
    public String getHeaderFieldValue(String key) {
        if (_headField == null) {
            return null;
        }

        return _headField.get(key);
    }

    /**
     * 设置头字段
     *
     * @param key
     * @param value
     * @return
     */
    public Request putHeaderField(String key, String value) {
        if (_headField == null) {
            _headField = new HashMap<String, String>();
        }
        _headField.put(key, value);
        return this;
    }

    /**
     * 获取头字段集
     *
     * @return
     */
    public Map<String, String> getHeaderFields() {
        return _headField;
    }
    //endregion

    //region Post参数属性处理

    /**
     * 设置Post请求单个参数键值对
     *
     * @param key
     * @param value
     * @return
     */
    public Request setParam(String key, String value) {
        if (_params == null) {
            _params = new HashMap<String, String>();
        }

        _params.put(key, value);
        return this;
    }

    /**
     * 批量设置Post请求参数
     *
     * @param params
     * @return
     */
    public Request setParams(String params) {
        if (_params == null) {
            _params = new HashMap<String, String>();
        }

        Map<String, String> pMap = HttpUtils.convertPostParamsStringToMap(params);

        _params.putAll(pMap);

        return this;
    }

    /**
     * 根据key获得对应参数值
     *
     * @param key
     * @return
     */
    public String getParam(String key) {
        if (_params == null) {
            return null;
        }
        return _params.get(key);
    }

    /**
     * 获取Post请求参数字符串形式
     *
     * @return
     */
    public String getStringParams() {
        return HttpUtils.convertPostParamsMaptoString(_params);
    }

    /**
     * 获取Post请求参数Map
     *
     * @return
     */
    public Map<String, String> getParams() {
        if (_params == null) {
            _params = new HashMap<String, String>();
        }

        if (method.equalsIgnoreCase(HttpConst.Method.POST)) {
            int i = url.indexOf("?");

            if (i > 0) {
                String str = url.substring(i + 1);
                if (str.length() > 0) {
                    _params.putAll(HttpUtils.convertPostParamsStringToMap(str));
                }
            }
        }
        return _params;
    }
    //endregion

    //region 请求方式

    /**
     * 获取请求方式
     *
     * @return
     */
    public String getMethod() {
        return method;
    }

    /**
     * 设置请求方式
     *
     * @param method
     * @return
     */
    public Request setMethod(String method) {
        this.method = method;
        return this;
    }
    //endregion

    //region 字符类型

    /**
     * 获得字符类型
     *
     * @return
     */
    public Charset getSendCharset() {
        return sendCharset;
    }

    /**
     * 设置字符类型
     *
     * @param charset
     * @return
     */
    public Request setSendCharset(Charset charset) {
        sendCharset = charset;
        return this;
    }

    /**
     * 获取返回字符集类型
     *
     * @return
     */
    public Charset getReceiveCharset() {
        return receiveCharset;
    }

    /**
     * 设置返回字符集类型
     *
     * @param receiveCharset
     * @return
     */
    public Request setReceiveCharset(Charset receiveCharset) {
        this.receiveCharset = receiveCharset;
        return this;
    }

    //endregion

    //region 请求结果状态码

    /**
     * 设置请求结果状态码
     *
     * @param statusCode
     * @return
     */
    public Request setStatusCode(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    /**
     * 获取请求结果状态码
     *
     * @return
     */
    public int getStatusCode() {
        return statusCode;
    }

    public boolean isNeedCookie() {
        return isNeedCookie;
    }

    public Request setNeedCookie(boolean needCookie) {
        isNeedCookie = needCookie;
        return this;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public Request setTimeOut(int timeOut) {
        this.timeOut = timeOut;
        return this;
    }

    public String getJsonContent() {
        return jsonContent;
    }

    public Request setJsonContent(String jsonContent) {
        this.jsonContent = jsonContent;
        return this;
    }
    //endregion
}
