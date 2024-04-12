package com.xisui.springbootbatch.config;

import com.xisui.springbootbatch.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.item.Chunk;
import org.springframework.stereotype.Component;

import static java.lang.String.format;

@Component
public class  MyWriteListener implements ItemWriteListener<User> {

    private Logger logger = LoggerFactory.getLogger(MyWriteListener.class);

    @Override
    public void beforeWrite(Chunk<? extends User> items) {
    }

    @Override
    public void afterWrite(Chunk<? extends User> items) {
      System.out.println("writer after: " + Thread.currentThread().getName()) ;
    }

    @Override
    public void onWriteError(Exception exception, Chunk<? extends User> items) {
        try {
            logger.info(format("%s%n", exception.getMessage()));
            for (User item : items) {
                logger.info(format("Failed writing BlogInfo : %s", item.toString()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
