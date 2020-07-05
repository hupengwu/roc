
package com.huawei.roc.http;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.ws.rs.HttpMethod;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.omg.CORBA.portable.ApplicationException;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;

@Named
public class SoaToolUtil {
    private static final Logger logger = LogManager.getLogger(SoaToolUtil.class);

    private static HttpClient httpsClient;

    /**
     * 返回https调用结果
     * @param requestType 参数
     * @param URL 参数
     * @param json 参数
     * @param token 参数
     * @param paramMap 参数
     * @return String
     * @throws Exception
     */
    public static String getResultByHttps(String requestType, String URL, String json, String token,
        Map<String, Object> paramMap) throws Exception {
        String result = "";
        httpsClient = new SSLClient();
        HttpResponse httpsResponse = null;
        if (HttpMethod.POST.equals(requestType)) {
            HttpPost httpPost = new HttpPost(URL);
            // 设置参数
            StringEntity stringEntity = new StringEntity(json, "UTF-8");
            httpPost.setEntity(stringEntity);
            // 鉴权参数
            httpPost.addHeader("Authorization", token);
            httpsResponse = httpsClient.execute(httpPost);
        } else if (HttpMethod.PUT.equals(requestType)) {
            HttpPut httpPut = new HttpPut(URL);
            // 设置参数
            StringEntity stringEntity = new StringEntity(json, "UTF-8");
            httpPut.setEntity(stringEntity);
            // 鉴权参数
            httpPut.addHeader("Authorization", token);
            httpsResponse = httpsClient.execute(httpPut);
        } else if (HttpMethod.GET.equals(requestType)) {
            String paramStr = "";
            Iterator<String> it = paramMap.keySet().iterator();
            if (it.hasNext()) {
                String key = it.next();
                String value = (String) paramMap.get(key);
                paramStr = key + "=" + value + "&";
            }
            if (!StringUtils.isEmpty(paramStr)) {
                paramStr = paramStr.substring(0, paramStr.length() - 1);// 去掉最后一个&
            }
            URL = URL + "?" + paramStr;
            HttpGet httpGet = new HttpGet(URL);
            httpGet.addHeader("Authorization", token);
            httpsResponse = httpsClient.execute(httpGet);
        } else if (HttpMethod.DELETE.equals(requestType)) {
            HttpDelete httpDelete = new HttpDelete(URL);
            httpDelete.setHeader("Content-Type", "application/json");
            httpDelete.addHeader("Authorization", token);
            httpsResponse = httpsClient.execute(httpDelete);
        }
        if (httpsResponse != null) {
            HttpEntity resEntity = httpsResponse.getEntity();
            if (resEntity != null) {
                result = EntityUtils.toString(resEntity, "UTF-8");
            }
        }
        return result;

    }

    /**
     * 返回https调用结果
     * @param requestType 参数
     * @param URL 参数
     * @param token 参数
     * @param paramMap 参数
     * @return String
     * @throws Exception
     */
    public static String getResultByHttps(String requestType, String URL, String token,
        String paramMap) throws Exception {
        String result = "";
        httpsClient = new SSLClient();
        HttpResponse httpsResponse = null;
        if (HttpMethod.POST.equals(requestType)) {
            HttpPost httpPost = new HttpPost(URL);
            // 设置参数
            StringEntity stringEntity = new StringEntity(paramMap, "UTF-8");
            httpPost.setEntity(stringEntity);
            // 鉴权参数
            httpPost.addHeader("Authorization", token);
            httpsResponse = httpsClient.execute(httpPost);
        }
        if (HttpMethod.PUT.equals(requestType)) {
            HttpPut httpPut = new HttpPut(URL);
            // 设置参数
            StringEntity stringEntity = new StringEntity(paramMap, "UTF-8");
            httpPut.setEntity(stringEntity);
            // 鉴权参数
            httpPut.addHeader("Authorization", token);
            httpsResponse = httpsClient.execute(httpPut);
        }
        if (HttpMethod.GET.equals(requestType)) {
            HttpGet httpGet = new HttpGet(URL);
            httpGet.addHeader("Authorization", token);
            httpsResponse = httpsClient.execute(httpGet);
        }
        if (httpsResponse != null) {
            HttpEntity resEntity = httpsResponse.getEntity();
            if (resEntity != null) {
                result = EntityUtils.toString(resEntity, "UTF-8");
            }
        }
        return result;

    }

