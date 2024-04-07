package com.xisui.springboottest.proxy.proxyFactory;

public class PersonService implements CommonDAO{
  public void save() {
    System.out.println("save method invoke...") ;
  }
}
