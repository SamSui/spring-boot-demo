package com.xisui.springbootbatch.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.item.Chunk;
import org.springframework.stereotype.Component;

import static java.lang.String.format;

@Component
public class  MyWriteListener implements ItemWriteListener<String> {

    private Logger logger = LoggerFactory.getLogger(MyWriteListener.class);

    @Override
    public void beforeWrite(Chunk<? extends String> items) {
    }

    @Override
    public void afterWrite(Chunk<? extends String> items) {
      System.out.println("writer after: " + Thread.currentThread().getName()) ;
    }

    @Override
    public void onWriteError(Exception exception, Chunk<? extends String> items) {
        try {
            logger.info(format("%s%n", exception.getMessage()));
            for (String item : items) {
                logger.info(format("Failed writing BlogInfo : %s", item.toString()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
