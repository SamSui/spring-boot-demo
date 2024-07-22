package com.xisui.springbootweb.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
/**
 * @author xisui not work
 * @date 2021/7/8 10:09
 */
@RestController
@RequestMapping("/api/git")
public class GitInfoController {

    @Value("${git.commit.id:}")
    private String commitId;

    @Value("${git.branch:}")
    private String branch;

    @Value("${git.commit.time:}")
    private String commitTime;

    @GetMapping("/git-info")
    public ResponseEntity<Map<String, String>> getGitInfo() {
        Map<String, String> gitInfo = new HashMap<>();
        gitInfo.put("commitId", commitId);
        gitInfo.put("branch", branch);
        gitInfo.put("commitTime", commitTime);
        return ResponseEntity.ok(gitInfo);
    }

    @GetMapping("/git-info2")
    public String getGitInfo2() throws Exception {
        Resource resource = new DefaultResourceLoader().getResource("classpath:git.properties");
        if (resource.exists()) {
            InputStream inputStream = resource.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuffer content = new StringBuffer();
            String s = "";
            while ((s = reader.readLine()) != null) {
                content = content.append(s);
            }
            return content.toString();
        } else {
            return "";
        }
    }
}