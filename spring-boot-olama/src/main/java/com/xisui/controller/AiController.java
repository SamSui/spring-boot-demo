package com.xisui.controller;

import com.xisui.domain.AudioOptionsDTO;
import com.xisui.domain.ImageOptionsDTO;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.Generation;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.image.Image;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.ollama.OllamaChatClient;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ai")
public class AiController {
    @Resource
    private OllamaChatClient chatClient;
//
//    private final AiService aiService ;
//    public AiController(AiService aiService) {
//        this.aiService = aiService ;
//    }

//    @GetMapping("/normal")
//    public String normal(String message) {
//        return this.aiService.normalCompletion(message) ;
//    }
//    @GetMapping("/stream")
//    public Flux<ChatResponse> stream(String message) {
//        return this.aiService.streamCompletion(message) ;
//    }

    @GetMapping("/ollama/chat")
    public Flux<String> chat(String message) {
        String systemPrompt = "{prompt}";
        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(systemPrompt);
        Message userMessage = new UserMessage(message);
        Message systemMessage = systemPromptTemplate.createMessage(Map.of("prompt", "you are a helpful AI assistant"));

        List<Message> list = new ArrayList<>();
        list.add(userMessage);
        list.add(systemMessage);
        Prompt prompt = new Prompt(list);
       chatClient.call(prompt).getResults().forEach(generation->{
            AssistantMessage assistantMessage =  generation.getOutput();
            String content = assistantMessage.getContent();
            System.out.println(content);
        });

        return Flux.just("success");
    }

//    @PostMapping("/image")
//    public void image(@RequestBody ImageOptionsDTO image) {
//        ImageResponse resp = this.aiService.genImage(image.getInstructions(), image) ;
//        resp.getResults().forEach(imageGen -> {
//            Image img = imageGen.getOutput() ;
//            String imgBase64 = img.getB64Json() ;
//            System.out.println(imgBase64) ;
//            String url = img.getUrl() ;
//            System.out.println(url) ;
//        }) ;
//    }
}
