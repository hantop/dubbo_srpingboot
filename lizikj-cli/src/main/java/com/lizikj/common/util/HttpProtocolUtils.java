package com.lizikj.common.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * Created by Michael.Huang on 2017/4/1.
 */
public class HttpProtocolUtils {

    /**
     * 获取消息头信息
     */
    public static String getHeaderValues(HttpServletRequest request) {
        StringBuffer sf = new StringBuffer();
        Enumeration<String> enu = request.getHeaderNames();
        while (enu.hasMoreElements()) {
            String headerName = enu.nextElement();
            String headerValue = request.getHeader(headerName);
            sf.append(headerName).append("=").append(headerValue).append(",");
        }
        return sf.toString();
    }

    /**
     * 获取客户端真实IP
     */
    public String getRealIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
