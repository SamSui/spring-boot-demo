package com.xisui.springbootdb.controller;

import com.xisui.springbootdb.domain.SysUser;
import com.xisui.springbootdb.service.SysUserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/user")
public class SysUserController {

    @Resource
    private SysUserService userService;

    @GetMapping("/info")
    public SysUser getUser(Long id) {
        return userService.getById(id);
    }

    @GetMapping("/save")
    public boolean save() {
        SysUser sysUser = new SysUser();
        sysUser.setName("xisui");
        sysUser.setNickName("sui");
        sysUser.setAddress("china");
        return userService.save(sysUser);
    }
    @GetMapping("/batch")
    public String  batch() throws SQLException {
        userService.testBatch();
        return "success";
    }

    @GetMapping("/batch2")
    public String  batch2() throws SQLException {
        userService.testBatch2();
        return "success";
    }

    @GetMapping("/batch3")
    public String  batch3() throws SQLException {
        userService.testBatch3();
        return "success";
    }
}
