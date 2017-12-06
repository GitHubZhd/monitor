package com.monitor.api.job;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.util.Enumeration;
import java.util.Map;

/**
 * Created by ps on 2017/8/23.
 */
public class UtilHelper {

    /**
     * 获取异常栈信息
     * @param e
     * @return
     */
    public static String getStackTrace(Exception e) {
        StringWriter writer = new StringWriter();
        e.printStackTrace(new PrintWriter(writer, true));
        return writer.toString();
    }

    /**
     * 摘要算法加密
     * @param algorithm
     * @param str
     * @return
     */
    public static String encrypt(String algorithm, String str) {
        try {

            //返回实现指定摘要算法的 MessageDigest 对象
            MessageDigest md = MessageDigest.getInstance(algorithm);
            //使用指定的 byte 数组更新摘要
            md.update(str.getBytes());
            StringBuffer sb = new StringBuffer();
            //通过执行诸如填充之类的最终操作完成哈希计算
            byte[] bytes = md.digest();


            for (int i = 0; i < bytes.length; i++) {
                int b = bytes[i] & 0xFF;
                if (b < 0x10) {
                    sb.append('0');
                }
                sb.append(Integer.toHexString(b));
            }
            return sb.toString();
        } catch (Exception e) {
            return "";
        }
    }

    public static boolean isWindowOS() {
        boolean isWindowOS = false;
        String osName = System.getProperty("os.name");
        if(osName.toLowerCase().indexOf("windows") > -1) {
            isWindowOS = true;
        }
        return isWindowOS;
    }

    public static InetAddress getInetAddress() {
        InetAddress inetAddress = null;
        try {
            //如果是windows操作系统
            if (isWindowOS()) {
                inetAddress = InetAddress.getLocalHost();
            } else {
                boolean bFindIP = false;
                //定义一个内容都是NetworkInterface的枚举对象
                Enumeration<NetworkInterface> netInterfaces = (Enumeration<NetworkInterface>)
                        NetworkInterface.getNetworkInterfaces();
                //如果枚举对象里面还有内容(NetworkInterface)
                while (netInterfaces.hasMoreElements()) {
                    if (bFindIP) {
                        break;
                    }
                    //获取下一个内容(NetworkInterface)
                    NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
                    //----------特定情况，可以考虑用ni.getName判断
                    //遍历所有IP
                    Enumeration<InetAddress> ips = ni.getInetAddresses();
                    while (ips.hasMoreElements()) {
                        inetAddress = (InetAddress) ips.nextElement();
                        if (inetAddress.isSiteLocalAddress()         //属于本地地址
                                && !inetAddress.isLoopbackAddress()  //不是回环地址
                                && inetAddress.getHostAddress().indexOf(":") == -1) {   //地址里面没有:号
                            bFindIP = true;     //找到了地址
                            break;              //退出循环
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inetAddress;
    }

    /**
     * 获取本机IP地址
     * @return
     */
    public static String getLocalIP() {
        return getInetAddress().getHostAddress();
    }

    /**
     * 格式化String
     * @param sourceStr
     * @param paras
     * @return
     */
    public static String formatString(String sourceStr,String... paras){
        String targetStr = sourceStr;
        for(int i=0;i<paras.length;i++){
            targetStr=targetStr.replace("{"+i+"}",paras[i]);
        }
        return targetStr;
    }

    /**
     * URL 编码
     * @param str
     * @return
     */
    public static String urlEncode(String str){

        try {
            return java.net.URLEncoder.encode(str,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Map转Bean
     * @param type
     * @param map
     * @return
     * @throws IntrospectionException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws InvocationTargetException
     */
    public static Object convertMap(Class type, Map map)
            throws IntrospectionException, IllegalAccessException,
            InstantiationException, InvocationTargetException {
        BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性
        Object obj = type.newInstance(); // 创建 JavaBean 对象

        // 给 JavaBean 对象的属性赋值
        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
        for (int i = 0; i< propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();

            if (map.containsKey(propertyName)) {
                Object value = map.get(propertyName);
                Object[] args = new Object[1];
                args[0] = value;

                descriptor.getWriteMethod().invoke(obj, args);
            }
        }
        return obj;
    }

    /**
     * unicode 转字符串
     */
    public static String unicode2String(String unicode) {

        StringBuffer string = new StringBuffer();

        String[] hex = unicode.split("\\\\u");

        for (int i = 1; i < hex.length; i++) {
            // 转换出每一个代码点
            int data = Integer.parseInt(hex[i], 16);
            // 追加成string
            string.append((char) data);
        }
        return string.toString();
    }

    /**
     * 字符串转换unicode
     */
    public static String string2Unicode(String string) {

        StringBuffer unicode = new StringBuffer();
        for (int i = 0; i < string.length(); i++) {
            // 取出每一个字符
            char c = string.charAt(i);
            // 转换为unicode
            unicode.append("\\u" + Integer.toHexString(c));
        }
        return unicode.toString();
    }

    /**
     * inputStream转换为字符串
     * @param inpurtStream
     * @return
     */
    public static String inputStream2String(InputStream inpurtStream){
        try{
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
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * inputStream转换为字节数组
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static byte[] InputStreamToByte(InputStream inputStream) throws IOException {
        if(inputStream==null){
            return new byte[]{};
        }
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc = 0;
        while ((rc = inputStream.read(buff, 0, 100)) > 0) {
            swapStream.write(buff, 0, rc);
        }
        byte[] dataByteArr = swapStream.toByteArray();
        return dataByteArr;

    }
}
