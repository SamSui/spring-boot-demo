package com.xisui.springbootbatch.config;

import com.xisui.springbootbatch.domain.User;
import org.slf4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.boot.autoconfigure.batch.BatchDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Configuration
public class BatchConfig {
    private static final int BATCH_SIZE = 10;
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(BatchConfig.class);

    @Bean(name="batchDataSource")
    @Primary
    @BatchDataSource
    public DataSource batchDataSource(){
        return DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:3306/testdb?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8&nullCatalogMeansCurrent=true&allowMultiQueries=true&rewriteBatchedStatements=true")
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .username("root")
                .password("123456")
                .build();
    }

    @Bean(name="readDataSource")
    public DataSource readDataSource(){
        return DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:3306/dcdb?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8&nullCatalogMeansCurrent=true&allowMultiQueries=true&rewriteBatchedStatements=true")
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .username("root")
                .password("123456")
                .build();
    }

    @Bean("batchTransactionManager")
    public PlatformTransactionManager getTransactionManager() {
        return new JdbcTransactionManager(batchDataSource());
    }

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
    ItemProcessor<User, User> myProcessor() {
        return new ItemProcessor<User, User>() {
            @Override
            public User process(User item) throws Exception {
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
                .<User, User>chunk(BATCH_SIZE, batchTransactionManager)
                .reader(userReader())
                .listener(new MyReadListener())
                .processor(myProcessor())
                .writer(userWriter())
                .listener(new MyWriteListener())
                .build();
    }


    @Bean
    ItemReader<User> userReader() {
        JdbcCursorItemReaderBuilder<User> builder = new JdbcCursorItemReaderBuilder<>() ;
        builder.dataSource(readDataSource());
        builder.sql("select * from sys_user");
        builder.rowMapper(new BeanPropertyRowMapper<>(User.class)) ;
        //builder.rowMapper((rs, rowNum) -> {
        //    User user = new User() ;
        //    user.setUserId(rs.getLong("user_id")) ;
        //    user.setDeptId(rs.getLong("dept_id"));
        //    user.setUserName(rs.getString("user_name")) ;
        //    user.setNickName(rs.getString("nick_name")) ;
        //
        //    return user ;
        //}) ;
        builder.name("userReader") ;
        return builder.build() ;
    }

    @Bean
    ItemWriter<User> userWriter() {
        // 通过JDBC批量处理
        JdbcBatchItemWriterBuilder<User> builder = new JdbcBatchItemWriterBuilder<>() ;
        builder.dataSource(batchDataSource()) ;
        builder.sql("insert into sys_user (user_id, dept_id, user_name, nick_name) values (?, ?, ?, ?)") ;
        builder.itemPreparedStatementSetter(new ItemPreparedStatementSetter<User>() {
            @Override
            public void setValues(User item, PreparedStatement ps) throws SQLException {
                ps.setLong(1, item.getUserId()) ;
                ps.setLong(2, item.getDeptId()) ;
                ps.setString(3, item.getUserName()) ;
                ps.setString(4, item.getNickName()) ;
            }
        }) ;
        return builder.build() ;
    }

    //@Bean
    //public ItemReader<String> reader() {
    //    List<String> data = Arrays.asList("Byte", "Code", "Data", "Disk", "File", "Input", "Loop", "Logic", "Mode", "Node", "Port", "Query", "Ratio", "Root", "Route", "Scope", "Syntax", "Token", "Trace");
    //    return new ListItemReader<>(data);
    //}
    //
    //@Bean
    //public ItemWriter<String> writer() {
    //    return items -> {
    //        for (var item : items) {
    //            logger.info("Writing item: {}", item);
    //        }
    //    };
    //}
}
