package com.xisui.springbootai;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class OpenAIApiTest {
    public static void main(String[] args) {
        testGet();
    }

    public static void testGet(){
        // 设置 OpenAI API 的密钥
        String apiKey = "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";

        // 设置请求 URL
        String apiUrl = "https://api.openai.com/v1/models";

        // 创建 HttpClient 对象
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // 创建 HttpGet 请求对象
            HttpGet httpGet = new HttpGet(apiUrl);

            // 设置请求头
            httpGet.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            httpGet.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey);


            // 发送请求并获取响应
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                // 处理响应
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    // 打印响应内容
                    System.out.println(EntityUtils.toString(entity));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void testPost(){
        // 设置 OpenAI API 的密钥
        String apiKey = "xxxx";

        // 设置请求 URL
        String apiUrl = "https://api.openai.com/v1/completions";

        // 设置请求的 JSON 数据
        String requestData = "{\"prompt\": \"Once upon a time\", \"max_tokens\": 50}";

        // 创建 HttpClient 对象
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // 创建 HttpPost 请求对象
            HttpPost httpPost = new HttpPost(apiUrl);

            // 设置请求头
            httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            httpPost.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey);

            // 设置请求体
            httpPost.setEntity(new StringEntity(requestData));

            // 发送请求并获取响应
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                // 处理响应
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    // 打印响应内容
                    System.out.println(EntityUtils.toString(entity));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
