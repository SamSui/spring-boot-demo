package com.xisui.springbootdb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xisui.springbootdb.domain.SysUser;
import com.xisui.springbootdb.mapper.SysUserMapper;
import com.xisui.springbootdb.service.SysUserService;
import jakarta.annotation.Resource;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private SqlSessionTemplate sqlSessionTemplate;

    //@Transactional(rollbackFor = Exception.class)
    public void testBatch() throws SQLException {
        SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession();
        Connection con = sqlSession.getConnection();
        try {
            con.setAutoCommit(false);
            ExecutorService service = Executors.newFixedThreadPool(5);
            List<Callable<Integer>> callableList  = new ArrayList<>();
            List<SysUser> lists = new ArrayList<>();
            for (int i =0;i<10;i++){
                SysUser sysUser = new SysUser();
                sysUser.setName("xisui"+i);
                sysUser.setNickName("xisui"+i);
                sysUser.setAddress("123456");
                lists.add(sysUser);
            }
            for (int i =0;i<lists.size();i++){
                int finalI = i;
                Callable<Integer> callable = () -> {
                    if(finalI == 5){
                        throw new RuntimeException("出现异常");
                    }
                    return sysUserMapper.insert(lists.get(finalI));
                };

                callableList.add(callable);
            }
            //执行子线程
            List<Future<Integer>> futures = service.invokeAll(callableList);
            for (Future<Integer> future:futures) {
                if (future.get()<=0){
                    throw new RuntimeException("添加失败");
                }
            }
            System.out.println("添加完毕");
            con.commit();
        }catch (Exception e){
            con.rollback();
            throw new RuntimeException("出现异常3123123123", e);
        }finally {
            con.close();
            sqlSession.close();
        }
    }
}
