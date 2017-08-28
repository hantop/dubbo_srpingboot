package com.lizikj.common.util;

import com.alibaba.fastjson.JSON;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.Map.Entry;

/**
 * Created by Michael.Huang on 2017/4/1.
 */
public class HttpClientUtils {
    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);
    private static RequestConfig requestConfig = RequestConfig.custom()
            .setSocketTimeout(60 * 1000)
            .setConnectTimeout(60 * 1000)
            .setConnectionRequestTimeout(60 * 1000).build();

    /**
     * @param url
     * @param headers
     * @param postContent
     * @param defaultCharset
     * @return
     * @throws IOException
     */
    public static String httpPost(String url, Map<String, String> headers, String postContent, String defaultCharset) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = null;
        try {
            httpPost = new HttpPost(url);
            HttpEntity entity = new StringEntity(postContent, defaultCharset);
            Header[] params = new Header[headers.size()];
            Set<String> set = headers.keySet();
            Iterator<String> it = set.iterator();
            int i = 0;
            while (it.hasNext()) {
                String key = it.next();
                String value = String.valueOf(headers.get(key));
                params[i] = new BasicHeader(key, value);
                i++;
            }
            httpPost.setHeaders(params);
            httpPost.setEntity(entity);
            httpPost.setConfig(requestConfig);
            HttpResponse response = httpClient.execute(httpPost);
            return EntityUtils.toString(response.getEntity(), defaultCharset);
        } finally {
            if (null != httpPost) {
                httpPost.abort();
            }
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * @param url
     * @param headers
     * @param postContent
     * @return
     */
    public static String httpPost(String url, Map<String, String> headers, String postContent) throws IOException {
        return httpPost(url, headers, postContent, Charset.forName("UTF-8").displayName());
    }

    /**
     * @param url
     * @param headers
     * @param params
     * @return
     */
    public static String httpPost(String url, Map<String, String> headers, Map<String, Object> params) throws IOException {
        return httpPost(url, headers, JSON.toJSONString(params), Charset.forName("UTF-8").displayName());
    }

    /**
     * @param url
     * @param map
     * @param charset
     * @return
     */
    public static String doPost(String url, Map<String, String> map, String charset, String contentType) {
        CloseableHttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        try {
            //httpClient = new SSLClient();
            httpClient = HttpClientUtils.createSSLClientDefault();
            httpPost = new HttpPost(url);
            if (!StringUtils.isEmpty(contentType)) {
                httpPost.setHeader("content-type", contentType);
            }
            // 设置参数
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            Iterator<Entry<String, String>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Entry<String, String> entry = iterator.next();
                list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            if (list.size() > 0) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
                httpPost.setEntity(entity);
            }
            HttpResponse response = httpClient.execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, charset);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (null != httpPost) {
                httpPost.abort();
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException expect) {
                }
            }
        }
        return result;
    }

    public static String doPost(String url, Map<String, String> map, String charset) {
        return doPost(url, map, charset, "");
    }

    /**
     * @param url
     * @param map
     * @param charset
     * @return
     */
    public static String doPostWithConifg(String url, Map<String, String> map, String charset) {
        CloseableHttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        try {
            //httpClient = new SSLClient();
            httpClient = HttpClients.createDefault();
            httpPost = new HttpPost(url);
            httpPost.setConfig(requestConfig);
            // 设置参数
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            Iterator<Entry<String, String>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Entry<String, String> entry = iterator.next();
                list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            if (list.size() > 0) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
                httpPost.setEntity(entity);
            }
            HttpResponse response = httpClient.execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, charset);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (null != httpPost) {
                httpPost.abort();
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException expect) {
                }
            }
        }
        return result;
    }

    /**
     * @param url
     * @return
     */
    public static String doGet(String url) {
        CloseableHttpClient httpClient = null;
        HttpGet httpGet = null;
        String result = null;
        try {
            //httpClient = new SSLClient();
            httpClient = HttpClientUtils.createSSLClientDefault();
            httpGet = new HttpGet(url);
            HttpResponse response = httpClient.execute(httpGet);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, Charset.forName("UTF-8"));
                }
            }
        } catch (Exception ex) {
            logger.error("HttpClientUtils.doGet报错,url=" + url, ex);
        } finally {
            if (null != httpGet) {
                httpGet.abort();
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException expect) {
                }
            }
        }
        return result;
    }

    /**
     * 通过http get获取数据
     *
     * @param url
     * @return
     * @date 2017年3月24日
     */
    public static byte[] doGetRawData(String url) {
        CloseableHttpClient httpClient = null;
        HttpGet httpGet = null;
        byte[] result = null;
        try {
            httpClient = HttpClientUtils.createSSLClientDefault();
            httpGet = new HttpGet(url);
            HttpResponse response = httpClient.execute(httpGet);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toByteArray(resEntity);
                }
            }
        } catch (Exception ex) {
            logger.error("HttpClientUtils.doGet报错,url=" + url, ex);
        } finally {
            if (null != httpGet) {
                httpGet.abort();
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException expect) {
                }
            }
        }
        return result;
    }

    /**
     * @param url
     * @return
     */
    public static String doPost(String url, String content, Map<String, String> header) {
        CloseableHttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        try {
            //httpClient = new SSLClient();
            httpClient = HttpClientUtils.createSSLClientDefault();
            httpPost = new HttpPost(url);
            if (header != null) {
                Set<Entry<String, String>> headerSet = header.entrySet();
                for (Entry<String, String> entry : headerSet) {
                    httpPost.setHeader(entry.getKey(), entry.getValue());
                }
            }
            StringEntity stringEntity = new StringEntity(content, Charset.forName("UTF-8"));
            httpPost.setEntity(stringEntity);
            HttpResponse response = httpClient.execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, Charset.forName("UTF-8"));
                }
            }
        } catch (Exception ex) {
            logger.error("HttpClientUtils.doPost报错,url=" + url, ex);
        } finally {
            if (null != httpPost) {
                httpPost.abort();
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException expect) {
                }
            }
        }
        return result;
    }

    public static String doPost(String url, String content) {
        return doPost(url, content, null);
    }

    public static CloseableHttpClient createSSLClientDefault() {
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                // 信任所有
                @Override
                public boolean isTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {
                    return true;
                }
            }).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return HttpClients.createDefault();
    }

}