    /**
     * 返回http调用结果
     * @param requestType 参数
     * @param URL 参数
     * @param json 参数
     * @param token 参数
     * @param paramMap 参数
     * @return String
     * @throws Exception
     */
    public static String getResultByHttp(String requestType, String URL, String json, String token,
        Map<String, Object> paramMap) throws Exception {
        String result = "";
        Request request = null;
        Response response = null;
        OkHttpClient okHttpClient = getOkHttpClient();
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), json.toString());
        Builder builder = getBuilder(token, URL);
        if (HttpMethod.POST.equals(requestType)) {
            request = builder.post(requestBody).build();
        } else if (HttpMethod.PUT.equals(requestType)) {
            request = builder.put(requestBody).build();
        } else if (HttpMethod.GET.equals(requestType) || HttpMethod.DELETE.equals(requestType)) {
            String paramStr = "";
            Iterator<String> it = paramMap.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                Object obj = paramMap.get(key);
                if (obj instanceof Set || obj instanceof Map || obj instanceof List) {
                    // ignore
                } else {
                    String value = String.valueOf(obj);
                    paramStr = paramStr + "&" + key + "=" + value;
                }
            }
            if (StringUtils.isNotEmpty(paramStr)) {
                paramStr = paramStr.substring(1);// 去掉最开始一个&
            }
            URL = URL + "?" + paramStr;
            logger.info("GET Request URL:{0}", URL);
            builder = getBuilder(token, URL);
            if (HttpMethod.GET.equals(requestType)) {
                request = builder.get().build();
            } else if (HttpMethod.DELETE.equals(requestType)) {
                request = builder.delete().build();
            }
        } else {
        }
        response = okHttpClient.newCall(request).execute();
        if (response.code() > 199 && response.code() < 310) {
            result = response.body().string();
        } else {
            result = response.body().string();
            logger.error("Call the SOA service failed：" + response.code() + "----" + response.message()
                + "---response body:" + result);
            String str = "熔断开关已开启";
            if (result.contains(str)) {
                logger.info(result);
                return result;
            }
            throw new Exception("---SOA---error-----URL:[" + "],httpCode:[" + response.code() + "],message:"
                + response.message() + "response body:" + response.body().string());
        }
        return result;
    }

    /**
     * 返回http调用结果
     * @param requestType 参数
     * @param URL 参数
     * @param token 参数
     * @param paramMap 参数
     * @return String
     * @throws Exception
     */
    private static String getResultByHttp(String requestType, String URL, String token,
        String paramMap) throws Exception {
        String result = "";
        Request request = null;
        Response response = null;
        OkHttpClient okHttpClient = getOkHttpClient();
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), paramMap);
        Builder builder = getBuilder(token, URL);
        if (HttpMethod.POST.equals(requestType)) {
            request = builder.post(requestBody).build();
        }
        if (HttpMethod.PUT.equals(requestType)) {
            request = builder.put(requestBody).build();
        }
        if (HttpMethod.GET.equals(requestType) || HttpMethod.DELETE.equals(requestType)) {
            builder = getBuilder(token, URL);
            if (HttpMethod.GET.equals(requestType)) {
                request = builder.get().build();
            } else if (HttpMethod.DELETE.equals(requestType)) {
                request = builder.delete().build();
            }
        }
        response = okHttpClient.newCall(request).execute();
        if (response.code() > 199) {
            result = response.body().string();
        }
        return result;
    }

    /**
     * soa调用
     * @param paramMap 请求参数
     * @param path 请求路径
     * @param requestType 请求方式
     * @return ResponseBody.body
     * @throws Exception
     */
    public static String sendRequestWithSoa(String paramMap, String path, String requestType) throws Exception {
        logger.info("sendRequestWithSoa start path:{},paramMap:{},requestType:{}", path, paramMap, requestType);
        String result = null;
        String token = getToken();
        String url = path;
        logger.info("Go HTTP request URL:{}", url);
        long startime = System.currentTimeMillis();
        if (url.startsWith("https")) {
            result = getResultByHttps(requestType, url, token, paramMap);
        } else {
            result = getResultByHttp(requestType, url, token, paramMap);
        }
        logger.info("sendRequestWithSoa start URL:{},paramMap:{},requestType:{},result:{}",
            url,
            paramMap,
            requestType,
            result);
        logger.info("The time of call the SOA service:{}", (System.currentTimeMillis() - startime));
        return result;
    }

    /**
     * 获取Builder
     * @since 2019年3月6日
     * @return Builder
     * @throws ApplicationException
     */
    private static Builder getBuilder(String token, String URL) {
        Builder builder = new Request.Builder().url(URL).addHeader("Authorization", token);
        builder.addHeader("X-App-Id", "com.huawei.ebg.bcm.mcr");
        return builder;
    }

    /**
     * 获取动态Token值
     * @since 2019年3月6日
     * @return String
     * @throws ApplicationException
     */
    private static String getToken() {
        String token = "";

        return token;
    }

    /**
     * 获取okHttpClient实例
     * @return okHttpClient
     */
    private static OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder buildClient = new OkHttpClient.Builder();
        buildClient.connectTimeout(120, TimeUnit.SECONDS);
        buildClient.readTimeout(120, TimeUnit.SECONDS);
        OkHttpClient okHttpClient = buildClient.build();
        return okHttpClient;
    }
}
