package com.xisui.springboottest.test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorDemo {
    public static void main(String[] args) throws InterruptedException {
        try {
            testCorePoolSizeForZeroThreads();
        } catch (Exception e) {
            System.out.println("testCorePoolSizeForZeroThreads: " + e.getMessage());
        }
        System.out.println("===================================");
        try {
            testCorePoolSize();
        } catch (Exception e) {
            System.out.println("testCorePoolSize: " + e.getMessage());
        }

    }

    private static void testCorePoolSizeForZeroThreads() {
        /*
         * 线程池初始化时，corePoolSize为0，
         * 1、第一次加入任务时，线程池会创建一个工作线程去执行任务
         * 2、非第一次加入任务时，线程池会判断队列是否满，如果满了，则创建一个非核心工作线程去执行任务，如果队列不满，则将任务加入队列
         * 3、如果队列已满，且线程池中线程数已超过maximumPoolSize，则线程池会执行拒绝策略
         */
        ThreadPoolExecutor executor = new ThreadPoolExecutor(0, 10, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10)) ;
        for (int i = 0; i < 20; i++) {
            executor.execute(() -> {

                System.out.println("testCorePoolSizeForZeroThreads: " + Thread.currentThread().getName() + " , info: " + executor.toString());
                try {
                    Thread.sleep(10);
                }catch (InterruptedException e){}
            });
        }
    }

    private static void testCorePoolSize() {
        /*
         * 线程池初始化时，corePoolSize大于0，
         * 1、当加入任务时，运行的线程数小于 corePoolSize，线程池会创建一个工作线程去执行任务
         * 2、运行的线程数大于 corePoolSize，线程池会判断队列是否满，如果满了，则创建一个非核心工作线程去执行任务，如果队列不满，则将任务加入队列
         * 3、如果队列已满，且线程池中线程数已超过maximumPoolSize，则线程池会执行拒绝策略
         */
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10)) ;
        for (int i = 0; i < 20; i++) {
            executor.execute(() -> {
                System.out.println("testCorePoolSize: " + Thread.currentThread().getName() + " , info: " + executor.toString());
                try {
                    Thread.sleep(10);
                }catch (InterruptedException e){}
            });
        }
    }
}
