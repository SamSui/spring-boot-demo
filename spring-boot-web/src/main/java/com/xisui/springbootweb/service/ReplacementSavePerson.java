package com.xisui.springbootweb.service;

import org.springframework.beans.factory.support.MethodReplacer;

import java.lang.reflect.Method;
import java.util.Arrays;

public class ReplacementSavePerson implements MethodReplacer {
  @Override
  public Object reimplement(Object obj, Method method, Object[] args) throws Throwable {
    System.out.println(Arrays.toString(args)) ;
    System.out.println(obj) ;
    return "replace save person" ;
  }
}
