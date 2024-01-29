package com.xisui.springbootneo4j.controller;

import com.xisui.springbootneo4j.doman.Person;
import com.xisui.springbootneo4j.repository.PersonRepository;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/person")
@Slf4j
public class PersonController {
    @Resource
    private PersonRepository personRepository;

    @GetMapping("/create")
    public String createPerson(){
        Person greg = new Person("Greg");
        Person roy = new Person("Roy");
        Person craig = new Person("Craig");

        List<Person> team = Arrays.asList(greg, roy, craig);

        log.info("Before linking up with Neo4j...");

        team.forEach(person -> log.info("\t" + person.toString()));

        personRepository.save(greg);
        personRepository.save(roy);
        personRepository.save(craig);

        greg = personRepository.findByName(greg.getName());
        greg.worksWith(roy);
        greg.worksWith(craig);
        personRepository.save(greg);

        roy = personRepository.findByName(roy.getName());
        roy.worksWith(craig);
        // We already know that roy works with greg
        personRepository.save(roy);

        return "okok";
    }

    @GetMapping("/delete")
    public String deletePerson(){
        personRepository.deleteAll();
        return "success";
    }

    @GetMapping("/find")
    public String findPerson(){
        log.info("Lookup each person by name...");
        List<String> userNames = Arrays.asList("Greg", "Roy", "Craig");
        userNames.forEach(name -> log.info(
                "\t" + personRepository.findByName(name).toString()));

        List<Person> teammates = personRepository.findByTeammatesName("Craig");
        List<Person> teammates2 = personRepository.getByTeammatesName("Craig");
        log.info("The following have Greg as a teammate...");
        teammates.forEach(person -> log.info("\t" + person.getName()));

        return "success";
    }
}
