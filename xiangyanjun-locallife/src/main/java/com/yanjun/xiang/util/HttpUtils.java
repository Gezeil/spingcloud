package com.yanjun.xiang.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.Set;

/**
 * @Description
 * @Author xiangyanjun
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2020/7/3 0003
 */
@Slf4j
public class HttpUtils {

    public static String sendXmlPost(String urlStr, String xmlInfo) {
        // xmlInfo xml具体字符串

        try {
            URL url = new URL(urlStr);
            URLConnection con = url.openConnection();
            con.setDoOutput(true);
            con.setRequestProperty("Pragma:", "no-cache");
            con.setRequestProperty("Cache-Control", "no-cache");
            con.setRequestProperty("Content-Type", "text/xml");
            OutputStreamWriter out = new OutputStreamWriter(
                    con.getOutputStream());
            out.write(new String(xmlInfo.getBytes("utf-8")));
            out.flush();
            out.close();
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String lines = "";
            for (String line = br.readLine(); line != null; line = br
                    .readLine()) {
                lines = lines + line;
            }
            return lines; // 返回请求结果
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "fail";
    }

    /**
     * 构建get方式的url
     *
     * @param reqUrl 基础的url地址
     * @param params 查询参数
     * @return 构建好的url
     */
    public static String buildUrl(String reqUrl, Map<String, String> params) {
        StringBuilder query = new StringBuilder();
        Set<String> set = params.keySet();
        for (String key : set) {
            query.append(String.format("%s=%s&", key, params.get(key)));
        }
        return reqUrl + "?" + query.toString();
    }

    public static final String httpClientPostString(String url, String body) {
        String result = "";
        HttpClient client = new HttpClient();
        client.getParams().setContentCharset("UTF-8");
        client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
        PostMethod postMethod = new PostMethod(url);
        try {
            postMethod.setRequestBody(body);
            Integer status = client.executeMethod(postMethod);
            postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");
            result = postMethod.getResponseBodyAsString();
        } catch (Exception e) {
            log.error(e.toString());
        } finally {
            postMethod.releaseConnection();
        }
        return result;
    }

    public static final String httpClientGet(String url,Map<String,String> map) {
        String result = "";
        HttpClient client = new HttpClient();
        client.getHttpConnectionManager().getParams().setConnectionTimeout(8000);
        String u = buildUrl(url, map);
        GetMethod getMethod = new GetMethod(u);
        try {
            client.executeMethod(getMethod);
            Integer status = client.executeMethod(getMethod);
            getMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");
            result = getMethod.getResponseBodyAsString();
        } catch (Exception e) {
            log.error(e.toString());
        } finally {
            getMethod.releaseConnection();
        }
        return result;
    }
}
