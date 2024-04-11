package com.xisui.springbootbatch.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.stereotype.Component;

@Component
public class MyReadListener implements ItemReadListener<String> {

  private Logger logger = LoggerFactory.getLogger(MyReadListener.class);

  @Override
  public void beforeRead() {
  }

  @Override
  public void afterRead(String item) {
    System.out.println("reader after: " + Thread.currentThread().getName()) ;
  }

  @Override
  public void onReadError(Exception ex) {
    logger.info("读取数据错误：{}", ex);
  }
}
