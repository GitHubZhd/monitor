package com.monitor.api.http;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.util.Map;

/**
 * Created by Pan on 16-12-7.
 * Description:
 * Version:
 */
public class HttpClientUtil {

    public enum HTTP_TYPE {
        HTTP("http", 1), HTTPS("https", 2);
        private String name;
        private int index;

        HTTP_TYPE(String name, int index) {
            this.name = name;
            this.index = index;
        }

        public static String getName(int index) {
            for (HTTP_TYPE c : HTTP_TYPE.values()) {
                if (c.getIndex() == index) {
                    return c.name;
                }
            }
            return null;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }

    private static CloseableHttpClient httpClient=null;

    static {

        HTTP_TYPE http_type= HTTP_TYPE.HTTP;
        try {
            Registry<ConnectionSocketFactory> reg = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register(http_type.getName(),
                            http_type.getName().equals(HTTP_TYPE.HTTP.getName()) ?
                                    PlainConnectionSocketFactory.INSTANCE : new SSLConnectionSocketFactory(createIgnoreVerifySSL()))
                    .build();
            PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(reg);
            connectionManager.setMaxTotal(500);
            connectionManager.setDefaultMaxPerRoute(300);
            httpClient = HttpClients.custom().setConnectionManager(connectionManager).build();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }

    public static CloseableHttpClient getClient() throws KeyManagementException, NoSuchAlgorithmException {

        return httpClient;
    }

    public static CloseableHttpClient createHttpClient(HTTP_TYPE http_type) throws NoSuchAlgorithmException, KeyManagementException {
        Registry<ConnectionSocketFactory> reg = RegistryBuilder.<ConnectionSocketFactory>create()
                .register(http_type.getName(),
                        http_type.getName().equals(HTTP_TYPE.HTTP.getName()) ?
                                PlainConnectionSocketFactory.INSTANCE : new SSLConnectionSocketFactory(createIgnoreVerifySSL()))
                .build();
        BasicHttpClientConnectionManager connectionManager = new BasicHttpClientConnectionManager(reg, null, null, null);
        HttpClientBuilder httpClient = HttpClients.custom().setConnectionManager(connectionManager);

        return httpClient.build();
    }

    private static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sslContext = SSLContext.getInstance("SSL");

        X509TrustManager trustManager = new X509TrustManager() {

            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };

        sslContext.init(null, new TrustManager[]{trustManager}, new SecureRandom());
        return sslContext;
    }

    public static HttpUriRequest getHttpUriRequest(Request request) throws UnsupportedEncodingException {
        RequestBuilder requestBuilder = getRequestMethod(request).setUri(request.getUrl());

        Map<String, String> headers = request.getHeaderFields();
        if (headers != null) {
            for (Map.Entry<String, String> headerEntry : headers.entrySet()) {
                requestBuilder.addHeader(headerEntry.getKey(), headerEntry.getValue());
            }
        }

        if (request.getSendCharset() != null) {
            requestBuilder.setCharset(request.getSendCharset());
        } else {
            requestBuilder.setCharset(Charset.defaultCharset());
        }

        RequestConfig.Builder requestConfigBuilder = RequestConfig.custom()
                .setConnectionRequestTimeout(request.getTimeOut())
                .setSocketTimeout(request.getTimeOut())
                .setConnectTimeout(request.getTimeOut())
                .setCookieSpec(request == null ? CookieSpecs.IGNORE_COOKIES : (!request.isNeedCookie() ? CookieSpecs.IGNORE_COOKIES : CookieSpecs.STANDARD));

        requestBuilder.setConfig(requestConfigBuilder.build());
        return requestBuilder.build();
    }

    /**
     * 获取请求方法
     *
     * @param request
     * @return
     */
    private static RequestBuilder getRequestMethod(Request request) throws UnsupportedEncodingException {
        String method = request.getMethod();

        if (method == null || method.equalsIgnoreCase(HttpConst.Method.GET)) {
            return RequestBuilder.get();
        } else if (method.equalsIgnoreCase(HttpConst.Method.POST)) {
            RequestBuilder requestBuilder = RequestBuilder.post();
            if (request.getHeaderFields().containsKey(HttpConst.Header.CONTENT_TYPE)
                    && request.getHeaderFields().get(HttpConst.Header.CONTENT_TYPE).contains("application/json")) {
                String jsonParam = request.getJsonContent();

                HttpEntity entity = new StringEntity(null != jsonParam?jsonParam:"{}", ContentType.APPLICATION_JSON);
                requestBuilder.setEntity(entity);
            } else {
                int size = request.getParams().entrySet().size();
                NameValuePair[] nameValuePairs = new NameValuePair[size];
                int index = 0;
                for (Map.Entry<String, String> entry : request.getParams().entrySet()) {
                    nameValuePairs[index] = new BasicNameValuePair(entry.getKey(), entry.getValue());
                    index++;
                }

                if (nameValuePairs != null && nameValuePairs.length > 0) {
                    requestBuilder.addParameters(nameValuePairs);
                }
            }

            return requestBuilder;
        } else if (method.equalsIgnoreCase(HttpConst.Method.HEAD)) {
            return RequestBuilder.head();
        } else if (method.equalsIgnoreCase(HttpConst.Method.PUT)) {
            return RequestBuilder.put();
        } else if (method.equalsIgnoreCase(HttpConst.Method.DELETE)) {
            return RequestBuilder.delete();
        } else if (method.equalsIgnoreCase(HttpConst.Method.TRACE)) {
            return RequestBuilder.trace();
        }

        throw new IllegalArgumentException("Illegal HTTP Method " + method);
    }

    /**
     * 获取Response的响应内容
     * @param response
     * @return
     * @throws IOException
     */
    public static String getResponseResult(CloseableHttpResponse response) throws IOException {

        InputStream inpurtStream = response.getEntity().getContent();

        if(inpurtStream!=null){
            ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
            byte[] buff = new byte[100];
            int rc = 0;
            while ((rc = inpurtStream.read(buff, 0, 100)) > 0) {
                swapStream.write(buff, 0, rc);
            }
            byte[] dataByteArr = swapStream.toByteArray();
            return new String(dataByteArr);
        }

        return null;

    }
}
