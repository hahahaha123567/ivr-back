package org.ivr.api.helper;

import com.google.common.collect.ImmutableMap;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.Map;

/**
 * @author zhangyaoxin@yiwise.com
 * @create 2019/04/04
 **/
public class HttpHelper {

    private static final Logger logger = LoggerFactory.getLogger(HttpHelper.class);

    private static final Map<String, String> HTTP_HEADER = ImmutableMap.of(
            "user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36",
            "cache-control", "max-age=0",
            "accept-encoding", "gzip, deflate, br",
            "accept-language", "zh-CN,zh;q=0.9,en;q=0.8"
    );

    public static String get(String url) {
        Assert.notNull(url, "http的get请求url为空");
        logger.info("发送get请求: {}", url);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        for (Map.Entry<String, String> entry : HTTP_HEADER.entrySet()) {
            httpGet.setHeader(entry.getKey(), entry.getValue());
        }
        String ret = null;
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                return null;
            }
            HttpEntity entity = response.getEntity();
            String res = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
            response.close();
            ret = res;
        } catch (IOException e) {
            logger.error("GET请求{}失败, errorStack:", url, e);
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                logger.error("关闭GET请求Client失败, errorStack:", e);
            }
        }
        return ret;
    }

    public static String concatUrl(String base, Map<String, Object> params) {
        Assert.notNull(base, "要拼接的url中base为空");
        StringBuilder sb = new StringBuilder(base);
        sb.append("?");
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            sb.append(entry.getKey());
            sb.append("=");
            sb.append(entry.getValue());
            sb.append("&");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
