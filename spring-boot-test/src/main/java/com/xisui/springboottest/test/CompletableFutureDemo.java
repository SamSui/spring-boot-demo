package com.xisui.springboottest.test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CompletableFutureDemo {
    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 5, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10)) ;
        CompletableFuture<Double> task1 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2) ;
                System.out.println(Thread.currentThread().getName() + ", 任务1执行完成") ;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 10d ;
        }, executor) ;
        CompletableFuture<Double> task2 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(6) ;
                System.out.println(Thread.currentThread().getName() + ", 任务2执行完成") ;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 20d ;
        }, executor) ;
        task1.applyToEither(task2, res -> {
            return res ;
        }).whenComplete((res, tx) -> {
            System.out.println("获取到结果：" + res) ;
            if (tx != null) {
                System.err.println("发生错误了：" + tx.getMessage()) ;
            }
            executor.shutdown();
        }) ;
    }
}
