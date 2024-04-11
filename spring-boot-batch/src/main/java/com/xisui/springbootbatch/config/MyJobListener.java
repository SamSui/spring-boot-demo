package com.xisui.springbootbatch.config;

import org.slf4j.Logger;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class MyJobListener implements JobExecutionListener {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(MyJobListener.class);
    @Override
    public void beforeJob(JobExecution jobExecution) {
        logger.info("beforeJob");
    }
    @Override
    public void afterJob(JobExecution jobExecution) {
        logger.info("afterJob");
    }
}
