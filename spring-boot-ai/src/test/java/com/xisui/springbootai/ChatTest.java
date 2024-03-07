package com.xisui.springbootai;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@SpringBootTest
class ChatTest {

    @Autowired
//    private OllamaChatClient chatClient;
    private OpenAiChatClient chatClient;

    @Test
    void contextLoads() {
        String message = """
                鲁迅和周树人是什么关系？
                """;
        System.out.println(chatClient.call(message));
    }

    @Test
    void streamChat() throws ExecutionException, InterruptedException {
        // 构建一个异步函数，实现手动关闭测试函数
        CompletableFuture<Void> future = new CompletableFuture<>();

        String message = """
                年终总结
                """;
        PromptTemplate promptTemplate = new PromptTemplate("""
                你是一个Java开发工程师，你擅长于写公司年底的工作总结报告，
                根据：{message} 场景写100字的总结报告
                """);
        Prompt prompt = promptTemplate.create(Map.of("message", message));
        chatClient.stream(prompt).subscribe(chatResponse -> {
            System.out.println("response: " + chatResponse.getResult().getOutput().getContent());
        }, throwable -> {
            System.err.println("err: " + throwable.fillInStackTrace());
        }, () -> {
            System.out.println("complete~!");
            // 关闭函数
            future.complete(null);
        });
        future.get();
    }


    @Test
    void OpenAiAnswerer() {

        //代理
        String proxyHost = "74.48.125.75";
        int proxyPort = 20;
        //String url = "https://api.openai.com/v1/completions";
        String url = "https://api.openai.com/v1/models";
        //String message = "hello";
        String message = "Hello, how are you?";
        // json为请求体
        String requestBody = "{\"prompt\": \"" + message + "\", \"max_tokens\": 100}";
        String result = "";
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection(proxy);
            connection.setRequestProperty("Authorization", "Bearer " + "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");
            connection.setRequestProperty("Content-Type", "application/json");

            //connection.setRequestMethod("POST");
            connection.setRequestMethod("GET");

            //connection.setDoOutput(true);
            //byte[] requestBodyBytes = requestBody.getBytes(StandardCharsets.UTF_8);
            //connection.getOutputStream().write(requestBodyBytes);
            //try (OutputStream outputStream = connection.getOutputStream()) {
            //    outputStream.write(requestBodyBytes, 0, requestBodyBytes.length);
            //}
            try (InputStream inputStream = connection.getInputStream()) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                result = response.toString();

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println(result);
    }
}
