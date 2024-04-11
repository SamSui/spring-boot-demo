package com.xisui.springbootbatch.config;

import org.slf4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
public class BatchConfig extends DefaultBatchConfiguration {
    private static final int BATCH_SIZE = 10;
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(BatchConfig.class);
    @Bean
    public Job firstJob(JobRepository jobRepository, PlatformTransactionManager batchTransactionManager) {
        return new JobBuilder("firstJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(myJobListener())
                .start(chunkStep(jobRepository, batchTransactionManager))
                .next(taskletStep(jobRepository, batchTransactionManager))
                .build();
    }

    @Bean
    TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor() ;
        taskExecutor.setThreadNamePrefix("spring_batch_launcher") ;
        taskExecutor.setCorePoolSize(5) ;
        taskExecutor.setMaxPoolSize(10) ;
        taskExecutor.initialize() ;
        return taskExecutor ;
    }

    @Bean
    public MyJobListener myJobListener() {
        return new MyJobListener();
    }

    @Bean
    ItemProcessor<String, String> myProcessor() {
        return new ItemProcessor<String, String>() {
            @Override
            public String process(String item) throws Exception {
                logger.info("%s - 开始处理数据：%s%n", Thread.currentThread().getName(), item.toString()) ;
                // 模拟耗时操作
                // 在这里你可以对数据进行相应的处理。
                return item ;
            }
        } ;
    }

    @Bean
    public Step taskletStep(JobRepository jobRepository, PlatformTransactionManager batchTransactionManager) {
        return new StepBuilder("firstStep", jobRepository)
                .tasklet((stepContribution, chunkContext) -> {
                    logger.info("This is first tasklet step");
                    logger.info("SEC = {}", chunkContext.getStepContext().getStepExecutionContext());
                    return RepeatStatus.FINISHED;
                }, batchTransactionManager).build();
    }

    @Bean
    public Step chunkStep(JobRepository jobRepository, PlatformTransactionManager batchTransactionManager) {
        return new StepBuilder("firstStep", jobRepository)
                .<String, String>chunk(BATCH_SIZE, batchTransactionManager)
                .reader(reader())
                .listener(new MyReadListener())
                .processor(myProcessor())
                .writer(writer())
                .listener(new MyWriteListener())
                .build();
    }

    @Bean
    public ItemReader<String> reader() {
        List<String> data = Arrays.asList("Byte", "Code", "Data", "Disk", "File", "Input", "Loop", "Logic", "Mode", "Node", "Port", "Query", "Ratio", "Root", "Route", "Scope", "Syntax", "Token", "Trace");
        return new ListItemReader<>(data);
    }

    @Bean
    public ItemWriter<String> writer() {
        return items -> {
            for (var item : items) {
                logger.info("Writing item: {}", item);
            }
        };
    }
}
