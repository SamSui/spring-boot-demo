package com.xisui.springbootbatch.config;

import com.xisui.springbootbatch.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.stereotype.Component;

@Component
public class MyReadListener implements ItemReadListener<User> {

  private Logger logger = LoggerFactory.getLogger(MyReadListener.class);

  @Override
  public void beforeRead() {
  }

  @Override
  public void afterRead(User item) {
    System.out.println("reader after: " + Thread.currentThread().getName()) ;
  }

  @Override
  public void onReadError(Exception ex) {
    logger.info("读取数据错误：{}", ex);
  }
}
