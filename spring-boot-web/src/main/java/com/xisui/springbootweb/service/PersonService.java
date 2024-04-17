package com.xisui.springbootweb.service;

public class PersonService {
  public String savePerson(Person person) {
    return "save person" ;
  }

  public String savePerson(String name) {
    return "save person "+ name ;
  }
}
