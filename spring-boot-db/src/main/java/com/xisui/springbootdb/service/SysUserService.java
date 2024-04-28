package com.xisui.springbootdb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xisui.springbootdb.domain.SysUser;

public interface SysUserService extends IService<SysUser> {
    void testBatch();
}
