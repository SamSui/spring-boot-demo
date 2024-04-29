package com.xisui.springbootdb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xisui.springbootdb.domain.SysUser;
import com.xisui.springbootdb.mapper.SysUserMapper;
import com.xisui.springbootdb.service.SysUserService;
import jakarta.annotation.Resource;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
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
    private static ExecutorService executorService = Executors.newFixedThreadPool(10);


    public void testBatch() throws SQLException {
        SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession();
        Connection con = sqlSession.getConnection();
        try {
            con.setAutoCommit(false);
            // 必须从session中获得mapper 否则session是隔离的
            SysUserMapper employeeMapper = sqlSession.getMapper(SysUserMapper.class);
            List<Callable<Integer>> callableList  = new ArrayList<>();
            List<SysUser> lists = new ArrayList<>();
            for (int i =0;i<10;i++){
                SysUser sysUser = new SysUser();
                sysUser.setName("testBatch_xisui"+i);
                sysUser.setNickName("testBatch_xisui"+i);
                sysUser.setAddress("123456");
                lists.add(sysUser);
            }
            for (int i =0;i<lists.size();i++){
                int finalI = i;
                Callable<Integer> callable = () -> {
                    if(finalI == 5){
                        throw new RuntimeException("出现异常");
                    }
                    return employeeMapper.insert(lists.get(finalI));
                };

                callableList.add(callable);
            }
            //执行子线程
            List<Future<Integer>> futures = executorService.invokeAll(callableList);
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


    @Transactional(rollbackFor = Exception.class)
    public void testBatch3() throws SQLException {
        // 回滚是不work的 虽然主程序能够获得异常 但是子线程的无法回滚
        try {
            List<Callable<Integer>> callableList  = new ArrayList<>();
            List<SysUser> lists = new ArrayList<>();
            for (int i =0;i<10;i++){
                SysUser sysUser = new SysUser();
                sysUser.setName("testBatch_xisui"+i);
                sysUser.setNickName("testBatch_xisui"+i);
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
            List<Future<Integer>> futures = executorService.invokeAll(callableList);
            for (Future<Integer> future:futures) {
                if (future.get()<=0){
                    throw new RuntimeException("添加失败");
                }
            }
            System.out.println("添加完毕");
        }catch (Exception e){
            throw new RuntimeException("出现异常3123123123", e);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void testBatch2() throws SQLException {
        for (int i =0;i<10;i++){
            SysUser sysUser = new SysUser();
            sysUser.setName("testBatch2_xisui"+i);
            sysUser.setNickName("testBatch2_xisui"+i);
            sysUser.setAddress("123456");
            if(i == 5){
                throw new RuntimeException("出现异常");
            }
            sysUserMapper.insert(sysUser);
        }
    }
}
