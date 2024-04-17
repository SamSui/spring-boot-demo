package com.xisui.springbootweb.controllers;

import com.xisui.springbootweb.service.Person;
import com.xisui.springbootweb.service.PersonService;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/preson")
public class PersonController {
    @Resource
    @Lazy
    private PersonService personService;

    @GetMapping("/save")
    public String savePerson() {
        Person person = new Person();
        person.setName("xisui");
        person.setAge(18);
        return personService.savePerson(person);
    }

    @GetMapping("/save2")
    public String savePerson2() {
        return personService.savePerson("person2");
    }
}
