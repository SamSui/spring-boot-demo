package com.xisui.springbootdb.controller;

import com.xisui.springbootdb.domain.SysUser;
import com.xisui.springbootdb.service.SysUserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

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
    public String  getUser() {
        userService.testBatch();
        return "success";
    }
}
