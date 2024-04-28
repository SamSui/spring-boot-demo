package com.xisui.springbootdb.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@TableName("sys_user")
@Data
public class SysUser{
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private String name;
    private String nickName;
    private String address;
}
