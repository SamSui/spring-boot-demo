package com.xisui.springbootdb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xisui.springbootdb.domain.SysUser;

import java.sql.SQLException;

public interface SysUserService extends IService<SysUser> {
    void testBatch() throws SQLException;
}
